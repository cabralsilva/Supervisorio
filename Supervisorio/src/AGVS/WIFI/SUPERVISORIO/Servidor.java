package AGVS.WIFI.SUPERVISORIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import AGVS.Util.Log;
import WebService.http.Worker;

public class Servidor implements Runnable {
	private Thread thread = null;
	private boolean sair = false;
	private ServerSocket serverSocket = null;
	public static final String AGV = "AGV";
	public static final String SUPERVISORIO = "SUPERVISORIO";
	private String tipo;

	public Servidor(String tipo) throws IOException {
		int porta = getPorta();
		open(porta);
		this.tipo = tipo;
	}

	public Servidor(int porta) throws IOException {
		open(porta);
	}

	private int getPorta() {
//		return 27015;
		return 23;
	}

	public void start() {
		if (thread != null) {
			return;
		}

		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (thread == null) {
			return;
		}

		sair = true;

		try {
			thread.join();
		} catch (InterruptedException e) {
			new Log(e);
		}

		close();

		thread = null;
		sair = false;
	}

	protected void open(int porta) throws IOException {
		System.out.println("Criando ServerSocket wifi.");
		serverSocket = new ServerSocket(porta);
	}

	protected void close() {
		if (serverSocket != null) {
			try {
				System.out.println("Fechando ServerSocket.");
				serverSocket.close();
			} catch (Exception e) {
				new Log(e);
			}

			serverSocket = null;
		}
	}

	public void run() {
		while (!sair) {
			try {
				// System.out.println("Aguardando conexao.");
				System.gc();
				serverSocket.setSoTimeout(60000);
				Socket socket = serverSocket.accept();
				Controle cl = new Controle(socket, tipo);
				Thread.sleep(1);
				cl.start();

			} catch (SocketTimeoutException e) {
				// ignorar
			} catch (Exception e) {
				new Log(e);
			}
		}
	}
}
