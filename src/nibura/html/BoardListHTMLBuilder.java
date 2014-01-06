package nibura.html;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

import nibura.logic.BoardGroup;
import nibura.logic.BoardLink;
import nibura.logic.BoardList;
import nibura.logic.BoardListElement;
import nibura.logic.OptionsHandler;
import nibura.logic.ResourceHandler;

public class BoardListHTMLBuilder {
	private BoardList boardList = null;
	
	public BoardListHTMLBuilder(BoardList boardList_in) {
		if(boardList_in == null) 
			throw new NullPointerException("Error: Null BoardList in BoardListHTMLBuilder. Submit bug report.");
		
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
	private String getHeaderHTML() throws FileNotFoundException {
		String htmlHeader = "";
		InputStream header_file = ResourceHandler.BOARD_LIST_HEADER_HTML.getResourceStream();
		Scanner scanner = new Scanner(header_file);
		
		htmlHeader = scanner.useDelimiter("\\Z").next(); // Read the file in
		scanner.close();
		
		htmlHeader += "\r\n";  // Add in trailing break which Scanner deletes
		
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
		InputStream footer_file = ResourceHandler.BOARD_LIST_FOOTER_HTML.getResourceStream();
		Scanner scanner = new Scanner(footer_file);
		
		htmlFooter = scanner.useDelimiter("\\Z").next(); // Read the file in
		scanner.close();
		
		return htmlFooter;
	}

	private String createLinkHTML(BoardListElement element) {
		String boardLinkHTML = ""
				+ "<a href=\"" + element.getLink() + "\">"
				+ element.getName()
				+ "</a><br/>\n";
		return boardLinkHTML;
	}
	
	private String createGroupHTML(BoardListElement element) throws Exception {
		return createGroupHTML(element,0);
	}
	
	private String createGroupHTML(BoardListElement element, int depthCount) throws Exception {
		String boardGroupHTML = "";
		BoardGroup boardGroup = ((BoardGroup) element); // Cast BoardListElement up to BoardGroup 
		
		/* Create opening HTML for board group */
		boardGroupHTML += 	"<div class=\"group\">"
				+ "<div class=\"group-header\" style=\"padding:5px;\">"
				+ "<span class=\"open-close-icon\"></span>"
				+ boardGroup.getName()
				+ "</div>\n"
				+ "<div class=\"group-content\" style=\"margin-left:10px;\">\n";
		
		/* Add internal content */
		Iterator<BoardListElement> groupElementIterator = boardGroup.iterator();
		while(groupElementIterator.hasNext()) {
			BoardListElement groupElement = groupElementIterator.next();
			
			if(groupElement instanceof BoardGroup) {
				// Check that we aren't exceeding maximum call stack depth
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
		boardGroupHTML += "</div><!--Close Group Content-->\n</div><!--Close Group-->\n";
		
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
