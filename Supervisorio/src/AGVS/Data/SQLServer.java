package AGVS.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import AGVS.Util.Log;
import AGVS.Util.Util;

public class SQLServer implements BancoDados {

	private String url;
	private String host;
	private String usuario;
	private String senha;

	private final String strSelectUsuarios = "select * from usuarios";
	private final String strSelectEntradasMesh = "select entradasMesh.id as 'entradasMesh.id', entradasMesh.porta as 'entradasMesh.porta', entradasMesh.acionamento as 'entradasMesh.acionamento', entradasMesh.descricao as 'entradasMesh.descricao', entradasMesh.status as 'entradasMesh.status' \r\n"
			+ "	from entradasMesh\r\n";
	private final String strSelectSaidasMesh = "select saidasMesh.id as 'saidasMesh.id', saidasMesh.porta as 'saidasMesh.porta', saidasMesh.descricao as 'saidasMesh.descricao', saidasMesh.status as 'saidasMesh.status' \r\n"
			+ "	from saidasMesh\r\n";
	private final String strSelectCondicao = "select condicao.id as 'condicao.id', condicao.indice as 'condicao.indice' from condicao\r\n"
			+ "	left join logica on condicao.fkLogica = logica.id\r\n"
			+ "	left join meshSerial on logica.fkMeshSerial = meshSerial.id";

	private final String strSelectEntradasCondicao = "select entradaCondicaoRel.fkEntrada as 'entradaCondicaoRel.fkEntrada', entradaCondicaoRel.fkCondicao as 'entradaCondicaoRel.fkCondicao', \r\n"
			+ "entradaCondicaoRel.status as 'entradaCondicaoRel.status', "
			+ "entradasMesh.id as 'entradasMesh.id', entradasMesh.porta as 'entradasMesh.porta', entradasMesh.acionamento as 'entradasMesh.acionamento', entradasMesh.descricao as 'entradasMesh.descricao', entradasMesh.status as 'entradasMesh.status' "
			+ "from entradaCondicaoRel " + "left join entradasMesh on entradaCondicaoRel.fkEntrada = entradasMesh.id";
	private final String strSelectLogUsuarios = "select * from logUsuarios";
	private final String strSelectAGVS = "select agvs.oldStatusFalha as 'agvs.oldStatusFalha', agvs.statusOldTime as 'agvs.statusOldTime', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso', agvs.frequencia as 'agvs.frequencia', agvs.ip as 'agvs.ip' from agvs";
	private final String strSelectLastSixAGVS = "select top 6 agvs.oldStatusFalha as 'agvs.oldStatusFalha', agvs.statusOldTime as 'agvs.statusOldTime', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso', agvs.frequencia as 'agvs.frequencia', agvs.ip as 'agvs.ip', CASE WHEN statusOldTime > tagAtualTime THEN statusOldTime ELSE tagAtualTime END as lastUpdating from agvs order by lastUpdating desc, agvs.id";
	private final String strSelectLastEigthAGVS = "select top 8 agvs.oldStatusFalha as 'agvs.oldStatusFalha', agvs.statusOldTime as 'agvs.statusOldTime', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso', agvs.frequencia as 'agvs.frequencia', agvs.ip as 'agvs.ip', CASE WHEN statusOldTime > tagAtualTime THEN statusOldTime ELSE tagAtualTime END as lastUpdating from agvs order by lastUpdating desc, agvs.id";

	private final String strSelectMesh = "select meshSerial.id as 'meshserial.id', meshSerial.nome as 'meshserial.nome', meshSerial.mac16 as 'meshSerial.mac16', "
			+ "meshSerial.mac64 as 'meshserial.mac64', meshSerial.numero_entradas as 'meshSerial.numeroEntradas', meshSerial.numero_saidas as 'meshSerial.numeroSaidas' "
			+ "from meshSerial ";

	private final String strSelectSemaforo = "select semaforo.id as 'semaforo.id', semaforo.nome as 'semaforo.nome', meshSerial.id as 'meshserial.id', meshSerial.nome as 'meshserial.nome', meshSerial.mac16 as 'meshSerial.mac16', meshSerial.mac64 as 'meshserial.mac64', meshSerial.numero_entradas as 'meshSerial.numeroEntradas', meshSerial.numero_saidas as 'meshSerial.numeroSaidas' from semaforo left join meshSerial on semaforo.fk_meshSerial = meshSerial.id";
	private final String strSelectLines = "select lines.descricao as 'lines.descricao', lines.xInicial as 'lines.xInicial', lines.yInicial as 'lines.yInicial', lines.xFinal as 'lines.xFinal', lines.yFinal as 'lines.yFinal', lines.cor as 'lines.cor' from lines";
	private final String strSelectTags = "select tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tags";
	private final String strSelectFalhas = "select falhas.id as 'falhas.id', falhas.data as 'falhas.data', falhas.msg as 'falhas.msg', falhas.idAGV as 'falhas.idAGV' from falhas";
	private final String strSelectRotas = "select rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao' from rotas";
	private final String strSelectTagAtraso = "select timeTag.nome as 'timeTag.nome', timeTag.tempo as 'timeTag.tempo', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from timeTag LEFT JOIN tags  ON tags.epc = timeTag.epc";
	private final String strSelectTagRota = "select tagsRota.nome as 'tagsRota.nome', tagsRota.posicao as 'tagsRota.posicao', tagsRota.addRota as 'tagsRota.addRota', tagsRota.setPoint as 'tagsRota.setPoint', tagsRota.velocidade as 'tagsRota.velocidade', tagsRota.temporizador as 'tagsRota.temporizador', tagsRota.girar as 'tagsRota.girar', tagsRota.estadoAtuador as 'tagsRota.estadoAtuador', tagsRota.sensorObstaculo as 'tagsRota.sensorObstaculo', tagsRota.sinalSonoro as 'tagsRota.sinalSonoro', tagsRota.tagDestino as 'tagsRota.tagDestino', tagsRota.tagParada as 'tagsRota.tagParada', tagsRota.pitStop as 'tagsRota.pitStop', rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tagsRota LEFT JOIN rotas ON tagsRota.nomeRota = rotas.nome LEFT JOIN tags ON tags.epc = tagsRota.epc";
	private final String strSelectCruzamentos = "select cruzamentos.nome as 'cruzamentos.nome', cruzamentos.descricao as 'cruzamentos.descricao' from cruzamentos";
	private final String strSelectTagsCruzamentos = "select tagsCruzamento.nome as 'tagsCruzamento.nome', tagsCruzamento.tipo as 'tagsCruzamento.tipo', cruzamentos.nome as 'cruzamentos.nome', cruzamentos.descricao as 'cruzamentos.descricao', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tagsCruzamento LEFT JOIN tags ON tags.epc = tagsCruzamento.epc LEFT JOIN cruzamentos ON cruzamentos.nome = tagsCruzamento.nomeCruzamento ";
	private final String strSelectTagsSemaforo = "select tagsSemaforo.id as 'tagsSemaforo.id', tagsSemaforo.nome as 'tagsSemaforo.nome', tagsSemaforo.tipo as 'tagsSemaforo.tipo', semaforo.id as 'semaforo.id', semaforo.nome as 'semaforo.nome', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY', meshSerial.id as 'meshserial.id', meshSerial.nome as 'meshserial.nome', meshSerial.mac16 as 'meshSerial.mac16', meshSerial.mac64 as 'meshserial.mac64', meshSerial.numero_entradas as 'meshSerial.numeroEntradas', meshSerial.numero_saidas as 'meshSerial.numeroSaidas' from tagsSemaforo LEFT JOIN tags ON tags.epc = tagsSemaforo.epc LEFT JOIN semaforo ON semaforo.id = tagsSemaforo.fk_semaforo LEFT JOIN meshSerial ON meshSerial.id = semaforo.fk_meshSerial";
	private final String strSelectLogTags = "select logTags.id as 'logTags.id', logTags.idAGV as 'logTags.idAGV', logTags.data as 'logTags.data', logTags.epc as 'logTags.epc', logTags.msg as 'logTags.msg', logTags.velocidade as 'logTags.velocidade', logTags.bateria as 'logTags.bateria' from logTags ";
	private final String strSelectSupermercados = "select supermercados.id as 'supermercados.id', supermercados.nome as 'supermercados.nome', supermercados.data as 'supermercados.data', supermercados.produto as 'supermercados.produto' FROM supermercados";
	private final String strSelectEquipamentos = "select equipamentos.id as 'equipamentos.id', equipamentos.nome as 'equipamentos.nome', equipamentos.rota as 'equipamentos.rota', equipamentos.tipo as 'equipamentos.tipo' FROM equipamentos";
	private final String strSelectTempoTagsParado = "select tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY', tagTempoParado.nome as 'tagTempoParado.nome' from tagTempoParado LEFT JOIN tags on tags.epc = tagTempoParado.epc";

