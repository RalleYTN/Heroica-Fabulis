package de.ralleytn.games.heroicafabulis.engine.rendering.camera;

import static org.lwjgl.glfw.GLFW.*;

import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.Direction;
import de.ralleytn.games.heroicafabulis.engine.Game;
import de.ralleytn.games.heroicafabulis.engine.display.Display;
import de.ralleytn.games.heroicafabulis.engine.input.KeyboardController;
import de.ralleytn.games.heroicafabulis.engine.input.MouseController;

/**
 * Represents the behavior of a flying camera commonly used for debugging purposes.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 25.08.2018/0.3.0
 * @since 13.08.2018/0.1.0
 */
public class FlyCamBehavior extends CameraBehavior {

	private float mouseSensitivity;
	private float speed;
	private boolean invertY;
	private boolean invertX;
	private boolean flipping;
	private int upKey;
	private int downKey;
	private int leftKey;
	private int rightKey;
	private int forwardKey;
	private int backwardKey;
	
	/**
	 * @since 13.08.2018/0.1.0
	 */
	public FlyCamBehavior() {
		
		this.mouseSensitivity = 0.02F;
		this.speed = 1.0F;
		this.upKey = GLFW_KEY_SPACE;
		this.downKey = GLFW_KEY_LEFT_SHIFT;
		this.forwardKey = GLFW_KEY_W;
		this.backwardKey = GLFW_KEY_S;
		this.rightKey = GLFW_KEY_D;
		this.leftKey = GLFW_KEY_A;
	}
	
	@Override
	public void setCamera(Camera camera) {
		
		if(camera == null) {
			
			this.getCamera().getGame().getMouseController().setCursorVisible(true);
		}
		
		super.setCamera(camera);
		
		if(camera != null) {
			
			camera.getGame().getMouseController().setCursorVisible(false);
		}
	}
	
	/**
	 * 
	 * @param mouseSensitivity
	 * @since 25.08.2018/0.3.0
	 */
	public void setMouseSensitivity(float mouseSensitivity) {
		
		this.mouseSensitivity = mouseSensitivity;
	}
	
	/**
	 * 
	 * @param speed
	 * @since 25.08.2018/0.3.0
	 */
	public void setSpeed(float speed) {
		
		this.speed = speed;
	}
	
	/**
	 * 
	 * @param invertY
	 * @since 25.08.2018/0.3.0
	 */
	public void setInvertY(boolean invertY) {
		
		this.invertY = invertY;
	}
	
	/**
	 * 
	 * @param invertX
	 * @since 25.08.2018/0.3.0
	 */
	public void setInvertX(boolean invertX) {
		
		this.invertX = invertX;
	}
	
	/**
	 * 
	 * @param flipping
	 * @since 25.08.2018/0.3.0
	 */
	public void setFlipping(boolean flipping) {
		
		this.flipping = flipping;
	}
	
	/**
	 * 
	 * @param upKey
	 * @since 25.08.2018/0.3.0
	 */
	public void setUpKey(int upKey) {
		
		this.upKey = upKey;
	}
	
	/**
	 * 
	 * @param downKey
	 * @since 25.08.2018/0.3.0
	 */
	public void setDownKey(int downKey) {
		
		this.downKey = downKey;
	}
	
	/**
	 * 
	 * @param leftKey
	 * @since 25.08.2018/0.3.0
	 */
	public void setLeftKey(int leftKey) {
		
		this.leftKey = leftKey;
	}
	
	/**
	 * 
	 * @param rightKey
	 * @since 25.08.2018/0.3.0
	 */
	public void setRightKey(int rightKey) {
		
		this.rightKey = rightKey;
	}
	
	/**
	 * 
	 * @param forwardKey
	 * @since 25.08.2018/0.3.0
	 */
	public void setForwardKey(int forwardKey) {
		
		this.forwardKey = forwardKey;
	}
	
	/**
	 * 
	 * @param backwardKey
	 * @since 25.08.2018/0.3.0
	 */
	public void setBackwardKey(int backwardKey) {
		
		this.backwardKey = backwardKey;
	}
	
	@Override
	public void update(float delta) {
		
		Camera camera = this.getCamera();
		Game game = camera.getGame();
		MouseController mouseController = game.getMouseController();
		Display display = this.getCamera().getGame().getDisplay();
		int middleX = display.getFrameBufferWidth() / 2;
		int middleY = display.getFrameBufferHeight() / 2;
		int deltaX = mouseController.getCursorX() - middleX;
		int deltaY = mouseController.getCursorY() - middleY;
		mouseController.setCursorPosition(middleX, middleY);
		
		this.getCamera().rotate(((this.invertY ? -deltaY : deltaY) * this.mouseSensitivity) * delta, ((this.invertX ? -deltaX : deltaX) * this.mouseSensitivity) * delta, 0.0F);
		
		if(!this.flipping) {
			
			Vector3f rotation = this.getCamera().getRotation();
			rotation.x = Math.min(Math.max(rotation.x, -90.0F), 90.0F);
		}
		
		KeyboardController keyboardController = game.getKeyboardController();
		
		if(keyboardController.isKeyDown(this.forwardKey)) {
			
			camera.move(Direction.FORWARD, this.speed * delta);
			
		} else if(keyboardController.isKeyDown(this.backwardKey)) {
			
			camera.move(Direction.BACKWARD, this.speed * delta);
		}
		
		if(keyboardController.isKeyDown(this.leftKey)) {
			
			camera.move(Direction.LEFT, this.speed * delta);
			
		} else if(keyboardController.isKeyDown(this.rightKey)) {
			
			camera.move(Direction.RIGHT, this.speed * delta);
		}
		
		if(keyboardController.isKeyDown(this.upKey)) {
			
			camera.move(Direction.UP, this.speed * delta);
			
		} else if(keyboardController.isKeyDown(this.downKey)) {
			
			camera.move(Direction.DOWN, this.speed * delta);
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public float getMouseSensitivity() {
		
		return this.mouseSensitivity;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public float getSpeed() {
		
		return this.speed;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public boolean invertY() {
		
		return this.invertY;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public boolean invertX() {
		
		return this.invertX;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public boolean allowsFlipping() {
		
		return this.flipping;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public int getUpKey() {
		
		return this.upKey;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public int getDownKey() {
		
		return this.downKey;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public int getLeftKey() {
		
		return this.leftKey;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public int getRightKey() {
		
		return this.rightKey;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public int getForwardKey() {
		
		return this.forwardKey;
	}
	
	/**
	 * 
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public int getBackwardKey() {
		
		return this.backwardKey;
	}
}
