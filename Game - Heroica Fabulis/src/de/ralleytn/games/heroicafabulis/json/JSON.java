package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Lightweight JSON parser.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public final class JSON {

	private static final int S_INIT = 0;
	private static final int S_IN_FINISHED_VALUE = 1;
	private static final int S_IN_OBJECT = 2;
	private static final int S_IN_ARRAY = 3;
	private static final int S_PASSED_PAIR_KEY = 4;
	private static final int S_IN_ERROR = -1;
	
	/**
	 * Private because no instance of this class should exist.
	 * @since 17.08.2018/0.2.0
	 */
	private JSON() {}
	
	/**
	 * Parses JSON from a reader.
	 * @param reader the reader
	 * @return an object representing the parsed JSON
	 * @throws IOException if an I/O error occurred
	 * @throws JSONParseException if the JSON is invalid
	 * @since 17.08.2018/0.2.0
	 */
	@SuppressWarnings("unchecked")
	public static final Object parse(Reader reader) throws IOException, JSONParseException {

		JSONLexer lexer = new JSONLexer(reader);
		JSONToken token = null;
        int status = S_INIT;
		
		Stack<Object> statusStack = new Stack<>();
		Stack<Object> valueStack = new Stack<>();

		do {
			
			token = lexer.nextToken();
			
			if(token == null) {
				
				token = new JSONToken(JSONToken.TYPE_EOF, null);
			}
			
			int tokenType = token.getType();
			Object tokenValue = token.getValue();
			
			if(status == S_INIT) {
					   
				if(tokenType == JSONToken.TYPE_VALUE) {
					
					status = S_IN_FINISHED_VALUE;
					statusStack.push(status);
					valueStack.push(tokenType);
					
				} else if(tokenType == JSONToken.TYPE_LEFT_BRACE) {
					
					status = S_IN_OBJECT;
					statusStack.push(status);
					valueStack.push(new JSONObject());
					
				} else if(tokenType == JSONToken.TYPE_LEFT_SQUARE) {
					
					status = S_IN_ARRAY;
					statusStack.push(status);
					valueStack.push(new JSONArray());
					
				} else {
					
					status = S_IN_ERROR;
				}
					   
			} else if(status == S_IN_FINISHED_VALUE) {
				
				if(tokenType == JSONToken.TYPE_EOF) {
					
					return valueStack.pop();
					
				} else {
					
					throw new JSONParseException(lexer.getCharacterPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
				}
				
			} else if(status == S_IN_OBJECT) {
				
				if(tokenType == JSONToken.TYPE_VALUE) {
					
					if(tokenValue instanceof String) {
						
						String key = (String)tokenValue;
						valueStack.push(key);
						status = S_PASSED_PAIR_KEY;
						statusStack.push(status);
						
					} else {
						
						status = S_IN_ERROR;
					}
					
				} else if(tokenType == JSONToken.TYPE_RIGHT_BRACE) {
					
					if(valueStack.size() > 1){
						
						statusStack.pop();
						valueStack.pop();
						status = (int)statusStack.peek();
						
					} else {
						
						status = S_IN_FINISHED_VALUE;
					}
					
				} else if(tokenType != JSONToken.TYPE_COMMA) {
					
					status = S_IN_ERROR;
				}
				
			} else if(status == S_PASSED_PAIR_KEY) {
				
				if(tokenType == JSONToken.TYPE_VALUE) {
					
					statusStack.pop();
					String key = (String)valueStack.pop();
					Map<Object, Object> parent = (Map<Object, Object>)valueStack.peek();
					parent.put(key, tokenValue);
					status = (int)statusStack.peek();
					
				} else if(tokenType == JSONToken.TYPE_LEFT_SQUARE) {
					
					statusStack.pop();
					String key = (String)valueStack.pop();
					Map<Object, Object> parent = (Map<Object, Object>)valueStack.peek();
					List<Object> newArray = new JSONArray();
					parent.put(key, newArray);
					status = S_IN_ARRAY;
					statusStack.push(status);
					valueStack.push(newArray);
					
				} else if(tokenType == JSONToken.TYPE_LEFT_BRACE) {
					
					statusStack.pop();
					String key = (String)valueStack.pop();
					Map<Object, Object> parent = (Map<Object, Object>)valueStack.peek();
					Map<Object, Object> newObject = new JSONObject();
					parent.put(key, newObject);
					status = S_IN_OBJECT;
					statusStack.push(status);
					valueStack.push(newObject);
					
				} else if(tokenType != JSONToken.TYPE_COLON) {
					
					status = S_IN_ERROR;
				}
				
			} else if(status == S_IN_ARRAY) {
				
				if(tokenType == JSONToken.TYPE_VALUE) {
					
					List<Object> val = (List<Object>)valueStack.peek();
					val.add(tokenValue);
					
				} else if(tokenType == JSONToken.TYPE_RIGHT_SQUARE) {
					
					if(valueStack.size() > 1) {
						
						statusStack.pop();
						valueStack.pop();
						status = (int)statusStack.peek();
					
					} else {
						
						status = S_IN_FINISHED_VALUE;
					}
					
				} else if(tokenType == JSONToken.TYPE_LEFT_BRACE) {
					
					List<Object> val = (List<Object>)valueStack.peek();
					Map<Object, Object> newObject = new JSONObject();
					val.add(newObject);
					status = S_IN_OBJECT;
					statusStack.push(status);
					valueStack.push(newObject);
					
				} else if(tokenType == JSONToken.TYPE_LEFT_SQUARE) {
					
					List<Object> val = (List<Object>)valueStack.peek();
					List<Object> newArray = new JSONArray();
					val.add(newArray);
					status = S_IN_ARRAY;
					statusStack.push(status);
					valueStack.push(newArray);
					
				} else if(tokenType != JSONToken.TYPE_COMMA) {
					
					status = S_IN_ERROR;
				}
			}
			
			if(status == S_IN_ERROR) {
				
				throw new JSONParseException(lexer.getCharacterPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
			}
	
		} while(token.getType() != JSONToken.TYPE_EOF);

		throw new JSONParseException(lexer.getCharacterPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
	}
}