	private final String strSelectZoneTime = "select zoneTime.id as 'zoneTime.id', zoneTime.description as 'zoneTime.description', \r\n"
			+ "	tagStart.epc as 'tagStart.epc',	tagStart.nome as 'tagStart.nome', tagStart.codigo as 'tagStart.codigo', \r\n"
			+ "	tagStart.coordenadaX as 'tagStart.coordenadaX', tagStart.coordenadaY as 'tagStart.coordenadaY', \r\n"
			+ "	tagEnd.epc as 'tagEnd.epc',	tagEnd.nome as 'tagEnd.nome', tagEnd.codigo as 'tagEnd.codigo', \r\n"
			+ "	tagEnd.coordenadaX as 'tagEnd.coordenadaX', tagEnd.coordenadaY as 'tagEnd.coordenadaY', zoneTime.limitTime as 'zoneTime.limitTime'\r\n"
			+ "		from zoneTime \r\n" + "	LEFT JOIN tags as tagStart ON tagStart.epc = zoneTime.fkTagStart\r\n"
			+ "	LEFT JOIN tags as tagEnd ON tagEnd.epc = zoneTime.fkTagEnd";

	private final String strSelectLogZoneTime = "select logZoneTime.idLog as 'logZoneTime.id', logZoneTime.timeRoute as 'logZoneTime.timeRoute', "
			+ " logZoneTime.timeLost as 'logZoneTime.timeLost', FORMAT(logZoneTime.data,'yyyy-MM-dd HH:mm:ss.fff') as 'logZoneTime.data', logZoneTime.timeLostObstacle as 'logZoneTime.timeLostObstacle', "
			+ " zoneTime.id as 'zoneTime.id', zoneTime.description as 'zoneTime.description', zoneTime.limitTime as 'zoneTime.limitTime',"
			+ " tagStart.epc as 'tagStart.epc',	tagStart.nome as 'tagStart.nome', tagStart.codigo as 'tagStart.codigo',"
			+ " tagStart.coordenadaX as 'tagStart.coordenadaX', tagStart.coordenadaY as 'tagStart.coordenadaY',"
			+ " tagEnd.epc as 'tagEnd.epc',	tagEnd.nome as 'tagEnd.nome', tagEnd.codigo as 'tagEnd.codigo',"
			+ " tagEnd.coordenadaX as 'tagEnd.coordenadaX', tagEnd.coordenadaY as 'tagEnd.coordenadaY',"
			+ " agvs.oldStatusFalha as 'agvs.oldStatusFalha', agvs.statusOldTime as 'agvs.statusOldTime', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso', agvs.frequencia as 'agvs.frequencia'"
			+ " from logZoneTime" + " LEFT JOIN zoneTime ON zoneTime.id = logZoneTime.fkZoneTime"
			+ " LEFT JOIN agvs ON agvs.id = logZoneTime.fkAgv"
			+ " LEFT JOIN tags as tagStart ON tagStart.epc = zoneTime.fkTagStart"
			+ "	LEFT JOIN tags as tagEnd ON tagEnd.epc = zoneTime.fkTagEnd";

	private final String strSelectItem(int id, String item) {
		return "select * from " + item + " where id=" + id;
	}

	private AGV getAGV(ResultSet rs) throws SQLException {
		return new AGV(rs.getInt("agvs.id"), rs.getString("agvs.nome"), rs.getString("agvs.status"),
				rs.getString("agvs.tipo"), rs.getInt("agvs.velocidade"), rs.getInt("agvs.bateria"),
				rs.getString("agvs.tagAtual"), rs.getString("agvs.mac64"), rs.getString("agvs.mac16"),
				Util.getConvertDateBD(rs.getString("agvs.tagAtualTime")), rs.getInt("agvs.atraso"),
				rs.getString("agvs.oldStatusFalha"), Util.getConvertDateBD(rs.getString("agvs.statusOldTime")),
				rs.getInt("agvs.frequencia"), rs.getString("agvs.ip"));
	}

	private MeshSerial getMeshSerial(ResultSet rs) throws SQLException {
		return new MeshSerial(rs.getInt("meshSerial.numeroEntradas"), rs.getInt("meshSerial.numeroSaidas"),
				rs.getString("meshSerial.mac16"), rs.getString("meshserial.mac64"), rs.getString("meshserial.nome"),
				rs.getInt("meshserial.id"));
	}

	private Semaforo getSemaforo(ResultSet rs) throws SQLException {
		return new Semaforo(rs.getInt("semaforo.id"), getMeshSerial(rs), rs.getString("semaforo.nome"), null, null,
				null, null, null, null);
	}

	private Line getLine(ResultSet rs) throws SQLException {
		return new Line(rs.getString("lines.descricao"), rs.getInt("lines.xInicial"), rs.getInt("lines.yInicial"),
				rs.getInt("lines.xFinal"), rs.getInt("lines.yFinal"), rs.getString("lines.cor"));
	}

	private Tag getTag(ResultSet rs) throws SQLException {
		return new Tag(rs.getString("tags.epc"), rs.getString("tags.nome"), rs.getInt("tags.codigo"),
				rs.getInt("tags.coordenadaX"), rs.getInt("tags.coordenadaY"));
	}

	private AlertFalhas getAlerts(ResultSet rs) throws SQLException {
		return new AlertFalhas(rs.getInt("falhas.idAGV"), rs.getString("falhas.msg"),
				Util.getConvertDateBD(rs.getString("falhas.data")));
	}

	private Rota getRota(ResultSet rs) throws SQLException {
		return new Rota(rs.getString("rotas.nome"), rs.getString("rotas.descricao"));
	}

