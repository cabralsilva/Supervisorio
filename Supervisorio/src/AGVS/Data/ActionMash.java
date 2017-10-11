package AGVS.Data;

public class ActionMash {
	private String nome;
	private ComandoMashSerial cms;
	private Tag tag;
	private Input input;

	public ComandoMashSerial getCms() {
		return cms;
	}

	public void setCms(ComandoMashSerial cms) {
		this.cms = cms;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ActionMash(String nome, ComandoMashSerial cms, Tag tag, Input input) {
		super();
		this.nome = nome;
		this.cms = cms;
		this.tag = tag;
		this.input = input;
	}

}
