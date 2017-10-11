package AGVS.Data;

public class RotaAGV {
	private String nome;
	private AGV agv;
	private Rota rota;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AGV getAgv() {
		return agv;
	}

	public void setAgv(AGV agv) {
		this.agv = agv;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public RotaAGV(String nome, AGV agv, Rota rota) {
		super();
		this.nome = nome;
		this.agv = agv;
		this.rota = rota;
	}

}
