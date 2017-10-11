package Testes;

import AGVS.Data.ConfigProcess;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TesteSemafaro {
	public static void main(String[] args) {
		try {
			Serial serial = new Serial();
			serial.conectar();
			while (true) {
				serial.enviar("<xml>d0m0b0v1</xml>", "f176", "0013A20040F40A2D");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b1v1</xml>", "f176", "0013A20040F40A2D");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b2v1</xml>", "f176", "0013A20040F40A2D");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b3v1</xml>", "f176", "0013A20040F40A2D");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b4v1</xml>", "f176", "0013A20040F40A2D");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b5v1</xml>", "f176", "0013A20040F40A2D");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b6v1</xml>", "820C", "0013A20040F40A52");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b7v1</xml>", "820C", "0013A20040F40A52");
				Thread.sleep(1000);
				
				serial.enviar("<xml>d0m0b8v1</xml>", "820C", "0013A20040F40A52");
				Thread.sleep(1000);
				serial.enviar("<xml>d0m0b9v1</xml>", "820C", "0013A20040F40A52");
				Thread.sleep(1000);
				
			}

		} catch (Exception e) {
			new Log(e);
		}
	}
}
