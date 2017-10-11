package WebService.http;

import java.util.Properties;

import AGVS.Util.Log;

import java.io.FileInputStream;

public class Config extends Properties {

	public static final String CONFIG_FILE = "config.properties";
	public static final String PROP_HTTP_PORTA = "http.porta";
	public static final String PROP_HTTP_DEFAULT_RESOURCE = "http.default.resource";
	public static final String PROP_HTTP_DEFAULT_REPOS = "http.default.repos";
	public static final int DEFAULT_HTTP_PORTA = 80;
	public static final String PROP_REP_CONVERT_PAG_HTTP = "pages";
	public static final String PROP_LENGHT_BUFFER_SCADA = "bufferScada";
	public static final String PROP_LOGIN = "login";
	public static final String PROP_PASSWORD = "password";
	public static final String PROP_SERIAL_PORT = "serial.port";
	public static final String PROP_SERIAL_BAUDRATE = "serial.baudrate";
	public static final String PROP_SOROCABA_MS1_MAC64 = "sorocaba_ms1_mac64";
	public static final String PROP_SOROCABA_MS1_MAC16 = "sorocaba_ms1_mac16";
	public static final String PROP_SOROCABA_MS2_MAC64 = "sorocaba_ms2_mac64";
	public static final String PROP_SOROCABA_MS2_MAC16 = "sorocaba_ms2_mac16";
	public static final String PROP_SOROCABA_MS3_MAC64 = "sorocaba_ms3_mac64";
	public static final String PROP_SOROCABA_MS3_MAC16 = "sorocaba_ms3_mac16";
	public static final String PROP_SOROCABA_MS4_MAC64 = "sorocaba_ms4_mac64";
	public static final String PROP_SOROCABA_MS4_MAC16 = "sorocaba_ms4_mac16";
	public static final String PROP_SOROCABA_MS5_MAC64 = "sorocaba_ms5_mac64";
	public static final String PROP_SOROCABA_MS5_MAC16 = "sorocaba_ms5_mac16";

	public static final String PROP_SEMAFARO_1_VERDE_1 = "Semafaro1Verde1";
	public static final String PROP_SEMAFARO_1_VERDE_2 = "Semafaro1Verde2";
	public static final String PROP_SEMAFARO_1_VERDE_3 = "Semafaro1Verde3";
	public static final String PROP_SEMAFARO_1_VERDE_4 = "Semafaro1Verde4";
	public static final String PROP_SEMAFARO_1_VERMELHO_1 = "Semafaro1Vermelho1";
	public static final String PROP_SEMAFARO_1_VERMELHO_2 = "Semafaro1Vermelho2";
	public static final String PROP_SEMAFARO_1_VERMELHO_3 = "Semafaro1Vermelho3";
	public static final String PROP_SEMAFARO_1_VERMELHO_4 = "Semafaro1Vermelho4";

	public static final String PROP_SEMAFARO_2_VERDE_1 = "Semafaro2Verde1";
	public static final String PROP_SEMAFARO_2_VERDE_2 = "Semafaro2Verde2";
	public static final String PROP_SEMAFARO_2_VERDE_3 = "Semafaro2Verde3";
	public static final String PROP_SEMAFARO_2_VERDE_4 = "Semafaro2Verde4";
	public static final String PROP_SEMAFARO_2_VERMELHO_1 = "Semafaro2Vermelho1";
	public static final String PROP_SEMAFARO_2_VERMELHO_2 = "Semafaro2Vermelho2";
	public static final String PROP_SEMAFARO_2_VERMELHO_3 = "Semafaro2Vermelho3";
	public static final String PROP_SEMAFARO_2_VERMELHO_4 = "Semafaro2Vermelho4";

	public static final String PROP_SEMAFARO_3_VERDE_1 = "Semafaro3Verde1";
	public static final String PROP_SEMAFARO_3_VERDE_2 = "Semafaro3Verde2";
	public static final String PROP_SEMAFARO_3_VERDE_3 = "Semafaro3Verde3";
	public static final String PROP_SEMAFARO_3_VERDE_4 = "Semafaro3Verde4";
	public static final String PROP_SEMAFARO_3_VERMELHO_1 = "Semafaro3Vermelho1";
	public static final String PROP_SEMAFARO_3_VERMELHO_2 = "Semafaro3Vermelho2";
	public static final String PROP_SEMAFARO_3_VERMELHO_3 = "Semafaro3Vermelho3";
	public static final String PROP_SEMAFARO_3_VERMELHO_4 = "Semafaro3Vermelho4";

