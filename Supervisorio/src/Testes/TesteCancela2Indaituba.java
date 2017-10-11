package Testes;

import java.util.Scanner;

import AGVS.Data.ConfigProcess;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TesteCancela2Indaituba {
	public static void main(String[] args) {
		try {
			ConfigProcess.serial.conectar();
			String mca = "";
			String mcb = "";
			
			System.out.println("===========================================================");
			System.out.println("=============Teste Cancela 2 Indaiatuba Toyota=============");
			System.out.println("===========================================================");
			int val = 0;
			long time = 7000;
			System.out.println("Mac64:");
			mca = "0013A20041565603";
			mcb = "964a";
			while (true) {
				
				ConfigProcess.serial.enviar("<xml>1</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>0</xml>", mcb, mca);
				Thread.sleep(time);
				

			}
		} catch (Exception e) {
			new Log(e);
		}
	}
}