	private TagsRotas getTagRota(ResultSet rs) throws SQLException {
		Rota rt;
		if (rs.getString("tagsRota.addRota") == null) {
			rt = new Rota("Desativado", "Desativado");
		} else {
			rt = new Rota(rs.getString("tagsRota.addRota"), rs.getString("tagsRota.addRota"));
		}
		return new TagsRotas(rs.getString("tagsRota.nome"), rs.getInt("tagsRota.posicao"), rt, getTag(rs),
				rs.getInt("tagsRota.setPoint"), rs.getInt("tagsRota.velocidade"), rs.getInt("tagsRota.temporizador"),
				rs.getString("tagsRota.girar"), rs.getString("tagsRota.estadoAtuador"),
				rs.getString("tagsRota.sensorObstaculo"), rs.getString("tagsRota.sinalSonoro"),
				rs.getInt("tagsRota.tagDestino"), rs.getInt("tagsRota.tagParada"), rs.getInt("tagsRota.pitStop"));
	}

	private Cruzamento getCruzamento(ResultSet rs) throws SQLException {
		return new Cruzamento(rs.getString("cruzamentos.nome"), rs.getString("cruzamentos.descricao"), null, null,
				null);
	}

	private TagCruzamento getTagCruzamento(ResultSet rs) throws SQLException {
		return new TagCruzamento(rs.getString("tagsCruzamento.nome"), getTag(rs), getCruzamento(rs),
				rs.getString("tagsCruzamento.tipo"));
	}

	private TagSemaforos getTagsSemaforo(ResultSet rs) throws SQLException {
		return new TagSemaforos(rs.getInt("tagsSemaforo.id"), rs.getString("tagsSemaforo.nome"), getTag(rs),
				getSemaforo(rs), rs.getString("tagsSemaforo.tipo"));
	}

	private TagAtraso getTagAtraso(ResultSet rs) throws SQLException {
		return new TagAtraso(rs.getString("timeTag.nome"), getTag(rs), rs.getInt("timeTag.tempo"));
	}

	private LogTags getLogTags(ResultSet rs) throws SQLException {
		return new LogTags(rs.getInt("logTags.id"), Util.getConvertDateBD(rs.getString("logTags.data")),
				rs.getInt("logTags.idAgv"), rs.getString("logTags.msg"), rs.getString("logTags.epc"),
				rs.getInt("logTags.bateria"), rs.getInt("logTags.velocidade"));
	}

	private Supermercado getSupermercado(ResultSet rs) throws SQLException {
		return new Supermercado(rs.getString("supermercados.nome"), rs.getString("supermercados.produto"),
				Util.getConvertDateBD(rs.getString("supermercados.data")), rs.getInt("supermercados.id"));
	}

	private Equipamentos getEquipamentos(ResultSet rs) throws SQLException {
		return new Equipamentos(rs.getString("equipamentos.nome"), rs.getString("equipamentos.tipo"),
				rs.getInt("equipamentos.rota"), rs.getString("equipamentos.id"));
	}

	private TagTempoParado getTagTempoParado(ResultSet rs) throws SQLException {
		return new TagTempoParado(rs.getString("tagTempoParado.nome"),
				new Tag(rs.getString("tags.epc"), rs.getString("tags.nome"), rs.getInt("tags.codigo"),
						rs.getInt("tags.coordenadaX"), rs.getInt("tags.coordenadaY")));
	}

	private ZoneTime getZoneTime(ResultSet rs) throws SQLException, ParseException {
		Tag tagStart = new Tag(rs.getString("tagStart.epc"), rs.getString("tagStart.nome"),
				rs.getInt("tagStart.codigo"), rs.getInt("tagStart.coordenadaX"), rs.getInt("tagStart.coordenadaY"));
		Tag tagEnd = new Tag(rs.getString("tagEnd.epc"), rs.getString("tagEnd.nome"), rs.getInt("tagEnd.codigo"),
				rs.getInt("tagEnd.coordenadaX"), rs.getInt("tagEnd.coordenadaY"));
		return new ZoneTime(rs.getInt("zoneTime.id"), rs.getString("zoneTime.description"), tagStart, tagEnd,
				rs.getString("zoneTime.limitTime"));
	}

	private LogZoneTime getLogZoneTime(ResultSet rs) throws SQLException, ParseException {
		// System.out.println(rs.getString("logZoneTime.data"));
		return new LogZoneTime(rs.getInt("logZoneTime.id"), getZoneTime(rs), rs.getTime("logZoneTime.timeRoute"),
				rs.getString("logZoneTime.timeLost"), getAGV(rs), rs.getString("logZoneTime.data"),
				rs.getString("logZoneTime.timeLostObstacle"));
	}

	private PortaMashSerial getPortaInMeshSerial(ResultSet rs) throws SQLException, ParseException {
		return new PortaMashSerial(rs.getString("entradasMesh.descricao"), rs.getString("entradasMesh.porta"),
				rs.getString("entradasMesh.acionamento"), rs.getString("entradasMesh.status"), null);
	}

	private PortaSaidaMeshSerial getPortaOutMeshSerial(ResultSet rs) throws SQLException, ParseException {
		return new PortaSaidaMeshSerial(rs.getInt("saidasMesh.id"), rs.getString("saidasMesh.descricao"),
				rs.getString("saidasMesh.porta"), rs.getString("saidasMesh.status"), null);
	}

	private Condicao getCondicao(ResultSet rs) throws SQLException, ParseException {
		return new Condicao(rs.getInt("condicao.id"), rs.getInt("condicao.indice"), new ArrayList<EntradaCondicao>());
	}

	private EntradaCondicao getEntradasCondicao(ResultSet rs) throws SQLException, ParseException {
		return new EntradaCondicao(getPortaInMeshSerial(rs), rs.getString("entradaCondicaoRel.status"));
	}

	public boolean criarBanco() {
		Connection connection = null;
		try {

			Connection con = null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlserver://;serverName=" + host + ";user=" + usuario + ";password=" + senha);

			String script = "create DATABASE SupervisorioAGVS;";
			PreparedStatement stmt = conn.prepareStatement(script);
			stmt.execute();

			script = "ALTER DATABASE SupervisorioAGVS collate SQL_Latin1_General_CP1251_CI_AS";
			stmt = conn.prepareStatement(script);
			stmt.execute();

			script = "use SupervisorioAGVS;";
			stmt = conn.prepareStatement(script);
			stmt.execute();

			FileReader arq = new FileReader("Banco.sql");
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			String sql = "";
			while (linha != null) {
				sql += "\n" + linha;
				linha = lerArq.readLine();

			}

			stmt = conn.prepareStatement(sql);
			stmt.execute();
			lerArq.close();
			arq.close();
			conn.close();
			return true;
		} catch (Exception e) {
			new Log(e);

		}
		return false;
	}

	public SQLServer(String host, String usuario, String senha) {
		this.host = host;
		this.usuario = usuario;
		this.senha = senha;
		url = "jdbc:sqlserver://;serverName=" + host + ";databasename=SupervisorioAGVS;user=" + usuario
				+ ";password=" + senha;
	}

	private final LogUsuario getLogUsuario(ResultSet rs) throws SQLException {
		return new LogUsuario(rs.getInt("id"), Util.getDateTimeFormatoBR(Util.getConvertDateBD(rs.getString("data"))),
				rs.getString("descricao"), rs.getString("tipo"), rs.getString("nome"));

	}

