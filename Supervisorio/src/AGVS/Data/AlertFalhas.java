package AGVS.Data;

import java.util.ArrayList;
import java.util.List;

public class AlertFalhas {
	public static List<AlertFalhas> alerts = new ArrayList<AlertFalhas>();
	public static int count = 0;

	private int idAlert;
	private int id;
	private String msg;
	private long data;

	
	
	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

	public static int getCount() {
		return count;
	}

	public int getIdAlert() {
		return idAlert;
	}

	public void setIdAlert(int idAlert) {
		this.idAlert = idAlert;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AlertFalhas(int id, String msg, long data) {
		super();
		this.idAlert = count++;
		this.id = id;
		this.msg = msg;
		this.data = data;
	}

}
