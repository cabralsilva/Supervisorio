package AGVS.Data;

import java.util.Comparator;

import AGVS.WIFI.SUPERVISORIO.ClienteAGV;

public class AGV implements Comparable<AGV>{

	public static final String tipoCarregador = "Carregador";
	public static final String tipoRebocador = "Rebocador";
	public static final String tipoRebocadorToyota = "Rebocador Toyota";
	public static final String tipoRebocadorJacto = "Rebocador Jacto";
	public static final String[] tiposAGV = { tipoCarregador, tipoRebocador, tipoRebocadorToyota, tipoRebocadorJacto };

	public static final String statusRodando = "Rodando";
	public static final String statusRodandoManual = "Rodando Manual";
	public static final String statusCarregando = "Carregando";
	public static final String statusParadoManual = "Parado Manual";
	public static final String statusParado = "Parado";
	public static final String statusHome = "Home";
	public static final String statusInativo = "Inativo";
	public static final String statusManutecao = "Manutencao";
	public static final String statusObstaculo = "Obstaculo";
	public static final String statusFugaRota = "Fuga de Rota";
	public static final String statusEmEspera = "Em espera";
	public static final String statusEmRepouso = "Em repouso";
	public static final String statusEmCruzamento = "Cruzamento";
	public static final String statusEmFila = "Em Fila";
	public static final String statusEmergencia = "Emergencia";
	public static final String statusManual = "Manual";
	public static final String statusEmergenciaRemota = "Emergencia remota";

//	public static final String[] statusAGV = { statusRodando, statusCarregando, statusParado, statusHome, statusInativo,
//			statusManutecao, statusObstaculo, statusFugaRota, statusEmEspera, statusCorrenteMotorAlto,
//			statusFluidoFreioBaixo, statusTemperaturaMotores, statusEmergencia, statusManual, statusEmergenciaRemota };
	
	public static final String[] statusAGV = { statusRodando, statusCarregando, statusParado, statusHome, statusInativo,
			statusManutecao, statusObstaculo, statusFugaRota, statusEmEspera, statusEmRepouso,
			statusEmCruzamento, statusEmFila, statusEmergencia, statusManual, statusEmergenciaRemota, statusRodandoManual, statusParadoManual };

	public static final String[] statusAGVWar = { statusObstaculo, statusFugaRota, statusEmergencia, statusEmergenciaRemota };

	private int id;
	private String nome;
	private String status;
	private String tipo;
	private int velocidade;
	private int bateria;
	private String tagAtual;
	private String mac16;
	private String mac64;
	private String ip;
	private long tagAtualTime;
	private int atraso;
	private String statusOld;
	private long statusTimeOld;
	private int frequency;
	private long sinalWifi;

	private long erroTime;
	private String erroStatus;

	public static final Comparator<AGV> POR_CODIGO = new Comparator<AGV>() {
		public int compare(AGV a, AGV b) {
			return a.compareTo(b);
		}
	};
	
	
	
	@Override
	public int compareTo(AGV arg0) {
		Integer i = id;
		int valor = i.compareTo(arg0.id);
		return (valor!=0?valor:1);
	}

	public static void sendRota(int[][] rotas, String mac16, String mac64) {
		if (rotas.length >= 10) {
			String xml = "";
			xml += "<xml><r>";
			for (int i = 0; i < 10; i++) {
				xml += (char) (rotas[i][0] & 0xFF);
				xml += (char) (rotas[i][1] & 0xFF);
				System.out.print("R" + (i + 1) + "(" + rotas[i][0] + "|" + rotas[i][1] + ")\n");
			}
			xml += "</r></xml>";

			for (byte a : xml.getBytes()) {
				System.out.print("|" + (a & 0xff) + "|");
			}
			System.out.println();
			for (char a : xml.toCharArray()) {
				System.out.print("|" + (char) (a & 0xff) + "|");
			}
			System.out.println();
			ConfigProcess.serial.enviar(xml, mac16, mac64);
		}
	}	
	
	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getStatusOld() {
		return statusOld;
	}

	public void setStatusOld(String statusOld) {
		this.statusOld = statusOld;
	}

