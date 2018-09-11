package de.ralleytn.engine.caveman.json;

import static de.ralleytn.engine.caveman.json.JSONToken.*;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Lightweight JSON parser.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.09.2018/0.4.0
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
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(byte[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(short[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(int[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(long[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(char[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(float[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(double[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(boolean[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param array
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final <T>void write(T[] array, Writer writer) throws IOException {
		
		if(array == null) {
			
			writer.write("null");
			
		} else if(array.length == 0) {
			
			writer.write("[]");
			
		} else {
			
			writer.write('[');
			writer.write(String.valueOf(array[0]));
			
			for(int index = 1; index < array.length; index++) {
				
				writer.write(',');
				writer.write(String.valueOf(array[index]));
			}
			
			writer.write(']');
		}
	}
	
	/**
	 * 
	 * @param collection
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(Collection<?> collection, Writer writer) throws IOException {

		if(collection != null) {
			
			boolean first = true;
			Iterator<?> iterator = collection.iterator();
			writer.write('[');
			
			while(iterator.hasNext()) {
				
				if(first) first = false; else writer.write(',');
				
				Object value = iterator.next();
				
				if(value != null) {
					
					write(value, writer);
					
				} else {
					
					writer.write("null");
				}
			}
			
			writer.write(']');
			
		} else {
			
			writer.write("null");
		}
	}
	
	/**
	 * 
	 * @param map
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	private static final void write(Map<?, ?> map, Writer writer) throws IOException {
		
		if(map != null) {
			
			boolean first = true;
			writer.write('{');
			
			for(Map.Entry<?, ?> entry : map.entrySet()) {
				
				if(first) first = false; else writer.write(',');
				
				writer.write('\"');
				writer.write(JSONUtil.escape(String.valueOf(entry.getKey())));
				writer.write('\"');
				writer.write(':');
				
				write(entry.getValue(), writer);
			}
			
			writer.write('}');
			
		} else {
			
			writer.write("null");
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param writer
	 * @throws IOException
	 * @since 11.09.2018/0.4.0
	 */
	@SuppressWarnings("unchecked")
	public static final void write(Object value, Writer writer) throws IOException {
		
		if(value == null) {
			
			writer.write("null");
			
		} else if(value instanceof Map) {write((Map<Object, Object>)value, writer);
		} else if(value instanceof Collection) {write((Collection<Object>)value, writer);
		} else if(value instanceof String) {writer.write(JSONUtil.escape(value.toString()));
		} else if(value instanceof Double) {writer.write(((Double)value).isInfinite() || ((Double)value).isNaN() ? "null" : value.toString());
		} else if(value instanceof Float) {writer.write(((Float)value).isInfinite() || ((Float)value).isNaN() ? "null" : value.toString());
		} else if(value instanceof Number) {writer.write(value.toString());
		} else if(value instanceof Boolean) {writer.write(value.toString());
		} else if(value instanceof int[]) {write((int[])value, writer);
		} else if(value instanceof float[]) {write((float[])value, writer);
		} else if(value instanceof boolean[]) {write((boolean[])value, writer);
		} else if(value instanceof short[]) {write((short[])value, writer);
		} else if(value instanceof byte[]) {write((byte[])value, writer);
		} else if(value instanceof double[]) {write((double[])value, writer);
		} else if(value instanceof char[]) {write((char[])value, writer);
		} else if(value.getClass().isArray()) {write((Object[])value, writer);
		} else {
			
			writer.write('"');
			writer.write(JSONUtil.escape(value.toString()));
			writer.write('"');
		}
	}
	
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
							
						case TYPE_LEFT_SQUARE:
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
					}
					
					throw new JSONParseException(lexer.getCharacterPosition(), JSONParseException.ERROR_UNEXPECTED_TOKEN, token);
					
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
