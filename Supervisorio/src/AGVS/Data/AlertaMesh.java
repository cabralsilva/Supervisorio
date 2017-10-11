package AGVS.Data;

public class AlertaMesh {
	private String nome;
	private Input acionar;
	private Input desacionar;
	private int oldState;

	public final static int stateAC = 1;
	public final static int stateDAC = 0;
	public final static int stateNULL = 2;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Input getAcionar() {
		return acionar;
	}

	public void setAcionar(Input acionar) {
		this.acionar = acionar;
	}

	public Input getDesacionar() {
		return desacionar;
	}

	public void setDesacionar(Input desacionar) {
		this.desacionar = desacionar;
	}

	public AlertaMesh(String nome, Input acionar, Input desacionar) {
		super();
		this.nome = nome;
		this.acionar = acionar;
		this.desacionar = desacionar;
		this.oldState = stateNULL;
	}

	public void acionar() {
		if (oldState != stateAC) {
			acionar.sendComando();
			acionar.sendComando();
			oldState = stateAC;
		}
	}

	public void desacionar() {
		if (oldState != stateDAC) {
			desacionar.sendComando();
			desacionar.sendComando();
			oldState = stateDAC;
		}
	}
}