	public static final String PROP_SEMAFARO_4_VERDE_1 = "Semafaro4Verde1";
	public static final String PROP_SEMAFARO_4_VERDE_2 = "Semafaro4Verde2";
	public static final String PROP_SEMAFARO_4_VERDE_3 = "Semafaro4Verde3";
	public static final String PROP_SEMAFARO_4_VERDE_4 = "Semafaro4Verde4";
	public static final String PROP_SEMAFARO_4_VERMELHO_1 = "Semafaro4Vermelho1";
	public static final String PROP_SEMAFARO_4_VERMELHO_2 = "Semafaro4Vermelho2";
	public static final String PROP_SEMAFARO_4_VERMELHO_3 = "Semafaro4Vermelho3";
	public static final String PROP_SEMAFARO_4_VERMELHO_4 = "Semafaro4Vermelho4";

	public static final String PROP_SEMAFARO_5_VERDE_1 = "Semafaro5Verde1";
	public static final String PROP_SEMAFARO_5_VERDE_2 = "Semafaro5Verde2";
	public static final String PROP_SEMAFARO_5_VERDE_3 = "Semafaro5Verde3";
	public static final String PROP_SEMAFARO_5_VERDE_4 = "Semafaro5Verde4";
	public static final String PROP_SEMAFARO_5_VERMELHO_1 = "Semafaro5Vermelho1";
	public static final String PROP_SEMAFARO_5_VERMELHO_2 = "Semafaro5Vermelho2";
	public static final String PROP_SEMAFARO_5_VERMELHO_3 = "Semafaro5Vermelho3";
	public static final String PROP_SEMAFARO_5_VERMELHO_4 = "Semafaro5Vermelho4";

	public static final String PROP_SEMAFARO_6_VERDE_1 = "Semafaro6Verde1";
	public static final String PROP_SEMAFARO_6_VERDE_2 = "Semafaro6Verde2";
	public static final String PROP_SEMAFARO_6_VERDE_3 = "Semafaro6Verde3";
	public static final String PROP_SEMAFARO_6_VERDE_4 = "Semafaro6Verde4";
	public static final String PROP_SEMAFARO_6_VERMELHO_1 = "Semafaro6Vermelho1";
	public static final String PROP_SEMAFARO_6_VERMELHO_2 = "Semafaro6Vermelho2";
	public static final String PROP_SEMAFARO_6_VERMELHO_3 = "Semafaro6Vermelho3";
	public static final String PROP_SEMAFARO_6_VERMELHO_4 = "Semafaro6Vermelho4";
	
	public static final String PROP_SEMAFARO_7_VERDE_1 = "Semafaro7Verde1";
	public static final String PROP_SEMAFARO_7_VERDE_2 = "Semafaro7Verde2";
	public static final String PROP_SEMAFARO_7_VERDE_3 = "Semafaro7Verde3";
	public static final String PROP_SEMAFARO_7_VERDE_4 = "Semafaro7Verde4";
	public static final String PROP_SEMAFARO_7_VERMELHO_1 = "Semafaro7Vermelho1";
	public static final String PROP_SEMAFARO_7_VERMELHO_2 = "Semafaro7Vermelho2";
	public static final String PROP_SEMAFARO_7_VERMELHO_3 = "Semafaro7Vermelho3";
	public static final String PROP_SEMAFARO_7_VERMELHO_4 = "Semafaro7Vermelho4";

