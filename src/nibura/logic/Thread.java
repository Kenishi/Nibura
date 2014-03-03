package nibura.logic;

import java.util.ArrayList;

import nibura.logic.NichThreadFetcher.PostParsingException;

public class Thread {
	ArrayList<Post> posts = null;
	
	public Thread(ThreadLink link) throws InvalidSuiteTypeException, ParsingErrorException, PostParsingException{
			NichThreadFetcher fetcher = new NichThreadFetcher(link);
			posts = fetcher.getPosts();
	}
	
	public Post getPost(int postNum) {
		return posts.get(postNum);
	}
}
