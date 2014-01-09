package functional;

import java.net.URI;
import java.net.URISyntaxException;

public enum TestResources {
	/* Used in Functional Tests to test actual site parsing */
	TWOCH_LIST_HTML_FILE ("/res/2CH_TEST_MENU.html"),
		
	/* Used Primarily in Unit Tests */
	SIMPLE_BOARDLIST_HTML_FILE ("/res/SimpleListTestHtml.html"), 
	
	/* A partial file with the expected HTML after parsing */
	NICH_LIVE_EXPECTED_HTML("/res/2CH_LIVE_BOARDLIST_EXPECTED.html"),
	
	/* A partial board HTML */
	NICH_LIVE_BOARD_HTML("/res/2CH_TEST_BOARD.html"),
	NICH_LIVE_BOARD_EXPECTED("/res/2CH_TEST_BOARD_EXPECTED.csv");
	
	private String relPathToRes = null;
	
	private TestResources(String path) {
		relPathToRes = path;
	}
	
	public URI getURI() throws URISyntaxException {
		return TestResources.class.getClass().getResource(relPathToRes).toURI();
	}
}
