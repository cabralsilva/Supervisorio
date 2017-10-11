package Testes;

import java.util.Scanner;

import AGVS.Data.ConfigProcess;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TesteSemafaroLuz3 {
	private static int i = 0;
	public static void main(String[] args) {
		try {
			ConfigProcess.serial.conectar();
			while (true) {
				//0013A20040F40A3E
				
				
				String mca = "0013A2004154B6A6";
				String mcb = "FFFC";
				long time = 3000;
				ConfigProcess.serial.enviar("<xml>verde</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>vermelho</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>amarelo</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>desligar</xml>", mcb, mca);
				Thread.sleep(time);
				ConfigProcess.serial.enviar("<xml>alerta</xml>", mcb, mca);
				Thread.sleep(15000);
				ConfigProcess.serial.enviar("<xml>alertaVerd</xml>", mcb, mca);
				Thread.sleep(15000);
				ConfigProcess.serial.enviar("<xml>alertaVerm</xml>", mcb, mca);
				Thread.sleep(15000);
				
				
			}
		} catch (Exception e) {
			new Log(e);
		}
	}
}

