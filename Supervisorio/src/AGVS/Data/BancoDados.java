package AGVS.Data;

import java.util.List;

public interface BancoDados {
	public void criarBancoDados();

	public boolean conectarBancoDados();

	public List<Usuario> selectUsuarios();

	public boolean insertUsuarios(String login, String nome, String email, String password, String permissao);

	public boolean deleteUsuario(String id);

	public boolean updateUsuarios(String id, String login, String nome, String email, String password,
			String permissao);

	public boolean updateUsuarios(String id, String idPassword, String password);

	public boolean updateUsuarios(String id, String login, String nome, String email, String permissao);

	public boolean updateUsuarios(String id, String liberado);

	public List<LogUsuario> selectLogUsuarios();

	public boolean deleteLogUsuarios(String id);

	public boolean insertLogUsuarios(long data, String nome, String descricao, String tipo);

	public boolean criarBanco();

	public boolean insertAGV(int id, String nome, String status, String tipo, String mac64, String mac16);
	
	public int insertMesh(int id, String nome, String mac16, String mac64, int entradas, int saidas);
	
	public int insertSemaforo(String nome, int fk_MeshSerial);

	public boolean deleteAGV(int id);
	
	public boolean deleteMesh(int id);
	
	public boolean deleteSemaforo(int id);

	public boolean updateAGV(int id, String nome, String status, String tipo, String mac64, String mac16, int oldId);
	
	public boolean updateMesh(int id, String nome, String mac16, String mac64, int entradas, int saidas);
	
	public boolean updateSemaforo(int id, String nome, int fk_MeshSerial);

	public List<AGV> selecAGVS();

	public List<AGV> selecAGVSLastSixUpdate();
	
	public List<MeshSerial> selectMeshSerial();
	
	public List<Semaforo> selectSemaforo();

	public AGV selecAGVS(int id);

	public boolean insertLine(String descricao, int xInicial, int yInicial, int xFinal, int yFinal, String cor);

	public boolean deleteLine(String descricao);

	public boolean updateLine(String descricao, int xInicial, int yInicial, int xFinal, int yFinal, String cor,
			String oldDescricao);

	public List<Line> selecLines();

	public boolean insertTag(String epc, String nome, int codigo, int coordenadaX, int coordenadaY);

	public boolean deleteTag(String id);

	public boolean updateTag(String epc, String nome, int codigo, int coordenadaX, int coordenadaY, String oldEpc);

	public List<Tag> selecTags();

	public List<Tag> selecTags(String epc);

	public boolean updateAGV(int id, String tagAtual, int bateria, long tagAtualTime, int atraso, String status);
	
	public boolean updateIPAGV(int id, String ip);

	public boolean updateAGV(int id, int atraso);

	public boolean updateAGV(int id, String status);

	public boolean updateAGV(int id, String status, long time);

	public boolean insertFalhas(int id, String msg, long data);

	public boolean deleteFalhas(int id);

	public List<AlertFalhas> selecFalhas();
	
	public List<AlertFalhas> selectFalhasByDate(String dateS, String dateE);
	public List<AlertFalhas> selectFalhasByDateReport(String dateS, String dateE, Integer idAgv);
	
	public List<LogTags> selectLogTagsByDate(String dateS, String dateE);
	public List<LogTags> selectLogTagsByDateReport(String dateS, String dateE, Integer idAGV);
	
	public List<LogUsuario> selectLogUsuariosByDate(String dateS, String dateE);
	
	public List<LogZoneTime> selectLogZoneTimeByDate(String dateS, String dateE);

	public List<LogZoneTime> selectLogZoneTimeByDateReport(String dateS, String dateE, Integer idAgv, Integer idZT);

	public List<Rota> selectRotas();

	public boolean deleteRota(String nome);

	public boolean insertRota(String nome, String descricao);

	public boolean updateRota(String nome, String descricao, String nomeOld);

	public List<TagsRotas> selectTagsRotas();

	public List<TagsRotas> selectTagsRotas(String rota);

	public boolean insertTagsRotas(String nome, int posicao, String idRotaExtra, String idTag, int setPoint,
			int velocidade, int temporizador, String girar, String estadoAtuador, String sensorObstaculo,
			String sinalSonoro, int tagDestino, int tagParada, int tagPitStop, String rota);

