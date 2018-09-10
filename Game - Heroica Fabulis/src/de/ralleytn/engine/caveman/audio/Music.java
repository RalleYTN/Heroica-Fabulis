package de.ralleytn.engine.caveman.audio;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import de.ralleytn.engine.caveman.Engine;
import de.ralleytn.engine.caveman.Errors;
import de.ralleytn.engine.caveman.Game;
import de.ralleytn.engine.caveman.Updatable;
import de.ralleytn.engine.caveman.io.audio.AudioReader;

/**
 * Represents the music player for the game.
 * All of the audio data will be streamed with this class.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 10.09.2018/0.4.0
 * @since 26.08.2018/0.3.0
 */
public class Music implements Updatable {

	private static final int QUEUE_CAPACITY = 16;
	
	private Source source;
	private AudioReader reader;
	private Queue<ALBuffer> queue;
	private boolean playing;
	private boolean looping;
	private boolean reachedEnd;
	
	/**
	 * No other instances of this class should be created.
	 * Use {@link Game#getMusic()} instead.
	 * @since 26.08.2018/0.3.0
	 * @see Game#getMusic()
	 */
	public Music() {
		
		this.source = new Source();
		this.queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY, true);
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
		this.reachedEnd = false;
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
		
		boolean playing = this.playing;
		this.stop();
		this.reader.reset();
		this.reachedEnd = false;
		
		for(int index = 0; index < QUEUE_CAPACITY; index++) {
			
			this.queue.offer(new ALBuffer(this.reader.nextChunk()));
		}
		
		this.source.queueBuffers(this.queue.toArray(new ALBuffer[QUEUE_CAPACITY]));
		this.playing = playing;
		
		if(playing) {
			
			this.source.play();
		}
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
		
		this.rewind();
		this.source.play();
		this.playing = true;
	}
	
	/**
	 * Resumes playing after being paused.
	 * @since 27.08.2018/0.3.0
	 */
	public void resume() {
		
		this.source.play();
		this.playing = true;
	}
	
	/**
	 * Pauses the audio.
	 * @since 26.08.2018/0.3.0
	 */
	public void pause() {
		
		this.source.pause();
		this.playing = false;
	}
	
	/**
	 * Stops the audio.
	 * @since 26.08.2018/0.3.0
	 */
	public void stop() {
		
		this.source.stop();
		this.source.unqueueBuffers();
		this.playing = false;
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

	@Override
	public void update(float delta) {
		
		try {
		
			if(this.playing) {
				
				int processedCount = Music.this.source.getProcessedBuffersCount();
				
				if(this.reachedEnd && processedCount == 0 && this.queue.size() == 0) {
					
					if(this.looping) {
	
						this.rewind();
					
					} else {
						
						this.stop();
					}
					
				}
				
				while(processedCount > 0) {
					
					ALBuffer buffer = this.queue.poll();
					this.source.unqueueBuffers(new ALBuffer[] {buffer});
					
					if(!this.reachedEnd) {
						
						AudioData data = this.reader.nextChunk();
						
						if(data != null) {
							
							buffer.setData(data);
							this.source.queueBuffer(buffer);
							this.queue.offer(buffer);
							
						} else {
							
							this.reachedEnd = true;
						}
					}
					
					processedCount--;
				}
				
				if(this.source.getState() == Source.STATE_STOPPED) {
					
					this.source.play();
				}
			}
			
		} catch(IOException exception) {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
		}
	}
}
