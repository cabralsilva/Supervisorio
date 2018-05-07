package AGVS.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.omg.CORBA.Environment;

import AGVS.Util.Log;
import WebService.http.Config;

public class Semaforo {
	private int id;
	private MeshSerial ms;
	private String nome;
	private List<TagSemaforos> tagsSinalVerde;
	private List<TagSemaforos> tagsSinalVermelho;
	private List<TagSemaforos> tagsSinalAmarelo;
	private List<TagSemaforos> tagsSinalPiscaVerde;
	private List<TagSemaforos> tagsSinalPiscaVermelho;
	private List<TagSemaforos> tagsSinalPiscaAmarelo;
	private List<AGV> agvsRodando;
	private Semaphore sp;
	private String status;

	public final static String INDEFINIDO = "Status Indefinido";
	public final static String DESLIGADO = "Desligado";
	public final static String VERDE = "Verde";
	public final static String VERMELHO = "Vermelho";
	public final static String AMARELO = "Amarelo";
	public final static String PISCA_VERDE = "Piscando Verde";
	public final static String PISCA_VERMELHO = "Piscando Vermelho";
	public final static String PISCA_AMARELO = "Piscando Amarelo";

	public final static String[] comandosSemafaro = { DESLIGADO, VERDE, VERMELHO, AMARELO, PISCA_VERDE, PISCA_AMARELO,
			PISCA_VERMELHO };

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MeshSerial getMs() {
		return ms;
	}

	public void setMs(MeshSerial ms) {
		this.ms = ms;
	}

