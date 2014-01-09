package nibura.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

public class PostListCreator {
	private File csvFile = null;
	
	public PostListCreator(URI uri) {
		csvFile = new File(uri);
	}
	
	public PostList createPostListFromCSV() throws FileNotFoundException {
		PostList postList = new PostList();
		Scanner scanner = new Scanner(csvFile);
		scanner.useDelimiter(",");
		
		
		return postList;
	}
	
	private String getCSVText() throws FileNotFoundException {
		Scanner scanner = new Scanner(csvFile);
		return scanner.useDelimiter("\\Z").next();
	}
}
