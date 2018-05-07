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

public class ActionLogCruzamentos implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "        ";

		if (DatabaseStatic.cruzamentos != null) {
			String[] vet = new String[DatabaseStatic.cruzamentos.size()];

			for (int i = 0; i < DatabaseStatic.cruzamentos.size(); i++) {
				vet[i] = DatabaseStatic.cruzamentos.get(i).getNome();
			}

			Arrays.sort(vet);

			List<Cruzamento_OLD> cruzamentos = new ArrayList<Cruzamento_OLD>();
			for (int i = 0; i < vet.length; i++) {

				for (int j = 0; j < DatabaseStatic.cruzamentos.size(); j++) {
					if (DatabaseStatic.cruzamentos.get(j).getNome().equals(vet[i])) {
						cruzamentos.add(DatabaseStatic.cruzamentos.get(j));
					}
				}
			}

			DatabaseStatic.cruzamentos = cruzamentos;

		}

		for (

				int i = 0; DatabaseStatic.cruzamentos != null && i < DatabaseStatic.cruzamentos.size(); i++) {
			Cruzamento_OLD ct = DatabaseStatic.cruzamentos.get(i);
			html += "<div class='col-md-12'>";
			html += "<div class='panel'>";
			html += "<center><h3>Cruzamento: " + ct.getNome() + "</h3></center>";
			html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
			html += "<thead>";
			html += "<tr>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"enviarDados('/ActionCadastroCruzamentos?action=resetCruzamento&id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-dark\"><i class=\"icon wb-upload\" aria-hidden=\"true\"></i> Resetar Cruzamento</button></h3>";
			html += "</th>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"requestPopupCruzamentosAGVS('/AdicionarAGV?id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-success\"><i class=\"icon wb-plus\" aria-hidden=\"true\"></i> Adicionar AGV</button></h3>";
			html += "</th>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"requestPopupCruzamentosAGVSDel('/AdicionarAGV?id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-danger\"><i class=\"icon wb-minus\" aria-hidden=\"true\"></i> Remover AGV</button></h3>";
			html += "</th>";
			if (ct.getAgvInRota() != null) {
				html += "<p><h6>Em Rota: AGV " + ct.getAgvInRota().getId() + "</h6></p>";
			} else {
				html += "<p><h6>Em Rota: Sem Agv em rota" + "</h6></p>";
			}
			if (ct.getFilaEspera() == null || ct.getFilaEspera().size() == 0) {
				html += "<p><h6>Em fila de espera: Sem Agv em espera" + "</h6></p>";
			} else {
				html += "<p><h6>Fila de Espera: </h6></p>";
				for (int j = 0; ct.getFilaEspera() != null && j < ct.getFilaEspera().size(); j++) {
					html += "<p><h6>Em fila de espera Numero " + j + ": AGV " + ct.getFilaEspera().get(j).getId()
							+ "</h6></p>";
				}
			}
			html += "</tr>";
			html += "</th>";
			html += "</thead>";
			html += "</table>";
			html += "</div>";
			html += "</div>";
		}
		for (int i = 0; DatabaseStatic.cruzamentoMash != null && i < DatabaseStatic.cruzamentoMash.size(); i++) {

			CruzamentoMash ct = DatabaseStatic.cruzamentoMash.get(i);

			html += "<div class='col-md-12'>";
			html += "<div class='panel'>";
			html += "<center><h3>Cruzamento Mash: " + ct.getNome() + "</h3></center>";
			html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
			html += "<thead>";
			html += "<tr>";
			html += "<th>";

			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"enviarDados('/ActionCadastroCruzamentos?action=play2&id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-dark\"><i class=\"icon wb-upload\" aria-hidden=\"true\"></i> Resetar Cruzamento</button></h3>";
			html += "</th>";
			if (ct.isBloqC1()) {
				html += "<p><h6>Cruzamento Bloqueado Externamente" + "</h6></p>";
			}
			if (ct.getAgvInRota() == null || ct.getAgvInRota().size() == 0) {
				html += "<p><h6>Em rota: Sem Agv em rota" + "</h6></p>";
			} else {
				html += "<p><h6>Em rota: </h6></p>";
				for (int j = 0; ct.getAgvInRota() != null && j < ct.getAgvInRota().size(); j++) {
					html += "<p><h6>Em rota Numero " + j + ": AGV " + ct.getAgvInRota().get(j).getId() + "</h6></p>";
				}
			}
			if (ct.getFilaEspera() == null || ct.getFilaEspera().size() == 0) {
				html += "<p><h6>Em fila de espera: Sem Agv em espera" + "</h6></p>";
			} else {
				html += "<p><h6>Fila de Espera: </h6></p>";
				for (int j = 0; ct.getFilaEspera() != null && j < ct.getFilaEspera().size(); j++) {
					html += "<p><h6>Em fila de espera Numero " + j + ": AGV " + ct.getFilaEspera().get(j).getId()
							+ "</h6></p>";
				}
			}
			html += "</tr>";
			html += "</th>";
			html += "</thead>";
			html += "</table>";
			html += "</div>";
			html += "</div>";
		}

		for (int i = 0; DatabaseStatic.tagCruzamentoMash != null && i < DatabaseStatic.tagCruzamentoMash.size(); i++) {
			TagCruzamentoMash ct = DatabaseStatic.tagCruzamentoMash.get(i);
			html += "<div class='col-md-12'>";
			html += "<div class='panel'>";
			html += "<center><h3>Cruzamento AGV: " + ct.getNome() + "</h3></center>";
			html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
			html += "<thead>";
			html += "<tr>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"enviarDados('/ActionCadastroCruzamentos?action=play&id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-dark\"><i class=\"icon wb-upload\" aria-hidden=\"true\"></i> Resetar Cruzamento</button></h3>";
			html += "</th>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"requestPopupCruzamentosAGVS('/AdicionarAGV?id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-success\"><i class=\"icon wb-plus\" aria-hidden=\"true\"></i> Adicionar AGV</button></h3>";
			html += "</th>";
			if (ct.getAgvRota() == null || ct.getAgvRota().size() == 0) {
				html += "<p><h6>Em rota: Sem Agv em rota" + "</h6></p>";
			} else {
				html += "<p><h6>Em rota: </h6></p>";
				for (int j = 0; ct.getAgvRota() != null && j < ct.getAgvRota().size(); j++) {
					html += "<p><h6>Em rota Numero " + j + ": AGV " + ct.getAgvRota().get(j).getId() + "</h6></p>";
				}
			}
			html += "</tr>";
			html += "</th>";
			html += "</thead>";
			html += "</table>";
			html += "</div>";
			html += "</div>";
		}

		for (int i = 0; DatabaseStatic.cancelas != null && i < DatabaseStatic.cancelas.size(); i++) {
			Cancelas ct = DatabaseStatic.cancelas.get(i);
			html += "<div class='col-md-12'>";
			html += "<div class='panel'>";
			html += "<center><h3>Cancelas: " + ct.getNome() + "</h3></center>";
			html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
			html += "<thead>";
			html += "<tr>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"enviarDados('/ActionCadastroCruzamentos?action=playcl&id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-dark\"><i class=\"icon wb-upload\" aria-hidden=\"true\"></i> Resetar Cruzamento</button></h3>";
			html += "</th>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"enviarDados('/ActionCadastroCruzamentos?action=ativado&id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-success\"><i class=\"icon wb-play\" aria-hidden=\"true\"></i>Ativado</button></h3>";
			html += "</th>";
			html += "<th>";
			html += "<h3 class=\"panel-title\"><button type=\"button\" onclick=\"enviarDados('/ActionCadastroCruzamentos?action=desativado&id="
					+ ct.getNome()
					+ "');\" class=\"btn btn-danger\"><i class=\"icon wb-stop\" aria-hidden=\"true\"></i>Desativado</button></h3>";
			html += "</th>";
			if (ct.isAtivado()) {
				html += "<p><h6>Ativado" + "</h6></p>";
			} else {
				html += "<p><h6>Desativado" + "</h6></p>";
			}

			if (ct.isBloqC1()) {
				html += "<p><h6>Bloqueando AGVs" + "</h6></p>";
			} else {
				html += "<p><h6>Liberando AGVs" + "</h6></p>";
			}

			if (ct.getSinalACC1() == Cancelas.FECHADO) {
				html += "<p><h6>Cancela 1 Fechada" + "</h6></p>";
			} else {
				html += "<p><h6>Cancela 1 Aberta" + "</h6></p>";
			}

			if (ct.getSinalACC2() == Cancelas.FECHADO) {
				html += "<p><h6>Cancela 2 Fechada" + "</h6></p>";
			} else {
				html += "<p><h6>Cancela 2 Aberta" + "</h6></p>";
			}

			if (ct.getAgvInRota() == null || ct.getAgvInRota().size() == 0) {
				html += "<p><h6>Em rota: Sem Agv em rota" + "</h6></p>";
			} else {
				html += "<p><h6>Em rota: </h6></p>";
				for (int j = 0; ct.getAgvInRota() != null && j < ct.getAgvInRota().size(); j++) {
					html += "<p><h6>Em rota Numero " + j + ": AGV " + ct.getAgvInRota().get(j).getId() + "</h6></p>";
				}
			}
			html += "</tr>";
			html += "</th>";
			html += "</thead>";
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
