package WebService.HTML;

import java.util.HashMap;
import java.util.Map;

public class Tags {

	private final String idex1 = "#{";
	private final String idex2 = "}#";

	private Map<String, ProcessingMethod> methodMap;

	public Tags() {
		methodMap = new HashMap<String, ProcessingMethod>();
	}

	public void add(String key, ProcessingMethod method) {
		methodMap.put(key, method);
	}

	public String execute(String html) {
		
		while (true && html != null && html.length() > 0) {
	
			int pos1 = html.indexOf(idex1);
			int pos2 = html.indexOf(idex2) + idex2.length();
			if (pos2 < 0 || pos1 < 0)
				break;
			//System.out.println(html);
			String tagValue = html.substring(pos1, pos2);
			if (methodMap.containsKey(tagValue)) {
				html = html.replace(tagValue, methodMap.get(tagValue).method());
			} else {
				html = html.replace(tagValue, "");
			}
		}
		return html;
	}

	@Override
	public String toString() {
		return "Tags [idex1=" + idex1 + ", idex2=" + idex2 + ", methodMap=" + methodMap + "]";
	}
	
	
}
