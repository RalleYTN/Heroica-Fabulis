package de.ralleytn.games.heroicafabulis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import de.ralleytn.games.heroicafabulis.engine.EngineException;
import de.ralleytn.games.heroicafabulis.engine.Entity;
import de.ralleytn.games.heroicafabulis.engine.Errors;
import de.ralleytn.games.heroicafabulis.engine.Game;
import de.ralleytn.games.heroicafabulis.engine.audio.ALBuffer;
import de.ralleytn.games.heroicafabulis.engine.audio.OpenAL;
import de.ralleytn.games.heroicafabulis.engine.audio.Source;
import de.ralleytn.games.heroicafabulis.engine.io.audio.WavAudioReader;
import de.ralleytn.games.heroicafabulis.engine.io.meshes.XMeshReader;
import de.ralleytn.games.heroicafabulis.engine.io.textures.png.PngReader;
import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;
import de.ralleytn.games.heroicafabulis.engine.rendering.camera.FlyCamBehavior;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Box;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Mesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.StaticMesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.light.Light;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.BasicShaderPipeline;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.Material;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.ShaderPipeline;

/**
 * This is the main class in which the game components are assembled and the game is started.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
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
		
		game.getCamera().setBehavior(new FlyCamBehavior());

		ShaderPipeline shaderPipeline = new BasicShaderPipeline(new File("res/shaders"), "basic");
		
		Texture colorMap = new Texture(new PngReader().read(new FileInputStream("res/textures/stall.png")));
		
		Material material = new Material();
		material.setMinBrightness(0.1F);
		material.setColorMap(colorMap);
		material.setAffectedByLight(true);
		
		Mesh mesh = new StaticMesh(new XMeshReader().read(new FileInputStream("res/meshes/stall.xmesh")));
		
		Entity stall = new Entity();
		stall.setShaderPipeline(shaderPipeline);
		stall.setMaterial(material);
		stall.setMesh(mesh);
		stall.setTranslation(0, 0, -10);
		
		Entity cube = new Entity();
		cube.setTranslation(0,0, 10);
		cube.setMesh(new Box(1, 1, 1));
		Material material2 = new Material();
		material2.setMinBrightness(0.1F);
		material2.setColorMap(new Texture(new PngReader().read(new FileInputStream("res/textures/test.png"))));
		material2.setAffectedByLight(true);
		cube.setShaderPipeline(shaderPipeline);
		cube.setMaterial(material2);
		
		Source source = new Source();
		source.setBuffer(new ALBuffer(new WavAudioReader().read(new FileInputStream("res/audio/sounds/sample.wav"))));
		source.setRelativeToListener(true);
		source.setReferenceDistance(10.0F);
		
		Light sun = new Light();
		sun.setTranslation(0, 10, 0);
		
		game.getScene().addEntity(stall);
		game.getScene().setSun(sun);
		game.getScene().addEntity(cube);
	}

	@Override
	public void update(float delta) {
		
		this.getDisplay().setTitle(this.getTitle() + " (" + this.getCurrentFPS() + ")");
		OpenAL.getListener().setTranslation(this.getCamera().getTranslation());
	}
}
