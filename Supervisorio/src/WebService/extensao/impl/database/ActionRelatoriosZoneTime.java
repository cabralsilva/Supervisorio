package WebService.extensao.impl.database;

import java.io.PrintStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import AGVS.Data.AlertFalhas;
import AGVS.Data.ConfigProcess;
import AGVS.Data.LogTags;
import AGVS.Data.LogZoneTime;
import AGVS.Util.Util;
import WebService.extensao.CommandDB;
import WebService.extensao.impl.Tags.TagsValues;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionRelatoriosZoneTime implements CommandDB {
	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "Nao foi possivel realizar comando";
		
		if (req.getGetParams().containsKey(TagsValues.paramAction)) 
		{
			if (req.getGetParams().get(TagsValues.paramAction).equals(TagsValues.valueSearchParamAction)) 
			{
				if (req.getGetParams().containsKey(TagsValues.paramDateStart) && 
						req.getGetParams().containsKey(TagsValues.paramDateEnd) &&
						req.getGetParams().containsKey(TagsValues.paramIdAGV) &&
						req.getGetParams().containsKey(TagsValues.paramIdZoneTime) ) 
				{
					final SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
					final Date data = formatador.parse("00:00:00");
					final Time timeZero = new Time(data.getTime());
					
					String dateS = req.getGetParams().get(TagsValues.paramDateStart);
					String dateE = req.getGetParams().get(TagsValues.paramDateEnd);
					Integer idAgv = Integer.parseInt(req.getGetParams().get(TagsValues.paramIdAGV));
					Integer idZT = Integer.parseInt(req.getGetParams().get(TagsValues.paramIdZoneTime));
					
					List<LogZoneTime> lstLogZoneTime = ConfigProcess.bd().selectLogZoneTimeByDateReport(dateS, dateE, idAgv, idZT);
					html = "OK";
					int nVoltas = lstLogZoneTime.size();
					if (nVoltas > 0) {
						html += "<thead>\r\n" + 
								"	<tr>\r\n" + 
								"		<th colspan=\"7\" style=\"text-align: center; padding: 10px 18px; border-bottom: 1px solid #111; font-weight: bold;\">" + 
								"			Zona de tempo: #"+lstLogZoneTime.get(0).getZoneTime().getDescricao()+"/Tempo: "+lstLogZoneTime.get(0).getZoneTime().getLimiteTempoByString()+"  (mm:ss)</th>\r\n" + 
								"	</tr>\r\n" + 
								"	<tr >\r\n" + 
								"		<th>Volta</th>\r\n" + 
								"		<th>AGV</th>\r\n" + 
								"		<th>Hora Inicio</th>\r\n" + 
								"		<th>Hora Fim</th>\r\n" + 
								"		<th>Tempo</th>\r\n" + 
								"		<th>Obstaculo</th>\r\n" + 
								"		<th>Atraso</th>\r\n" + 
								"	</tr>\r\n" + 
								"</thead>\r\n" + 
								"<tbody>";
						for(LogZoneTime lzt : lstLogZoneTime) {
							Date start = new Date(lzt.getData().getTime() - (lzt.getTimeRoute().getTime() - timeZero.getTime()));
							html += "<tr style=\"color: #000; font-weight: bold; background-color: rgba(200,200,200,1);\">\r\n" + 
									"	<td>"+nVoltas+"ª Volta</td>\r\n" + 
									"	<td>"+lzt.getAgv().getNome()+"</td>\r\n" + 
									"	<td>"+Util.getTimeOnly(start.getTime()) + "</td>\r\n" + 
									"	<td>"+Util.getTimeOnly(lzt.getData().getTime()) + "</td>\r\n" + 
									"	<td>"+lzt.getTimeRoute()+"</td>\r\n" + 
									"	<td>"+Util.getDurationConvertString(lzt.getTimeLostObstacle())+"</td>\r\n" + 
									"	<td>"+lzt.getsTimeLost()+"</td>\r\n" + 
									"</tr>";
								
							List<LogTags> lstLogTags = ConfigProcess.bd().selectLogTagsByDateReport(
										Util.getDateTimeBR(start.getTime()), 
										Util.getDateTimeBR(lzt.getData().getTime()), 
										idAgv);
							
							for (int i = 0; lstLogZoneTime != null && i < lstLogTags.size(); i++) {
								LogTags lt = lstLogTags.get(i);
								String endTag;
								Date timeTag;
								List<AlertFalhas> lstStatus;
								if (i+1 < lstLogTags.size()) {
									endTag = Util.getTimeOnly(Util.getDateTimeLong(lstLogTags.get(i+1).getData()));
									timeTag = new Date(Util.getDateTimeLong(lstLogTags.get(i+1).getData()) - Util.getDateTimeLong(lt.getData()));
									lstStatus = ConfigProcess.bd().selectFalhasByDateReport(
											Util.getDateTimeBR(lt.getData()), 
											Util.getDateTimeBR(Util.getDateTimeLong(lstLogTags.get(i+1).getData())), 
											idAgv);
								}else {
									endTag = "###";
									timeTag = new Date(data.getTime() - timeZero.getTime());
									lstStatus = new ArrayList<AlertFalhas>();
								}
								
								html += "<tr style=\"color: #000; background-color: rgba(200,200,200,0.6);\">\r\n" + 
										"	<td></td>\r\n" + 
										"	<td>TAG "+lt.getMsg()+"</td>\r\n" + 
										"	<td>"+Util.getTimeOnly(Util.getDateTimeLong(lt.getData()))+"</td>\r\n" + 
										"	<td>"+endTag+"</td>\r\n" + 
										"	<td>"+Util.getTimeOnly((timeZero.getTime() + timeTag.getTime()))+"</td>\r\n" + 
										"	<td>Bateria: ###</td>\r\n" + 
										"	<td>MD: ### / ME: ### / Temp: ###</td>\r\n" + 
										"</tr>";
								
								
								for(int j = 0; lstStatus != null && j < lstStatus.size(); j++) {
									AlertFalhas status = lstStatus.get(j);
									
									if (j == lstStatus.size()-1) {
										Date timeStatus = new Date(Util.getDateTimeLong(lstLogTags.get(i+1).getData()) - Util.getDateTimeLong(status.getData()));
										html += "<tr style=\"color: #000; background-color: rgba(200,200,200,0.3);\">\r\n" + 
												"	<td></td>\r\n" + 
												"	<td align=\"right\">"+status.getMsg()+"</td>\r\n" + 
												"	<td>"+Util.getTimeOnly(Util.getDateTimeLong(status.getData()))+"</td>\r\n" + 
												"	<td>"+endTag+"</td>\r\n" + 
												"	<td>"+Util.getTimeOnly((timeZero.getTime() + timeStatus.getTime()))+"</td>\r\n" + 
												"	<td></td>\r\n" + 
												"	<td></td>" +
												"</tr>";
									}else {
										Date timeStatus = new Date(Util.getDateTimeLong(lstStatus.get(j+1).getData()) - Util.getDateTimeLong(status.getData()));
										long timeEnd = (status.getData() > lstStatus.get(j+1).getData())?status.getData():lstStatus.get(j+1).getData();
										long tempoStatus = (status.getData() > lstStatus.get(j+1).getData())?(timeZero.getTime() - timeStatus.getTime()):(timeZero.getTime() + timeStatus.getTime());
										html += "<tr style=\"color: #000; background-color: rgba(200,200,200,0.3);\">\r\n" + 
												"	<td></td>\r\n" + 
												"	<td align=\"right\">"+status.getMsg()+"</td>\r\n" + 
												"	<td>"+Util.getTimeOnly(Util.getDateTimeLong(status.getData()))+"</td>\r\n" + 
												"	<td>"+Util.getTimeOnly(Util.getDateTimeLong(timeEnd))+"</td>\r\n" + 
												"	<td>"+Util.getTimeOnly(tempoStatus)+"</td>\r\n" + 
												"	<td></td>\r\n" + 
												"	<td></td>" +
												"</tr>";
									}
								}
							}
							nVoltas--;
						}
						html += "</tbody>";
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
