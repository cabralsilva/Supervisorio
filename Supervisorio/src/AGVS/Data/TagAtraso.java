package AGVS.Data;

public class TagAtraso {

	private String nome;
	private Tag tag;
	private long time;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTgInicio(Tag tag) {
		this.tag = tag;
	}

	public long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public TagAtraso(String nome, Tag tag, long time) {
		super();
		this.nome = nome;
		this.tag = tag;
		this.time = time;
	}

}
