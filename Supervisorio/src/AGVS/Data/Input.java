package AGVS.Data;

public class Input {
	private MeshSerial ms;
	private String nome;
	private int db;
	private int memory;
	private int vByte;
	private int value;

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

	public int getDb() {
		return db;
	}

	public void setDb(int db) {
		this.db = db;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getvByte() {
		return vByte;
	}

	public void setvByte(int vByte) {
		this.vByte = vByte;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getSendSerial() {
		return "<xml>d" + db + "m" + memory + "b" + vByte + "v" + value + "</xml>";
	}

	public void sendComando() {
		ConfigProcess.serial.enviar(getSendSerial(), ms.getIp(), ms.getMac64());
		ConfigProcess.serial.enviar(getSendSerial(), ms.getIp(), ms.getMac64());
		ConfigProcess.serial.enviar(getSendSerial(), ms.getIp(), ms.getMac64());
	}

	public Input(MeshSerial ms, String nome, int db, int memory, int vByte, int value) {
		super();
		this.ms = ms;
		this.nome = nome;
		this.db = db;
		this.memory = memory;
		this.vByte = vByte;
		this.value = value;
	}

}
