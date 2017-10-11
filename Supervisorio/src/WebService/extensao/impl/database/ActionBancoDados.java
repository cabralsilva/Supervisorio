package WebService.extensao.impl.database;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;

import AGVS.Data.ConfigProcess;
import AGVS.Util.Util;
import WebService.HTML.ConvertPAGinHTML;
import WebService.HTML.Dashboards;
import WebService.HTML.PathFilesPAG;
import WebService.HTML.Tags;
import WebService.extensao.Command;
import WebService.extensao.CommandDB;
import WebService.extensao.impl.Reload;
import WebService.extensao.impl.Login.Login;
import WebService.extensao.impl.Tags.Keys;
import WebService.extensao.impl.Tags.Methods;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionBancoDados implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Não foi possivel realizar comando";
		
		if (req.getGetParams().containsKey(TagsValues.paramAction)) {
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueSalvarParamAction)) {
				if (req.getGetParams().containsKey(TagsValues.paramEnd)) {
					if (req.getGetParams().containsKey(TagsValues.paramUser)) {
						if (req.getGetParams().containsKey(TagsValues.paramPassword)) {
							if(Util.setXml("instancia", req.getGetParams().get(TagsValues.paramEnd))){
								if(Util.setXml("user", req.getGetParams().get(TagsValues.paramUser))){
									if(Util.setXml("pwd", req.getGetParams().get(TagsValues.paramPassword))){
										html = "OK";
									}
								}
							}
							
						}
					}
				}
			} else {
				if (req.getGetParams().containsKey(TagsValues.paramEnd)) {
					if (req.getGetParams().containsKey(TagsValues.paramUser)) {
						if (req.getGetParams().containsKey(TagsValues.paramPassword)) {
							if(Util.setXml("instancia", req.getGetParams().get(TagsValues.paramEnd))){
								if(Util.setXml("user", req.getGetParams().get(TagsValues.paramUser))){
									if(Util.setXml("pwd", req.getGetParams().get(TagsValues.paramPassword))){
										if(ConfigProcess.bd().criarBanco()){
											html="OK";
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
