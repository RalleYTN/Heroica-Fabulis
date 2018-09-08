package de.ralleytn.games.heroicafabulis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import de.ralleytn.engine.caveman.EngineException;
import de.ralleytn.engine.caveman.Entity;
import de.ralleytn.engine.caveman.Errors;
import de.ralleytn.engine.caveman.Game;
import de.ralleytn.engine.caveman.Terrain;
import de.ralleytn.engine.caveman.audio.OpenAL;
import de.ralleytn.engine.caveman.io.meshes.XMeshReader;
import de.ralleytn.engine.caveman.io.textures.XImgTextureReader;
import de.ralleytn.engine.caveman.rendering.Texture;
import de.ralleytn.engine.caveman.rendering.camera.FlyCamBehavior;
import de.ralleytn.engine.caveman.rendering.geom.StaticMesh;
import de.ralleytn.engine.caveman.rendering.light.Light;
import de.ralleytn.engine.caveman.rendering.shader.BasicShaderPipeline;
import de.ralleytn.engine.caveman.rendering.shader.Fog;
import de.ralleytn.engine.caveman.rendering.shader.Material;
import de.ralleytn.engine.caveman.rendering.shader.ShaderPipeline;

/**
 * This is the main class in which the game components are assembled and the game is started.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 26.08.2018/0.3.0
 * @since 30.07.2018/0.1.0
 */
public final class HeroicaFabulis extends Game {
	
	/**
	 * @throws IOException 
	 * @since 30.07.2018/0.1.0
	 */
	public HeroicaFabulis() throws IOException {
		
		super("Heroica Fabulis v0.1.0", new File("options.txt"), "de/ralleytn/games/heroicafabulis/defaults.txt");
	}
	
	/**
	 * First method to be called.
	 * @param args the arguments that were used to start the program (should be none)
	 * @since 30.07.2018/0.1.0
	 */
	public static void main(String[] args) {
		
		HeroicaFabulis game = null;
		
		try {

			game = new HeroicaFabulis();
			game.start();
			
		} catch(Exception exception) {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, game.getErrLogDirectory()));
			
		} finally {
			
			if(game != null) {
				
				game.stop();
			}
			
			System.exit(1);
		}
	}

	@Override
	public void initialize(Game game) throws EngineException, IOException {
		
		FlyCamBehavior behavior = new FlyCamBehavior();
		behavior.setSpeed(0.02F);
		game.getCamera().setBehavior(behavior);
		
		this.getCamera().setTranslation(0, 1, 0);

		ShaderPipeline shaderPipeline = new BasicShaderPipeline(new File("res/shaders"), "basic");
		ShaderPipeline terrainShaderPipeline = new BasicShaderPipeline(new File("res/shaders"), "terrain");

		Fog fog = new Fog();
		
		Material playerMaterial = new Material();
		playerMaterial.setMinBrightness(0.1F);
		playerMaterial.setAffectedByLight(true);
		
		Material stallMaterial = new Material();
		stallMaterial.setMinBrightness(0.1F);
		stallMaterial.setColorMap(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/stall.ximg")), true));
		stallMaterial.setAffectedByLight(true);
		stallMaterial.setFog(fog);
		
		Material terrainMaterial = new Material();
		terrainMaterial.setMinBrightness(0.3F);
		terrainMaterial.setColorMap(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/grass.ximg")), true));
		terrainMaterial.setOverlay1(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/street.ximg")), true));
		terrainMaterial.setBlendMap(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/blendMap1.ximg"))));
		terrainMaterial.setColorMapTiling(Terrain.VERTEX_COUNT);
		terrainMaterial.setOverlay1Tiling(30);
		terrainMaterial.setAffectedByLight(true);
		terrainMaterial.setFog(fog);

		Entity stall = new Entity();
		stall.setShaderPipeline(shaderPipeline);
		stall.setMaterial(stallMaterial);
		stall.setMesh(new StaticMesh(new XMeshReader().read(new FileInputStream("res/meshes/stall.xmesh"))));
		stall.setTranslation(-50, 0, -50);
		stall.setRotation(0, 180, 0);

		Terrain terrain0 = new Terrain(-1, -1);
		terrain0.setMaterial(terrainMaterial);
		terrain0.setShaderPipeline(terrainShaderPipeline);

		Light sun = new Light();
		sun.setTranslation(0, 10, 0);

		game.getScene().addEntity(stall);
		game.getScene().setSun(sun);
		game.getScene().addTerrain(terrain0);
	}

	@Override
	public void update(float delta) {
		
		this.getDisplay().setTitle(this.getTitle() + " (" + this.getCurrentFPS() + ")");
		OpenAL.getListener().setTranslation(this.getCamera().getTranslation());
		OpenAL.getListener().setOrientation(this.getCamera().getViewMatrix());
	}
}
