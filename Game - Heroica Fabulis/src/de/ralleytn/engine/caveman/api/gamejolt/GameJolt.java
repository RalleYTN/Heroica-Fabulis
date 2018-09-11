package de.ralleytn.engine.caveman.api.gamejolt;

import java.util.List;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.09.2018/0.4.0
 * @since 11.09.2018/0.4.0
 */
public class GameJolt {

	private final long gameID;
	private final String privateKey;
	
	private String username;
	private String token;
	
	/**
	 * 
	 * @param gameID
	 * @param privateKey
	 * @since 11.09.2018/0.4.0
	 */
	public GameJolt(long gameID, String privateKey) {
		
		this.gameID = gameID;
		this.privateKey = privateKey;
	}
	
	/**
	 * 
	 * @param username
	 * @param token
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean login(String username, String token) {
		
		return false;
	}
	
	/**
	 * 
	 * @since 11.09.2018/0.4.0
	 */
	public void logout() {
		
		
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public GameJoltUser getUser(String username) {
		
		return null;
	}
	
	/**
	 * 
	 * @param userID
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public GameJoltUser getUser(long userID) {
		
		return null;
	}
	
	/**
	 * 
	 * @param userIDs
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public List<GameJoltUser> getUsers(long[] userIDs) {
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public long[] getFriends() {
		
		return null;
	}
	
	/**
	 * 
	 * @param trophyID
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public GameJoltTrophy getTrophy(long trophyID) {
		
		return null;
	}
	
	/**
	 * 
	 * @param trophyIDs
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public List<GameJoltTrophy> getTrophies(long[] trophyIDs) {
		
		return null;
	}
	
	/**
	 * 
	 * @param achieved
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public List<GameJoltTrophy> getTrophies(boolean achieved) {
		
		return null;
	}
	
	/**
	 * 
	 * @param trophyID
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean achieveTrophy(long trophyID) {
		
		return false;
	}
	
	/**
	 * 
	 * @param trophyID
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public boolean unachieveTrophy(long trophyID) {
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.09.2018/0.4.0
	 */
	public GameJoltTime getTime() {
		
		return null;
	}
}
