package AGVS.Serial;

import java.util.List;

import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.TagsRotas;

public class ThreadSendTags extends Thread{

	private String epc;
	private int agvId;
	public ThreadSendTags(String epc, int agvId) {
		this.epc = epc;
		this.agvId = agvId;
		this.start();
	}
	
	public void run(){
		
		AGV agv = ConfigProcess.bd().selecAGVS(agvId);
		if(agv != null) {
			
		}
		
	}

}
