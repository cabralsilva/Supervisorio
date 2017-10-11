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

public class ActionUsuario implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Não foi possivel realizar comando";

		System.out.println(req.toString());

		if (!Login.login(req, resp, Login.tokenUsuarios)) {
			html = "Usuario sem permissao para realizar comando";
			PrintStream out = resp.getPrintStream();
			out.println(html);
			out.flush();
			resp.flush();
			return;
		}

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteUsuario(req.getGetParams().get(TagsValues.paramID))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Usuario " + req.getGetParams().get(TagsValues.paramID), Login.strExcluir);
						html = "OK";
					}
				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueAlterarSenhaParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramPassword) && !req.getParam(TagsValues.paramPassword).equals("")) {
					if (ConfigProcess.bd().updateUsuarios(req.getCookie("name").getValue(),
							req.getCookie("password").getValue(),
							Util.gerarCriptMD5(req.getParam(TagsValues.paramPassword)))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Alterou senha do Usuario " + req.getCookie("name").getValue(), Login.strAlterar);
						html = "US";
					}
				}
			} else if (req.getGetParams().get(TagsValues.paramAction)
					.equals(TagsValues.valueEditPermissaoParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {

					String liberado = "";
					for (int i = 0; i < Login.listTokens.length; i++) {
						String temp = Util.removerAcentos(new String(Login.listTokens[i].getBytes(), "UTF-8"));

						if (req.getGetParams().containsKey(temp)) {

							// System.out.println(Login.listTokens[i] + "-" +
							// req.getGetParams().get(Login.listTokens[i]));

							if (req.getGetParams().get(temp).equals("true")) {
								if (!liberado.equals("")) {
									liberado += "#";
								}
								liberado += temp;
							}
						}
					}
					if (ConfigProcess.bd().updateUsuarios(req.getGetParams().get(TagsValues.paramID), liberado)) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Alterou Permissao do Usuario " + req.getGetParams().get(TagsValues.paramDescricao),
								Login.strAlterar);
						html = "OK";

					}

				}
			} else {
				if (req.getGetParams().containsKey(TagsValues.paramLogin)) {
					if (req.getGetParams().containsKey(TagsValues.paramNome)) {
						if (req.getGetParams().containsKey(TagsValues.paramPassword)) {
							if (req.getGetParams().containsKey(TagsValues.paramEmail)) {
								if (req.getGetParams().containsKey(TagsValues.paramID)) {
									if (req.getGetParams().containsKey(TagsValues.paramPermissao)) {

										if (req.getGetParams().get(TagsValues.paramAction)
												.equals(TagsValues.valueSalvarParamAction)) {
											if (ConfigProcess.bd().insertUsuarios(
													req.getGetParams().get(TagsValues.paramLogin),
													req.getGetParams().get(TagsValues.paramNome),
													req.getGetParams().get(TagsValues.paramEmail),
													req.getGetParams().get(TagsValues.paramPassword),
													req.getGetParams().get(TagsValues.paramPermissao))) {
												ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
														req.getCookies().get(Login.strKeyName).getValue(),
														"Adicionou Usuario "
																+ req.getGetParams().get(TagsValues.paramDescricao),
														Login.strAdicionar);
												html = "OK";
											}
										} else {
											if (req.getGetParams().get(TagsValues.paramPassword).equals("")) {
												if (ConfigProcess.bd().updateUsuarios(
														req.getGetParams().get(TagsValues.paramID),
														req.getGetParams().get(TagsValues.paramLogin),
														req.getGetParams().get(TagsValues.paramNome),
														req.getGetParams().get(TagsValues.paramEmail),
														req.getGetParams().get(TagsValues.paramPermissao))) {
													ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
															req.getCookies().get(Login.strKeyName).getValue(),
															"Alterou Usuario "
																	+ req.getGetParams().get(TagsValues.paramDescricao),
															Login.strAlterar);
													html = "OK";
												}
											} else {
												if (ConfigProcess.bd().updateUsuarios(
														req.getGetParams().get(TagsValues.paramID),
														req.getGetParams().get(TagsValues.paramLogin),
														req.getGetParams().get(TagsValues.paramNome),
														req.getGetParams().get(TagsValues.paramEmail),
														req.getGetParams().get(TagsValues.paramPassword),
														req.getGetParams().get(TagsValues.paramPermissao))) {
													ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
															req.getCookies().get(Login.strKeyName).getValue(),
															"Alterou Usuario "
																	+ req.getGetParams().get(TagsValues.paramDescricao),
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
