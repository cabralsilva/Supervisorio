package Testes;

import java.util.Scanner;

import AGVS.Serial.Serial;

public class TesteSerial {

	public static void main(String[] args) {
		Serial serial = new Serial();
		serial.conectar();
		System.out.println("***************************************************");
		System.out.println("**************:AGVS LEITURA DE TAGS:***************");
		System.out.println("***************************************************");
		System.out.println("Digite algo para finalizar...");
		
		new Scanner(System.in).next();
		serial.desconectar();
	}

}
