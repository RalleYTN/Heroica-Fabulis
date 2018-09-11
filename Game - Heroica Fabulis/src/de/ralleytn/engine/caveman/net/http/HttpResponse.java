package de.ralleytn.engine.caveman.net.http;

import java.util.Collections;
import java.util.Map;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.09.2018/0.4.0
 * @since 11.09.2018/0.4.0
 */
public class HttpResponse {

	private final int statusCode;
	private final String statusMessage;
	private final byte[] body;
	private final Map<String, String> headers;
	private final HttpRequest request;
	
	/**
	 * 
	 * @param statusCode
	 * @param statusMessage
	 * @param request
	 * @param body
	 * @param headers
	 * @since 11.09.2018/0.4.0
	 */
	HttpResponse(int statusCode, String statusMessage, HttpRequest request, byte[] body, Map<String, String> headers) {
		
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.request = request;
		this.body = body;
		this.headers = headers;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public int getStatusCode() {
		
		return this.statusCode;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public String getStatusMessage() {
		
		return this.statusMessage;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public byte[] getBody() {
		
		return this.body;
	}
	
	/**
	 * 
	 * @param header
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public String getHeader(String header) {
		
		return this.headers.get(header);
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public Map<String, String> getHeaders() {
		
		return Collections.unmodifiableMap(this.headers);
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public HttpRequest getRequest() {
		
		return this.request;
	}
}
