import AGVS.Data.ConfigProcess;
import AGVS.Serial.DatabaseStatic;
import AGVS.Util.Log;
import AGVS.WIFI.SUPERVISORIO.ClienteAGV;
import WebService.http.Servidor;

public class Main {

	public static void main(String[] args) {
		//ConfigProcess.bd().insertLogTags(System.currentTimeMillis(), 1, "Tag Exemplo", "E0040150747423");
		try {
			new AGVS.WIFI.SUPERVISORIO.Servidor(AGVS.WIFI.SUPERVISORIO.Servidor.SUPERVISORIO).start();
			
			System.out.println("Iniciando Supervisorio 8325");
//			ConfigProcess.serial.conectar();
			Servidor serv = new Servidor();
			serv.start();
			System.out.println("Started server");
			new DatabaseStatic();
			ConfigProcess.verificaUsuarios();
			ConfigProcess.initSystem();
			System.out.println("Supervisorio Iniciado.");
//			ClienteAGV.enviar("<xml>PLAY</xml>", "192.168.43.123");
		} catch (Exception e) {
			new Log(e);
		}
	}
}
