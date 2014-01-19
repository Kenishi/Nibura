package nibura.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ThreadDownloader {

	protected static String getContentByURL(URL threadURL) throws URISyntaxException, ClientProtocolException, IOException, InvalidThreadException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(threadURL.toURI());
		CloseableHttpResponse response = client.execute(method);
		
		// Check for 404 NOT FOUND
		StatusLine status = response.getStatusLine();
		if(status.getStatusCode() == 404) {
			throw new InvalidThreadException("404 THREAD NOT FOUND (" + threadURL.toString() + ")");
		}
		
		String content = "";
		try {
			HttpEntity entity =  response.getEntity();
			String encoding = entity.getContentEncoding().getValue();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),Charset.forName("SJIS")));
			String line = "";
			while((line = reader.readLine()) != null ) {
				content += line;
			}
		}
		finally {
			response.close();
		}
		
		return content;
	}
}
