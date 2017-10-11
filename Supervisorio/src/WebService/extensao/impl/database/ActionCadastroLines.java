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

public class ActionCadastroLines implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		//System.out.println(req.toString());

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteLine(req.getGetParams().get(TagsValues.paramID))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Line " + req.getGetParams().get(TagsValues.paramID), Login.strExcluir);
						html = "OK";
					}

				}

			} else {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (req.getGetParams().containsKey(TagsValues.paramDescricao)) {
						if (req.getGetParams().containsKey(TagsValues.paramXi)) {
							if (req.getGetParams().containsKey(TagsValues.paramYi)) {
								if (req.getGetParams().containsKey(TagsValues.paramXf)) {
									if (req.getGetParams().containsKey(TagsValues.paramYf)) {
										if (req.getGetParams().containsKey(TagsValues.paramCor)) {
											if (req.getGetParams().get(TagsValues.paramAction)
													.equals(TagsValues.valueSalvarParamAction)) {
												if (ConfigProcess.bd().insertLine(
														req.getGetParams().get(TagsValues.paramDescricao),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramXi)),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramYi)),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramXf)),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramYf)),
														req.getGetParams().get(TagsValues.paramCor))) {
													ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
															req.getCookies().get(Login.strKeyName).getValue(),
															"Adicionou Line "
																	+ req.getGetParams().get(TagsValues.paramNome),
															Login.strAdicionar);
													html = "OK";
												}

											} else {

												if (ConfigProcess.bd().updateLine(
														req.getGetParams().get(TagsValues.paramDescricao),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramXi)),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramYi)),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramXf)),
														Integer.parseInt(req.getGetParams().get(TagsValues.paramYf)),
														req.getGetParams().get(TagsValues.paramCor),
														req.getGetParams().get(TagsValues.paramID))) {
													ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
															req.getCookies().get(Login.strKeyName).getValue(),
															"Alterou Line "
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
			}

		}

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
