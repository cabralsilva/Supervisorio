package AGVS.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import AGVS.Util.Log;
import WebService.http.Config;

public class Cancelas {
	private String nome;
	private boolean bloqC1;
	private List<AGV> agvInRota;
	private List<AGV> filaEspera;
	private List<TagCruzamento> tagsEntrada;
	private List<TagCruzamento> tagsSaida;
	private Semaphore sp = new Semaphore(1);
	private PortaMashSerial c1;
	private PortaMashSerial c2;
	private int sinalACC1;
	private int sinalACC2;
	private int statusC1;
	private int statusC2;
	private long timeSend = 0;
	private boolean ativado = true;

	public static final int SINALHIGH = 1;
	public static final int SINALLOW = 0;
	public static final int ABERTO = 100;
	public static final int FECHADO = 101;
	public static final int NULL = 255;

	public String getNome() {
		return nome;
	}

	public boolean isBloqC1() {
		return bloqC1;
	}

	public void setBloqC1(boolean bloqC1) {
		this.bloqC1 = bloqC1;
	}

	public List<AGV> getAgvInRota() {
		return agvInRota;
	}

	public void setAgvInRota(List<AGV> agvInRota) {
		this.agvInRota = agvInRota;
	}

	public List<AGV> getFilaEspera() {
		return filaEspera;
	}

