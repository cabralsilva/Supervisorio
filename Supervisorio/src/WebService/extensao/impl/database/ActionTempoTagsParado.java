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

public class ActionTempoTagsParado implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		//System.out.println(req.toString());
		
		if (req.getGetParams().containsKey(TagsValues.paramAction)) {

			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				// deletar
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteTagAtraso(req.getGetParams().get(TagsValues.paramID))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Tempo Tags " + req.getGetParams().get(TagsValues.paramID), Login.strExcluir);
						html = "OK";
					}

				}

			} else {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (req.getGetParams().containsKey(TagsValues.paramNome)) {
						if (req.getGetParams().containsKey(TagsValues.paramStatus)) {
							if (req.getGetParams().containsKey(TagsValues.paramIP)) {
								if (!Util.validate(req.getGetParams().get(TagsValues.paramIP))) {
									html = "IP Invalido";
								} else {
									if (req.getGetParams().containsKey(TagsValues.paramMac64)) {
										if (!Util.verificaMac(req.getGetParams().get(TagsValues.paramMac64),
												Util.size_mac64)) {
											html = "MAC 64 Invalido";
										} else {

											if (req.getGetParams().containsKey(TagsValues.paramTipo)) {
												if (req.getGetParams().get(TagsValues.paramAction)
														.equals(TagsValues.valueSalvarParamAction)) {
													if (ConfigProcess.bd().insertAGV(
															Integer.parseInt(
																	req.getGetParams().get(TagsValues.paramID)),
															req.getGetParams().get(TagsValues.paramNome),
															req.getGetParams().get(TagsValues.paramStatus),
															req.getGetParams().get(TagsValues.paramTipo),
															req.getGetParams().get(TagsValues.paramMac64),
															req.getGetParams().get(TagsValues.paramIP))) {
														ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
																req.getCookies().get(Login.strKeyName).getValue(),
																"Adicionou AGV "
																		+ req.getGetParams().get(TagsValues.paramNome),
																Login.strAdicionar);
														html = "OK";
													}

												} else {

													if (ConfigProcess.bd().updateAGV(
															Integer.parseInt(
																	req.getGetParams().get(TagsValues.paramID)),
															req.getGetParams().get(TagsValues.paramNome),
															req.getGetParams().get(TagsValues.paramStatus),
															req.getGetParams().get(TagsValues.paramTipo),
															req.getGetParams().get(TagsValues.paramMac64),
															req.getGetParams().get(TagsValues.paramIP),
															Integer.parseInt(
																	req.getGetParams().get(TagsValues.paramIDOld)))) {
														ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
																req.getCookies().get(Login.strKeyName).getValue(),
																"Alterou AGV "
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

		}

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