	public static final String PROP_PARADA_1_TKL_VAZIO = "Parada1TKLVazio";
	public static final String PROP_PARADA_1_TKL_VAZIO2 = "Parada1TKLVazio2";
	public static final String PROP_PARADA_1_TKL_VAZIO3 = "Parada1TKLVazio3";
	public static final String PROP_PARADA_2_TKL_VAZIO = "Parada2TKLVazio";
	public static final String PROP_PARADA_2_TKL_VAZIO2 = "Parada2TKLVazio2";
	public static final String PROP_PARADA_1_TKL_CHEIO = "Parada1TKLCheio";
	public static final String PROP_PARADA_1_TKL_CHEIO2 = "Parada1TKLCheio2";
	
	public static final String PROP_ENTRADA_1_CANCELA = "Entrada1Cancela";
	public static final String PROP_ENTRADA_2_CANCELA = "Entrada2Cancela";
	public static final String PROP_ENTRADA_3_CANCELA = "Entrada3Cancela";
	public static final String PROP_ENTRADA_4_CANCELA = "Entrada4Cancela";
	public static final String PROP_ENTRADA_5_CANCELA = "Entrada5Cancela";
	public static final String PROP_ENTRADA_6_CANCELA = "Entrada6Cancela";
	public static final String PROP_ENTRADA_7_CANCELA = "Entrada7Cancela";
	public static final String PROP_ENTRADA_8_CANCELA = "Entrada8Cancela";
	public static final String PROP_ENTRADA_9_CANCELA = "Entrada9Cancela";
	public static final String PROP_ENTRADA_10_CANCELA = "Entrada10Cancela";

	public static final String PROP_SAIDA_1_CANCELA = "Saida1Cancela";
	public static final String PROP_SAIDA_2_CANCELA = "Saida2Cancela";
	public static final String PROP_SAIDA_3_CANCELA = "Saida3Cancela";
	public static final String PROP_SAIDA_4_CANCELA = "Saida4Cancela";
	public static final String PROP_SAIDA_5_CANCELA = "Saida5Cancela";
	public static final String PROP_SAIDA_6_CANCELA = "Saida6Cancela";
	public static final String PROP_SAIDA_7_CANCELA = "Saida7Cancela";
	public static final String PROP_SAIDA_8_CANCELA = "Saida8Cancela";
	public static final String PROP_SAIDA_9_CANCELA = "Saida9Cancela";
	public static final String PROP_SAIDA_10_CANCELA = "Saida10Cancela";
	public static final String PROP_SAIDA_11_CANCELA = "Saida11Cancela";

	public static final String PROP_ENTRADA_1_CAMINHAO = "Entrada1Caminhao";
	public static final String PROP_ENTRADA_2_CAMINHAO = "Entrada2Caminhao";
	public static final String PROP_ENTRADA_3_CAMINHAO = "Entrada3Caminhao";
	public static final String PROP_ENTRADA_4_CAMINHAO = "Entrada4Caminhao";
	public static final String PROP_SAIDA_1_CAMINHAO = "Saida1Caminhao";
	public static final String PROP_SAIDA_2_CAMINHAO = "Saida2Caminhao";
	public static final String PROP_SAIDA_3_CAMINHAO = "Saida3Caminhao";
	public static final String PROP_SAIDA_4_CAMINHAO = "Saida4Caminhao";

	public static final String PROP_P1 = "p1";
	public static final String PROP_P2 = "p2";
	public static final String PROP_P3 = "p3";
	public static final String PROP_P4 = "p4";
	public static final String PROP_P5 = "p5";
	public static final String PROP_P6 = "p6";

	public static final String PROP_GY_MS201 = "gy_ms201";
	public static final String PROP_GY_MS202 = "gy_ms202";
	public static final String PROP_GY_MS203 = "gy_ms203";
	public static final String PROP_GY_MS204 = "gy_ms204";
	public static final String PROP_GY_MS205 = "gy_ms205";
	public static final String PROP_GY_MS206 = "gy_ms206";
	public static final String PROP_GY_MS207 = "gy_ms207";
	public static final String PROP_GY_MS208 = "gy_ms208";
	public static final String PROP_GY_MS209 = "gy_ms209";
	public static final String PROP_GY_MS210 = "gy_ms210";
	public static final String PROP_GY_MS211 = "gy_ms211";
	public static final String PROP_GY_MS212 = "gy_ms212";
	public static final String PROP_GY_MS213 = "gy_ms213";
	public static final String PROP_GY_MS214 = "gy_ms214";
	public static final String PROP_GY_MS215 = "gy_ms215";
	public static final String PROP_GY_MS216 = "gy_ms216";
	public static final String PROP_GY_MS217 = "gy_ms217";
	public static final String PROP_GY_MS218 = "gy_ms218";
	public static final String PROP_GY_MS219 = "gy_ms219";
	public static final String PROP_GY_MS220 = "gy_ms220";

