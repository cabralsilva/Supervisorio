package WebService.http;

import java.net.Socket;

import AGVS.Data.ConfigProcess;
import AGVS.Util.Log;

import java.io.InputStream;
import java.io.PrintStream;

public class Worker implements Runnable {
	private Thread thread;
	private boolean sair;
	private Socket socket;

	public Worker(Socket socket) {
		this.thread = null;
		this.sair = false;
		this.socket = socket;
	}

	public void start() {
		if (thread != null) {
			return;
		}

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		// System.out.println("Working - #" + Thread.currentThread().getId() + "
		// *******************");

		try {
			Response resp = new Response(socket.getOutputStream());

			Request req = new Request(socket.getInputStream());
			req.setResponse(resp);

			Dispatcher disp = new Dispatcher();
			disp.resolve(req, resp);
			disp = null;
			req = null;
			System.gc();
		} catch (Exception e) {
			new Log(e);
		} finally {

			try {
				// System.out.println(socket);
				// System.out.println("Close Connection - #" +
				// Thread.currentThread().getId() + " *******************");
				socket.close();
			} catch (Exception e) {
				new Log(e);
			}
		}
	}
}
