package AGVS.Data;

public class AcaoSaida {

	private PortaSaidaMeshSerial fkSaida;
	private String status;
	
	public PortaSaidaMeshSerial getFkEntrada() {
		return fkSaida;
	}
	public void setFkSaida(PortaSaidaMeshSerial fkSaida) {
		this.fkSaida = fkSaida;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "EntradaCondicao [fkSaida=" + fkSaida+ ", status=" + status + "]";
	}
	public AcaoSaida(PortaSaidaMeshSerial fkSaida, String status) {
		super();
		this.fkSaida = fkSaida;
		this.status = status;
	}

	
}
