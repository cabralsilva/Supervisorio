package AGVS.Data;

public class PortaMashSerial {

	public final static String E1_ON = "E1_ON";
	public final static String E1_OFF = "E1_OFF";
	public final static String E2_ON = "E2_ON";
	public final static String E2_OFF = "E2_OFF";
	public final static String E3_ON = "E3_ON";
	public final static String E3_OFF = "E3_OFF";
	public final static String E4_ON = "E4_ON";
	public final static String E4_OFF = "E4_OFF";
	public final static String E5_ON = "E5_ON";
	public final static String E5_OFF = "E5_OFF";
	public final static String E6_ON = "E6_ON";
	public final static String E6_OFF = "E6_OFF";
	public final static String E7_ON = "E7_ON";
	public final static String E7_OFF = "E7_OFF";
	public final static String E8_ON = "E8_ON";
	public final static String E8_OFF = "E8_OFF";

	public final static String portE1 = "E1";
	public final static String portE2 = "E2";
	public final static String portE3 = "E3";
	public final static String portE4 = "E4";
	public final static String portE5 = "E5";
	public final static String portE6 = "E6";
	public final static String portE7 = "E7";
	public final static String portE8 = "E8";

	public static String getPortState(int id) {
		switch (id) {
		case 17:
			return E1_ON;
		case 18:
			return E1_OFF;
		case 19:
			return E2_ON;
		case 20:
			return E2_OFF;
		case 21:
			return E3_ON;
		case 22:
			return E3_OFF;
		case 23:
			return E4_ON;
		case 24:
			return E4_OFF;
		case 25:
			return E5_ON;
		case 26:
			return E5_OFF;
		case 27:
			return E6_ON;
		case 28:
			return E6_OFF;
		case 29:
			return E7_ON;
		case 30:
			return E7_OFF;
		case 31:
			return E8_ON;
		case 32:
			return E8_OFF;
		default:
			return null;
		}
	}

	public static String getPort(int id) {
		switch (id) {
		case 17:
			return portE1;
		case 18:
			return portE1;
		case 19:
			return portE2;
		case 20:
			return portE2;
		case 21:
			return portE3;
		case 22:
			return portE3;
		case 23:
			return portE4;
		case 24:
			return portE4;
		case 25:
			return portE5;
		case 26:
			return portE5;
		case 27:
			return portE6;
		case 28:
			return portE6;
		case 29:
			return portE7;
		case 30:
			return portE7;
		case 31:
			return portE8;
		case 32:
			return portE8;
		default:
			return null;
		}
	}

	private String nome;
	private String porta;
	private String acionamento;
	private String status;
	private MeshSerial ms;

	
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

	public String getAcionamento() {
		return acionamento;
	}

	public void setAcionamento(String acionamento) {
		this.acionamento = acionamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PortaMashSerial(String nome, String porta, String acionamento, String status, MeshSerial ms) {
		super();
		this.nome = nome;
		this.porta = porta;
		this.acionamento = acionamento;
		this.status = status;
		this.ms = ms;
	}

	@Override
	public String toString() {
		return "PortaMashSerial [nome=" + nome + ", porta=" + porta + ", acionamento=" + acionamento + ", status="
				+ status + ", ms=" + ms + "]";
	}

}
