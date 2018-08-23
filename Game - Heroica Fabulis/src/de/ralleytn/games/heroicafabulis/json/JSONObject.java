package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a JSON object.
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
	 * @param map {@linkplain Map} with which the JSON object should be initialized
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject(Map<?, ?> map) {
		
		super(map);
	}
	
	/**
	 * Directly parses JSON into a JSON object.
	 * @param reader the {@linkplain Reader} with the JSON data
	 * @throws IOException if an I/O error occurred
	 * @throws JSONParseException if the JSON is invalid
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject(Reader reader) throws IOException, JSONParseException {
		
		super((JSONObject)JSON.parse(reader));
	}
	
	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain JSONObject} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public JSONObject getObject(String key) {
		
		return JSONUtil.getObject(this.get(key));
	}
	
	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain JSONArray} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public JSONArray getArray(String key) {
		
		return JSONUtil.getArray(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain Boolean} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public Boolean getBoolean(String key) {
		
		return JSONUtil.getBoolean(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain Byte} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public Byte getByte(String key) {
		
		return JSONUtil.getByte(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain Short} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public Short getShort(String key) {
		
		return JSONUtil.getShort(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain Integer} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public Integer getInteger(String key) {
		
		return JSONUtil.getInteger(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain Long} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public Long getLong(String key) {
		
		return JSONUtil.getLong(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain Float} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public Float getFloat(String key) {
		
		return JSONUtil.getFloat(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain Double} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public Double getDouble(String key) {
		
		return JSONUtil.getDouble(this.get(key));
	}

	/**
	 * @param key the attribute name
	 * @return the attribute as {@linkplain String} or {@code null} if it can not be represented by that, the attribute is {@code null} or doesn't exist
	 * @since 17.08.2018/0.2.0
	 */
	public String getString(String key) {
		
		return JSONUtil.getString(this.get(key));
	}
}
