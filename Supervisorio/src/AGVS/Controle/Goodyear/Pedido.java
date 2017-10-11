package AGVS.Controle.Goodyear;

import AGVS.Data.AGV;
import AGVS.Data.Supermercado;

public class Pedido {
	private long id;
	private AGV agv;
	private String idProduto;
	private String idEquipamento;
	private String tipo;
	private Supermercado supermercadoA;
	private Supermercado supermercadoB;
	private String retorno;
	private String log;
	private int[][] rotas;

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AGV getAgv() {
		return agv;
	}

	public void setAgv(AGV agv) {
		this.agv = agv;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public String getIdEquipamento() {
		return idEquipamento;
	}

	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Supermercado getSupermercadoA() {
		return supermercadoA;
	}

	public void setSupermercadoA(Supermercado supermercadoA) {
		this.supermercadoA = supermercadoA;
	}

	public Supermercado getSupermercadoB() {
		return supermercadoB;
	}

	public void setSupermercadoB(Supermercado supermercadoB) {
		this.supermercadoB = supermercadoB;
	}

	public int[][] getRotas() {
		return rotas;
	}

	public void setRotas(int[][] rotas) {
		this.rotas = rotas;
	}

	public Pedido(long id, AGV agv, String idProduto, String idEquipamento, String tipo, Supermercado supermercadoA,
			Supermercado supermercadoB, String retorno) {
		super();
		this.id = id;
		this.agv = agv;
		this.idProduto = idProduto;
		this.idEquipamento = idEquipamento;
		this.tipo = tipo;
		this.supermercadoA = supermercadoA;
		this.supermercadoB = supermercadoB;
		this.retorno = retorno;
		this.log = "";
		this.rotas = null;
	}

}
