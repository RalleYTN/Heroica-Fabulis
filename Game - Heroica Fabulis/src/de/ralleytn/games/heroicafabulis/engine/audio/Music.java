package de.ralleytn.games.heroicafabulis.engine.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.ralleytn.games.heroicafabulis.engine.Engine;
import de.ralleytn.games.heroicafabulis.engine.Errors;
import de.ralleytn.games.heroicafabulis.engine.io.audio.WavAudioReader;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 27.08.2018/0.3.0
 * @since 26.08.2018/0.3.0
 */
public class Music {

	private static final int QUEUE_CAPACITY = 3;
	
	private Source source;
	private File file;
	private Thread thread;
	private Play play;
	private WavAudioReader reader;
	private Queue<ALBuffer> queue;
	
	/**
	 * 
	 * @since 26.08.2018/0.3.0
	 */
	public Music() {
		
		this.source = new Source();
		this.play = new Play();
		this.queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY, true);
		
		this.thread = new Thread(this.play);
		this.thread.setUncaughtExceptionHandler((thread, exception) -> {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
			Engine.stop();
		});
		this.thread.setDaemon(true);
	}
	
	/**
	 * 
	 * @param file
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @since 26.08.2018/0.3.0
	 */
	public void setAudioFile(File file) throws UnsupportedAudioFileException, IOException {
		
		this.source.stop();
		this.source.unqueueBuffers();
		this.file = file;
		this.reader = new WavAudioReader(new FileInputStream(file));
		this.source.setLooping(false); // if this value is true, the number of processed buffers is always 0
	}
	
	/**
	 * 
	 * @throws IOException
	 * @since 27.08.2018/0.3.0
	 */
	public void rewind() throws IOException {
		
		this.reader.reset();
		this.queue.offer(new ALBuffer(this.reader.nextChunk()));
		this.queue.offer(new ALBuffer(this.reader.nextChunk()));
		this.queue.offer(new ALBuffer(this.reader.nextChunk()));
		this.source.queueBuffers(this.queue.toArray(new ALBuffer[QUEUE_CAPACITY]));
	}
	
	/**
	 * 
	 * @throws IOException 
	 * @since 26.08.2018/0.3.0
	 */
	public void play() throws IOException {
		
		this.stop();
		this.rewind();
		this.source.play();
		this.thread.start();
	}
	
	/**
	 * 
	 * @since 27.08.2018/0.3.0
	 */
	public void resume() {
		
		this.source.play();
	}
	
	/**
	 * 
	 * @since 26.08.2018/0.3.0
	 */
	public void pause() {
		
		this.source.pause();
	}
	
	/**
	 * 
	 * @since 26.08.2018/0.3.0
	 */
	public void stop() {
		
		this.source.stop();
		this.source.unqueueBuffers();
		this.thread.interrupt();
	}
	
	/**
	 * 
	 * @return
	 * @since 26.08.2018/0.3.0
	 */
	public Source getSource() {
		
		return this.source;
	}
	
	/**
	 * 
	 * @return
	 * @since 26.08.2018/0.3.0
	 */
	public File getFile() {
		
		return this.file;
	}
	
	/**
	 * 
	 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
	 * @version 26.08.2018/0.3.0
	 * @since 26.08.2018/0.3.0
	 */
	private final class Play implements Runnable {

		@Override
		public void run() {
			
			try {
				
				while(!Thread.interrupted()) {
					
					int processedCount = Music.this.source.getProcessedBuffersCount();
					
					if(processedCount > 0) {
						
						
					}
					
					Thread.sleep(10);
				}
				
			} catch(InterruptedException exception) {
				
				// DO NOTHING!
			}
		}
	}
}
