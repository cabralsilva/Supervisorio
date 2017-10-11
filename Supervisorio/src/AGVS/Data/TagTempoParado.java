package AGVS.Data;

public class TagTempoParado {
	private String nome;
	private Tag tag;

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

	public TagTempoParado(String nome, Tag tag) {
		super();
		this.nome = nome;
		this.tag = tag;
	}

}
