package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JSONObject extends HashMap<Object, Object> {

	private static final long serialVersionUID = 1287795563780034349L;

	public JSONObject() {}
	
	public JSONObject(Map<?, ?> map) {
		
		super(map);
	}
	
	public JSONObject(String json) throws JSONParseException {
		
		super((JSONObject)JSON.parse(json));
	}
	
	public JSONObject(Reader reader) throws IOException, JSONParseException {
		
		super((JSONObject)JSON.parse(reader));
	}
	
	public JSONObject getObject(String key) {
		
		return JSONUtil.getObject(this.get(key));
	}
	
	public JSONArray getArray(String key) {
		
		return JSONUtil.getArray(this.get(key));
	}

	public Boolean getBoolean(String key) {
		
		return JSONUtil.getBoolean(this.get(key));
	}

	public Byte getByte(String key) {
		
		return JSONUtil.getByte(this.get(key));
	}

	public Short getShort(String key) {
		
		return JSONUtil.getShort(this.get(key));
	}

	public Integer getInteger(String key) {
		
		return JSONUtil.getInteger(this.get(key));
	}

	public Long getLong(String key) {
		
		return JSONUtil.getLong(this.get(key));
	}

	public Float getFloat(String key) {
		
		return JSONUtil.getFloat(this.get(key));
	}

	public Double getDouble(String key) {
		
		return JSONUtil.getDouble(this.get(key));
	}

	public String getString(String key) {
		
		return JSONUtil.getString(this.get(key));
	}

	public Date getDate(String key, DateFormat format) throws ParseException {
		
		return JSONUtil.getDate(this.get(key), format);
	}

	public <T extends Enum<T>>T getEnum(String key, Class<T> type) {
		
		return JSONUtil.getEnum(this.get(key), type);
	}
}
