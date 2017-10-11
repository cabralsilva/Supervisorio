import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Serial.DatabaseStatic;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import AGVS.Util.Util;
import WebService.http.Servidor;

public class Main {

	public static void main(String[] args) {
		//ConfigProcess.bd().insertLogTags(System.currentTimeMillis(), 1, "Tag Exemplo", "E0040150747423");
		try {
			System.out.println("Iniciando Supervisorio...");
			ConfigProcess.serial.conectar();
			Servidor serv = new Servidor();
			serv.start();
			System.out.println("Started server");
			new DatabaseStatic();
			ConfigProcess.verificaUsuarios();
			ConfigProcess.initSystem();
			System.out.println("Supervisorio Iniciado.");
		} catch (Exception e) {
			new Log(e);
		}
	}
}
