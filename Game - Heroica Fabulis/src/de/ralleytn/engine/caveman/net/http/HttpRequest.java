package de.ralleytn.engine.caveman.net.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.09.2018/0.4.0
 * @since 11.09.2018/0.4.0
 */
public class HttpRequest {

	/** @since 11.09.2018/0.4.0 */ public static final String METHOD_GET = "GET";
	/** @since 11.09.2018/0.4.0 */ public static final String METHOD_POST = "POST";
	/** @since 11.09.2018/0.4.0 */ public static final String METHOD_PUT = "PUT";
	/** @since 11.09.2018/0.4.0 */ public static final String METHOD_DELETE = "DELETE";
	/** @since 11.09.2018/0.4.0 */ public static final String METHOD_PATCH = "PATCH";
	
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_ACCEPT = "Accept";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_AUTHORIZATION = "Authorization";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_CACHE_CONTROL = "Cache-Control";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_CONNECTION = "Connection";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_COOKIE = "Cookie";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_CONTENT_LENGTH = "Content-Length";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_CONTENT_MD5 = "Content-MD5";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_CONTENT_TYPE = "Content-Type";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_DATE = "Date";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_HOST = "Host";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_IF_MATCH = "If-Match";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_USER_AGENT = "User-Agent";
	/** @since 11.09.2018/0.4.0 */ public static final String HEADER_DNT = "DNT";
	
	private String url;
	private String method;
	private Map<String, String> headers;
	private InputStream body;
	
	/**
	 * 
	 * @param url
	 * @param method
	 * @since 11.09.2018/0.4.0
	 */
	public HttpRequest(String url, String method) {
		
		this.url = url;
		this.method = method;
		this.headers = new HashMap<>();
	}
	
	/**
	 * 
	 * @param header
	 * @param value
	 * @since 11.09.2018/0.4.0
	 */
	public void setHeader(String header, String value) {
		
		this.headers.put(header, value);
	}
	
	/**
	 * 
	 * @param body
	 * @since 11.09.2018/0.4.0
	 */
	public void setBody(byte[] body) {
		
		this.body = new ByteArrayInputStream(body);
	}
	
	/**
	 * 
	 * @param body
	 * @since 11.09.2018/0.4.0
	 */
	public void setBody(InputStream body) {
		
		this.body = body;
	}
	
	/**
	 * 
	 * @param body
	 * @param charset
	 * @since 11.09.2018/0.4.0
	 */
	public void setBody(String body, Charset charset) {
		
		this.body = new ByteArrayInputStream(body.getBytes(charset));
	}
	
	/**
	 * 
	 * @param body
	 * @since 11.09.2018/0.4.0
	 */
	public void setBody(String body) {
		
		this.body = new ByteArrayInputStream(body.getBytes());
	}
	
	/**
	 * 
	 * @param body
	 * @since 11.09.2018/0.4.0
	 */
	public void setBody(File body) {
		
		StringBuilder builder = new StringBuilder();
		
		this.body = new ByteArrayInputStream(builder.toString().getBytes());
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public String getURL() {
		
		return this.url;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public InputStream getBody() {
		
		return this.body;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public String getMethod() {
		
		return this.method;
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
}
