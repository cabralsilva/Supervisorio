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

public class ActionCadastroRotasTags implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		//System.out.println(req.toString());

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteTagsRota(req.getGetParams().get(TagsValues.paramID))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu na rota o tag " + req.getGetParams().get(TagsValues.paramID),
								Login.strExcluir);
						html = "OK";
					}

				}
			} else {

				if (req.getGetParams().containsKey(TagsValues.paramNome)) {
					if (req.getGetParams().containsKey(TagsValues.paramAddRota)) {
						if (req.getGetParams().containsKey(TagsValues.paramSetPoint)) {
							if (req.getGetParams().containsKey(TagsValues.paramVelocidade)) {
								if (req.getGetParams().containsKey(TagsValues.paramAtuador)) {
									if (req.getGetParams().containsKey(TagsValues.paramPitStop)) {
										if (req.getGetParams().containsKey(TagsValues.paramTagParada)) {
											if (req.getGetParams().containsKey(TagsValues.paramTagDestino)) {
												if (req.getGetParams().containsKey(TagsValues.paramPosicao)) {
													if (req.getGetParams().containsKey(TagsValues.paramSinalSonoro)) {
														if (req.getGetParams().containsKey(TagsValues.paramIDOld)) {
															if (req.getGetParams().containsKey(TagsValues.paramRota)) {
																if (req.getGetParams()
																		.containsKey(TagsValues.paramSensorObstaculo)) {
																	if (req.getGetParams()
																			.containsKey(TagsValues.paramComando)) {

																		if (req.getGetParams()
																				.get(TagsValues.paramAction)
																				.equals(TagsValues.valueSalvarParamAction)) {
																			if (ConfigProcess.bd().insertTagsRotas(
																					req.getGetParams()
																							.get(TagsValues.paramNome),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramPosicao)),
																					req.getGetParams()
																							.get(TagsValues.paramAddRota),
																					req.getGetParams()
																							.get(TagsValues.paramTag),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramSetPoint)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramVelocidade)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramComando)),
																					req.getGetParams()
																							.get(TagsValues.paramGirar),
																					req.getGetParams()
																							.get(TagsValues.paramAtuador),
																					req.getGetParams()
																							.get(TagsValues.paramSensorObstaculo),
																					req.getGetParams()
																							.get(TagsValues.paramSinalSonoro),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramTagDestino)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramTagParada)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramPitStop)),
																					req.getGetParams().get(
																							TagsValues.paramRota))) {
																				ConfigProcess.bd().insertLogUsuarios(
																						System.currentTimeMillis(),
																						req.getCookies()
																								.get(Login.strKeyName)
																								.getValue(),
																						"Adicionou na rota o tag "
																								+ req.getGetParams()
																										.get(TagsValues.paramNome),
																						Login.strAdicionar);
																				html = "OK";
																			}

																		} else {

																			if (ConfigProcess.bd().updateTagsRotas(
																					req.getGetParams()
																							.get(TagsValues.paramNome),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramPosicao)),
																					req.getGetParams()
																							.get(TagsValues.paramAddRota),
																					req.getGetParams()
																							.get(TagsValues.paramTag),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramSetPoint)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramVelocidade)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramComando)),
																					req.getGetParams()
																							.get(TagsValues.paramGirar),
																					req.getGetParams()
																							.get(TagsValues.paramAtuador),
																					req.getGetParams()
																							.get(TagsValues.paramSensorObstaculo),
																					req.getGetParams()
																							.get(TagsValues.paramSinalSonoro),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramTagDestino)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramTagParada)),
																					Integer.parseInt(req.getGetParams()
																							.get(TagsValues.paramPitStop)),
																					req.getGetParams()
																							.get(TagsValues.paramRota),
																					req.getGetParams().get(
																							TagsValues.paramIDOld))) {
																				ConfigProcess.bd().insertLogUsuarios(
																						System.currentTimeMillis(),
																						req.getCookies()
																								.get(Login.strKeyName)
																								.getValue(),
																						"Alterou na rota o tag "
																								+ req.getGetParams()
																										.get(TagsValues.paramNome),
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