package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFrame;

import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Line;
import AGVS.Data.Tag;
import AGVS.Serial.DatabaseStatic;
import AGVS.Util.Log;
import AGVS.Util.Util;
import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.Command;
import WebService.extensao.CommandDB;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionLayoutInicioTurno implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "        ";

		List<Line> lines = ConfigProcess.bd().selecLines();
		List<Tag> tags = ConfigProcess.bd().selecTags();
		html += "<center>";
		html += "<svg id = \"svg\"width=\"1000\"height=\"520\"viewBox=\"-1 -1 1000 520\"preserveAspectRatio=\"xMidYMin slice\">";

		for (int i = 0; lines != null && i < lines.size(); i++) {
			Line l = lines.get(i);
			String rgb = "rgb(0,0,0)";
			if (l.getCor().equals(Line.corVermelho)) {
				rgb = "rgb(255,0,0)";
			} else if (l.getCor().equals(Line.corVerde)) {
				rgb = "rgb(0,255,0)";
			} else if (l.getCor().equals(Line.corAzul)) {
				rgb = "rgb(0,0,255)";
			} else if (l.getCor().equals(Line.corAmarelo)) {
				rgb = "rgb(218,165,32)";
			} else if (l.getCor().equals(Line.corAzulClaro)) {
				rgb = "rgb(0,255,255)";
			} else if (l.getCor().equals(Line.corCinza)) {
				rgb = "rgb(128,128,128)";
			} else if (l.getCor().equals(Line.corMarrom)) {
				rgb = "rgb(128,128,0)";
			}
			html += "<line x1 = \"" + l.getxInicial() + "\"y1=\"" + l.getyInicial() + "\"x2=\"" + l.getxFinal()
					+ "\"y2=\"" + l.getyFinal() + "\"style=\"stroke:" + rgb + ";stroke-width:2\"/>";
		}

		for (int i = 0; tags != null && i < tags.size(); i++) {

			boolean ok = false;
			Tag t = tags.get(i);
			String color = "green";
			String codigo = "";
			String nome = "";
			for (int j = 0; DatabaseStatic.pits != null && j < DatabaseStatic.pits.size(); j++) {
				// System.out.println(DatabaseStatic.pits.get(j).getTag().getEpc());
				if (DatabaseStatic.pits.get(j).getTag().getEpc().equals(t.getEpc())) {
					ok = true;
					if (DatabaseStatic.pits.get(j).getAgv() != null) {
						color = "blue";
						codigo = "" + DatabaseStatic.pits.get(j).getAgv().getId();
					} else {
						color = "green";
						codigo = DatabaseStatic.pits.get(j).getNome();
					}
					nome = DatabaseStatic.pits.get(j).getNome();
					break;
				}
			}
			if (ok) {
				int r = 14;
				html += "<a class=\"btn btn-sm btn-default btn-outline btn-round\" "
						+ "onclick=\"requestPopupAddAGVInicioTurno('/AdicionarAGV?id=" + nome + "');\"" + " >";
				html += "<g class=\"cursor-rota\">";
				html += "<circle cx = \"" + t.getCoordenadaX() + "\"cy=\"" + t.getCoordenadaY() + "\"r=\"" + r
						+ "\"fill=\"" + color + "\"id=\"C" + t.getEpc() + "\"/>";
				html += "<text x=\"" + t.getCoordenadaX() + "\"y=\"" + t.getCoordenadaY()
						+ "\"dx=\"-7\"dy=\"4\"style=\"fill:#FFF; font-size: 14px\"id=\"T" + t.getEpc() + "\">" + codigo
						+ "</text></g>";
				html += "</a>";
			}
		}

		html += "</svg></center>";

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
