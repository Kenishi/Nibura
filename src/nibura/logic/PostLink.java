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
	
	/**
	 * Checks if this post is updated from comparedPost.
	 * @param comparePost The new PostLink to compare against.
	 * @return Returns true when the comparePost is newer. Returns false when there is no update or if the posts are not the same.
	 */
	public boolean isUpdated(PostLink comparePost) {
		if(this.isSamePostLink(comparePost)) {
			if(comparePost.getPostCount() > this.getPostCount()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks that content matches. Ignores last update and post count.
	 * @param comparePost
	 * @return Returns true if they are the same post link. Otherwise, false.
	 */
	public boolean isSamePostLink(PostLink comparePost) {
		if(comparePost.getSuiteType() != suite)
			return false;
		if(comparePost.getTitle().equals(title) == false)
			return false;
		if(comparePost.getURL().equals(postLink) == false)
			return false;
		return true;
	}
}
