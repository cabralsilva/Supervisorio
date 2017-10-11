package AGVS.Util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import AGVS.Data.ConfigProcess;
import WebService.http.Config;

public class Util {

	public static final String nameArqBd = "bancoConf.xml";

	public static String centralizeString(String str, int i, String f) {
		int c = 0;
		while (str.length() < i) {
			if (c == 0) {
				str = str + f;
				c = 1;
			} else if (c == 1) {
				str = f + str;
				c = 0;
			} else {
				c = 0;
			}
		}
		return str;
	}

	public static String getXml(String key, String arquivo) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(arquivo));
			document.getDocumentElement().normalize();
			return document.getElementsByTagName(key).item(0).getTextContent();
		} catch (Exception e) {
			new Log(e);
		}
		return "";

	}

	public static boolean setXml(String key, String value) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(nameArqBd));
			document.getDocumentElement().normalize();
			document.getElementsByTagName(key).item(0).setTextContent(value);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(nameArqBd));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			return true;
		} catch (Exception e) {
			new Log(e);
		}
		return false;
	}

	public static String getXml(String key) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(nameArqBd));
			document.getDocumentElement().normalize();
			return document.getElementsByTagName(key).item(0).getTextContent();
		} catch (Exception e) {
			new Log(e);
		}
		return "";

	}

	public static String convertTraceInString(Exception e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		e.printStackTrace(pw);

		sw.flush();
		pw.flush();

		String ret = sw.toString();

		try {
			sw.close();
		} catch (Exception e1) {
			new Log(e1);
		}

		pw.close();

		return ret;
	}

	public static String trim(String testeTrim) {
		return testeTrim.replaceAll("\\s+", "");
	}

	public static String ltrim(String testeTrim) {
		return testeTrim.replaceAll("^\\s+", "");
	}

	public static String rtrim(String testeTrim) {
		return testeTrim.replaceAll("\\s+$", "");
	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static String localizarStrXML(String xml, String procura1, String procura2) {
		int pos1 = xml.indexOf(procura1) + procura1.length();
		int pos2 = xml.indexOf(procura2);
		return xml.substring(pos1, pos2);
	}

	public static String criarXMLUsr(String login, String password) {
		return "<login>" + login + "</login>" + "<password>" + password + "</password>";
	}

	public static String gerarCriptMD5(String msg) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(msg.getBytes(), 0, msg.length());
		return new BigInteger(1, m.digest()).toString(16);
	}

	public final static int size_mac64 = 16;
	public final static int size_mac16 = 4;

	public static boolean verificaMac(String mac64, int len) {
		if (mac64.toCharArray().length == len) {
			mac64 = mac64.toUpperCase();
			if (len == size_mac16) {
				return true;
			}
			char[] valids = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			for (int i = 0; i < mac64.toCharArray().length; i++) {
				boolean error = false;
				for (int j = 0; j < valids.length; j++) {
					if (valids[j] == mac64.toCharArray()[i]) {
						error = true;
						break;
					}
				}
				if (!error) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static void gravaTexto(File f, int fator, String texto) throws Exception {
		BufferedImage im = ImageIO.read(f);
		Graphics2D g = im.createGraphics();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(texto, 5, im.getHeight() - 5); // im.getWidth()
		ImageIO.write(im, "jpeg", f);
	}

	public static String getDateTime(long time) {
		Config config = Config.getInstance();
		DateFormat dateFormat;
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			//dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");
		} else {
			dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time));
		return (dateFormat.format(cal.getTime()));
	}

	public static String getDateTimeBR(long time) {
		Config config = Config.getInstance();
		DateFormat dateFormat;
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
			dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			//dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");
		} else {
			dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time));
		return (dateFormat.format(cal.getTime()));
	}
	
	public static String getTimeOnly(long time) {
		Config config = Config.getInstance();
		DateFormat dateFormat;
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
			dateFormat = new SimpleDateFormat("HH:mm:ss");
			//dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.SSS");
		} else {
			dateFormat = new SimpleDateFormat("HH:mm:ss");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time));
//		System.out.println(dateFormat.format(cal.getTime()));
		return (dateFormat.format(cal.getTime()));
	}

	public static long getDateTime(String time) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		Date d = dateFormat.parse(time);
		return d.getTime();
	}

	public static String getDateTimeFormatoBR(long time) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time));
		return (dateFormat.format(cal.getTime()));
	}

	public static String getDateTimeFormatoBRSomenteData(long time) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time));
		return (dateFormat.format(cal.getTime()));
	}

	public static long getConvertDateBD(String time) {
		if (time == null) {
			return 0;
		}
		Config config = Config.getInstance();
		SimpleDateFormat f;
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
			f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		} else {
			f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		}

		Date d;
		long milliseconds = 0;
		try {
			d = f.parse(time);
			milliseconds = d.getTime();
		} catch (ParseException e) {
			new Log(e);
		}
		return milliseconds;
	}

	public static Long getDateTimeLong(long time) {

		return new Date(time).getTime();
	}

	public static byte[] getImagemConvertStringtoBytes(String imagem) {

		byte[] retImg = Base64.getDecoder().decode(imagem);
		return retImg;

	}

	public static String getImagemConvertBytestoString(byte[] imagem) {

		String retImg = Base64.getEncoder().encodeToString(imagem);
		return retImg;

	}
	
	public static String getDurationConvertString(Duration d) {
		LocalTime t = LocalTime.MIDNIGHT.plus(d);
		return DateTimeFormatter.ofPattern("HH:mm:ss").format(t);
	}

	public static Duration getStringConvertDuration(String s) {
		return Duration.between ( LocalTime.MIN , LocalTime.parse ( s ) );
	}
}
