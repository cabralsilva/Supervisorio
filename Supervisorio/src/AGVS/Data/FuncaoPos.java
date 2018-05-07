package AGVS.Data;

public class FuncaoPos {
	private PosicaoInicioTurno pit;
	private String nome;
	private String status;
	private int pos;
	private Cruzamento_OLD ct;

	public static final String RODANDO = "Rodando";
	public static final String FILAESPERA = "Fila de Espera";

	public PosicaoInicioTurno getPit() {
		return pit;
	}

	public void setPit(PosicaoInicioTurno pit) {
		this.pit = pit;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public Cruzamento_OLD getCt() {
		return ct;
	}

	public void setCt(Cruzamento_OLD ct) {
		this.ct = ct;
	}

	public FuncaoPos(PosicaoInicioTurno pit, String nome, String status, int pos, Cruzamento_OLD ct) {
		super();
		this.pit = pit;
		this.nome = nome;
		this.status = status;
		this.pos = pos;
		this.ct = ct;
	}

}