	public static final String PROP_SEM_H1V1_VD1 = "SEM.H1V1.VD1";
	public static final String PROP_SEM_H1V1_VD2 = "SEM.H1V1.VD2";
	public static final String PROP_SEM_H1V1_VD3 = "SEM.H1V1.VD3";
	public static final String PROP_SEM_H1V1_VD4 = "SEM.H1V1.VD4";
	public static final String PROP_SEM_H1V1_VD5 = "SEM.H1V1.VD5";
	public static final String PROP_SEM_H1V1_VM1 = "SEM.H1V1.VM1";
	public static final String PROP_SEM_H1V1_VM2 = "SEM.H1V1.VM2";
	public static final String PROP_SEM_H1V1_VM3 = "SEM.H1V1.VM3";
	public static final String PROP_SEM_H1V1_VM4 = "SEM.H1V1.VM4";
	public static final String PROP_SEM_H1V1_VM5 = "SEM.H1V1.VM5";

	public static final String PROP_SEM_H2V1_VD1 = "SEM.H2V1.VD1";
	public static final String PROP_SEM_H2V1_VD2 = "SEM.H2V1.VD2";
	public static final String PROP_SEM_H2V1_VD3 = "SEM.H2V1.VD3";
	public static final String PROP_SEM_H2V1_VD4 = "SEM.H2V1.VD4";
	public static final String PROP_SEM_H2V1_VD5 = "SEM.H2V1.VD5";
	public static final String PROP_SEM_H2V1_VM1 = "SEM.H2V1.VM1";
	public static final String PROP_SEM_H2V1_VM2 = "SEM.H2V1.VM2";
	public static final String PROP_SEM_H2V1_VM3 = "SEM.H2V1.VM3";
	public static final String PROP_SEM_H2V1_VM4 = "SEM.H2V1.VM4";
	public static final String PROP_SEM_H2V1_VM5 = "SEM.H2V1.VM5";

	public static final String PROP_SEM_H3V1_VD1 = "SEM.H3V1.VD1";
	public static final String PROP_SEM_H3V1_VD2 = "SEM.H3V1.VD2";
	public static final String PROP_SEM_H3V1_VD3 = "SEM.H3V1.VD3";
	public static final String PROP_SEM_H3V1_VD4 = "SEM.H3V1.VD4";
	public static final String PROP_SEM_H3V1_VD5 = "SEM.H3V1.VD5";
	public static final String PROP_SEM_H3V1_VM1 = "SEM.H3V1.VM1";
	public static final String PROP_SEM_H3V1_VM2 = "SEM.H3V1.VM2";
	public static final String PROP_SEM_H3V1_VM3 = "SEM.H3V1.VM3";
	public static final String PROP_SEM_H3V1_VM4 = "SEM.H3V1.VM4";
	public static final String PROP_SEM_H3V1_VM5 = "SEM.H3V1.VM5";

	public static final String PROP_SEM_H4V1_VD1 = "SEM.H4V1.VD1";
	public static final String PROP_SEM_H4V1_VD2 = "SEM.H4V1.VD2";
	public static final String PROP_SEM_H4V1_VD3 = "SEM.H4V1.VD3";
	public static final String PROP_SEM_H4V1_VD4 = "SEM.H4V1.VD4";
	public static final String PROP_SEM_H4V1_VD5 = "SEM.H4V1.VD5";
	public static final String PROP_SEM_H4V1_VM1 = "SEM.H4V1.VM1";
	public static final String PROP_SEM_H4V1_VM2 = "SEM.H4V1.VM2";
	public static final String PROP_SEM_H4V1_VM3 = "SEM.H4V1.VM3";
	public static final String PROP_SEM_H4V1_VM4 = "SEM.H4V1.VM4";
	public static final String PROP_SEM_H4V1_VM5 = "SEM.H4V1.VM5";

