package WebService.HTML;import WebService.extensao.impl.Tags.Methods;public class Dashboards {	private static final String linkDefualt = "javascript:void(0)";	private static final String iconeIndicadores = "wb-stats-bars";	private static final String iconeRelatorios = "wb-print";	private static final String iconeCadastros = "wb-order";	private static final String iconeTurno = "wb-settings";	private static final String iconeConfiguracao = "wb-settings";	private static final String iconeUsuarios = "wb-users";	private static final String iconePortais = "wb-inbox";	private static final String iconeSobre = "wb-help";	private static final String iconeLog = "wb-book";	private DashboardSubMenu[] arrayIndicadoresSubMenu = {};	public DashboardMenu Indicadores = new DashboardMenu("Indicadores", false, iconeIndicadores, linkDefualt, null,			arrayIndicadoresSubMenu);	public DashboardSubMenu RelatoriosEventosAGVS = new DashboardSubMenu("Eventos AGVS", false,			"/SupervisorioAGVS/Relatorios/EventosAGVS");		public DashboardSubMenu RelatorioZoneTime= new DashboardSubMenu("Zona de Tempo", false,			"/SupervisorioAGVS/Relatorios/ZonaTempo");		public DashboardSubMenu LogTags = new DashboardSubMenu("Log de Tags", false,			"/SupervisorioAGVS/Relatorios/LogTags");	private DashboardSubMenu[] arrayRelatoriosSubMenu = { RelatoriosEventosAGVS, LogTags, RelatorioZoneTime };	public DashboardMenu Relatorios = new DashboardMenu("Relatorios", false, iconeRelatorios, linkDefualt, null,			arrayRelatoriosSubMenu);	public DashboardSubMenu LogUsuarios = new DashboardSubMenu("Usuarios", false,			"/SupervisorioAGVS/LogsSistema/LogUsuarios");	public DashboardSubMenu LogCruzamentos = new DashboardSubMenu("Cruzamentos", false,			"/SupervisorioAGVS/LogCruzamentos");	public DashboardSubMenu LogSemafaros = new DashboardSubMenu("Semaforos", false, "/SupervisorioAGVS/LogSemaforos");	private DashboardSubMenu[] arrayLogSistema = { LogUsuarios, LogCruzamentos/*, LogSemafaros*/ };	public DashboardMenu LogSistema = new DashboardMenu("Logs do Sistema", false, iconeLog, linkDefualt, null,			arrayLogSistema);	private DashboardMenu[] arrayBAM = { Indicadores, Relatorios, LogSistema };	public DashboardTitulo BAM = new DashboardTitulo("BAM", arrayBAM);	public DashboardSubMenu cadastroAGVS = new DashboardSubMenu("AGVS", false, "/SupervisorioAGVS/Cadastro/AGVS");	public DashboardSubMenu cadastroMesh = new DashboardSubMenu("Mesh", false, "/SupervisorioAGVS/Cadastro/Mesh");	public DashboardSubMenu cadastroSemaforo = new DashboardSubMenu("Semáforo", false, "/SupervisorioAGVS/Cadastro/Semaforo");	public DashboardSubMenu cadastroZoneTime = new DashboardSubMenu("Zonas de Tempo", false, "/SupervisorioAGVS/Cadastro/ZoneTime");	public DashboardSubMenu cadastroLayout = new DashboardSubMenu("Layout", false, "/SupervisorioAGVS/Cadastro/Layout");	public DashboardSubMenu cadastroTags = new DashboardSubMenu("Tags", false, "/SupervisorioAGVS/Cadastro/Tags");	public DashboardSubMenu cadastroRotas = new DashboardSubMenu("Rotas", false, "/SupervisorioAGVS/Cadastro/Rotas");	public DashboardSubMenu cadastroCruzamentos = new DashboardSubMenu("Cruzamentos", false,			"/SupervisorioAGVS/Cadastro/Cruzamentos");	public DashboardSubMenu cadastroTagsCruzamentos = new DashboardSubMenu("Tags dos Cruzamentos", false,			"/SupervisorioAGVS/Cadastro/TagsCruzamento");//	public DashboardSubMenu cadastroTagsSemaforos = new DashboardSubMenu("Tags dos Semaforos", false,//			"/SupervisorioAGVS/Cadastro/TagsSemaforo");//	public DashboardSubMenu cadastroEquipamentos = new DashboardSubMenu("Equipamentos", false,//			"/SupervisorioAGVS/Cadastro/Equipamentos");//	public DashboardSubMenu cadastroSupermercados = new DashboardSubMenu("Supermercados", false,//			"/SupervisorioAGVS/Cadastro/Supermercados");//	public DashboardSubMenu cadastroTagsTempoParado = new DashboardSubMenu("Tags Tempo Parado", false,//			"/SupervisorioAGVS/Cadastro/TagsTempoParado");	private DashboardSubMenu[] arrayCadastros = { cadastroAGVS, cadastroLayout, cadastroTags, cadastroZoneTime, cadastroRotas,			cadastroCruzamentos, cadastroMesh, cadastroTagsCruzamentos};//			, cadastroSemaforo, cadastroTagsSemaforos, //			cadastroEquipamentos, cadastroSupermercados, cadastroTagsTempoParado };	public DashboardMenu Cadastro = new DashboardMenu("Cadastros", false, iconeCadastros, linkDefualt, null,			arrayCadastros);	public DashboardSubMenu configuracaoBancoDados = new DashboardSubMenu("Banco de Dados", false,			"/SupervisorioAGVS/Configuracao/BD");	private DashboardSubMenu[] arrayConfiguracao = { configuracaoBancoDados };	public DashboardMenu Configuracao = new DashboardMenu("Configuracao", false, iconeConfiguracao, linkDefualt, null,			arrayConfiguracao);	private DashboardSubMenu[] arrayUsuarios = {};	public DashboardMenu Usuarios = new DashboardMenu("Usuarios", false, iconeUsuarios, "/SupervisorioAGVS/Usuarios",			null, arrayUsuarios);	private DashboardMenu[] arrayAdministrador = { Cadastro, Configuracao, Usuarios };	public DashboardTitulo ADM = new DashboardTitulo("Administrador", arrayAdministrador);	public DashboardSubMenu InicioTurno = new DashboardSubMenu("Inicio de Turno", false,			"/SupervisorioAGVS/Operador/InicioTurno");	public DashboardSubMenu Pedidos = new DashboardSubMenu("Pedidos", false,			"/SupervisorioAGVS/Operador/Manutencao/Pedidos");	public DashboardSubMenu Rastrear = new DashboardSubMenu("Rastreamento", false,			"/SupervisorioAGVS/Operador/Manutencao/Rastrear");//	private DashboardSubMenu[] arrayTurno = { InicioTurno, Pedidos, Rastrear };//	public DashboardMenu Turno = new DashboardMenu("Manutencao", false, iconeTurno, linkDefualt, null, arrayTurno);//	private DashboardMenu[] arrayOperador = { Turno };//	public DashboardTitulo operador = new DashboardTitulo("Operador", arrayOperador);	private DashboardSubMenu[] arraySobre = {};	public DashboardMenu sobre = new DashboardMenu("Sobre", false, iconeSobre, "/SupervisorioAGVS/Sobre", null,			arraySobre);	private DashboardMenu[] arrayAjuda = { sobre };	public DashboardTitulo ajuda = new DashboardTitulo("Ajuda", arrayAjuda);	public DashboardTitulo[] titulos = { BAM, ADM, /*operador,*/ ajuda };	public String gerarDashBoard(DashboardSubMenu subMenuActive, DashboardMenu menuActive, int idPortal) {		if (subMenuActive != null)			subMenuActive.setAtivo(true);		if (menuActive != null)			menuActive.setAtivo(true);		String htmlDashboart = "<div class=\"site-menubar\">" + "<div class=\"site-menubar-body\">" + "<div>" + "<div>"				+ "<ul class=\"site-menu\">";		for (DashboardTitulo titulo : titulos) {			htmlDashboart += "<li class=\"site-menu-category\">" + titulo.getTitulo() + "</li>";			for (DashboardMenu dashboardMenu : titulo.getMenus()) {								if (dashboardMenu.getGerar() != null) {					dashboardMenu.getGerar().setArgs("" + idPortal);					htmlDashboart += dashboardMenu.getGerar().method();					continue;				}				String active = "";				if (dashboardMenu.isAtivo()) {					active = "active open";				}				htmlDashboart += "<li class=\"site-menu-item has-sub " + active + "\">" + "<a href=\""						+ dashboardMenu.getLink() + "\">" + "<i class=\"site-menu-icon " + dashboardMenu.getIcone()						+ "\" aria-hidden=\"true\"></i>" + "<span class=\"site-menu-title\">" + dashboardMenu.getNome()						+ "</span>" + "</a>" + "<ul class=\"site-menu-sub\">";				for (DashboardSubMenu dashboardSubMenu : dashboardMenu.getSubMenus()) {//					System.out.println(dashboardSubMenu.getNome());					active = "";					if (dashboardSubMenu.isAtivo()) {						active = "active";					}					htmlDashboart += "<li class=\"site-menu-item " + active + "\">"							+ "<a class=\"animsition-link\" href=\"" + dashboardSubMenu.getLink() + "\">"							+ "<span class=\"site-menu-title\">" + dashboardSubMenu.getNome() + "</span>" + "</a>"							+ "</li>";				}				htmlDashboart += "</ul>" + "</li>";			}		}		htmlDashboart += "</ul>" + "</div>" + "</div>" + "</div>";		return htmlDashboart;	}}