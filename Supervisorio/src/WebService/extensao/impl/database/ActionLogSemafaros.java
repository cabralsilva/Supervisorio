package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.AGV;
import AGVS.Data.Cancelas;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Cruzamento_OLD;
import AGVS.Data.CruzamentoMash;
import AGVS.Data.Line;
import AGVS.Data.Semaforo;
import AGVS.Data.Tag;
import AGVS.Data.TagCruzamentoMash;
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

public class ActionLogSemafaros implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "        ";

		if (DatabaseStatic.semafaros != null) {
			String[] vet = new String[DatabaseStatic.semafaros.size()];

			for (int i = 0; i < DatabaseStatic.semafaros.size(); i++) {
				vet[i] = DatabaseStatic.semafaros.get(i).getNome();
			}

			Arrays.sort(vet);

			List<Semaforo> semafaros = new ArrayList<Semaforo>();
			for (int i = 0; i < vet.length; i++) {

				for (int j = 0; j < DatabaseStatic.semafaros.size(); j++) {
					if (DatabaseStatic.semafaros.get(j).getNome().equals(vet[i])) {
						semafaros.add(DatabaseStatic.semafaros.get(j));
					}
				}
			}

			DatabaseStatic.semafaros = semafaros;

		}

		for (int i = 0; DatabaseStatic.semafaros != null && i < DatabaseStatic.semafaros.size(); i++) {
			Semaforo a = DatabaseStatic.semafaros.get(i);
			html += "<div class='col-md-4'>";
			html += "<div class='panel'>";
			html += "<center><h3>" + a.getNome() + "</h3></center>";
			html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
			html += "<thead>";
			html += "<tr>";
			html += "<th><center><img class='img-rounded' src='/TAS/images/semaf 2.png' alt='...'></center></th>";
			html += "</thead>";
			html += "</tr>";
			html += "<tbody>";
			html += "<tr class='gradeA odd' role='row'>";
			html += "<td class='sorting_1'>" + a.getStatus() + "</td>";
			html += "</td>";
			html += "</tr>";

			html += "<tr class='gradeA odd' role='row'>";
			html += "<td class='sorting_1'> ";
			html += "<center>";
			html += "<button type='button' onclick='BtnVermelho(\"" + a.getNome()
					+ "\")' class='btn btn-floating btn-danger btn-sm'><i class='icon ' aria-hidden='true'></i></button>";
			html += "<button type='button' onclick='BtnAmarelo(\"" + a.getNome()
					+ "\")' class='btn btn-floating btn-warning btn-sm'><i class='icon ' aria-hidden='true'></i></button>";
			html += "<button type='button' onclick='BtnVerde(\"" + a.getNome()
					+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon ' aria-hidden='true'></i></button>";
			html += "<button type='button' onclick='BtnDesligar(\"" + a.getNome()
					+ "\")' class='btn btn-floating btn-alert btn-sm'><i class='icon wb-power' aria-hidden='true'></i></button>";
			html += "<button type='button' onclick='BtnVermelhoA(\"" + a.getNome()
					+ "\")' class='btn btn-floating btn-danger btn-sm'><i class='icon wb-warning' aria-hidden='true'></i></button>";
			html += "<button type='button' onclick='BtnAmareloA(\"" + a.getNome()
					+ "\")' class='btn btn-floating btn-warning btn-sm'><i class='icon wb-warning' aria-hidden='true'></i></button>";
			html += "<button type='button' onclick='BtnVerdeA(\"" + a.getNome()
					+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon wb-warning' aria-hidden='true'></i></button>";

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
