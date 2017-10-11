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

public class SelectRotas implements CommandDB {

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {

		System.out.println(req.toString());

		PrintStream out = resp.getPrintStream();
		String html = "";

		if (req.getGetParams().containsKey(TagsValues.paramID)) {

			html = "OK<form class='form-horizontal'>\n";
			html += "	<div class='form-group'>\n";
			html += "		<div class='col-md-6'>\n";

			html += "<div class='col-md-12 form-group'>";
			html += "<label class='col-sm-3 control-label' for='inputSizingSmall'>AGV:</label>";
			html += "<input type='number' value='" + req.getParam(TagsValues.paramID)
					+ "' class='form-control' placeholder='AGV'>";
			html += "</div>";

			for (int i = 0; i < 10; i++) {
				html += "<div class='col-md-6 form-group'>";
				html += "<input type='number' id='r" + i + "' class='form-control' placeholder='Rota " + (i + 1) + "'>";
				html += "</div>";
				html += "<div class='col-md-6 form-group'>";
				html += "<input type='number' id='c" + i + "' class='form-control' placeholder='Cmd " + (i + 1) + "'>";
				html += "</div>";
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