	public static final String PROP_SEM_H1V2_VD1 = "SEM.H1V2.VD1";
	public static final String PROP_SEM_H1V2_VD2 = "SEM.H1V2.VD2";
	public static final String PROP_SEM_H1V2_VD3 = "SEM.H1V2.VD3";
	public static final String PROP_SEM_H1V2_VD4 = "SEM.H1V2.VD4";
	public static final String PROP_SEM_H1V2_VD5 = "SEM.H1V2.VD5";
	public static final String PROP_SEM_H1V2_VM1 = "SEM.H1V2.VM1";
	public static final String PROP_SEM_H1V2_VM2 = "SEM.H1V2.VM2";
	public static final String PROP_SEM_H1V2_VM3 = "SEM.H1V2.VM3";
	public static final String PROP_SEM_H1V2_VM4 = "SEM.H1V2.VM4";
	public static final String PROP_SEM_H1V2_VM5 = "SEM.H1V2.VM5";

	public static final String PROP_SEM_H2V2_VD1 = "SEM.H2V2.VD1";
	public static final String PROP_SEM_H2V2_VD2 = "SEM.H2V2.VD2";
	public static final String PROP_SEM_H2V2_VD3 = "SEM.H2V2.VD3";
	public static final String PROP_SEM_H2V2_VD4 = "SEM.H2V2.VD4";
	public static final String PROP_SEM_H2V2_VD5 = "SEM.H2V2.VD5";
	public static final String PROP_SEM_H2V2_VM1 = "SEM.H2V2.VM1";
	public static final String PROP_SEM_H2V2_VM2 = "SEM.H2V2.VM2";
	public static final String PROP_SEM_H2V2_VM3 = "SEM.H2V2.VM3";
	public static final String PROP_SEM_H2V2_VM4 = "SEM.H2V2.VM4";
	public static final String PROP_SEM_H2V2_VM5 = "SEM.H2V2.VM5";

	public static final String PROP_SEM_H3V2_VD1 = "SEM.H3V2.VD1";
	public static final String PROP_SEM_H3V2_VD2 = "SEM.H3V2.VD2";
	public static final String PROP_SEM_H3V2_VD3 = "SEM.H3V2.VD3";
	public static final String PROP_SEM_H3V2_VD4 = "SEM.H3V2.VD4";
	public static final String PROP_SEM_H3V2_VD5 = "SEM.H3V2.VD5";
	public static final String PROP_SEM_H3V2_VM1 = "SEM.H3V2.VM1";
	public static final String PROP_SEM_H3V2_VM2 = "SEM.H3V2.VM2";
	public static final String PROP_SEM_H3V2_VM3 = "SEM.H3V2.VM3";
	public static final String PROP_SEM_H3V2_VM4 = "SEM.H3V2.VM4";
	public static final String PROP_SEM_H3V2_VM5 = "SEM.H3V2.VM5";

	public static final String PROP_SEM_H4V2_VD1 = "SEM.H4V2.VD1";
	public static final String PROP_SEM_H4V2_VD2 = "SEM.H4V2.VD2";
	public static final String PROP_SEM_H4V2_VD3 = "SEM.H4V2.VD3";
	public static final String PROP_SEM_H4V2_VD4 = "SEM.H4V2.VD4";
	public static final String PROP_SEM_H4V2_VD5 = "SEM.H4V2.VD5";
	public static final String PROP_SEM_H4V2_VM1 = "SEM.H4V2.VM1";
	public static final String PROP_SEM_H4V2_VM2 = "SEM.H4V2.VM2";
	public static final String PROP_SEM_H4V2_VM3 = "SEM.H4V2.VM3";
	public static final String PROP_SEM_H4V2_VM4 = "SEM.H4V2.VM4";
	public static final String PROP_SEM_H4V2_VM5 = "SEM.H4V2.VM5";

