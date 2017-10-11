package AGVS.Data;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import AGVS.Serial.DatabaseStatic;

public class ZoneTime {

	private int id;
	private String descricao;
	private Tag tagStart;
	private Tag tagEnd;
	private Time limiteTempo;
	
	public void encerrarZoneTimes(AGV agv) throws ParseException {
		final SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
		final Date data = formatador.parse("00:00:00");
		final Time timeZero = new Time(data.getTime());
		
		for (final LogZoneTime instLog : DatabaseStatic.logZoneTimes) {
			if (instLog.getAgv().getId() == agv.getId()){
				long tempoRota; 
				if (!instLog.isBurst()) {
					tempoRota = instLog.getZoneTime().getLimiteTempo().getTime() - instLog.getCurrentTime().getTime();
				}else {
					tempoRota = (instLog.getZoneTime().getLimiteTempo().getTime() - timeZero.getTime()) + (instLog.getCurrentTime().getTime() - timeZero.getTime());
				}
				long tempoPerdido = timeZero.getTime() - instLog.getCurrentTime().getTime();
				Duration interval = Duration.ofMillis(tempoRota);
				long timeSeconds = interval.getSeconds();
				instLog.setTimeRouteByString(String.format("%d:%02d:%02d", timeSeconds/3600, (timeSeconds%3600)/60, (timeSeconds%60)));
				interval = Duration.ofMillis(tempoPerdido);
				timeSeconds = interval.getSeconds();
				long absSeconds = Math.abs(timeSeconds);
				String positive = String.format("%d:%02d:%02d",absSeconds / 3600,(absSeconds % 3600) / 60,absSeconds % 60);
				if (!instLog.isBurst())
					instLog.setsTimeLost("-" + positive);
				else
					instLog.setsTimeLost(positive);
				instLog.setTimeLostByString(String.format("%d:%02d:%02d", timeSeconds/3600, (timeSeconds%3600)/60, (timeSeconds%60)));
				instLog.threadPeriodic.shutdownNow();
				ConfigProcess.bd().insertLogZoneTimes(instLog);
				DatabaseStatic.logZoneTimes.remove(instLog);
				break;
			}
		}
	}
	
