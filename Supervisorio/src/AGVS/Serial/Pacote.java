package AGVS.Serial;

public class Pacote {
	private int[] pk;
	private long time;
	private int replys;
	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[] getPk() {
		return pk;
	}

	public void setPk(int[] pk) {
		this.pk = pk;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getReplys() {
		return replys;
	}

	public void setReplys(int replys) {
		this.replys = replys;
	}

	public Pacote(int[] pk, long time, int size) {
		super();
		this.pk = pk;
		this.time = time;
		this.replys = 0;
		this.size = size;
	}

}
