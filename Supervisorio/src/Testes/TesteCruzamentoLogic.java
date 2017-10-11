package Testes;

import AGVS.Data.AGV;

import AGVS.Serial.DatabaseStatic;

public class TesteCruzamentoLogic {
	public static void main(String[] args) {
		new DatabaseStatic();
		AGV agv1 = new AGV(1, "AGV1", AGV.statusRodando, AGV.tipoRebocador, 30, 0, "", "", "", 0, 0, null, 0, 1);
		AGV agv2 = new AGV(2, "AGV2", AGV.statusRodando, AGV.tipoRebocador, 30, 0, "", "", "", 0, 0, null, 0, 1);
		AGV agv3 = new AGV(3, "AGV3", AGV.statusRodando, AGV.tipoRebocador, 30, 0, "", "", "", 0, 0, null, 0, 1);
		System.out.println("Inicia Teste Cruzamento");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv1, "E00401507887B6DC23");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv1, "E00401507887B6DC23");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv2, "E00401507887B6DC23");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv3, "E004015064BEF67223");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv3, "E004015064BEF67223");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv1, "E00401507887B6F423");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv1, "E004015064BEF67223");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv2, "E00401507887B29F23");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv3, "E00401507887B29F23");
		DatabaseStatic.cruzamentos.get(1).verificaCruzamento(agv1, "E00401507887B29F23");

	}
}
