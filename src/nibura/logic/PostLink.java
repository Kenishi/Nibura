package nibura.logic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import nibura.logic.BoardListElement.SuiteType;

public class PostLink {
	public final static Date NO_LAST_UPDATE = null;
	
	private String title = null;
	private URL postLink = null;
	private Integer postCount = null; 
	private SuiteType suite = null;
	private Date lastUpdate = null;
	
	public PostLink(String title, URL postLink, int postCount, SuiteType suite, Date lastUpdate) {
		this.title = title;
		this.postLink = postLink;
		this.postCount = postCount;
		this.suite = suite;		
		this.lastUpdate = lastUpdate;
	}
	
	public PostLink(String title, String postLink, int postCount, SuiteType suite, Date lastUpdate) throws MalformedURLException {
		this(title, new URL(postLink), postCount, suite, lastUpdate);
	}
	
	public PostLink(String title, URL postLink, int postCount, SuiteType suite) {
		this(title, postLink, postCount, suite, PostLink.NO_LAST_UPDATE);
	}
	
	public PostLink(String title, String postLink, int postCount, SuiteType suite) throws MalformedURLException {
		this(title, new URL(postLink), postCount, suite, PostLink.NO_LAST_UPDATE);
	}
	
	public String getTitle() {
		return title;
	}
	
	public URL getURL() {
		return postLink;
	}
	
	public int getPostCount() {
		return postCount.intValue();
	}
	
	public SuiteType getSuiteType() {
		return suite;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public boolean isNewer(PostLink post) {
		Date postDate = post.getLastUpdate();
		postDate.c
	}
}
