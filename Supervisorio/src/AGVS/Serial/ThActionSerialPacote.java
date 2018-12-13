package AGVS.Serial;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AGVS.Data.AGV;
import AGVS.Data.Cancelas;
import AGVS.Data.ConfigProcess;
import AGVS.Data.LogZoneTime;
import AGVS.Data.MeshSerial;
import AGVS.Data.PausablePlayer;
import AGVS.Data.PortaMashSerial;
import AGVS.Data.Tag;
import AGVS.Data.TagAtraso;
import AGVS.Util.Log;
import AGVS.Util.Util;
import WebService.http.Config;

public class ThActionSerialPacote extends Thread {

	private Map<Integer, String> status;
	private Map<Integer, String> status_war;
	private String pacote = "";
	private static final String comandoAtualizarPos = "R";
	private static final String comandoStatus = "S";
	private static final String comandoFrequencia = "F";
	private static final String comandoInformacoesAGV = "I";
	private static final String comandoInformacoesMesh = "M";
	private static final String comandoNext = "N";
	private Serial serial;
	private List<TagAtraso> tagsAtraso;
	private static int idFechaCancela = 0;
	private int[] pacoteInt = new int[255];
	private static long valTime = 0;
	
	public static FileInputStream streamMedia;
	public static PausablePlayer player;
	public static Boolean streamPlay = false;

	
	
	// Somente Para Primeiro desenvolvimento.

	public ThActionSerialPacote(String pacote, Serial serial, int[] pacoteInt) {
		
		status = new HashMap<Integer, String>();
		status_war = new HashMap<Integer, String>();
		for (int i = 0; i < AGV.statusAGV.length; i++) {
			for (int j = 0; j < AGV.statusAGVWar.length; j++) {
				if (AGV.statusAGV[i] == AGV.statusAGVWar[j])
					status_war.put(i + 1, AGV.statusAGV[i]);
			}
			status.put(i + 1, AGV.statusAGV[i]);

		}

		for (int j = 0; j < pacoteInt.length; j++) {
			this.pacoteInt[j] = pacoteInt[j];
		}

		this.pacote = pacote;
		this.serial = serial;
		this.start();

	}

