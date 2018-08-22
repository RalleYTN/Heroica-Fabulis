package de.ralleytn.games.heroicafabulis.engine;

import java.util.ArrayList;
import java.util.List;

import de.ralleytn.games.heroicafabulis.engine.rendering.Graphics3D;
import de.ralleytn.games.heroicafabulis.engine.rendering.Renderable;
import de.ralleytn.games.heroicafabulis.engine.rendering.light.Light;

/**
 * Represents the currently processed universe.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 31.07.2018/0.1.0
 */
public class Scene implements Renderable, Updatable {

	private List<RenderableObject> objects;
	private Light sun;
	
	/**
	 * @since 05.08.2018
	 */
	public Scene() {
		
		this.objects = new ArrayList<>();
	}
	
	/**
	 * Adds an entity to the scene.
	 * @param entity the entity
	 * @since 11.08.2018/0.1.0
	 */
	public void addEntity(Entity entity) {
		
		this.objects.add(entity);
	}
	
	/**
	 * Adds a terrain tile.
	 * @param terrain the terrain tile
	 * @since 21.08.2018/0.2.0
	 */
	public void addTerrain(Terrain terrain) {
		
		this.objects.add(terrain);
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
		
		this.objects.remove(entity);
	}
	
	/**
	 * Removes a terrain tile.
	 * @param terrain the terrain ntile
	 * @since 21.08.2018/0.2.0
	 */
	public void removeTerrain(Terrain terrain) {
		
		this.objects.remove(terrain);
	}
	
	/**
	 * Removes everything from the scene.
	 * @since 05.08.2018/0.1.0
	 */
	public void clear() {
		
		this.objects.clear();
		this.sun = null;
	}
	
	@Override
	public void update(float delta) {
		
		for(RenderableObject object : this.objects) {

			if(object instanceof Entity) {
				
				((Entity)object).update(delta);
			}
		}
	}

	@Override
	public void render(Graphics3D graphics) {
		
		for(RenderableObject object : this.objects) {
			
			if(object.isRendering()) {
				
				graphics.renderObject(object);
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
