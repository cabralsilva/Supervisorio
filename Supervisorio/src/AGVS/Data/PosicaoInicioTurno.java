package AGVS.Data;

import java.util.List;

public class PosicaoInicioTurno {
	private String nome;
	private Tag tag;
	private AGV agv;
	private List<FuncaoPos> fps;

	public List<FuncaoPos> getFps() {
		return fps;
	}

	public void setFps(List<FuncaoPos> fps) {
		this.fps = fps;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public PosicaoInicioTurno(String nome, Tag tag, AGV agv, List<FuncaoPos> fps) {
		super();
		this.nome = nome;
		this.tag = tag;
		this.agv = agv;
		this.fps = fps;
	}

	public AGV getAgv() {
		return agv;
	}

	public void setAgv(AGV agv) {
		this.agv = agv;
	}

}
