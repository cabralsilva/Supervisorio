package WebService.extensao;

import WebService.http.*;

public interface CommandDB {
	void execute(Request req, Response resp, Dispatcher disp) throws Exception;
}