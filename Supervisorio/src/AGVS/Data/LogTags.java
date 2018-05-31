package AGVS.Data;

public class LogTags {
	private int id;
	private long data;
	private int idAgv;
	private String msg;
	private String epc;
	private int bateria;
	private int velocidade;
	
	public int getBateria() {
		return bateria;
	}

	public void setBateria(int bateria) {
		this.bateria = bateria;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

	public int getIdAgv() {
		return idAgv;
	}

	public void setIdAgv(int idAgv) {
		this.idAgv = idAgv;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	public LogTags(int id, long data, int idAgv, String msg, String epc, int bateria, int velocidade) {
		super();
		this.id = id;
		this.data = data;
		this.idAgv = idAgv;
		this.msg = msg;
		this.epc = epc;
		this.bateria = bateria;
		this.velocidade = velocidade;
	}

}
