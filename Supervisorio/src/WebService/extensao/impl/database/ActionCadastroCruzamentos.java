package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Cruzamento_OLD;
import AGVS.Data.FuncaoPos;
import AGVS.Data.PosicaoInicioTurno;
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

public class ActionCadastroCruzamentos implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {

			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueResetParamAction)) {
				// resetar
				DatabaseStatic.resetCruzamentos();
				ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
						req.getCookies().get(Login.strKeyName).getValue(), "Resetou os Cruzamentos ", Login.strAlterar);
				html = "OK";
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valuePlayParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					for (int i = 0; DatabaseStatic.tagCruzamentoMash != null
							&& i < DatabaseStatic.tagCruzamentoMash.size(); i++) {
						if (DatabaseStatic.tagCruzamentoMash.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							DatabaseStatic.tagCruzamentoMash.get(i).liberar();
							html = "OK";
							break;
						}
					}

				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valuePlayClParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					for (int i = 0; DatabaseStatic.cancelas != null && i < DatabaseStatic.cancelas.size(); i++) {
						if (DatabaseStatic.cancelas.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							DatabaseStatic.cancelas.get(i).liberar();
							html = "OK";
							break;
						}
					}

				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueAtivadoParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					for (int i = 0; DatabaseStatic.cancelas != null && i < DatabaseStatic.cancelas.size(); i++) {
						if (DatabaseStatic.cancelas.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							DatabaseStatic.cancelas.get(i).ativar();
							html = "OK";
							break;
						}
					}

				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDesativadoParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					for (int i = 0; DatabaseStatic.cancelas != null && i < DatabaseStatic.cancelas.size(); i++) {
						if (DatabaseStatic.cancelas.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							DatabaseStatic.cancelas.get(i).desativar();
							html = "OK";
							break;
						}
					}

				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valuePlay2ParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					for (int i = 0; DatabaseStatic.cruzamentoMash != null
							&& i < DatabaseStatic.cruzamentoMash.size(); i++) {
						if (DatabaseStatic.cruzamentoMash.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							DatabaseStatic.cruzamentoMash.get(i).liberar();
							html = "OK";
							break;
						}
					}

				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueCreateParamAction)) {

				List<PosicaoInicioTurno> pits = DatabaseStatic.pits;

				boolean ok = true;
				for (int i = 0; pits != null && i < pits.size(); i++) {
					if (pits.get(i).getAgv() == null) {
						ok = false;
					}
				}

				if (ok) {
					List<Cruzamento_OLD> cruzamentos = DatabaseStatic.cruzamentos;
					List<FuncaoPos> fps = DatabaseStatic.funcPos;
					for (int i = 0; cruzamentos != null && i < cruzamentos.size(); i++) {
						Cruzamento_OLD cz = cruzamentos.get(i);
						cz.limparTudo();
						for (int j = 0; fps != null && j < fps.size(); j++) {
							FuncaoPos fp = fps.get(j);
							if (fp.getPos() == 0 && fp.getStatus().equals(FuncaoPos.RODANDO)
									&& fp.getCt().getNome().equals(cz.getNome())) {
								fp.getPit().getAgv().setStatus(AGV.statusRodando);
								cz.verificaCruzamento(fp.getPit().getAgv(),
										cz.getTagsEntrada().get(0).getTag().getEpc());

							}
						}
						for (int j = 0; j < 1000; j++) {
							for (int k = 0; fps != null && k < fps.size(); k++) {
								FuncaoPos fp = fps.get(k);
								if (fp.getPos() == j && cz.getNome().equals(fp.getCt().getNome())
										&& fp.getStatus().equals(FuncaoPos.FILAESPERA)) {
									fp.getPit().getAgv().setStatus(AGV.statusRodando);
									cz.verificaCruzamento(fp.getPit().getAgv(),
											cz.getTagsEntrada().get(0).getTag().getEpc());
								}
							}

						}

					}
					html = "OK";
					for (int i = 0; pits != null && i < pits.size(); i++) {
						pits.get(i).setAgv(null);
					}
				} else {
					html = "Possui pontos de paradas sem AGV selecionado.";
				}

			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDelParamAction)) {
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

					for (int i = 0; DatabaseStatic.cruzamentos != null && i < DatabaseStatic.cruzamentos.size(); i++) {
						if (DatabaseStatic.cruzamentos.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							for (int j = 0; agvsAdd != null && j < agvsAdd.size(); j++) {
								agvsAdd.get(j).setStatus(AGV.statusManual);
								DatabaseStatic.cruzamentos.get(i).execLiberaVerificaAGV(agvsAdd.get(j));
							}
						}
					}
					html = "OK";
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

					for (int i = 0; DatabaseStatic.tagCruzamentoMash != null
							&& i < DatabaseStatic.tagCruzamentoMash.size(); i++) {
						if (DatabaseStatic.tagCruzamentoMash.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							for (int j = 0; agvsAdd != null && j < agvsAdd.size(); j++) {
								agvsAdd.get(j).setStatus(AGV.statusRodando);
								DatabaseStatic.tagCruzamentoMash.get(i).cruzamentoEntrada(agvsAdd.get(j));
							}
						}

					}

					for (int i = 0; DatabaseStatic.cruzamentos != null && i < DatabaseStatic.cruzamentos.size(); i++) {
						if (DatabaseStatic.cruzamentos.get(i).getNome()
								.equals(req.getGetParams().get(TagsValues.paramID))) {
							for (int j = 0; agvsAdd != null && j < agvsAdd.size(); j++) {
								agvsAdd.get(j).setStatus(AGV.statusRodando);
								DatabaseStatic.cruzamentos.get(i).verificaCruzamento(agvsAdd.get(j),
										DatabaseStatic.cruzamentos.get(i).getTagsEntrada().get(0).getTag().getEpc());
							}
						}
					}
					html = "OK";
				}
			} else if (req.getGetParams().get(TagsValues.paramAction)
					.equals(TagsValues.valueResetCruzamentoParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					for (int i = 0; DatabaseStatic.cruzamentos != null && i < DatabaseStatic.cruzamentos.size(); i++) {
						Cruzamento_OLD c = DatabaseStatic.cruzamentos.get(i);
						if (c.getNome().equals(req.getGetParams().get(TagsValues.paramID))) {
							c.execLiberaCruzamento();
							html = "OK";
						}
					}
				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				// deletar
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					if (ConfigProcess.bd().deleteCruzamento(req.getGetParams().get(TagsValues.paramID))) {
						ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
								req.getCookies().get(Login.strKeyName).getValue(),
								"Excluiu Cruzamento " + req.getGetParams().get(TagsValues.paramID), Login.strExcluir);
						html = "OK";
					}

				}

			} else {

				if (req.getGetParams().containsKey(TagsValues.paramNome)) {
					if (req.getGetParams().containsKey(TagsValues.paramDescricao)) {

						if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueSalvarParamAction)) {
							if (ConfigProcess.bd().insertCruzamentos(req.getGetParams().get(TagsValues.paramNome),
									req.getGetParams().get(TagsValues.paramDescricao))) {
								ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
										req.getCookies().get(Login.strKeyName).getValue(),
										"Adicionou Cruzamento " + req.getGetParams().get(TagsValues.paramNome),
										Login.strAdicionar);
								html = "OK";
							}

						} else {

							if (ConfigProcess.bd().updateCruzamentos(req.getGetParams().get(TagsValues.paramNome),
									req.getGetParams().get(TagsValues.paramDescricao),
									req.getGetParams().get(TagsValues.paramIDOld))) {
								ConfigProcess.bd().insertLogUsuarios(System.currentTimeMillis(),
										req.getCookies().get(Login.strKeyName).getValue(),
										"Alterou Cruzamento " + req.getGetParams().get(TagsValues.paramNome),
										Login.strAlterar);
								html = "OK";
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
