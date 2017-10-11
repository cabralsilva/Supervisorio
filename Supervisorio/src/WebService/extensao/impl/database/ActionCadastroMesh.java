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
import AGVS.Data.ConfigProcess;
import AGVS.Data.MeshSerial;
import AGVS.Data.PortaMashSerial;
import AGVS.Data.PortaSaidaMeshSerial;
import AGVS.Serial.DatabaseStatic;
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

public class ActionCadastroMesh implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
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
							if (req.getGetParams().get(TagsValues.paramAction)
									.equals(TagsValues.valueSalvarParamAction)) {
								int idInsert;
								
								if ((idInsert = ConfigProcess.bd().insertMesh(
										Integer.parseInt(req.getGetParams().get(TagsValues.paramID)),
										req.getGetParams().get(TagsValues.paramNome),
										req.getGetParams().get(TagsValues.paramMac16),
										req.getGetParams().get(TagsValues.paramMac64),
										Integer.parseInt(req.getGetParams().get(TagsValues.paramEntradas)),
										Integer.parseInt(req.getGetParams().get(TagsValues.paramSaidas))
										))>0) {
									
									ConfigProcess.bd().insertEntradasMesh(idInsert, 
											Integer.parseInt(req.getGetParams().get(TagsValues.paramEntradas)),
											Integer.parseInt(req.getGetParams().get(TagsValues.paramSaidas)));
									
									
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