	public String getStatus() {
		return status;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<TagSemaforos> getTagsSinalVerde() {
		return tagsSinalVerde;
	}

	public void setTagsSinalVerde(List<TagSemaforos> tagsSinalVerde) {
		this.tagsSinalVerde = tagsSinalVerde;
	}

	public List<TagSemaforos> getTagsSinalVermelho() {
		return tagsSinalVermelho;
	}

	public void setTagsSinalVermelho(List<TagSemaforos> tagsSinalVermelho) {
		this.tagsSinalVermelho = tagsSinalVermelho;
	}

	public List<TagSemaforos> getTagsSinalAmarelo() {
		return tagsSinalAmarelo;
	}

	public void setTagsSinalAmarelo(List<TagSemaforos> tagsSinalAmarelo) {
		this.tagsSinalAmarelo = tagsSinalAmarelo;
	}

	public List<TagSemaforos> getTagsSinalPiscaVerde() {
		return tagsSinalPiscaVerde;
	}

	public void setTagsSinalPiscaVerde(List<TagSemaforos> tagsSinalPiscaVerde) {
		this.tagsSinalPiscaVerde = tagsSinalPiscaVerde;
	}

	public List<TagSemaforos> getTagsSinalPiscaVermelho() {
		return tagsSinalPiscaVermelho;
	}

	public void setTagsSinalPiscaVermelho(List<TagSemaforos> tagsSinalPiscaVermelho) {
		this.tagsSinalPiscaVermelho = tagsSinalPiscaVermelho;
	}

	public List<TagSemaforos> getTagsSinalPiscaAmarelo() {
		return tagsSinalPiscaAmarelo;
	}

	public void setTagsSinalPiscaAmarelo(List<TagSemaforos> tagsSinalPiscaAmarelo) {
		this.tagsSinalPiscaAmarelo = tagsSinalPiscaAmarelo;
	}

	public void sinalVerde() {
		ConfigProcess.serial.enviar("<xml>verde</xml>", ms.getIp(), ms.getMac64());
		this.status = VERDE;
	}

	public void sinalVermelho() {
		ConfigProcess.serial.enviar("<xml>vermelho</xml>", ms.getIp(), ms.getMac64());
		this.status = VERMELHO;
	}

	public void sinalAmarelo() {
		ConfigProcess.serial.enviar("<xml>amarelo</xml>", ms.getIp(), ms.getMac64());
		this.status = AMARELO;
	}

	public void sinalDesligar() {
		ConfigProcess.serial.enviar("<xml>desligar</xml>", ms.getIp(), ms.getMac64());
		this.status = DESLIGADO;
	}

	public void sinalAlerta() {
		ConfigProcess.serial.enviar("<xml>alerta</xml>", ms.getIp(), ms.getMac64());
		this.status = PISCA_AMARELO;
	}

	public void sinalPiscaVermelho() {
		ConfigProcess.serial.enviar("<xml>alertaVerm</xml>", ms.getIp(), ms.getMac64());
		this.status = PISCA_VERMELHO;
	}

	public void sinalPiscaVerde() {
		ConfigProcess.serial.enviar("<xml>alertaVerd</xml>", ms.getIp(), ms.getMac64());
		this.status = PISCA_VERDE;
	}
	
	public void clear() {
		try {
			sp.acquire();
			agvsRodando.clear();
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	private boolean removerAGV(AGV agv) {
		Config config = Config.getInstance();
		if (config.getProperty(Config.PROP_SEMAFARO_TYPE).equals("V2")) {
			try {

				sp.acquire();

				for (int i = 0; agvsRodando != null && i < agvsRodando.size(); i++) {
					AGV a = agvsRodando.get(i);
					if (a.getId() == agv.getId()) {
						agvsRodando.remove(i);
						i--;
					}
				}

			} catch (Exception e) {
				new Log(e);
			}

			if (agvsRodando != null && agvsRodando.size() == 0) {
				sp.release();
				return true;
			}
			sp.release();
			return false;
		} else {
			return true;
		}
	}

	private boolean addAGV(AGV agv) {
		boolean ret = true;
		Config config = Config.getInstance();
		if (config.getProperty(Config.PROP_SEMAFARO_TYPE).equals("V2")) {
			try {
				sp.acquire();

				if (agvsRodando != null && agvsRodando.size() > 0) {
					ret = false;
				}

				agvsRodando.add(agv);

			} catch (Exception e) {
				new Log(e);
			}

			sp.release();
		}
		return ret;
	}

	public void verificaStateSinal(String epc, AGV agv) {

		for (int i = 0; tagsSinalVerde != null && i < tagsSinalVerde.size(); i++) {
			TagSemaforos tagS = tagsSinalVerde.get(i);
			if (tagS.getTag().getEpc().equals(epc)) {
				if (removerAGV(agv)) {
					sinalVerde();
					sinalVerde();
				}
			}
		}
		for (int i = 0; tagsSinalVermelho != null && i < tagsSinalVermelho.size(); i++) {
			TagSemaforos tagS = tagsSinalVermelho.get(i);
			if (tagS.getTag().getEpc().equals(epc)) {
				if (addAGV(agv)) {
					sinalVermelho();
					sinalVermelho();
				}
			}
		}
		for (int i = 0; tagsSinalAmarelo != null && i < tagsSinalAmarelo.size(); i++) {
			TagSemaforos tagS = tagsSinalAmarelo.get(i);
			if (tagS.getTag().getEpc().equals(epc)) {
				if (addAGV(agv)) {
					sinalAmarelo();
					sinalAmarelo();
				}
			}
		}

		for (int i = 0; tagsSinalPiscaVermelho != null && i < tagsSinalPiscaVermelho.size(); i++) {
			TagSemaforos tagS = tagsSinalPiscaVermelho.get(i);
			if (tagS.getTag().getEpc().equals(epc)) {
				sinalPiscaVermelho();
				sinalPiscaVermelho();
			}
		}
		for (int i = 0; tagsSinalPiscaVerde != null && i < tagsSinalPiscaVerde.size(); i++) {
			TagSemaforos tagS = tagsSinalPiscaVerde.get(i);
			if (tagS.getTag().getEpc().equals(epc)) {
				sinalPiscaVerde();
				sinalPiscaVerde();
			}
		}
		for (int i = 0; tagsSinalPiscaAmarelo != null && i < tagsSinalPiscaAmarelo.size(); i++) {
			TagSemaforos tagS = tagsSinalPiscaAmarelo.get(i);
			if (tagS.getTag().getEpc().equals(epc)) {
				sinalAlerta();
				sinalAlerta();
			}
		}
	}

	public Semaforo(int id, MeshSerial ms, String nome, List<TagSemaforos> tagsSinalVerde,
			List<TagSemaforos> tagsSinalVermelho, List<TagSemaforos> tagsSinalAmarelo,
			List<TagSemaforos> tagsSinalPiscaAmarelo, List<TagSemaforos> tagsSinalPiscaVerde,
			List<TagSemaforos> tagsSinalPiscaVermelho) {
		super();
		this.id = id;
		this.ms = ms;
		this.nome = nome;
		this.tagsSinalVerde = tagsSinalVerde;
		this.tagsSinalVermelho = tagsSinalVermelho;
		this.tagsSinalAmarelo = tagsSinalAmarelo;
		this.tagsSinalPiscaAmarelo = tagsSinalAmarelo;
		this.tagsSinalPiscaVermelho = tagsSinalPiscaVermelho;
		this.tagsSinalPiscaVerde = tagsSinalPiscaVerde;
		this.status = INDEFINIDO;
		agvsRodando = new ArrayList<AGV>();
		sp = new Semaphore(1);
	}

}
