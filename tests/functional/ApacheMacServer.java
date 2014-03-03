package functional;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ApacheMacServer extends ApacheServer {

	@Override
	protected OS getOS() {
		return OS.MAC;
	}

	@Override
	public void exit() {
		System.out.println("TEST_LOG: APACHE MAC SERVER EXIT REQUESTED.");
	}

	@Override
	public boolean isRunning() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet("http://localhost:6000/res");
		CloseableHttpResponse response;
		try {
			response = client.execute(method);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		// Check for 404 NOT FOUND
		StatusLine status = response.getStatusLine();
		if(status.getStatusCode() == 404) {
			return false;
		}
		return true;
	}

}
