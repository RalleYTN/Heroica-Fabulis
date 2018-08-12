package de.ralleytn.games.heroicafabulis.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ralleytn.games.heroicafabulis.engine.rendering.Graphics3D;
import de.ralleytn.games.heroicafabulis.engine.rendering.Light;

/**
 * Represents the currently processed universe.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 31.07.2018/0.1.0
 */
public class Scene implements Renderable, Updatable {

	private List<Entity> entities;
	private Light sun;
	
	/**
	 * 
	 * @since 05.08.2018
	 */
	Scene() {
		
		this.entities = new ArrayList<>();
	}
	
	/**
	 * 
	 * @param entity
	 * @since 11.08.2018/0.1.0
	 */
	public void addEntity(Entity entity) {
		
		this.entities.add(entity);
	}
	
	/**
	 * 
	 * @param sun
	 * @since 11.08.2018/0.1.0
	 */
	public void setSun(Light sun) {
		
		this.sun = sun;
	}
	
	/**
	 * 
	 * @param entity
	 * @since 11.08.2018/0.1.0
	 */
	public void removeEntity(Entity entity) {
		
		this.entities.remove(entity);
	}
	
	/**
	 * @since 05.08.2018/0.1.0
	 */
	public void clear() {
		
		this.entities.clear();
	}
	
	@Override
	public void update(float delta) {
		
		for(Entity entity : this.entities) {

			entity.update(delta);
		}
	}

	@Override
	public void render(Graphics3D graphics) {
		
		for(Entity entity : this.entities) {
			
			if(entity.isRendering()) {
				
				graphics.renderEntity(entity);
			}
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public List<Entity> getEntities() {
		
		return Collections.unmodifiableList(this.entities);
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public Light getSun() {
		
		return this.sun;
	}
}
