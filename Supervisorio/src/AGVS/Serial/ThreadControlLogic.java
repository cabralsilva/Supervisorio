package AGVS.Serial;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

import AGVS.Data.AGV;
import AGVS.Data.AlertaMesh;
import AGVS.Data.AlertasSupervisorio;
import AGVS.Data.ComandoMashSerial;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Cruzamento;
import AGVS.Data.MeshSerial;
import AGVS.Data.PortaMashSerial;
import AGVS.Data.Tag;
import AGVS.Data.TagAtraso;
import AGVS.Util.Log;
import WebService.http.Config;

public class ThreadControlLogic extends Thread {

	public ThreadControlLogic() {
		System.out.println("INIT ThreadControlLogic");
		this.start();
	}

	public void run() {
		try {
			System.out.println("Iniciando sistema");
			/*
			 * for (int i = 0; DatabaseStatic.mashs != null && i <
			 * DatabaseStatic.mashs.size(); i++) { MashSerial ms =
			 * DatabaseStatic.mashs.get(i);
			 * ConfigProcess.serial.enviar("<xml>load</xml>", ms.getMac16(),
			 * ms.getMac64()); Thread.sleep(7000); }
			 */
		} catch (Exception e1) {

		}
		System.out.println("Logica OK");
		while (true) {
			try {

				for (int i = 0; DatabaseStatic.cruzamentos != null && i < DatabaseStatic.cruzamentos.size(); i++) {
					DatabaseStatic.cruzamentos.get(i).liberaCruzamento();
				}

				for (int i = 0; DatabaseStatic.cancelas != null && i < DatabaseStatic.cancelas.size(); i++) {
					DatabaseStatic.cancelas.get(i).cancelaControle();
				}

				for (int i = 0; DatabaseStatic.cruzamentoMash != null
						&& i < DatabaseStatic.cruzamentoMash.size(); i++) {
					DatabaseStatic.cruzamentoMash.get(i).cancelaControle();
				}

				List<AGV> agvs = ConfigProcess.bd().selecAGVS();

				// Acionar alertas do supervisorio como pirulito de sinaliza��o.
				AlertasSupervisorio.executarAlertaSupervisorio(agvs);

				for (int i = 0; agvs != null && i < agvs.size(); i++) {
					AGV agv = agvs.get(i);
					boolean exist = false;
					for (int j = 0; DatabaseStatic.tagsAtraso != null && j < DatabaseStatic.tagsAtraso.size(); j++) {
						TagAtraso t = DatabaseStatic.tagsAtraso.get(j);

						if (agv.getTagAtual() != null && agv.getTagAtual().equals(t.getTag().getEpc())) {
							exist = true;
							if ((System.currentTimeMillis() - agv.getTagAtualTime()) > t.getTime()) {
								ConfigProcess.bd().updateAGV(agv.getId(), 1);
							} else {
								ConfigProcess.bd().updateAGV(agv.getId(), 0);
							}
						}
					}
					if (!exist) {
						ConfigProcess.bd().updateAGV(agv.getId(), 0);
					}
				}
				List<AlertaMesh> ams = new ArrayList<AlertaMesh>();
				
				for (int i = 0; DatabaseStatic.cms != null && i < DatabaseStatic.cms.size(); i++) {
					ComandoMashSerial aux = DatabaseStatic.cms.get(i);
					boolean executado = false;
					for (int j = 0; agvs != null && j < agvs.size(); j++) {
						AGV agv = agvs.get(j);
						if (!agv.getStatus().equals(AGV.statusManual) && agv.getTagAtual() != null) {
							List<Tag> tags = aux.getTags();
							for (int k = 0; tags != null && k < tags.size(); k++) {
								boolean enviar = true;
								if (aux.getTagPrioridade() == null
										|| !agv.getTagAtual().equals(aux.getTagPrioridade().getEpc())) {
									Tag tg = tags.get(k);

									if (agv.getTagAtual().equals(tg.getEpc())) {

										List<PortaMashSerial> pmss = aux.getTrigger();
										for (int l = 0; pmss != null && l < pmss.size(); l++) {

											PortaMashSerial pms = pmss.get(l);

											if (pms.getStatus() == null
													|| !pms.getStatus().equals(pms.getAcionamento())) {
												enviar = false;

											}
										}
										if (enviar != false && aux.getTagPrioridade() != null) {
											for (int r = 0; agvs != null && r < agvs.size(); r++) {
												AGV agvTest = agvs.get(r);
												if (agvTest.getTagAtual().equals(aux.getTagPrioridade().getEpc())) {
													enviar = false;
												}
											}
										}
									} else {
										enviar = false;
									}
								} else {

									if (agv.getTagAtual().equals(aux.getTagPrioridade().getEpc())) {
										k = tags.size();
										List<PortaMashSerial> pmss = aux.getTrigger();
										for (int l = 0; pmss != null && l < pmss.size(); l++) {
											PortaMashSerial pms = pmss.get(l);
											if (pms.getStatus() == null
													|| !pms.getStatus().equals(pms.getAcionamento())) {
												enviar = false;
											}
										}
									} else {
										enviar = false;
									}
								}

								if (enviar) {
									switch (aux.getComando()) {
									case ComandoMashSerial.liberar:

										if (System.currentTimeMillis() - aux.getTimeOld() > 3000) {
											if (aux.getTcm() != null) {
												aux.getTcm().cruzamentoEntrada(agv);
											}
											aux.setTimeOld(System.currentTimeMillis());
											Config config = Config.getInstance();

											if (config.getProperty(Config.PROP_PROJ)
													.equals(ConfigProcess.PROJ_GOODYEAR)) {
												AGV.enviarParar(agv.getMac16(), agv.getMac64());
											}

											AGV.enviarPlay(agv.getMac16(), agv.getMac64());
											AGV.enviarPlay(agv.getMac16(), agv.getMac64());
											AGV.enviarPlay(agv.getMac16(), agv.getMac64());
											System.out.println("Liberar AGV: " + agv.getNome());
											ConfigProcess.bd().insertFalhas(agv.getId(), "Mash Mandou Play",
													System.currentTimeMillis());

										}
										if (aux.getAm() != null) {
											aux.getAm().acionar();
											ams.add(aux.getAm());
										}

										break;
									case ComandoMashSerial.parar:

										if (System.currentTimeMillis() - aux.getTimeOld() > 3000) {
											if (aux.getTcm() != null) {
												aux.getTcm().cruzamentoEntrada(agv);
											}
											aux.setTimeOld(System.currentTimeMillis());
											AGV.enviarParar(agv.getMac16(), agv.getMac64());
											AGV.enviarParar(agv.getMac16(), agv.getMac64());
											AGV.enviarParar(agv.getMac16(), agv.getMac64());
											System.out.println("Parar AGV: " + agv.getNome());
											ConfigProcess.bd().insertFalhas(agv.getId(), "Mash Mandou Stop",
													System.currentTimeMillis());

										}
										if (aux.getAm() != null) {
											aux.getAm().acionar();
											ams.add(aux.getAm());
										}
										break;
									case ComandoMashSerial.emergencia:

										if (System.currentTimeMillis() - aux.getTimeOld() > 3000) {
											if (aux.getTcm() != null) {
												aux.getTcm().cruzamentoEntrada(agv);
											}
											aux.setTimeOld(System.currentTimeMillis());
											AGV.enviarEmergencia(agv.getMac16(), agv.getMac64());
											AGV.enviarEmergencia(agv.getMac16(), agv.getMac64());
											AGV.enviarEmergencia(agv.getMac16(), agv.getMac64());
											System.out.println("Emergencia AGV: " + agv.getNome());
											ConfigProcess.bd().insertFalhas(agv.getId(), "Mash Mandou Emergencia",
													System.currentTimeMillis());

										}
										if (aux.getAm() != null) {
											aux.getAm().acionar();
											ams.add(aux.getAm());
										}
										break;
									default:
										break;
									}
								}
							}

						}
					}
					if (!executado) {
						if (aux.getAm() != null) {
							boolean ok = true;
							for (int j = 0; ams != null && j < ams.size(); j++) {
								if (ams.get(i).getNome().equals(aux.getAm().getNome()))
									ok = false;
							}
							if (ok)
								aux.getAm().desacionar();
						}
					}
				}
				Thread.sleep(100);
			} catch (Exception e) {
				new Log(e);
			}

		}
	}
}
