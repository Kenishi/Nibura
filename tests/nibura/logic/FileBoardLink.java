package nibura.logic;

import java.io.File;
import java.net.MalformedURLException;

public class FileBoardLink extends BoardLink {
	private File file = null;
	
	public FileBoardLink(String boardName, String url, SuiteType suite, File file)
			throws MalformedURLException {
		super(boardName, url, suite);
		this.file = file;
	}

	public File getFile() {
		return file;
	}
	
}
