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

public class ActionCadastroTagsSemaforo implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		System.out.println(req.toString());

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {

			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				// deletar
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteTagSemaforo(Integer.parseInt(req.getGetParams().get(TagsValues.paramID)))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Tag " + req.getGetParams().get(TagsValues.paramID) + " do Semáforo",
								Login.strExcluir);
							
						html = "OK";
					}

				}

			} else {

				if (req.getGetParams().containsKey(TagsValues.paramNome)) {
					if (req.getGetParams().containsKey(TagsValues.paramTipo)) {
						if (req.getGetParams().containsKey(TagsValues.paramSemaforo)) {
							if (req.getGetParams().containsKey(TagsValues.paramEpc)) {
								if (req.getGetParams().get(TagsValues.paramAction)
										.equals(TagsValues.valueSalvarParamAction)) {
									int idInsert;
									if ((idInsert = ConfigProcess.bd().insertTagSemaforo(
											req.getGetParams().get(TagsValues.paramNome),
											Integer.parseInt(req.getGetParams().get(TagsValues.paramSemaforo)),
											req.getGetParams().get(TagsValues.paramEpc),
											req.getGetParams().get(TagsValues.paramTipo)))>0) {
										ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
												req.getCookies().get(Login.strKeyName).getValue(),
												"Adicionou Tag " + req.getGetParams().get(TagsValues.paramNome)
														+ " do Semáforo",
												Login.strAdicionar);
										html = "ID"+((int)Math.log10(idInsert) + 1)+idInsert;
									}

								} else {
									int idInsert;
									if (ConfigProcess.bd().updateTagSemaforo(
											Integer.parseInt(req.getGetParams().get(TagsValues.paramID)),
											req.getGetParams().get(TagsValues.paramNome),
											Integer.parseInt(req.getGetParams().get(TagsValues.paramSemaforo)),
											req.getGetParams().get(TagsValues.paramEpc),
											req.getGetParams().get(TagsValues.paramTipo)
											)) {
										ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
												req.getCookies().get(Login.strKeyName).getValue(),
												"Alterou Tag " + req.getGetParams().get(TagsValues.paramNome)
														+ " do Semáforo",
												Login.strAlterar);
										idInsert = Integer.parseInt(req.getGetParams().get(TagsValues.paramID));
										html = "ID"+((int)Math.log10(idInsert) + 1)+idInsert;
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