	public long getStatusTimeOld() {
		return statusTimeOld;
	}

	public void setStatusTimeOld(long statusTimeOld) {
		this.statusTimeOld = statusTimeOld;
	}

	public long getErroTime() {
		return erroTime;
	}

	public void setErroTime(long erroTime) {
		this.erroTime = erroTime;
	}

	public String getErroStatus() {
		return erroStatus;
	}

	public void setErroStatus(String erroStatus) {
		this.erroStatus = erroStatus;
	}
	
	public long getSinalWifi() {
		return sinalWifi;
	}

	public void setSinalWifi(long sinalWifi) {
		this.sinalWifi = sinalWifi;
	}

	public static void enviarEmergencia(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>EM</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("EM", ip);
	}

	public static void enviarEmFila(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>EMFILA</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("EMFILA", ip);
	}
	
	public static void enviarEmCruzamento(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>EMCRUZAMENTO</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("EMCRUZAMENTO", ip);
	}
	
	public static void enviarEmRepouso(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>EMREPOUSO</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("EMREPOUSO", ip);
	}
	
	public static void enviarEmEspera(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>EMESPERA</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("EMESPERA", ip);
	}

	public static void enviarParar(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>STOP</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("STOP", ip);
	}

	public static void enviarPararAC(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>STOPAC</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("STOPAC", ip);
	}

	public static void teste(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>TESTE</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("TESTE", ip);
	}

	public static void enviarPlay(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>PLAY</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("PLAY", ip);
	}

	public static void enviarPlayRE(String mac16, String mac64,String ip) {
		ConfigProcess.serial.enviar("<xml>PLAYRE</xml>", mac16, mac64);
//		ClienteAGV.enviarNew("PLAYRE", ip);
	}

	public static void enviarMacSupervisorio(String mac16, String mac64, String macSupervisorio,String ip) {
		ConfigProcess.serial.enviar("<xml>"+macSupervisorio+"</xml>", mac16, mac64);
//		ClienteAGV.enviarNew(macSupervisorio, ip);
	}

	public long getTagAtualTime() {
		return tagAtualTime;
	}

	public void setTagAtualTime(long tagAtualTime) {
		this.tagAtualTime = tagAtualTime;
	}

	public int getAtraso() {
		return atraso;
	}

	public void setAtraso(int atraso) {
		this.atraso = atraso;
	}

	public String getMac16() {
		return mac16;
	}

	public void setMac16(String mac16) {
		this.mac16 = mac16;
	}

	public String getMac64() {
		return mac64;
	}

	public void setMac64(String mac64) {
		this.mac64 = mac64;
	}

	public String getTagAtual() {
		return tagAtual;
	}

	public void setTagAtual(String tagAtual) {
		this.tagAtual = tagAtual;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public int getBateria() {
		return bateria;
	}

	public void setBateria(int bateria) {
		this.bateria = bateria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public AGV(int id, String nome, String status, String tipo, int velocidade, int bateria, String tagAtual,
			String mac64, String mac16, long tagAtualTime, int atraso, String statusOld, long statusTimeOld, int frequencia, String ip) {
		super();
		this.id = id;
		this.nome = nome;
		this.status = status;
		this.tipo = tipo;
		this.velocidade = velocidade;
		this.bateria = bateria;
		this.tagAtual = tagAtual;
		this.mac16 = mac16;
		this.mac64 = mac64;
		this.tagAtualTime = tagAtualTime;
		this.atraso = atraso;
		this.statusOld = statusOld;
		this.statusTimeOld = statusTimeOld;
		this.frequency = frequencia;
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "AGV [id=" + id + ", nome=" + nome + ", status=" + status + ", tipo=" + tipo + ", velocidade="
				+ velocidade + ", bateria=" + bateria + ", tagAtual=" + tagAtual + ", mac16=" + mac16 + ", mac64="
				+ mac64 + ", tagAtualTime=" + tagAtualTime + ", atraso=" + atraso + ", statusOld=" + statusOld
				+ ", statusTimeOld=" + statusTimeOld + ", frequency=" + frequency + ", erroTime=" + erroTime
				+ ", erroStatus=" + erroStatus + "]";
	}

	
}