	public void run() {
		Config config = Config.getInstance();
		try {
			System.out.println("PACOTE: " + pacote);
			String comando = Util.localizarStrXML(pacote, "<c>", "</c>");
			switch (comando) {
			case comandoInformacoesMesh:
				System.out.println("Updating status Mesh");
				int vStatusMesh = Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>"));
				for(MeshSerial ms : DatabaseStatic.mashs) {
					if (ms.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
						for (PortaMashSerial pms : ms.getLstPms()) {
							if (pms.getNome().equals(PortaMashSerial.getPort(vStatusMesh))) {
								pms.setStatus(PortaMashSerial.getPortState(vStatusMesh));
								ConfigProcess.bd().updatePortIn(pms.getPorta(), ms.getId(), PortaMashSerial.getPortState(vStatusMesh));
							}								
						}
						//verificar entradas da mesh para liberação de agvs na entrada das linhas
						if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_FIAT)) {
							if (verifyStatusPort("5", ms.getLstPms()) &&
								verifyStatusPort("6", ms.getLstPms())) {
								if (DatabaseStatic.bufferEntradaLinhaE != null ) {
									AGV agv = DatabaseStatic.bufferEntradaLinhaE;
									AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
									DatabaseStatic.bufferEntradaLinhaE = null;
									System.out.println("Entrada ESQUERDA AUTORIZADA pela Mesh. AGV: " + agv.getId());
								}
								
								/*if(DatabaseStatic.bufferEmRotaCarregadoLinhaE.size() == 0 && !streamPlay) {
									System.out.println("play");
									streamMedia = new FileInputStream("media/001.mp3"); 
									player = new PausablePlayer(streamMedia);
									player.play();
									streamPlay = true;
								}*/
							}/*else {
								if (player != null) {
									player.stop();
									streamPlay = false;
								}
							}*/
							
//							if( verifyStatusPort("5", ms.getLstPms()) &&
//								verifyStatusPort("6", ms.getLstPms())) {
//								////verificar se já saíram para entrega
//								if(DatabaseStatic.bufferEmRotaCarregadoLinhaE.size() == 0 && !streamPlay) {
//									streamMedia = new FileInputStream("media/001.mp3"); 
//									player = new PausablePlayer(streamMedia);
//									player.play();
//									streamPlay = true;
//								}
//							}else {
//								if (player != null) {
//									player.stop();
//									streamPlay = false;
//								}
//							}
							
///////////////////////////
							if (verifyStatusPort("3", ms.getLstPms()) && 
								verifyStatusPort("4", ms.getLstPms())) {
								if (DatabaseStatic.bufferEntradaLinhaD != null ) {
									AGV agv = DatabaseStatic.bufferEntradaLinhaD;
									AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
									DatabaseStatic.bufferEntradaLinhaD = null;
									System.out.println("Entrada DIREITA AUTORIZADA pela Mesh. AGV: " + agv.getId());
								}
								
								/*if(DatabaseStatic.bufferEmRotaCarregadoLinhaD.size() == 0 && !streamPlay) {
									System.out.println("play");
									streamMedia = new FileInputStream("media/001.mp3"); 
									player = new PausablePlayer(streamMedia);
									player.play();
									streamPlay = true;
								}*/
							}/*else {
								if (player != null) {
									System.out.println("PARAR");
									player.stop();
									streamPlay = false;
								}
							}*/
							
//							if(verifyStatusPort("3", ms.getLstPms()) && verifyStatusPort("4", ms.getLstPms())) {
//								////verificar se já saíram para entrega
//								System.out.println("MUSICA");
//								if(DatabaseStatic.bufferEmRotaCarregadoLinhaD.size() == 0 && !streamPlay) {
//									System.out.println("play");
//									streamMedia = new FileInputStream("media/001.mp3"); 
//									player = new PausablePlayer(streamMedia);
//									player.play();
//									streamPlay = true;
//								}
//							}else {
//								if (player != null) {
//									player.stop();
//									streamPlay = false;
//								}
//							}
						}
						///////////////////////////////////////////////////////////////
					}
				}
				break;
			case comandoInformacoesAGV:
				for (AGV agv : DatabaseStatic.lstAGVS) {
					if (agv.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
						agv.setIp(Util.localizarStrXML(pacote, "<p>", "</p>"));
						agv.setSinalWifi(Long.parseLong(Util.localizarStrXML(pacote, "<w>", "</w>")));
						ConfigProcess.bd().updateIPAGV(agv.getId(), agv.getIp());
					}
				}
				break;
			case comandoAtualizarPos:
				System.out.println("comandoAtualizarPos");
				String sb = "";

//				int pos1 = pacote.indexOf("<t>") + "<t>".length()+ 1;
//				int pos2 = pacote.indexOf("</t>")+ 1;
				int pos1 = pacote.indexOf("<t>") + "<t>".length();
				int pos2 = pacote.indexOf("</t>");
				for (int i = pos1; i < pos2; i++) {
					String temp = Integer.toHexString(pacoteInt[i]).toUpperCase();
					if (Integer.toHexString(pacoteInt[i]).toUpperCase().length() == 1) {
						temp = "0" + temp;
					}
					sb += temp;
				}
				
				long bateria = Integer.parseInt(Util.localizarStrXML(pacote, "<b>", "</b>"))/1000;
				int bateriaPercentual = 0;
				if (bateria < 21.9 ) {
					bateriaPercentual= 10;
				}else if(bateria >= 21.9 && bateria < 22.1) {
					bateriaPercentual = 30;
				}else if(bateria >= 22.1 && bateria < 23.1) {
					bateriaPercentual = 40;
				}else if(bateria >= 23.1 && bateria < 24) {
					bateriaPercentual = 50;
				}else if(bateria >= 24 && bateria < 24.5) {
					bateriaPercentual = 60;
				}else if(bateria >= 24.5 && bateria < 25.1) {
					bateriaPercentual = 70;
				}else if(bateria >= 25.1 && bateria < 25.3) {
					bateriaPercentual = 80;
				}else if(bateria >= 25.3 && bateria < 25.6) {
					bateriaPercentual = 90;
				}else if(bateria >= 25.6) {
					bateriaPercentual = 100;
				}
////////////////////////////////////////
				for (AGV agv : DatabaseStatic.lstAGVS) {
					if (agv.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
						agv.setStatus(status.get(Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>"))));
						agv.setTagAtual(sb.toString());
						agv.setTagAtualTime(System.currentTimeMillis());
						agv.setStatusTimeOld(new Date().getTime());
						agv.setBateria(bateriaPercentual);
						agv.setVelocidade(Integer.parseInt(Util.localizarStrXML(pacote, "<V>", "</V>")));
					}
				}
///////////////////////////////////////
				List<AGV> agvs = ConfigProcess.bd().selecAGVS();
				int j;
				for (j = 0; agvs != null && j < agvs.size(); j++) {
					
					AGV agv = agvs.get(j);
					if (agv.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
						String oldStatus = agv.getStatus();
						agv.setStatus(status.get(Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>"))));
						verifyStopByStatus(Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>")));
						if (!oldStatus.equals(agv.getStatus())) {
							ConfigProcess.bd().updateAGV(agv.getId(), agv.getStatus(), agv.getBateria(), agv.getVelocidade());
						}
						if (agv.getTagAtual() == null || !agv.getTagAtual().equals(sb.toString())) {
							
							ConfigProcess.bd().updateAGV(Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")),
									sb.toString(), bateriaPercentual,
									System.currentTimeMillis(), 0, status.get(Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>"))),
									Integer.parseInt(Util.localizarStrXML(pacote, "<V>", "</V>")));
							
							for (int t = 0; DatabaseStatic.cruzamentos != null
									&& t < DatabaseStatic.cruzamentos.size(); t++) {
								DatabaseStatic.cruzamentos.get(t).verificaCruzamento(agv, sb.toString());
							}
							
							//verificar entradas da mesh
							if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_FIAT)) {
								MeshSerial ms = DatabaseStatic.mashs.get(0);
								if (sb.toString().equals("3432") && agv.getFrequency() == 5) {
									if(	!(verifyStatusPort("5", ms.getLstPms()) &&
										verifyStatusPort("6", ms.getLstPms()))) {
										AGV.enviarEmFila(agv.getMac16(), agv.getMac64(), agv.getIp());
										System.out.println("Entrada ESQUERDA na linha NÃO LIBERADA pela Mesh");
										DatabaseStatic.bufferEntradaLinhaE = agv;
									}
									
								}else if(sb.toString().equals("3038") && agv.getFrequency() == 1) {
									if (!(verifyStatusPort("3", ms.getLstPms()) && 
										verifyStatusPort("4", ms.getLstPms()))) {
										AGV.enviarEmFila(agv.getMac16(), agv.getMac64(), agv.getIp());
										System.out.println("Entrada DIREITA na linha NÃO LIBERADA pela Mesh");
										DatabaseStatic.bufferEntradaLinhaD = agv;
										System.out.println("BUFFER DIREITO ADD: " + DatabaseStatic.bufferEntradaLinhaD);
									}
									
								}
								//////SAIRAM PARA ENTREGA///////////////////
								/*else if ((sb.toString().equals("3033") || sb.toString().equals("3032")) && agv.getFrequency() == 1) {
									if (DatabaseStatic.bufferEmRotaCarregadoLinhaD.size() == 0 && player != null) {
										player.stop();
										streamPlay = false;
									}
									
									if (!DatabaseStatic.bufferEmRotaCarregadoLinhaD.contains(agv)) {
										DatabaseStatic.bufferEmRotaCarregadoLinhaD.add(agv);
									}
																			
								}else if (sb.toString().equals("3431")  && agv.getFrequency() == 5) {
									if (DatabaseStatic.bufferEmRotaCarregadoLinhaE.size() == 0 && player != null) {
										player.stop();
										streamPlay = false;
									}
									
									if (!DatabaseStatic.bufferEmRotaCarregadoLinhaE.contains(agv)) {
										DatabaseStatic.bufferEmRotaCarregadoLinhaE.add(agv);
										System.out.println("add buffer esquerdo");
									}								
									
								}*/
								//////////////////////////////////////
								///////////////ENTREGARAM////////////
								/*else if ((sb.toString().equals("3839") || sb.toString().equals("3135")) && agv.getFrequency() == 1) {
									for (AGV agvInDeliveryDireita : DatabaseStatic.bufferEmRotaCarregadoLinhaD) {
										if (agvInDeliveryDireita.getId() == agv.getId()) {
											DatabaseStatic.bufferEmRotaCarregadoLinhaD.remove(agv);
										}
									}
								}else if ((sb.toString().equals("3438") || sb.toString().equals("3439")) && agv.getFrequency() == 5) {
									for (AGV agvInDeliveryEsquerda : DatabaseStatic.bufferEmRotaCarregadoLinhaE) {
										if (agvInDeliveryEsquerda.getId() == agv.getId()) {
											DatabaseStatic.bufferEmRotaCarregadoLinhaE.remove(agv);
										}
									}
								}*/
								///////////////////////////////////////
							}
							

							for (int i = 0; DatabaseStatic.cancelas != null && i < DatabaseStatic.cancelas.size(); i++) {
								DatabaseStatic.cancelas.get(i).cruzamentoCancela(sb, agv);
							}

							for (int i = 0; DatabaseStatic.cruzamentoMash != null
									&& i < DatabaseStatic.cruzamentoMash.size(); i++) {

								DatabaseStatic.cruzamentoMash.get(i).cruzamentoCancela(sb, agv);
							}
							for (int i = 0; DatabaseStatic.tagCruzamentoMash != null
									&& i < DatabaseStatic.tagCruzamentoMash.size(); i++) {

								DatabaseStatic.tagCruzamentoMash.get(i).cruzamento(sb, agv);
							}
							
							List<Tag> tag = ConfigProcess.bd().selecTags(sb.toString());
							if (tag != null && tag.size() > 0) {
								ConfigProcess.bd().insertLogTags(System.currentTimeMillis(),
										Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")), tag.get(0).getNome(),
										sb.toString(), bateriaPercentual, 
										Integer.parseInt(Util.localizarStrXML(pacote, "<V>", "</V>")));
							} else {
								ConfigProcess.bd().insertLogTags(System.currentTimeMillis(),
										Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")), "Sem Cadastro",
										sb.toString(), bateriaPercentual, 
										Integer.parseInt(Util.localizarStrXML(pacote, "<V>", "</V>")));
							}
							
							for (int i = 0; DatabaseStatic.zoneTimes != null && i < DatabaseStatic.zoneTimes.size(); i++) {
								DatabaseStatic.zoneTimes.get(i).verificarZoneTime(agv, sb.toString());
							}
						}else {
							System.out.println("TAG JÁ PROCESSADA");
						}
					}
				}

				int atraso = 0;
				
				

				break;
			case comandoStatus:
				System.out.println("Updating status");
				int vStatus = Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>"));
				// Comando da MashSerial
				if (vStatus >= 100 && vStatus <= 101) {
					System.out.println("Info Cancela: " + vStatus);
					for (int i = 0; DatabaseStatic.cancelas != null && i < DatabaseStatic.cancelas.size(); i++) {
						Cancelas cl = DatabaseStatic.cancelas.get(i);
						int id = Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"));
						if (cl.getC1().getMs().getId() == id) {
							System.out.println("Cancela 1");
							cl.setStatusC1(vStatus);
						}
						if (cl.getC2().getMs().getId() == id) {
							System.out.println("Cancela 2");
							cl.setStatusC2(vStatus);
						}
					}
					return;
				}else if (vStatus >= 18 && vStatus <= 32) {
					System.out.println("Updating status Mesh");
					for(MeshSerial ms : DatabaseStatic.mashs) {
						if (ms.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
							for (PortaMashSerial pms : ms.getLstPms()) {
								if (pms.getNome().equals(PortaMashSerial.getPort(vStatus))) {
									pms.setStatus(PortaMashSerial.getPortState(vStatus));
									ConfigProcess.bd().updatePortIn(pms.getPorta(), ms.getId(), PortaMashSerial.getPortState(vStatus));
								}								
							}
							//verificar entradas da mesh para liberação de agvs na entrada das linhas
							if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_FIAT)) {
								if (verifyStatusPort("5", ms.getLstPms()) &&
									verifyStatusPort("6", ms.getLstPms())) {
									if (DatabaseStatic.bufferEntradaLinhaE != null ) {
										AGV agv = DatabaseStatic.bufferEntradaLinhaE;
										AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
										DatabaseStatic.bufferEntradaLinhaE = null;
										System.out.println("Entrada ESQUERDA AUTORIZADA pela Mesh. AGV: " + agv.getId());
									}
									
									/*if(DatabaseStatic.bufferEmRotaCarregadoLinhaE.size() == 0 && !streamPlay) {
										System.out.println("play");
										streamMedia = new FileInputStream("media/001.mp3"); 
										player = new PausablePlayer(streamMedia);
										player.play();
										streamPlay = true;
									}*/
								}/*else {
									if (player != null) {
										player.stop();
										streamPlay = false;
									}
								}*/
								
//								if( verifyStatusPort("5", ms.getLstPms()) &&
//									verifyStatusPort("6", ms.getLstPms())) {
//									////verificar se já saíram para entrega
//									if(DatabaseStatic.bufferEmRotaCarregadoLinhaE.size() == 0 && !streamPlay) {
//										streamMedia = new FileInputStream("media/001.mp3"); 
//										player = new PausablePlayer(streamMedia);
//										player.play();
//										streamPlay = true;
//									}
//								}else {
//									if (player != null) {
//										player.stop();
//										streamPlay = false;
//									}
//								}
								
	///////////////////////////
								if (verifyStatusPort("3", ms.getLstPms()) && 
									verifyStatusPort("4", ms.getLstPms())) {
									if (DatabaseStatic.bufferEntradaLinhaD != null ) {
										AGV agv = DatabaseStatic.bufferEntradaLinhaD;
										AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
										DatabaseStatic.bufferEntradaLinhaD = null;
										System.out.println("Entrada DIREITA AUTORIZADA pela Mesh. AGV: " + agv.getId());
									}
									
									/*if(DatabaseStatic.bufferEmRotaCarregadoLinhaD.size() == 0 && !streamPlay) {
										System.out.println("play");
										streamMedia = new FileInputStream("media/001.mp3"); 
										player = new PausablePlayer(streamMedia);
										player.play();
										streamPlay = true;
									}*/
								}/*else {
									if (player != null) {
										System.out.println("PARAR");
										player.stop();
										streamPlay = false;
									}
								}*/
								
//								if(verifyStatusPort("3", ms.getLstPms()) && verifyStatusPort("4", ms.getLstPms())) {
//									////verificar se já saíram para entrega
//									System.out.println("MUSICA");
//									if(DatabaseStatic.bufferEmRotaCarregadoLinhaD.size() == 0 && !streamPlay) {
//										System.out.println("play");
//										streamMedia = new FileInputStream("media/001.mp3"); 
//										player = new PausablePlayer(streamMedia);
//										player.play();
//										streamPlay = true;
//									}
//								}else {
//									if (player != null) {
//										player.stop();
//										streamPlay = false;
//									}
//								}
							}
							///////////////////////////////////////////////////////////////
						}
					}
					break;
				}else if (status.containsKey(vStatus)) {
					System.out.println(status.get(vStatus));
					long baterias = Integer.parseInt(Util.localizarStrXML(pacote, "<b>", "</b>"))/1000;
					int bateriaPercentuals = 0;
					if (baterias < 21.9 ) {
						bateriaPercentuals = 10;
					}else if(baterias >= 21.9 && baterias < 22.1) {
						bateriaPercentuals = 30;
					}else if(baterias >= 22.1 && baterias < 23.1) {
						bateriaPercentuals = 40;
					}else if(baterias >= 23.1 && baterias < 24) {
						bateriaPercentuals = 50;
					}else if(baterias >= 24 && baterias < 24.5) {
						bateriaPercentuals = 60;
					}else if(baterias >= 24.5 && baterias < 25.1) {
						bateriaPercentuals = 70;
					}else if(baterias >= 25.1 && baterias < 25.3) {
						bateriaPercentuals = 80;
					}else if(baterias >= 25.3 && baterias < 25.6) {
						bateriaPercentuals = 90;
					}else if(baterias >= 25.6) {
						bateriaPercentuals = 100;
					}
////////////////////////////////////////////////////////					
					for (AGV agv : DatabaseStatic.lstAGVS) {
						if (agv.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
							agv.setStatus(status.get(vStatus));
							agv.setStatusTimeOld(System.currentTimeMillis());
							agv.setBateria(bateriaPercentuals);
							agv.setVelocidade(Integer.parseInt(Util.localizarStrXML(pacote, "<V>", "</V>")));
						}
					}
////////////////////////////////////////////////////////					

					
					List<AGV> agvs2 = ConfigProcess.bd().selecAGVS();
					AGV agv = null;
					for (j = 0; agvs2 != null && j < agvs2.size(); j++) {
						agv = agvs2.get(j);
						if (agv.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
							/*VERIFICA SE O STATUS ATUAL É IGUAL AO ÚLTIMO STATUS - EVITAR STATUS REPETIDO EM SEQUENCIA */
							String msg = status.get(vStatus);
							if (!agv.getStatus().equals(msg)) {
								ConfigProcess.bd().insertFalhas(Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")), msg,
										System.currentTimeMillis());
							}
							///////////////////////////////////////////////////////////////////////////////////////////////
							
							agv.setStatus(status.get(vStatus));
//							System.out.println("--"+agv.getStatus());
							for (int t = 0; DatabaseStatic.cruzamentos != null
									&& t < DatabaseStatic.cruzamentos.size(); t++) {
								 DatabaseStatic.cruzamentos.get(t).execLiberaVerificaAGV(agv);
							}
							for (int i = 0; DatabaseStatic.tagCruzamentoMash != null
									&& i < DatabaseStatic.tagCruzamentoMash.size(); i++) {
								DatabaseStatic.tagCruzamentoMash.get(i).cruzamento(agv);
							}
							for (int i = 0; DatabaseStatic.cancelas != null
									&& i < DatabaseStatic.cancelas.size(); i++) {
								DatabaseStatic.cancelas.get(i).cruzamentoCancela(agv);
							}
							for (int i = 0; DatabaseStatic.cruzamentoMash != null
									&& i < DatabaseStatic.cruzamentoMash.size(); i++) {
								DatabaseStatic.cruzamentoMash.get(i).cruzamentoCancela(agv);
							}
							
							/*
							 * VERIFICA SE O STATUS ATUAL É IGUAL AO ÚLTIMO STATUS - EVITAR STATUS REPETIDO
							 * EM SEQUENCIA
							 */
//							String msg2 = status.get(vStatus);
//							if (!agv.getStatus().equals(msg2)) {
//								ConfigProcess.bd().insertFalhas(
//										Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")), msg2,
//										System.currentTimeMillis());
//								
//								
//							}
						}
					}
//					System.out.println("bd");
					ConfigProcess.bd().updateAGV(Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")),
							status.get(vStatus), 
							bateriaPercentuals, Integer.parseInt(Util.localizarStrXML(pacote, "<V>", "</V>")));					
					
					if (status_war.containsKey(vStatus)) {
						ConfigProcess.bd().updateAGV(Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")),
								status_war.get(vStatus), System.currentTimeMillis(),
								bateriaPercentuals, Integer.parseInt(Util.localizarStrXML(pacote, "<V>", "</V>")));
					}
					verifyStopByStatus(vStatus);
				}

				break;
			case comandoFrequencia:
				List<AGV> agvs3 = ConfigProcess.bd().selecAGVS();
				
				int iFrequencia = Integer.parseInt(Util.localizarStrXML(pacote, "<f>", "</f>"));
				int iAgv = Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"));
				
				int aux;
				for (aux = 0; agvs3 != null && aux < agvs3.size(); aux++) {
					AGV agv = agvs3.get(aux);
					if (agv.getId() == iAgv) {
						ConfigProcess.bd().updateAGVFrequency(iAgv, iFrequencia);
						DatabaseStatic.resetAGVS();
					}
				}
				
				
				break;
			default:
				break;
			}
		} catch (Exception e) {
			new Log(e);
		}
	}
	
	private void verifyStopByStatus(int vStatus) {
		//VERIFICA SE ELE ESTÁ DENTRO DE UMA ZONA DE TEMPO
		for (int i = 0; DatabaseStatic.logZoneTimes != null && i < DatabaseStatic.logZoneTimes.size(); i++) {
			LogZoneTime lzt = DatabaseStatic.logZoneTimes.get(i);
			if (lzt.getAgv().getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i"))) {
				if (status.get(vStatus) == AGV.statusObstaculo) {
					System.out.println("Obstaculo em zona de tempo");
					lzt.setObstacle(true);
				}else {
					lzt.setObstacle(false);
				}
				break;
			}
		}
	}
	
	private boolean verifyStatusPort(String port, List<PortaMashSerial> lstPms) {
		for(PortaMashSerial pms : lstPms) {
				if (!pms.getPorta().equals(null) && pms.getPorta().equals(port)) {
					if (pms.getStatus()!=null)
						if (pms.getStatus().equals(pms.getAcionamento())) return true;
					return false;
				}
			
			
		}
		return false;
	}

}
