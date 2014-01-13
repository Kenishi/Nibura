package nibura.logic;

import java.util.Date;

public class Post {
	private int postNum = -1;
	private String poster = null;
	private String email = null;
	private Date postDate = null;
	private String id = null; // The user ID if it exists
	private String content = null; // Post text
	
	public Post(int postNum, String poster, String email, Date postDate,
			String id, String content) {
		this.postNum = postNum;
		this.poster = poster;
		this.email = email;
		this.postDate = postDate;
		this.id = id;
		this.content = content;
	}
	
	public int getPostNum() {
		return postNum;
	}
	
	public String getPoster() {
		return poster;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Date getPostDate() {
		return postDate;
	}
	
	public String getID() {
		return id;
	}
	
	public String getContent() {
		return content;
	}

}
