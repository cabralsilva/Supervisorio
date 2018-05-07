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
	private String ip;
	private String mac64;
	private long tagAtualTime;
	private int atraso;
	private String statusOld;
	private long statusTimeOld;
	private int frequency;
	private long sinalWifi;

	private long erroTime;
	private String erroStatus;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public long getSinalWifi() {
		return sinalWifi;
	}

	public void setSinalWifi(long sinalWifi) {
		this.sinalWifi = sinalWifi;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AGV other = (AGV) obj;
		if (id != other.id)
			return false;
		return true;
	}


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

	public static void sendRota(int[][] rotas, String ip, String mac64) {
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
			ConfigProcess.serial.enviar(xml, ip, mac64);
		}
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

	public static void enviarEmergencia(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>EM</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>EM</xml>", ip);
		}		
	}

	public static void enviarEmFila(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>EMFILA</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>EMFILA</xml>", ip);
		}
		
	}
	
	public static void enviarEmCruzamento(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>EMCRUZAMENTO</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>EMCRUZAMENTO</xml>", ip);
		}
		
	}
	
	public static void enviarEmRepouso(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>EMREPOUSO</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>EMREPOUSO</xml>", ip);
		}
		
	}
	
	public static void enviarEmEspera(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>EMESPERA</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>EMESPERA</xml>", ip);
		}
		
	}

	public static void enviarParar(String ip, String mac64) { 
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>STOP</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>STOP</xml>", ip);
		}
		
	}

	public static void enviarPararAC(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>STOPAC</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>STOPAC</xml>", ip);
		}
		
	}

	public static void teste(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>TESTE</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>TESTE</xml>", ip);
		}
		
	}

	public static void enviarPlay(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>PLAY</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>PLAY</xml>", ip);
		}
		
	}

	public static void enviarPlayRE(String ip, String mac64) {
//		if (mac64.length() > 0) {
//			ConfigProcess.serial.enviar("<xml>PLAYRE</xml>", ip, mac64);
//		}
		if (ip.length() > 0) {
			ClienteAGV.enviar("<xml>PLAYRE</xml>", ip);
		}
		
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
			String mac64, String ip, long tagAtualTime, int atraso, String statusOld, long statusTimeOld, int frequencia) {
		super();
		this.id = id;
		this.nome = nome;
		this.status = status;
		this.tipo = tipo;
		this.velocidade = velocidade;
		this.bateria = bateria;
		this.tagAtual = tagAtual;
		this.ip = ip;
		this.mac64 = mac64;
		this.tagAtualTime = tagAtualTime;
		this.atraso = atraso;
		this.statusOld = statusOld;
		this.statusTimeOld = statusTimeOld;
		this.frequency = frequencia;
	}

	@Override
	public String toString() {
		return "AGV [id=" + id + ", nome=" + nome + ", status=" + status + ", tipo=" + tipo + ", velocidade="
				+ velocidade + ", bateria=" + bateria + ", tagAtual=" + tagAtual + ", ip=" + ip + ", mac64="
				+ mac64 + ", tagAtualTime=" + tagAtualTime + ", atraso=" + atraso + ", statusOld=" + statusOld
				+ ", statusTimeOld=" + statusTimeOld + ", frequency=" + frequency + ", erroTime=" + erroTime
				+ ", erroStatus=" + erroStatus + "]";
	}

	
}
