package WebService.extensao;

import WebService.http.*;

public interface Command {
	void execute(Request req, Response resp) throws Exception;
}