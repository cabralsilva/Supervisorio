package AGVS.Serial;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AGVS.Data.AGV;
import AGVS.Data.AlertFalhas;
import AGVS.Data.Cancelas;
import AGVS.Data.ComandoMashSerial;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Cruzamento_OLD;
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
									AGV.enviarPlay(agv.getIp(), agv.getMac64());
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
									AGV.enviarPlay(agv.getIp(), agv.getMac64());
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

					System.out.println(sb.toString());
				}
////////////////////////////////////////
				for (AGV agv : DatabaseStatic.lstAGVS) {
					if (agv.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
						agv.setStatus(status.get(Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>"))));
						agv.setTagAtual(sb.toString());
						agv.setTagAtualTime(System.currentTimeMillis());
						agv.setStatusTimeOld(new Date().getTime());
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
							ConfigProcess.bd().updateAGV(agv.getId(), agv.getStatus());
						}
//						System.out.println("Tag: " + sb.toString());
//						if (agv.getTagAtual() == null || !agv.getTagAtual().equals(sb.toString())) {
							for (int t = 0; DatabaseStatic.cruzamentos != null
									&& t < DatabaseStatic.cruzamentos.size(); t++) {
//								
//								System.out.println("Verificando cruzamentos: " + DatabaseStatic.cruzamentos.get(t).getNome());
								DatabaseStatic.cruzamentos.get(t).verificaCruzamento(agv, sb.toString());
							}
							
							//verificar entradas da mesh
							if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_FIAT)) {
								MeshSerial ms = DatabaseStatic.mashs.get(0);
								if (sb.toString().equals("3432") && agv.getFrequency() == 5) {
									if(	!(verifyStatusPort("5", ms.getLstPms()) &&
										verifyStatusPort("6", ms.getLstPms()))) {
										AGV.enviarEmFila(agv.getIp(), agv.getMac64());
										System.out.println("Entrada ESQUERDA na linha NÃO LIBERADA pela Mesh");
										DatabaseStatic.bufferEntradaLinhaE = agv;
									}
									
								}else if(sb.toString().equals("3038") && agv.getFrequency() == 1) {
									if (!(verifyStatusPort("3", ms.getLstPms()) && 
										verifyStatusPort("4", ms.getLstPms()))) {
										AGV.enviarEmFila(agv.getIp(), agv.getMac64());
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

//							for (int i = 0; DatabaseStatic.semafaros != null && i < DatabaseStatic.semafaros.size(); i++) {
//								if (!agv.getStatus().equals(AGV.statusManual)) {
//									DatabaseStatic.semafaros.get(i).verificaStateSinal(sb, agv);
//								}
//							}
							
							List<Tag> tag = ConfigProcess.bd().selecTags(sb.toString());
							if (tag != null && tag.size() > 0) {
								ConfigProcess.bd().insertLogTags(System.currentTimeMillis(),
										Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")), tag.get(0).getNome(),
										sb.toString());
							} else {
								ConfigProcess.bd().insertLogTags(System.currentTimeMillis(),
										Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")), "Sem Cadastro",
										sb.toString());
							}
							
							for (int i = 0; DatabaseStatic.zoneTimes != null && i < DatabaseStatic.zoneTimes.size(); i++) {
								DatabaseStatic.zoneTimes.get(i).verificarZoneTime(agv, sb.toString());
							}
//						}else {
//							System.out.println("TAG JÁ PROCESSADA");
//						}
					}
				}

				int atraso = 0;
				
				ConfigProcess.bd().updateAGV(Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")),
						sb.toString(), 100,
						System.currentTimeMillis(), atraso, status.get(Integer.parseInt(Util.localizarStrXML(pacote, "<s>", "</s>"))));

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
				}else if (status.containsKey(vStatus)) {
					System.out.println(status.get(vStatus));
////////////////////////////////////////////////////////					
					for (AGV agv : DatabaseStatic.lstAGVS) {
						if (agv.getId() == Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>"))) {
							agv.setStatus(status.get(vStatus));
							agv.setStatusTimeOld(System.currentTimeMillis());
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
							
							
						}
					}
					String msg = status.get(vStatus);
					if (msg.equals(AGV.statusHome) && ConfigProcess.xmlControleGoodyear != null) {
						ConfigProcess.xmlControleGoodyear
								.pedidoFinalizado(Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")));
					}
//					switch (vStatus) {
//					case 10:
//						String m1V = Util.localizarStrXML(pacote, "<v1>", "</v1>");
//						String m2V = Util.localizarStrXML(pacote, "<v2>", "</v2>");
//						msg += " (Motor 1: " + m1V + "A, Motor 2: " + m2V + "A)";
//						break;
//					case 12:
//						String m1A = Util.localizarStrXML(pacote, "<v1>", "</v1>");
//						String m2A = Util.localizarStrXML(pacote, "<v2>", "</v2>");
//						msg += " (Motor 1: " + m1A + "ºC, Motor 2: " + m2A + "ºC)";
//						break;
//
//					default:
//						break;
//					}
					
										
					ConfigProcess.bd().updateAGV(Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")),
							status.get(vStatus));
					if (status_war.containsKey(vStatus)) {
						ConfigProcess.bd().updateAGV(
								Integer.parseInt(Util.localizarStrXML(pacote, "<i>", "</i>")),
								status_war.get(vStatus), System.currentTimeMillis());
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
		for (LogZoneTime lzt : DatabaseStatic.logZoneTimes) {
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
