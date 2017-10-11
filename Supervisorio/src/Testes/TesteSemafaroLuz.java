package Testes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AGVS.Data.ConfigProcess;
import AGVS.Data.MeshSerial;
import AGVS.Data.Semaforo;
import AGVS.Data.Tag;
import AGVS.Data.TagSemaforos;
import AGVS.Serial.DatabaseStatic;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Config;
import WebService.http.Servidor;

public class TesteSemafaroLuz {
	private static int i = 0;

	public static void main(String[] args) {
		try {
			ConfigProcess.serial.conectar();

			// 0013A20040F40A3E
			Config config = Config.getInstance();

			MeshSerial mssf1 = new MeshSerial(0, 0, "FFFC", config.getProperty(Config.PROP_GY_MS201), "Semaforo 1", 201);
			List<TagSemaforos> tagsVerdeSF1 = new ArrayList<TagSemaforos>();
			List<TagSemaforos> tagsVermelhoSF1 = new ArrayList<TagSemaforos>();
			List<TagSemaforos> tagsAmareloSF1 = new ArrayList<TagSemaforos>();

			Semaforo sf1 = new Semaforo(0, mssf1, "Semaforo 1", tagsVerdeSF1, tagsVermelhoSF1, tagsAmareloSF1, null, null, null);

			sf1.sinalVermelho();
			Thread.sleep(5000);

		} catch (Exception e) {
			new Log(e);
		}
	}
}
