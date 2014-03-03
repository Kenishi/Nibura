package functional;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import functional.ApacheServer.UnknownOSException;

public class CanGetTestResFromDebugServer {
	private static ApacheServer server = null;
	
	@BeforeClass
	public static void setupOnce() throws IOException, UnknownOSException {
		server = ApacheServer.createServerInstance();
	}
	
	
	@Test
	public void test() throws IOException, URISyntaxException {
		URL url = TestResources.APACHE_TEST_RESOURCE.getLocalURL();
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url.toURI());
		CloseableHttpResponse response = client.execute(method);
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
		
		// Test
		Assert.assertEquals("APACHE TEST", content);
			
		
	}
	
	@AfterClass
	public static void tearDown() throws IOException, UnknownOSException {
		ApacheServer.createServerInstance().exit();
	}

}
