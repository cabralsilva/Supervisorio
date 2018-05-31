package AGVS.Data;

import java.util.List;

public class MeshSerial {
	private String mac16;
	private String mac64;
	private String ip;
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private String nome;
	private int id;
	private int numeroEntradas;
	private int numeroSaidas;
	private List<PortaMashSerial> lstPms;
	private List<PortaSaidaMeshSerial> lstSpms;
	
	/*CONTROLE BAIAS FUNILARIA FIAT*/
//	private List<PLCPheriferical> lstPLCPheriferical;
//	
//	public List<PLCPheriferical> getLstPLCPheriferical() {
//		return lstPLCPheriferical;
//	}
//
//	public void setLstPLCPheriferical(List<PLCPheriferical> lstPLCPheriferical) {
//		this.lstPLCPheriferical = lstPLCPheriferical;
//	}

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public int getNumeroEntradas() {
		return numeroEntradas;
	}

	public void setNumeroEntradas(int numeroEntradas) {
		this.numeroEntradas = numeroEntradas;
	}

	public int getNumeroSaidas() {
		return numeroSaidas;
	}

	public void setNumeroSaidas(int numeroSaidas) {
		this.numeroSaidas = numeroSaidas;
	}

	public MeshSerial(int numeroEntradas, int numeroSaidas, String mac16, String mac64, String nome, int id) {
		super();
		this.numeroEntradas = numeroEntradas;
		this.numeroSaidas = numeroSaidas;
		this.mac16 = mac16;
		this.mac64 = mac64;
		this.nome = nome;
		this.id = id;
		System.out.println("Init Mash id: " + id + " nome: " + nome + " mac64: " + mac64 + " mac16: " + mac16);
	}

	@Override
	public String toString() {
		return "MeshSerial [mac16=" + mac16 + ", mac64=" + mac64 + ", nome=" + nome + ", id=" + id + ", numeroEntradas="
				+ numeroEntradas + ", numeroSaidas=" + numeroSaidas + ", lstPms=" + lstPms + ", lstSpms=" + lstSpms
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((mac16 == null) ? 0 : mac16.hashCode());
		result = prime * result + ((mac64 == null) ? 0 : mac64.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + numeroEntradas;
		result = prime * result + numeroSaidas;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeshSerial other = (MeshSerial) obj;
		if (id != other.id)
			return false;
		if (mac16 == null) {
			if (other.mac16 != null)
				return false;
		} else if (!mac16.equals(other.mac16))
			return false;
		if (mac64 == null) {
			if (other.mac64 != null)
				return false;
		} else if (!mac64.equals(other.mac64))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numeroEntradas != other.numeroEntradas)
			return false;
		if (numeroSaidas != other.numeroSaidas)
			return false;
		return true;
	}

	public List<PortaMashSerial> getLstPms() {
		return lstPms;
	}

	public void setLstPms(List<PortaMashSerial> lstPms) {
		this.lstPms = lstPms;
	}
	
	
	
	public List<PortaSaidaMeshSerial> getLstSpms() {
		return lstSpms;
	}

	public void setLstSpms(List<PortaSaidaMeshSerial> lstSpms) {
		this.lstSpms = lstSpms;
	}

	public void acionarSaida(String iSaida) {
		switch(iSaida) {
			case "1":
				ConfigProcess.serial.enviar("<xml>S1_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "2":
				ConfigProcess.serial.enviar("<xml>S2_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "3":
				ConfigProcess.serial.enviar("<xml>S3_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "4":
				ConfigProcess.serial.enviar("<xml>S4_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "5":
				ConfigProcess.serial.enviar("<xml>S5_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "6":
				ConfigProcess.serial.enviar("<xml>S6_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "7":
				ConfigProcess.serial.enviar("<xml>S7_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "8":
				ConfigProcess.serial.enviar("<xml>S8_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "9":
				ConfigProcess.serial.enviar("<xml>S9_ON</xml>", this.getMac16(), this.getMac64());
				break;
			case "10":
				ConfigProcess.serial.enviar("<xml>S10_ON</xml>", this.getMac16(), this.getMac64());
				break;
		}
		for (PortaSaidaMeshSerial psms : this.lstSpms) {
			if (psms.getPorta().equals(iSaida)) {
				psms.setStatus("S"+iSaida+"_ON");
				ConfigProcess.bd().updateGatesOutMeshSerial(psms);
			}
		}
	}
	
	public void desacionarSaida(String iSaida) {
		switch(iSaida) {
			case "1":
				ConfigProcess.serial.enviar("<xml>S1_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "2":
				ConfigProcess.serial.enviar("<xml>S2_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "3":
				ConfigProcess.serial.enviar("<xml>S3_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "4":
				ConfigProcess.serial.enviar("<xml>S4_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "5":
				ConfigProcess.serial.enviar("<xml>S5_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "6":
				ConfigProcess.serial.enviar("<xml>S6_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "7":
				ConfigProcess.serial.enviar("<xml>S7_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "8":
				ConfigProcess.serial.enviar("<xml>S8_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "9":
				ConfigProcess.serial.enviar("<xml>S9_OFF</xml>", this.getMac16(), this.getMac64());
				break;
			case "10":
				ConfigProcess.serial.enviar("<xml>S10_OFF</xml>", this.getMac16(), this.getMac64());
				break;
		}
		for (PortaSaidaMeshSerial psms : this.lstSpms) {
//			System.out.println("desacionar...  " + psms.getPorta() + " == " + iSaida);
			if (psms.getPorta().equals(iSaida)) {
//				System.out.println("Desacionando saída " + iSaida);
				psms.setStatus("S"+iSaida+"_OFF");
				ConfigProcess.bd().updateGatesOutMeshSerial(psms);
			}
		}
	}
}
