package AGVS.Data;

import java.util.List;

public class MeshSerial {
	private String mac16;
	private String mac64;
	private String nome;
	private int id;
	private int numeroEntradas;
	private int numeroSaidas;
	private List<PortaMashSerial> lstPms;;

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
				+ numeroEntradas + ", numeroSaidas=" + numeroSaidas + ", lstPms=\n" + lstPms + "\n]";
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
	
	
}
