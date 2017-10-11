package Testes;

import AGVS.Data.ConfigProcess;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TesteMeshs {
	public static void main(String[] args) {
		try {
			Serial serial = new Serial();
			serial.conectar();
			
			serial.enviar("<xml>5N2</xml>", "F176", "0013A20040F40A2D");
			
			while (true) {
				
			}

		} catch (Exception e) {
			new Log(e);
		}
	}
}
