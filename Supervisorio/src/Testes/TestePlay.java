package Testes;

import AGVS.Data.ConfigProcess;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TestePlay {
	public static void main(String[] args) {
		try {
			Serial serial = new Serial();
			serial.conectar();

			serial.enviar("<xml>PLAY</xml>", "FFFC", "0013A20040F40A3C");

			serial.desconectar();

		} catch (Exception e) {
			new Log(e);
		}
	}
}

