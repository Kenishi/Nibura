package nibura.logic;

import java.util.ArrayList;

public class PostList {
	private ArrayList<PostLink> postList = null;
	
	public PostList() {
		postList = new ArrayList<PostLink>();
	}
	
	protected void addPostLink(PostLink link) {
		postList.add(link);
	}
}