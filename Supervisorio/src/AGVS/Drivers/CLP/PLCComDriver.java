package AGVS.Drivers.CLP;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import PLCCom.ConnectCallbackNotifier;
import PLCCom.ConnectionStateChangeNotifier;
import PLCCom.OperationResult;
import PLCCom.PLCcomDevice;
import PLCCom.ReadDataRequest;
import PLCCom.ReadDataResult;
import PLCCom.TCP_ISO_Device;
import PLCCom.WriteDataRequest;
import PLCCom.WriteDataResult;
import PLCCom.authentication;
import PLCCom.eDataType;
import PLCCom.ePLCType;
import PLCCom.eRegion;
import AGVS.Util.Log;

public class PLCComDriver {
	public static final String msgError = "Error";
	public static final String msgOk = "ok";
	public static final String msgNo = "no";

	public static String send(String ip, int port, int db, int memory, int itValue, String typeCLP) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			authentication.User("Luiz Henrique Sehnem");
			authentication.Serial("93917-49118-126486-2067371");

			// create TCP_ISO_Device instance from PLCcomDevice
			PLCcomDevice Device = new TCP_ISO_Device(ip, 0, 0, ePLCType.valueOf(typeCLP));

			if (Device.IsConnected())
				Device.DisConnect();

			Device.setTCP_Port_PLC(port);
			Device.setTCP_Port_Local(0);

			Device.StateChangeNotifier = new ConnectionStateChangeNotifier(null);

			Device.ConnectCBNotifier = new ConnectCallbackNotifier(null);
			Device.setAutoConnect(true, 10000);

			WriteDataRequest myWriteRequest = new WriteDataRequest(eRegion.DataBlock, // Region
					db, // DB / only for datablock operations otherwise 0
					memory, // write start adress
					Byte.valueOf("0"));

			myWriteRequest.addInt((short) itValue);

			WriteDataResult res = Device.writeData(myWriteRequest);

			res = Device.writeData(myWriteRequest);
			// evaluate results
			if (res.Quality().equals(OperationResult.eQuality.GOOD) && res.Message().equals(msgOk)) {
				Device.DisConnect();
				return msgOk;
			} else {
				Device.DisConnect();
				return msgError;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return msgError;
		}
	}

	public static String request(String ip, int port, int db, int memory, int[] itValues, String typeCLP) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {

			authentication.User("Luiz Henrique Sehnem");
			authentication.Serial("93917-49118-126486-2067371");

			// create TCP_ISO_Device instance from PLCcomDevice
			PLCcomDevice Device = new TCP_ISO_Device(ip, 0, 0, ePLCType.valueOf(typeCLP));

			if (Device.IsConnected())
				Device.DisConnect();

			Device.setTCP_Port_PLC(port);
			Device.setTCP_Port_Local(0);

			Device.StateChangeNotifier = new ConnectionStateChangeNotifier(null);

			Device.ConnectCBNotifier = new ConnectCallbackNotifier(null);
			Device.setAutoConnect(true, 10000);

			if (itValues == null || itValues.length < 1) {

				Device.DisConnect();
				return msgError;
			}
			int range = 0;
			for (int i : itValues) {
				if (range < i)
					range = i;
			}
			range++;

			// S7_1200_compatibel
			ReadDataRequest myReadDataRequest = new ReadDataRequest(eRegion.DataBlock, // Region
					db, // DB / only for datablock operations otherwise 0
					memory, // read start adress
					eDataType.BIT, // desired datatype
					range); // Quantity of reading values

			ReadDataResult res = Device.readData(myReadDataRequest);
			if (res.Quality() == OperationResult.eQuality.GOOD) {
				for (int i : itValues) {

					if ((boolean) res.getValues()[i] == false) {
						Device.DisConnect();
						return msgNo;
					}
				}
				Device.DisConnect();
				return msgOk;
			} else {
				System.out.println("Mensagem de Falha: " + (res.Quality()));
				Device.DisConnect();
				return msgError;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return msgError;
		}
	}
}
