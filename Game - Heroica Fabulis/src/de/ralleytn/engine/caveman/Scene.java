package de.ralleytn.engine.caveman;

import java.util.ArrayList;
import java.util.List;

import de.ralleytn.engine.caveman.rendering.Graphics3D;
import de.ralleytn.engine.caveman.rendering.Renderable;
import de.ralleytn.engine.caveman.rendering.light.Light;
import de.ralleytn.engine.caveman.util.VectorUtil;

/**
 * Represents the currently processed universe.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 29.08.2018/0.3.0
 * @since 31.07.2018/0.1.0
 */
public class Scene implements Renderable, Updatable {

	private final Game game;
	
	private List<Entity> entities;
	private List<Terrain> terrain;
	private Light sun;
	
	/**
	 * @param game the instance of {@linkplain Game} this scene belongs to
	 * @since 05.08.2018
	 */
	public Scene(Game game) {
		
		this.game = game;
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
	 * Adds a terrain tile.
	 * @param terrain the terrain tile
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
	 * Removes a terrain tile.
	 * @param terrain the terrain tile
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
			
			if(entity.isRendering() && VectorUtil.getDistance(this.game.getCamera().getTranslation(), entity.getTranslation()) <= entity.getRenderDistance()) {
				
				graphics.renderEntity(entity);
			}
		}
		
		for(Terrain terrain : this.terrain) {
			
			if(terrain.isRendering()) {
				
				graphics.renderTerrain(terrain);
			}
		}
	}
	
	/**
	 * @return the light source
	 * @since 11.08.2018/0.1.0
	 */
	public Light getSun() {
		
		return this.sun;
	}
}
