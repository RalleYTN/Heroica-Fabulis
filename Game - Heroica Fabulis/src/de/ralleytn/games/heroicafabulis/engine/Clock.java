package de.ralleytn.games.heroicafabulis.engine;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 26.08.2018/0.3.0
 * @since 26.08.2018/0.3.0
 */
public final class Clock implements Updatable {

	private long passedSeconds;
	private long millisInSecond;
	
	/**
	 * 
	 * @param millisInSecond
	 * @since 26.08.2018/0.3.0
	 */
	public void setMillisInSecond(long millisInSecond) {
		
		this.millisInSecond = millisInSecond;
	}

	/**
	 * @since 26.08.2018/0.3.0
	 */
	public void start() {
		
		
	}
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @since 26.08.2018/0.3.0
	 */
	public void stop() {
		
		
	}
	
	/**
	 * 
	 * @return
	 * @since 26.08.2018/0.3.0
	 */
	public long getMillisInSecond() {
		
		return this.millisInSecond;
	}
}
