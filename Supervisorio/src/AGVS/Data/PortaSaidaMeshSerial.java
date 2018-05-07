package AGVS.Data;

public class PortaSaidaMeshSerial {

	private int id;
	private String nome;
	private String porta;
	private String status;
	private MeshSerial ms;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MeshSerial getMs() {
		return ms;
	}

	public void setMs(MeshSerial ms) {
		this.ms = ms;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PortaSaidaMeshSerial(int id, String nome, String porta, String status, MeshSerial ms) {
		super();
		this.id = id;
		this.nome = nome;
		this.porta = porta;
		this.status = status;
		this.ms = ms;
	}

	@Override
	public String toString() {
		return "PortaSaidaMeshSerial [id=" + id + ", nome=" + nome + ", porta=" + porta + ", status=" + status + ", ms="
				+ ms + "]";
	}
}
