package de.ralleytn.games.heroicafabulis.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ralleytn.games.heroicafabulis.engine.rendering.Graphics3D;
import de.ralleytn.games.heroicafabulis.engine.rendering.Renderable;
import de.ralleytn.games.heroicafabulis.engine.rendering.light.Light;

/**
 * Represents the currently processed universe.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
 * @since 31.07.2018/0.1.0
 */
public class Scene implements Renderable, Updatable {

	private List<Entity> entities;
	private List<Terrain> terrain;
	private Light sun;
	
	/**
	 * @since 05.08.2018
	 */
	public Scene() {
		
		this.entities = new ArrayList<>();
		this.terrain = new ArrayList<>();
	}
	
	/**
	 * Adds an entity to the scene.
	 * @param entity the entity
	 * @since 11.08.2018/0.1.0
	 */
	public void addEntity(Entity entity) {
		
		this.entities.add(entity);
	}
	
	/**
	 * 
	 * @param terrain
	 * @since 21.08.2018/0.2.0
	 */
	public void addTerrain(Terrain terrain) {
		
		this.terrain.add(terrain);
	}
	
	/**
	 * Temporary method to add light to the scene until multiple light sources are a thing.
	 * @param sun the light source
	 * @since 11.08.2018/0.1.0
	 */
	public void setSun(Light sun) {
		
		this.sun = sun;
	}
	
	/**
	 * Removes an entity from the scene.
	 * @param entity the entity
	 * @since 11.08.2018/0.1.0
	 */
	public void removeEntity(Entity entity) {
		
		this.entities.remove(entity);
	}
	
	/**
	 * 
	 * @param terrain
	 * @since 21.08.2018/0.2.0
	 */
	public void removeTerrain(Terrain terrain) {
		
		this.terrain.remove(terrain);
	}
	
	/**
	 * Removes everything from the scene.
	 * @since 05.08.2018/0.1.0
	 */
	public void clear() {
		
		this.entities.clear();
		this.terrain.clear();
		this.sun = null;
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
		
		graphics.renderTerrain(this.terrain);
	}
	
	/**
	 * @return an unmodifiable list of the entities in the scene
	 * @since 11.08.2018/0.1.0
	 */
	public List<Entity> getEntities() {
		
		return Collections.unmodifiableList(this.entities);
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public List<Terrain> getTerrain() {
		
		return Collections.unmodifiableList(this.terrain);
	}
	
	/**
	 * @return the light source
	 * @since 11.08.2018/0.1.0
	 */
	public Light getSun() {
		
		return this.sun;
	}
}
