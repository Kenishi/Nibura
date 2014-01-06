package nibura.logic;

import java.io.InputStream;

public enum ResourceHandler {
	BOARD_LIST_HEADER_HTML ("/nibura/res/BoardListHeaderHTML.html"),
	BOARD_LIST_FOOTER_HTML ("/nibura/res/BoardListFooterHTML.html");
	
	private String relPathToRes = null;
	
	private ResourceHandler(String path) {
		relPathToRes = path;
	}
	
	public InputStream getResourceStream() {
		InputStream returnStream = ResourceHandler.class.getClass().getResourceAsStream(relPathToRes); 
		return returnStream;
	}
}
