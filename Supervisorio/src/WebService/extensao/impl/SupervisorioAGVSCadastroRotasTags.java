package WebService.extensao.impl;

import java.io.PrintStream;

import AGVS.Data.ConfigProcess;
import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.Command;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.http.Request;
import WebService.http.Response;

public class SupervisorioAGVSCadastroRotasTags implements Command{

	private ConvertPAGinHTML cph;
	private String[] files = {PathFilesPAG.cadastroRotasTags};
	
	
	@Override
	public void execute(Request req, Response resp) throws Exception {
		// TODO Auto-generated method stub
		
		if(!ConfigProcess.bd().conectarBancoDados()){
			PrintStream out = resp.getPrintStream();
			out.println("<meta http-equiv=\"refresh\" content=1;url=\"/SupervisorioAGVS/Configuracao/BD\">");
			out.flush();
			return;
		}
		if(!Login.login(req, resp, Login.tokenCadastroRotas)) {
			Login.redirectLoginInvalid(req, resp);
			return;
		}
//methodGerarTabelaRotas
		Tags tags = new Tags();
		Methods mt = new Methods();
		tags.add(Keys.keyGenerateDashboard, mt.methodGenerateDashboardCadastroRotas);
		tags.add(Keys.keyGenerateUser, mt.methodGenerateNomeUsuario);
		tags.add(Keys.keyGerarComboboxRotas, mt.methodGerarComboboxRotas);
		tags.add(Keys.keyGerarComboboxTags, mt.methodGerarComboboxTags);
		tags.add(Keys.keyGerarComboboxGirar, mt.methodGerarComboboxGirar);
		tags.add(Keys.keyGerarComboboxTagDestino, mt.methodGerarComboboxTagDestino);
		tags.add(Keys.keyGerarComboboxTagParada, mt.methodGerarComboboxTagParada);
		tags.add(Keys.keyGerarComboboxVelocidade, mt.methodGerarComboboxVelocidades);
		tags.add(Keys.keyGerarComboboxSetPoint, mt.methodGerarComboboxSetPoints);
		tags.add(Keys.keyGerarComboboxEstadoAtuador, mt.methodGerarComboboxEstadoAtuador);
		tags.add(Keys.keyGerarComboboxSinalSonoro, mt.methodGerarComboboxSinalSonoro);
		tags.add(Keys.keyGerarComboboxSensorObstaculo, mt.methodGerarComboboxSensorObstaculo);
		tags.add(Keys.keyGerarComboboxTagPitStop, mt.methodGerarComboboxTagPitStop);
		tags.add(Keys.keyNomeRota, mt.methodGenerateNomeRota);
		tags.add(Keys.keyGerarTabelaRotasTags, mt.methodGerarTabelaTagsRotas);
		mt.methodGerarTabelaTagsRotas.setArgs(req.getParam("nome"));
		mt.methodGenerateNomeRota.setArgs(req.getParam("nome"));
		mt.methodGenerateNomeUsuario.setArgs(req.getCookie("name").getValue());
		cph = new ConvertPAGinHTML();
		cph.generateHTML(files, tags);
		
		Home(req, resp);
	}
	
	private void Home(Request req, Response resp) {
		PrintStream out = resp.getPrintStream();
		out.println(cph.getHtml(files[0]));
		out.flush();
	}

}
