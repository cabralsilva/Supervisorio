package AGVS.WIFI.SUPERVISORIO;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import AGVS.Drivers.CLP.PLCComDriver;
//import Main.AGVCommunication;
//import Main.SupervisorioFord;
//import AGVS.Serial.SerialAGV;
import AGVS.Serial.ThActionSerialPacote;
import AGVS.Util.Log;
import AGVS.Util.Util;
import PLCCom.ePLCType;

public class Controle extends Thread {

	private Socket sk;
	private String tipo;
	public static String menssagemOld = null;

	public Controle(Socket sk, String tipo) {
		this.sk = sk;
		this.tipo = tipo;
	}

	public void AGV(String xml) {
		System.out.println(xml);
		try {
//			AGVCommunication.agv.enviar(xml);
		} catch (Exception e) {
			new Log(e);
		}

	}

	public static void Supervisorio(String xml) {
/*
		try {
			String cmd = Util.localizarStrXML(xml, "<c>", "</c>");
			switch (cmd) {
			// case "A":
			//
			// break;
			case "T":
				System.out.println(xml);
				System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 16,
						Integer.parseInt(Util.localizarStrXML(xml, "<a>", "</a>")),
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 26,
						Integer.parseInt(Util.localizarStrXML(xml, "<p>", "</p>")),
						ePLCType.S7_300_400_compatibel.name()));

				break;
			case "S":
				menssagemOld = xml;
				System.out.println(xml);
				int status = Integer.parseInt(Util.localizarStrXML(xml, "<s>", "</s>"));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 0, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 1, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 2, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 3, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 4, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 5, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 6, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 7, false,
						ePLCType.S7_300_400_compatibel.name()));

				switch (status) {

				case 1:
					System.out.println("Rodando");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 0, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 14, 0,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 2:
					System.out.println("Parado");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 1, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 14, 0,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 3:
					System.out.println("Obstaculo");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 2, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 14, 14,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 4:
					System.out.println("Manual");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 3, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 14, 0,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 5:
					System.out.println("Bateria");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 4, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 14, 15,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 6:
					System.out.println("Emergencia");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 5, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 14, 13,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 7:
					System.out.println("Fuga Rota");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 0, 6, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.err.println("Retorno = " + PLCComDriver.send(SupervisorioFord.IPPLC, 102, 52, 14, 16,
							ePLCType.S7_300_400_compatibel.name()));
					break;

				default:
					break;
				}
				break;
			case "A":

				System.out.println("Action");
				int ac = Integer.parseInt(Util.localizarStrXML(xml, "<a>", "</a>"));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 0, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 1, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 2, false,
						ePLCType.S7_300_400_compatibel.name()));
				System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 3, false,
						ePLCType.S7_300_400_compatibel.name()));
				switch (ac) {

				case 1:
					System.out.println("Init Dexindexa Action");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 2, true,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 2:
					System.out.println("Complet Dexindexa Action");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 3, true,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 3:
					System.out.println("Init Indexa Action");
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 0, true,
							ePLCType.S7_300_400_compatibel.name()));
					break;
				case 4:
					System.err.println("Retorno = " + PLCComDriver.sendBit(SupervisorioFord.IPPLC, 102, 52, 1, 1, true,
							ePLCType.S7_300_400_compatibel.name()));
					System.out.println("Complet Indexa Action");
					break;

				default:
					break;
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			new Log(e);
		}
*/
	}

	public void run() {
		OutputStream output = null;
		InputStream inputStream = null;
		try {
			output = sk.getOutputStream();
			inputStream = sk.getInputStream();
			int size = inputStream.read();
			byte[] b = new byte[size];
			inputStream.read(b);
			String xml = new String(b);
			if (tipo.equals(Servidor.AGV)) {
				AGV(xml);
			}
			if (tipo.equals(Servidor.SUPERVISORIO)) {
				int[] a = new int[xml.length()];
				for (int i = 0; i < xml.length(); i++) {
					a[i] = xml.getBytes()[i] & 0xFF;
				}
				new ThActionSerialPacote(xml, null, a);
			}

		} catch (Exception e) {
			// TODO: handle exception
			try {
				output.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {
				inputStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {
				sk.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

}
