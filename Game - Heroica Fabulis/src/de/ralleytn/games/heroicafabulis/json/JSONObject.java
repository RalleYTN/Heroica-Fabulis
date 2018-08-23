package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class JSONObject extends HashMap<Object, Object> {

	private static final long serialVersionUID = 1287795563780034349L;

	/**
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject() {}
	
	/**
	 * 
	 * @param map
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject(Map<?, ?> map) {
		
		super(map);
	}
	
	/**
	 * 
	 * @param reader
	 * @throws IOException
	 * @throws JSONParseException
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject(Reader reader) throws IOException, JSONParseException {
		
		super((JSONObject)JSON.parse(reader));
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject getObject(String key) {
		
		return JSONUtil.getObject(this.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray getArray(String key) {
		
		return JSONUtil.getArray(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Boolean getBoolean(String key) {
		
		return JSONUtil.getBoolean(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Byte getByte(String key) {
		
		return JSONUtil.getByte(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Short getShort(String key) {
		
		return JSONUtil.getShort(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Integer getInteger(String key) {
		
		return JSONUtil.getInteger(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Long getLong(String key) {
		
		return JSONUtil.getLong(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Float getFloat(String key) {
		
		return JSONUtil.getFloat(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public Double getDouble(String key) {
		
		return JSONUtil.getDouble(this.get(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public String getString(String key) {
		
		return JSONUtil.getString(this.get(key));
	}
}
