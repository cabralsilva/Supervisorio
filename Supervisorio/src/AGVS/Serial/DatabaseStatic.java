package AGVS.Serial;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import AGVS.Data.AGV;
import AGVS.Data.AlertaMesh;
import AGVS.Data.AlertasSupervisorio;
import AGVS.Data.Cancelas;
import AGVS.Data.ComandoMashSerial;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Cruzamento;
import AGVS.Data.CruzamentoMash;
import AGVS.Data.FuncaoPos;
import AGVS.Data.Input;
import AGVS.Data.LogZoneTime;
import AGVS.Data.MeshSerial;
import AGVS.Data.PausablePlayer;
import AGVS.Data.PortaMashSerial;
import AGVS.Data.PosicaoInicioTurno;
import AGVS.Data.Semaforo;
import AGVS.Data.Tag;
import AGVS.Data.TagAtraso;
import AGVS.Data.TagCruzamento;
import AGVS.Data.TagCruzamentoMash;
import AGVS.Data.ZoneTime;
import AGVS.Util.Log;
import WebService.http.Config;
import javazoom.jl.decoder.JavaLayerException;

public class DatabaseStatic {

	public static List<ComandoMashSerial> cms;
	public static List<MeshSerial> mashs;
	public static List<Cruzamento> cruzamentos;
	public static List<Semaforo> semaforos;
	public static List<PortaMashSerial> pms;
	public static List<Cancelas> cancelas;
	public static List<TagAtraso> tagsAtraso;
	public static List<PortaMashSerial> pmsBuffer;
	public static List<PortaMashSerial> pmsDoorOff;
	public static List<Semaforo> semafaros;
	public static List<CruzamentoMash> cruzamentoMash;
	public static List<TagCruzamentoMash> tagCruzamentoMash;
	public static List<AlertasSupervisorio> alertasSupervisorio;
	public static List<PosicaoInicioTurno> pits;
	public static List<FuncaoPos> funcPos;
	public static List<ZoneTime> zoneTimes;
	public static List<LogZoneTime> logZoneTimes;
	public static List<AGV> lstAGVS = new ArrayList<AGV>();
	//FIAT 25
	public static AGV bufferEntradaLinhaD;
	public static AGV bufferEntradaLinhaE;
	public static List<AGV> bufferEmRotaCarregadoLinhaE;
	public static List<AGV> bufferEmRotaCarregadoLinhaD;
	public static String macSupervisorio = "";
	///////////////////////////////////
	
	public static void resetMedia() {
		try {
//			streamMedia = new FileInputStream("media/001.mp3"); 
//			player = new PausablePlayer(streamMedia);
		} catch (Exception e) {
			// TODO: handle exception
			new Log(e);
		}
		
	}
	
	public static void resetZoneTime() {
		zoneTimes = ConfigProcess.bd().selectZoneTime();
		System.out.println("Reset ZoneTimes");
	}

	public static void resetAGVS() {
		lstAGVS = ConfigProcess.bd().selecAGVSLastSixUpdate();
		System.out.println("Reset AGVS");
	}

	public static void resetTagAtraso() {
		tagsAtraso = ConfigProcess.bd().selectTagAtraso();
		System.out.println("Reset Tag Atraso");
	}
	
	public static void resetMeshs() {
		mashs = ConfigProcess.bd().selectMeshSerial();
		for (MeshSerial ms : mashs) {
			ms.setLstPms(ConfigProcess.bd().selectGatesInByMesh(ms.getId()));
		}
	}

	public static void resetCruzamentos() {
		cruzamentos = ConfigProcess.bd().selectCruzamentosLogic();

		for (int i = 0; cruzamentos != null && i < cruzamentos.size(); i++) {
			if (cruzamentos.get(i).getNome().equals("buffer")) {
				cruzamentos.get(i).setPms(pmsBuffer);
				System.out.println("pms do cruzamento do buffer criado");
			}
			if (cruzamentos.get(i).getNome().equals("DoorOff")) {
				cruzamentos.get(i).setPms(pmsDoorOff);
				System.out.println("pms do cruzamento do dooroff criado");
			}
		}

		System.out.println("Reset Cruzamentos");

	}
	
