package de.ralleytn.games.heroicafabulis.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public final class JSONUtil {

	private JSONUtil() {}
	
	public static final JSONObject getObject(Object value) {

		if(value != null) {
			
			       if(value instanceof JSONObject) {return (JSONObject)value;
			} else if(value instanceof Map)        {return new JSONObject((Map<?, ?>)value);
			}
		}
		
		return null;
	}
	
	public static final JSONArray getArray(Object value) {

		if(value != null) {
			
			       if(value instanceof JSONArray)  {return (JSONArray)value;
			} else if(value instanceof boolean[])  {return new JSONArray((boolean[])value);
			} else if(value instanceof byte[])     {return new JSONArray((byte[])value);
			} else if(value instanceof char[])     {return new JSONArray((char[])value);
			} else if(value instanceof short[])    {return new JSONArray((short[])value);
			} else if(value instanceof int[])      {return new JSONArray((int[])value);
			} else if(value instanceof long[])     {return new JSONArray((long[])value);
			} else if(value instanceof float[])    {return new JSONArray((float[])value);
			} else if(value instanceof double[])   {return new JSONArray((double[])value);
			} else if(value instanceof Collection) {return new JSONArray((Collection<?>)value);
			} else if(value.getClass().isArray())  {return new JSONArray(value);
			}
		}
		
		return null;
	}
	
	public static final Boolean getBoolean(Object value) {

		if(value != null) {
			
			       if(value instanceof Boolean) {return (boolean)value;
			} else if(value instanceof String)  {return Boolean.parseBoolean((String)value);
			}
		}
		
		return null;
	}
	
	public static final Byte getByte(Object value) {
		
		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).byteValue();
			} else if(value instanceof String)  {return Byte.parseByte((String)value);
			}
		}
		
		return null;
	}
	
	public static final Short getShort(Object value) {

		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).shortValue();
			} else if(value instanceof String)  {return Short.parseShort((String)value);
			}
		}
		
		return null;
	}
	
	public static final Integer getInteger(Object value) {
		
		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).intValue();
			} else if(value instanceof String)  {return Integer.parseInt((String)value);
			}
		}
		
		return null;
	}
	
	public static final Long getLong(Object value) {

		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).longValue();
			} else if(value instanceof String)  {return Long.parseLong((String)value);
			}
		}
		
		return null;
	}
	
	public static final Float getFloat(Object value) {

		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).floatValue();
			} else if(value instanceof String)  {return Float.parseFloat((String)value);
			}
		}
		
		return null;
	}
	
	public static final Double getDouble(Object value) {
		
		if(value != null) {
			
			       if(value instanceof Number)  {return ((Number)value).doubleValue();
			} else if(value instanceof String)  {return Double.parseDouble((String)value);
			}
		}
		
		return null;
	}
	
	public static final String getString(Object value) {

		if(value != null) {
			
			return value.toString();
		}
		
		return null;
	}
	
	public static final Date getDate(Object value, DateFormat format) throws ParseException {

		if(value != null) {
			
			return value instanceof Date ? (Date)value : format.parse(value.toString());
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T extends Enum<T>>T getEnum(Object value, Class<T> type) {
		
		if(value != null) {
			
			for(Object enumConstant : type.getEnumConstants()) {
				
				if(((T)enumConstant).name().equals(value.toString())) {
					
					return (T)enumConstant;
				}
			}
		}
		
		return null;
	}
	
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
