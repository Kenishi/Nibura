package functional;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.android.nibura.logic.BoardList;
import com.android.nibura.logic.BoardListElement;
import com.android.nibura.logic.BoardListFetcher;
import com.android.nibura.logic.NichBoardListFetcher;


public class CanGetNiChBoardListData {
	private BoardListFetcher fetcher = null; 
	
	@Before
	public void setUp() throws Exception {
		File testBoardList = new File("src/2CH_TEST_MENU.html");
		fetcher = new NichBoardListFetcher(testBoardList);
	}

	@Test
	public void shouldGiveBoardList() {
		BoardList list = fetcher.getBoardList();
		
		Assert.assertSame(list.getClass(), BoardList.class);
	}

	@Test
	public void shouldHaveBoards() {
		
	}
	
	@Test
	public void shouldHaveNonBoardsRemoved() {
		BoardList list = fetcher.getBoardList();
		String removedGroupNames[] = {"É`ÉÉÉbÉg", "â^âcàƒì‡", "ÉcÅ[Éãóﬁ ", "BBSPINK", "Ç‹ÇøÇaÇaÇr", "ëºÇÃÉTÉCÉg"};
		
		// Verify that none of the group names exist in the board list
		Iterator<BoardListElement> boardElements = list.iterator();
		while(boardElements.hasNext()) {
			BoardListElement element = boardElements.next();
			String elementName = element.getName();
			
			boolean hasName = Arrays.asList(removedGroupNames).contains(elementName);
			
			Assert.assertFalse(hasName);
		}
		
	}
		

}
