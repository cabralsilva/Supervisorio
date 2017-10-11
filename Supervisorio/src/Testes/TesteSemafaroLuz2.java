package Testes;

import java.util.Scanner;

import AGVS.Data.ConfigProcess;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TesteSemafaroLuz2 {
	public static void main(String[] args) {
		try {
			ConfigProcess.serial.conectar();
			String mca = "";
			String mcb = "";
			
			System.out.println("==========================================");
			System.out.println("=============Teste Placa Mash=============");
			System.out.println("==========================================");
			int val = 0;
			long time = 1000;
			System.out.println("Mac64:");
			mca = new Scanner(System.in).next();
			System.out.println("Mac16:");
			mcb = new Scanner(System.in).next();
			while (true) {
				
				ConfigProcess.serial.enviar("<xml>verde</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>vermelho</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>amarelo</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>desligar</xml>", mcb, mca);
				Thread.sleep(time);


			}
		} catch (Exception e) {
			new Log(e);
		}
	}
}
