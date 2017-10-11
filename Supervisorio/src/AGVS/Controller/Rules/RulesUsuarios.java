package AGVS.Controller.Rules;

public class RulesUsuarios {
	public static final String permissaoAdministrador = "Administrador";
	public static final String permissaoOperador = "Operador";
	public static final String permissaoBam = "BAM";
	public static final String permissaoOperadorBam = "Operador/BAM";
	public static final String permissaoPersonalizado = "Personalizado";

	public static final String[] permissao = { permissaoAdministrador, permissaoOperador, permissaoBam, permissaoOperadorBam,
			permissaoPersonalizado };
	
	public static final String tipoEditar = "Editar";
	public static final String tipoSalvar = "Salvar";
	public static final String tipoExcluir = "Excluir";
	public static final String tipoLogin = "Login";
	
	public static final String[] tipo = {tipoEditar, tipoSalvar, tipoExcluir, tipoLogin};

}
