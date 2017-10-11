package AGVS.Data;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import AGVS.Util.Log;
import AGVS.Util.Util;

public class LogZoneTime {
	private int id;
	private ZoneTime zoneTime;
	private Time currentTime;
	private Time timeRoute;
	private Time timeLost;
	private String sTimeLost;
	private Duration timeLostObstacle;
	private Date data;
	private boolean burst;
	private boolean obstacle;
	private AGV agv;
	public ScheduledExecutorService threadPeriodic;
	public ScheduledFuture<?> threadFuturo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Time currentTime) {
		this.currentTime = currentTime;
	}

	

	public boolean isBurst() {
		return burst;
	}

	public void setBurst(boolean burst) {
		this.burst = burst;
	}

	public ZoneTime getZoneTime() {
		return zoneTime;
	}

	public void setZoneTime(ZoneTime zoneTime) {
		this.zoneTime = zoneTime;
	}

	public Time getTimeRoute() {
		return timeRoute;
	}

	public void setTimeRoute(Time timeRoute) {
		this.timeRoute = timeRoute;
	}
	
	public AGV getAgv() {
		return agv;
	}

	public void setAgv(AGV agv) {
		this.agv = agv;
	}

	public void setTimeRouteByString(String timeRoute) throws ParseException {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
		Date data = formatador.parse(timeRoute);
		this.timeRoute = new Time(data.getTime());
	}

	public Time getTimeLost() {
		return timeLost;
	}

	public void setTimeLost(Time timeLost) {
		this.timeLost = timeLost;
	}

	public void setTimeLostByString(String timeLost) throws ParseException {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
		Date data = formatador.parse(timeLost);
		this.timeLost = new Time(data.getTime());
	}

	public LogZoneTime() {
		super();
		this.burst = false;
		this.obstacle = false;
		this.timeLostObstacle = Duration.ofMillis(0);
	}

	public String getsTimeLost() {
		return sTimeLost;
	}

	public void setsTimeLost(String sTimeLost) {
		this.sTimeLost = sTimeLost;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	private void setDataByString(String sData) throws ParseException {
		SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		this.data = formatoBD.parse(sData);
	}
	
	public Duration getTimeLostObstacle() {
		return timeLostObstacle;
	}

	public void setTimeLostObstacle(Duration timeLostObstacle) {
		this.timeLostObstacle = timeLostObstacle;
	}
	
	

	public boolean isObstacle() {
		return obstacle;
	}

	public void setObstacle(boolean obstacle) {
		this.obstacle = obstacle;
	}

	@Override
	public String toString() {
		return "LogZoneTime [id=" + id + ", zoneTime=" + zoneTime + ", currentTime=" + currentTime + ", timeRoute="
				+ timeRoute + ", data=" + data + "]\n";
	}

	public LogZoneTime(int id, ZoneTime zoneTime, Time timeRoute, String timeLost, AGV agv, String data, String timeLostObstacle) {
		super();
		this.id = id;
		this.zoneTime = zoneTime;
		this.timeRoute = timeRoute;
		this.sTimeLost = timeLost;
		this.burst = false;
		this.obstacle = false;
		this.agv = agv;
		this.timeLostObstacle = timeLostObstacle == null ? Duration.ofMillis(0) : Util.getStringConvertDuration(timeLostObstacle);
		try {
			setDataByString(data);
		} catch (ParseException e) {
			new Log(e);
		}
	}
}
