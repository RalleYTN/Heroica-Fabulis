package de.ralleytn.games.heroicafabulis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.EngineException;
import de.ralleytn.games.heroicafabulis.engine.Entity;
import de.ralleytn.games.heroicafabulis.engine.Errors;
import de.ralleytn.games.heroicafabulis.engine.Game;
import de.ralleytn.games.heroicafabulis.engine.Terrain;
import de.ralleytn.games.heroicafabulis.engine.audio.OpenAL;
import de.ralleytn.games.heroicafabulis.engine.io.meshes.MeshData;
import de.ralleytn.games.heroicafabulis.engine.io.meshes.XMeshReader;
import de.ralleytn.games.heroicafabulis.engine.io.textures.XImgTextureReader;
import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;
import de.ralleytn.games.heroicafabulis.engine.rendering.camera.FlyCamBehavior;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Quad;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.StaticMesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.light.Light;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.BasicShaderPipeline;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.Fog;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.Material;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.ShaderPipeline;
import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;
import de.ralleytn.games.heroicafabulis.engine.util.MeshUtil;

/**
 * This is the main class in which the game components are assembled and the game is started.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
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
		behavior.setSpeed(0.002F);
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
		stallMaterial.setColorMap(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/stall.ximg"))));
		stallMaterial.setAffectedByLight(true);
		stallMaterial.setFog(fog);
		
		Material terrainMaterial = new Material();
		terrainMaterial.setMinBrightness(0.3F);
		terrainMaterial.setColorMap(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/grass.ximg"))));
		terrainMaterial.setOverlay1(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/street.ximg"))));
		terrainMaterial.setBlendMap(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/blendMap1.ximg"))));
		terrainMaterial.setColorMapTiling(Terrain.VERTEX_COUNT);
		terrainMaterial.setOverlay1Tiling(30);
		terrainMaterial.setAffectedByLight(true);
		terrainMaterial.setFog(fog);
		
		Material grassMaterial = new Material();
		grassMaterial.setMinBrightness(0.1F);
		grassMaterial.setColorMap(new Texture(new XImgTextureReader().read(new FileInputStream("res/textures/grass2.ximg"))));
		grassMaterial.setAffectedByLight(true);
		grassMaterial.setTransparent(true);
		grassMaterial.setUpwardsNormals(true);
		grassMaterial.setFog(fog);
		
		MeshData grassMeshData = Quad.generateMeshData(
			new Vector3f(0.0F, 1.0F, 0.0F),
			new Vector3f(0.0F, 0.0F, 0.0F),
			new Vector3f(1.0F, 0.0F, 0.0F),
			new Vector3f(1.0F, 1.0F, 0.0F)
		);
		
		StaticMesh grassMesh = new StaticMesh(MeshUtil.mergeLazy(Arrays.asList(grassMeshData, grassMeshData), Arrays.asList(
			MatrixUtil.createTransformationMatrx(
				new Vector3f(0.0F, 0.0F, 0.0F), 
				new Vector3f(0.0F, 0.0F, 0.0F),
				new Vector3f(1.0F, 1.0F, 1.0F)
			),
			MatrixUtil.createTransformationMatrx(
				new Vector3f(0.5F, 0.0F, 0.5F),
				new Vector3f(0.0F, 90.0F, 0.0F),
				new Vector3f(1.0F, 1.0F, 1.0F)
			)
		)));
		grassMesh.setCullMode(StaticMesh.CULLING_DISABLED);
		
		Entity grass = new Entity();
		grass.setShaderPipeline(shaderPipeline);
		grass.setMaterial(grassMaterial);
		grass.setMesh(grassMesh);
		grass.setTranslation(0, 0, 1);
		
		Entity grass2 = grass.copy();
		grass2.rotate(0, 30, 0);
		grass2.translate(1F, 0, 1F);

		Entity stall = new Entity();
		stall.setShaderPipeline(shaderPipeline);
		stall.setMaterial(stallMaterial);
		stall.setMesh(new StaticMesh(new XMeshReader().read(new FileInputStream("res/meshes/stall.xmesh"))));
		stall.setTranslation(0, 0, -10);
		
		Terrain terrain0 = new Terrain(-1, -1);
		terrain0.setMaterial(terrainMaterial);
		terrain0.setShaderPipeline(terrainShaderPipeline);
		
		Terrain terrain1 = new Terrain(0, -1);
		terrain1.setMaterial(terrainMaterial);
		terrain1.setShaderPipeline(terrainShaderPipeline);
		
		Terrain terrain2 = new Terrain(-1, 0);
		terrain2.setMaterial(terrainMaterial);
		terrain2.setShaderPipeline(terrainShaderPipeline);
		
		Terrain terrain3 = new Terrain(0, 0);
		terrain3.setMaterial(terrainMaterial);
		terrain3.setShaderPipeline(terrainShaderPipeline);

		Light sun = new Light();
		sun.setTranslation(0, 10, 0);
		
		game.getScene().addEntity(stall);
		game.getScene().addEntity(grass);
		game.getScene().addEntity(grass2);
		game.getScene().setSun(sun);
		game.getScene().addTerrain(terrain0);
		game.getScene().addTerrain(terrain1);
		game.getScene().addTerrain(terrain2);
		game.getScene().addTerrain(terrain3);
	}

	@Override
	public void update(float delta) {
		
		this.getDisplay().setTitle(this.getTitle() + " (" + this.getCurrentFPS() + ")");
		OpenAL.getListener().setTranslation(this.getCamera().getTranslation());
	}
}
