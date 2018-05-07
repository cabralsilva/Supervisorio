package WebService.http;

import java.io.*;

import AGVS.Data.ConfigProcess;
import AGVS.Util.Log;
import WebService.extensao.*;

public class Dispatcher {
	public Dispatcher() {
	}

	public String getClassName(Request req) {
		String res = req.getResource();
		if ("".equals(res)) {
			return null;
		}

		String className = null;
		String str = "";
		Config config = Config.getInstance();

		// reconhecimento do 1o. padrão
		int pos = res.indexOf("/");
		if (pos != -1) {
			str = res.substring(pos + 1);
		}
		className = config.getString("cmd_" + str);
		// System.out.println("getClassName: cmd_" + str);
		// reconheciomento do 2o. padr�o
		if (className == null) {
			pos = str.lastIndexOf(".");
			if (pos != -1) {
				str = str.substring(pos + 1);
			}
			className = config.getString("cmd_." + str);
		}

		return className;
	}

	public String getClassNameDataBase(Request req) {
		String res = req.getResource();
		if ("".equals(res)) {
			return null;
		}

		String className = null;
		String str = "";
		Config config = Config.getInstance();

		// reconhecimento do 1o. padr�o
		int pos = res.indexOf("/");
		if (pos != -1) {
			str = res.substring(pos + 1);
		}
		className = config.getString("db_" + str);
		//System.out.println("getClassNameDataBase: db_" + str);
		// reconheciomento do 2o. padr�o
		if (className == null) {
			pos = str.lastIndexOf(".");
			if (pos != -1) {
				str = str.substring(pos + 1);
			}
			className = config.getString("db_." + str);
		}

		return className;
	}

	public void resolve(Request req, Response resp) throws Exception {
		// verificar recurso din�mico
		String className = getClassName(req);
		String classNameDataBase = getClassNameDataBase(req);
		if (className != null) {
			try {
				Class clazz = Class.forName(className);
				Command cmd = (Command) clazz.newInstance();
				cmd.execute(req, resp);

				resp.flush();

			} catch (ClassNotFoundException e) {
				new Log(e);
				send("404", "File Not Found", req, resp);
			} catch (Exception e) {
				new Log(e);
				send("501", "Internal Error \n" + e.getMessage(), req, resp);
			}
		}
		if (classNameDataBase != null) {
			try {
				Class clazz = Class.forName(classNameDataBase);
				CommandDB cmd = (CommandDB) clazz.newInstance();
				cmd.execute(req, resp, this);

			} catch (ClassNotFoundException e) {
				new Log(e);
				send("404", "File Not Found", req, resp);
			} catch (Exception e) {
				new Log(e);
				send("501", "Internal Error \n" + e.getMessage(), req, resp);
			}
		} else {
			String repos = getRepos(req.getHost());

			//System.err.println(req.getResource());

			// String repos = getRepos(null);
			File file = new File(repos, req.getResource());
			// System.out.println("req.getResource(): " + req.getResource());
			// System.out.println("[resolve] >> repos: " + repos);
			if (file.exists()) {
				send(resp, file);
			} else {
				send("404", "File Not Found", req, resp);
				//System.err.println("[resolve] >> File Not Found - " + file.getAbsolutePath());
			}
		}
	}

	public void send(Response resp, File file) throws Exception {
		String mimeType = getMimeType(file);

		if (mimeType != null) {
			resp.setHeader("Content-Type", mimeType);
		}

		OutputStream bout = resp.getOutputStream();
		FileInputStream fin = new FileInputStream(file);

		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = fin.read(buffer)) != -1) {
			bout.write(buffer, 0, length);
		}
		bout.flush();
		fin.close();
		resp.flush();

	}

	public void send(Response resp, byte[] b, String format) throws Exception {

		if (format != null) {
			resp.setHeader("Content-Type", format);
		}

		OutputStream bout = resp.getOutputStream();

		byte[] buffer = new byte[1024];
		int length = 0;
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			length++;
			buffer[j] = b[i];
			j++;
			if (i == b.length - 1 || j == 1024) {
				bout.write(buffer, 0, length);
				length = 0;
				j = 0;
			}

		}
		bout.flush();
		resp.flush();
		buffer = null;
		bout = null;
		

	}

	private void send(String code, String desc, Request req, Response resp) throws Exception {
		String page = "<html><head><title>" + code + " " + desc + "</title></head><body><h1>" + code + " " + desc
				+ "</h1><h2>RES: '" + req.getResource() + "'</h2></body></html>";

		resp.setResponseLine(code, desc);
		resp.getPrintStream().print(page);
		resp.flush();
	}

	private String getRepos(String host) {
		String reposKey = "repos_" + host.replace(".", "_");

		Config cfg = Config.getInstance();

		String repos = cfg.getProperty(reposKey);

		if (repos == null) {
			repos = cfg.getProperty(Config.PROP_HTTP_DEFAULT_REPOS);
		}
		return repos;
	}

	private String getMimeType(File file) {
		String name = file.getName();
		String ext = "undefined";

		int pos = name.lastIndexOf(".");

		if (pos != 0) {
			ext = name.substring(pos + 1);
		}

		Config cfg = Config.getInstance();
		String mimeType = cfg.getProperty(ext);

		return mimeType;
	}
}
