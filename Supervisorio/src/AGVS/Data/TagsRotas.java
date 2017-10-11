package AGVS.Data;

import java.util.HashMap;
import java.util.Map;

public class TagsRotas {
	private String nome;
	private int posicao;
	private Rota rota;
	private Tag tag;
	private int setPoint;
	private int velocidade;
	private int temporizador;
	private String girar;
	private String estadoAtuador;
	private String sensorObstaculo;
	private String sinalSonoro;
	private int tagDestino;
	private int tagParada;
	private int pitStop;

	public final static int sp1000 = 1000;
	public final static int sp1500 = 1500;
	public final static int sp2000 = 2000;
	public final static int sp2500 = 2500;
	public final static int sp3000 = 3000;
	public final static int sp3500 = 3500;
	public final static int sp4000 = 4000;
	public final static int sp4500 = 4500;
	public final static int sp5000 = 5000;
	public final static int sp5500 = 5500;
	public final static int sp6000 = 6000;
	public final static int sp6500 = 6500;
	public final static int sp7000 = 7000;
	public final static int sp7500 = 7500;
	public final static int sp8000 = 8000;
	public final static int setPoints[] = { sp1000, sp1500, sp2000, sp2500, sp3000, sp3500, sp4000, sp4500, sp5000,
			sp5500, sp6000, sp6500, sp7000, sp7500, sp8000 };

	public final static int velPorc10 = 10;
	public final static int velPorc20 = 20;
	public final static int velPorc30 = 30;
	public final static int velPorc40 = 40;
	public final static int velPorc50 = 50;
	public final static int velPorc60 = 60;
	public final static int velPorc70 = 70;
	public final static int velPorc80 = 80;
	public final static int velPorc90 = 90;
	public final static int velPorc100 = 100;
	public final static int velocidades[] = { velPorc10, velPorc20, velPorc30, velPorc40, velPorc50, velPorc60,
			velPorc70, velPorc80, velPorc90, velPorc100 };

	public final static String girarDesativado = "Desativado";
	public final static String girarEsquerda = "Esquerda";
	public final static String girarDireita = "Direita";
	public final static String setGirar[] = { girarDesativado, girarEsquerda, girarDireita };

	public final static String sensObsLigado = "Ligado";
	public final static String sensObsDesligado = "Desligado";
	public final static String sensObs[] = { sensObsLigado, sensObsDesligado };

	public final static String sinalSonoroDesligado = "Ligado";
	public final static String sinalSonoroLigado = "Desligado";
	public final static String sinalSonoros[] = { sinalSonoroDesligado, sinalSonoroLigado };

	public final static String tagDestinoAtivado = "Ativado";
	public final static String tagDestinoDesativado = "Desativado";

	public final static String estAtuadorAtuado = "Atuado";
	public final static String estAtuadorRecuado = "Recuado";
	public final static String estAtuadorInverter = "Inverter";
	public final static String estAtuadorManter = "Manter";
	public final static String estAtuador[] = { estAtuadorAtuado, estAtuadorRecuado, estAtuadorInverter,
			estAtuadorManter };

	public final static Map<String, Integer> listTagDestino() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		list.put(tagDestinoDesativado, 0);
		list.put(tagDestinoAtivado, 1);
		
		return list;
	}

	public final static String tagParadaAtivado = "Ativado";
	public final static String tagParadaDesativado = "Desativado";

	public final static Map<String, Integer> listTagParada() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		list.put(tagParadaDesativado, 0);
		list.put(tagParadaAtivado, 1);
		
		return list;
	}
	
	public final static String tagPitStopAtivado = "Ativado";
	public final static String tagPitStopDesativado = "Desativado";

	public final static Map<String, Integer> listTagPitStop() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		list.put(tagPitStopDesativado, 0);
		list.put(tagPitStopAtivado, 1);
		
		return list;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public Tag getTag() {
		return tag;
	}

	public int getPitStop() {
		return pitStop;
	}

	public void setPitStop(int pitStop) {
		this.pitStop = pitStop;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public int getSetPoint() {
		return setPoint;
	}

	public void setSetPoint(int setPoint) {
		this.setPoint = setPoint;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public int getTemporizador() {
		return temporizador;
	}

	public void setTemporizador(int temporizador) {
		this.temporizador = temporizador;
	}

	public String getGirar() {
		return girar;
	}

	public void setGirar(String girar) {
		this.girar = girar;
	}

	public String getEstadoAtuador() {
		return estadoAtuador;
	}

	public void setEstadoAtuador(String estadoAtuador) {
		this.estadoAtuador = estadoAtuador;
	}

	public String getSensorObstaculo() {
		return sensorObstaculo;
	}

	public void setSensorObstaculo(String sensorObstaculo) {
		this.sensorObstaculo = sensorObstaculo;
	}

	public String getSinalSonoro() {
		return sinalSonoro;
	}

	public void setSinalSonoro(String sinalSonoro) {
		this.sinalSonoro = sinalSonoro;
	}

	public int getTagDestino() {
		return tagDestino;
	}

	public void setTagDestino(int tagDestino) {
		this.tagDestino = tagDestino;
	}

	public int getTagParada() {
		return tagParada;
	}

	public void setTagParada(int tagParada) {
		this.tagParada = tagParada;
	}

	public TagsRotas(String nome, int posicao, Rota rota, Tag tag, int setPoint, int velocidade, int temporizador,
			String girar, String estadoAtuador, String sensorObstaculo, String sinalSonoro, int tagDestino,
			int tagParada, int pitStop) {
		super();
		this.nome = nome;
		this.posicao = posicao;
		this.rota = rota;
		this.tag = tag;
		this.setPoint = setPoint;
		this.velocidade = velocidade;
		this.temporizador = temporizador;
		this.girar = girar;
		this.estadoAtuador = estadoAtuador;
		this.sensorObstaculo = sensorObstaculo;
		this.sinalSonoro = sinalSonoro;
		this.tagDestino = tagDestino;
		this.tagParada = tagParada;
		this.pitStop = pitStop;
	}

}
