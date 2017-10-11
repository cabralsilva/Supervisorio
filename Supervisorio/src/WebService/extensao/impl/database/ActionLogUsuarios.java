package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFrame;

import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.AGV;
import AGVS.Data.AlertFalhas;
import AGVS.Data.ConfigProcess;
import AGVS.Data.LogTags;
import AGVS.Data.LogUsuario;
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

public class ActionLogUsuarios implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";
		if (req.getGetParams().containsKey(TagsValues.paramAction)) 
		{
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueSearchParamAction)) 
			{
				if (req.getGetParams().containsKey(TagsValues.paramDateStart) && 
						req.getGetParams().containsKey(TagsValues.paramDateEnd)) 
				{
					String dateS = req.getGetParams().get(TagsValues.paramDateStart);
					String dateE = req.getGetParams().get(TagsValues.paramDateEnd);
					
					List<LogUsuario> lstLogs = ConfigProcess.bd().selectLogUsuariosByDate(dateS, dateE);
					int j = 0;
					html = "OK";
					for (int i = lstLogs.size() - 1; lstLogs != null && i > -1 && j < 1000; i--) {
						j++;
						LogUsuario lg = lstLogs.get(i);

						html += "<tr>\n" 
									+ "<th>" + lg.getData() + "</th>\n" 
									+ "<th>" + lg.getNome() + "</th>\n"
									+ "<th>" + lg.getTipo() + "</th>\n" 
									+ "<th>" + lg.getDescricao() + "</th>\n"  
								+ " </tr>\n";

					}					
				}
			}
		}

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
