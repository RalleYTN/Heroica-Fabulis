package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class JSONArray extends ArrayList<Object> {

	private static final long serialVersionUID = -2431489538036454681L;

	public JSONArray() {}
	
	public JSONArray(Collection<?> collection){
		
		super(collection);
	}
	
	public <T>JSONArray(T[] array) {
		
		for(T element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(byte[] array) {
		
		for(byte element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(boolean[] array) {
		
		for(boolean element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(char[] array) {
		
		for(char element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(short[] array) {
		
		for(short element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(int[] array) {
		
		for(int element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(long[] array) {
		
		for(long element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(float[] array) {
		
		for(float element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(double[] array) {
		
		for(double element : array) {
			
			this.add(element);
		}
	}

	public JSONArray(Object array) {

		if(array != null && array.getClass().isArray()) {
			
			int length = Array.getLength(array);
			
			for(int index = 0; index < length; index++) {
				
				this.add(Array.get(array, index));
			}
		}
	}

	public JSONArray(String json) throws JSONParseException {
		
		super((JSONArray)JSON.parse(json));
	}

	public JSONArray(Reader reader) throws JSONParseException, IOException {
		
		super((JSONArray)JSON.parse(reader));
	}
	
	public JSONObject getObject(int index) {
		
		return JSONUtil.getObject(this.get(index));
	}
	
	public JSONArray getArray(int index) {
		
		return JSONUtil.getArray(this.get(index));
	}

	public Boolean getBoolean(int index) {
		
		return JSONUtil.getBoolean(this.get(index));
	}

	public Byte getByte(int index) {
		
		return JSONUtil.getByte(this.get(index));
	}

	public Short getShort(int index) {
		
		return JSONUtil.getShort(this.get(index));
	}

	public Integer getInteger(int index) {
		
		return JSONUtil.getInteger(this.get(index));
	}

	public Long getLong(int index) {
		
		return JSONUtil.getLong(this.get(index));
	}

	public Float getFloat(int index) {
		
		return JSONUtil.getFloat(this.get(index));
	}

	public Double getDouble(int index) {
		
		return JSONUtil.getDouble(this.get(index));
	}

	public String getString(int index) {
		
		return JSONUtil.getString(this.get(index));
	}

	public Date getDate(int index, DateFormat format) throws ParseException {
		
		return JSONUtil.getDate(this.get(index), format);
	}

	public <T extends Enum<T>>T getEnum(int index, Class<T> type) {
		
		return JSONUtil.getEnum(this.get(index), type);
	}
}
