package nibura.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

public class PostListCreator {
	private File csvFile = null;
	private BoardLink boardLink = null;
	
	public PostListCreator(URI uri, BoardLink link) {
		csvFile = new File(uri);
		boardLink = link;
	}
	
	public PostList createPostListFromCSV() throws FileNotFoundException {
		PostList postList = new PostList();
		Scanner scanner = new Scanner(csvFile);
		scanner.useDelimiter(",");
		String url
		
		return postList;
	}
}
