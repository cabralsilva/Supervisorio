package AGVS.Data;

public class Rota {

	private String nome;
	private String descricao;

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

	public Rota(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}

}
