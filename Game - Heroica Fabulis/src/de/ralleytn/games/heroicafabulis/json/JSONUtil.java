package de.ralleytn.games.heroicafabulis.json;

import java.util.Collection;
import java.util.Map;

/**
 * Utility class providing methods for working with JSON.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public final class JSONUtil {

	/**
	 * @since 17.08.2018/0.2.0
	 */
	private JSONUtil() {}
	
	/**
	 * Converts the given object to a {@linkplain JSONObject}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final JSONObject getObject(Object value) {

		if(value != null) {
			
			       if(value instanceof JSONObject) {return (JSONObject)value;
			} else if(value instanceof Map)        {return new JSONObject((Map<?, ?>)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain JSONArray}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final JSONArray getArray(Object value) {

		if(value != null) {
			
			       if(value instanceof JSONArray)  {return (JSONArray)value;
			} else if(value instanceof Collection) {return new JSONArray((Collection<?>)value);
			} else if(value.getClass().isArray())  {return new JSONArray(value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain Boolean}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final Boolean getBoolean(Object value) {

		if(value != null) {
			
			       if(value instanceof Boolean) {return (boolean)value;
			} else if(value instanceof String)  {return Boolean.parseBoolean((String)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain Byte}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final Byte getByte(Object value) {
		
		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).byteValue();
			} else if(value instanceof String)  {return Byte.parseByte((String)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain Short}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final Short getShort(Object value) {

		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).shortValue();
			} else if(value instanceof String)  {return Short.parseShort((String)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain Integer}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final Integer getInteger(Object value) {
		
		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).intValue();
			} else if(value instanceof String)  {return Integer.parseInt((String)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain Long}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final Long getLong(Object value) {

		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).longValue();
			} else if(value instanceof String)  {return Long.parseLong((String)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain Float}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final Float getFloat(Object value) {

		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).floatValue();
			} else if(value instanceof String)  {return Float.parseFloat((String)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain Double}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final Double getDouble(Object value) {
		
		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).doubleValue();
			} else if(value instanceof String)  {return Double.parseDouble((String)value);
			}
		}
		
		return null;
	}
	
	/**
	 * Converts the given object to a {@linkplain String}.
	 * @param value the value
	 * @return the result or {@code null} if the given object could not be converted
	 * @since 17.08.2018/0.2.0
	 */
	public static final String getString(Object value) {

		if(value != null) {
			
			return value.toString();
		}
		
		return null;
	}
	
	/**
	 * Escapes a string with JSON rules.
	 * @param string the string that should be escaped
	 * @param builder the target on which the result should be written
	 * @since 17.08.2018/0.2.0
	 */
	public static void escape(String string, StringBuilder builder) {
    	
    	for(int index = 0; index < string.length(); index++) {
    		
    		char character = string.charAt(index);
    		
    		// If-Else is faster than Switch-Case
    		       if(character == '"')  {builder.append("\\\"");
    		} else if(character == '\\') {builder.append("\\\\");
    		} else if(character == '\b') {builder.append("\\b");
    		} else if(character == '\f') {builder.append("\\f");
    		} else if(character == '\n') {builder.append("\\n");
    		} else if(character == '\r') {builder.append("\\r");
    		} else if(character == '\t') {builder.append("\\t");
    		} else if(character == '/')  {builder.append("\\/");
    		} else {
    			
    			if((character >= '\u0000' && character <= '\u001F') ||
    			   (character >= '\u007F' && character <= '\u009F') ||
    			   (character >= '\u2000' && character <= '\u20FF')) {
    				
    				String hex = Integer.toHexString(character);
					builder.append("\\u");
					
					for(int k = 0; k < (4 - hex.length()); k++) {
						
						builder.append('0');
					}
					
					builder.append(hex.toUpperCase());
					
    			} else {
    				
    				builder.append(character);
    			}
    		}
    	}
	}
}
