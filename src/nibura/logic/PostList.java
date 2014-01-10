package nibura.logic;

import java.util.ArrayList;

public class PostList {
	private ArrayList<PostLink> postList = null;
	
	public PostList() {
		postList = new ArrayList<PostLink>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PostList) {
			PostList target = (PostList)obj;
			return target.isArrayEqual(postList);
		}
		else
			return false;
	}
	
	protected boolean isArrayEqual(ArrayList<PostLink> target) {
		if(postList.equals(target))
			return true;
		else
			return false;
	}
	protected void addPostLink(PostLink link) {
		postList.add(link);
	}
}
