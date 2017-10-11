package AGVS.Data;

public class Line {

	public static final String corPreto = "Preto";
	public static final String corVermelho = "Vermelho";
	public static final String corVerde = "Verde";
	public static final String corAzul = "Azul";
	public static final String corMarrom = "Marrom";
	public static final String corCinza = "Cinza";
	public static final String corAzulClaro = "Azul Claro";
	public static final String corAmarelo = "Amarelo";
	

	public static final String[] cores = { corPreto, corVermelho, corVerde, corAzul, corMarrom, corCinza, corAzulClaro, corAmarelo };

	private String descricao;
	private int xInicial;
	private int yInicial;
	private int xFinal;
	private int yFinal;
	private String cor;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getxInicial() {
		return xInicial;
	}

	public void setxInicial(int xInicial) {
		this.xInicial = xInicial;
	}

	public int getyInicial() {
		return yInicial;
	}

	public void setyInicial(int yInicial) {
		this.yInicial = yInicial;
	}

	public int getxFinal() {
		return xFinal;
	}

	public void setxFinal(int xFinal) {
		this.xFinal = xFinal;
	}

	public int getyFinal() {
		return yFinal;
	}

	public void setyFinal(int yFinal) {
		this.yFinal = yFinal;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Line(String descricao, int xInicial, int yInicial, int xFinal, int yFinal, String cor) {
		super();
		this.descricao = descricao;
		this.xInicial = xInicial;
		this.yInicial = yInicial;
		this.xFinal = xFinal;
		this.yFinal = yFinal;
		this.cor = cor;
	}

}
