package de.ralleytn.games.heroicafabulis.engine;

/**
 * Represents objects that are created and stored by LWJGL.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public abstract class LWJGLObject implements Disposable {

	protected int id;
	
	@Override
	protected void finalize() throws Throwable {
		
		try {
		
			this.dispose();
			
		} finally {
			
			super.finalize();
		}
	}
	
	/**
	 * @return the ID of this object
	 * @since 04.08.2018/0.1.0
	 */
	public int getID() {
		
		return this.id;
	}
}
