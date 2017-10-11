package Testes;

import AGVS.Data.ConfigProcess;
import AGVS.Data.Tag;
import AGVS.Data.TagsRotas;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TesteTag {
	public static void main(String[] args) {
		try {
			Serial serial = new Serial();
			serial.conectar();
			serial.enviar(new TagsRotas("Tag 1", 1, null, new Tag("E00401507887D89F23", "Tag 1", 1, 10, 10),
					TagsRotas.sp4500, TagsRotas.velPorc30, 0, TagsRotas.girarDesativado, TagsRotas.estAtuadorManter,
					TagsRotas.sensObsDesligado, TagsRotas.sinalSonoroDesligado, 0, 0, 0), "FDCA", "0013A20040F40A44");
			//serial.enviar(10, 50, 121, 10, "FDCA", "0013A20040F40A44");
		} catch (Exception e) {
			new Log(e);
		}
	}
}
