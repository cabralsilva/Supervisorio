package WebService.extensao.impl;

import java.io.PrintStream;

import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.Command;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.http.Cookie;
import WebService.http.Request;
import WebService.http.Response;

public class SupervisorioAGVSLogin implements Command {

	private ConvertPAGinHTML cph;
	private String[] files = { PathFilesPAG.login };

	@Override
	public void execute(Request req, Response resp) throws Exception {
		// TODO Auto-generated method stub
		req.getCookies().clear();
		if (req.getPostParams() != null && req.getPostParams().size() > 0) {
			if (Login.login(req, resp, null)) {
				Login.redirectLoginValid(req, resp);

			} else {

				String[] files2 = { PathFilesPAG.loginErr };
				Tags tags = new Tags();
				cph = new ConvertPAGinHTML();
				cph.generateHTML(files2, tags);
				PrintStream out = resp.getPrintStream();
				out.println(cph.getHtml(PathFilesPAG.loginErr));
				out.flush();
			}

			return;
		}

		Login.resetLogin(req, resp);

		Tags tags = new Tags();
		cph = new ConvertPAGinHTML();
		cph.generateHTML(files, tags);

		Home(req, resp);
	}

	private void Home(Request req, Response resp) {
		PrintStream out = resp.getPrintStream();
		out.println(cph.getHtml(PathFilesPAG.login));
		out.flush();
	}

}
