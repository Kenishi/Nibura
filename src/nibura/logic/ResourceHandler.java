package nibura.logic;

import java.io.InputStream;

import nibura.logic.RUNTIME_STATUS.STATUS;

public enum ResourceHandler {
	BOARD_LIST_HEADER_HTML ("BoardListHeaderHTML.html"),
	BOARD_LIST_FOOTER_HTML ("BoardListFooterHTML.html");
	
	private String relPathToRes = null;
	
	private ResourceHandler(String filename) {
		RUNTIME_STATUS.STATUS currentStatus = RUNTIME_STATUS.getStatus();
		
		if(currentStatus == STATUS.ANDROID) {
			relPathToRes = "/asset/" + filename;
		}
		else {
			relPathToRes = "/nibura/res/" + filename;
		}
	}
	
	public InputStream getResourceStream() {
		InputStream returnStream = ResourceHandler.class.getClass().getResourceAsStream(relPathToRes); 
		return returnStream;
	}
}
