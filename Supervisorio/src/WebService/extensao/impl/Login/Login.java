package WebService.extensao.impl.Login;

import java.io.PrintStream;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Usuario;
import AGVS.Util.Log;
import AGVS.Util.Util;
import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.http.Config;
import WebService.http.Cookie;
import WebService.http.Request;
import WebService.http.Response;

public class Login {

	public static final String strLoggin = "Loggin";
	public static final String strLoggout = "Loggout";
	public static final String strAdicionar = "Adicionar";
	public static final String strExcluir = "Excluir";
	public static final String strAlterar = "Alterar";

	// BAM
	// Relatorios
	public static final String tokenRelatorioEventosAGVS = "Relatorio de eventos dos AGS";
	// Logs do Sistema
	public static final String tokenLogUsuarios = "Log de Usuarios";
	public static final String tokenLogTags = "Log de Tags";

	public static final String tokenHome = "Home";

	// ADM
	public static final String tokenUsuarios = "Gerenciamento de Usuarios";
	// pages Cadastros
	public static final String tokenCadastroAGVS = "Cadastro de AGVS";
	public static final String tokenCadastroLayout = "Cadastro de Layout";
	public static final String tokenCadastroTags = "Cadastro de Tags";
	
	public static final String tokenCadastroCruzamentos = "Cadastro de Cruzamentos";
	public static final String tokenCadastroTagsCruzamentos = "Cadastro de Tags do Cruzamento";
	
	public static final String tokenCadastroSemaforos = "Cadastro de Semaforo";
	public static final String tokenCadastroMesh = "Cadastro de Mesh";
	public static final String tokenCadastroTagsSemaforo = "Cadastro de Tags do Semaforo";
	public static final String tokenCadastroRotas = "Cadastro de Rotas";
	public static final String tokenCadastroTempoTags = "Cadastro de Tempo entre Tags";
	public static final String tokenCadastroEquipamentos = "Cadastro de Equipamentos";
	public static final String tokenCadastroSupermercados = "Cadastro de Supermercados";
	public static final String tokenCadastroTempoParadoTags = "Cadastro de tempo parado nas Tags";
	public static final String tokenCadastroZoneTime = "Cadastro de Zonas de Tempo";
	public static final String tokenRelatorioZoneTime = "Relatorio de Zonas de Tempo";
	public static final String tokenRastreamento = "Rastreamento";
	public static final String tokenCadastroLogicaMesh = "Cadastro de Logicas da mesh";
	// pages Configura�ao
	public static final String tokenConfBD = "Configuracao de Bando de Dados";
	// Operador
	// Pages Operador
	public static final String tokenInicioTurno = "Inicio de Turno";
	public static final String tokenPedidos = "Pedidos";

	public static final String[] listTokens = { tokenHome, tokenRelatorioEventosAGVS, tokenRelatorioZoneTime, tokenLogUsuarios,
			tokenCadastroAGVS, tokenCadastroLayout, tokenCadastroTags, tokenCadastroRotas, tokenCadastroCruzamentos, tokenCadastroMesh, tokenCadastroLogicaMesh, 
			tokenCadastroTagsCruzamentos, 
			/*tokenCadastroSemaforos, tokenCadastroTagsSemaforo,*/ 
			tokenCadastroZoneTime, /*tokenCadastroEquipamentos, tokenCadastroSupermercados, tokenCadastroTempoParadoTags,*/ tokenUsuarios/*, 
			tokenInicioTurno, tokenPedidos, tokenRastreamento*/};

	public static final Map<String, String[]> getPermissao(String permissaoPersonalizada) {
		Map<String, String[]> permissao = new HashMap<String, String[]>();

		permissao.put(RulesUsuarios.permissaoAdministrador, null);
		String[] operador = { tokenHome, tokenInicioTurno, tokenPedidos };
		permissao.put(RulesUsuarios.permissaoOperador, operador);
		String[] bam = { tokenRelatorioEventosAGVS, tokenLogUsuarios, tokenUsuarios, tokenHome, tokenInicioTurno,
				tokenPedidos };
		permissao.put(RulesUsuarios.permissaoBam, bam);
		String[] operadoBam = { tokenRelatorioEventosAGVS, tokenLogUsuarios, tokenHome };
		permissao.put(RulesUsuarios.permissaoOperadorBam, operadoBam);
		if (permissaoPersonalizada != null) {
			permissao.put(RulesUsuarios.permissaoPersonalizado, permissaoPersonalizada.split("#"));
		} else {
			String[] personalizado = { tokenHome };
			permissao.put(RulesUsuarios.permissaoPersonalizado, personalizado);
		}

		return permissao;
	}

