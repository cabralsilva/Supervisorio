package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.Semaphore;

import AGVS.Data.AGV;
import AGVS.Data.AlertFalhas;
import AGVS.Data.ConfigProcess;
import AGVS.Util.Log;
import WebService.extensao.CommandDB;

import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionPopupFalha implements CommandDB {

	private static int id = 0;
	private static Semaphore sp = new Semaphore(1);

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "";
		try {
			sp.acquire();

			if (AlertFalhas.alerts.size() > 0) {
				html = "OK";
				html += "<html><p>" + AlertFalhas.alerts.get(0).getMsg() + "</p>";

				List<AGV> agvs = ConfigProcess.bd().selecAGVS();

				for (int i = 0; agvs != null && i < agvs.size(); i++) {
					AGV a = agvs.get(i);
					if (a.getId() == AlertFalhas.alerts.get(0).getId()) {
						html += "<center><div class='col-md-12'>";
						html += "<div class='panel'>";
						html += "<center><h3>" + a.getNome() + "</h3></center>";
						html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
						html += "<thead>";
						html += "<tr>";
						html += "<th>";
						html += "</th>";
						html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv.jpg' alt='...'></center></th>";
						html += "</tr>";
						html += "</thead>";
						html += "<tbody>";
						html += "<tr class='gradeA odd' role='row'>";
						html += "<td class='sorting_1'>ID:</td>";
						html += "<td class='sorting_1'>" + a.getId() + "</td>";
						html += "</tr>";
						html += "<tr class='gradeA odd' role='row'>";
						html += "<td class='sorting_1'>Bateria</td>";
						html += "<td class='sorting_1'>" + a.getBateria() + "%</td>";
						html += "</tr>";
						html += "<tr class='gradeA odd' role='row'>";
						html += "<td class='sorting_1'>Status</td>";
						html += "<td class='sorting_1'>" + a.getStatus() + "</td>";
						html += "</tr>";
						html += "<tr class='gradeA odd' role='row'>";
						html += "<td class='sorting_1'>Tag Atual:</td>";
						html += "<td class='sorting_1'>" + a.getTagAtual() + "</td>";
						html += "</tr>";
						html += "<tr class='gradeA odd' role='row'>";
						html += "<td class='sorting_1'></td>";
						html += "<td class='sorting_1'> ";
						html += "<center>";
						html += "<button type='button' onclick='alert(\"Desligar\")' class='btn btn-floating btn-danger btn-sm'><i class='icon wb-power' aria-hidden='true'></i></button>";
						html += "<button type='button' onclick='alert(\"Rota\")' class='btn btn-floating btn-success btn-sm'><i class='icon fa-road' aria-hidden='true'></i></button>";
						html += "</center>";
						html += "</td>";
						html += "</tr>";
						html += "</tbody>";
						html += "</table>";
						html += "</div>";
						html += "</div></center>";
					}

				}
				html += "</html>";
				AlertFalhas.alerts.remove(0);
			}

		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
