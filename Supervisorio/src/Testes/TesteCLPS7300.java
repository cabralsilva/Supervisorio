package Testes;

import AGVS.Drivers.CLP.PLCComDriver;
import PLCCom.PLCcomDevice;
import PLCCom.ePLCType;

public class TesteCLPS7300 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Iniciando teste CLP S7 300");
		int[] i = { 0 };
		System.out.println("Retorno = "
				+ PLCComDriver.request("192.168.0.1", 102, 100, 0, i, ePLCType.S7_300_400_compatibel.name()));
	}

}
