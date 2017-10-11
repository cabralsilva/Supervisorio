package AGVS.Data;

import java.util.List;

public class ComandoMashSerial {

	public final static String liberar = "Liberar";
	public final static String parar = "Parar";
	public final static String emergencia = "Emergencia";

	private String nome;

	private String comando;
	private List<Tag> tags;
	private Tag tagPrioridade;
	private List<PortaMashSerial> trigger;
	private AlertaMesh am;
	private long timeOld;
	private TagCruzamentoMash tcm;

	public TagCruzamentoMash getTcm() {
		return tcm;
	}

	public void setTcm(TagCruzamentoMash tcm) {
		this.tcm = tcm;
	}

	public AlertaMesh getAm() {
		return am;
	}

	public void setAm(AlertaMesh am) {
		this.am = am;
	}

	public Tag getTagPrioridade() {
		return tagPrioridade;
	}

	public void setTagPrioridade(Tag tagPrioridade) {
		this.tagPrioridade = tagPrioridade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<PortaMashSerial> getTrigger() {
		return trigger;
	}

	public void setTrigger(List<PortaMashSerial> trigger) {
		this.trigger = trigger;
	}

	public long getTimeOld() {
		return timeOld;
	}

	public void setTimeOld(long timeOld) {
		this.timeOld = timeOld;
	}

	public ComandoMashSerial(String nome, String comando, List<Tag> tags, List<PortaMashSerial> trigger,
			Tag tagPrioridade, AlertaMesh am, TagCruzamentoMash tcm) {
		super();
		this.nome = nome;
		this.comando = comando;
		this.tags = tags;
		this.trigger = trigger;
		this.tagPrioridade = tagPrioridade;
		this.am = am;
		timeOld = System.currentTimeMillis();
		this.tcm = tcm;
	}

}