	public static final String strKeyPassword = "password";
	public static final String strKeyName = "name";

	public static boolean login(Request req, Response resp, String tokenPage) throws Exception {
		Map<String, Cookie> cookies = req.getCookies();
		if (cookies != null && cookies.size() > 0 && cookies.containsKey(strKeyName)
				&& cookies.containsKey(strKeyPassword)
				&& validLogin(cookies.get(strKeyName).getValue(), cookies.get(strKeyPassword).getValue(), tokenPage)) {
			resp.setCookie(strKeyPassword, cookies.get(strKeyPassword).getValue());
			resp.setCookie(strKeyName, cookies.get(strKeyName).getValue());
			return true;
		}
		System.out.println("Post");
		Map<String, String> posts = req.getPostParams();
		if (posts != null && posts.size() > 0 && posts.containsKey(strKeyName) && posts.containsKey(strKeyPassword)
				&& validLogin(posts.get(strKeyName), Util.gerarCriptMD5(posts.get(strKeyPassword)), tokenPage)) {
			resp.setCookie(strKeyPassword, Util.gerarCriptMD5(posts.get(strKeyPassword)));
			resp.setCookie(strKeyName, posts.get(strKeyName));
			System.out.println("Post");
			ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(), posts.get(strKeyName),
					"Usuario " + posts.get(strKeyName) + " Conectou-se com o sistema", Login.strLoggin);
			return true;
		}
		return false;
	}

	public static void redirectLoginInvalid(Request req, Response resp) {
		Tags tags = new Tags();
		ConvertPAGinHTML cph = new ConvertPAGinHTML();
		String[] files = { PathFilesPAG.invalidLogin };
		cph.generateHTML(files, tags);
		PrintStream out = resp.getPrintStream();
		out.println(cph.getHtml(PathFilesPAG.invalidLogin));
		out.flush();

	}

	public static void redirectLoginValid(Request req, Response resp) {
		Tags tags = new Tags();
		ConvertPAGinHTML cph = new ConvertPAGinHTML();
		String[] files = { PathFilesPAG.validLogin };
		cph.generateHTML(files, tags);
		PrintStream out = resp.getPrintStream();
		out.println(cph.getHtml(PathFilesPAG.validLogin));
		out.flush();

	}

	public static void resetLogin(Request req, Response resp) {
		resp.setCookie(strKeyPassword, null);
		resp.setCookie(strKeyName, null);
	}

	public static Usuario getUsuario(String login, String password) {
		List<Usuario> usrs = ConfigProcess.bd().selectUsuarios();
		String lg = "";
		String pw = "";
		for (int i = 0; usrs != null && i < usrs.size(); i++) {
			pw = usrs.get(i).getPassword();
			lg = usrs.get(i).getLogin();

			if (pw.equals(password) && lg.equals(login)) {
				return usrs.get(i);
			}
		}
		return null;
	}

	public static Usuario getUsuario(String login) {
		List<Usuario> usrs = ConfigProcess.bd().selectUsuarios();
		String lg = "";
		for (int i = 0; usrs != null && i < usrs.size(); i++) {
			lg = usrs.get(i).getLogin();

			if (lg.equals(login)) {
				return usrs.get(i);
			}
		}
		return null;
	}

	private static boolean validLogin(String login, String password, String tokenPage) {
		String lg = "";
		String pw = "";
		try {
			List<Usuario> usrs = ConfigProcess.bd().selectUsuarios();
			for (int i = 0; usrs != null && i < usrs.size(); i++) {
				pw = usrs.get(i).getPassword();
				lg = usrs.get(i).getLogin();
				if (pw.equals(password) && lg.equals(login)) {
					Map<String, String[]> permissao = Login.getPermissao(usrs.get(i).getLiberado());

					if (permissao.containsKey(usrs.get(i).getPermissao())) {
						String[] tokens = permissao.get(usrs.get(i).getPermissao());
						if (tokenPage == null || tokens == null) {
							return true;
						}
						tokenPage = Util.removerAcentos(tokenPage);
						String tk = "";
						for (int j = 0; j < tokens.length; j++) {
							tk = Util.removerAcentos(tokens[j]);
							if (tk.equals(tokenPage)) {
								return true;
							}
						}
					}
					return false;
				}

			}
		} catch (Exception e) {
			new Log(e);
		}

		return false;
	}
}