	@Override
	public boolean conectarBancoDados() {
		Connection connection = null;
		try {

			Connection con = null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url);
			conn.close();
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			new Log(e);

		}
		return false;
	}

	private ResultSet selectItem(int id, String item) {
		ResultSet rs = null;
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectItem(id, item);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}
		return rs;
	}

	@Override
	public List<Usuario> selectUsuarios() {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectUsuarios;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				usuarios.add(new Usuario(rs.getString("login"), rs.getString("nome"), rs.getString("email"),
						rs.getString("senha"), rs.getString("permissao"), rs.getString("liberado")));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return usuarios;
	}

	@Override
	public boolean insertUsuarios(String login, String nome, String email, String password, String permissao) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into usuarios(login, nome, senha, email, permissao) values('" + login + "', '" + nome
					+ "', '" + Util.gerarCriptMD5(password) + "', '" + email + "', '" + permissao + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteUsuario(String id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from usuarios where login='" + id + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUsuarios(String id, String login, String nome, String email, String password,
			String permissao) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update usuarios set login='" + login + "', nome='" + nome + "', senha='"
					+ Util.gerarCriptMD5(password) + "', email='" + email + "', permissao='" + permissao + "'"
					+ " where login='" + id + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUsuarios(String id, String login, String nome, String email, String permissao) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update usuarios set login='" + login + "', nome='" + nome + "', email='" + email
					+ "', permissao='" + permissao + "'" + " where login='" + id + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	public boolean updateUsuarios(String id, String liberado) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update usuarios set liberado='" + liberado + "' where login='" + id + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	public boolean updateUsuarios(String id, String idPassword, String password) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update usuarios set senha='" + password + "' where login='" + id + "' and senha='"
					+ idPassword + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<LogUsuario> selectLogUsuarios() {
		List<LogUsuario> logUsr = new ArrayList<LogUsuario>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectLogUsuarios;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				logUsr.add(getLogUsuario(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return logUsr;
	}

	@Override
	public boolean deleteLogUsuarios(String id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from logUsuarios where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertLogUsuarios(long data, String nome, String descricao, String tipo) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into logUsuarios(data, nome, descricao, tipo) values('" + Util.getDateTime(data)
					+ "', '" + nome + "', '" + descricao + "', '" + tipo + "')";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public void criarBancoDados() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean insertAGV(int id, String nome, String status, String tipo, String mac64, String mac16, String ip) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into agvs(id, nome, status, tipo, ip, mac64, mac16) values(" + id + ", '" + nome + "', '"
					+ status + "', '" + tipo + "', '" + ip + "', '" + mac64 + "', '" + mac16 + "')";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public int insertMesh(int id, String nome, String ip, String mac64, int entradas, int saidas) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into meshSerial(id, nome, ip, mac64, numero_entradas, numero_saidas) values(" + id
					+ ", '" + nome + "', '" + ip + "', '" + mac64 + "', " + entradas + ", " + saidas + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return -1;
		}
		return id;
	}

	@Override
	public boolean insertEntradasMesh(int idMesh, List<PortaMashSerial> lstPms) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			for (PortaMashSerial pms : lstPms) {
				String sql = "insert into entradasMesh(porta, descricao, acionamento, status, fkMeshSerial) values("
						+ pms.getPorta() + ", '" + pms.getNome() + "', '" + pms.getAcionamento() + "', '"
						+ pms.getStatus() + "', " + idMesh + ")";
				PreparedStatement stmt = conexao.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			}
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertSaidasMesh(int idMesh, List<PortaSaidaMeshSerial> lstPsms) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			for (PortaSaidaMeshSerial psms : lstPsms) {
				String sql = "insert into saidasMesh(porta, descricao, status, fkMeshSerial) values(" + psms.getPorta()
						+ ", '" + psms.getNome() + "', '" + psms.getStatus() + "', " + idMesh + ")";
				PreparedStatement stmt = conexao.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			}
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteAGV(int id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from agvs where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteMesh(int id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from MeshSerial where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateAGV(int id, String nome, String status, String tipo, String mac64, String mac16, String ip, int oldId) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update agvs set id=" + id + ", nome='" + nome + "', status='" + status + "', tipo='" + tipo
					+ "', ip='" + ip + "', mac64='" + mac64 + "', mac16='" + mac16 + "' where id=" + oldId;
//			System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateAGVFrequency(int iAgv, int iFrequencia) {
		// TODO Auto-generated method stub
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update agvs set frequencia=" + iFrequencia + " where id=" + iAgv;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateMesh(int id, String nome, String ip, String mac64, int entradas, int saidas) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update MeshSerial set nome='" + nome + "', ip='" + ip + "', mac64='" + mac64
					+ "', numero_entradas=" + entradas + ", numero_saidas=" + saidas + " where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateAGV(int id, String tagAtual, int bateria, long tagAtualTime, int atraso, String status,
			int velocidade) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update agvs set tagAtual='" + tagAtual + "', bateria=" + bateria + ", tagAtualTime = '"
					+ Util.getDateTime(tagAtualTime) + "', status='" + status + "', statusOldTime = '"
					+ Util.getDateTime(tagAtualTime) + "', atraso = " + atraso + ", velocidade = " + velocidade
					+ " where id=" + id;
//			 System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateAGV(int id, int atraso) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update agvs set atraso = " + atraso + " where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateIPAGV(int id, String ip) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update agvs set ip = '" + ip + "' where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<MeshSerial> selectMeshSerial() {
		List<MeshSerial> lstMesh = new ArrayList<MeshSerial>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectMesh;
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				lstMesh.add(getMeshSerial(rs));

			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}
		return lstMesh;
	}

	@Override
	public List<Semaforo> selectSemaforo() {
		List<Semaforo> lstSemaforo = new ArrayList<Semaforo>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectSemaforo;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lstSemaforo.add(getSemaforo(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return lstSemaforo;
	}

	@Override
	public List<AGV> selecAGVS() {
		List<AGV> agvs = new ArrayList<AGV>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectAGVS;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				agvs.add(getAGV(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return agvs;
	}

	@Override
	public List<AGV> selecAGVSLastSixUpdate() {
		List<AGV> agvs = new ArrayList<AGV>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectLastSixAGVS;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				agvs.add(getAGV(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return agvs;
	}

	public AGV selecAGVS(int id) {
		AGV agv = null;

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectAGVS + " where id = " + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				agv = (getAGV(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return agv;
	}

	@Override
	public boolean insertLine(String descricao, int xInicial, int yInicial, int xFinal, int yFinal, String cor) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into lines(descricao, xInicial, yInicial, xFinal, yFinal, cor) values('" + descricao
					+ "', " + xInicial + ", " + yInicial + ", " + xFinal + ", " + yFinal + ", '" + cor + "'" + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteLine(String descricao) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from lines where descricao='" + descricao + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateLine(String descricao, int xInicial, int yInicial, int xFinal, int yFinal, String cor,
			String oldDescricao) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update lines set descricao='" + descricao + "', xInicial=" + xInicial + ", yInicial="
					+ yInicial + ", xFinal=" + xFinal + ", yFinal=" + yFinal + ", cor='" + cor + "'"
					+ " where descricao='" + oldDescricao + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<Line> selecLines() {
		List<Line> lines = new ArrayList<Line>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectLines;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lines.add(getLine(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return lines;
	}

	@Override
	public boolean insertTag(String epc, String nome, int codigo, int coordenadaX, int coordenadaY) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into tags(epc, nome, codigo, coordenadaX, coordenadaY) values('" + epc + "', '" + nome
					+ "', " + codigo + ", " + coordenadaX + ", " + coordenadaY + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTag(String id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from tags where epc='" + id + "'";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTag(String epc, String nome, int codigo, int coordenadaX, int coordenadaY, String oldEpc) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update tags set nome='" + nome + "', epc='" + epc + "', codigo=" + codigo + ", coordenadaX="
					+ coordenadaX + ", coordenadaY=" + coordenadaY + " where epc='" + oldEpc + "'";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<Tag> selecTags() {
		List<Tag> tags = new ArrayList<Tag>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTags;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tags.add(getTag(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return tags;
	}

	@Override
	public List<Tag> selecTags(String epc) {
		List<Tag> tags = new ArrayList<Tag>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTags + " where tags.epc = '" + epc + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tags.add(getTag(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return tags;
	}

	@Override
	public boolean insertFalhas(int id, String msg, long data) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into falhas(idAGV, msg, data) values(" + id + ", '" + msg + "', '"
					+ Util.getDateTime(data) + "'" + ")";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteFalhas(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AlertFalhas> selecFalhas() {
		List<AlertFalhas> alerts = new ArrayList<AlertFalhas>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectFalhas + " order by id desc";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				alerts.add(getAlerts(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return alerts;
	}

	@Override
	public List<AlertFalhas> selectFalhasByDate(String dateS, String dateE) {
		dateS = dateS + " 00:00:00:000";
		dateE = dateE + " 23:59:59:999";
		List<AlertFalhas> alerts = new ArrayList<AlertFalhas>();
		Connection conexao;
		try {
			SimpleDateFormat formatterBR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
			Date dateStart = formatterBR.parse(dateS);
			Date dateEnd = formatterBR.parse(dateE);
			String sDateStart = Util.getDateTime(dateStart.getTime());
			String sDateEnd = Util.getDateTime(dateEnd.getTime());

			conexao = DriverManager.getConnection(url);

			String sql = strSelectFalhas + " WHERE falhas.data BETWEEN '" + sDateStart + "' AND '" + sDateEnd + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				alerts.add(getAlerts(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return alerts;
	}

	@Override
	public List<AlertFalhas> selectFalhasByDateReport(String dateS, String dateE, Integer idAGV) {
		List<AlertFalhas> alerts = new ArrayList<AlertFalhas>();
		Connection conexao;
		try {
			SimpleDateFormat formatterBR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			Date dateStart = formatterBR.parse(dateS);
			Date dateEnd = formatterBR.parse(dateE);
			String sDateStart = Util.getDateTime(dateStart.getTime());
			String sDateEnd = Util.getDateTime(dateEnd.getTime());

			conexao = DriverManager.getConnection(url);

			String sql = strSelectFalhas + " WHERE falhas.data BETWEEN '" + sDateStart + "' AND '" + sDateEnd
					+ "' AND falhas.idAGV = " + idAGV;
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				alerts.add(getAlerts(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return alerts;
	}

	@Override
	public List<LogTags> selectLogTagsByDate(String dateS, String dateE) {
		// System.out.println(dateS);
		dateS = dateS + " 00:00:00:000";
		dateE = dateE + " 23:59:59:999";
		List<LogTags> lstLogTags = new ArrayList<LogTags>();
		Connection conexao;
		try {
			SimpleDateFormat formatterBR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
			Date dateStart = formatterBR.parse(dateS);
			Date dateEnd = formatterBR.parse(dateE);
			String sDateStart = Util.getDateTime(dateStart.getTime());
			String sDateEnd = Util.getDateTime(dateEnd.getTime());

			conexao = DriverManager.getConnection(url);

			String sql = strSelectLogTags + " WHERE logTags.data BETWEEN '" + sDateStart + "' AND '" + sDateEnd + "'";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lstLogTags.add(getLogTags(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return lstLogTags;
	}

	@Override
	public List<LogTags> selectLogTagsByDateReport(String dateS, String dateE, Integer idAGV) {
		// System.out.println(dateS + " ate " + dateE);
		List<LogTags> lstLogTags = new ArrayList<LogTags>();
		Connection conexao;
		try {
			SimpleDateFormat formatterBR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			Date dateStart = formatterBR.parse(dateS);
			Date dateEnd = formatterBR.parse(dateE);
			String sDateStart = Util.getDateTime(dateStart.getTime());
			String sDateEnd = Util.getDateTime(dateEnd.getTime());

			conexao = DriverManager.getConnection(url);

			String sql = strSelectLogTags + " WHERE logTags.data BETWEEN '" + sDateStart + "' AND '" + sDateEnd
					+ "' AND logTags.idAGV = " + idAGV + " order by logTags.data";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lstLogTags.add(getLogTags(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return lstLogTags;
	}

	@Override
	public List<LogUsuario> selectLogUsuariosByDate(String dateS, String dateE) {
		dateS = dateS + " 00:00:00:000";
		dateE = dateE + " 23:59:59:999";
		List<LogUsuario> logUsr = new ArrayList<LogUsuario>();
		Connection conexao;
		try {
			SimpleDateFormat formatterBR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
			Date dateStart = formatterBR.parse(dateS);
			Date dateEnd = formatterBR.parse(dateE);
			String sDateStart = Util.getDateTime(dateStart.getTime());
			String sDateEnd = Util.getDateTime(dateEnd.getTime());

			conexao = DriverManager.getConnection(url);

			String sql = strSelectLogUsuarios + " WHERE logUsuarios.data BETWEEN '" + sDateStart + "' AND '" + sDateEnd
					+ "'";

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				logUsr.add(getLogUsuario(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return logUsr;
	}

	@Override
	public List<LogZoneTime> selectLogZoneTimeByDate(String dateS, String dateE) {
		dateS = dateS + " 00:00:00:000";
		dateE = dateE + " 23:59:59:999";
		List<LogZoneTime> logZT = new ArrayList<LogZoneTime>();
		Connection conexao;
		try {
			SimpleDateFormat formatterBR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
			Date dateStart = formatterBR.parse(dateS);
			Date dateEnd = formatterBR.parse(dateE);
			String sDateStart = Util.getDateTime(dateStart.getTime());
			String sDateEnd = Util.getDateTime(dateEnd.getTime());

			conexao = DriverManager.getConnection(url);

			String sql = strSelectLogZoneTime + " WHERE logZoneTime.data BETWEEN '" + sDateStart + "' AND '" + sDateEnd
					+ "' order by logZoneTime.data desc";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				logZT.add(getLogZoneTime(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return logZT;
	}

	@Override
	public List<LogZoneTime> selectLogZoneTimeByDateReport(String dateS, String dateE, Integer idAgv, Integer idZT) {
		dateS = dateS + " 00:00:00:000";
		dateE = dateE + " 23:59:59:999";
		List<LogZoneTime> logZT = new ArrayList<LogZoneTime>();
		Connection conexao;
		try {
			SimpleDateFormat formatterBR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
			Date dateStart = formatterBR.parse(dateS);
			Date dateEnd = formatterBR.parse(dateE);
			String sDateStart = Util.getDateTime(dateStart.getTime());
			String sDateEnd = Util.getDateTime(dateEnd.getTime());

			conexao = DriverManager.getConnection(url);

			String sql = strSelectLogZoneTime + " WHERE logZoneTime.data BETWEEN '" + sDateStart + "' AND '" + sDateEnd
					+ "' AND fkAgv = " + idAgv + " AND fkZoneTime=" + idZT + " order by logZoneTime.data desc";
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				logZT.add(getLogZoneTime(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return logZT;
	}

	@Override
	public boolean updateAGV(int id, String status, int bateria, int velocidade) {
		Connection conexao;
		try {
			if (status != null) {
				conexao = DriverManager.getConnection(url);
				String sql = "update agvs set status='" + status + "', bateria = " + bateria + ", velocidade = "
						+ velocidade + " where id=" + id;
//				 System.out.println(sql);
				PreparedStatement stmt = conexao.prepareStatement(sql);
				stmt.execute();
				stmt.close();
				conexao.close();
			}
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateAGV(int id, String status, long time, int bateria, int velocidade) {
		Connection conexao;
		try {
			if (status != null) {
				conexao = DriverManager.getConnection(url);
				String sql = "update agvs set oldStatusFalha='" + status + "',statusOldTime='" + Util.getDateTime(time)
						+ "', bateria = " + bateria + ", velocidade = " + velocidade + " where id=" + id;
				
				PreparedStatement stmt = conexao.prepareStatement(sql);
				stmt.execute();
				stmt.close();
				conexao.close();
			}
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<Rota> selectRotas() {
		List<Rota> rotas = new ArrayList<Rota>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectRotas;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				rotas.add(getRota(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return rotas;
	}

	@Override
	public boolean deleteRota(String nome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from rotas where nome='" + nome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertRota(String nome, String descricao) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into rotas(nome, descricao) values('" + nome + "', '" + descricao + "'" + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateRota(String nome, String descricao, String nomeOld) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update rotas set nome ='" + nome + "', descricao='" + descricao + "' where nome='" + nomeOld
					+ "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<TagsRotas> selectTagsRotas() {
		List<TagsRotas> tgRotas = new ArrayList<TagsRotas>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTagRota;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tgRotas.add(getTagRota(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return tgRotas;
	}

	@Override
	public List<TagsRotas> selectTagsRotas(String rota) {
		List<TagsRotas> tgRotas = new ArrayList<TagsRotas>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTagRota + " where rotas.nome='" + rota + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tgRotas.add(getTagRota(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return tgRotas;
	}

	@Override
	public boolean insertTagsRotas(String nome, int posicao, String idRotaExtra, String idTag, int setPoint,
			int velocidade, int temporizador, String girar, String estadoAtuador, String sensorObstaculo,
			String sinalSonoro, int tagDestino, int tagParada, int tagPitStop, String rota) {
		Connection conexao;

		if (!idRotaExtra.equals("null")) {
			idRotaExtra = "'" + idRotaExtra + "'";
		}

		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into tagsRota(nome, posicao, nomeRota, epc, addRota, setPoint, velocidade, temporizador, girar, estadoAtuador, sensorObstaculo, sinalSonoro, tagDestino, tagParada, pitStop) values('"
					+ nome + "'," + posicao + ",'" + rota + "','" + idTag + "'," + idRotaExtra + "," + setPoint + ","
					+ velocidade + "," + temporizador + ",'" + girar + "','" + estadoAtuador + "','" + sensorObstaculo
					+ "','" + sinalSonoro + "'," + tagDestino + "," + tagParada + "," + tagPitStop + ")";

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTagsRota(String id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from tagsRota where nome='" + id + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTagsRotas(String nome, int posicao, String idRota, String idTag, int setPoint, int velocidade,
			int temporizador, String girar, String estadoAtuador, String sensorObstaculo, String sinalSonoro,
			int tagDestino, int tagParada, int tagPitStop, String rota, String oldNome) {
		Connection conexao;
		try {
			if (!idRota.equals("null")) {
				idRota = "'" + idRota + "'";
			}
			conexao = DriverManager.getConnection(url);
			String sql = "update tagsRota set nome ='" + nome + "', posicao = " + posicao + ", nomeRota = '" + rota
					+ "', epc = '" + idTag + "', addRota = " + idRota + ", setPoint = " + setPoint + ", velocidade = "
					+ velocidade + ", temporizador = " + temporizador + ", girar = '" + girar + "', estadoAtuador = '"
					+ estadoAtuador + "', sensorObstaculo = '" + sensorObstaculo + "', sinalSonoro = '" + sinalSonoro
					+ "', tagDestino = " + tagDestino + ", tagParada = " + tagParada + ", pitStop = " + tagPitStop
					+ " where nome = '" + oldNome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<Cruzamento> selectCruzamentos() {
		List<Cruzamento> cruzamentos = new ArrayList<Cruzamento>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectCruzamentos;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cruzamentos.add(getCruzamento(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return cruzamentos;
	}

	@Override
	public List<Cruzamento> selectCruzamentosLogic() {
		List<Cruzamento> cruzamentos = new ArrayList<Cruzamento>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTagsCruzamentos;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Cruzamento cruzamento = null;
				TagCruzamento tc = getTagCruzamento(rs);
				for (int i = 0; cruzamentos != null && i < cruzamentos.size(); i++) {
					Cruzamento aux = cruzamentos.get(i);
					if (aux.getNome().equals(tc.getCruzamento().getNome())) {
						cruzamento = aux;
					}
				}

				if (cruzamento == null) {
					cruzamento = tc.getCruzamento();
					cruzamentos.add(cruzamento);
				}

				if (tc.getTipo().equals(TagCruzamento.tipoEntrada)) {
					if (cruzamento.getTagsEntrada() == null) {
						List<TagCruzamento> tgent = new ArrayList<TagCruzamento>();
						cruzamento.setTagsEntrada(tgent);
					}
					cruzamento.getTagsEntrada().add(tc);

				} else {
					if (cruzamento.getTagsSaida() == null) {
						List<TagCruzamento> tgent = new ArrayList<TagCruzamento>();
						cruzamento.setTagsSaida(tgent);
					}
					cruzamento.getTagsSaida().add(tc);
				}

			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return cruzamentos;
	}

	@Override
	public List<Semaforo> selectSemaforosLogic() {
		List<Semaforo> semaforos = new ArrayList<Semaforo>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTagsSemaforo;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Semaforo semaforo = null;
				TagSemaforos ts = getTagsSemaforo(rs);
				for (int i = 0; semaforos != null && i < semaforos.size(); i++) {
					Semaforo aux = semaforos.get(i);
					if (aux.getNome().equals(ts.getSemaforo().getNome())) {
						semaforo = aux;
					}
				}

				if (semaforo == null) {
					semaforo = ts.getSemaforo();
					semaforos.add(semaforo);
				}

				if (ts.getTipo().equals(Semaforo.VERMELHO)) {
					if (semaforo.getTagsSinalVermelho() == null) {
						List<TagSemaforos> tgent = new ArrayList<TagSemaforos>();
						semaforo.setTagsSinalVermelho(tgent);
					}
					semaforo.getTagsSinalVermelho().add(ts);
				} else if (ts.getTipo().equals(Semaforo.AMARELO)) {
					if (semaforo.getTagsSinalAmarelo() == null) {
						List<TagSemaforos> tgent = new ArrayList<TagSemaforos>();
						semaforo.setTagsSinalAmarelo(tgent);
					}
					semaforo.getTagsSinalAmarelo().add(ts);
				} else if (ts.getTipo().equals(Semaforo.VERDE)) {
					if (semaforo.getTagsSinalVerde() == null) {
						List<TagSemaforos> tgent = new ArrayList<TagSemaforos>();
						semaforo.setTagsSinalVerde(tgent);
					}
					semaforo.getTagsSinalVerde().add(ts);
				} else if (ts.getTipo().equals(Semaforo.PISCA_VERMELHO)) {
					if (semaforo.getTagsSinalPiscaVermelho() == null) {
						List<TagSemaforos> tgent = new ArrayList<TagSemaforos>();
						semaforo.setTagsSinalPiscaVermelho(tgent);
					}
					semaforo.getTagsSinalPiscaVermelho().add(ts);
				} else if (ts.getTipo().equals(Semaforo.PISCA_AMARELO)) {
					if (semaforo.getTagsSinalPiscaAmarelo() == null) {
						List<TagSemaforos> tgent = new ArrayList<TagSemaforos>();
						semaforo.setTagsSinalPiscaAmarelo(tgent);
					}
					semaforo.getTagsSinalPiscaAmarelo().add(ts);
				} else if (ts.getTipo().equals(Semaforo.PISCA_VERDE)) {
					if (semaforo.getTagsSinalPiscaVerde() == null) {
						List<TagSemaforos> tgent = new ArrayList<TagSemaforos>();
						semaforo.setTagsSinalPiscaVerde(tgent);
					}
					semaforo.getTagsSinalPiscaVerde().add(ts);
				}
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return semaforos;
	}

	@Override
	public boolean insertCruzamentos(String nome, String descricao) {
		Connection conexao;

		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into cruzamentos(nome, descricao) values('" + nome + "','" + descricao + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateCruzamentos(String nome, String descricao, String oldNome) {
		Connection conexao;
		try {

			conexao = DriverManager.getConnection(url);
			String sql = "update cruzamentos set nome ='" + nome + "', descricao = '" + descricao + "' where nome = '"
					+ oldNome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteCruzamento(String nome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from cruzamentos where nome='" + nome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<TagCruzamento> selectTagCruzamento() {
		List<TagCruzamento> TagCruzamentos = new ArrayList<TagCruzamento>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTagsCruzamentos;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TagCruzamentos.add(getTagCruzamento(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return TagCruzamentos;
	}

	@Override
	public boolean insertTagCruzamento(String nome, String idCruzamento, String idTag, String tipo) {
		Connection conexao;

		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into tagsCruzamento(nome, tipo, nomeCruzamento, epc) values('" + nome + "','" + tipo
					+ "','" + idCruzamento + "','" + idTag + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<TagSemaforos> selectTagsSemaforo() {
		List<TagSemaforos> tagsSemaforo = new ArrayList<TagSemaforos>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTagsSemaforo;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tagsSemaforo.add(getTagsSemaforo(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return tagsSemaforo;
	}

	@Override
	public int insertTagSemaforo(String nome, int idSemaforo, String idTag, String tipo) {
		Connection conexao;
		Long id = -1L;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into tagsSemaforo(nome, epc, fk_semaforo, tipo) values('" + nome + "', '" + idTag
					+ "', " + idSemaforo + ", '" + tipo + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql, 1);
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return id.intValue();
		}
		return id.intValue();
	}

	@Override
	public boolean updateTagCruzamento(String nome, String idCruzamento, String idTag, String tipo, String idNome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update tagsCruzamento set nome ='" + nome + "', tipo = '" + tipo + "', nomeCruzamento = '"
					+ idCruzamento + "', epc = '" + idTag + "' where nome = '" + idNome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTagCruzamento(String nome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from tagsCruzamento where nome='" + nome + "'";

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<RotaAGV> selectRotaAGV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertRotaAGV(String nome, int idAgv, String idRota) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRotaAGV(String nome, int idAgv, String idRota, String oldNome) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TagAtraso> selectTagAtraso() {
		List<TagAtraso> tagAtraso = new ArrayList<TagAtraso>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectTagAtraso;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tagAtraso.add(getTagAtraso(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return tagAtraso;
	}

	@Override
	public boolean insertTagAtraso(String nome, String tag, long time) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into timeTag(nome, epc, tempo) values('" + nome + "', '" + tag + "'," + time + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTagAtraso(String nome, String tag, long time, String oldNome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update timeTag set nome ='" + nome + "', epc='" + tag + "', tempo=" + time + " where nome='"
					+ oldNome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTagAtraso(String id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from timeTag where nome='" + id + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<LogTags> selectLogTags() {
		List<LogTags> logTags = new ArrayList<LogTags>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectLogTags;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				logTags.add(getLogTags(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return logTags;
	}

	@Override
	public boolean insertLogTags(long data, int idAgv, String msg, String epc, int bateria, int velocidade) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into logTags(idAgv, data, msg, epc, bateria, velocidade) values(" + idAgv + ", '" + Util.getDateTime(data)
					+ "','" + msg + "','" + epc + "', "+bateria+", "+velocidade+")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<Supermercado> selectSupermercados() {
		List<Supermercado> supermercados = new ArrayList<Supermercado>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectSupermercados;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				supermercados.add(getSupermercado(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return supermercados;
	}

	@Override
	public boolean insertSupermercados(String nome, String produto, long data, int id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into supermercados(nome, produto, id, data) values('" + nome + "', '" + produto + "',"
					+ id + ",'" + Util.getDateTime(data) + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateSupermercados(String nome, String produto, int id, long data, String oldNome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update supermercados set nome ='" + nome + "', data='" + Util.getDateTime(data)
					+ "', produto='" + produto + "', id=" + id + " where nome='" + oldNome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteSupermercados(String nome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from supermercados where nome='" + nome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<Equipamentos> selectEquipamentos() {
		List<Equipamentos> equipamentos = new ArrayList<Equipamentos>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectEquipamentos;
			// //System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				equipamentos.add(getEquipamentos(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return equipamentos;
	}

	@Override
	public boolean insertEquipamentos(String nome, String tipo, int rota, String id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into equipamentos(nome, tipo, rota, id) values('" + nome + "', '" + tipo + "'," + rota
					+ ",'" + id + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateEquipamentos(String nome, String tipo, int rota, String id, String oldNome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update equipamentos set nome ='" + nome + "', tipo='" + tipo + "', rota=" + rota + ", id='"
					+ id + "' where nome='" + oldNome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteEquipamentos(String nome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from equipamentos where nome='" + nome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<TagTempoParado> selectTagTempoParado() {
		List<TagTempoParado> tagTempoParado = new ArrayList<TagTempoParado>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectEquipamentos;
			// //System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tagTempoParado.add(getTagTempoParado(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return null;
		}

		return tagTempoParado;
	}

	@Override
	public boolean insertTagTempoParado(String nome, String epc) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into tagTempoParado(nome, epc) values('" + nome + "', '" + epc + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTagTempoParado(String nome, String epc, String oldNome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update tagTempoParado set nome='" + nome + "', epc='" + epc + "' where nome='" + oldNome
					+ "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTagTempoParado(String nome) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from tagTempoParado where nome='" + nome + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public int insertSemaforo(String nome, int fk_MeshSerial) {
		Connection conexao;
		Long id = -1L;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into semaforo(nome, fk_MeshSerial) values('" + nome + "', " + fk_MeshSerial + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql, 1);
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return id.intValue();
		}
		return id.intValue();
	}

	@Override
	public boolean deleteSemaforo(int id) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from semaforo where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateSemaforo(int id, String nome, int fk_MeshSerial) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update semaforo set nome='" + nome + "', fk_MeshSerial=" + fk_MeshSerial + " where id=" + id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTagSemaforo(int idTagSemaforo, String nome, int idSemaforo, String idTag, String tipo) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update tagsSemaforo set nome='" + nome + "', " + "epc='" + idTag + "', " + "fk_semaforo="
					+ idSemaforo + ", " + "tipo='" + tipo + "' where id=" + idTagSemaforo;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTagSemaforo(int idTagSemaforo) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from tagsSemaforo where id=" + idTagSemaforo;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<ZoneTime> selectZoneTime() {
		List<ZoneTime> zoneTime = new ArrayList<ZoneTime>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectZoneTime;
			// System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				zoneTime.add(getZoneTime(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return zoneTime;
	}

	@Override
	public int insertZoneTime(String descricao, String epcStart, String epcEnd, String limitTime) {
		Long id = -1L;
		SimpleDateFormat formatador = new SimpleDateFormat("mm:ss");
		Date data;
		Time tempo;
		try {
			data = formatador.parse(limitTime);
			tempo = new Time(data.getTime());
		} catch (ParseException e1) {
			new Log(e1);
			return id.intValue();
		}

		Connection conexao;

		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into zoneTime(description, fkTagStart, fkTagEnd, limitTime) values(" + "'" + descricao
					+ "', " + "'" + epcStart + "', " + "'" + epcEnd + "', " + "'" + tempo.toString() + "')";
			PreparedStatement stmt = conexao.prepareStatement(sql, 1);
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return id.intValue();
		}
		return id.intValue();
	}

	@Override
	public boolean updateZoneTime(int id, String descricao, String epcStart, String epcEnd, String limitTime) {
		SimpleDateFormat formatador = new SimpleDateFormat("mm:ss");
		Date data;
		Time tempo;
		try {
			data = formatador.parse(limitTime);
			tempo = new Time(data.getTime());
		} catch (ParseException e1) {
			new Log(e1);
			return false;
		}
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update zoneTime set " + "description='" + descricao + "', " + "fkTagStart='" + epcStart
					+ "', " + "fkTagEnd='" + epcEnd + "', " + "limitTime='" + tempo.toString() + "' " + "where id="
					+ id;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertLogZoneTimes(LogZoneTime lzt) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into logZoneTime(fkZoneTime, timeRoute, timeLost, timeLostObstacle, fkAgv) values("
					+ lzt.getZoneTime().getId() + ", '" + lzt.getTimeRoute() + "'" + ", '" + lzt.getsTimeLost() + "'"
					+ ", '" + Util.getDurationConvertString(lzt.getTimeLostObstacle()) + "'" + ", "
					+ lzt.getAgv().getId() + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteZoneTime(int idZoneTime) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from zoneTime where id=" + idZoneTime;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<LogZoneTime> selectLogZoneTime() {
		List<LogZoneTime> logZoneTime = new ArrayList<LogZoneTime>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectLogZoneTime;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				logZoneTime.add(getLogZoneTime(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return logZoneTime;
	}

	@Override
	public List<PortaMashSerial> selectGatesInByMesh(int idMesh) {
		List<PortaMashSerial> lstPortas = new ArrayList<PortaMashSerial>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectEntradasMesh + " where entradasMesh.fkMeshSerial = " + idMesh
					+ " order by entradasMesh.porta";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lstPortas.add(getPortaInMeshSerial(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return lstPortas;
	}

	@Override
	public boolean deletePortInMeshSerialByPortLarger(int indPorta, int idMesh) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from entradasMesh where porta > " + indPorta + " AND fkMeshSerial = " + idMesh;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deletePortInMeshSerial(int idMesh) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from entradasMesh where fkMeshSerial = " + idMesh;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertEntradasMeshUpdate(int idMesh, int ind) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into entradasMesh(porta, descricao, acionamento, fkMeshSerial) values(" + ind + ", 'E"
					+ ind + "', 'E" + ind + "_ON', " + idMesh + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<PortaSaidaMeshSerial> selectGatesOutByMesh(int idMesh) {
		List<PortaSaidaMeshSerial> lstPortas = new ArrayList<PortaSaidaMeshSerial>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectSaidasMesh + " where saidasMesh.fkMeshSerial = " + idMesh
					+ " order by saidasMesh.porta";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lstPortas.add(getPortaOutMeshSerial(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return lstPortas;
	}

	@Override
	public boolean deletePortOutMeshSerialByPortLarger(int indPorta, int idMesh) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "delete from saidasMesh where porta > " + indPorta + " AND fkMeshSerial = " + idMesh;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertSaidasMeshUpdate(int idMesh, int ind) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "insert into saidasMesh(porta, descricao, fkMeshSerial) values(" + ind + ", 'S" + ind + "', "
					+ idMesh + ")";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}

	@Override
	public List<Condicao> selectCondicoes() {
		List<Condicao> lstCondicoes = new ArrayList<Condicao>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectCondicao + " where meshSerial.id = 1 order by condicao.indice";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lstCondicoes.add(getCondicao(rs));
			}
			// Condicao cnd = getCondicao(rs);
			// String sqlIn = strSelectEntradasCondicao + " where
			// entradaCondicaoRel.fkCondicao = " + cnd.getId();
			// PreparedStatement stmtIn = conexao.prepareStatement(sqlIn);
			// stmtIn.execute();
			// ResultSet rsIn = stmt.executeQuery();
			//
			// while(rsIn.next()) {
			// cnd.getLstEntradasCondicao().add(getEntradasCondicao(rsIn));
			// }
			// stmtIn.close();
			// lstCondicoes.add(cnd);
			// }
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return lstCondicoes;
	}

	@Override
	public List<EntradaCondicao> selectEntradaCondicao(int idCondicao) {
		List<EntradaCondicao> lstEntrada = new ArrayList<EntradaCondicao>();

		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = strSelectEntradasCondicao + " where entradaCondicaoRel.fkCondicao = " + idCondicao;
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lstEntrada.add(getEntradasCondicao(rs));
			}
			stmt.close();
			conexao.close();
		} catch (SQLException | ParseException e) {
			new Log(e);
			return null;
		}

		return lstEntrada;
	}

	@Override
	public boolean updatePortIn(String porta, int idMesh, String status) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update entradasMesh set " + "status='" + status + "'" + "where porta=" + porta
					+ " AND fkMeshSerial=" + idMesh;
			System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;

	}
	@Override
	public boolean updateGatesOutMeshSerial(PortaSaidaMeshSerial psms) {
		Connection conexao;
		try {
			conexao = DriverManager.getConnection(url);
			String sql = "update saidasMesh set "
						+ "status='" + psms.getStatus() + "'"
						+ "where id=" + psms.getId();
			System.out.println(sql);
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (Exception e) {
			new Log(e);
			return false;
		}
		return true;
	}
}
