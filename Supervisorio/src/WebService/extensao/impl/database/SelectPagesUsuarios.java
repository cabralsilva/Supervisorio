package WebService.extensao.impl.database;

import java.io.PrintStream;

import AGVS.Data.Usuario;
import AGVS.Util.Util;
import WebService.extensao.CommandDB;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Methods;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class SelectPagesUsuarios implements CommandDB {

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {

		//System.out.println(req.toString());

		PrintStream out = resp.getPrintStream();
		String html = "";
		if (req.getGetParams().containsKey(TagsValues.paramID)) {

			Usuario usr = Login.getUsuario(req.getGetParams().get(TagsValues.paramID));
			html = "Usuario não encontrado";
			if (usr != null) {

				html = "OK<form class='form-horizontal'>\n";
				html += "	<div class='form-group'>\n";
				html += "		<div class='col-md-6'>\n";

				for (int i = 0; i < Login.listTokens.length; i++) {

					boolean noCheck = true;

					for (int j = 0; usr.getLiberado() != null && j < usr.getLiberado().split("#").length; j++) {
						String temp = Util.removerAcentos(new String(Login.listTokens[i].getBytes(), "UTF-8"));
						if (temp.equals(Util.removerAcentos(usr.getLiberado().split("#")[j]))) {
							noCheck = false;
						}
					}

					if (!noCheck) {
						html += "<div class='checkbox-custom checkbox-primary'>";
						html += "<input type='checkbox' id='" + Login.listTokens[i] + "' name='tokens' checked>";
						html += "<label for='" + Login.listTokens[i] + "'>" + Login.listTokens[i] + "</label>";
						html += "</div>";
					} else {
						html += "<div class='checkbox-custom checkbox-primary'>";
						html += "<input type='checkbox' id='" + Login.listTokens[i] + "' name='tokens'>";
						html += "<label for='" + Login.listTokens[i] + "'>" + Login.listTokens[i] + "</label>";
						html += "</div>";
					}
				}
			}

			html += "	</div>\n";
			html += "		</div>\n";
			html += "</form>\n";
			html += "#" + req.getGetParams().get(TagsValues.paramID);
		} else {
			html += "Não foi passado nenhum usuario para editar suas permissões";
		}
		out.println(html);

		out.flush();
		resp.flush();

	}

}
