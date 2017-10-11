package WebService.HTML;

import java.util.List;

public class DashboardTitulo {
	private String titulo;
	private DashboardMenu[] menus;
	private boolean visivel = false;

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public boolean isVisivel() {
		return this.visivel;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public DashboardMenu[] getMenus() {
		return menus;
	}

	public void setMenus(DashboardMenu[] menus) {
		this.menus = menus;
	}

	public DashboardTitulo(String titulo, DashboardMenu[] menus) {
		super();
		this.titulo = titulo;
		this.menus = menus;
	}

}
