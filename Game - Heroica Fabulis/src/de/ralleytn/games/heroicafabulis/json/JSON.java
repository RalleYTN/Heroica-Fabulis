package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static de.ralleytn.games.heroicafabulis.json.JSONToken.*;

/**
 * Lightweight JSON parser.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 24.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public final class JSON {

	private static final int STATUS_INIT = 0;
	private static final int STATUS_IN_FINISHED_VALUE = 1;
	private static final int STATUS_IN_OBJECT = 2;
	private static final int STATUS_IN_ARRAY = 3;
	private static final int STATUS_PASSED_PAIR_KEY = 4;
	private static final int STATUS_IN_ERROR = -1;
	
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
        int status = STATUS_INIT;
		
		Stack<Object> statusStack = new Stack<>();
		Stack<Object> valueStack = new Stack<>();

		do {
			
			token = lexer.nextToken();
			
			if(token == null) {
				
				token = new JSONToken(TYPE_EOF, null);
			}
			
			int tokenType = token.getType();
			Object tokenValue = token.getValue();
			
			switch(status) {
			
				case STATUS_INIT:
					switch(tokenType) {
					
						case TYPE_VALUE:
							status = STATUS_IN_FINISHED_VALUE;
							statusStack.push(status);
							valueStack.push(tokenType);
							break;
							
						case TYPE_LEFT_BRACE:
							status = STATUS_IN_OBJECT;
							statusStack.push(status);
							valueStack.push(new JSONObject());
							break;
							
						case TYPE_RIGHT_BRACE:
							status = STATUS_IN_ARRAY;
							statusStack.push(status);
							valueStack.push(new JSONArray());
							break;
							
						default:
							status = STATUS_IN_ERROR;
					}
					break;
					
				case STATUS_IN_FINISHED_VALUE:
					if(tokenType == TYPE_EOF) {
						
						return valueStack.pop();
						
					} else {
						
						throw new JSONParseException(lexer.getCharacterPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
					}
					
				case STATUS_IN_OBJECT:
					switch(tokenType) {
						
						case TYPE_VALUE:
							if(tokenValue instanceof String) {
								
								String key = (String)tokenValue;
								valueStack.push(key);
								status = STATUS_PASSED_PAIR_KEY;
								statusStack.push(status);
								
							} else {
								
								status = STATUS_IN_ERROR;
							}
							break;
							
						case TYPE_RIGHT_BRACE:
							if(valueStack.size() > 1){
								
								statusStack.pop();
								valueStack.pop();
								status = (int)statusStack.peek();
								
							} else {
								
								status = STATUS_IN_FINISHED_VALUE;
							}
							break;
							
						case TYPE_COMMA:
							status = STATUS_IN_ERROR;
					}
					break;
					
				case STATUS_IN_ARRAY:
					List<Object> value = null;
					
					switch(tokenType) {
					
						case TYPE_VALUE:
							value = (List<Object>)valueStack.peek();
							value.add(tokenValue);
							break;
							
						case TYPE_RIGHT_SQUARE:
							if(valueStack.size() > 1) {
								
								statusStack.pop();
								valueStack.pop();
								status = (int)statusStack.peek();
							
							} else {
								
								status = STATUS_IN_FINISHED_VALUE;
							}
							break;
							
						case TYPE_LEFT_BRACE:
							value = (List<Object>)valueStack.peek();
							Map<Object, Object> newObject = new JSONObject();
							value.add(newObject);
							status = STATUS_IN_OBJECT;
							statusStack.push(status);
							valueStack.push(newObject);
							break;
							
						case TYPE_LEFT_SQUARE:
							value = (List<Object>)valueStack.peek();
							List<Object> newArray = new JSONArray();
							value.add(newArray);
							status = STATUS_IN_ARRAY;
							statusStack.push(status);
							valueStack.push(newArray);
							break;
							
						case JSONToken.TYPE_COMMA:
							status = STATUS_IN_ERROR;
					}
					break;
					
				case STATUS_PASSED_PAIR_KEY:
					
					Map<Object, Object> parent = null;
					String key = null;
					
					switch(tokenType) {
					
						case TYPE_VALUE:
							statusStack.pop();
							key = (String)valueStack.pop();
							parent = (Map<Object, Object>)valueStack.peek();
							parent.put(key, tokenValue);
							status = (int)statusStack.peek();
							break;
							
						case TYPE_LEFT_SQUARE:
							statusStack.pop();
							key = (String)valueStack.pop();
							parent = (Map<Object, Object>)valueStack.peek();
							List<Object> newArray = new JSONArray();
							parent.put(key, newArray);
							status = STATUS_IN_ARRAY;
							statusStack.push(status);
							valueStack.push(newArray);
							break;
							
						case TYPE_LEFT_BRACE:
							statusStack.pop();
							key = (String)valueStack.pop();
							parent = (Map<Object, Object>)valueStack.peek();
							Map<Object, Object> newObject = new JSONObject();
							parent.put(key, newObject);
							status = STATUS_IN_OBJECT;
							statusStack.push(status);
							valueStack.push(newObject);
							break;
							
						case TYPE_COLON:
							status = STATUS_IN_ERROR;
					}
					break;
					
				case STATUS_IN_ERROR:
					throw new JSONParseException(lexer.getCharacterPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
			}
	
		} while(token.getType() != JSONToken.TYPE_EOF);

		throw new JSONParseException(lexer.getCharacterPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
	}
}
