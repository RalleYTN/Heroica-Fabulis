package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class JSON {

	private static final int S_INIT = 0;
	private static final int S_IN_FINISHED_VALUE = 1;
	private static final int S_IN_OBJECT = 2;
	private static final int S_IN_ARRAY = 3;
	private static final int S_PASSED_PAIR_KEY = 4;
	private static final int S_IN_ERROR = -1;
	
	private JSON() {}
	
	private static Yylex lexer = new Yylex((Reader)null);
	private static Yytoken token;
	private static int status = S_INIT;
	
	private static final void nextToken() throws JSONParseException, IOException {
		
		token = lexer.yylex();
		
		if(token == null) {
			
			token = new Yytoken(Yytoken.TYPE_EOF, null);
		}
	}
	
	private static final void init(Stack<Object> statusStack, Stack<Object> valueStack) {
		
		if(token.type == Yytoken.TYPE_VALUE) {
			
			status = S_IN_FINISHED_VALUE;
			statusStack.push(status);
			valueStack.push(token.value);
			
		} else if(token.type == Yytoken.TYPE_LEFT_BRACE) {
			
			status = S_IN_OBJECT;
			statusStack.push(status);
			valueStack.push(new JSONObject());
			
		} else if(token.type == Yytoken.TYPE_LEFT_SQUARE) {
			
			status = S_IN_ARRAY;
			statusStack.push(status);
			valueStack.push(new JSONArray());
			
		} else {
			
			status = S_IN_ERROR;
		}
	}
	
	private static final Object inFinishedValue(Stack<Object> valueStack) throws JSONParseException {
		
		if(token.type == Yytoken.TYPE_EOF) {
			
			return valueStack.pop();
			
		} else {
			
			throw new JSONParseException(getPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
		}
	}
	
	private static final void inObject(Stack<Object> statusStack, Stack<Object> valueStack) {
		
		if(token.type == Yytoken.TYPE_VALUE) {
			
			if(token.value instanceof String) {
				
				String key = (String)token.value;
				valueStack.push(key);
				status = S_PASSED_PAIR_KEY;
				statusStack.push(status);
				
			} else {
				
				status = S_IN_ERROR;
			}
			
		} else if(token.type == Yytoken.TYPE_RIGHT_BRACE) {
			
			if(valueStack.size() > 1){
				
				statusStack.pop();
				valueStack.pop();
				status = (int)statusStack.peek();
				
			} else {
				
				status = S_IN_FINISHED_VALUE;
			}
			
		} else if(token.type != Yytoken.TYPE_COMMA) {
			
			status = S_IN_ERROR;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static final void inPassedPairKey(Stack<Object> statusStack, Stack<Object> valueStack) {
		
		if(token.type == Yytoken.TYPE_VALUE) {
			
			statusStack.pop();
			String key = (String)valueStack.pop();
			Map<Object, Object> parent = (Map<Object, Object>)valueStack.peek();
			parent.put(key, token.value);
			status = (int)statusStack.peek();
			
		} else if(token.type == Yytoken.TYPE_LEFT_SQUARE) {
			
			statusStack.pop();
			String key = (String)valueStack.pop();
			Map<Object, Object> parent = (Map<Object, Object>)valueStack.peek();
			List<Object> newArray = new JSONArray();
			parent.put(key, newArray);
			status = S_IN_ARRAY;
			statusStack.push(status);
			valueStack.push(newArray);
			
		} else if(token.type == Yytoken.TYPE_LEFT_BRACE) {
			
			statusStack.pop();
			String key = (String)valueStack.pop();
			Map<Object, Object> parent = (Map<Object, Object>)valueStack.peek();
			Map<Object, Object> newObject = new JSONObject();
			parent.put(key, newObject);
			status = S_IN_OBJECT;
			statusStack.push(status);
			valueStack.push(newObject);
			
		} else if(token.type != Yytoken.TYPE_COLON) {
			
			status = S_IN_ERROR;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static final void inArray(Stack<Object> statusStack, Stack<Object> valueStack) {
		
		if(token.type == Yytoken.TYPE_VALUE) {
			
			List<Object> val = (List<Object>)valueStack.peek();
			val.add(token.value);
			
		} else if(token.type == Yytoken.TYPE_RIGHT_SQUARE) {
			
			if(valueStack.size() > 1) {
				
				statusStack.pop();
				valueStack.pop();
				status = (int)statusStack.peek();
			
			} else {
				
				status = S_IN_FINISHED_VALUE;
			}
			
		} else if(token.type == Yytoken.TYPE_LEFT_BRACE) {
			
			List<Object> val = (List<Object>)valueStack.peek();
			Map<Object, Object> newObject = new JSONObject();
			val.add(newObject);
			status = S_IN_OBJECT;
			statusStack.push(status);
			valueStack.push(newObject);
			
		} else if(token.type == Yytoken.TYPE_LEFT_SQUARE) {
			
			List<Object> val = (List<Object>)valueStack.peek();
			List<Object> newArray = new JSONArray();
			val.add(newArray);
			status = S_IN_ARRAY;
			statusStack.push(status);
			valueStack.push(newArray);
			
		} else if(token.type != Yytoken.TYPE_COMMA) {
			
			status = S_IN_ERROR;
		}
	}

    public static final void reset() {
    	
        token = null;
        status = S_INIT;
    }

	public static final void reset(Reader reader) {
		
		lexer.yyreset(reader);
		reset();
	}

	public static final int getPosition() {
		
		return lexer.getPosition();
	}
	
	public static final Object parse(String json) throws JSONParseException {
		
		try(StringReader reader = new StringReader(json)) {
			
			return parse(reader);
			
		} catch(IOException exception) {
			
			// WILL NEVER HAPPEN!
		}
		
		return null;
	}
	
	public static final Object parse(Reader reader) throws IOException, JSONParseException {

		reset(reader);
		Stack<Object> statusStack = new Stack<>();
		Stack<Object> valueStack = new Stack<>();

		do {
			
			nextToken();
			
				   if(status == S_INIT)              {init(statusStack, valueStack);
			} else if(status == S_IN_FINISHED_VALUE) {return inFinishedValue(valueStack);
			} else if(status == S_IN_OBJECT)         {inObject(statusStack, valueStack);
			} else if(status == S_PASSED_PAIR_KEY)   {inPassedPairKey(statusStack, valueStack);
			} else if(status == S_IN_ARRAY)          {inArray(statusStack, valueStack);
			}
			
			if(status == S_IN_ERROR) {
				
				throw new JSONParseException(getPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
			}
	
		} while(token.type != Yytoken.TYPE_EOF);

		throw new JSONParseException(getPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
	}
}