	public static final String PROP_SEM_H1V3_VD1 = "SEM.H1V3.VD1";
	public static final String PROP_SEM_H1V3_VD2 = "SEM.H1V3.VD2";
	public static final String PROP_SEM_H1V3_VD3 = "SEM.H1V3.VD3";
	public static final String PROP_SEM_H1V3_VD4 = "SEM.H1V3.VD4";
	public static final String PROP_SEM_H1V3_VD5 = "SEM.H1V3.VD5";
	public static final String PROP_SEM_H1V3_VM1 = "SEM.H1V3.VM1";
	public static final String PROP_SEM_H1V3_VM2 = "SEM.H1V3.VM2";
	public static final String PROP_SEM_H1V3_VM3 = "SEM.H1V3.VM3";
	public static final String PROP_SEM_H1V3_VM4 = "SEM.H1V3.VM4";
	public static final String PROP_SEM_H1V3_VM5 = "SEM.H1V3.VM5";

	public static final String PROP_SEM_H2V3_VD1 = "SEM.H2V3.VD1";
	public static final String PROP_SEM_H2V3_VD2 = "SEM.H2V3.VD2";
	public static final String PROP_SEM_H2V3_VD3 = "SEM.H2V3.VD3";
	public static final String PROP_SEM_H2V3_VD4 = "SEM.H2V3.VD4";
	public static final String PROP_SEM_H2V3_VD5 = "SEM.H2V3.VD5";
	public static final String PROP_SEM_H2V3_VM1 = "SEM.H2V3.VM1";
	public static final String PROP_SEM_H2V3_VM2 = "SEM.H2V3.VM2";
	public static final String PROP_SEM_H2V3_VM3 = "SEM.H2V3.VM3";
	public static final String PROP_SEM_H2V3_VM4 = "SEM.H2V3.VM4";
	public static final String PROP_SEM_H2V3_VM5 = "SEM.H2V3.VM5";

	public static final String PROP_SEM_H3V3_VD1 = "SEM.H3V3.VD1";
	public static final String PROP_SEM_H3V3_VD2 = "SEM.H3V3.VD2";
	public static final String PROP_SEM_H3V3_VD3 = "SEM.H3V3.VD3";
	public static final String PROP_SEM_H3V3_VD4 = "SEM.H3V3.VD4";
	public static final String PROP_SEM_H3V3_VD5 = "SEM.H3V3.VD5";
	public static final String PROP_SEM_H3V3_VM1 = "SEM.H3V3.VM1";
	public static final String PROP_SEM_H3V3_VM2 = "SEM.H3V3.VM2";
	public static final String PROP_SEM_H3V3_VM3 = "SEM.H3V3.VM3";
	public static final String PROP_SEM_H3V3_VM4 = "SEM.H3V3.VM4";
	public static final String PROP_SEM_H3V3_VM5 = "SEM.H3V3.VM5";

	public static final String PROP_SEM_H4V3_VD1 = "SEM.H4V3.VD1";
	public static final String PROP_SEM_H4V3_VD2 = "SEM.H4V3.VD2";
	public static final String PROP_SEM_H4V3_VD3 = "SEM.H4V3.VD3";
	public static final String PROP_SEM_H4V3_VD4 = "SEM.H4V3.VD4";
	public static final String PROP_SEM_H4V3_VD5 = "SEM.H4V3.VD5";
	public static final String PROP_SEM_H4V3_VM1 = "SEM.H4V3.VM1";
	public static final String PROP_SEM_H4V3_VM2 = "SEM.H4V3.VM2";
	public static final String PROP_SEM_H4V3_VM3 = "SEM.H4V3.VM3";
	public static final String PROP_SEM_H4V3_VM4 = "SEM.H4V3.VM4";
	public static final String PROP_SEM_H4V3_VM5 = "SEM.H4V3.VM5";

