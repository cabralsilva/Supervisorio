package WebService.HTML;

import java.util.List;

public class DashboardMenu {
	private String nome;
	private boolean ativo;
	private String icone;
	private String link;
	private ProcessingMethod gerar;
	private DashboardSubMenu[] subMenus;
	private boolean visivel = false;

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public boolean isVisivel() {
		return this.visivel;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public ProcessingMethod getGerar() {
		return gerar;
	}

	public void setGerar(ProcessingMethod gerar) {
		this.gerar = gerar;
	}

	public DashboardSubMenu[] getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(DashboardSubMenu[] subMenus) {
		this.subMenus = subMenus;
	}

	public DashboardMenu(String nome, boolean ativo, String icone, String link, ProcessingMethod gerar, DashboardSubMenu[] subMenus) {
		super();
		this.nome = nome;
		this.ativo = ativo;
		this.icone = icone;
		this.link = link;
		this.gerar = gerar;
		this.subMenus = subMenus;
	}
	
	

}
