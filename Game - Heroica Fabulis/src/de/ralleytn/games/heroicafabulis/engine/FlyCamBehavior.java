package de.ralleytn.games.heroicafabulis.engine;

import static org.lwjgl.glfw.GLFW.*;

import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.input.MouseController;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class FlyCamBehavior extends CameraBehavior {

	private float mouseSensitivity;
	private float speed;
	private boolean invertY;
	private boolean invertX;
	private boolean flip;
	private int upKey;
	private int downKey;
	private int leftKey;
	private int rightKey;
	private int forwardKey;
	private int backwardKey;
	
	/**
	 * 
	 * @since 13.08.2018/0.1.0
	 */
	public FlyCamBehavior() {
		
		this.mouseSensitivity = 0.1F;
		this.speed = 0.05F;
		this.upKey = GLFW_KEY_SPACE;
		this.downKey = GLFW_KEY_LEFT_SHIFT;
		this.forwardKey = GLFW_KEY_W;
		this.backwardKey = GLFW_KEY_S;
		this.rightKey = GLFW_KEY_D;
		this.leftKey = GLFW_KEY_A;
	}
	
	@Override
	void setCamera(Camera camera) {
		
		if(camera == null) {
			
			this.getCamera().getGame().getMouseController().setCursorVisible(true);
		}
		
		super.setCamera(camera);
		
		if(camera != null) {
			
			camera.getGame().getMouseController().setCursorVisible(false);
		}
	}
	
	@Override
	public void update(float delta) {
		
		MouseController mouseController = this.getCamera().getGame().getMouseController();
		Display display = this.getCamera().getGame().getDisplay();
		int middleX = display.getFrameBufferWidth() / 2;
		int middleY = display.getFrameBufferHeight() / 2;
		int deltaX = mouseController.getCursorX() - middleX;
		int deltaY = mouseController.getCursorY() - middleY;
		mouseController.setCursorPosition(middleX, middleY);
		
		this.getCamera().rotate(((this.invertY ? -deltaY : deltaY) * this.mouseSensitivity) * delta, ((this.invertX ? -deltaX : deltaX) * this.mouseSensitivity) * delta, 0.0F);
		
		if(!this.flip) {
			
			Vector3f rotation = this.getCamera().getRotation();
			this.getCamera().setRotation(Math.min(Math.max(rotation.x, -90.0F), 90.0F), rotation.y, rotation.z);
		}
	}
}
