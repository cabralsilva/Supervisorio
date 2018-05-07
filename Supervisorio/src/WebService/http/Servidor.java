package WebService.http;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;

import AGVS.Util.Log;
import WebService.HTML.ConvertPAGinHTML;


public class Servidor implements Runnable {
	private Thread thread = null;
	private boolean sair = false;
	private ServerSocket serverSocket = null;
	
	public Servidor() throws IOException {
		int porta = getPorta();
		open(porta);
	}
	
	
	public Servidor(int porta) throws IOException {
		open(porta);
	}
	
	private int getPorta() {
		String strPorta = System.getProperty(Config.PROP_HTTP_PORTA);
		if (strPorta != null) {
			System.out.println("(SYSTEM) HTTP.PORTA -> " + strPorta);
			return Integer.parseInt(strPorta);
		} else {
			Config config = Config.getInstance();			
			if (config.containsKey(Config.PROP_HTTP_PORTA)) {
				int porta = config.getInt(Config.PROP_HTTP_PORTA);
				System.out.println("(CONFIG) HTTP.PORTA -> " + porta);
				return porta;
			} else {
				System.out.println("(DEFAULT) HTTP.PORTA -> 80");
				return Config.DEFAULT_HTTP_PORTA;
			}
		}
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
		} catch(InterruptedException e) {
			new Log(e);
		}
		
		close();
		
		thread = null;
		sair = false;
	}
	
	protected void open(int porta) throws IOException {
		System.out.println("Criando ServerSocket.");
		serverSocket = new ServerSocket(porta);
	}
	
	protected void close() {
		if (serverSocket != null) {	
			try {
				System.out.println("Fechando ServerSocket.");
				serverSocket.close();
			} catch(Exception e) {
				new Log(e);
			}
			
			serverSocket = null;
		}
	}
	
	public void run() {
		while (!sair) {
			try {
				//System.out.println("Aguardando conexao.");
				System.gc();
				serverSocket.setSoTimeout(5000);
				Socket socket = serverSocket.accept();
				
				Worker worker = new Worker(socket);
				Thread.sleep(1);
				worker.start();
				
				
			} catch(SocketTimeoutException e) {
				// ignorar
			} catch(Exception e) {
				new Log(e);
			}
		}
	}
}