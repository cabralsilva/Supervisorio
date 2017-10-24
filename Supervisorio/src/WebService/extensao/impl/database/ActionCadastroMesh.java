package WebService.extensao.impl.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import AGVS.Data.ConfigProcess;
import AGVS.Data.PausablePlayer;
import AGVS.Data.PortaMashSerial;
import AGVS.Data.PortaSaidaMeshSerial;
import AGVS.Serial.DatabaseStatic;
import WebService.extensao.CommandDB;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionCadastroMesh implements CommandDB {
	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {

//		FileInputStream input = new FileInputStream("media/001.mp3"); 
//      PausablePlayer player = new PausablePlayer(input);
//      System.out.println("starting");
      // start playing
//      DatabaseStatic.player.play();
//      System.out.println("playing");
      // after 5 secs, pause
//      Thread.sleep(5000);
//      DatabaseStatic.player.pause();     
//      System.out.println("pause");
      // after 5 secs, resume
//      Thread.sleep(5000);
//		DatabaseStatic.streamMedia = new FileInputStream("media/001.mp3"); 
//		DatabaseStatic.player = new PausablePlayer(DatabaseStatic.streamMedia);
//		DatabaseStatic.player.play();
//      DatabaseStatic.player.resume();
//      System.out.println("PLAY");
//      Thread.sleep(5000);
//      Thread.sleep(5000);
//      Thread.sleep(5000);
      
//      DatabaseStatic.player.pause();
      
//      DatabaseStatic.streamMedia.available();
//      DatabaseStatic.player.stop();
//      DatabaseStatic.player.close();
//      System.out.println("STOP");
		
		String html = "Nao foi possivel realizar comando";
		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueResetParamAction)) {
				// resetar
				DatabaseStatic.resetMeshs();
				ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
						req.getCookies().get(Login.strKeyName).getValue(), "Resetou as Mesh ", Login.strAlterar);
				html = "OK";
			}else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				// deletar
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteMesh(Integer.parseInt(req.getGetParams().get(TagsValues.paramID)))) {
						
						ConfigProcess.bd().deletePortInMeshSerial(Integer.parseInt(req.getGetParams().get(TagsValues.paramID)));
						
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Mesh " + req.getGetParams().get(TagsValues.paramID), Login.strExcluir);
						html = "OK";
					}
				}
			} else {
				if (req.getGetParams().containsKey(TagsValues.paramNome)) {
					if (req.getGetParams().containsKey(TagsValues.paramMac16)) {
						if (req.getGetParams().containsKey(TagsValues.paramMac64)) {
							
							List<PortaMashSerial> lstPms = new ArrayList<PortaMashSerial>();
							PortaMashSerial pms1 = new PortaMashSerial(req.getParam(TagsValues.paramE1), "1", req.getParam(TagsValues.paramEa1), "E1_OFF", null);
							PortaMashSerial pms2 = new PortaMashSerial(req.getParam(TagsValues.paramE2), "2", req.getParam(TagsValues.paramEa2), "E2_OFF", null);
							PortaMashSerial pms3 = new PortaMashSerial(req.getParam(TagsValues.paramE3), "3", req.getParam(TagsValues.paramEa3), "E3_OFF", null);
							PortaMashSerial pms4 = new PortaMashSerial(req.getParam(TagsValues.paramE4), "4", req.getParam(TagsValues.paramEa4), "E4_OFF", null);
							PortaMashSerial pms5 = new PortaMashSerial(req.getParam(TagsValues.paramE5), "5", req.getParam(TagsValues.paramEa5), "E5_OFF", null);
							PortaMashSerial pms6 = new PortaMashSerial(req.getParam(TagsValues.paramE6), "6", req.getParam(TagsValues.paramEa6), "E6_OFF", null);
							PortaMashSerial pms7 = new PortaMashSerial(req.getParam(TagsValues.paramE7), "7", req.getParam(TagsValues.paramEa7), "E7_OFF", null);
							PortaMashSerial pms8 = new PortaMashSerial(req.getParam(TagsValues.paramE8), "8", req.getParam(TagsValues.paramEa8), "E8_OFF", null);
							
							lstPms.add(pms1); lstPms.add(pms2);
							lstPms.add(pms3); lstPms.add(pms4);
							lstPms.add(pms5); lstPms.add(pms6);
							lstPms.add(pms7); lstPms.add(pms8);
							
							List<PortaSaidaMeshSerial> lstPsms = new ArrayList<PortaSaidaMeshSerial>();
							PortaSaidaMeshSerial psms1 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS1), "1", "S1_OFF", null);
							PortaSaidaMeshSerial psms2 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS2), "2", "S2_OFF", null);
							PortaSaidaMeshSerial psms3 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS3), "3", "S3_OFF", null);
							PortaSaidaMeshSerial psms4 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS4), "4", "S4_OFF", null);
							PortaSaidaMeshSerial psms5 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS5), "5", "S5_OFF", null);
							PortaSaidaMeshSerial psms6 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS6), "6", "S6_OFF", null);
							PortaSaidaMeshSerial psms7 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS7), "7", "S7_OFF", null);
							PortaSaidaMeshSerial psms8 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS8), "8", "S8_OFF", null);
							PortaSaidaMeshSerial psms9 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS9), "9", "S9_OFF", null);
							PortaSaidaMeshSerial psms10 = new PortaSaidaMeshSerial(req.getParam(TagsValues.paramS10), "10", "S10_OFF", null);
							
							lstPsms.add(psms1); lstPsms.add(psms2);
							lstPsms.add(psms3); lstPsms.add(psms4);
							lstPsms.add(psms5); lstPsms.add(psms6);
							lstPsms.add(psms7); lstPsms.add(psms8);
							lstPsms.add(psms9); lstPsms.add(psms10);
							
							if (req.getGetParams().get(TagsValues.paramAction)
									.equals(TagsValues.valueSalvarParamAction)) {
								int idInsert;
								

								if ((idInsert = ConfigProcess.bd().insertMesh(
										Integer.parseInt(req.getGetParams().get(TagsValues.paramID)),
										req.getGetParams().get(TagsValues.paramNome),
										req.getGetParams().get(TagsValues.paramMac16),
										req.getGetParams().get(TagsValues.paramMac64),
										lstPms.size(), lstPsms.size()))>0) {
									
									ConfigProcess.bd().insertEntradasMesh(idInsert, lstPms);
									ConfigProcess.bd().insertSaidasMesh(idInsert, lstPsms);
									
									
									ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
											req.getCookies().get(Login.strKeyName).getValue(),
											"Adicionou Mesh " + req.getGetParams().get(TagsValues.paramNome),
											Login.strAdicionar);
									html = "ID"+((int)Math.log10(idInsert) + 1)+idInsert;
								}
							} else {
								int idInsert;
								if (ConfigProcess.bd().updateMesh(
										Integer.parseInt(req.getGetParams().get(TagsValues.paramID)),
										req.getGetParams().get(TagsValues.paramNome),
										req.getGetParams().get(TagsValues.paramMac16),
										req.getGetParams().get(TagsValues.paramMac64),
										Integer.parseInt(req.getGetParams().get(TagsValues.paramEntradas)),
										Integer.parseInt(req.getGetParams().get(TagsValues.paramSaidas)))) {
										
									idInsert = Integer.parseInt(req.getGetParams().get(TagsValues.paramID));
									
									//entradas
									List<PortaMashSerial> listPmsIn = ConfigProcess.bd().selectGatesInByMesh(idInsert);
									int numNewPortsIn = Integer.parseInt(req.getGetParams().get(TagsValues.paramEntradas));
									if (numNewPortsIn < listPmsIn.size()) {
										ConfigProcess.bd().deletePortInMeshSerialByPortLarger(numNewPortsIn, idInsert);
									}else if (numNewPortsIn > listPmsIn.size()) {
										for(int i = listPmsIn.size() + 1; i <= numNewPortsIn; i++) {
											ConfigProcess.bd().insertEntradasMeshUpdate(idInsert, i);
										}
									}
									
									//saidas
									List<PortaSaidaMeshSerial> listPmsOut = ConfigProcess.bd().selectGatesOutByMesh(idInsert);
									int numNewPortsOut = Integer.parseInt(req.getGetParams().get(TagsValues.paramSaidas));
									if (numNewPortsOut < listPmsOut.size()) {
										ConfigProcess.bd().deletePortOutMeshSerialByPortLarger(numNewPortsOut, idInsert);
									}else if (numNewPortsOut > listPmsOut.size()) {
										for(int i = listPmsOut.size() + 1; i <= numNewPortsOut; i++) {
											ConfigProcess.bd().insertSaidasMeshUpdate(idInsert, i);
										}
									}
									
									ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
											req.getCookies().get(Login.strKeyName).getValue(),
											"Alterou Mesh " + req.getGetParams().get(TagsValues.paramNome),
											Login.strAlterar);
									html = "ID"+((int)Math.log10(idInsert) + 1)+idInsert;
								}

							}

						}
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
