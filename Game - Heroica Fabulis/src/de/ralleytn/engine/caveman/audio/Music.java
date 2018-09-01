package de.ralleytn.engine.caveman.audio;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import de.ralleytn.engine.caveman.Engine;
import de.ralleytn.engine.caveman.Errors;
import de.ralleytn.engine.caveman.Game;
import de.ralleytn.engine.caveman.io.audio.AudioReader;

/**
 * Represents the music player for the game.
 * All of the audio data will be streamed with this class.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 31.08.2018/0.3.0
 * @since 26.08.2018/0.3.0
 */
public class Music {

	private static final int QUEUE_CAPACITY = 16;
	
	private Source source;
	private Thread thread;
	private Play play;
	private AudioReader reader;
	private Queue<ALBuffer> queue;
	private Object monitor;
	private boolean paused;
	private boolean looping;
	
	/**
	 * No other instances of this class should be created.
	 * Use {@link Game#getMusic()} instead.
	 * @since 26.08.2018/0.3.0
	 * @see Game#getMusic()
	 */
	public Music() {
		
		this.source = new Source();
		this.play = new Play();
		this.queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY, true);
		this.monitor = new Object();
		this.createThread();
	}
	
	private final void createThread() {
		
		this.thread = new Thread(this.play);
		this.thread.setUncaughtExceptionHandler((thread, exception) -> {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
			Engine.stop();
		});
		this.thread.setDaemon(true);
	}
	
	/**
	 * Sets the reader from which the audio data comes from.
	 * @param reader the reader
	 * @since 27.08.2018/0.3.0
	 */
	public void setReader(AudioReader reader) {

		if(this.reader != null && reader != this.reader) {
			
			try {
				
				this.reader.close();
				
			} catch(IOException exception) {
				
				// SHOULD NEVER HAPPEN!
				throw new RuntimeException(exception);
			}
		}
		
		this.reader = reader;
	}
	
	/**
	 * Sets the looping flag.
	 * @param looping {@code true} = audio will restart from the beginning when the end is reached, {@code false} = audio stops playing if the end is reached
	 * @since 29.08.2018/0.3.0
	 */
	public void setLooping(boolean looping) {
		
		this.looping = looping;
	}
	
	/**
	 * Rewinds the audio.
	 * @throws IOException if an I/O error occurs
	 * @since 27.08.2018/0.3.0
	 */
	public void rewind() throws IOException {
		
		this.stop();
		this.reader.reset();
		
		for(int index = 0; index < QUEUE_CAPACITY; index++) {
			
			this.queue.offer(new ALBuffer(this.reader.nextChunk()));
		}
		
		this.source.queueBuffers(this.queue.toArray(new ALBuffer[QUEUE_CAPACITY]));
	}
	
	/**
	 * Sets the volume.
	 * @param gain {@code 1.0F} = 100%, {@code 0.0F} = 0%
	 * @since 28.08.2018/0.3.0
	 */
	public void setVolume(float gain) {
		
		this.source.setGain(gain);
	}
	
	/**
	 * Plays the audio from the beginning.
	 * @throws IOException if an I/O error occurs
	 * @since 26.08.2018/0.3.0
	 */
	public void play() throws IOException {
		
		if(this.paused) {
			
			this.resume();
		}
		
		this.rewind();
		this.thread.start();
		this.source.play();
	}
	
	/**
	 * Resumes playing after being paused.
	 * @since 27.08.2018/0.3.0
	 */
	public void resume() {
		
		this.source.play();
		
		synchronized(this.monitor) {
			
			this.paused = false;
			this.monitor.notifyAll();
		}
	}
	
	/**
	 * Pauses the audio.
	 * @since 26.08.2018/0.3.0
	 */
	public void pause() {
		
		this.source.pause();
		this.paused = true;
	}
	
	/**
	 * Stops the audio.
	 * @since 26.08.2018/0.3.0
	 */
	public void stop() {
		
		this.source.stop();
		this.source.unqueueBuffers();
		this.thread.interrupt();
		this.createThread();
	}
	
	/**
	 * @return the current reader
	 * @since 27.08.2018/0.3.0
	 */
	public AudioReader getReader() {
		
		return this.reader;
	}
	
	/**
	 * @return the current volume
	 * @since 29.08.2018/0.3.0
	 */
	public float getVolume() {
		
		return this.source.getGain();
	}
	
	/**
	 * @return the current value of the looping flag
	 * @since 29.08.2018/0.3.0
	 */
	public boolean isLooping() {
		
		return this.looping;
	}
	
	/**
	 * Represents the play action.
	 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
	 * @version 29.08.2018/0.3.0
	 * @since 26.08.2018/0.3.0
	 */
	private final class Play implements Runnable {

		@Override
		public void run() {
			
			boolean end = false;
			
			try {
				
				while(!Thread.interrupted()) {
					
					synchronized(Music.this.monitor) {
						
						int processedCount = Music.this.source.getProcessedBuffersCount();
						
						if(end && processedCount == 0) {
							
							Music.this.stop();
						}
						
						while(processedCount > 0) {
							
							try {
								
								ALBuffer buffer = Music.this.queue.poll();
								Music.this.source.unqueueBuffers(new ALBuffer[] {buffer});
								
								if(!end) {
									
									AudioData data = Music.this.reader.nextChunk();
									
									if(data != null) {
										
										buffer.setData(data);
										Music.this.source.queueBuffer(buffer);
										Music.this.queue.offer(buffer);
										
									} else {
										
										end = true;
									}
								}
							
							} catch(IOException exception) {
								
								Errors.print(exception);
								Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
							}
							
							processedCount--;
						}
						
						Thread.sleep(10);
						
						if(Music.this.paused) {
							
							Music.this.monitor.wait();
						}
					}
				}
				
			} catch(InterruptedException exception) {
				
				// DO NOTHING!
			}
		}
	}
}
