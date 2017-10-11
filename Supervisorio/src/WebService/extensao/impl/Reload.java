package WebService.extensao.impl;

import java.io.PrintStream;

import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.http.Request;
import WebService.http.Response;

public class Reload {
	public static void reloadPage(String link, Response resp, boolean status) {
		System.out.println("HOST: " + link );
		Tags tags = new Tags();
		Methods mt = new Methods();
		tags.add(Keys.keyReload, mt.methodReload);
		mt.methodReload.setArgs(link);
		PrintStream out = resp.getPrintStream();
		ConvertPAGinHTML cph;
		cph = new ConvertPAGinHTML();
		String[] files = { PathFilesPAG.reload };
		
		cph.generateHTML(files, tags);
		out.println(cph.getHtml(PathFilesPAG.reload));
		out.flush();
	}
}
