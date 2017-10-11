package WebService.HTML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import AGVS.Util.Log;
import WebService.http.Config;

public class ConvertPAGinHTML {

	private List<HTML> pages;

	public String getHtml(String nameFile) {
		if (nameFile != null && pages != null && pages.size() > 0) {
			for (HTML html : pages) {
				if (html != null && html.getName() != null && html.getName().equals(nameFile)
						&& html.getHtml() != null) {
					return html.getHtml();
				}
			}
		}
		return "";
	}

	public void generateHTML(String[] files, Tags tags) {
		Config config = Config.getInstance();
		String diretorio = config.getProperty(Config.PROP_REP_CONVERT_PAG_HTTP);
		if (diretorio != null && files != null && files.length > 0) {
			pages = new ArrayList<HTML>();
			for (String string : files) {
				File file = new File(diretorio+ "/" + string);
				if(file.exists()) {
					try {
						String html = new String(Files.readAllBytes(file.toPath()));
//						System.out.println("Conteúdo: \n" + html);
						html = tags.execute(html);
//						System.out.println("Update Tags: \n" + html);
						pages.add(new HTML(html, string));
					} catch (IOException e) {
						new Log(e);
					}
				}

						
			}
		}
	}
}
