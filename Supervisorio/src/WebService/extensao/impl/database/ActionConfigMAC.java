package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFrame;

import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.MeshSerial;
import AGVS.Serial.DatabaseStatic;
import AGVS.Util.Log;
import AGVS.Util.Util;
import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.Dashboards;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.Command;
import WebService.extensao.CommandDB;
import WebService.extensao.impl.Reload;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionConfigMAC implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Não foi possivel realizar comando";
		
		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueSalvarParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramMac64)) {
					DatabaseStatic.macSupervisorio = req.getGetParams().get(TagsValues.paramMac64);
					try {
						List<AGV> AGVS = ConfigProcess.bd().selecAGVS();
						for (int i = 0; AGVS != null && i < AGVS.size(); i++) {
							AGV agv = AGVS.get(i);
							AGV.enviarMacSupervisorio(agv.getMac16(), agv.getMac64(), DatabaseStatic.macSupervisorio, agv.getIp());
						}
						
						List<MeshSerial> meshs = ConfigProcess.bd().selectMeshSerial();
						for (int i = 0; meshs != null && i < meshs.size(); i++) {
							MeshSerial mesh = meshs.get(i);
							AGV.enviarMacSupervisorio(mesh.getMac16(), mesh.getMac64(), DatabaseStatic.macSupervisorio, mesh.getIp());
						}
						html = "OK";
					} catch (Exception e) {
						new Log(e);
						System.out.println(e.getMessage());
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
