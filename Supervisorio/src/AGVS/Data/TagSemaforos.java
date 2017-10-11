package AGVS.Data;

public class TagSemaforos {
	private int id;
	private String nome;
	private Tag tag;
	private Semaforo semaforo;
	private String tipo;
	
	public final static String[] tipos = {Semaforo.DESLIGADO, Semaforo.VERDE, Semaforo.VERMELHO, Semaforo.AMARELO, Semaforo.PISCA_VERDE, Semaforo.PISCA_AMARELO, Semaforo.PISCA_VERMELHO};

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

	public Semaforo getSemaforo() {
		return semaforo;
	}

	public void setCruzamento(Semaforo semaforo) {
		this.semaforo = semaforo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSemaforo(Semaforo semaforo) {
		this.semaforo = semaforo;
	}

	public TagSemaforos(int id, String nome, Tag tag,Semaforo semaforo, String tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.tag = tag;
		this.semaforo = semaforo;
		this.tipo = tipo;
	}

}
