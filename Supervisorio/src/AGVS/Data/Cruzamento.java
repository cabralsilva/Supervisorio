package AGVS.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.print.attribute.standard.Fidelity;

import AGVS.Serial.DatabaseStatic;
import AGVS.Util.Log;
import WebService.http.Config;

public class Cruzamento {

	private String nome;
	private String descricao;
	private AGV agvInRota;
	private List<AGV> filaEspera;
	private List<TagCruzamento> tagsEntrada;
	private List<TagCruzamento> tagsSaida;
	private List<PortaMashSerial> pms;
	private Semaphore sp = new Semaphore(1);

	public void verificaCruzamento(AGV agv, String epc) {

		if (agv == null || epc == null) {
			return;
		}
		try {
			sp.acquire();
			for (int i = 0; tagsEntrada != null && i < tagsEntrada.size(); i++) {
				Tag tg = tagsEntrada.get(i).getTag();

				if (tg.getEpc().equals(epc) && !agv.getStatus().equals(AGV.statusManual)) {

					boolean ok = false;
					if (getPms() != null) {
						for (int j = 0; j < getPms().size(); j++) {
							PortaMashSerial aux = getPms().get(j);
							if (aux.getStatus() != null && aux.getStatus().equals(aux.getAcionamento())) {
								ok = true;
							}
						}
					}
					if (getAgvInRota() != null || ok) {
						if (ok || getAgvInRota().getId() != agv.getId()) {
							boolean bloq = true;
							if (!ok) {
								for (int j = 0; filaEspera != null && j < filaEspera.size(); j++) {
									if (filaEspera.get(j).getId() == agv.getId()) {
										bloq = false;
									}
								}
							}
							if (bloq) {
								System.out.println("Entrou na fila o AGV: " + agv.getNome());
								filaEspera.add(agv);
							}
//							AGV.enviarParar(agv.getMac16(), agv.getMac64());
//							AGV.enviarParar(agv.getMac16(), agv.getMac64());
//							AGV.enviarParar(agv.getMac16(), agv.getMac64());
							AGV.enviarEmCruzamento(agv.getMac16(), agv.getMac64());
//							AGV.enviarEmCruzamento(agv.getMac16(), agv.getMac64());
//							AGV.enviarEmCruzamento(agv.getMac16(), agv.getMac64());
							ConfigProcess.bd().insertFalhas(agv.getId(), "Cruzamento " + getNome() + " Mandou Stop",
									System.currentTimeMillis());
							System.out.println("Bloqueado no Cruzamento " + nome + " AGV: " + agv.getNome());

						}
					} else {
						setAgvInRota(agv);
						System.out.println("Entrou no Cruzamento AGV: " + agv.getNome());
					}
				}
			}
			for (int i = 0; tagsSaida != null && i < tagsSaida.size(); i++) {
				Tag tg = tagsSaida.get(i).getTag();
				if (tg.getEpc().equals(epc)) {
					if (getAgvInRota() != null && agv.getId() == getAgvInRota().getId()) {
						agvInRota = null;
						System.out.println("Saiu do Cruzamento AGV: " + agv.getNome());
						sp.release();
						liberaCruzamento();
						return;

					}

					for (int j = 0; j < filaEspera.size(); j++) {
						if (filaEspera.get(j).getId() == agv.getId()) {
							filaEspera.remove(j);
							j--;
						}
					}
				}

			}
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();

	}

	public void execLiberaVerificaAGV(AGV a) {

		if (a != null && getAgvInRota() != null && a.getId() == getAgvInRota().getId()
				&& a.getStatus().equals(AGV.statusManual)) {

			try {
				Thread.sleep(5000);
				System.out.println("AGV: " + a.getId() + " Passou para manual");
				sp.acquire();
				setAgvInRota(null);
				if (getFilaEspera() != null && getFilaEspera().size() > 0) {
					AGV agv = getFilaEspera().get(0);
					setAgvInRota(agv);
					Config config = Config.getInstance();

					if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
						AGV.enviarParar(agv.getMac16(), agv.getMac64());
					}

					AGV.enviarPlay(agv.getMac16(), agv.getMac64());
					AGV.enviarPlay(agv.getMac16(), agv.getMac64());
					AGV.enviarPlay(agv.getMac16(), agv.getMac64());
					getFilaEspera().remove(0);
					ConfigProcess.bd().insertFalhas(agv.getId(), "Cruzamento " + getNome() + " Mandou Play",
							System.currentTimeMillis());
					System.out.println("Liberou do Cruzamento AGV: " + agv.getNome());
				}
			} catch (Exception e) {
				new Log(e);
			}
			sp.release();

		}
		if (a.getStatus().equals(AGV.statusManual)) {
			try {
				sp.acquire();
				if (getFilaEspera() != null && getFilaEspera().size() > 0) {
					for (int i = 0; i < getFilaEspera().size(); i++) {
						AGV agv = getFilaEspera().get(i);
						if (agv.getId() == a.getId()) {
							getFilaEspera().remove(i);
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

	public void execLiberaCruzamento() {
		System.out.println("Comando Liberar Cruzamento");
		if (getFilaEspera() != null && getFilaEspera().size() > 0) {
			try {
				sp.acquire();

				AGV agv = getFilaEspera().get(0);
				setAgvInRota(agv);

				Config config = Config.getInstance();

				if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
					AGV.enviarParar(agv.getMac16(), agv.getMac64());
				}

				AGV.enviarPlay(agv.getMac16(), agv.getMac64());
				AGV.enviarPlay(agv.getMac16(), agv.getMac64());
				AGV.enviarPlay(agv.getMac16(), agv.getMac64());
				getFilaEspera().remove(0);
				System.out.println("Liberou do Cruzamento AGV: " + agv.getNome());
				ConfigProcess.bd().insertFalhas(agv.getId(), "Cruzamento " + getNome() + " Mandou Stop",
						System.currentTimeMillis());

			} catch (Exception e) {
				new Log(e);
			}
		} else {
			agvInRota = null;
		}
		sp.release();

	}

	public void limparTudo() {
		try {
			sp.acquire();
			filaEspera.clear();
			agvInRota = null;
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public void liberaCruzamento() {
		try {
			sp.acquire();
			if (getFilaEspera() != null && getFilaEspera().size() > 0) {
				boolean ok = true;
				if (getPms() != null) {
					for (int i = 0; i < getPms().size(); i++) {
						System.out.println("Teste Mash Libera Cruzamento");
						PortaMashSerial aux = getPms().get(i);
						if (aux.getStatus() != null && aux.getStatus().equals(aux.getAcionamento())) {
							System.out.println("Cruzamento bloq por mash");
							ok = false;
						}
					}
				}

				if (getAgvInRota() == null && ok) {
					AGV agv = getFilaEspera().get(0);
					setAgvInRota(agv);
					Config config = Config.getInstance();

					if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
						AGV.enviarParar(agv.getMac16(), agv.getMac64());
					}

					AGV.enviarPlay(agv.getMac16(), agv.getMac64());
					AGV.enviarPlay(agv.getMac16(), agv.getMac64());
					AGV.enviarPlay(agv.getMac16(), agv.getMac64());
					getFilaEspera().remove(0);
					ConfigProcess.bd().insertFalhas(agv.getId(), "Cruzamento " + getNome() + " Mandou PLay",
							System.currentTimeMillis());
					System.out.println("Liberou do Cruzamento AGV: " + agv.getNome());
				}
			}
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public List<PortaMashSerial> getPms() {
		return pms;
	}

	public void setPms(List<PortaMashSerial> pms) {
		this.pms = pms;
	}

	public Semaphore getSp() {
		return sp;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public AGV getAgvInRota() {
		return agvInRota;
	}

	public void setAgvInRota(AGV agvInRota) {
		this.agvInRota = agvInRota;
	}

	public List<AGV> getFilaEspera() {
		return filaEspera;
	}

	public void setFilaEspera(List<AGV> filaEspera) {
		this.filaEspera = filaEspera;
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

	public Cruzamento(String nome, String descricao, List<TagCruzamento> tagsEntrada, List<TagCruzamento> tagsSaida,
			List<PortaMashSerial> pms) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.agvInRota = null;
		this.filaEspera = new ArrayList<AGV>();
		this.tagsEntrada = tagsEntrada;
		this.tagsSaida = tagsSaida;
		this.pms = pms;
	}

}
