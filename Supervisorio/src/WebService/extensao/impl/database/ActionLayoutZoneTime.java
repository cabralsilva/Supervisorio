package WebService.extensao.impl.database;

import java.io.PrintStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import AGVS.Data.AGV;
import AGVS.Data.LogZoneTime;
import AGVS.Serial.DatabaseStatic;
import AGVS.Util.Util;
import WebService.extensao.CommandDB;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionLayoutZoneTime implements CommandDB {
	private static int i = 0;

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {
		String html = "        ";
		if (DatabaseStatic.logZoneTimes != null) {
			for(LogZoneTime lzt : DatabaseStatic.logZoneTimes){
				SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
				Date data = formatador.parse("00:00:00");
				Time timeZero = new Time(data.getTime());
				long interval = lzt.getZoneTime().getLimiteTempo().getTime() - timeZero.getTime();
				long current = (lzt.getCurrentTime().getTime() - timeZero.getTime());
				long percent = 0;
				if (!lzt.isBurst()) {
					percent = 100 - (current * 100/interval);
				}
				
				html += "<div class='widget-content padding-30 bg-white'>"
				        +"<div class='counter counter-md text-left'>"
				        +"  <div class='counter-label text-uppercase margin-bottom-5'>#" + lzt.getAgv().getNome() + "#" + lzt.getZoneTime().getDescricao()+"</div>"
				        +" <div class='counter-number-group margin-bottom-10'>"
				        +"   <span class='counter-number'>" + lzt.getCurrentTime() + "</span>"
				        +" </div>"
				        
				         +" <div class='counter-label'>"
				         +"  <div class='progress progress-xs margin-bottom-10'>";
					if (!lzt.isBurst()) {
						html +="<div class='progress-bar progress-bar-info bg-blue-600' aria-valuenow='" + percent + "' aria-valuemin='0' aria-valuemax='100' style='width: "+ percent +"%' role='progressbar'>"
								+"<span class='sr-only'>"+ percent +"%</span>"
							+ "</div>";
					}else {
						html +="<div class='progress-bar progress-bar-info bg-red-600' aria-valuenow='100' aria-valuemin='0' aria-valuemax='100' style='width: 100%' role='progressbar'>"
								+"<span class='sr-only'>100%</span>"
							+ "</div>";
					}
					html+="  </div>"
	        		  +"<div class='counter-label text-uppercase margin-bottom-5'>" 
						+ AGV.statusObstaculo
							+" <div class='counter-number-group margin-bottom-10'>"
							+"   <span class='counter-number'> " + Util.getDurationConvertString(lzt.getTimeLostObstacle()) + "</span>"
							+" </div>"
					  +"</div>"
			         +" </div>"
			        +"</div>"
			  	+"</div>";
			}
		}
		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}

}
