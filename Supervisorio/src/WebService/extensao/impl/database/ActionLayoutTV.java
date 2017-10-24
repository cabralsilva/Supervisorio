package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
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
import WebService.http.Config;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionLayoutTV implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "white   ";

		List<Line> lines = ConfigProcess.bd().selecLines();
		List<Tag> tags = ConfigProcess.bd().selecTags();
//		List<AGV> agvs = ConfigProcess.bd().selecAGVS();
		List<AGV> agvs = DatabaseStatic.lstAGVS;
		html += "<center>";
//		html += "<svg id = \"svg\"width=\"57.69vw\"height=\"30vw\"viewBox=\"-1 -1 1000 520\"preserveAspectRatio=\"xMidYMin slice\">";
		html += "<svg id = \"svg\"width=\"100%\"height=\"100%\"viewBox=\"-1 -1 1000 520\"preserveAspectRatio=\"xMidYMin slice\">";

		Config config = Config.getInstance();
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_TOYOTA_INDAIATUBA)) {

			html += "<text x=\"" + 5 + "\"y=\"" + 10 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Caixa Vazia (TKL)" + "</text>";
			html += "<text x=\"" + 160 + "\"y=\"" + 10 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Caixa Cheia (TKL)" + "</text>";
			html += "<text x=\"" + 700 + "\"y=\"" + 450
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Entrada (PCZone)"
					+ "</text>";
			html += "<text x=\"" + 700 + "\"y=\"" + 500
					+ "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M" + 1 + "\">" + "Saida (PCZone)"
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
			html += "<text x=\"" + 820 + "\"y=\"" + 100 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Cruzamento 4" + "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 120 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Cruzamento 5" + "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 140 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Cruzamento 6" + "</text>";
			html += "<text x=\"" + 820 + "\"y=\"" + 160 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Cruzamento 7" + "</text>";
			
			html += "<text x=\"" + 260 + "\"y=\"" + 200 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Area Externa" + "</text>";
			html += "<text x=\"" + 700 + "\"y=\"" + 350 + "\"dx=\"-7\"dy=\"6\"style=\"fill:bold; font-size: 16px\"id=\"M"
					+ 1 + "\">" + "Canopy" + "</text>";
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
			int r = 22;
			int codigo = t.getCodigo();
			String color = "green";
			for (int j = 0; agvs != null && j < agvs.size(); j++) {
				AGV a = agvs.get(j);
				if (a.getStatusTimeOld() < (new Date().getTime() - 1800000) ) {
					a.setStatus(AGV.statusEmRepouso);
				}
				String fontCor = "white";
				if (!a.getStatus().equals(AGV.statusManual)) {
					if (a.getTagAtual() != null && t.getEpc() != null && a.getTagAtual().equals(t.getEpc())) {
						if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_FIAT)) {
							
							switch (a.getStatus()) {
							case AGV.statusFugaRota:
							case AGV.statusEmergenciaRemota:
							case AGV.statusEmergencia:
								color = "red";
								fontCor = "black";
								break;
							case AGV.statusEmCruzamento:
							case AGV.statusEmFila:
							case AGV.statusObstaculo:
//								color = "rgba(128,0,128,0.8)";
								color = "yellow";
								fontCor = "black";
								break;
							case AGV.statusEmEspera:
//								color = "rgba(12,103,193,0.8)";
								color = "rgba(255,192,203,1)";
								fontCor = "black";
								break;
							case AGV.statusEmRepouso:
								color = "white";
								fontCor = "black";
								break;
							case AGV.statusManual:
								color = "rgba(12,103,193,0.8)";
							default:
								color = "rgba(0,255,0,0.7)";
								fontCor = "black";
								break;
							}
							
						}else {
							color = "green";
							if (a.getStatus().equals(AGV.statusParado)) {
								color = "blue";
							}

							if (a.getStatus().equals(AGV.statusFugaRota) || a.getStatus().equals(AGV.statusEmergencia)
									|| a.getStatus().equals(AGV.statusObstaculo)) {
								color = "red";
							}

							if (a.getAtraso() == 1) {
								color = "orange";
							}
						}

						

						codigo = a.getId();

						html += "<g class=\"cursor-rota\">";
						html += "<circle cx = \"" + t.getCoordenadaX() + "\"cy=\"" + t.getCoordenadaY() + "\"r=\"" + r
								+ "\"fill=\"" + color + "\" stroke=\"black\" stroke-width=\"2\" id=\"C" + t.getEpc() + "\"/>";
						html += "<text x=\"" + t.getCoordenadaX() + "\"y=\"" + t.getCoordenadaY()
								+ "\"dx=\"-12\"dy=\"6\"style=\"fill:"+ fontCor +"; font-size: 20px\"id=\"T" + t.getEpc() + "\">"
								+ codigo + "</text></g>";
					}
				}
			}

		}
		
//		<div class="col-md-3">test</div>

		html += "</svg></center>";

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
