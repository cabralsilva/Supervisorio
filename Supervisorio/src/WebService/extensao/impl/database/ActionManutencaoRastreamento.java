package WebService.extensao.impl.database;

import java.io.PrintStream;
import AGVS.Serial.DatabaseStatic;
import WebService.extensao.CommandDB;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionManutencaoRastreamento implements CommandDB {

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "";
//		if (DatabaseStatic.lstRastreamento != null) {
//			for(String lstR : DatabaseStatic.lstRastreamento){
//				html += lstR + "<br>";
//			}
//		}
		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
