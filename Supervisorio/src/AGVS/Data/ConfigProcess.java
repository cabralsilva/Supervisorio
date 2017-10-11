package AGVS.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import AGVS.Controle.Goodyear.XMLControleGoodyear;
import AGVS.Controller.Rules.RulesUsuarios;
import AGVS.Serial.ReSendSerial;
import AGVS.Serial.Serial;
import AGVS.Util.Log;
import AGVS.Util.Util;

import WebService.http.Config;

public class ConfigProcess {

	public static final String PROJ_TOYOTA_SOROCABA = "TOYOTA_SOROCABA";
	public static final String PROJ_TOYOTA_INDAIATUBA = "TOYOTA_INDAIATUBA";
	public static final String PROJ_GOODYEAR = "GOODYEAR";
	public static final String PROJ_FIAT = "FIAT";
	public static Serial serial = new Serial();

	// public static ReSendSerial reSendSerial = new ReSendSerial();

	public static void verificaUsuarios() {
		List<Usuario> usrs = bd().selectUsuarios();
		boolean criar = true;
		for (int i = 0; usrs != null && i < usrs.size(); i++) {
			if (usrs.get(i).getPermissao().equals(RulesUsuarios.permissaoAdministrador)) {
				criar = false;
				break;
			}
		}
		if (criar) {
			try {
				bd().insertUsuarios("agvs", "agvs", "agvs@agvs.com.br", Util.gerarCriptMD5("agvs"),
						RulesUsuarios.permissaoAdministrador);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				new Log(e);
			}
		}
	}

	public static XMLControleGoodyear xmlControleGoodyear = null;

	// Inicia o Sistema para o projeto especifico.
	public static void initSystem() {
		Config config = Config.getInstance();
		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
			xmlControleGoodyear = new XMLControleGoodyear();
		}
	}

	public static BancoDados bd() {

		return new SQLServer(Util.getXml("instancia"), Util.getXml("user"), Util.getXml("pwd"));

	}

}
