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
import AGVS.Data.Cruzamento;
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

public class ActionPedidos implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";

		System.out.println(req.toString());

		if (req.getGetParams().containsKey(TagsValues.paramAction)) {

			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueFimParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					ConfigProcess.xmlControleGoodyear
							.pedidoFinalizadoID(Integer.parseInt(req.getGetParams().get(TagsValues.paramID)));
					html = "OK";
				}
			} else if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueDeleteParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramID)) {
					ConfigProcess.xmlControleGoodyear
							.removePedido(Integer.parseInt(req.getGetParams().get(TagsValues.paramID)));
					html = "OK";
				}
			}
		}

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
