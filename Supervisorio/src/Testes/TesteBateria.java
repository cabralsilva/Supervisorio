package Testes;

public class TesteBateria {
	public static void main(String argv[]) {
		try {
			SerialTest serial = new SerialTest();
			serial.conectar();
			while (true) {

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
