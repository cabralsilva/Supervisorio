/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import AGVS.Data.ComandoMashSerial;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Cruzamento_OLD;
import AGVS.Data.MeshSerial;
import AGVS.Data.PortaMashSerial;
import AGVS.Data.Tag;
import AGVS.Data.TagsRotas;
import AGVS.Util.Log;
import AGVS.Util.Util;
import WebService.http.Config;

/**
 *
 * @author Luiz
 */
public class SerialTest implements SerialPortEventListener {

	private InputStream in;
	private SerialPort serialPort;
	private OutputStream out;
	private boolean conectado = false;
	private int frameIDPK = 1;
	private static Semaphore semp = new Semaphore(1);
	private int[] pacoteInt = new int[255];
	private int id = 0;

	public boolean getConectado() {
		return this.conectado;
	}

	public SerialTest() {
		for (int i = 0; i < pacoteInt.length; i++) {
			pacoteInt[i] = 0;

		}

	}

	public void conectar() {
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(Config.getInstance().getProperty(Config.PROP_SERIAL_PORT));
			if (portIdentifier.isCurrentlyOwned()) {
				System.out.println("Erro: Porta em uso");
			} else {
				CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

				if (commPort instanceof SerialPort) {
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(
							Integer.parseInt(Config.getInstance().getProperty(Config.PROP_SERIAL_BAUDRATE)),
							SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					System.out.println("Conexao Serial Start OK !");
					in = serialPort.getInputStream();
					out = serialPort.getOutputStream();

					serialPort.addEventListener(this);
					serialPort.notifyOnDataAvailable(true);
					conectado = true;
					System.out.println("Conectado");
				} else {
					System.out.println("Error: Somente as portas seriais s�o tratadas.");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			// TODO: handle exception
		}
	}

	public void enviar(String a, String mac16, String mac64) {
		try {
			semp.acquire();
			if (frameIDPK > 20) {
				frameIDPK = 1;
			}
			System.out.println("Enviando: " + a);
			int[] pk = new int[255];
			pk[0] = 0x7E;
			pk[1] = 0;
			pk[2] = a.length() + 14;
			pk[3] = 0x10;
			pk[4] = frameIDPK++;

			int checksum = 0;
			checksum += pk[3];
			checksum += pk[4];

			int i = 5;
			int j;
			for (j = 0; j < mac64.length(); j++) {
				pk[i] = Integer.parseInt(mac64.substring(j, j + 2), 16);
				checksum += pk[i++];
				j++;

			}
			for (j = 0; j < mac16.length(); j++) {
				pk[i] = Integer.parseInt(mac16.substring(j, j + 2), 16);
				checksum += pk[i++];
				j++;
			}

			pk[i++] = 0x00;
			pk[i++] = 0x00;

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				checksum += pk[i++];
			}

			checksum = (0xFF - (checksum & 0xFF)) & 0xFF;
			pk[i++] = checksum;

			//System.out.print("PACOTE:");
			for (int k = 0; k < i; k++) {
				out.flush();
				out.write((pk[k] & 0xFF));
				//System.out.print("(" + ((char) pk[k] & 0xFF) + ")");
				out.flush();

			}
			//System.out.print("\n");

			// ConfigProcess.reSendSerial.addPacote(pk, i);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		semp.release();
	}

	public void enviar(TagsRotas tr, String mac16, String mac64) {
		try {
			semp.acquire();
			if (frameIDPK > 20) {
				frameIDPK = 1;
			}
			System.out.println("Enviando Tag: " + tr.getTag().getEpc());
			int[] pk = new int[255];
			pk[0] = 0x7E;
			pk[1] = 0;
			// pk[2] = a.length() + 14;
			pk[3] = 0x10;
			pk[4] = frameIDPK++;

			int checksum = 0;
			checksum += pk[3];
			checksum += pk[4];

			int i = 5;
			int j;
			for (j = 0; j < mac64.length(); j++) {
				pk[i] = Integer.parseInt(mac64.substring(j, j + 2), 16);
				checksum += pk[i++];
				j++;

			}
			for (j = 0; j < mac16.length(); j++) {
				pk[i] = Integer.parseInt(mac16.substring(j, j + 2), 16);
				checksum += pk[i++];
				j++;
			}

			pk[i++] = 0x00;
			pk[i++] = 0x00;
			int sizeTotal = 14;

			String a = "<xml>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<t>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<e>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = tr.getTag().getEpc();

			HexBinaryAdapter tg = new HexBinaryAdapter();
			byte[] v = tg.unmarshal(a);
			for (j = 0; j < v.length; j++) {
				pk[i] = (v[j] & 0xff);
				sizeTotal++;
				System.out.println(j + " " + pk[i]);
				checksum += pk[i++];
			}

			a = "</e>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<c>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "" + tr.getTemporizador();

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</c>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<g>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			if (tr.getGirar().equals(TagsRotas.girarDesativado)) {
				pk[i] = '0';
				sizeTotal++;
				checksum += pk[i++];
			}

			if (tr.getGirar().equals(TagsRotas.girarDireita)) {
				pk[i] = '1';
				sizeTotal++;
				checksum += pk[i++];
			}

			if (tr.getGirar().equals(TagsRotas.girarEsquerda)) {
				pk[i] = '2';
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</g>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<a>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			if (tr.getEstadoAtuador().equals(TagsRotas.estAtuadorManter)) {
				pk[i] = '0';
				sizeTotal++;
				checksum += pk[i++];
			}

			if (tr.getEstadoAtuador().equals(TagsRotas.estAtuadorAtuado)) {
				pk[i] = '1';
				sizeTotal++;
				checksum += pk[i++];
			}

			if (tr.getEstadoAtuador().equals(TagsRotas.estAtuadorRecuado)) {
				pk[i] = '2';
				sizeTotal++;
				checksum += pk[i++];
			}

			if (tr.getEstadoAtuador().equals(TagsRotas.estAtuadorInverter)) {
				pk[i] = '3';
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</a>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<ts>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "" + tr.getTagParada();
			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</ts>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<tp>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "" + tr.getPitStop();
			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</tp>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<ss>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "" + tr.getSinalSonoro();
			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</ss>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<so>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "" + tr.getSensorObstaculo();
			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</so>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<v>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "" + tr.getVelocidade();
			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</v>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</t>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</xml>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			pk[2] = sizeTotal;
			checksum = (0xFF - (checksum & 0xFF)) & 0xFF;
			pk[i++] = checksum;

			//System.out.print("\n");
			for (int k = 0; k < i; k++) {
				out.flush();
				out.write(pk[k]);
				out.flush();

			}
			// ConfigProcess.reSendSerial.addPacote(pk, i);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		semp.release();
	}

	public void enviar(int kp, int ki, int kd, int velocidade, int bandaMorta, String mac16, String mac64) {
		try {
			semp.acquire();
			if (frameIDPK > 20) {
				frameIDPK = 1;
			}
			int[] pk = new int[255];
			pk[0] = 0x7E;
			pk[1] = 0;
			// pk[2] = a.length() + 14;
			pk[3] = 0x10;
			pk[4] = frameIDPK++;

			int checksum = 0;
			checksum += pk[3];
			checksum += pk[4];

			int i = 5;
			int j;
			for (j = 0; j < mac64.length(); j++) {
				pk[i] = Integer.parseInt(mac64.substring(j, j + 2), 16);
				checksum += pk[i++];
				j++;

			}
			for (j = 0; j < mac16.length(); j++) {
				pk[i] = Integer.parseInt(mac16.substring(j, j + 2), 16);
				checksum += pk[i++];
				j++;
			}

			pk[i++] = 0x00;
			pk[i++] = 0x00;
			int sizeTotal = 14;

			String a = "<xml>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<ct>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<pid>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			pk[i] = kp + 1;
			sizeTotal++;
			checksum += pk[i++];
			pk[i] = ki + 1;
			sizeTotal++;
			checksum += pk[i++];
			pk[i] = kd + 1;
			sizeTotal++;
			checksum += pk[i++];
			pk[i] = bandaMorta + 1;
			sizeTotal++;
			checksum += pk[i++];

			a = "</pid>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "<v>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "" + velocidade;

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</v>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</ct>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			a = "</xml>";

			for (j = 0; j < a.length(); j++) {
				pk[i] = a.getBytes()[j];
				sizeTotal++;
				checksum += pk[i++];
			}

			pk[2] = sizeTotal;
			checksum = (0xFF - (checksum & 0xFF)) & 0xFF;
			pk[i++] = checksum;
			//System.out.print("\n");
			for (int k = 0; k < i; k++) {
				out.flush();
				out.write(pk[k]);
				out.flush();

			}
			// ConfigProcess.reSendSerial.addPacote(pk, i);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		semp.release();
	}

	public void enviar(int[] pk, int size) {
		try {

			semp.acquire();
			//System.out.print("\n");
			for (int k = 0; k < size; k++) {
				out.flush();
				out.write(pk[k]);
				out.flush();
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		semp.release();
	}

	public void desconectar() {
		try {
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			serialPort.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private String pacote = "";
	// private int[] auxpk = new int[255];
	// private int idAux = 0;
	private int size = -1;

	public void serialEvent(SerialPortEvent arg0) {
		char data;

		try {
			while (in.available() > 0) {
				int ler = in.read();
				data = (char) ler;

				if (id < 255) {
					pacote += data;
					if (pacote.indexOf("<xml>") != -1) {
						pacoteInt[id++] = ler & 0xff;
					}

				}

				try {
					pacote = Util.localizarStrXML(pacote, "<xml>", "</xml>");
					int[] pacoteIntAux = new int[255];
					for (int i = 0; i < pacoteInt.length; i++) {
						if (id > i) {
							pacoteIntAux[i] = pacoteInt[i];
						} else {
							pacoteIntAux[i] = 0;
						}
						pacoteInt[i] = 0;
					}

					System.out.println(Util.getDateTimeFormatoBR(System.currentTimeMillis()) + " Pacote: " + pacote);
					
					pacote = "";
					id = 0;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			new Log(e);
		}

	}

	/*
	 * public void serialEvent(SerialPortEvent arg0) { char data;
	 * 
	 * try {
	 * 
	 * int ler = in.read(); data = (char) ler;
	 * 
	 * if (idAux < 255) { pacote += data;
	 * 
	 * if (idAux == 2) { size = data; } auxpk[idAux++] = data; if
	 * (pacote.indexOf("<xml>") != -1) { pacoteInt[id++] = ler & 0xff; }
	 * 
	 * } if (idAux > 255 || data == 0x7e) {
	 * System.out.println("\nInicio Pacote"); id = 0; idAux = 0; auxpk[idAux++]
	 * = data; size = -1; pacote = ""; System.out.print("\n"); for (int i = 0; i
	 * < 255; i++) { pacoteInt[i] = 0; } }
	 * 
	 * 
	 * 
	 * 
	 * if (size != -1 && idAux == size + 4) { System.out.println("Comando: " +
	 * auxpk[3]); if (auxpk[3] == 0x90) { try { pacote =
	 * Util.localizarStrXML(pacote, "<xml>", "</xml>"); int[] pacoteIntAux = new
	 * int[255]; for (int i = 0; i < pacoteInt.length; i++) { if (id > i) {
	 * pacoteIntAux[i] = pacoteInt[i]; } else { pacoteIntAux[i] = 0; }
	 * pacoteInt[i] = 0; }
	 * 
	 * System.out.println("Pacote: " + pacote); new ThActionSerialPacote(pacote,
	 * this, pacoteIntAux); pacote = "";
	 * 
	 * id = 0; } catch (Exception e) { // TODO: handle exception } } if
	 * (auxpk[3] == 139) { if (auxpk[8] == 0x00) {
	 * System.out.println("Pacote Enviado: " + auxpk[4]);
	 * //ConfigProcess.reSendSerial.removePacote(auxpk); } else {
	 * System.out.println("Falha no envio: " + auxpk[4]); } } }
	 * 
	 * } catch (Exception e) { new Log(e); }
	 * 
	 * }
	 */
}
