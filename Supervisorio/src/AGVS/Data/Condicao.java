package AGVS.Data;

import java.util.List;

public class Condicao {

	private int id;
	private int indice;
	private List<EntradaCondicao> lstEntradasCondicao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public List<EntradaCondicao> getLstEntradasCondicao() {
		return lstEntradasCondicao;
	}
	public void setLstEntradasCondicao(List<EntradaCondicao> lstEntradasCondicao) {
		this.lstEntradasCondicao = lstEntradasCondicao;
	}
	public Condicao(int id, int indice, List<EntradaCondicao> lstEntradasCondicao) {
		super();
		this.id = id;
		this.indice = indice;
		this.lstEntradasCondicao = lstEntradasCondicao;
	}
	@Override
	public String toString() {
		return "Condicao [id=" + id + ", indice=" + indice + ", lstEntradasCondicao=" + lstEntradasCondicao + "]";
	}
	

	

}
