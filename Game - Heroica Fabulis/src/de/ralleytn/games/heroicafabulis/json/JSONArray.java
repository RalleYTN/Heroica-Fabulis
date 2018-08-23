package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a JSON array.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class JSONArray extends ArrayList<Object> {

	private static final long serialVersionUID = -2431489538036454681L;

	/**
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray() {}
	
	/**
	 * @param collection collection with which this JSON array should be initialized
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray(Collection<?> collection){
		
		super(collection);
	}

	/**
	 * @param array array with which this JSON array should be initialized
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
	 * Directly parses a JSON array.
	 * @param reader the reader with the JSON data
	 * @throws JSONParseException if the JSON is invalid
	 * @throws IOException if an I/O error occurred
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
}
