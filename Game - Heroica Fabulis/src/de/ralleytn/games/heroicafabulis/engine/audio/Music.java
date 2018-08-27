package de.ralleytn.games.heroicafabulis.engine.audio;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import de.ralleytn.games.heroicafabulis.engine.Engine;
import de.ralleytn.games.heroicafabulis.engine.Errors;
import de.ralleytn.games.heroicafabulis.engine.io.audio.AudioData;
import de.ralleytn.games.heroicafabulis.engine.io.audio.AudioReader;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 27.08.2018/0.3.0
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
	
	/**
	 * 
	 * @since 26.08.2018/0.3.0
	 */
	public Music() {
		
		this.source = new Source();
		this.play = new Play();
		this.queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY, true);
		this.monitor = new Object();
		
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
	 * @param reader
	 * @since 27.08.2018/0.3.0
	 */
	public void setReader(AudioReader reader) {

		this.reader = reader;
	}
	
	/**
	 * 
	 * @throws IOException
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
	 * 
	 * @throws IOException 
	 * @since 26.08.2018/0.3.0
	 */
	public void play() throws IOException {
		
		if(this.paused) {
			
			this.resume();
		}
		
		this.stop();
		this.rewind();
		this.thread.start();
		this.source.play();
	}
	
	/**
	 * 
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
	 * 
	 * @since 26.08.2018/0.3.0
	 */
	public void pause() {
		
		this.source.pause();
		this.paused = true;
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
	 * @since 27.08.2018/0.3.0
	 */
	public AudioReader getReader() {
		
		return this.reader;
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
			
			boolean end = false;
			
			try {
				
				while(!Thread.interrupted()) {
					
					synchronized(Music.this.monitor) {
						
						int processedCount = Music.this.source.getProcessedBuffersCount();
						
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
										
										if(Music.this.source.getState() == Source.STATE_STOPPED) {
											
											Music.this.source.play();
										}
										
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
