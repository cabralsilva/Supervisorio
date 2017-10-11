package AGVS.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Log extends Thread {

	private final String dirLog = "Logs/";
	private final String arqLog = dirLog + "logserr";
	private static int infoLog = 0;
	private final String extLog = ".txt";

	private final int quantArq = 5;
	private final int lenArq = 100000000;
	private static Semaphore logRun = new Semaphore(1);
	public Exception msgLog;

	public Log(Exception msgLog) {
		this.msgLog = msgLog;
		this.start();
	}

	public void run() {
		try {
			Thread.sleep(0);
			File f = new File(dirLog);
			if (!f.exists()) {
				f.mkdir();
			}

			String auxFile = "";
			logRun.acquire();

			for (int i = 0; i < quantArq; i++) {

				auxFile = arqLog + i + extLog;
				f = new File(auxFile);

				if (!f.exists()) {
					f.delete();
					f.createNewFile();
				}

				if (f.length() < lenArq) {
					infoLog = i;
					break;
				} else if (i == quantArq - 1) {

					if (infoLog == quantArq - 1) {
						infoLog = 0;
					} else {
						infoLog++;
					}
					auxFile = arqLog + infoLog + extLog;
					f = new File(auxFile);
					f.delete();
					f.createNewFile();
					break;
				}
			}
			guardarLog(f);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		logRun.release();

	}

	private void guardarLog(File file) throws Exception {
		FileWriter fw = new FileWriter(file, true);

		BufferedWriter bw = new BufferedWriter(fw);

		bw.newLine();
		bw.write(Util.getDateTimeFormatoBR(System.currentTimeMillis()) + "#########################################");
		bw.newLine();
		bw.write(Util.convertTraceInString(msgLog));
		bw.newLine();
		bw.write("################################################################");
		bw.newLine();
		bw.close();
		fw.close();
	}

}
