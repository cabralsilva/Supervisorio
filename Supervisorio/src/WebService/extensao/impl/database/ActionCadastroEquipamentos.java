package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;

import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.ConfigProcess;
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
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionCadastroEquipamentos implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		// System.out.println(req.toString());

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramNome)) {
					if (ConfigProcess.bd().deleteEquipamentos(req.getGetParams().get(TagsValues.paramNome))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Equipamento " + req.getGetParams().get(TagsValues.paramNome),
								Login.strExcluir);
						html = "OK";
					}

				}

			} else {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (req.getGetParams().containsKey(TagsValues.paramNome)) {
						if (req.getGetParams().containsKey(TagsValues.paramTipo)) {
							if (req.getGetParams().containsKey(TagsValues.paramRota)) {
								if (req.getGetParams().containsKey(TagsValues.paramNomeOld)) {

									if (req.getGetParams().get(TagsValues.paramAction)
											.equals(TagsValues.valueSalvarParamAction)) {
										if (ConfigProcess.bd().insertEquipamentos(
												req.getGetParams().get(TagsValues.paramNome),
												req.getGetParams().get(TagsValues.paramTipo),
												Integer.parseInt(req.getGetParams().get(TagsValues.paramRota)),
												req.getGetParams().get(TagsValues.paramID))) {
											ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
													req.getCookies().get(Login.strKeyName).getValue(),
													"Adicionou Supermercado "
															+ req.getGetParams().get(TagsValues.paramNome),
													Login.strAdicionar);
											html = "OK";
										}

									} else {

										if (ConfigProcess.bd().updateEquipamentos(
												req.getGetParams().get(TagsValues.paramNome),
												req.getGetParams().get(TagsValues.paramTipo),
												Integer.parseInt(req.getGetParams().get(TagsValues.paramRota)),
												req.getGetParams().get(TagsValues.paramID),
												req.getGetParams().get(TagsValues.paramNomeOld))) {
											ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
													req.getCookies().get(Login.strKeyName).getValue(),
													"Alterou Supermercado "
															+ req.getGetParams().get(TagsValues.paramNome),
													Login.strAlterar);
											html = "OK";
										}

									}
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