	public static void resetSemaforos() {
		semafaros = ConfigProcess.bd().selectSemaforosLogic();
		System.out.println("Reset Semáforos");

	}

	public void DBIndaituba() {

		Config config = Config.getInstance();

		funcPos = new ArrayList<FuncaoPos>();
		pits = new ArrayList<PosicaoInicioTurno>();
		PosicaoInicioTurno pitP1 = new PosicaoInicioTurno("P1",
				new Tag(config.getProperty(Config.PROP_P1), "", 0, 0, 0), null, null);
		PosicaoInicioTurno pitP2 = new PosicaoInicioTurno("P2",
				new Tag(config.getProperty(Config.PROP_P2), "", 0, 0, 0), null, null);
		PosicaoInicioTurno pitP3 = new PosicaoInicioTurno("P3",
				new Tag(config.getProperty(Config.PROP_P3), "", 0, 0, 0), null, null);
		PosicaoInicioTurno pitP4 = new PosicaoInicioTurno("P4",
				new Tag(config.getProperty(Config.PROP_P4), "", 0, 0, 0), null, null);
		PosicaoInicioTurno pitP5 = new PosicaoInicioTurno("P5",
				new Tag(config.getProperty(Config.PROP_P5), "", 0, 0, 0), null, null);
		PosicaoInicioTurno pitP6 = new PosicaoInicioTurno("P6",
				new Tag(config.getProperty(Config.PROP_P6), "", 0, 0, 0), null, null);
		pits.add(pitP1);
		pits.add(pitP2);
		pits.add(pitP3);
		pits.add(pitP4);
		pits.add(pitP5);
		pits.add(pitP6);

		List<FuncaoPos> fps1 = new ArrayList<FuncaoPos>();
		List<FuncaoPos> fps2 = new ArrayList<FuncaoPos>();
		List<FuncaoPos> fps3 = new ArrayList<FuncaoPos>();
		List<FuncaoPos> fps4 = new ArrayList<FuncaoPos>();
		List<FuncaoPos> fps5 = new ArrayList<FuncaoPos>();
		List<FuncaoPos> fps6 = new ArrayList<FuncaoPos>();

		FuncaoPos fpP1_1 = new FuncaoPos(pitP1, "", FuncaoPos.RODANDO, 0,
				new Cruzamento("Cruzamento 3", "", null, null, null));

		FuncaoPos fpP2_1 = new FuncaoPos(pitP2, "", FuncaoPos.RODANDO, 0,
				new Cruzamento("Cruzamento 4", "", null, null, null));

		FuncaoPos fpP3_1 = new FuncaoPos(pitP3, "", FuncaoPos.RODANDO, 0,
				new Cruzamento("Cruzamento 1", "", null, null, null));

		FuncaoPos fpP3_2 = new FuncaoPos(pitP3, "", FuncaoPos.RODANDO, 0,
				new Cruzamento("Cruzamento 7", "", null, null, null));

		FuncaoPos fpP3_3 = new FuncaoPos(pitP3, "", FuncaoPos.FILAESPERA, 0,
				new Cruzamento("Cruzamento 2", "", null, null, null));

		FuncaoPos fpP4_1 = new FuncaoPos(pitP4, "", FuncaoPos.RODANDO, 0,
				new Cruzamento("Cruzamento 2", "", null, null, null));

		FuncaoPos fpP5_1 = new FuncaoPos(pitP6, "", FuncaoPos.FILAESPERA, 1,
				new Cruzamento("Cruzamento 6", "", null, null, null));

		FuncaoPos fpP6_1 = new FuncaoPos(pitP5, "", FuncaoPos.RODANDO, 0,
				new Cruzamento("Cruzamento 6", "", null, null, null));

		FuncaoPos fpP6_2 = new FuncaoPos(pitP5, "", FuncaoPos.FILAESPERA, 1,
				new Cruzamento("Cruzamento 3", "", null, null, null));

		funcPos.add(fpP1_1);
		funcPos.add(fpP2_1);
		funcPos.add(fpP3_1);
		funcPos.add(fpP3_2);
		funcPos.add(fpP3_3);
		funcPos.add(fpP4_1);
		funcPos.add(fpP5_1);
		funcPos.add(fpP6_1);
		funcPos.add(fpP6_2);

		fps1.add(fpP1_1);
		fps2.add(fpP2_1);
		fps3.add(fpP3_1);
		fps3.add(fpP3_2);
		fps3.add(fpP3_3);
		fps4.add(fpP4_1);
		fps5.add(fpP5_1);
		fps6.add(fpP6_1);
		fps6.add(fpP6_2);

		MeshSerial supervi = new MeshSerial(0, 0, "FFFC", "0013A200415655C8", "Mash Supervisorio", 255);

		alertasSupervisorio = new ArrayList<>();
		AlertasSupervisorio as = new AlertasSupervisorio("Alerta Supervisorio", new AlertaMesh("Pirulito",
				new Input(supervi, "Vermelho", 0, 0, 0, 1), new Input(supervi, "Verde", 0, 0, 0, 0)));
		alertasSupervisorio.add(as);


		// ***************************************************************************************************************
		// ***************************************************************************************************************

		System.out.println("Config Indaiatuba");
		DatabaseStatic.pms = new ArrayList<PortaMashSerial>();
		DatabaseStatic.cancelas = new ArrayList<Cancelas>();
		MeshSerial ms1 = new MeshSerial(0, 0, "FFFC", "0013A20041565607", "MS1", 95);
		MeshSerial ms7 = new MeshSerial(0, 0, "FFFC", "0013A2004154B69E", "MS2", 96);
		MeshSerial ms2 = new MeshSerial(0, 0, "FFFC", "0013A200415655BC", "Cancela 1", 91);
		MeshSerial ms3 = new MeshSerial(0, 0, "FFFC", "0013A20041565603", "Cancela 2", 92);
		MeshSerial ms4 = new MeshSerial(0, 0, "FFFC", "0013A20040F40AF6", "RFID 1", 250);
		MeshSerial ms5 = new MeshSerial(0, 0, "FFFC", "0013A200415655FF", "RFID 2", 251);
		MeshSerial ms6 = new MeshSerial(0, 0, "FFFC", "0013A2004154B69E", "Controle Caminhao", 50);
		MeshSerial ms8 = new MeshSerial(0, 0, "FFFC", "0013A20040F40A3B", "Controle Caminhao2", 105);

		DatabaseStatic.mashs = new ArrayList<MeshSerial>();

		mashs.add(ms1);
		mashs.add(ms2);
		mashs.add(ms3);
		mashs.add(ms4);
		mashs.add(ms5);
		mashs.add(ms6);
		mashs.add(ms7);
		mashs.add(ms8);
		DatabaseStatic.cms = new ArrayList<ComandoMashSerial>();

		List<Tag> tgsLiberaTKLA = new ArrayList<Tag>();
		List<Tag> tgsLiberaTKLB = new ArrayList<Tag>();
		Tag tg26 = new Tag(config.getProperty(Config.PROP_PARADA_1_TKL_VAZIO), "", 1, 0, 0);
		Tag tg26a = new Tag(config.getProperty(Config.PROP_PARADA_1_TKL_VAZIO2), "", 1, 0, 0);
		Tag tg26b = new Tag(config.getProperty(Config.PROP_PARADA_1_TKL_VAZIO3), "", 1, 0, 0);

		Tag tg27 = new Tag(config.getProperty(Config.PROP_PARADA_2_TKL_VAZIO), "", 1, 0, 0);
		Tag tg27a = new Tag(config.getProperty(Config.PROP_PARADA_2_TKL_VAZIO2), "", 1, 0, 0);

		Tag tg36 = new Tag(config.getProperty(Config.PROP_PARADA_1_TKL_CHEIO), "", 1, 0, 0);
		Tag tg36a = new Tag(config.getProperty(Config.PROP_PARADA_1_TKL_CHEIO2), "", 1, 0, 0);

		tgsLiberaTKLA.add(tg26);
		tgsLiberaTKLA.add(tg26a);
		tgsLiberaTKLA.add(tg26b);
		tgsLiberaTKLA.add(tg27);
		tgsLiberaTKLA.add(tg27a);
		tgsLiberaTKLB.add(tg36);
		tgsLiberaTKLB.add(tg36a);

		List<PortaMashSerial> portaMashSerialLiberaTKLA = new ArrayList<PortaMashSerial>();
		List<PortaMashSerial> portaMashSerialLiberaTKLB = new ArrayList<PortaMashSerial>();
		PortaMashSerial pms1;
		if (config.getProperty(Config.PROP_MODE_MASH_TKL).equals("NEW")) {
			pms1 = new PortaMashSerial("Liberar TKL A", PortaMashSerial.portE1, PortaMashSerial.E1_ON, null, ms7);
		} else {
			pms1 = new PortaMashSerial("Liberar TKL A", PortaMashSerial.portE1, PortaMashSerial.E1_ON, null, ms1);
		}

		PortaMashSerial pms2 = new PortaMashSerial("Liberar TKL B", PortaMashSerial.portE2, PortaMashSerial.E2_ON, null, ms1);
		PortaMashSerial pms3 = new PortaMashSerial("LC1", PortaMashSerial.portE1, PortaMashSerial.E1_ON, null, ms2);
		PortaMashSerial pms4 = new PortaMashSerial("LC2", PortaMashSerial.portE1, PortaMashSerial.E1_ON, null, ms3);

		PortaMashSerial pms5 = new PortaMashSerial("RFID1", PortaMashSerial.portE1, PortaMashSerial.E1_ON, null, ms4);
		PortaMashSerial pms6 = new PortaMashSerial("RFID2", PortaMashSerial.portE1, PortaMashSerial.E1_ON, null, ms5);
		PortaMashSerial pms7 = new PortaMashSerial("Controle Caminhao", PortaMashSerial.portE8, PortaMashSerial.E8_ON,
				null, ms6);

		PortaMashSerial pms8 = new PortaMashSerial("Controle Caminhao2", PortaMashSerial.portE1, PortaMashSerial.E1_ON,
				null, ms8);

		pms.add(pms1);
		pms.add(pms2);
		pms.add(pms3);
		pms.add(pms4);
		pms.add(pms5);
		pms.add(pms6);
		pms.add(pms7);
		pms.add(pms8);

		List<PortaMashSerial> portaMashSerialCruzamento = new ArrayList<PortaMashSerial>();
		List<TagCruzamento> tagsEntradaCruzamento = new ArrayList<TagCruzamento>();
		List<TagCruzamento> tagsSaidaCruzamento = new ArrayList<TagCruzamento>();

		Tag tag96 = new Tag(config.getProperty(Config.PROP_ENTRADA_1_CAMINHAO), "Tag 96", 96, 0, 0);
		Tag tag97 = new Tag(config.getProperty(Config.PROP_ENTRADA_2_CAMINHAO), "Tag 97", 97, 0, 0);
		Tag tag69 = new Tag(config.getProperty(Config.PROP_ENTRADA_3_CAMINHAO), "Tag 69", 69, 0, 0);
		Tag tag70 = new Tag(config.getProperty(Config.PROP_ENTRADA_4_CAMINHAO), "Tag 70", 70, 0, 0);

		tagsEntradaCruzamento.add(new TagCruzamento("tag96", tag96, null, TagCruzamento.tipoEntrada));
		tagsEntradaCruzamento.add(new TagCruzamento("tag97", tag97, null, TagCruzamento.tipoEntrada));
		tagsEntradaCruzamento.add(new TagCruzamento("tag69", tag69, null, TagCruzamento.tipoEntrada));
		tagsEntradaCruzamento.add(new TagCruzamento("tag70", tag70, null, TagCruzamento.tipoEntrada));

		Tag tag63 = new Tag(config.getProperty(Config.PROP_SAIDA_1_CAMINHAO), "Tag 63", 63, 0, 0);
		Tag tag64 = new Tag(config.getProperty(Config.PROP_SAIDA_2_CAMINHAO), "Tag 64", 64, 0, 0);
		Tag tag74 = new Tag(config.getProperty(Config.PROP_SAIDA_3_CAMINHAO), "Tag 74", 74, 0, 0);
		Tag tag75 = new Tag(config.getProperty(Config.PROP_SAIDA_4_CAMINHAO), "Tag 75", 75, 0, 0);

		tagsSaidaCruzamento.add(new TagCruzamento("tag63", tag63, null, TagCruzamento.tipoSaida));
		tagsSaidaCruzamento.add(new TagCruzamento("tag64", tag64, null, TagCruzamento.tipoSaida));
		tagsSaidaCruzamento.add(new TagCruzamento("tag74", tag74, null, TagCruzamento.tipoSaida));
		tagsSaidaCruzamento.add(new TagCruzamento("tag75", tag75, null, TagCruzamento.tipoSaida));

		portaMashSerialCruzamento.add(pms5);
		portaMashSerialCruzamento.add(pms6);
		portaMashSerialCruzamento.add(pms7);
		portaMashSerialCruzamento.add(pms8);

		cruzamentoMash = new ArrayList<CruzamentoMash>();
		CruzamentoMash cm1 = new CruzamentoMash("Caminhao e RFID", tagsEntradaCruzamento, tagsSaidaCruzamento,
				portaMashSerialCruzamento, null, null, null);
		cruzamentoMash.add(cm1);

		Tag tage1 = new Tag(config.getProperty(Config.PROP_ENTRADA_1_CANCELA), "Tag 9", 9, 0, 0);

		Tag tage2 = new Tag(config.getProperty(Config.PROP_ENTRADA_2_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage3 = new Tag(config.getProperty(Config.PROP_ENTRADA_3_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage4 = new Tag(config.getProperty(Config.PROP_ENTRADA_4_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage5 = new Tag(config.getProperty(Config.PROP_ENTRADA_5_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage6 = new Tag(config.getProperty(Config.PROP_ENTRADA_6_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage7 = new Tag(config.getProperty(Config.PROP_ENTRADA_7_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage8 = new Tag(config.getProperty(Config.PROP_ENTRADA_8_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage9 = new Tag(config.getProperty(Config.PROP_ENTRADA_9_CANCELA), "Tag 41", 9, 0, 0);

		Tag tage10 = new Tag(config.getProperty(Config.PROP_ENTRADA_10_CANCELA), "Tag 41", 9, 0, 0);

		Tag tags1 = new Tag(config.getProperty(Config.PROP_SAIDA_1_CANCELA), "Tag 48", 9, 0, 0);

		Tag tags2 = new Tag(config.getProperty(Config.PROP_SAIDA_2_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags3 = new Tag(config.getProperty(Config.PROP_SAIDA_3_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags4 = new Tag(config.getProperty(Config.PROP_SAIDA_4_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags5 = new Tag(config.getProperty(Config.PROP_SAIDA_5_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags6 = new Tag(config.getProperty(Config.PROP_SAIDA_6_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags7 = new Tag(config.getProperty(Config.PROP_SAIDA_7_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags8 = new Tag(config.getProperty(Config.PROP_SAIDA_8_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags9 = new Tag(config.getProperty(Config.PROP_SAIDA_9_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags10 = new Tag(config.getProperty(Config.PROP_SAIDA_10_CANCELA), "Tag 14", 14, 0, 0);

		Tag tags11 = new Tag(config.getProperty(Config.PROP_SAIDA_11_CANCELA), "Tag 14", 14, 0, 0);

		TagCruzamento tce1 = new TagCruzamento("Tag 9", tage1, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce2 = new TagCruzamento("Tag 41", tage2, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce3 = new TagCruzamento("Tag 41", tage3, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce4 = new TagCruzamento("Tag 41", tage4, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce5 = new TagCruzamento("Tag 41", tage5, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce6 = new TagCruzamento("Tag 41", tage6, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce7 = new TagCruzamento("Tag 41", tage7, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce8 = new TagCruzamento("Tag 41", tage8, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce9 = new TagCruzamento("Tag 41", tage9, null, TagCruzamento.tipoEntrada);
		TagCruzamento tce10 = new TagCruzamento("Tag 41", tage10, null, TagCruzamento.tipoEntrada);
		List<TagCruzamento> tces = new ArrayList<TagCruzamento>();
		tces.add(tce1);
		tces.add(tce2);
		tces.add(tce3);
		tces.add(tce4);
		tces.add(tce5);
		tces.add(tce6);
		tces.add(tce7);
		tces.add(tce8);
		tces.add(tce9);
		tces.add(tce10);
		TagCruzamento tcs1 = new TagCruzamento("Tag 48", tags1, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs2 = new TagCruzamento("Tag 14", tags2, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs3 = new TagCruzamento("Tag 14", tags3, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs4 = new TagCruzamento("Tag 14", tags4, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs5 = new TagCruzamento("Tag 14", tags5, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs6 = new TagCruzamento("Tag 14", tags6, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs7 = new TagCruzamento("Tag 14", tags7, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs8 = new TagCruzamento("Tag 14", tags8, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs9 = new TagCruzamento("Tag 14", tags9, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs10 = new TagCruzamento("Tag 14", tags10, null, TagCruzamento.tipoSaida);
		TagCruzamento tcs11 = new TagCruzamento("Tag 14", tags11, null, TagCruzamento.tipoSaida);
		List<TagCruzamento> tcss = new ArrayList<TagCruzamento>();
		tcss.add(tcs1);
		tcss.add(tcs2);
		tcss.add(tcs3);
		tcss.add(tcs4);
		tcss.add(tcs5);
		tcss.add(tcs6);
		tcss.add(tcs7);
		tcss.add(tcs8);
		tcss.add(tcs9);
		tcss.add(tcs10);
		tcss.add(tcs11);
		Cancelas c1 = new Cancelas("Cruzamento Caminhao", tces, tcss, pms3, pms4, Cancelas.SINALLOW, Cancelas.SINALLOW);

		DatabaseStatic.cancelas.add(c1);

		portaMashSerialLiberaTKLA.add(pms1);
		portaMashSerialLiberaTKLB.add(pms2);

		ComandoMashSerial cmsLiberaPTKLA = new ComandoMashSerial("Libera Para TKL A", ComandoMashSerial.liberar,
				tgsLiberaTKLA, portaMashSerialLiberaTKLA, null, null, null);
		ComandoMashSerial cmsLiberaPTKLB = new ComandoMashSerial("Libera Para TKL B", ComandoMashSerial.liberar,
				tgsLiberaTKLB, portaMashSerialLiberaTKLB, null, null, null);

		cms.add(cmsLiberaPTKLA);
		cms.add(cmsLiberaPTKLB);

		System.out.println("Fim Config Indaiatuba");

	}

	public void DBGoodyear() {
		

	}

	public void DBSorocaba() {

		DatabaseStatic.pms = new ArrayList<PortaMashSerial>();

		// ***************************************************************************************************************
		// Sorocaba MashSerial
		// ***************************************************************************************************************
		DatabaseStatic.cms = new ArrayList<ComandoMashSerial>();

		MeshSerial ms2 = new MeshSerial(0, 0, Config.getInstance().getProperty(Config.PROP_SOROCABA_MS2_MAC16),
				Config.getInstance().getProperty(Config.PROP_SOROCABA_MS2_MAC64), "MS2", 92);
		MeshSerial ms4 = new MeshSerial(0, 0, Config.getInstance().getProperty(Config.PROP_SOROCABA_MS4_MAC16),
				Config.getInstance().getProperty(Config.PROP_SOROCABA_MS4_MAC64), "MS4", 94);
		MeshSerial ms5 = new MeshSerial(0, 0, Config.getInstance().getProperty(Config.PROP_SOROCABA_MS5_MAC16),
				Config.getInstance().getProperty(Config.PROP_SOROCABA_MS5_MAC64), "MS5", 95);
		DatabaseStatic.mashs = new ArrayList<MeshSerial>();

		DatabaseStatic.mashs.add(ms2);
		DatabaseStatic.mashs.add(ms4);
		DatabaseStatic.mashs.add(ms5);

		tagCruzamentoMash = new ArrayList<TagCruzamentoMash>();

		Tag bloqMSDoorOff1 = new Tag("E004015078892A1B23", "", 0, 0, 0);
		Tag desBloqMSDoorOff1 = new Tag("E00401507887BBFF23", "", 0, 0, 0);
		Tag desBloqMSDoorOff2 = new Tag("E004015078892D8C23", "", 0, 0, 0);
		Tag desBloqMSDoorOff3 = new Tag("E00401507887B71823", "", 0, 0, 0);
		Input acMSDoorOff = new Input(ms2, "acMSDoorOff", 0, 0, 0, 1);
		Input dacMSDoorOff = new Input(ms2, "dacMSDoorOff", 0, 0, 0, 0);
		List<Tag> bloqMSDoorOff = new ArrayList<Tag>();
		List<Tag> desBloqMSDoorOff = new ArrayList<Tag>();
		bloqMSDoorOff.add(bloqMSDoorOff1);
		desBloqMSDoorOff.add(desBloqMSDoorOff1);
		desBloqMSDoorOff.add(desBloqMSDoorOff2);
		desBloqMSDoorOff.add(desBloqMSDoorOff3);
		TagCruzamentoMash tcmDoorOff = new TagCruzamentoMash("DoorOffAGVSaida", acMSDoorOff, dacMSDoorOff,
				bloqMSDoorOff, desBloqMSDoorOff);
		tagCruzamentoMash.add(tcmDoorOff);

		Tag bloqMSCentroOff1 = new Tag("E004015078892A1B23", "", 0, 0, 0);
		Tag bloqMSCentroOff2 = new Tag("E004015078892F7D23", "", 0, 0, 0);
		Tag bloqMSCentroOff3 = new Tag("E00401507887BE3B23", "", 0, 0, 0);
		Tag bloqMSCentroOff4 = new Tag("E004015078892B8923", "", 0, 0, 0);

		Tag desBloqMSCentroOff1 = new Tag("E00401507887B71823", "", 0, 0, 0);
		Tag desBloqMSCentroOff2 = new Tag("E0040150788927FD23", "", 0, 0, 0);
		Tag desBloqMSCentroOff3 = new Tag("E00401507887BC0123", "", 0, 0, 0);
		Tag desBloqMSCentroOff4 = new Tag("E00401507887C27123", "", 0, 0, 0);

		Input acMSCentroOff = new Input(ms5, "acMSCentroOff", 0, 0, 0, 1);
		Input dacMSCentroOff = new Input(ms5, "dacMSCentroOff", 0, 0, 0, 0);
		List<Tag> bloqMSCentroOff = new ArrayList<Tag>();
		List<Tag> desBloqMSCentroOff = new ArrayList<Tag>();

		bloqMSCentroOff.add(bloqMSCentroOff1);
		bloqMSCentroOff.add(bloqMSCentroOff2);
		bloqMSCentroOff.add(bloqMSCentroOff3);
		bloqMSCentroOff.add(bloqMSCentroOff4);

		desBloqMSCentroOff.add(desBloqMSCentroOff1);
		desBloqMSCentroOff.add(desBloqMSCentroOff2);
		desBloqMSCentroOff.add(desBloqMSCentroOff3);
		desBloqMSCentroOff.add(desBloqMSCentroOff4);
		TagCruzamentoMash tcmCentroOff = new TagCruzamentoMash("CentroOffAGVSaida", acMSCentroOff, dacMSCentroOff,
				bloqMSCentroOff, desBloqMSCentroOff);
		tagCruzamentoMash.add(tcmCentroOff);

		// Antes de Door Off

		List<Tag> tgsSegurancaCentro = new ArrayList<Tag>();

		Tag tgParadaCentro = new Tag("E0040150788930CA23", "", 99, 0, 0);
		Tag tgParadaCentro2 = new Tag("E004015078892B8923", "", 100, 0, 0);

		tgsSegurancaCentro.add(tgParadaCentro);
		tgsSegurancaCentro.add(tgParadaCentro2);

		List<PortaMashSerial> portaMashSerialSegurancaLiberaCentro = new ArrayList<PortaMashSerial>();
		List<PortaMashSerial> portaMashSerialSegurancaPararCentro = new ArrayList<PortaMashSerial>();

		pmsBuffer = new ArrayList<PortaMashSerial>();
		pmsDoorOff = new ArrayList<PortaMashSerial>();

		PortaMashSerial pms14 = new PortaMashSerial("Libera Cruzamento Trim 01", PortaMashSerial.portE3,
				PortaMashSerial.E3_ON, null, ms4);

		PortaMashSerial pms16 = new PortaMashSerial("Libera Cruzamento DoorOff", PortaMashSerial.portE4,
				PortaMashSerial.E4_ON, null, ms2);

		PortaMashSerial pms17 = new PortaMashSerial("Libera Cruzamento Centro agv", PortaMashSerial.portE1,
				PortaMashSerial.E1_OFF, null, ms5);
		PortaMashSerial pms18 = new PortaMashSerial("Parada Cruzamento Centro agv", PortaMashSerial.portE1,
				PortaMashSerial.E1_ON, null, ms5);

		DatabaseStatic.pms.add(pms14);
		DatabaseStatic.pms.add(pms16);
		DatabaseStatic.pms.add(pms17);
		DatabaseStatic.pms.add(pms18);

		pmsBuffer.add(pms14); // Add buffer
		pmsDoorOff.add(pms16); // Add dooroff

		portaMashSerialSegurancaLiberaCentro.add(pms17);
		portaMashSerialSegurancaPararCentro.add(pms18);

		ComandoMashSerial cmsSegurancaLiberarCentro = new ComandoMashSerial("Seguranca Liberar Centro AGV",
				ComandoMashSerial.liberar, tgsSegurancaCentro, portaMashSerialSegurancaLiberaCentro, null, null, null);

		ComandoMashSerial cmsSegurancaPararCentro = new ComandoMashSerial("Seguranca parada Centro AGV",
				ComandoMashSerial.parar, tgsSegurancaCentro, portaMashSerialSegurancaPararCentro, null, null, null);

		DatabaseStatic.cms.add(cmsSegurancaLiberarCentro);
		DatabaseStatic.cms.add(cmsSegurancaPararCentro);
	}

	public static void logicExtraIndaiatuba(String value) {

	}

	public static void logicExtraSorocaba(String value) {

	}

	public DatabaseStatic() {

		Config config = Config.getInstance();

		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_TOYOTA_SOROCABA)) {
			DBSorocaba();
		}

		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_TOYOTA_INDAIATUBA)) {
			DBIndaituba();
		}

		if (config.getProperty(Config.PROP_PROJ).equals(ConfigProcess.PROJ_GOODYEAR)) {
			DBGoodyear();
		}

		for (int i = 0; tagCruzamentoMash != null && i < tagCruzamentoMash.size(); i++) {
			tagCruzamentoMash.get(i).getDac().sendComando();
		}

		resetCruzamentos();
		resetTagAtraso();
		resetSemaforos();
		resetZoneTime();
		
		logZoneTimes = new ArrayList<LogZoneTime>();
		bufferEntradaLinhaE = null;
		bufferEntradaLinhaD = null;
		bufferEmRotaCarregadoLinhaE = new ArrayList<AGV>();
		bufferEmRotaCarregadoLinhaD = new ArrayList<AGV>();
		
		resetAGVS();
		resetMeshs();
		resetMedia();
		new ThreadControlLogic();

	  
		
	}

}
