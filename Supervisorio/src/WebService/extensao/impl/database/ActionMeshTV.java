package WebService.extensao.impl.database;

import java.io.PrintStream;
import java.util.List;

import AGVS.Data.ConfigProcess;
import AGVS.Data.MeshSerial;
import AGVS.Data.PortaMashSerial;
import AGVS.Serial.DatabaseStatic;
import WebService.extensao.CommandDB;
import WebService.http.Config;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionMeshTV implements CommandDB {

	private boolean verifyStatusPort(String port, List<PortaMashSerial> lstPms) {
		for(PortaMashSerial pms : lstPms) {
				if (!pms.getPorta().equals(null) && pms.getPorta().equals(port)) {
					if (pms.getStatus()!=null) {
//						System.out.println("Porta " + pms.getPorta() + " -------- ACIONAM " + pms.getAcionamento() + "-------- " + pms.getStatus());
//						System.out.println(pms.getStatus().equals(pms.getAcionamento()));
						if (pms.getStatus().equals(pms.getAcionamento())) {
							return true;
						}
					}
					return false;
				}
			
			
		}
		return false;
	}
	
	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {


		Config config = Config.getInstance();
		String html = "        ";//reservado para a cor do plano de fundo 8 caracteres
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_FIAT)) {
			MeshSerial ms = DatabaseStatic.mashs.get(0);
			 
			html += "<div class='col-md-6' style='margin-right: 0px; width: 49.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: white;'>"+
					"<center><span style='font-size: 20px;'>LADO DIREITO</span></center>";

			if (!verifyStatusPort("3", ms.getLstPms()) && 
				!verifyStatusPort("4", ms.getLstPms())) {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green;'>"+
					"<div style='position: relative; top: 50%; transform: translateY(-50%);'>5-7</div></div>"+
					"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green;'>"+
					"<div style='position: relative; top: 50%; transform: translateY(-50%);'>5-7</div></div>"+
					"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green;'>"+
					"<div style='position: relative; top: 50%; transform: translateY(-50%);'>5-7</div></div>";
			}else if(verifyStatusPort("3", ms.getLstPms()) &&
					!verifyStatusPort("4", ms.getLstPms())) {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: yellow;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>4</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: yellow;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>4</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: #fff;'>"+
						"</div>";
			}else if(verifyStatusPort("3", ms.getLstPms()) &&
					verifyStatusPort("4", ms.getLstPms())) {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: red;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>0-3</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: #fff;'>"+
						"</div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: #fff;'>"+
						"</div>";
			}else {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: red;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>Abastecendo...</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: yellow;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>Abastecendo...</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>Abastecendo...</div></div>";
			}
					
			html += "</div>";
			
			html += "<div class='col-md-6' style='margin-right: 4px; width: 49.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px;background-color: white;'>" +
					"<center><span style='font-size: 20px;'>LADO ESQUERDO</span></center>";
			if (!verifyStatusPort("5", ms.getLstPms()) &&
				!verifyStatusPort("6", ms.getLstPms())) {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center; margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green;'>"+
					"<div style='position: relative; top: 50%; transform: translateY(-50%);'>5-7</div></div>"+
					"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center; margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green;'>"+
					"<div style='position: relative; top: 50%; transform: translateY(-50%);'>5-7</div></div>"+
					"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center; margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green;'>"+
					"<div style='position: relative; top: 50%; transform: translateY(-50%);'>5-7</div></div>";
			}else if(!verifyStatusPort("5", ms.getLstPms()) &&
					verifyStatusPort("6", ms.getLstPms())) {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: yellow;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>4</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: yellow;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>4</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: #fff;'>"+
						"</div>";
			}else if(verifyStatusPort("5", ms.getLstPms()) &&
					verifyStatusPort("6", ms.getLstPms())) {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: red;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>0-3</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: #fff;'>"+
						"</div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: #fff;'>"+
						"</div>";
			}else {
				html += "<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: red;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>Abastecendo...</div></div>"+
						"<div class='col-md-offset-4 c"
						+ "ol-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: yellow;'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>Abastecendo...</div></div>"+
						"<div class='col-md-offset-4 col-md-4' style='font-weight: bold; color: black; font-size: x-large; text-align: center;margin-right: 4px; height: 30%; border: #cdcdcd 1px solid; border-radius: 50px; background-color: green'>"+
						"<div style='position: relative; top: 50%; transform: translateY(-50%);'>Abastecendo...</div></div>";
			}
			
			
			html += "</div>";
		}else {

			html = "<span style='font-size: 20px;'>#MESH</span><br>" + 
							"<div class='col-md-12 col-sm-12 col-xs-12'>";
			for (MeshSerial ms : DatabaseStatic.mashs) {
				for (PortaMashSerial pms : ms.getLstPms()) {		
					html += "<div class='col-md-1 col-sm-2 col-xs-3 margin-bottom-10'>" + 
							"	<label class='control-label'>" + pms.getNome() + "</label>" + 
							"</div>";
					if (pms.getStatus() != null) {
						if (pms.getStatus().equals(pms.getAcionamento())) {
							html += "<div class='col-md-5 col-sm-4 col-xs-3 margin-bottom-20'>" + 
									"	<div style='height: 20px; width: 50px; background-color: #f96868; border-radius: 10px;'></div>" + 
									"</div>";
							
						}else {
							html += "<div class='col-md-5 col-sm-4 col-xs-3 margin-bottom-20'>" + 
									"	<div style='height: 20px; width: 50px; background-color: gray; border-radius: 10px;'></div>" + 
									"</div>";
						}
					}else {
						html += "<div class='col-md-5 col-sm-4 col-xs-3 margin-bottom-20'>" + 
								"	<div style='height: 20px; width: 50px; background-color: #efa61b; border-radius: 10px;'></div>" + 
								"</div>";
					}
				}
			}
			html += "</div>";
			html += "<div class='col-md-12 col-sm-12 col-xs-12' style='top: 45px;'>"
					+ "	<div class='col-md-offset-2 col-md-1 col-sm-1 col-xs-2' style='height: 20px;  background-color: #f96868; border-radius: 10px;'></div><div class='col-md-2 col-sm-2 col-xs-2'>Ativado</div>  "
					+ "	<div class='col-md-1 col-sm-2 col-xs-2' style='height: 20px;  background-color: gray; border-radius: 10px;'></div><div class='col-md-2 col-sm-2 col-xs-2'>Desativado</div>  "
					+ "	<div class='col-md-1 col-sm-2 col-xs-2' style='height: 20px;  background-color: #efa61b; border-radius: 10px;'></div><div class='col-md-2 col-sm-2 col-xs-2'>Indefinido</div>  "
				+"</div>";	
		}
		
		
		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}
}
