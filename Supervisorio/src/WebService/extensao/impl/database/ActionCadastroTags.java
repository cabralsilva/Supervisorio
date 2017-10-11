package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.PosicaoInicioTurno;
import AGVS.Data.Semaforo;
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
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionCadastroTags implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		// System.out.println(req.toString());

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteTag(req.getGetParams().get(TagsValues.paramID))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Tag " + req.getGetParams().get(TagsValues.paramID), Login.strExcluir);
						html = "OK";
					}

				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueSemafaroParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (req.getGetParams().containsKey(TagsValues.paramCMD)) {
						for (int i = 0; DatabaseStatic.semafaros != null && i < DatabaseStatic.semafaros.size(); i++) {
							Semaforo sf = DatabaseStatic.semafaros.get(i);
							if (sf.getNome().equals(req.getGetParams().get(TagsValues.paramID))) {
								if ("vd".equals(req.getGetParams().get(TagsValues.paramCMD))) {
									sf.clear();
									sf.sinalVerde();
								}

								if ("vm".equals(req.getGetParams().get(TagsValues.paramCMD))) {
									sf.clear();
									sf.sinalVermelho();
								}

								if ("am".equals(req.getGetParams().get(TagsValues.paramCMD))) {
									sf.clear();
									sf.sinalAmarelo();
								}

								if ("des".equals(req.getGetParams().get(TagsValues.paramCMD))) {
									sf.clear();
									sf.sinalDesligar();
								}

								if ("vda".equals(req.getGetParams().get(TagsValues.paramCMD))) {
									sf.clear();
									sf.sinalPiscaVerde();
								}

								if ("vma".equals(req.getGetParams().get(TagsValues.paramCMD))) {
									sf.clear();
									sf.sinalPiscaVermelho();
								}

								if ("ama".equals(req.getGetParams().get(TagsValues.paramCMD))) {
									sf.clear();
									sf.sinalAlerta();
								}
								html = "OK";
							}
						}

					}
				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueAddParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {

					List<AGV> agvsAdd = new ArrayList<AGV>();
					List<AGV> agvs = ConfigProcess.bd().selecAGVS();

					for (int i = 0; agvs != null && i < agvs.size(); i++) {
						if (req.getGetParams().containsKey(agvs.get(i).getNome())) {
							if (req.getGetParams().get(agvs.get(i).getNome()).equals("true")) {
								agvsAdd.add(agvs.get(i));

							}
						}
					}

					boolean ok = true;
					for (int i = 0; DatabaseStatic.pits != null && i < DatabaseStatic.pits.size(); i++) {
						PosicaoInicioTurno pit = DatabaseStatic.pits.get(i);
						if (agvsAdd.size() > 0) {
							if (pit.getAgv() != null && pit.getAgv().getId() == agvsAdd.get(0).getId()) {
								ok = false;
							}
						}
					}
					if (ok) {
						for (int i = 0; DatabaseStatic.pits != null && i < DatabaseStatic.pits.size(); i++) {
							PosicaoInicioTurno pit = DatabaseStatic.pits.get(i);
							if (pit.getNome().equals(req.getGetParams().get(TagsValues.paramID))) {
								if (agvsAdd.size() > 0)
									pit.setAgv(agvsAdd.get(0));
							}
						}
						html = "OK";
					} else {
						html = "AGV esta em um ponto de inicio.";
					}

				}
			} else {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (req.getGetParams().containsKey(TagsValues.paramEpc)) {
						if (req.getGetParams().containsKey(TagsValues.paramNome)) {
							if (req.getGetParams().containsKey(TagsValues.paramCodigo)) {
								if (req.getGetParams().containsKey(TagsValues.paramXi)) {
									if (req.getGetParams().containsKey(TagsValues.paramYi)) {

										if (req.getGetParams().get(TagsValues.paramAction)
												.equals(TagsValues.valueSalvarParamAction)) {
											if (ConfigProcess.bd().insertTag(
													req.getGetParams().get(TagsValues.paramEpc),
													req.getGetParams().get(TagsValues.paramNome),
													Integer.parseInt(req.getGetParams().get(TagsValues.paramCodigo)),
													Integer.parseInt(req.getGetParams().get(TagsValues.paramXi)),
													Integer.parseInt(req.getGetParams().get(TagsValues.paramYi)))) {
												ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
														req.getCookies().get(Login.strKeyName).getValue(),
														"Adicionou Tag " + req.getGetParams().get(TagsValues.paramNome),
														Login.strAdicionar);
												html = "OK";
											}

										} else {

											if (ConfigProcess.bd().updateTag(
													req.getGetParams().get(TagsValues.paramEpc),
													req.getGetParams().get(TagsValues.paramNome),
													Integer.parseInt(req.getGetParams().get(TagsValues.paramCodigo)),
													Integer.parseInt(req.getGetParams().get(TagsValues.paramXi)),
													Integer.parseInt(req.getGetParams().get(TagsValues.paramYi)),
													req.getGetParams().get(TagsValues.paramID))) {
												ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
														req.getCookies().get(Login.strKeyName).getValue(),
														"Alterou Tag " + req.getGetParams().get(TagsValues.paramNome),
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

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
