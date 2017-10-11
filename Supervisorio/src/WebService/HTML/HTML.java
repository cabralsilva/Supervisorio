package WebService.HTML;

public class HTML {
	private String html;
	private String name;
	
	public String getHtml() {
		return this.html;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setHtml(String html) {
		this.html = html;
	}

	public HTML(String html, String name) {
		super();
		this.html = html;
		this.name = name;
	}
	
}
