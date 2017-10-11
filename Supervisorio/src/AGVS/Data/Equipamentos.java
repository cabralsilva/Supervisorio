package AGVS.Data;

public class Equipamentos {
	private String nome;
	private String tipo;
	private int rota;
	private String id;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getRota() {
		return rota;
	}

	public void setRota(int rota) {
		this.rota = rota;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Equipamentos(String nome, String tipo, int rota, String id) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.rota = rota;
		this.id = id;
	}

}
