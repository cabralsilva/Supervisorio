package AGVS.Data;

public class EntradaCondicao {

	private PortaMashSerial fkEntrada;
	private String status;
	public PortaMashSerial getFkEntrada() {
		return fkEntrada;
	}
	public void setFkEntrada(PortaMashSerial fkEntrada) {
		this.fkEntrada = fkEntrada;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "EntradaCondicao [fkEntrada=" + fkEntrada + ", status=" + status + "]";
	}
	public EntradaCondicao(PortaMashSerial fkEntrada, String status) {
		super();
		this.fkEntrada = fkEntrada;
		this.status = status;
	}

	
}
