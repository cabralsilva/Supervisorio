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
import WebService.http.Config;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionLayout implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "        ";

		List<Line> lines = ConfigProcess.bd().selecLines();
		List<Tag> tags = ConfigProcess.bd().selecTags();
		html += "<center>";
		html += "<svg id = \"svg\"width=\"1000\"height=\"520\"viewBox=\"-1 -1 1000 520\"preserveAspectRatio=\"xMidYMin slice\">";
		Config config = Config.getInstance();
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_TOYOTA_INDAIATUBA)) {

			html += "<text x=\"" + 5 + "\"y=\"" + 10 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Caixa Vazia (TKL)" + "</text>";
			html += "<text x=\"" + 160 + "\"y=\"" + 10 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Caixa Cheia (TKL)" + "</text>";
			html += "<text x=\"" + 700 + "\"y=\"" + 450
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Entrada (PNZone)"
					+ "</text>";
			html += "<text x=\"" + 700 + "\"y=\"" + 500
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Saida (PNZone)"
					+ "</text>";

			html += "<circle cx = \"" + 40 + "\"cy=\"" + 500 + "\"r=\"" + 10 + "\"fill=\"blue\"id=\"CV\"/>";
			html += "<text x=\"" + 65 + "\"y=\"" + 500 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Parado" + "</text>";
			html += "<circle cx = \"" + 125 + "\"cy=\"" + 500 + "\"r=\"" + 10 + "\"fill=\"red\"id=\"CV\"/>";
			html += "<text x=\"" + 150 + "\"y=\"" + 500
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Alerta"
					+ "</text>";
			html += "<circle cx = \"" + 205 + "\"cy=\"" + 500 + "\"r=\"" + 10 + "\"fill=\"orange\"id=\"CV\"/>";
			html += "<text x=\"" + 225 + "\"y=\"" + 500
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Atraso"
					+ "</text>";
			html += "<circle cx = \"" + 280 + "\"cy=\"" + 500 + "\"r=\"" + 10 + "\"fill=\"green\"id=\"CV\"/>";
			html += "<text x=\"" + 300 + "\"y=\"" + 500
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Rodando"
					+ "</text>";

			html += "<text x=\"" + 820 + "\"y=\"" + 40 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Cruzamento 1" + "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 60 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Cruzamento 2" + "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 80 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Cruzamento 3" + "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 100
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Cruzamento 4"
					+ "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 120
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Cruzamento 5"
					+ "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 140
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Cruzamento 6"
					+ "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 160
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Cruzamento 7"
					+ "</text>";

			html += "<text x=\"" + 260 + "\"y=\"" + 200
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Area Externa"
					+ "</text>";
			html += "<text x=\"" + 700 + "\"y=\"" + 350
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Canopy"
					+ "</text>";
		}
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
					+ "\"y2=\"" + l.getyFinal() + "\"style=\"stroke:" + rgb + ";stroke-width:3\"/>";
		}

		for (int i = 0; tags != null && i < tags.size(); i++) {
			Tag t = tags.get(i);
			int r = 11;
			int codigo = t.getCodigo();
			String color = "green";
			html += "<g class=\"cursor-rota\">";
			html += "<circle cx = \"" + t.getCoordenadaX() + "\"cy=\"" + t.getCoordenadaY() + "\"r=\"" + r + "\"fill=\""
					+ color + "\"id=\"C" + t.getEpc() + "\"/>";
			html += "<text x=\"" + t.getCoordenadaX() + "\"y=\"" + t.getCoordenadaY()
					+ "\"dx=\"-7\"dy=\"4\"style=\"fill:#FFF; font-size: 14px\"id=\"T" + t.getEpc() + "\">" + codigo
					+ "</text></g>";
		}

		html += "</svg></center>";

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
