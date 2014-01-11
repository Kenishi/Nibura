package nibura.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Date;
import java.util.Scanner;

import nibura.logic.BoardListElement.SuiteType;

public class PostListCreator {
	private File csvFile = null;
	private BoardLink boardLink = null;
	
	public PostListCreator(URI uri, BoardLink link) {
		csvFile = new File(uri);
		boardLink = link;
	}
	
	public ThreadList createPostListFromCSV() throws FileNotFoundException, MalformedURLException {
		ThreadList postList = new ThreadList();
		
		Scanner scanner = new Scanner(csvFile);
		scanner.useDelimiter("\n");
		while(scanner.hasNext()) {
			String line = scanner.next();
			String output[] = line.split(",");
			
			String url = boardLink.getLink() + output[0];
			String title = output[1];
			int postCount = Integer.parseInt(output[2]);
			
			ThreadLink postLink = new ThreadLink(title,url,postCount,SuiteType.NICH_SUITE, new Date());
			postList.addThreadLink(postLink);
		}		
		scanner.close();
		
		return postList;
	}
}
