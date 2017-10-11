package WebService.extensao.impl;

import java.io.PrintStream;

import AGVS.Data.ConfigProcess;
import AGVS.Data.Usuario;
import AGVS.Util.Util;
import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.Command;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Request;
import WebService.http.Response;

public class SupervisorioAGVSUsuarios implements Command {

	private ConvertPAGinHTML cph;
	private String[] files = { PathFilesPAG.usuarios };

	@Override
	public void execute(Request req, Response resp) throws Exception {
		// TODO Auto-generated method stub
		if(!ConfigProcess.bd().conectarBancoDados()){
			PrintStream out = resp.getPrintStream();
			out.println("<meta http-equiv=\"refresh\" content=1;url=\"/SupervisorioAGVS/Configuracao/BD\">");
			out.flush();
			return;
		}
		if (!Login.login(req, resp, Login.tokenUsuarios)) {
			Login.redirectLoginInvalid(req, resp);
			return;
		}

		

		Tags tags = new Tags();
		Methods mt = new Methods();
		String login = req.getCookies().get(Login.strKeyName).getValue();
		String password = req.getCookies().get(Login.strKeyPassword).getValue();;
		tags.add(Keys.keyGenerataTableUsuarios, mt.methodGenerataTableUsuarios);
		mt.methodGenerataTableUsuarios.setArgs(Util.criarXMLUsr(login, password));
		tags.add(Keys.keyGenerateDashboard, mt.methodGenerateDashboardUsuarios);
		tags.add(Keys.keyGenerataCoboxPermissao, mt.methodGenerataComboxPermissaoUsuarios);
		tags.add(Keys.keyGenerateUser, mt.methodGenerateNomeUsuario);
		mt.methodGenerateNomeUsuario.setArgs(req.getCookie("name").getValue());
		cph = new ConvertPAGinHTML();
		cph.generateHTML(files, tags);

		Home(req, resp);
	}

	private void Home(Request req, Response resp) {
		PrintStream out = resp.getPrintStream();
		out.println(cph.getHtml(PathFilesPAG.usuarios));
		out.flush();
	}

}
