package nibura.logic;

import java.net.URI;
import java.net.URISyntaxException;

public enum ResourceHandler {
	BOARD_LIST_HEADER_HTML ("/res/BoardListHeaderHTML.html"),
	BOARD_LIST_FOOTER_HTML ("/res/BoardListFooterHTML.html");
	
	private String relPathToRes = null;
	
	private ResourceHandler(String path) {
		relPathToRes = path;
	}
	
	public URI getURI() throws URISyntaxException {
		URI returnURI = ResourceHandler.class.getClass().getResource(relPathToRes).toURI(); 
		return returnURI;
	}
}
