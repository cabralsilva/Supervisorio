package AGVS.Data;

public class Logica {

	private int id;
	private MeshSerial fkMesh;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MeshSerial getFkMesh() {
		return fkMesh;
	}
	public void setFkMesh(MeshSerial fkMesh) {
		this.fkMesh = fkMesh;
	}
	@Override
	public String toString() {
		return "Logica [id=" + id + ", fkMesh=" + fkMesh + "]";
	}
	public Logica(int id, MeshSerial fkMesh) {
		super();
		this.id = id;
		this.fkMesh = fkMesh;
	}

	

}