	public static final String PROP_SEM_H1V4_VD1 = "SEM.H1V4.VD1";
	public static final String PROP_SEM_H1V4_VD2 = "SEM.H1V4.VD2";
	public static final String PROP_SEM_H1V4_VD3 = "SEM.H1V4.VD3";
	public static final String PROP_SEM_H1V4_VD4 = "SEM.H1V4.VD4";
	public static final String PROP_SEM_H1V4_VD5 = "SEM.H1V4.VD5";
	public static final String PROP_SEM_H1V4_VM1 = "SEM.H1V4.VM1";
	public static final String PROP_SEM_H1V4_VM2 = "SEM.H1V4.VM2";
	public static final String PROP_SEM_H1V4_VM3 = "SEM.H1V4.VM3";
	public static final String PROP_SEM_H1V4_VM4 = "SEM.H1V4.VM4";
	public static final String PROP_SEM_H1V4_VM5 = "SEM.H1V4.VM5";

	public static final String PROP_SEM_H2V4_VD1 = "SEM.H2V4.VD1";
	public static final String PROP_SEM_H2V4_VD2 = "SEM.H2V4.VD2";
	public static final String PROP_SEM_H2V4_VD3 = "SEM.H2V4.VD3";
	public static final String PROP_SEM_H2V4_VD4 = "SEM.H2V4.VD4";
	public static final String PROP_SEM_H2V4_VD5 = "SEM.H2V4.VD5";
	public static final String PROP_SEM_H2V4_VM1 = "SEM.H2V4.VM1";
	public static final String PROP_SEM_H2V4_VM2 = "SEM.H2V4.VM2";
	public static final String PROP_SEM_H2V4_VM3 = "SEM.H2V4.VM3";
	public static final String PROP_SEM_H2V4_VM4 = "SEM.H2V4.VM4";
	public static final String PROP_SEM_H2V4_VM5 = "SEM.H2V4.VM5";

	public static final String PROP_SEM_H3V4_VD1 = "SEM.H3V4.VD1";
	public static final String PROP_SEM_H3V4_VD2 = "SEM.H3V4.VD2";
	public static final String PROP_SEM_H3V4_VD3 = "SEM.H3V4.VD3";
	public static final String PROP_SEM_H3V4_VD4 = "SEM.H3V4.VD4";
	public static final String PROP_SEM_H3V4_VD5 = "SEM.H3V4.VD5";
	public static final String PROP_SEM_H3V4_VM1 = "SEM.H3V4.VM1";
	public static final String PROP_SEM_H3V4_VM2 = "SEM.H3V4.VM2";
	public static final String PROP_SEM_H3V4_VM3 = "SEM.H3V4.VM3";
	public static final String PROP_SEM_H3V4_VM4 = "SEM.H3V4.VM4";
	public static final String PROP_SEM_H3V4_VM5 = "SEM.H3V4.VM5";

	public static final String PROP_SEM_H4V4_VD1 = "SEM.H4V4.VD1";
	public static final String PROP_SEM_H4V4_VD2 = "SEM.H4V4.VD2";
	public static final String PROP_SEM_H4V4_VD3 = "SEM.H4V4.VD3";
	public static final String PROP_SEM_H4V4_VD4 = "SEM.H4V4.VD4";
	public static final String PROP_SEM_H4V4_VD5 = "SEM.H4V4.VD5";
	public static final String PROP_SEM_H4V4_VM1 = "SEM.H4V4.VM1";
	public static final String PROP_SEM_H4V4_VM2 = "SEM.H4V4.VM2";
	public static final String PROP_SEM_H4V4_VM3 = "SEM.H4V4.VM3";
	public static final String PROP_SEM_H4V4_VM4 = "SEM.H4V4.VM4";
	public static final String PROP_SEM_H4V4_VM5 = "SEM.H4V4.VM5";

	public static final String PROP_SEM_H1V5_VD1 = "SEM.H1V5.VD1";
	public static final String PROP_SEM_H1V5_VD2 = "SEM.H1V5.VD2";
	public static final String PROP_SEM_H1V5_VD3 = "SEM.H1V5.VD3";
	public static final String PROP_SEM_H1V5_VD4 = "SEM.H1V5.VD4";
	public static final String PROP_SEM_H1V5_VD5 = "SEM.H1V5.VD5";
	public static final String PROP_SEM_H1V5_VM1 = "SEM.H1V5.VM1";
	public static final String PROP_SEM_H1V5_VM2 = "SEM.H1V5.VM2";
	public static final String PROP_SEM_H1V5_VM3 = "SEM.H1V5.VM3";
	public static final String PROP_SEM_H1V5_VM4 = "SEM.H1V5.VM4";
	public static final String PROP_SEM_H1V5_VM5 = "SEM.H1V5.VM5";

