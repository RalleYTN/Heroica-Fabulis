package de.ralleytn.games.heroicafabulis.engine.audio;

import java.io.File;

import de.ralleytn.games.heroicafabulis.engine.Engine;
import de.ralleytn.games.heroicafabulis.engine.Errors;

public class Music {

	private Source source;
	private File file;
	private Thread thread;
	private Play play;
	
	public Music() {
		
		this.source = new Source();
		this.play = new Play();
		
		this.thread = new Thread(this.play);
		this.thread.setUncaughtExceptionHandler((thread, exception) -> {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
			Engine.stop();
		});
		this.thread.setDaemon(true);
	}
	
	public void setAudioFile(File file) {
		
		this.source.stop();
		this.source.unqueueBuffers();
		this.file = file;
	}
	
	public void play() {
		
		this.source.play();
		this.thread.start();
	}
	
	public void pause() {
		
		this.source.pause();
	}
	
	public void stop() {
		
		this.source.stop();
		this.source.unqueueBuffers();
		this.thread.interrupt();
	}
	
	public Source getSource() {
		
		return this.source;
	}
	
	public File getFile() {
		
		return this.file;
	}
	
	private final class Play implements Runnable {

		@Override
		public void run() {
			
			try {
				
				while(!Thread.interrupted()) {
					
					Thread.sleep(100);
				}
				
			} catch(InterruptedException exception) {
				
				// DO NOTHING!
			}
		}
	}
}
