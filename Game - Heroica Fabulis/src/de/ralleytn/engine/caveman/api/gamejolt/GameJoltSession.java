package de.ralleytn.engine.caveman.api.gamejolt;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.09.2018/0.4.0
 * @since 11.09.2018/0.4.0
 */
public class GameJoltSession {

	private final GameJolt gamejolt;
	
	/**
	 * 
	 * @param gamejolt
	 * @since 11.09.2018/0.4.0
	 */
	GameJoltSession(GameJolt gamejolt) {
		
		this.gamejolt = gamejolt;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean open() {
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean isOpen() {
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean close() {
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean ping() {
		
		return false;
	}
	
	/**
	 * 
	 * @param status
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean ping(Status status) {
		
		return false;
	}
	
	/**
	 * 
	 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
	 * @version 11.09.2018/0.4.0
	 * @since 11.09.2018/0.4.0
	 */
	public static enum Status {
		
		/**
		 * @since 11.09.2018/0.4.0
		 */
		ACTIVE("active"),
		
		/**
		 * 
		 * @since 11.09.2018/0.4.0
		 */
		IDLE("idle");
		
		private final String value;
		
		/**
		 * 
		 * @param value
		 * @since 11.09.2018/0.4.0
		 */
		private Status(String value) {
			
			this.value = value;
		}
		
		@Override
		public String toString() {
			
			return this.value;
		}
	}
}
