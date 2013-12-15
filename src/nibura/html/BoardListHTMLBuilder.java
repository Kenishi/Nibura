package nibura.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Scanner;

import nibura.logic.BoardGroup;
import nibura.logic.BoardLink;
import nibura.logic.BoardList;
import nibura.logic.BoardListElement;
import nibura.logic.OptionsHandler;
import nibura.logic.ResourceHandler;

import org.junit.Assert;

public class BoardListHTMLBuilder {
	private BoardList boardList = null;
	
	public BoardListHTMLBuilder(BoardList boardList_in) {
		Assert.assertNotNull(boardList_in);
		
		boardList = boardList_in;
	}

	public String getHTML() throws FileNotFoundException, Exception {
		String htmlOut = "";
		
		htmlOut += getHeaderHTML();
		htmlOut += getContentHTML();
		htmlOut += getFooterHTML();
		
		return htmlOut;
	}

	// Protected
	// Private
	private String getHeaderHTML() throws FileNotFoundException, URISyntaxException {
		String htmlHeader = "";
		File header_file = new File(ResourceHandler.BOARD_LIST_HEADER_HTML.getURI());
		Scanner scanner = new Scanner(header_file);
		
		htmlHeader = scanner.useDelimiter("\\Z").next(); // Read the file in
		scanner.close();
		
		return htmlHeader;
	}
	
	private String getContentHTML() throws Exception {
		String htmlContent = "";
		
		Iterator<BoardListElement> elementIterator = boardList.iterator();
		while(elementIterator.hasNext()) {
			BoardListElement element = elementIterator.next();
			
			if(element instanceof BoardGroup) {
				htmlContent += createGroupHTML(element);
			}
			else if(element instanceof BoardLink) { // Link
				htmlContent += createLinkHTML(element);
			}
			else {
				throw new Exception("Unknown BoardListElement encountered while generating Board HTML.");
			}
		}
		
		return htmlContent;
	}
	
	
	private String getFooterHTML() throws FileNotFoundException {
		String htmlFooter = "";
		File footer_file = new File("src/BoardListHTMLFooter.html");
		Scanner scanner = new Scanner(footer_file);
		
		htmlFooter = scanner.useDelimiter("\\Z").next(); // Read the file in
		scanner.close();
		
		return htmlFooter;
	}

	private String createLinkHTML(BoardListElement element) {
		String boardLinkHTML = ""
				+ "<a href=\"" + element.getLink() + "\">"
				+ element.getName()
				+ "</a>";
		return boardLinkHTML;
	}
	
	private String createGroupHTML(BoardListElement element) throws Exception {
		return createGroupHTML(element,0);
	}
	
	private String createGroupHTML(BoardListElement element, int depthCount) throws Exception {
		String boardGroupHTML = "";
		BoardGroup boardGroup = ((BoardGroup) element); // Cast BoardListElement up to BoardGroup 
		
		/* Create opening HTML for board group */
		boardGroupHTML += 	"<div id=\"group\">"
				+ "<div class=\"group-header\">"
				+ "<span id=\"open-close-icon\"></span> +"
				+ boardGroup.getName()
				+ "</div>\n"
				+ "<div id=\"group-content\">\n";
		
		/* Add internal content */
		Iterator<BoardListElement> groupElementIterator = boardGroup.iterator();
		while(groupElementIterator.hasNext()) {
			BoardListElement groupElement = groupElementIterator.next();
			
			if(groupElement instanceof BoardGroup) {
				// Check that we aren't exceeding maxium call stack depth
				if(depthCount > OptionsHandler.getMaxGroupDepth()) {
					throw new GroupCallStackLimitException();
				}
				else {
					boardGroupHTML += createGroupHTML(groupElement, depthCount+1);
				}
			}
			else if(groupElement instanceof BoardLink) {
				boardGroupHTML += createLinkHTML(groupElement);
			}
			else {
				throw new Exception("Unknown BoardListElement encountered while generating Board HTML.");
			}
		}
		
		/* Close the board group */
		boardGroupHTML += "</div>\n";
		
		return boardGroupHTML;
	}
	

	
	// === Internal Classes
	
	@SuppressWarnings("serial")
	public class GroupCallStackLimitException extends Exception {
		public GroupCallStackLimitException() {
			super("Max group depth exceeded");
		}
	}
}
