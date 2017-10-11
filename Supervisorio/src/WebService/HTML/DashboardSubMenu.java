package WebService.HTML;

public class DashboardSubMenu {
	private String nome;
	private boolean ativo;
	private String link;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public DashboardSubMenu(String nome, boolean ativo, String link) {
		super();
		this.nome = nome;
		this.ativo = ativo;
		this.link = link;
	}

}
