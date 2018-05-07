package WebService.extensao.impl.database;

import java.io.PrintStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.LogZoneTime;
import AGVS.Data.Tag;
import AGVS.Serial.DatabaseStatic;
import AGVS.Util.Util;
import WebService.extensao.CommandDB;
import WebService.http.Config;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionAGVHome implements CommandDB {

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "white   ";

		List<AGV> agvs = ConfigProcess.bd().selecAGVS();

		Config config = Config.getInstance();
		for (int i = 0; agvs != null && i < agvs.size(); i++) {
			AGV a = agvs.get(i);
			html += "<div class='col-md-4' style='width: 32%;>";
			html += "<div class='panel'>";
			html += "<center><h3>" + a.getNome() + "</h3></center>";
			html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
			html += "<thead>";
			html += "<tr>";
			html += "<th>";

			if ( a.getStatus().equals(AGV.statusObstaculo) || a.getStatus().equals(AGV.statusFugaRota)) {

				html += "<button type='button' class='btn btn-floating btn-danger'><i class='icon wb-warning' aria-hidden='true'></i></button>";

			}

			html += "</th>";
			if (a.getTipo().equals(AGV.tipoRebocadorJacto)) {
				html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv-Jacto.jpg' alt='...'></center></th>";
			} else if (a.getTipo().equals(AGV.tipoRebocadorToyota)) {
				html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv-toyota.jpg' alt='...'></center></th>";
			} else if (a.getTipo().equals(AGV.tipoCarregador)) {
				html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv-carregador.jpg' alt='...'></center></th>";
			} else {
				html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv-rebocador.jpg' alt='...'></center></th>";
			}

			html += "</thead>";
			html += "</tr>";
			html += "<tbody>";
			html += "<tr class='gradeA odd' role='row'>";
			html += "<td style='font-size: 11px;font-weight: bold;'>ID:</td>";
			html += "<td class='sorting_1'>" + a.getId() + "</td>";
			html += "</tr>";
			/*
			 * html += "<tr class='gradeA odd' role='row'>"; html +=
			 * "<td class='sorting_1'>Bateria</td>"; html += "<td class='sorting_1'>" +
			 * a.getBateria() + "%</td>"; html += "</tr>";
			 */
			html += "<tr class='gradeA odd' role='row'>";
			html += "<td style='font-size: 11px;font-weight: bold;'>Status</td>";
			html += "<td class='sorting_1'>" + a.getStatus() + "</td>";
			html += "</tr>";
			html += "<tr class='gradeA odd' role='row'>";
			html += "<td style='font-size: 11px;font-weight: bold;'>Tag Atual EPC:</td>";
			html += "<td class='sorting_1'>" + a.getTagAtual() + "</td>";
			html += "</tr>";

			if (a.getStatusOld() != null) {
				html += "<tr class='gradeA odd' role='row'>";
				html += "<td style='font-size: 11px;font-weight: bold;'>" + a.getStatusOld() + "</td>";
				html += "<td style='font-size: 13px;'>" + Util.getDateTimeFormatoBR(a.getStatusTimeOld()) + "</td>";
				html += "</tr>";
			} else {
				html += "<tr class='gradeA odd' role='row'>";
				html += "<td style='font-size: 11px;font-weight: bold;'>Sem Historico de Erro</td>";
				html += "<td class='sorting_1'>-</td>";
				html += "</tr>";
			}

			List<Tag> tg = null;

			if (a.getTagAtual() != null) {
				tg = ConfigProcess.bd().selecTags(a.getTagAtual());
			}

			if (tg != null && tg.size() > 0) {
				html += "<tr class='gradeA odd' role='row'>";
				html += "<td style='font-size: 11px;font-weight: bold;'>Tag Atual Nome:</td>";
				html += "<td class='sorting_1'>" + tg.get(0).getNome() + "</td>";
				html += "</tr>";
			} else {
				html += "<tr class='gradeA odd' role='row'>";
				html += "<td style='font-size: 11px;font-weight: bold;'>Tag Atual Nome:</td>";
				html += "<td class='sorting_1'>Tag Sem Cadastro</td>";
				html += "</tr>";
			}

			html += "<tr class='gradeA odd' role='row'>";
			html += "<td style='font-size: 11px;font-weight: bold;'><center>"
					+ "<button type='button' onclick='BtnZerarZoneTime(" + a.getId() + ")' class='btn btn-floating btn-timer btn-sm'>"
						+ "<i class='fa fa-clock-o' aria-hidden='true'></i></button></center></td>";
			html += "<td class='sorting_1'> ";
			html += "<center>";
			// Config config = Config.getInstance();

			html += "<button type='button' onclick='BtnEmergencia(\"" + a.getId()
					+ "\")' class='btn btn-floating btn-danger btn-sm'><i class='icon wb-power' aria-hidden='true'></i></button>";

			if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
				html += "<button type='button' onclick='requestPopupRotas(\"/SelectRotas?id=" + a.getId()
						+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon fa-road' aria-hidden='true'></i></button>";

				html += "<button type='button' onclick='BtnPlayRe(\"" + a.getId()
						+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon fa-step-backward' aria-hidden='true'></i></button>";

				html += "<button type='button' onclick='BtnPlay(\"" + a.getId()
						+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon fa-step-forward' aria-hidden='true'></i></button>";

			} else {
				html += "<button type='button' onclick='BtnPlay(\"" + a.getId()
						+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon wb-play' aria-hidden='true'></i></button>";

			}

			html += "<button type='button' onclick='BtnParar(\"" + a.getId()
					+ "\")' class='btn btn-floating btn-danger btn-sm'><i class='icon wb-stop' aria-hidden='true'></i></button>";

			html += "</center>";
			html += "</td>";
			html += "</tr>";
			html += "</tbody>";
			html += "</table>";
			html += "</div>";
			html += "</div>";

		}
		

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}
}