	public void setFilaEspera(List<AGV> filaEspera) {
		this.filaEspera = filaEspera;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getStatusC1() {
		return statusC1;
	}

	public void setStatusC1(int statusC1) {
		this.statusC1 = statusC1;
	}

	public int getStatusC2() {
		return statusC2;
	}

	public void setStatusC2(int statusC2) {
		this.statusC2 = statusC2;
	}

	public int getSinalACC1() {
		return sinalACC1;
	}

	public void setSinalACC1(int sinalACC1) {
		this.sinalACC1 = sinalACC1;
	}

	public int getSinalACC2() {
		return sinalACC2;
	}

	public void setSinalACC2(int sinalACC2) {
		this.sinalACC2 = sinalACC2;
	}

	public PortaMashSerial getC1() {
		return c1;
	}

	public void setC1(PortaMashSerial c1) {
		this.c1 = c1;
	}

	public PortaMashSerial getC2() {
		return c2;
	}

	public void setC2(PortaMashSerial c2) {
		this.c2 = c2;
	}

	public List<TagCruzamento> getTagsEntrada() {
		return tagsEntrada;
	}

	public void setTagsEntrada(List<TagCruzamento> tagsEntrada) {
		this.tagsEntrada = tagsEntrada;
	}

	public List<TagCruzamento> getTagsSaida() {
		return tagsSaida;
	}

	public void setTagsSaida(List<TagCruzamento> tagsSaida) {
		this.tagsSaida = tagsSaida;
	}

	public Cancelas(String nome, List<TagCruzamento> tagsEntrada, List<TagCruzamento> tagsSaida, PortaMashSerial c1,
			PortaMashSerial c2, int sinalACC1, int sinalACC2) {
		super();
		this.ativado = false;
		this.nome = nome;
		this.bloqC1 = false;
		this.agvInRota = new ArrayList<AGV>();
		this.filaEspera = new ArrayList<AGV>();
		this.tagsEntrada = tagsEntrada;
		this.tagsSaida = tagsSaida;
		this.c1 = c1;
		this.c2 = c2;
		this.sinalACC1 = sinalACC1;
		this.sinalACC2 = sinalACC2;
		this.statusC1 = NULL;
		this.statusC1 = NULL;

	}

	public boolean isAtivado() {
		return ativado;
	}

	public void setAtivado(boolean ativado) {
		this.ativado = ativado;

	}

	public void liberar() {
		try {
			sp.acquire();
			agvInRota.clear();
			filaEspera.clear();
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public void ativar() {
		ativado = true;
		liberar();
	}

	public void desativar() {
		ativado = false;
		liberar();
	}

	public void cruzamentoCancela(AGV a) {
		if (ativado) {
			try {

				sp.acquire();
				for (int i = 0; agvInRota != null && i < agvInRota.size(); i++) {
					AGV agv = agvInRota.get(i);
					if (a != null && a.getId() == agv.getId() && a.getStatus().equals(AGV.statusManual)) {
						System.out.println("AGV: " + a.getId() + " Passou para manual");
						agvInRota.remove(i);
						System.out.println("Liberou do Cruzamento AGV: " + agv.getNome());
					}
				}
			} catch (Exception e) {
				new Log(e);
			}
			sp.release();

			if (a.getStatus().equals(AGV.statusManual)) {
				try {
					sp.acquire();
					if (filaEspera != null && filaEspera.size() > 0) {
						for (int i = 0; i < filaEspera.size(); i++) {
							AGV agv = filaEspera.get(i);
							if (agv.getId() == a.getId()) {
								filaEspera.remove(i);
								i--;
							}
						}
					}
				} catch (Exception e) {
					new Log(e);
				}
				sp.release();
			}
		}
	}

	public void cruzamentoCancela(String epc, AGV agv) {
		if (ativado) {
			try {
				sp.acquire();

				for (int i = 0; tagsEntrada != null && i < tagsEntrada.size(); i++) {
					Tag tg = tagsEntrada.get(i).getTag();
					if (tg.getEpc().equals(epc)) {

						if (bloqC1) {
							boolean filaEsperaOK = true;
							for (int j = 0; filaEspera != null && j < filaEspera.size(); j++) {
								AGV a = filaEspera.get(j);
								if (a.getId() == agv.getId()) {
									filaEsperaOK = false;
								}
							}
							if (filaEsperaOK) {
								filaEspera.add(agv);
								AGV.enviarParar(agv.getMac16(), agv.getMac64());
								System.out.println("Bloqueio do Cruzamento AGV: " + agv.getNome());
							} else {
								System.out.println("Ja esta bloqueado AGV: " + agv.getNome());
							}
						} else {
							boolean agvInRotaOK = true;
							for (int j = 0; agvInRota != null && j < agvInRota.size(); j++) {
								AGV a = agvInRota.get(j);
								if (a.getId() == agv.getId()) {
									agvInRotaOK = false;
								}

							}
							if (agvInRotaOK) {
								System.out.println("Entrou no Cruzamento AGV: " + agv.getNome());
								agvInRota.add(agv);
							} else {
								System.out.println("Ja esta no Cruzamento AGV: " + agv.getNome());
							}

						}
						i = tagsEntrada.size();
					}

				}
				for (int i = 0; tagsSaida != null && i < tagsSaida.size(); i++) {
					Tag tg = tagsSaida.get(i).getTag();
					if (tg.getEpc().equals(epc)) {
						int agvInRotaOK = -1;
						for (int j = 0; agvInRota != null && j < agvInRota.size(); j++) {
							AGV a = agvInRota.get(j);
							if (a.getId() == agv.getId()) {
								agvInRotaOK = j;
								j = agvInRota.size();
							}

						}
						if (agvInRotaOK > -1) {
							System.out.println("Saiu do Cruzamento AGV: " + agv.getNome());
							agvInRota.remove(agvInRotaOK);
						} else {
							System.out.println("Ja estava Liberado AGV: " + agv.getNome());
						}
						i = tagsSaida.size();
					}

				}
			} catch (Exception e) {
				new Log(e);
			}
			sp.release();
		}
	}

	private long timeC1Old = 0;
	private long timeC1Open = 0;
	private int replyOpen = 0;
	private int replyClosed = 0;

	public void cancelaControle() {

		try {
			if (ativado) {
				sp.acquire();
				// System.out.println("cancela lg");
				if (bloqC1 == false) {
					if (filaEspera != null && filaEspera.size() > 0) {
						AGV agv = filaEspera.get(0);
						Config config = Config.getInstance();

						if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
							AGV.enviarParar(agv.getMac16(), agv.getMac64());
						}

						AGV.enviarPlay(agv.getMac16(), agv.getMac64());
						filaEspera.remove(0);
						System.out.println("Liberou do Cruzamento AGV: " + agv.getNome());
						boolean agvInRotaOK = true;
						for (int j = 0; agvInRota != null && j < agvInRota.size(); j++) {
							AGV a = agvInRota.get(j);
							if (a.getId() == agv.getId()) {
								agvInRotaOK = false;
							}

						}
						if (agvInRotaOK) {
							System.out.println("Entrou no Cruzamento AGV: " + agv.getNome());
							agvInRota.add(agv);
						} else {
							System.out.println("Ja esta no Cruzamento AGV: " + agv.getNome());
						}
					}
				}
				// System.out.println("c1" + c1.getStatus());
				// System.out.println("c2" + c2.getStatus());
				if ((agvInRota == null || agvInRota.size() <= 0)
						&& ((c1.getStatus() != null && c1.getStatus().equals(c1.getAcionamento()))
								|| (c2.getStatus() != null && c2.getStatus().equals(c2.getAcionamento())))) {

					if (timeC1Old != 0) {
						if ((System.currentTimeMillis() - timeC1Old) >= 1000 || timeC1Open > 0) {
							replyClosed = 0;
							timeC1Open = System.currentTimeMillis();
							// Abrir Cancela
							if ((timeSend == 0 || (System.currentTimeMillis() - timeSend) > 4000)) {

								if (statusC1 != ABERTO) {
									System.out.println(c1.getMs().getMac16());
									System.out.println(c1.getMs().getMac64());
									ConfigProcess.serial.enviar("<xml>" + sinalACC1 + "</xml>", c1.getMs().getMac16(),
											c1.getMs().getMac64());
								}
								if (statusC2 != ABERTO) {
									ConfigProcess.serial.enviar("<xml>" + sinalACC2 + "</xml>", c2.getMs().getMac16(),
											c2.getMs().getMac64());
								}
								timeSend = System.currentTimeMillis();
							}
							bloqC1 = true;
						}
					} else {
						replyOpen = 0;
						timeC1Old = System.currentTimeMillis();
					}
				} else {
					timeC1Old = 0;
				}

				// System.out.println("timeC1Open: " + timeC1Open);
				if (timeC1Open > 0) {
					if (System.currentTimeMillis() - timeC1Open >= 7000) {

						if ((timeSend == 0 || (System.currentTimeMillis() - timeSend) > 4000)
								&& (statusC1 == ABERTO || statusC2 == ABERTO)) {
							System.out.println("FECHAR");
							int sinalACC1aux;
							int sinalACC2aux;
							if (sinalACC1 == 1) {
								sinalACC1aux = 0;
							} else {
								sinalACC1aux = 1;
							}
							if (sinalACC2 == 1) {
								sinalACC2aux = 0;
							} else {
								sinalACC2aux = 1;
							}
							if (bloqC1 && statusC1 == ABERTO) {
								ConfigProcess.serial.enviar("<xml>" + sinalACC1aux + "</xml>", c1.getMs().getMac16(),
										c1.getMs().getMac64());
							}
							if (bloqC1 && statusC2 == ABERTO) {
								ConfigProcess.serial.enviar("<xml>" + sinalACC2aux + "</xml>", c2.getMs().getMac16(),
										c2.getMs().getMac64());
							}
							timeSend = System.currentTimeMillis();
						} else {
							timeC1Open = 0;
							bloqC1 = false;
						}

					}
				}
			} else {
				if (((System.currentTimeMillis() - timeSend) > 4000) && (statusC1 == FECHADO || statusC2 == FECHADO)) {
					System.out.println("ABRIR");

					if (statusC1 == FECHADO) {
						ConfigProcess.serial.enviar("<xml>" + sinalACC1 + "</xml>", c1.getMs().getMac16(),
								c1.getMs().getMac64());
					}
					if (statusC2 == FECHADO) {
						ConfigProcess.serial.enviar("<xml>" + sinalACC2 + "</xml>", c2.getMs().getMac16(),
								c2.getMs().getMac64());
					}
					timeSend = System.currentTimeMillis();

				}
			}
		} catch (Exception e) {

			new Log(e);
		}

		sp.release();

	}

}
