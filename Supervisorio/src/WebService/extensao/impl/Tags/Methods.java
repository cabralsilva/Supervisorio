package WebService.extensao.impl.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import AGVS.Controle.Goodyear.Pedido;
import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.AGV;
import AGVS.Data.AlertFalhas;
import AGVS.Data.Condicao;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Cruzamento;
import AGVS.Data.EntradaCondicao;
import AGVS.Data.Equipamentos;
import AGVS.Data.Line;
import AGVS.Data.LogTags;
import AGVS.Data.LogUsuario;
import AGVS.Data.LogZoneTime;
import AGVS.Data.MeshSerial;
import AGVS.Data.Rota;
import AGVS.Data.Semaforo;
import AGVS.Data.Supermercado;
import AGVS.Data.Tag;
import AGVS.Data.TagAtraso;
import AGVS.Data.TagCruzamento;
import AGVS.Data.TagSemaforos;
import AGVS.Data.TagsRotas;
import AGVS.Data.Usuario;
import AGVS.Data.ZoneTime;
import AGVS.Util.Util;
import WebService.HTML.Dashboards;
import WebService.HTML.ProcessingMethod;
import WebService.extensao.impl.Login.Login;

public class Methods {

	public ProcessingMethod methodReload = new ProcessingMethod() {

		private String msg = "/";

		public String method() {
			return msg;
		}

		@Override
		public void setArgs(String args) {
			this.msg = args;
		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroEquipamentos = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroEquipamentos, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGerarTabelaEquipamentos = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Equipamentos> data = ConfigProcess.bd().selectEquipamentos();
			for (int i = 0; data != null && i < data.size(); i++) {
				Equipamentos value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getNome() + "</td>" + "<td>" + value.getTipo() + "</td>"
						+ "<td>" + value.getRota() + "</td>" + "<td>" + value.getId() + "</td>"
						+ "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaSupermercados = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Supermercado> data = ConfigProcess.bd().selectSupermercados();
			for (int i = 0; data != null && i < data.size(); i++) {
				Supermercado value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getNome() + "</td>" + "<td>" + value.getId() + "</td>"
						+ "<td>" + value.getProduto() + "</td>" + "<td>" + Util.getDateTimeFormatoBR(value.getData())
						+ "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroSupermercados = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroSupermercados, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardHome = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(null, null, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardLogsCruzamentos = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.LogCruzamentos, dash.LogSistema, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardLogsSemafaros = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.LogSemafaros, dash.LogSistema, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroAGVS = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroAGVS, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};
	
	public ProcessingMethod methodGenerateDashboardCadastroZoneTime = new ProcessingMethod() {
		
		@Override
		public String method() {
			
			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroZoneTime, dash.Cadastro, -1);
			return msg;
		}
		
		@Override
		public void setArgs(String args) {
			
		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroMesh = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroMesh, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroSemaforo = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroSemaforo, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroTagsCruzamento = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroTagsCruzamentos, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroTagsSemaforo = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroTagsSemaforos, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroCruzamentos = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroCruzamentos, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardPedidos = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.Pedidos, dash.Turno, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroLayou = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroLayout, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardOperadorTurno = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.Rastrear, dash.Turno, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};
	
	public ProcessingMethod methodGenerateDashboardManutencaoRastremento = new ProcessingMethod() {
		
		@Override
		public String method() {
			
			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.InicioTurno, dash.Turno, -1);
			return msg;
		}
		
		@Override
		public void setArgs(String args) {
			
		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroRotas = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroRotas, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardCadastroTags = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.cadastroTags, dash.Cadastro, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateNomeUsuario = new ProcessingMethod() {

		String nomeUsuario = "";

		public String method() {

			return nomeUsuario;
		}

		@Override
		public void setArgs(String args) {
			nomeUsuario = args;
		}
	};

	public ProcessingMethod methodGenerateNomeRota = new ProcessingMethod() {

		String nomeRota = "";

		public String method() {

			return nomeRota;
		}

		@Override
		public void setArgs(String args) {
			nomeRota = args;
		}
	};

	public ProcessingMethod methodGenerateDashboardUsuarios = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(null, dash.Usuarios, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerataComboxPermissaoUsuarios = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < RulesUsuarios.permissao.length; i++) {
				String value = RulesUsuarios.permissao[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGenerataTableUsuarios = new ProcessingMethod() {

		private String xml = "";

		@Override
		public String method() {

			String msg = "";
			String lg = Util.localizarStrXML(xml, "<login>", "</login>");
			String pw = Util.localizarStrXML(xml, "<password>", "</password>");
			Usuario usr = Login.getUsuario(lg, pw);
			System.out.println(xml);
			List<Usuario> usuarios = ConfigProcess.bd().selectUsuarios();
			for (int i = 0; usuarios != null && i < usuarios.size(); i++) {
				Usuario am = usuarios.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>";
				msg += am.getLogin() + "</td>" + "<td>" + "</td>" + "<td>" + am.getNome() + "</td>" + "<td>";
				msg += am.getEmail() + "</td>" + "<td>" + am.getPermissao() + "</td>" + "<td class=\"actions\">";
				if (pw.equals(am.getPassword()) && lg.equals(am.getLogin())
						|| (usr != null && usr.getPermissao().equals(RulesUsuarios.permissaoAdministrador))) {
					msg += "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\"";
					msg += "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>";
					msg += "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\"";
					msg += "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>";
					msg += "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\"";
					msg += "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
							+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
							+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>";
					if (am.getPermissao().equals(RulesUsuarios.permissaoPersonalizado)
							&& usr.getPermissao().equals(RulesUsuarios.permissaoAdministrador)) {
						msg += "<a onclick=\"requestPopupUsers('/SelectPagesUsuarios?id=" + am.getLogin()
								+ "');\" href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default\" "
								+ "data-toggle=\"tooltip\" data-original-title=\"Permiss�o\"><i class=\"icon wb-unlock\" aria-hidden=\"true\"></i></a>";
					}
				}
				msg += "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			xml = args;

		}
	};

	public ProcessingMethod methodGerarTabelaRelatorioEventoAGVS = new ProcessingMethod() {

		@Override
		public String method() {

			String html = "";
			List<AlertFalhas> log = ConfigProcess.bd().selecFalhas();
			int j = 0;
			for (int i = log.size() - 1; log != null && i > -1 && j < 1000; i--) {
				j++;
				AlertFalhas lg = log.get(i);

				html += "<tr>\n" 
						+ "<th>" + lg.getId() + "</th>\n" 
						+ "<th>" + Util.getDateTimeFormatoBR(lg.getData())+ "</th>\n" 
						+ "<th>" + lg.getMsg() + "</th>\n" 
						+ " </tr>\n";

			}
			return html;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGerarTabelaLogTags = new ProcessingMethod() {

		@Override
		public String method() {

			String html = "";
			List<LogTags> log = ConfigProcess.bd().selectLogTags();
			int j = 0;
			if (log != null)
				for (int i = log.size() - 1; log != null && i > -1 && j < 1000; i--) {
					j++;
					LogTags lg = log.get(i);

					html += "<tr>\n" + "<th>" + lg.getId() + "</th>\n" + "<th>" + lg.getIdAgv() + "</th>\n" + "<th>"
							+ Util.getDateTimeFormatoBR(lg.getData()) + "</th>\n" + "<th>" + lg.getMsg() + "</th>\n"
							+ "<th>" + lg.getEpc() + "</th>\n" + " </tr>\n";

				}
			return html;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerataTableLogUsuarios = new ProcessingMethod() {

		@Override
		public String method() {

			String html = "";
			List<LogUsuario> log = ConfigProcess.bd().selectLogUsuarios();
			for (int i = 0; log != null && i < log.size(); i++) {
				LogUsuario lg = log.get(i);

				html += "<tr>\n" + "<th>" + lg.getData() + "</th>\n" + "<th>" + lg.getNome() + "</th>\n" + "<th>"
						+ lg.getTipo() + "</th>\n" + "<th>" + lg.getDescricao() + "</th>\n" + " </tr>\n";

			}
			return html;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardLogUsuarios = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.LogUsuarios, dash.LogSistema, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardLogTags = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.LogTags, dash.Relatorios, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardRelatorioEventosAGVS = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.RelatoriosEventosAGVS, dash.Relatorios, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};
	
	public ProcessingMethod methodGenerateDashboardRelatorioZoneTime = new ProcessingMethod() {
		
		@Override
		public String method() {
			
			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.RelatorioZoneTime, dash.Relatorios, -1);
			return msg;
		}
		
		@Override
		public void setArgs(String args) {
			
		}
	};

	public ProcessingMethod methodGerarEndBanco = new ProcessingMethod() {

		@Override
		public String method() {

			return Util.getXml("instancia");
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGerarUsrBanco = new ProcessingMethod() {

		@Override
		public String method() {

			return Util.getXml("user");
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGerarSenhaBanco = new ProcessingMethod() {

		@Override
		public String method() {

			return Util.getXml("pwd");
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodGenerateDashboardConfigBancoDados = new ProcessingMethod() {

		@Override
		public String method() {

			Dashboards dash = new Dashboards();
			String msg = dash.gerarDashBoard(dash.configuracaoBancoDados, dash.Configuracao, -1);
			return msg;
		}

		@Override
		public void setArgs(String args) {

		}
	};

	public ProcessingMethod methodListTipoAGVS = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < AGV.tiposAGV.length; i++) {
				String value = AGV.tiposAGV[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodListStatusAGVS = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < AGV.statusAGV.length; i++) {
				String value = AGV.statusAGV[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaMesh = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<MeshSerial> data = ConfigProcess.bd().selectMeshSerial();
			for (int i = 0; data != null && i < data.size(); i++) {
				MeshSerial value = data.get(i);
				msg += "<tr class=\"gradeA\">" 
						+ "<td>" + value.getId() + "</td>" 
						+ "<td>" + value.getNome() + "</td>"
						+ "<td>" + value.getMac16() + "</td>" 
						+ "<td>" + value.getMac64() + "</td>"
						+ "<td>" + value.getNumeroEntradas() + "</td>"
						+ "<td>" + value.getNumeroSaidas() + "</td>"
						+ "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaSemaforo = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Semaforo> data = ConfigProcess.bd().selectSemaforo();
			// System.out.println(data);
			for (int i = 0; data != null && i < data.size(); i++) {
				Semaforo value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getId() + "</td>" + "<td>" + value.getNome() + "</td>"
						+ "<td>" + value.getMs().getNome() + "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaZoneTime = new ProcessingMethod() {
		
		@Override
		public String method() {
			
			String msg = "";
			List<ZoneTime> data = ConfigProcess.bd().selectZoneTime();
			for (int i = 0; data != null && i < data.size(); i++) {
				ZoneTime value = data.get(i);
				msg += "<tr class=\"gradeA\">" 
						+ "<td>" + value.getId() + "</td>" 
						+ "<td>" + value.getDescricao() + "</td>"
						+ "<td>" + value.getTagStart().getNome() + "</td>" 
						+ "<td>" + value.getTagEnd().getNome() + "</td>" 
						+ "<td>" + value.getLimiteTempoByString() + "</td>" 
						
						+ "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}
			
			return msg;
		}
		
		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub
			
		}
	};

	public ProcessingMethod methodListMesh = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<MeshSerial> data = ConfigProcess.bd().selectMeshSerial();
			for (int i = 0; data != null && i < data.size(); i++) {
				MeshSerial value = data.get(i);
				msg += "<option value=\"" + value.getId() + "\">" + value.getNome() + "</option>";
			}
			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaAGVS = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<AGV> data = ConfigProcess.bd().selecAGVS();
			for (int i = 0; data != null && i < data.size(); i++) {
				AGV value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getId() + "</td>" + "<td>" + value.getNome() + "</td>"
						+ "<td>" + value.getStatus() + "</td>" + "<td>" + value.getTipo() + "</td>" + "<td>"
						+ value.getMac64() + "</td>" + "<td>" + value.getMac16() + "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaTempoTags = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<TagAtraso> data = ConfigProcess.bd().selectTagAtraso();
			for (int i = 0; data != null && i < data.size(); i++) {
				TagAtraso value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getNome() + "</td>" + "<td>" + value.getTag().getNome()
						+ "</td>" + "<td>" + value.getTime() + "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaTagsCruzamento = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<TagCruzamento> data = ConfigProcess.bd().selectTagCruzamento();
			for (int i = 0; data != null && i < data.size(); i++) {
				TagCruzamento value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getNome() + "</td>" + "<td>"
						+ value.getCruzamento().getNome() + "</td>" + "<td>" + value.getTag().getNome() + "</td>"
						+ "<td>" + value.getTipo() + "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTableCruzamento = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Cruzamento> data = ConfigProcess.bd().selectCruzamentos();
			for (int i = 0; data != null && i < data.size(); i++) {
				Cruzamento value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getNome() + "</td>" + "<td>" + value.getDescricao()
						+ "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default libera-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Liberar\"><i class=\"icon wb-unlock\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTablePedidos = new ProcessingMethod() {

		@Override
		public String method() {
			System.err.println("PEDIDOS ????");
			String msg = "";
			List<Pedido> data = ConfigProcess.xmlControleGoodyear.getPedidos();
			for (int i = 0; data != null && i < data.size(); i++) {
				Pedido value = data.get(i);
				String status = "Em espera";
				String agv = "-";
				if (value.getAgv() != null) {
					status = "Executando o pedido";
					agv = value.getAgv().getNome();
				}
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getId() + "</td>" + "<td>" + status + "</td>" + "<td>";
				msg += agv + "</td>" + "<td>" + value.getIdProduto() + "</td>" + "<td>" + value.getIdEquipamento();
				msg += "</td>" + "<td>" + value.getTipo() + "</td>" + "<td>" + value.getRetorno() + "</td>" + "<td>";
				msg += value.getLog() + "</td>" + "<td class=\"actions\">";
				if (value.getAgv() != null) {
					msg += "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default finaliza-row\"";
					msg += "data-toggle=\"tooltip\" data-original-title=\"Finalizar\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>";
				}
				msg += "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" ";
				msg += "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>";
				msg += "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodListCor = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < Line.cores.length; i++) {
				String value = Line.cores[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodListAGVs = new ProcessingMethod() {
		
		@Override
		public String method() {
			
			String msg = "";
			List<AGV> listAGV = ConfigProcess.bd().selecAGVS();
			Collections.sort(listAGV);
			for(AGV agv : listAGV) {
				msg += "<option value=\"" + agv.getId() + "\">" + agv.getNome() + "</option>";
			}
			
			return msg;
		}
		
		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub
			
		}
	};
	public ProcessingMethod methodListZoneTime = new ProcessingMethod() {
		
		@Override
		public String method() {
			
			String msg = "";
			List<ZoneTime> listZT = ConfigProcess.bd().selectZoneTime();
			for(ZoneTime zt : listZT) {
				msg += "<option value=\"" + zt.getId() + "\">" + zt.getDescricao() + "</option>";
			}
			
			return msg;
		}
		
		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub
			
		}
	};

	public ProcessingMethod methodGerarTabelaLines = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Line> data = ConfigProcess.bd().selecLines();
			for (int i = 0; data != null && i < data.size(); i++) {
				Line value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getDescricao() + "</td>" + "<td>" + value.getxInicial()
						+ "</td>" + "<td>" + value.getyInicial() + "</td>" + "<td>" + value.getxFinal() + "</td>"
						+ "<td>" + value.getyFinal() + "</td>" + "<td>" + value.getCor() + "</td>"
						+ "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaTags = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Tag> data = ConfigProcess.bd().selecTags();
			for (int i = 0; data != null && i < data.size(); i++) {
				Tag value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getEpc() + "</td>" + "<td>" + value.getNome() + "</td>"
						+ "<td>" + value.getCodigo() + "</td>" + "<td>" + value.getCoordenadaX() + "</td>" + "<td>"
						+ value.getCoordenadaY() + "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaTagsRotas = new ProcessingMethod() {

		private String rota = null;

		@Override
		public String method() {

			String msg = "";
			List<TagsRotas> data;
			if (rota == null) {
				data = ConfigProcess.bd().selectTagsRotas();
			} else {
				data = ConfigProcess.bd().selectTagsRotas(rota);
			}
			String dest = "Ativado";
			String parada = "Ativado";
			String pit = "Ativado";

			for (int i = 0; data != null && i < data.size(); i++) {
				TagsRotas value = data.get(i);
				if (value.getTagDestino() == 0) {
					dest = "Desativado";
				}
				if (value.getTagParada() == 0) {
					parada = "Desativado";
				}
				if (value.getPitStop() == 0) {
					pit = "Desativado";
				}
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getNome() + "</td>" + "<td>" + value.getPosicao()
						+ "</td>" + "<td>" + value.getRota().getNome() + "</td>" + "<td>" + value.getTag().getNome()
						+ "</td>" + "<td>" + value.getSetPoint() + "</td>" + "<td>" + value.getVelocidade() + "</td>"
						+ "<td>" + value.getTemporizador() + "</td>" + "<td>" + value.getGirar() + "</td>" + "<td>"
						+ value.getEstadoAtuador() + "</td>" + "<td>" + value.getSensorObstaculo() + "</td>" + "<td>"
						+ value.getSinalSonoro() + "</td>" + "<td>" + dest + "</td>" + "<td>" + parada + "</td>"
						+ "<td>" + pit + "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			rota = args;
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaRotas = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Rota> data = ConfigProcess.bd().selectRotas();
			for (int i = 0; data != null && i < data.size(); i++) {
				Rota value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getNome() + "</td>" + "<td>" + value.getDescricao()
						+ "</td>" + "<td class=\"actions\">" + "<a href=\"/SupervisorioAGVS/Cadastro/Rotas/Tags?nome="
						+ value.getNome() + "\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Adicionar Tags\"><i class=\"icon wb-tag\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxCruzamento = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Cruzamento> values = ConfigProcess.bd().selectCruzamentos();
			for (int i = 0; values != null && i < values.size(); i++) {
				Cruzamento value = values.get(i);
				msg += "<option value=\"" + value.getNome() + "\">" + value.getNome() + "</option>";
			}
			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxTags = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";

			List<Tag> values = ConfigProcess.bd().selecTags();

			if (values != null) {
				String[] vet = new String[values.size()];

				for (int i = 0; i < values.size(); i++) {
					vet[i] = values.get(i).getNome();
				}

				Arrays.sort(vet);

				List<Tag> va = new ArrayList<Tag>();
				for (int i = 0; i < vet.length; i++) {

					for (int j = 0; j < values.size(); j++) {
						if (values.get(j).getNome().equals(vet[i])) {
							va.add(values.get(j));
						}
					}
				}

				values = va;

			}

			for (int i = 0; values != null && i < values.size(); i++) {
				Tag value = values.get(i);
				msg += "<option value=\"" + value.getEpc() + "\">" + value.getNome() + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxTiposTagsCruzamento = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";

			String[] values = TagCruzamento.tipos;
			for (int i = 0; values != null && i < values.length; i++) {
				String value = values[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxRotas = new ProcessingMethod() {

		@Override
		public String method() {
			String msg = "";
			List<Rota> values = ConfigProcess.bd().selectRotas();
			msg += "<option value=\"null\">Desativado</option>";
			for (int i = 0; values != null && i < values.size(); i++) {
				Rota value = values.get(i);
				msg += "<option value=\"" + value.getNome() + "\">" + value.getNome() + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub
		}
	};

	public ProcessingMethod methodGerarComboboxVelocidades = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < TagsRotas.velocidades.length; i++) {
				int value = TagsRotas.velocidades[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxSetPoints = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < TagsRotas.setPoints.length; i++) {
				int value = TagsRotas.setPoints[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxGirar = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < TagsRotas.setGirar.length; i++) {
				String value = TagsRotas.setGirar[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxTagDestino = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			Iterator entries = TagsRotas.listTagDestino().entrySet().iterator();
			while (entries.hasNext()) {
				Entry thisEntry = (Entry) entries.next();
				Object key = thisEntry.getKey();
				Object value = thisEntry.getValue();
				msg += "<option value=\"" + value + "\">" + key + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxTagParada = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			Iterator entries = TagsRotas.listTagParada().entrySet().iterator();
			while (entries.hasNext()) {
				Entry thisEntry = (Entry) entries.next();
				Object key = thisEntry.getKey();
				Object value = thisEntry.getValue();
				msg += "<option value=\"" + value + "\">" + key + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxEstadoAtuador = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < TagsRotas.estAtuador.length; i++) {
				String value = TagsRotas.estAtuador[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxSinalSonoro = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < TagsRotas.sinalSonoros.length; i++) {
				String value = TagsRotas.sinalSonoros[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxSensorObstaculo = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			for (int i = 0; i < TagsRotas.sensObs.length; i++) {
				String value = TagsRotas.sensObs[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxTagPitStop = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			Iterator entries = TagsRotas.listTagPitStop().entrySet().iterator();
			while (entries.hasNext()) {
				Entry thisEntry = (Entry) entries.next();
				Object key = thisEntry.getKey();
				Object value = thisEntry.getValue();
				msg += "<option value=\"" + value + "\">" + key + "</option>";

			}
			System.out.println("CHECKBOX = " + msg);
			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxTiposTagsSemaforo = new ProcessingMethod() {

		@Override
		public String method() {
			String msg = "";
			String[] values = TagSemaforos.tipos;
			System.out.println(values);
			for (int i = 0; values != null && i < values.length; i++) {
				String value = values[i];
				msg += "<option value=\"" + value + "\">" + value + "</option>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarComboboxSemaforo = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<Semaforo> values = ConfigProcess.bd().selectSemaforo();
			for (int i = 0; values != null && i < values.size(); i++) {
				Semaforo value = values.get(i);
				msg += "<option value=\"" + value.getId() + "\">" + value.getNome() + "</option>";
			}
			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarTabelaTagsSemaforo = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			List<TagSemaforos> data = ConfigProcess.bd().selectTagsSemaforo();
			for (int i = 0; data != null && i < data.size(); i++) {
				TagSemaforos value = data.get(i);
				msg += "<tr class=\"gradeA\">" + "<td>" + value.getId() + "</td>" + "<td>" + value.getNome() + "</td>"
						+ "<td>" + value.getSemaforo().getNome() + "</td>" + "<td>" + value.getTag().getNome() + "</td>"
						+ "<td>" + value.getTipo() + "</td>" + "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}
			System.out.println(msg);
			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};

	public ProcessingMethod methodGerarRelatorioZoneTime = new ProcessingMethod() {
		
		@Override
		public String method() {
			
			String msg = "";
			List<LogZoneTime> data = ConfigProcess.bd().selectLogZoneTime();
			for (int i = 0; data != null && i < data.size(); i++) {
				LogZoneTime value = data.get(i);
				msg += "<tr>" 
						+ "<th>" + (value.getZoneTime().getDescricao() == null ? "Sem Zona":value.getZoneTime().getDescricao()) + "</th>" 
						+ "<th>" + value.getAgv().getNome() + "</th>"
						+ "<th>" + value.getTimeRoute() + "</th>" 
						+ "<th>" + value.getsTimeLost() + "</th>"
						+ "<th>" + Util.getDurationConvertString(value.getTimeLostObstacle()) + "</th>" 
						+ "<th>" + Util.getDateTimeFormatoBR(value.getData().getTime()) + "</th>" 
					+ "</tr>";
			}
			return msg;
		}
		
		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public ProcessingMethod methodGerarTabelaCondicoesLogicaMesh = new ProcessingMethod() {

		@Override
		public String method() {

			String msg = "";
			
			List<Condicao> condicoesLogica = ConfigProcess.bd().selectCondicoes();
			for (int i = 0; condicoesLogica != null && i < condicoesLogica.size(); i++) {
				Condicao value = condicoesLogica.get(i);
				value.setLstEntradasCondicao(ConfigProcess.bd().selectEntradaCondicao(value.getId()));
				System.out.println(value);
				msg += "<tr class=\"gradeA\">" 
						+ "<td>";
							for(EntradaCondicao ec : value.getLstEntradasCondicao())
								msg += ec.getFkEntrada().getNome() + ", ";
					msg += "</td>" 
						+ "<td>ATIVO</td>"
						+ "<td>Entradas</td>" 
						+ "<td>In, In, In</td>" 
						+ "<td class=\"actions\">"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Save\"><i class=\"icon wb-wrench\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Delete\"><i class=\"icon wb-close\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default edit-row\""
						+ "data-toggle=\"tooltip\" data-original-title=\"Edit\"><i class=\"icon wb-edit\" aria-hidden=\"true\"></i></a>"
						+ "<a href=\"#\" class=\"btn btn-sm btn-icon btn-pure btn-default on-default remove-row\" "
						+ "data-toggle=\"tooltip\" data-original-title=\"Remove\"><i class=\"icon wb-trash\" aria-hidden=\"true\"></i></a>"
						+ "</td>" + "</tr>";
			}

			return msg;
		}

		@Override
		public void setArgs(String args) {
			// TODO Auto-generated method stub

		}
	};
	
}
