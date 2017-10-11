package Testes;

import java.util.Scanner;

import AGVS.Data.ConfigProcess;
import AGVS.Data.Tag;
import AGVS.Data.TagsRotas;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class ConfigurarPIDRemoto {
	public static void main(String[] args) {
		try {
			Serial serial = new Serial();
			serial.conectar();
			System.out.println("============================================================");
			System.out.println("                    Configurar PID Remoto                   ");
			System.out.println("============================================================");
			System.out.print("MAC 16: ");
			Scanner s = new Scanner(System.in);
			String mac16 = s.next();
			System.out.print("MAC 64: ");
			String mac64= s.next();
			String cmd = "";
			while (!cmd.equals("exit")) {
				System.out.print("kp: ");
				int kp = s.nextInt();
				System.out.print("ki: ");
				int ki = s.nextInt();
				System.out.print("kd: ");
				int kd = s.nextInt();
				System.out.print("Banda Morta: ");
				int bandaMorta = s.nextInt();
				System.out.print("velocidade (10) (20) (30) (40) (50) (60) (70) (80) (90) (100):");
				int vel = s.nextInt();
				serial.enviar(kp, ki, kd, vel, bandaMorta, mac16, mac64);
				System.out.println("Escreva exit para sair.");
				cmd = s.next();
			}
			serial.desconectar();
			
		} catch (Exception e) {
			new Log(e);
		}
	}
}
