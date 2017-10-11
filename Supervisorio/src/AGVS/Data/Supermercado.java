package AGVS.Data;

public class Supermercado {
	private String nome;
	private String produto;
	private long data;
	private int id;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Supermercado(String nome, String produto, long data, int id) {
		super();
		this.nome = nome;
		this.produto = produto;
		this.data = data;
		this.id = id;
	}

}
