/*
 * ApacehServer.Java
 * 
 * This singleton class serves as the manager for starting and stopping the Apache Debug Test server.
 * 
 * Many of the tests in packages require accessing web pages and expect partial HTML files that can't
 * be retrieved from the LIVE servers.
 * 
 * In order to bring the tests closer in line and avoid pointless
 * wrappers. There is an Apache server stored on the drive at C:/Apache24_Debug
 * This server is started before each test that requires it and the server acts to serve the files for the
 * test. After the test class is completed, the server is torn down. This process of Start/Stop, causes the
 * testing process to take a bit more time to run, but brings the tests closer to LIVE.
 * 
 * Port for server is hard coded to 6000 in the TestResource.java.
 * Its also specified that way in the httpd.conf file on apache.
 */

package functional;

import java.io.IOException;

class ApacheServer {
	private static ApacheServer instance = null;
	private String HTTPD_COMMAND = null;
	private static Process server = null;
	
	private ApacheServer() throws IOException {
		String os_name = System.getProperty("os.name");
		if(os_name.contains("Mac OS")) {
			HTTPD_COMMAND = "/usr/sbin/httpd start";
		}
		else if(os_name.contains("Windows")) {
			HTTPD_COMMAND = "C:/Apache24_Debug/bin/httpd.exe";
		}
		
		if(!isRunning()) {
			server = Runtime.getRuntime().exec(HTTPD_COMMAND);
		}
	}

	public static ApacheServer createServerInstance() throws IOException {
		if(instance == null)
			return new ApacheServer();
		else
			return instance;
	}
	
	public static void exit() {
		if(isRunning()) {
			server.destroy();
			server = null;
		}
	}
	public static boolean isRunning() {
		if(server == null)
			return false;
		return true;
	}
}