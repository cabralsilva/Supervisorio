package Testes;

import AGVS.Data.ConfigProcess;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import WebService.http.Servidor;

public class TesteConvertHex {
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 255; i++) {
				String temp = Integer.toHexString(i).toUpperCase();
				if(Integer.toHexString(i).toUpperCase().length() == 1)
				{
					temp = "0" + temp;
				}
				
				System.out.println(i + " | " + temp);
			}

		} catch (Exception e) {
			new Log(e);
		}
	}
}
