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
	 * Sets the mouse sensitivity.
	 * @param mouseSensitivity mouse sensitivity
	 * @since 25.08.2018/0.3.0
	 */
	public void setMouseSensitivity(float mouseSensitivity) {
		
		this.mouseSensitivity = mouseSensitivity;
	}
	
	/**
	 * Sets the movement speed.
	 * @param speed the movement speed
	 * @since 25.08.2018/0.3.0
	 */
	public void setSpeed(float speed) {
		
		this.speed = speed;
	}
	
	/**
	 * Sets if the Y axis is inverted.
	 * @param invertY {@code true} to invert Y axis, {@code false} to not invert it
	 * @since 25.08.2018/0.3.0
	 */
	public void setInvertY(boolean invertY) {
		
		this.invertY = invertY;
	}
	
	/**
	 * Sets if the X axis is inverted.
	 * @param invertX {@code true} to invert X axis, {@code false} to not invert it
	 * @since 25.08.2018/0.3.0
	 */
	public void setInvertX(boolean invertX) {
		
		this.invertX = invertX;
	}
	
	/**
	 * Sets whether the camera is allowed to flip over its X axis or not.
	 * @param flipping {@code true} the camera can stand on its head, {@code false} camera cannot rotate more than 90° on the X axis
	 * @since 25.08.2018/0.3.0
	 */
	public void setFlipping(boolean flipping) {
		
		this.flipping = flipping;
	}
	
	/**
	 * Sets the up key.
	 * @param upKey the up key
	 * @since 25.08.2018/0.3.0
	 */
	public void setUpKey(int upKey) {
		
		this.upKey = upKey;
	}
	
	/**
	 * Sets the down key.
	 * @param downKey the down key
	 * @since 25.08.2018/0.3.0
	 */
	public void setDownKey(int downKey) {
		
		this.downKey = downKey;
	}
	
	/**
	 * Sets the left key.
	 * @param leftKey the left key
	 * @since 25.08.2018/0.3.0
	 */
	public void setLeftKey(int leftKey) {
		
		this.leftKey = leftKey;
	}
	
	/**
	 * Sets the right key.
	 * @param rightKey the right key
	 * @since 25.08.2018/0.3.0
	 */
	public void setRightKey(int rightKey) {
		
		this.rightKey = rightKey;
	}
	
	/**
	 * Sets the forward key.
	 * @param forwardKey the forward key
	 * @since 25.08.2018/0.3.0
	 */
	public void setForwardKey(int forwardKey) {
		
		this.forwardKey = forwardKey;
	}
	
	/**
	 * Sets the backward key.
	 * @param backwardKey the backward key.
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
	 * @return the mouse sensitivity
	 * @since 25.08.2018/0.3.0
	 */
	public float getMouseSensitivity() {
		
		return this.mouseSensitivity;
	}
	
	/**
	 * @return the movement speed
	 * @since 25.08.2018/0.3.0
	 */
	public float getSpeed() {
		
		return this.speed;
	}
	
	/**
	 * @return {@code true} if the Y axis is inverted, else {@code false}
	 * @since 25.08.2018/0.3.0
	 */
	public boolean invertY() {
		
		return this.invertY;
	}
	
	/**
	 * @return {@code true} if the X axis is inverted, else {@code false}
	 * @since 25.08.2018/0.3.0
	 */
	public boolean invertX() {
		
		return this.invertX;
	}
	
	/**
	 * @return {@code true} if the camera can flip, else {@code false}
	 * @since 25.08.2018/0.3.0
	 */
	public boolean allowsFlipping() {
		
		return this.flipping;
	}
	
	/**
	 * @return the up key
	 * @since 25.08.2018/0.3.0
	 */
	public int getUpKey() {
		
		return this.upKey;
	}
	
	/**
	 * @return the down key
	 * @since 25.08.2018/0.3.0
	 */
	public int getDownKey() {
		
		return this.downKey;
	}
	
	/**
	 * @return the left key
	 * @since 25.08.2018/0.3.0
	 */
	public int getLeftKey() {
		
		return this.leftKey;
	}
	
	/**
	 * @return the right key
	 * @since 25.08.2018/0.3.0
	 */
	public int getRightKey() {
		
		return this.rightKey;
	}
	
	/**
	 * @return the forward key
	 * @since 25.08.2018/0.3.0
	 */
	public int getForwardKey() {
		
		return this.forwardKey;
	}
	
	/**
	 * @return the backward key
	 * @since 25.08.2018/0.3.0
	 */
	public int getBackwardKey() {
		
		return this.backwardKey;
	}
}
