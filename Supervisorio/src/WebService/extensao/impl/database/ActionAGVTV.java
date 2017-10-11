package WebService.extensao.impl.database;

import java.io.PrintStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.LogZoneTime;
import AGVS.Data.Tag;
import AGVS.Serial.DatabaseStatic;
import AGVS.Util.Util;
import WebService.extensao.CommandDB;
import WebService.http.Config;
import WebService.http.Dispatcher;
import WebService.http.Request;
import WebService.http.Response;

public class ActionAGVTV implements CommandDB {

	@Override
	public void execute(Request req, Response resp, Dispatcher disp) throws Exception {	
		String html = "";
//		List<AGV> agvs = ConfigProcess.bd().selecAGVSLastSixUpdate();
		List<AGV> agvs = DatabaseStatic.lstAGVS;
		Collections.sort(agvs);
		Config config = Config.getInstance();
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_FIAT)) {
			
			String bgColor = "white   ";
			for(AGV agv : agvs) {
				if (agv.getStatus().equals(AGV.statusObstaculo)) {
					bgColor = "yellow  ";
					break;
				}
			}
			for(AGV agv : agvs) {
				if (agv.getStatus().equals(AGV.statusEmergencia) || agv.getStatus().equals(AGV.statusEmergenciaRemota) || agv.getStatus().equals(AGV.statusFugaRota)) {
					bgColor = "red     ";
					break;
				}
			}
			html+=bgColor;
			
			int totalAgvs = agvs.size();
			for (int i = 0; agvs != null && i < totalAgvs; i++) {
				AGV a = agvs.get(i);
				String fontCor = "white";
				////////////////////////////////////////////////////////
				switch (a.getStatus()) {
					case AGV.statusRodando:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: rgba(255,255,255,0.7);'>";
						fontCor = "black";
						break;
					case AGV.statusEmEspera:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: rgba(12,103,193,0.8);'>";
						break;
					case AGV.statusEmRepouso:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: white;'>";
						break;
					case AGV.statusEmCruzamento:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: rgba(128,0,128,0.8);'>";
						break;
					case AGV.statusEmFila:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: rgba(128,0,128,0.5);'>";
						break;
					case AGV.statusObstaculo:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: yellow;'>";
						fontCor = "black";
						break;
					case AGV.statusEmergencia:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: red;'>";
						break;
					case AGV.statusEmergenciaRemota:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: red;'>";
						break;
					case AGV.statusFugaRota:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: red;'>";
						break;
					case AGV.statusManual:
						html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: white;'>";
						break;
					default:
						break;
				}
				
				////////////////////////////////////////////////////////
				
				
//				if ((i % 2) == 0)
//					html += "<div class='col-md-2' style='margin-right: 0px; width: 16.6%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: #ffffff;'>";
//				else
//					html += "<div class='col-md-2' style='margin-right: 0px; width: 16.7%; height: 100%; border: #cdcdcd 1px solid; border-radius: 10px; background-color: #eeeeee;'>";

				html += "<table width=\"100%\" style=\"border-spacing: 5px;\">" + 
						"<tr>" 
						+"	<td>";
				switch (a.getTipo()) {
				case "Carregador":
					html += "<img class='img-rounded' src='/TAS/images/icon-agv-carregador.jpg' alt='...'> ";
					break;
				case "Rebocador":
					html += "<img class='img-rounded' src='/TAS/images/icon-agv-rebocador.jpg' alt='...'> ";
					break;
				default:
					break;
				}
				
				html += "</td>" +
						"<td>" +
								"<center><span style='font-size: 32px; color: " + fontCor + "; font-weight: bold;'>"+a.getNome()+"</span></center>" + 
						"</td>"+
					"</tr>";				
				
				boolean agvInZone = false;
				if (DatabaseStatic.logZoneTimes != null) {
					for (LogZoneTime lzt : DatabaseStatic.logZoneTimes) {
						if (lzt.getAgv().getId() == a.getId() && !(lzt.getZoneTime().getTagStart().equals(lzt.getZoneTime().getTagEnd()))) {
							agvInZone = true;
							SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
							Date data = formatador.parse("00:00:00");
							Time timeZero = new Time(data.getTime());
							long interval = lzt.getZoneTime().getLimiteTempo().getTime() - timeZero.getTime();
							long current = (lzt.getCurrentTime().getTime() - timeZero.getTime());
							long percent = 0;
							if (!lzt.isBurst()) {
								percent = 100 - (current * 100 / interval);
							}
							
							html += "<tr><td colspan=\"2\"><center><span style='font-size: 18px;'>#" + lzt.getZoneTime().getDescricao() +"</span><br></center></td></tr>"+
							
									"<tr><td style=\"padding: 5px;\"><center><span style='font-size: 12px;'>Tempo</span>"+
										"<div class='counter-number-group margin-bottom-0'  style='line-height: 1;'>"+
											"<span class='counter-number'>" + lzt.getCurrentTime() + "</span>"+
										"</div>"+	
										"<div class='progress progress-xs margin-bottom-0'>";
							if (!lzt.isBurst()) {
								html += "<div class='progress-bar progress-bar-info bg-green-600' aria-valuenow='" 
											+ percent + "' aria-valuemin='0' aria-valuemax='100' style='width: " + percent 
											+ "%' role='progressbar'><span class='sr-only'>"+percent+"%</span>"+
										"</div>";
							} else {
								
								html += "<div class='progress-bar progress-bar-info bg-red-600' aria-valuenow='100' aria-valuemin='0' aria-valuemax='100' style='width: 100%' role='progressbar'>"
										+ "<span class='sr-only'>100%</span>"+
									"</div>";
							}
							
							html += "</center></td>"+
										"<td><center><span style='font-size: 12px;'>Obstaculos</span>"+
											"<div class='counter-number-group margin-bottom-0'  style='line-height: 1;'>"+
												"<span class='counter-number'>" + Util.getDurationConvertString(lzt.getTimeLostObstacle()) + "</span>"+
											"</div>"+
										"</center></td>"+
									"</tr>";
						}
					}
				}
				if (!agvInZone){
					html+="<tr><td colspan=\"2\"><center><span style='font-size: 14px;'>###</span><br></center></td></tr>";
				}
				
				html+="</table>"+
						"<div class='col-md-offset-1 col-md-8 bg-";
//				if (a.getStatusTimeOld() < (new Date().getTime() - 600000) ) {
//					html += "white-600' ";
//					html += "style='font-size: 16px; font-weight: bold; color: gray; height: 30px; border-radius: 2px; position: absolute; bottom: 10px; margin-right: 0;'>"+
//							"<center>Em repouso</center>"+
//						"</div>"+ 
//					"</div>";
//				}else {
					switch (a.getStatus()) {
						case AGV.statusRodando:
							html += "green-600' ";
							break;
						case AGV.statusEmEspera:
							html += "blue-600' ";
							break;
						case AGV.statusManual:
						case AGV.statusEmRepouso:
							html += "white-600' ";
							break;
						case AGV.statusEmCruzamento:
						case AGV.statusEmFila:
							html += "purple-600' ";
							break;
						case AGV.statusObstaculo:
							html += "yellow-600' ";
							break;
						case AGV.statusEmergencia:
						case AGV.statusEmergenciaRemota:
						case AGV.statusFugaRota:
							html += "red-600' ";
							break;
						default:
							break;
					}
					html += "style='font-size: 20px; font-weight: bold; color: "+fontCor+"; height: 30px; border-radius: 2px; position: absolute; bottom: 10px; margin-right: 0;'>"+
							"<center>" + a.getStatus() + "</center>"+
						"</div>"+ 
					"</div>";
//				}
				
				
				
				

			}

		} else {
			for (int i = 0; agvs != null && i < agvs.size(); i++) {
				AGV a = agvs.get(i);
				html += "<div class='col-md-4'>";
				html += "<div class='panel'>";
				html += "<center><h3>" + a.getNome() + "</h3></center>";
				html += "<table class='table table-bordered table-hover table-striped' id='exampleAddRow'>";
				html += "<thead>";
				html += "<tr>";
				html += "<th>";

				if (a.getStatus().equals(AGV.statusObstaculo)|| a.getStatus().equals(AGV.statusFugaRota)) {

					html += "<button type='button' class='btn btn-floating btn-danger'><i class='icon wb-warning' aria-hidden='true'></i></button>";

				}

				html += "</th>";
				if (a.getTipo().equals(AGV.tipoRebocadorJacto)) {
					html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv-Jacto.jpg' alt='...'></center></th>";
				} else if (a.getTipo().equals(AGV.tipoRebocadorToyota)) {
					html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv-toyota.jpg' alt='...'></center></th>";
				} else {
					html += "<th><center><img class='img-rounded' src='/TAS/images/icon-agv-carregador.jpg' alt='...'></center></th>";
				}

				html += "</thead>";
				html += "</tr>";
				html += "<tbody>";
				html += "<tr class='gradeA odd' role='row'>";
				html += "<td class='sorting_1'>ID:</td>";
				html += "<td class='sorting_1'>" + a.getId() + "</td>";
				html += "</tr>";
				/*
				 * html += "<tr class='gradeA odd' role='row'>"; html +=
				 * "<td class='sorting_1'>Bateria</td>"; html += "<td class='sorting_1'>" +
				 * a.getBateria() + "%</td>"; html += "</tr>";
				 */
				html += "<tr class='gradeA odd' role='row'>";
				html += "<td class='sorting_1'>Status</td>";
				html += "<td class='sorting_1'>" + a.getStatus() + "</td>";
				html += "</tr>";
				html += "<tr class='gradeA odd' role='row'>";
				html += "<td class='sorting_1'>Tag Atual EPC:</td>";
				html += "<td class='sorting_1'>" + a.getTagAtual() + "</td>";
				html += "</tr>";

				if (a.getStatusOld() != null) {
					html += "<tbody>";
					html += "<tr class='gradeA odd' role='row'>";
					html += "<td class='sorting_1'>" + a.getStatusOld() + "</td>";
					html += "<td class='sorting_1'>" + Util.getDateTimeFormatoBR(a.getStatusTimeOld()) + "</td>";
					html += "</tr>";
				} else {
					html += "<tbody>";
					html += "<tr class='gradeA odd' role='row'>";
					html += "<td class='sorting_1'>Sem Historico de Erro</td>";
					html += "<td class='sorting_1'>-</td>";
					html += "</tr>";
				}

				List<Tag> tg = null;

				if (a.getTagAtual() != null) {
					tg = ConfigProcess.bd().selecTags(a.getTagAtual());
				}

				if (tg != null && tg.size() > 0) {
					html += "<tr class='gradeA odd' role='row'>";
					html += "<td class='sorting_1'>Tag Atual Nome:</td>";
					html += "<td class='sorting_1'>" + tg.get(0).getNome() + "</td>";
					html += "</tr>";
				} else {
					html += "<tr class='gradeA odd' role='row'>";
					html += "<td class='sorting_1'>Tag Atual Nome:</td>";
					html += "<td class='sorting_1'>Tag Sem Cadastro</td>";
					html += "</tr>";
				}

				html += "<tr class='gradeA odd' role='row'>";
				html += "<td class='sorting_1'></td>";
				html += "<td class='sorting_1'> ";
				html += "<center>";
				// Config config = Config.getInstance();

				html += "<button type='button' onclick='BtnEmergencia(\"" + a.getId()
						+ "\")' class='btn btn-floating btn-danger btn-sm'><i class='icon wb-power' aria-hidden='true'></i></button>";

				if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
					html += "<button type='button' onclick='requestPopupRotas(\"/SelectRotas?id=" + a.getId()
							+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon fa-road' aria-hidden='true'></i></button>";

					html += "<button type='button' onclick='BtnPlayRe(\"" + a.getId()
							+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon fa-step-backward' aria-hidden='true'></i></button>";

					html += "<button type='button' onclick='BtnPlay(\"" + a.getId()
							+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon fa-step-forward' aria-hidden='true'></i></button>";

				} else {
					html += "<button type='button' onclick='BtnPlay(\"" + a.getId()
							+ "\")' class='btn btn-floating btn-success btn-sm'><i class='icon wb-play' aria-hidden='true'></i></button>";

				}

				html += "<button type='button' onclick='BtnParar(\"" + a.getId()
						+ "\")' class='btn btn-floating btn-danger btn-sm'><i class='icon wb-stop' aria-hidden='true'></i></button>";

				html += "</center>";
				html += "</td>";
				html += "</tr>";
				html += "</tbody>";
				html += "</table>";
				html += "</div>";
				html += "</div>";

			}
		}

		PrintStream out = resp.getPrintStream();
		out.println(html);
		out.flush();
		resp.flush();

	}
}
