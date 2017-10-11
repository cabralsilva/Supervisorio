package AGVS.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import AGVS.Util.Log;

public class TagCruzamentoMash {
	private String nome;
	private Input ac;
	private Input dac;
	private List<Tag> tagAC;
	private List<Tag> Stop;
	private List<Tag> tagDAC;
	private List<AGV> agvRota;
	private Semaphore sp = new Semaphore(1);

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Input getAc() {
		return ac;
	}

	public void setAc(Input ac) {
		this.ac = ac;
	}

	public Input getDac() {
		return dac;
	}

	public void setDac(Input dac) {
		this.dac = dac;
	}

	public List<Tag> getTagAC() {
		return tagAC;
	}

	public void setTagAC(List<Tag> tagAC) {
		this.tagAC = tagAC;
	}

	public List<Tag> getTagDAC() {
		return tagDAC;
	}

	public void setTagDAC(List<Tag> tagDAC) {
		this.tagDAC = tagDAC;
	}

	public List<AGV> getAgvRota() {
		return agvRota;
	}

	public void setAgvRota(List<AGV> agvRota) {
		this.agvRota = agvRota;
	}

	public TagCruzamentoMash(String nome, Input ac, Input dac, List<Tag> tagAC, List<Tag> tagDAC) {
		super();
		this.nome = nome;
		this.ac = ac;
		this.dac = dac;
		this.tagAC = tagAC;
		this.tagDAC = tagDAC;
		this.agvRota = new ArrayList<AGV>();
	}

	private boolean testeAgvRota(AGV agv) {
		for (AGV a : agvRota) {
			if (a.getId() == agv.getId()) {
				return true;
			}
		}
		return false;
	}

	public void liberar() {
		try {
			sp.acquire();
			agvRota.clear();
			dac.sendComando();
			dac.sendComando();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new Log(e);
		}
		sp.release();
	}

	public void cruzamento(String epc, AGV agv) {
		try {
			sp.acquire();
			System.out.println("Verificando Mash Cruzamento");
			if (!agv.getStatus().equals(AGV.statusManual)) {
				for (int i = 0; tagAC != null && i < tagAC.size(); i++) {
					Tag tg = tagAC.get(i);
					if (tg.getEpc().equals(epc) && !testeAgvRota(agv)) {

						agvRota.add(agv);
						System.out.println("Entrou: " + agv.getId());
						ac.sendComando();
						ac.sendComando();
					}
				}
			}
			for (int i = 0; tagDAC != null && i < tagDAC.size(); i++) {
				Tag tg = tagDAC.get(i);
				if (tg.getEpc().equals(epc)) {
					for (int j = 0; agvRota != null && j < agvRota.size(); j++) {
						AGV agvR = agvRota.get(j);
						if (agvR.getId() == agv.getId()) {
							System.out.println("Saiu Tag Cruzamento AGV: " + agv.getId());
							agvRota.remove(j);
							j--;
						}
					}
					if (agvRota != null && agvRota.size() <= 0) {
						System.out.println("Desbloqueou");
						dac.sendComando();
						dac.sendComando();
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			new Log(e);
		}
		sp.release();
	}

	public void cruzamentoEntrada(AGV agv) {
		try {
			sp.acquire();
			System.out.println("Verificando Mash Cruzamento");
			if (!agv.getStatus().equals(AGV.statusManual)) {
				agvRota.add(agv);
				System.out.println("Entrou: " + agv.getId());
				ac.sendComando();
				ac.sendComando();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			new Log(e);
		}
		sp.release();
	}

	public void cruzamento(AGV agv) {
		try {
			Thread.sleep(5000);
			sp.acquire();
			boolean send = false;
			for (int i = 0; agvRota != null && i < agvRota.size(); i++) {
				AGV agvR = agvRota.get(i);
				if (agvR.getId() == agv.getId() && agv.getStatus().equals(AGV.statusManual)) {
					send = true;
					System.out.println("Passou agv para manual: " + agv.getId());
					agvRota.remove(i);
					i--;
				}
			}
			if (agvRota != null && agvRota.size() <= 0 && send) {
				dac.sendComando();
				dac.sendComando();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new Log(e);
		}
		sp.release();

	}

}
