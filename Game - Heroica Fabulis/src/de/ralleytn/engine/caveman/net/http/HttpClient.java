package de.ralleytn.engine.caveman.net.http;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import de.ralleytn.engine.caveman.Engine;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.09.2018/0.4.0
 * @since 11.09.2018/0.4.0
 */
public class HttpClient {

	private static final ExecutorService EXECUTOR = Engine.createExecutor();
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	public HttpResponse send(HttpRequest request) throws IOException {
		
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param onReceive
	 * @param onError
	 * @since 11.09.2018/0.4.0
	 */
	public void sendASync(HttpRequest request, Consumer<HttpResponse> onReceive, Consumer<Exception> onError) {
		
		
	}
}
