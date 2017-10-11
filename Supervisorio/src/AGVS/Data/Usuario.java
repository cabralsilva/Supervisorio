package AGVS.Data;

public class Usuario {
	
	private String login;
	private String nome;
	private String email;
	private String password;
	private String permissao;
	private String liberado;
	
	public String getLiberado() {
		return liberado;
	}
	public void setLiberado(String liberado) {
		this.liberado = liberado;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPermissao() {
		return permissao;
	}
	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	public Usuario(String login, String nome, String email, String password, String permissao) {
		super();
		this.login = login;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.permissao = permissao;
		this.liberado = null;
	}
	
	public Usuario(String login, String nome, String email, String password, String permissao, String liberado) {
		super();
		this.login = login;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.permissao = permissao;
		this.liberado = liberado;
	}
	
	
	
	
}
