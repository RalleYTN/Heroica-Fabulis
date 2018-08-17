package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class JSONArray extends ArrayList<Object> {

	private static final long serialVersionUID = -2431489538036454681L;

	/**
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray() {}
	
	/**
	 * 
	 * @param collection
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(Collection<?> collection){
		
		super(collection);
	}
	
	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public <T>JSONArray(T[] array) {
		
		for(T element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(byte[] array) {
		
		for(byte element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(boolean[] array) {
		
		for(boolean element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(char[] array) {
		
		for(char element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(short[] array) {
		
		for(short element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(int[] array) {
		
		for(int element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(long[] array) {
		
		for(long element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(float[] array) {
		
		for(float element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(double[] array) {
		
		for(double element : array) {
			
			this.add(element);
		}
	}

	/**
	 * 
	 * @param array
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(Object array) {

		if(array != null && array.getClass().isArray()) {
			
			int length = Array.getLength(array);
			
			for(int index = 0; index < length; index++) {
				
				this.add(Array.get(array, index));
			}
		}
	}

	/**
	 * 
	 * @param json
	 * @throws JSONParseException
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(String json) throws JSONParseException {
		
		super((JSONArray)JSON.parse(json));
	}

	/**
	 * 
	 * @param reader
	 * @throws JSONParseException
	 * @throws IOException
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(Reader reader) throws JSONParseException, IOException {
		
		super((JSONArray)JSON.parse(reader));
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject getObject(int index) {
		
		return JSONUtil.getObject(this.get(index));
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray getArray(int index) {
		
		return JSONUtil.getArray(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Boolean getBoolean(int index) {
		
		return JSONUtil.getBoolean(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Byte getByte(int index) {
		
		return JSONUtil.getByte(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Short getShort(int index) {
		
		return JSONUtil.getShort(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Integer getInteger(int index) {
		
		return JSONUtil.getInteger(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Long getLong(int index) {
		
		return JSONUtil.getLong(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Float getFloat(int index) {
		
		return JSONUtil.getFloat(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Double getDouble(int index) {
		
		return JSONUtil.getDouble(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public String getString(int index) {
		
		return JSONUtil.getString(this.get(index));
	}

	/**
	 * 
	 * @param index
	 * @param format
	 * @return
	 * @throws ParseException
	 * @since 17.08.2018/0.2.0
	 */
	public Date getDate(int index, DateFormat format) throws ParseException {
		
		return JSONUtil.getDate(this.get(index), format);
	}

	/**
	 * 
	 * @param index
	 * @param type
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public <T extends Enum<T>>T getEnum(int index, Class<T> type) {
		
		return JSONUtil.getEnum(this.get(index), type);
	}
}
