package AGVS.Serial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.omg.CORBA.Environment;

import AGVS.Data.ConfigProcess;
import AGVS.Util.Log;

public class ReSendSerial extends Thread {

	private List<Pacote> pacotes;
	private Semaphore sp;

	public ReSendSerial() {
		sp = new Semaphore(1);
		pacotes = new ArrayList<Pacote>();
		this.start();
	}

	public void addPacote(int[] pk, int size) {
		try {
			sp.acquire();
			Pacote pacote = new Pacote(pk, System.currentTimeMillis(), size);
			pacotes.add(pacote);
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public void removePacote(int[] pk) {
		try {
			sp.acquire();
			int exI = -1;
			
			for (int i = 0; i < pacotes.size(); i++) {
				if (pacotes.get(i).getPk()[4] == pk[4]) {
					exI = i;
					i = pacotes.size();
					
				}
			}

			if (exI >= 0) {
				System.out.println("Pacote Removido: " + pk[4]);
				pacotes.remove(exI);
			}
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public void run() {
		while (true) {
			try {
				sp.acquire();
				
				for (int i = 0; i < pacotes.size(); i++) {
					if ((System.currentTimeMillis() - pacotes.get(i).getTime()) > 5000) {
						System.out.println("Enviado Novamente Pacote: " + pacotes.get(i).getPk()[4]);
						ConfigProcess.serial.enviar(pacotes.get(i).getPk(), pacotes.get(i).getSize());
						pacotes.get(i).setReplys(pacotes.get(i).getReplys() + 1);
						pacotes.get(i).setTime(System.currentTimeMillis());
						if(pacotes.get(i).getReplys() > 1) {
							System.out.println("Pacote enviado 3 Vezes: " + pacotes.get(i).getPk()[4]);
							pacotes.remove(i);
							i--;
						}
					}
				}
				sp.release();
				Thread.sleep(1000);
			} catch (Exception e) {
				new Log(e);
				sp.release();
			}
			
		}
	}
}
