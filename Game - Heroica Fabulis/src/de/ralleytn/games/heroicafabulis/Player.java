package de.ralleytn.games.heroicafabulis;

import de.ralleytn.engine.caveman.Entity;
import de.ralleytn.engine.caveman.rendering.camera.Camera;
import de.ralleytn.engine.caveman.rendering.geom.AxisAlignedBox;
import de.ralleytn.engine.caveman.rendering.geom.StaticMesh;

public class Player extends Entity {

	private Camera camera;
	private Entity entity;
	
	public Player(Camera camera, Entity entity) {
		
		this.camera = camera;
		this.entity = entity;
		this.setMesh(new StaticMesh(new AxisAlignedBox(-0.5F, -0.5F, -0.5F, 1, 1, 1).createMeshData()));
		this.setRendering(false);
	}
	
	@Override
	public void update(float delta) {
		
		if(this.entity.getOBB().intersects(this.getOBB())) {
			
			System.out.println("Collision");
		}
		
		this.setTranslation(this.camera.getTranslation());
		this.setRotation(this.camera.getRotation());
	}
}
