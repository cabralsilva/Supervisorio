package WebService.extensao.impl.database;

import java.io.PrintStream;
import java.util.List;

import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Usuario;
import AGVS.Util.Util;
import WebService.extensao.CommandDB;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Methods;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class AdicionarAGV implements CommandDB {

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {

		// System.out.println(req.toString());

		PrintStream out = resp.getPrintStream();
		String html = "";
		if (req.getGetParams().containsKey(TagsValues.paramID)) {

			html = "Usuario não encontrado";

			html = "OK<form class='form-horizontal'>\n";
			html += "	<div class='form-group'>\n";
			html += "		<div class='col-md-6'>\n";

			List<AGV> agvs = ConfigProcess.bd().selecAGVS();
			for (int j = 0; agvs != null && j < agvs.size(); j++) {
				AGV agv = agvs.get(j);
				html += "<div class='checkbox-custom checkbox-primary'>";
				html += "<input type='checkbox' id='" + agv.getNome() + "' name='tokens'>";
				html += "<label for='" + agv.getNome() + "'>" + agv.getNome() + "</label>";
				html += "</div>";

			}

		}

		html += "	</div>\n";
		html += "		</div>\n";
		html += "</form>\n";
		html += "#" + req.getGetParams().get(TagsValues.paramID);

		out.println(html);

		out.flush();
		resp.flush();

	}

}