	public static final String PROP_SEM_H2V5_VD1 = "SEM.H2V5.VD1";
	public static final String PROP_SEM_H2V5_VD2 = "SEM.H2V5.VD2";
	public static final String PROP_SEM_H2V5_VD3 = "SEM.H2V5.VD3";
	public static final String PROP_SEM_H2V5_VD4 = "SEM.H2V5.VD4";
	public static final String PROP_SEM_H2V5_VD5 = "SEM.H2V5.VD5";
	public static final String PROP_SEM_H2V5_VM1 = "SEM.H2V5.VM1";
	public static final String PROP_SEM_H2V5_VM2 = "SEM.H2V5.VM2";
	public static final String PROP_SEM_H2V5_VM3 = "SEM.H2V5.VM3";
	public static final String PROP_SEM_H2V5_VM4 = "SEM.H2V5.VM4";
	public static final String PROP_SEM_H2V5_VM5 = "SEM.H2V5.VM5";

	public static final String PROP_SEM_H3V5_VD1 = "SEM.H3V5.VD1";
	public static final String PROP_SEM_H3V5_VD2 = "SEM.H3V5.VD2";
	public static final String PROP_SEM_H3V5_VD3 = "SEM.H3V5.VD3";
	public static final String PROP_SEM_H3V5_VD4 = "SEM.H3V5.VD4";
	public static final String PROP_SEM_H3V5_VD5 = "SEM.H3V5.VD5";
	public static final String PROP_SEM_H3V5_VM1 = "SEM.H3V5.VM1";
	public static final String PROP_SEM_H3V5_VM2 = "SEM.H3V5.VM2";
	public static final String PROP_SEM_H3V5_VM3 = "SEM.H3V5.VM3";
	public static final String PROP_SEM_H3V5_VM4 = "SEM.H3V5.VM4";
	public static final String PROP_SEM_H3V5_VM5 = "SEM.H3V5.VM5";

	public static final String PROP_SEM_H4V5_VD1 = "SEM.H4V5.VD1";
	public static final String PROP_SEM_H4V5_VD2 = "SEM.H4V5.VD2";
	public static final String PROP_SEM_H4V5_VD3 = "SEM.H4V5.VD3";
	public static final String PROP_SEM_H4V5_VD4 = "SEM.H4V5.VD4";
	public static final String PROP_SEM_H4V5_VD5 = "SEM.H4V5.VD5";
	public static final String PROP_SEM_H4V5_VM1 = "SEM.H4V5.VM1";
	public static final String PROP_SEM_H4V5_VM2 = "SEM.H4V5.VM2";
	public static final String PROP_SEM_H4V5_VM3 = "SEM.H4V5.VM3";
	public static final String PROP_SEM_H4V5_VM4 = "SEM.H4V5.VM4";
	public static final String PROP_SEM_H4V5_VM5 = "SEM.H4V5.VM5";

	public static final String PROP_SEMAFARO_TYPE = "semafaro.tipo";
	public static final String PROP_MODE_MASH_TKL = "mode.mash.tkl";

	public static final String PROP_PROJ = "proj";
	public static final String PROP_BANCO_DATA_CONVERT = "banco.data.convert";
	public static final String PROP_PC_DATA_CONVERT = "pc.data.convert";
	
	private static class Holder {
		private static final Config INSTANCE = new Config();
	}

	public static Config getInstance() {
		return Holder.INSTANCE;
	}

	private Config() {
		try {
			System.out.println("Carregando arquivo de configuracao.");
			load(new FileInputStream(CONFIG_FILE));
		} catch (Exception e) {
			System.out.println("Falha ao tentar carregar arquivo de configuracao.");
			new Log(e);
		}
	}

	public String getString(String name) {
		return getProperty(name);
	}

	public int getInt(String name) {
		return Integer.parseInt(getProperty(name));
	}
}
