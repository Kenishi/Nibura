package nibura.logic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import nibura.logic.BoardListElement.SuiteType;

public class ThreadLink {
	public final static Date NO_LAST_UPDATE = new Date(0);

	private String title = null;
	private URL threadLink = null;
	private Integer postCount = null; 
	private SuiteType suite = null;
	private Date lastUpdate = null;
	private UUID id = null;
	
	public ThreadLink(String title, URL threadLink, int postCount, SuiteType suite, Date lastUpdate) {
		this.title = title;
		this.threadLink = threadLink;
		this.postCount = new Integer(postCount);
		this.suite = suite;		
		this.lastUpdate = lastUpdate;
		
		// Create ID
		this.id = UUID.randomUUID();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ThreadLink) {
			ThreadLink target = (ThreadLink)obj;
			if(target.getPostCount() != this.getPostCount())
				return false;
			if(! target.getLastUpdate().equals(this.getLastUpdate()))
				return false;
			if(! target.getSuiteType().equals(this.getSuiteType()))
				return false;
			if(! target.getTitle().equals(this.getTitle()))
				return false;
			if(! target.getURL().equals(this.getURL()))
				return false;
			return true;
		}
		else 
			return false;
	}
	public ThreadLink(String title, String threadLink, int postCount, SuiteType suite, Date lastUpdate) throws MalformedURLException {
		this(title, new URL(threadLink), postCount, suite, lastUpdate);
	}
	
	public ThreadLink(String title, URL threadLink, int postCount, SuiteType suite) {
		this(title, threadLink, postCount, suite, ThreadLink.NO_LAST_UPDATE);
	}
	
	public ThreadLink(String title, String threadLink, int postCount, SuiteType suite) throws MalformedURLException {
		this(title, new URL(threadLink), postCount, suite, ThreadLink.NO_LAST_UPDATE);
	}
	
	public String getTitle() {
		return title;
	}
	
	public URL getURL() {
		return threadLink;
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
	 * Checks if this post is updated from compareThread.
	 * @param compareThread The new PostLink to compare against.
	 * @return Returns true when the comparePost is newer. Returns false when there is no update or if the posts are not the same.
	 */
	public boolean isUpdated(ThreadLink compareThread) {
		if(this.isSameThreadLink(compareThread)) {
			if(compareThread.getPostCount() > this.getPostCount()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks that content matches. Ignores last update and post count.
	 * @param compareThread
	 * @return Returns true if they are the same post link. Otherwise, false.
	 */
	public boolean isSameThreadLink(ThreadLink compareThread) {
		if(compareThread.getSuiteType() != suite)
			return false;
		if(compareThread.getTitle().equals(title) == false)
			return false;
		if(compareThread.getURL().equals(threadLink) == false)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String out = "";
		
		out += lastUpdate.toString() + "-";
		out += title + " ";
		out += "[" + postCount.toString() + "] ";
		out += "<" + threadLink.toString() + "> ";
		out += suite.toString();
		
		return out;
	}
	
	public String toString_withID() {
		String out = "";
		
		out += "(" + id.toString() + ") ";
		out += this.toString();
		
		return out;
	}
	
	// Protected Methods
	protected ThreadLink getThreadLinkByID(UUID id) {
		if(this.id.compareTo(id) == 0) {
			return this;
		}
		return null;
	}
	
}
