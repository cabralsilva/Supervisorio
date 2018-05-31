package WebService.extensao.impl;

import java.io.PrintStream;

import AGVS.Data.ConfigProcess;
import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.Command;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.http.Request;
import WebService.http.Response;

public class SupervisorioAGVSConfigMAC implements Command {

	private ConvertPAGinHTML cph;
	private String[] files = { PathFilesPAG.configMAC };

	@Override
	public void execute(Request req, Response resp) throws Exception {
		// TODO Auto-generated method stub
		if (ConfigProcess.bd().conectarBancoDados() && !Login.login(req, resp, Login.tokenConfMAC)) {
			Login.redirectLoginInvalid(req, resp);
			return;
		}
		

		Tags tags = new Tags();
		Methods mt = new Methods();
		tags.add(Keys.keyGenerateDashboard, mt.methodGenerateDashboardConfigMAC);

		tags.add(Keys.keyGerarLinhaMacSupervisorio, mt.methodGerarLinhaMacSupervisorio);
		if (req.getCookies().containsKey("name"))
			mt.methodGenerateNomeUsuario.setArgs(req.getCookie("name").getValue());
		
		cph = new ConvertPAGinHTML();
		cph.generateHTML(files, tags);

		Home(req, resp);
	}

	private void Home(Request req, Response resp) {
		PrintStream out = resp.getPrintStream();
		out.println(cph.getHtml(PathFilesPAG.configMAC));
		out.flush();
	}

}
