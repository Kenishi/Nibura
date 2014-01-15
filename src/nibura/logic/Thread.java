package nibura.logic;

public class Thread {
	ArrayList<Post> posts = null;
	
	public Thread(ThreadLink link){
			NichThreadFetcher fetcher = new NichThreadFetcher(link);
			posts = fetcher.getPosts();
	}
	
	public Post getPost(int postNum) {
		
	}
}