	public void verificarZoneTime(AGV agv, String epc) throws ParseException {
		final SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
		final Date data = formatador.parse("00:00:00");
		final Time timeZero = new Time(data.getTime());
		
		if (agv == null || epc == null) {
			return;
		}
		
//		System.out.println("Zone: " + this.descricao);
//		System.out.println(tagStart.getEpc());
//		System.out.println(tagEnd.getEpc());
		if (tagStart.getEpc().equals(epc) && tagEnd.getEpc().equals(epc)) {
//			System.out.println("tag inicio igual tag fim");
			boolean jaExiste = false;
			for (final LogZoneTime instLog : DatabaseStatic.logZoneTimes) {
				if (instLog.getAgv().getId() == agv.getId() && (instLog.getZoneTime().getId() == this.getId())){
					jaExiste = true;
					long tempoRota; 
					if (!instLog.isBurst()) {
						tempoRota = instLog.getZoneTime().getLimiteTempo().getTime() - instLog.getCurrentTime().getTime();
					}else {
						tempoRota = (instLog.getZoneTime().getLimiteTempo().getTime() - timeZero.getTime()) + (instLog.getCurrentTime().getTime() - timeZero.getTime());
					}
					long tempoPerdido = timeZero.getTime() - instLog.getCurrentTime().getTime();
					Duration interval = Duration.ofMillis(tempoRota);
					long timeSeconds = interval.getSeconds();
					instLog.setTimeRouteByString(String.format("%d:%02d:%02d", timeSeconds/3600, (timeSeconds%3600)/60, (timeSeconds%60)));
					interval = Duration.ofMillis(tempoPerdido);
					timeSeconds = interval.getSeconds();
					long absSeconds = Math.abs(timeSeconds);
					String positive = String.format("%d:%02d:%02d",absSeconds / 3600,(absSeconds % 3600) / 60,absSeconds % 60);
					if (!instLog.isBurst())
						instLog.setsTimeLost("-" + positive);
					else
						instLog.setsTimeLost(positive);
					instLog.setTimeLostByString(String.format("%d:%02d:%02d", timeSeconds/3600, (timeSeconds%3600)/60, (timeSeconds%60)));
					instLog.threadPeriodic.shutdownNow();
					ConfigProcess.bd().insertLogZoneTimes(instLog);
					DatabaseStatic.logZoneTimes.remove(instLog);
//					jaExiste = false;//ESPECIFICO PARA REINICIAR OS TEMPOS DAS ZONAS COM MESMA TAG INICIAL E FINAL AO PASSAR PELA TAG FINAL
					break;
				}
			}
			if (!jaExiste) {
				final LogZoneTime lzt = new LogZoneTime();
				lzt.setAgv(agv);
				lzt.setZoneTime(this);
				lzt.setCurrentTime(this.limiteTempo);
				lzt.threadPeriodic = Executors.newScheduledThreadPool(1);
				
				Runnable r = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						cal.setTime(lzt.getCurrentTime());
						if (lzt.isBurst()) {
							cal.add(Calendar.SECOND, +1);
						}else {
							cal.add(Calendar.SECOND, -1);
							if (cal.getTime().getTime() - timeZero.getTime() <= 0) {
								lzt.setBurst(true);
							}
						}
						if (lzt.isObstacle())
							lzt.setTimeLostObstacle(lzt.getTimeLostObstacle().plus(Duration.ofSeconds(1)));
						lzt.setCurrentTime(new Time(cal.getTime().getTime()));
						
					}
				};
				
				lzt.threadFuturo = lzt.threadPeriodic.scheduleAtFixedRate(r, 1L, 1L, TimeUnit.SECONDS);
				lzt.threadPeriodic.submit(r);
				DatabaseStatic.logZoneTimes.add(lzt);
			}
		}else if (tagStart.getEpc().equals(epc)) {
			boolean jaExiste = false;
			for (final LogZoneTime instLog : DatabaseStatic.logZoneTimes) {
				if (instLog.getAgv().getId() == agv.getId() && (instLog.getZoneTime().getId() == this.getId())){
					jaExiste = true;
					instLog.threadPeriodic.shutdownNow();
					instLog.setBurst(false);
					instLog.setCurrentTime(this.limiteTempo);
					instLog.threadPeriodic = Executors.newScheduledThreadPool(1);
					
					Runnable r = new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Calendar cal = Calendar.getInstance();
							cal.setTime(instLog.getCurrentTime());
							if (instLog.isBurst()) {
								cal.add(Calendar.SECOND, +1);
							}else {
								cal.add(Calendar.SECOND, -1);
								if (cal.getTime().getTime() - timeZero.getTime() <= 0) {
									instLog.setBurst(true);
								}
							}
							if (instLog.isObstacle())
								instLog.setTimeLostObstacle(instLog.getTimeLostObstacle().plus(Duration.ofSeconds(1)));
							instLog.setCurrentTime(new Time(cal.getTime().getTime()));
						}
					};
					
					instLog.threadFuturo = instLog.threadPeriodic.scheduleAtFixedRate(r, 1L, 1L, TimeUnit.SECONDS);
					instLog.threadPeriodic.submit(r);
					break;
				}
			}
			
			if (!jaExiste) {
				final LogZoneTime lzt = new LogZoneTime();
				lzt.setAgv(agv);
				lzt.setZoneTime(this);
				lzt.setCurrentTime(this.limiteTempo);
				lzt.threadPeriodic = Executors.newScheduledThreadPool(1);
				
				Runnable r = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						cal.setTime(lzt.getCurrentTime());
						if (lzt.isBurst()) {
							cal.add(Calendar.SECOND, +1);
						}else {
							cal.add(Calendar.SECOND, -1);
							if (cal.getTime().getTime() - timeZero.getTime() <= 0) {
								lzt.setBurst(true);
							}
						}
						if (lzt.isObstacle())
							lzt.setTimeLostObstacle(lzt.getTimeLostObstacle().plus(Duration.ofSeconds(1)));
						lzt.setCurrentTime(new Time(cal.getTime().getTime()));
						
					}
				};
				
				lzt.threadFuturo = lzt.threadPeriodic.scheduleAtFixedRate(r, 1L, 1L, TimeUnit.SECONDS);
				lzt.threadPeriodic.submit(r);
				DatabaseStatic.logZoneTimes.add(lzt);
			}
			
			
		}else if (tagEnd.getEpc().equals(epc)) {
			for (LogZoneTime logZT : DatabaseStatic.logZoneTimes) {
				if (agv.getId() == logZT.getAgv().getId() && (logZT.getZoneTime().getId() == this.getId())) {
					long tempoRota; 
					if (!logZT.isBurst()) {
						tempoRota = logZT.getZoneTime().getLimiteTempo().getTime() - logZT.getCurrentTime().getTime();
					}else {
						tempoRota = (logZT.getZoneTime().getLimiteTempo().getTime() - timeZero.getTime()) + (logZT.getCurrentTime().getTime() - timeZero.getTime());
					}
					long tempoPerdido = timeZero.getTime() - logZT.getCurrentTime().getTime();
					Duration interval = Duration.ofMillis(tempoRota);
					long timeSeconds = interval.getSeconds();
					logZT.setTimeRouteByString(String.format("%d:%02d:%02d", timeSeconds/3600, (timeSeconds%3600)/60, (timeSeconds%60)));
					interval = Duration.ofMillis(tempoPerdido);
					timeSeconds = interval.getSeconds();
					long absSeconds = Math.abs(timeSeconds);
					String positive = String.format("%d:%02d:%02d",absSeconds / 3600,(absSeconds % 3600) / 60,absSeconds % 60);
					if (!logZT.isBurst())
						logZT.setsTimeLost("-" + positive);
					else
						logZT.setsTimeLost(positive);
					logZT.setTimeLostByString(String.format("%d:%02d:%02d", timeSeconds/3600, (timeSeconds%3600)/60, (timeSeconds%60)));
					logZT.threadPeriodic.shutdownNow();
					ConfigProcess.bd().insertLogZoneTimes(logZT);
					DatabaseStatic.logZoneTimes.remove(logZT);
					break;
				}
			}
		}
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public Tag getTagStart() {
		return tagStart;
	}



	public void setTagStart(Tag tagStart) {
		this.tagStart = tagStart;
	}



	public Tag getTagEnd() {
		return tagEnd;
	}



	public void setTagEnd(Tag tagEnd) {
		this.tagEnd = tagEnd;
	}



	public Time getLimiteTempo() {
		return limiteTempo;
	}

	public String getLimiteTempoByString() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(limiteTempo);
		return (cal.get(Calendar.HOUR_OF_DAY) * 60) + cal.get(Calendar.MINUTE) + ":" + String.format("%02d", cal.get(Calendar.SECOND));
	}

	public void setLimiteTempo(Time limiteTempo) {
		this.limiteTempo = limiteTempo;
	}

	public void setLimiteTempoByString(String limiteTempo) throws ParseException {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
		Date data = formatador.parse(limiteTempo);
		this.limiteTempo = new Time(data.getTime());
	}

	public ZoneTime(int id, String descricao, Tag tagStart, Tag tagEnd, String limiteTempo) throws ParseException {
		super();
		if (id > 0) {
			this.id = id;
			this.descricao = descricao;
			this.tagStart = tagStart;
			this.tagEnd = tagEnd;
			this.setLimiteTempoByString(limiteTempo);
		}
	}

}