	public boolean updateTagsRotas(String nome, int posicao, String idRota, String idTag, int setPoint, int velocidade,
			int temporizador, String girar, String estadoAtuador, String sensorObstaculo, String sinalSonoro,
			int tagDestino, int tagParada, int tagPitStop, String rota, String oldNome);

	public boolean deleteTagsRota(String id);

	public List<Cruzamento_OLD> selectCruzamentos();
	
	public List<TagSemaforos> selectTagsSemaforo();
	
	public List<Cruzamento_OLD> selectCruzamentosLogic();
	
	public List<Semaforo> selectSemaforosLogic();

	public boolean insertCruzamentos(String nome, String descricao);

	public boolean updateCruzamentos(String nome, String descricao, String oldNome);

	public boolean deleteCruzamento(String nome);

	public List<TagCruzamento> selectTagCruzamento();

	public boolean insertTagCruzamento(String nome, String idCruzamento, String idTag, String tipo);

	public boolean updateTagCruzamento(String nome, String idCruzamento, String idTag, String tipo, String idNome);

	public boolean deleteTagCruzamento(String nome);

	public List<RotaAGV> selectRotaAGV();

	public boolean insertRotaAGV(String nome, int idAgv, String idRota);

	public boolean updateRotaAGV(String nome, int idAgv, String idRota, String oldNome);

	public List<TagAtraso> selectTagAtraso();

	public boolean insertTagAtraso(String nome, String tag, long time);

	public boolean updateTagAtraso(String nome, String tag, long time, String oldNome);

	public boolean deleteTagAtraso(String id);

	public List<LogTags> selectLogTags();

	public boolean insertLogTags(long data, int idAgv, String msg, String epc);

	public List<Supermercado> selectSupermercados();

	public boolean insertSupermercados(String nome, String produto, long data, int id);

	public boolean updateSupermercados(String nome, String produto, int id, long data, String oldNome);

	public boolean deleteSupermercados(String nome);

	public List<Equipamentos> selectEquipamentos();

	public boolean insertEquipamentos(String nome, String tipo, int rota, String id);

	public boolean updateEquipamentos(String nome, String tipo, int rota, String id, String oldNome);

	public boolean deleteEquipamentos(String nome);
	
	public List<TagTempoParado> selectTagTempoParado();
	
	public boolean insertTagTempoParado(String nome, String epc);
	
	public boolean updateTagTempoParado(String nome, String epc, String oldNome);
	
	public boolean deleteTagTempoParado(String nome);

	public int insertTagSemaforo(String nome, int idSemaforo, String idTag, String tipo);
	
	public boolean updateTagSemaforo(int idTagSemaforo, String nome, int idSemaforo, String idTag, String tipo);

	public boolean deleteTagSemaforo(int idTagSemaforo);
	
	public List<ZoneTime> selectZoneTime();
	
	public int insertZoneTime(String description, String epcTagStart, String epcTagEnd, String limitTime);

	public boolean updateZoneTime(int id, String description, String epcTagStart, String epcTagEnd, String limitTime);
	
	public boolean insertLogZoneTimes(LogZoneTime lzt);
	
	public boolean deleteZoneTime(int idZoneTime);
	
	public List<LogZoneTime> selectLogZoneTime();
	
	public boolean insertEntradasMesh(int idMesh, List<PortaMashSerial> lstPms);

	public boolean insertSaidasMesh(int idMesh, List<PortaSaidaMeshSerial> lstPsms);

	public boolean insertEntradasMeshUpdate(int idMesh, int ind);
	
	public List<PortaMashSerial> selectGatesInByMesh(int idMesh);
	
	public boolean deletePortInMeshSerialByPortLarger(int indPorta, int idMesh);

	public boolean deletePortInMeshSerial(int idMesh);
	
	public List<PortaSaidaMeshSerial> selectGatesOutByMesh(int idMesh);
	
	public boolean deletePortOutMeshSerialByPortLarger(int indPorta, int idMesh);
	
	public boolean insertSaidasMeshUpdate(int idMesh, int ind);
	
	public List<Condicao> selectCondicoes();
	
	public List<EntradaCondicao> selectEntradaCondicao(int idCondicao);

	public boolean updateAGVFrequency(int iAgv, int iFrequencia);
	
	public boolean updatePortIn(String porta, int idMesh, String status);
}
