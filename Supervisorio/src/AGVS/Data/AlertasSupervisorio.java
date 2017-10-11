package AGVS.Data;

import java.util.List;

import AGVS.Serial.DatabaseStatic;

public class AlertasSupervisorio {
	private String nome;
	private AlertaMesh am;
	public int count = 0;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AlertaMesh getAm() {
		return am;
	}

	public void setAm(AlertaMesh am) {
		this.am = am;
	}

	public AlertasSupervisorio(String nome, AlertaMesh am) {
		super();
		this.nome = nome;
		this.am = am;
	}

	public static void executarAlertaSupervisorio(List<AGV> agvs) {
		List<AlertasSupervisorio> alertasSupervisorio = DatabaseStatic.alertasSupervisorio;
		if (alertasSupervisorio != null) {
			if (agvs != null) {
				boolean falha = false;
				for (int i = 0; i < agvs.size(); i++) {
					AGV agv = agvs.get(i);
					if (!agv.getStatus().equals(AGV.statusRodando) && !agv.getStatus().equals(AGV.statusParado)
							&& !agv.getStatus().equals(AGV.statusHome) && !agv.getStatus().equals(AGV.statusManual)
							&& !agv.getStatus().equals(AGV.statusManutecao)
							&& !agv.getStatus().equals(AGV.statusCarregando)) {
						falha = true;
					}
				}
				for (int i = 0; alertasSupervisorio != null && i < alertasSupervisorio.size(); i++) {
					AlertasSupervisorio as = alertasSupervisorio.get(i);
					if (falha) {
						as.getAm().acionar();
						as.count = 0;
					} else {
						if (as.count++ > 5) {
							as.getAm().desacionar();
						}
					}

				}
			} else {

				for (int i = 0; alertasSupervisorio != null && i < alertasSupervisorio.size(); i++) {
					AlertasSupervisorio as = alertasSupervisorio.get(i);
					if (as.count++ > 5) {
						as.getAm().desacionar();
					}
				}
			}
		}
	}

}
