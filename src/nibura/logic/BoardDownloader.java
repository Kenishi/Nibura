package nibura.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BoardDownloader {
	public static String getContentByURL(String url) throws ClientProtocolException, IOException, InvalidBoardException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		CloseableHttpResponse response = client.execute(method);
		
		// Check for 404 NOT FOUND
		StatusLine status = response.getStatusLine();
		if(status.getStatusCode() == 404) {
			throw new InvalidBoardException("404 BOARD URL NOT FOUND (" + url + ")");
		}
		
		String content = "";
		try {
			HttpEntity entity =  response.getEntity();
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
