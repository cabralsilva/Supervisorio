package AGVS.Data;

public class LogUsuario {
	private int id;
	private String data;
	private String descricao;
	private String tipo;
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LogUsuario(int id, String data, String descricao, String tipo, String nome) {
		super();
		this.id = id;
		this.data = data;
		this.descricao = descricao;
		this.tipo = tipo;
		this.nome = nome;
	}
	
	

}
