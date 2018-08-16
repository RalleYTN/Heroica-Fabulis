package de.ralleytn.games.heroicafabulis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import de.ralleytn.games.heroicafabulis.engine.EngineException;
import de.ralleytn.games.heroicafabulis.engine.Entity;
import de.ralleytn.games.heroicafabulis.engine.Errors;
import de.ralleytn.games.heroicafabulis.engine.Game;
import de.ralleytn.games.heroicafabulis.engine.io.DefaultTextureReader;
import de.ralleytn.games.heroicafabulis.engine.localization.Localization;
import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;
import de.ralleytn.games.heroicafabulis.engine.rendering.camera.FlyCamBehavior;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Box;
import de.ralleytn.games.heroicafabulis.engine.rendering.light.Light;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.BasicShaderPipeline;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.Material;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.ShaderPipeline;

/**
 * This is the main class in which the game components are assembled and the game is started.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public final class HeroicaFabulis extends Game {

	/**
	 * @since 30.07.2018/0.1.0
	 */
	public HeroicaFabulis() {
		
		super("Heroica Fabulis v0.1.0", new File("native"));
		
		this.setLocaleDirectory(new File("res/lang"));
		this.setErrLogDirectory(new File("err_logs"));
	}
	
	/**
	 * First method to be called.
	 * @param args the arguments that were used to start the program (should be none)
	 * @since 30.07.2018/0.1.0
	 */
	public static void main(String[] args) {
		
		HeroicaFabulis game = new HeroicaFabulis();
		
		try {

			game.start();
			
		} catch(Exception exception) {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, game.getErrLogDirectory()));
			
		} finally {
			
			game.stop();
			System.exit(1);
		}
	}

	@Override
	public void initialize(Game game) throws EngineException, IOException {
		
		game.getDisplay().setFullscreen(true);
		game.getCamera().setBehavior(new FlyCamBehavior());
		
		System.out.println(Localization.getLocalizedString("TestString"));
		Localization.setLocale("en");
		System.out.println(Localization.getLocalizedString("TestString"));
		
		Texture colorMap = this.loadTexture("test/colorMap");
		Texture specularMap = this.loadTexture("test/specularMap");
		Texture overlay = this.loadTexture("test/overlay");
		ShaderPipeline shaderPipeline = new BasicShaderPipeline(new File("res/shaders"), "basic");
		
		Material material = new Material();
		material.setColorMap(colorMap);
		material.setSpecularMap(specularMap);
		material.setOverlay1(overlay);
		material.setSpecular(true);
		material.setAffectedByLight(true);
		material.setMinBrightness(0.3F);
		
		Entity cube = new Entity() {
			
			@Override
			public void update(float delta) {
				
				this.rotate(0.2F * delta, 0.2F * delta, 0.0F);
			}
		};
		cube.setShaderPipeline(shaderPipeline);
		cube.setMesh(new Box(1, 1, 1));
		cube.setMaterial(material);
		cube.setTranslation(0, 1, -10);
		
		Light sun = new Light();
		sun.setTranslation(0, 10, 0);
		
		game.getScene().addEntity(cube);
		game.getScene().setSun(sun);
	}

	@Override
	public void update(float delta) {
		
		this.getDisplay().setTitle(this.getTitle() + " (" + this.getCurrentFPS() + ")");
	}
	
	private Texture loadTexture(String file) throws IOException {
		
		Texture texture = null;
		
		try(FileInputStream textureInput = new FileInputStream(new File(String.format("res/textures/%s.png", file)))) {
			
			texture = new DefaultTextureReader().read(textureInput);
		}
		
		return texture;
	}
}
