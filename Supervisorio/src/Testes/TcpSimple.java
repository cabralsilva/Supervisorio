package Testes;
//test3.java 7/23/97 - JAVA program to read registers via gateway

//compile as
//javac test3.java
//run as
//java test3 aswales1.modicon.com 1 2 3

import java.io.*;
import java.net.*;
import java.util.*;

public class TcpSimple {
	public static void main(String argv[]) {
		while (true) {
			try {
				String ip_adrs = "192.168.15.20";
				int unit = 4;
				int reg_no = 0;
				int num_regs = 20;
				System.out.println(
						"ip_adrs = " + ip_adrs + " unit = " + unit + " reg_no = " + reg_no + " num_regs = " + num_regs);

				// set up socket
				Socket es = new Socket(ip_adrs, 502);
				System.out.println("ES OK");
				OutputStream os = es.getOutputStream();
				System.out.println("OS OK");
				FilterInputStream is = new BufferedInputStream(es.getInputStream());
				System.out.println("IS OK");
				byte obuf[] = new byte[261];
				byte ibuf[] = new byte[261];
				int c = 0;
				int i;

				// build request of form 0 0 0 0 0 6 ui 3 rr rr nn nn
				for (i = 0; i < 5; i++)
					obuf[i] = 0;
				obuf[5] = 6;
				obuf[6] = (byte) unit;
				obuf[7] = 3;
				obuf[8] = (byte) (reg_no >> 8);
				obuf[9] = (byte) (reg_no & 0xff);
				obuf[10] = (byte) (num_regs >> 8);
				obuf[11] = (byte) (num_regs & 0xff);

				// send request
				os.write(obuf, 0, 12);

				// read response
				i = is.read(ibuf, 0, 261);
				if (i < 9) {
					if (i == 0) {
						System.out.println("unexpected close of connection at remote end");
					} else {
						System.out.println("response was too short - " + i + " chars");
					}
				} else if (0 != (ibuf[7] & 0x80)) {
					System.out.println("MODBUS exception response - type " + ibuf[8]);
				} else if (i != (9 + 2 * num_regs)) {
					System.out.println("incorrect response size is " + i +

							" expected" + (9 + 2 * num_regs));
				} else {
					for (i = 0; i < num_regs; i++) {
						int w = (ibuf[9 + i + i] << 8) + ibuf[10 + i + i];
						
						if (i == 0) {
							System.out.println("word " + i + " = " + (w));
							if (w >= 1024) {
								System.out.println("Botao ADC ON");
							} else {
								System.out.println("Botao ADC OFF");
							}
						}

						if (i == 5) {
							System.out.println("word " + i + " = " + (w));
							if (w == 256) {
								System.out.println("Botao 1 ON");
								System.out.println("Botao 2 OFF");
							} else if (w == 512) {
								System.out.println("Botao 1 OFF");
								System.out.println("Botao 2 ON");
							} else if (w == 768) {
								System.out.println("Botao 1 ON");
								System.out.println("Botao 2 ON");
							} else {
								System.out.println("Botao 1 OFF");
								System.out.println("Botao 2 OFF");
							}
						}
					}
				}

				// close down
				es.close();
				Thread.sleep(1100);
			} catch (Exception e) {
				System.out.println("exception :" + e);
			}
		}
	}
}