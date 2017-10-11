package AGVS.Data;

public class TagCruzamento {
	private String nome;
	private Tag tag;
	private Cruzamento cruzamento;
	private String tipo;

	public static final String tipoEntrada = "Entrada";
	public static final String tipoSaida = "Saida";
	public static final String[] tipos = { tipoEntrada, tipoSaida };

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

	public Cruzamento getCruzamento() {
		return cruzamento;
	}

	public void setCruzamento(Cruzamento cruzamento) {
		this.cruzamento = cruzamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public TagCruzamento(String nome, Tag tag, Cruzamento cruzamento, String tipo) {
		super();
		this.nome = nome;
		this.tag = tag;
		this.cruzamento = cruzamento;
		this.tipo = tipo;
	}

}
