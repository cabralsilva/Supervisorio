package AGVS.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import AGVS.Util.Log;
import WebService.http.Config;

public class CruzamentoMash {
	private String nome;
	private boolean bloqC1;
	private List<AGV> agvInRota;
	private List<AGV> filaEspera;
	private List<TagCruzamento> tagsEntrada;
	private List<TagCruzamento> tagsSaida;
	private Semaphore sp = new Semaphore(1);
	private List<PortaMashSerial> pms;
	private int oldState;
	private Semaforo sf;
	private Semaforo sf1;
	private Semaforo sf2;

	public final static int stateNULL = 2;
	public final static int stateAC = 1;
	public final static int stateDAC = 0;

	public boolean isBloqC1() {
		return bloqC1;
	}

	public void setBloqC1(boolean bloqC1) {
		this.bloqC1 = bloqC1;
	}

	public String getNome() {
		return nome;
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

	public List<PortaMashSerial> getPms() {
		return pms;
	}

	public void setPms(List<PortaMashSerial> pms) {
		this.pms = pms;
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

	public CruzamentoMash(String nome, List<TagCruzamento> tagsEntrada, List<TagCruzamento> tagsSaida,
			List<PortaMashSerial> pms, Semaforo sf, Semaforo sf1, Semaforo sf2) {
		super();
		this.nome = nome;
		this.bloqC1 = false;
		this.agvInRota = new ArrayList<AGV>();
		this.filaEspera = new ArrayList<AGV>();
		this.tagsEntrada = tagsEntrada;
		this.tagsSaida = tagsSaida;
		this.pms = pms;
		this.sf = sf;
		this.sf1 = sf1;
		this.sf2 = sf2;

		this.oldState = stateNULL;

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

	public void cruzamentoCancela(AGV a) {
		try {
			sp.acquire();
			for (int i = 0; agvInRota != null && i < agvInRota.size(); i++) {
				AGV agv = agvInRota.get(i);
				if (a != null && a.getId() == agv.getId() && a.getStatus().equals(AGV.statusManual)) {
					System.out.println("AGV: " + a.getId() + " Passou para manual");
					agvInRota.remove(i);
					i--;
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

	public void cruzamentoCancela(String epc, AGV agv) {
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
							AGV.enviarParar(agv.getMac16(), agv.getMac64(), agv.getIp());
							
							AGV.enviarParar(agv.getMac16(), agv.getMac64(), agv.getIp());
							AGV.enviarParar(agv.getMac16(), agv.getMac64(), agv.getIp());
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
				}

			}
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	private long timeC1Open = 0;

	public void cancelaControle() {
		try {
			sp.acquire();

			if (bloqC1 == false) {
				if (filaEspera != null && filaEspera.size() > 0) {
					AGV agv = filaEspera.get(0);
					Config config = Config.getInstance();

					if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
						AGV.enviarParar(agv.getMac16(), agv.getMac64(), agv.getIp());
					}

					AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
					AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
					AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
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

			for (int i = 0; pms != null && i < pms.size(); i++) {
				PortaMashSerial c1 = pms.get(i);

				if ((agvInRota == null || agvInRota.size() <= 0)
						&& ((c1.getStatus() != null && c1.getStatus().equals(c1.getAcionamento())))) {

					System.out.println("Detct: " + c1.getNome());
					System.out.println("Detct2: " + c1.getNome());
					System.out.println(oldState);

					System.out.println("Piscar");
					sf.sinalVerde();
					sf.sinalVerde();
					sf1.sinalVerde();
					sf1.sinalVerde();
					sf2.sinalVermelho();
					sf2.sinalVermelho();
					oldState = stateAC;

					c1.setStatus(PortaMashSerial.E1_OFF);
					System.out.println("Bloqueio Cruamento mash");
					timeC1Open = System.currentTimeMillis();
					bloqC1 = true;

				}
			}

			if (timeC1Open > 0) {
				if (System.currentTimeMillis() - timeC1Open >= 120000) {
					timeC1Open = 0;

					sf.sinalAlerta();
					sf.sinalAlerta();
					sf1.sinalAlerta();
					sf1.sinalAlerta();
					sf2.sinalVerde();
					sf2.sinalVerde();
					oldState = stateDAC;

					System.out.println("Libera Cruamento mash");
					bloqC1 = false;
				}

			}

		} catch (Exception e) {

			new Log(e);
		}

		sp.release();
	}

}
