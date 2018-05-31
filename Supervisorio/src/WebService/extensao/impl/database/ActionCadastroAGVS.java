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
import WebService.http.Config;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionCadastroAGVS implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		 System.out.println(req.toString());

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {

			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueEmergenciaParamAction)) {
				// emergencia
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					List<AGV> agvs = ConfigProcess.bd().selecAGVS();
					for (int i = 0; agvs != null && i < agvs.size(); i++) {
						AGV agv = agvs.get(i);

						if (agv.getId() == Integer.parseInt(req.getGetParams().get(TagsValues.paramID))) {
							AGV.enviarEmergencia(agv.getMac16(), agv.getMac64(), agv.getIp());
							html = "OK";
						}
					}
				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueRotaParamAction)) {
				// Rotas
				System.out.println("COmando ROTA");
				if (req.getGetParams().containsKey(TagsValues.paramID)) {

					int r0 = 255;
					int c0 = 255;
					int r1 = 255;
					int c1 = 255;
					int r2 = 255;
					int c2 = 255;
					int r3 = 255;
					int c3 = 255;
					int r4 = 255;
					int c4 = 255;
					int r5 = 255;
					int c5 = 255;
					int r6 = 255;
					int c6 = 255;
					int r7 = 255;
					int c7 = 255;
					int r8 = 255;
					int c8 = 255;
					int r9 = 255;
					int c9 = 255;

					if (req.getGetParams().containsKey(TagsValues.paramR0)) {
						try {
							r0 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR0));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR1)) {
						try {
							r1 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR1));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR2)) {
						try {
							r2 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR2));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR3)) {
						try {
							r3 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR3));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR4)) {
						try {
							r4 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR4));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR5)) {
						try {
							r5 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR5));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR6)) {
						try {
							r6 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR6));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR7)) {
						try {
							r7 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR7));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR8)) {
						try {
							r8 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR8));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramR9)) {
						try {
							r9 = Integer.parseInt(req.getGetParams().get(TagsValues.paramR9));
						} catch (Exception e) {

						}
					}

					if (req.getGetParams().containsKey(TagsValues.paramC0)) {
						try {
							c0 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC0));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC1)) {
						try {
							c1 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC1));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC2)) {
						try {
							c2 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC2));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC3)) {
						try {
							c3 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC3));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC4)) {
						try {
							c4 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC4));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC5)) {
						try {
							c5 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC5));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC6)) {
						try {
							c6 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC6));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC7)) {
						try {
							c7 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC7));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC8)) {
						try {
							c8 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC8));
						} catch (Exception e) {

						}
					}
					if (req.getGetParams().containsKey(TagsValues.paramC9)) {
						try {
							c9 = Integer.parseInt(req.getGetParams().get(TagsValues.paramC9));
						} catch (Exception e) {

						}
					}
					System.out.println("COmando OK");
					List<AGV> agvs = ConfigProcess.bd().selecAGVS();
					for (int i = 0; agvs != null && i < agvs.size(); i++) {
						AGV agv = agvs.get(i);

						int[][] rotas = new int[10][2];
						rotas[0][0] = r0 & 0xff;
						rotas[0][1] = c0 & 0xff;
						rotas[1][0] = r1 & 0xff;
						rotas[1][1] = c1 & 0xff;
						rotas[2][0] = r2 & 0xff;
						rotas[2][1] = c2 & 0xff;
						rotas[3][0] = r3 & 0xff;
						rotas[3][1] = c3 & 0xff;
						rotas[4][0] = r4 & 0xff;
						rotas[4][1] = c4 & 0xff;
						rotas[5][0] = r5 & 0xff;
						rotas[5][1] = c5 & 0xff;
						rotas[6][0] = r6 & 0xff;
						rotas[6][1] = c6 & 0xff;
						rotas[7][0] = r7 & 0xff;
						rotas[7][1] = c7 & 0xff;
						rotas[8][0] = r8 & 0xff;
						rotas[8][1] = c8 & 0xff;
						rotas[9][0] = r9 & 0xff;
						rotas[9][1] = c9 & 0xff;

						for (int j = 0; j < rotas.length; j++) {
							if (rotas[j][0] == 0 || rotas[j][0] > 255 || rotas[j][0] < 0) {
								rotas[j][0] = 255;
							}
							if (rotas[j][1] == 0 || rotas[j][1] > 255 || rotas[j][1] < 0) {
								rotas[j][1] = 255;
							}
						}

						if (agv.getId() == Integer.parseInt(req.getGetParams().get(TagsValues.paramID))) {
							AGV.sendRota(rotas, agv.getMac16(), agv.getMac64());
							html = "OK";
						}
					}
				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valuePlayParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					List<AGV> agvs = ConfigProcess.bd().selecAGVS();
					for (int i = 0; agvs != null && i < agvs.size(); i++) {
						AGV agv = agvs.get(i);

						if (agv.getId() == Integer.parseInt(req.getGetParams().get(TagsValues.paramID))) {
							Config config = Config.getInstance();

							if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
								AGV.enviarParar(agv.getMac16(), agv.getMac64(), agv.getIp());
							}

							AGV.enviarPlay(agv.getMac16(), agv.getMac64(), agv.getIp());
							html = "OK";
						}
					}
				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valuePitStopParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					List<AGV> agvs = ConfigProcess.bd().selecAGVS();
					for (int i = 0; agvs != null && i < agvs.size(); i++) {
						AGV agv = agvs.get(i);

						if (agv.getId() == Integer.parseInt(req.getGetParams().get(TagsValues.paramID))) {
							AGV.enviarPararAC(agv.getMac16(), agv.getMac64(), agv.getIp());
							html = "OK";
						}
					}
				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valuePlayReParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					List<AGV> agvs = ConfigProcess.bd().selecAGVS();
					for (int i = 0; agvs != null && i < agvs.size(); i++) {
						AGV agv = agvs.get(i);

						if (agv.getId() == Integer.parseInt(req.getGetParams().get(TagsValues.paramID))) {

							Config config = Config.getInstance();

							if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
								AGV.enviarParar(agv.getMac16(), agv.getMac64(), agv.getIp());
							}

							AGV.enviarPlayRE(agv.getMac16(), agv.getMac64(), agv.getIp());
							html = "OK";
						}
					}
				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valuePararParamAction)) {
				// parar
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					List<AGV> agvs = ConfigProcess.bd().selecAGVS();
					for (int i = 0; agvs != null && i < agvs.size(); i++) {
						AGV agv = agvs.get(i);

						if (agv.getId() == Integer.parseInt(req.getGetParams().get(TagsValues.paramID))) {
							AGV.enviarParar(agv.getMac16(), agv.getMac64(), agv.getIp());
							html = "OK";
						}
					}
				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				// deletar
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteAGV(Integer.parseInt(req.getGetParams().get(TagsValues.paramID)))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu AGV " + req.getGetParams().get(TagsValues.paramID), Login.strExcluir);
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
															req.getGetParams().get(TagsValues.paramMac16),
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
															req.getGetParams().get(TagsValues.paramMac16),
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
