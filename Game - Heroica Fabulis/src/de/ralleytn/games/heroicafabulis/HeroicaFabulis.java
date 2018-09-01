package de.ralleytn.games.heroicafabulis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.EngineException;
import de.ralleytn.engine.caveman.Entity;
import de.ralleytn.engine.caveman.Errors;
import de.ralleytn.engine.caveman.Game;
import de.ralleytn.engine.caveman.Terrain;
import de.ralleytn.engine.caveman.audio.ALBuffer;
import de.ralleytn.engine.caveman.audio.OpenAL;
import de.ralleytn.engine.caveman.audio.Source;
import de.ralleytn.engine.caveman.io.audio.WavAudioReader;
import de.ralleytn.engine.caveman.io.meshes.XMeshReader;
import de.ralleytn.engine.caveman.io.textures.XImgTextureReader;
import de.ralleytn.engine.caveman.rendering.Texture;
import de.ralleytn.engine.caveman.rendering.TextureData;
import de.ralleytn.engine.caveman.rendering.camera.FlyCamBehavior;
import de.ralleytn.engine.caveman.rendering.geom.MeshData;
import de.ralleytn.engine.caveman.rendering.geom.Quad;
import de.ralleytn.engine.caveman.rendering.geom.StaticMesh;
import de.ralleytn.engine.caveman.rendering.light.Light;
import de.ralleytn.engine.caveman.rendering.shader.BasicShaderPipeline;
import de.ralleytn.engine.caveman.rendering.shader.Fog;
import de.ralleytn.engine.caveman.rendering.shader.Material;
import de.ralleytn.engine.caveman.rendering.shader.ShaderPipeline;
import de.ralleytn.engine.caveman.util.MatrixUtil;
import de.ralleytn.engine.caveman.util.MeshUtil;

/**
 * This is the main class in which the game components are assembled and the game is started.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 26.08.2018/0.3.0
 * @since 30.07.2018/0.1.0
 */
public final class HeroicaFabulis extends Game {
	
	private Source source;
	private long time = System.currentTimeMillis();
	
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
		stall.setTranslation(0, 0, -10);
		
		this.populateGrass(shaderPipeline, fog, -200, -200, 400, 400);
		
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
		
		Entity stall2 = stall.copy();
		stall2.setTranslation(10, 0, 0);
		stall2.setRotation(0, 45, 0);
		
		game.getScene().addEntity(stall);
		game.getScene().addEntity(stall2);
		game.getScene().setSun(sun);
		game.getScene().addTerrain(terrain0);
		game.getScene().addTerrain(terrain1);
		game.getScene().addTerrain(terrain2);
		game.getScene().addTerrain(terrain3);
		
		ALBuffer buffer = new ALBuffer();
		buffer.setData(new WavAudioReader().read(new FileInputStream("res/audio/sounds/sample.wav")));
		
		source = new Source();
		source.setBuffer(buffer);
		source.setTranslation(0, 0, 0);
		
			try {
				this.getMusic().setReader(new WavAudioReader(new FileInputStream(new File("res/audio/music/strings3.wav"))));
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.getMusic().setVolume(0.3F);
			this.getMusic().setLooping(true);
			this.getMusic().play();
	}

	@Override
	public void update(float delta) {
		
		this.getDisplay().setTitle(this.getTitle() + " (" + this.getCurrentFPS() + ")");
		OpenAL.getListener().setTranslation(this.getCamera().getTranslation());
		OpenAL.getListener().setOrientation(this.getCamera().getViewMatrix());
		
		if(System.currentTimeMillis() - time >= 3000) {
			
			time = System.currentTimeMillis();
			source.play();
		}
	}
	
	private void populateGrass(ShaderPipeline pipeline, Fog fog, int x, int y, int width, int height) throws IOException {
		
		Material grassMaterial = new Material();
		grassMaterial.setMinBrightness(0.1F);
		TextureData data = new XImgTextureReader().read(new FileInputStream("res/textures/grass2.ximg"));
		data.removeHalfTransparency();
		grassMaterial.setColorMap(new Texture(data, true));
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
		
		for(int i = x; i < x + width; i++) {
			
			for(int j = y; j < y + height; j++) {
				
				Entity grass = new Entity();
				grass.setShaderPipeline(pipeline);
				grass.setMaterial(grassMaterial);
				grass.setMesh(grassMesh);
				grass.setRotation(0, (float)(Math.random() * 359), 0);
				grass.setTranslation((float)(i + Math.random() - 0.2F), 0, (float)(j + Math.random() - 0.2F));
				grass.setRenderDistance(60);
				this.getScene().addEntity(grass);
			}
		}
	}
}
