package functional;

import java.net.URI;
import java.net.URISyntaxException;

public enum TestResources {
	/* Used in Functional Tests to test actual site parsing */
	TWOCH_LIST_HTML_FILE ("/res/2CH_TEST_MENU.html"),
		
	/* Used Primarily in Unit Tests */
	SIMPLE_BOARDLIST_HTML_FILE ("/res/SimpleListTestHtml.html"), 
	
	NICH_LIVE_EXPECTED_HTML("/res/Nich_Live_Expected_List.html");
		
	private String relPathToRes = null;
	
	private TestResources(String path) {
		relPathToRes = path;
	}
	
	public URI getURI() throws URISyntaxException {
		return TestResources.class.getClass().getResource(relPathToRes).toURI();
	}
}
