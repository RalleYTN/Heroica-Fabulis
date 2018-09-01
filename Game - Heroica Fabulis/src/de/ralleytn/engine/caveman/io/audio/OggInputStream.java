package de.ralleytn.engine.caveman.io.audio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.BufferUtils;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

/**
 * {@linkplain InputStream} for reading Vorbis audio data.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 28.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public class OggInputStream extends InputStream {

	private int conversionSize;
	private byte[] conversionBuffer;
	private InputStream input;
	private Info oggInfo;
	private boolean endOfStream;
	private SyncState syncState;
	private StreamState streamState;
	private Page page;
	private Packet packet;
	private Comment comment;
	private DspState dspState;
	private Block vorbisBlock;
	private byte[] buffer;
	private int bytes = 0;
	private boolean bigEndian;
	private boolean endOfBitStream;
	private boolean initialized;
	private int readIndex;
	private ByteBuffer pcmBuffer;

	/**
	 * @param input the {@linkplain InputStream} that should be wrapped
	 * @throws IOException if an I/O error occurred
	 * @since 17.08.2018/0.2.0
	 */
	public OggInputStream(InputStream input) throws IOException {
		
		this.bigEndian = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
		this.oggInfo = new Info();
		this.input = input;
		this.mark(0);
		this.reset();
		this.syncState.init();
		this.readPCM();
	}
	
	@Override
	public synchronized void mark(int readlimit) {
		
		this.input.mark(readlimit);
	}
	
	@Override
	public boolean markSupported() {
		
		return true;
	}
	
	@Override
	public synchronized void reset() throws IOException {
		
		this.conversionSize = 4096 * 4;
		this.conversionBuffer = new byte[this.conversionSize];
		this.endOfStream = false;
		this.syncState = new SyncState();
		this.streamState = new StreamState();
		this.page = new Page();
		this.packet = new Packet();
		this.comment = new Comment();
		this.dspState = new DspState();
		this.vorbisBlock = new Block(this.dspState);
		this.endOfBitStream = true;
		this.initialized = false;
		this.pcmBuffer = BufferUtils.createByteBuffer(4096 * 500);
		this.input.reset();
		this.syncState.init();
		this.readPCM();
	}
		
	@Override
	public int available() {
		
		return this.endOfStream ? 0 : 1;
	}
	
	/**
	 * Reads page and packet.
	 * @return {@code true} if the method finished without errors, else {@code false}
	 * @throws IOException if an I/O error occurred
	 * @since 17.08.2018/0.2.0
	 */
	private boolean getPageAndPacket() throws IOException {
		
		int index = this.syncState.buffer(4096);
		this.buffer = this.syncState.data;
		
		if(this.buffer == null) {
			
			this.endOfStream = true;
			return false;
		}
		
		try {
			
			this.bytes = this.input.read(this.buffer, index, 4096);
			
		} catch(Exception exception) {
			
			this.endOfStream = true;
			throw new IOException(exception);
		}
		
		this.syncState.wrote(this.bytes);

		if(this.syncState.pageout(this.page) != 1) {
			
			if(this.bytes < 4096) {
				
				return false;
			}

			this.endOfStream = true;
			throw new IOException("Input does not appear to be an Ogg bitstream!");
		}

		this.streamState.init(this.page.serialno());
		this.oggInfo.init();
		this.comment.init();
		
		if(this.streamState.pagein(this.page) < 0) {
			
			this.endOfStream = true;
			throw new IOException("Error reading first page of OGG bitstream!");
		}

		if(this.streamState.packetout(this.packet) != 1) {
			
			this.endOfStream = true;
			throw new IOException("Error reading initial header packet!");
		}

		if(this.oggInfo.synthesis_headerin(this.comment, this.packet) < 0) {
			
			this.endOfStream = true;
			throw new IOException("This Ogg bitstream does not contain Vorbis audio data.");
		}

		int i = 0;
		
		while(i < 2) {
			
			while(i < 2) {

				int result = this.syncState.pageout(this.page);
				
				if(result == 0) {
					
					break;
				}
				
				if(result == 1) {
					
					this.streamState.pagein(this.page);
					
					while(i < 2) {
						
						result = this.streamState.packetout(this.packet);
						
						if(result == 0) {
							
							break;
						}
						
						if(result == -1) {
							
							this.endOfStream = true;
							throw new IOException("Corrupt secondary header!");
						}
						
						this.oggInfo.synthesis_headerin(this.comment, this.packet);
						i++;
					}
				}
			}
			
			index = this.syncState.buffer(4096);
			this.buffer = this.syncState.data;
			
			try {
				
				this.bytes = this.input.read(this.buffer, index, 4096);
				
			} catch(Exception exception) {

				this.endOfStream = true;
				throw new IOException("Failed to read Vorbis!");
			}
			
			if(this.bytes == 0 && i < 2) {
				
				this.endOfStream = true;
				return false;
			}
			
			this.syncState.wrote(this.bytes);
		}

		this.conversionSize = 4096 / this.oggInfo.channels;
		this.dspState.synthesis_init(this.oggInfo);
		this.vorbisBlock.init(this.dspState);
		
		return true;
	}
	
	/**
	 * Reads the Vorbis audio data.
	 * @throws IOException if an I/O error occurred
	 * @since 17.08.2018/0.2.0
	 */
	private void readPCM() throws IOException {
		
		boolean wrote = false;
		
		while(true) {
			
			if(this.endOfBitStream) {
				
				if(!getPageAndPacket()) {
					
					break;
				}
				
				this.endOfBitStream = false;
			}

			if(!this.initialized) {
				
				this.initialized = true;
				return;
			}
			
			float[][][] _pcm = new float[1][][];
			int[] _index = new int[this.oggInfo.channels];
			
			while(!this.endOfBitStream) {
				
				while(!this.endOfBitStream) {
					
					int result = this.syncState.pageout(this.page);
					
					if(result == 0) {
						
						break;
					}

					if(result != -1) {
						
						this.streamState.pagein(this.page);

						while(true) {
							
							result = this.streamState.packetout(this.packet);

							if(result == 0) {
								
								break;
							}
							
							if(result != -1) {
								
								int samples;
								
								if(this.vorbisBlock.synthesis(this.packet) == 0) {
									
									this.dspState.synthesis_blockin(this.vorbisBlock);
								}

								while((samples = this.dspState.synthesis_pcmout(_pcm, _index)) > 0) {
									
									float[][] pcm = _pcm[0];
									int bout = (samples < this.conversionSize ? samples : this.conversionSize);

									for(int i = 0; i < this.oggInfo.channels; i++) {
										
										int ptr = i * 2;
										int mono = _index[i];
										
										for(int j = 0; j < bout; j++) {
											
											int val = (int)(pcm[i][mono + j] * 32767.);
											
											if(val > 32767) {
												
												val = 32767;
												
											} else if(val < -32768) {
												
												val = -32768;
												
											} else if(val < 0) {
												
												val = val | 0x8000;
											}
				
											if(this.bigEndian) {
												
												this.conversionBuffer[ptr] = (byte)(val >>> 8);
												this.conversionBuffer[ptr + 1] = (byte)(val);
												
											} else {
												
												this.conversionBuffer[ptr] = (byte)(val);
												this.conversionBuffer[ptr + 1] = (byte)(val >>> 8);
											}
											
											ptr += 2 * (this.oggInfo.channels);
										}
									}

									int bytesToWrite = 2 * this.oggInfo.channels * bout;
									
									if(bytesToWrite >= this.pcmBuffer.remaining()) {
										
										throw new IOException("Read block from OGG that was too big to be buffered: " + bytesToWrite);
									
									} else {
										
										this.pcmBuffer.put(this.conversionBuffer, 0, bytesToWrite);
									}
									
									wrote = true;
									this.dspState.synthesis_read(bout);
								}
							}
						}
						
						if(this.page.eos() != 0) {
							
							this.endOfBitStream = true;
						} 
						
						if((!this.endOfBitStream) && (wrote)) {
							
							return;
						}
					}
				}

				if(!this.endOfBitStream) {
					
					this.bytes = 0;
					int index = this.syncState.buffer(4096);
					
					if(index >= 0) {
						
						this.buffer = this.syncState.data;
						
						try {
							
							this.bytes = this.input.read(this.buffer, index, 4096);
							
						} catch(Exception exception) {
							
							this.endOfStream = true;
							throw new IOException(exception);
						}
						
					} else {
						
						this.bytes = 0;
					}
					
					this.syncState.wrote(this.bytes);
					
					if(this.bytes == 0) {
						
						this.endOfBitStream = true;
					}
				}
			}

			this.streamState.clear();
			this.vorbisBlock.clear();
			this.dspState.clear();
			this.oggInfo.clear();
		}

		this.syncState.clear();
		this.endOfStream = true;
	}
	
	@Override
	public int read() throws IOException {
		
		if(this.readIndex >= this.pcmBuffer.position()) {
			
			this.pcmBuffer.clear();
			readPCM();
			this.readIndex = 0;
		}
		
		if(this.readIndex >= this.pcmBuffer.position()) {
			
			return -1;
		}

		int value = this.pcmBuffer.get(this.readIndex);
		
		if(value < 0) {
			
			value = 256 + value;
		}
		
		this.readIndex++;
		return value;
	}

	@Override
	public int read(byte[] buffer, int offset, int length) throws IOException {
		
		for(int index = 0; index < length; index++) {

			int value = this.read();
				
			if(value != -1) {
					
				buffer[index] = (byte)value;
					
			} else {

				return index == 0 ? -1 : index;
			}
		}
		
		return length;
	}

	@Override
	public int read(byte[] buffer) throws IOException {
		
		return this.read(buffer, 0, buffer.length);
	}

	@Override
	public void close() throws IOException {
		
		if(this.input != null) {
			
			this.input.close();
		}
	}
	
	/**
	 * @return {@code true} if the stream reached the end, else {@code false}
	 * @since 17.08.2018/0.2.0
	 */
	public boolean atEnd() {
		
		return this.endOfStream && (this.readIndex >= this.pcmBuffer.position());
	}
	
	/**
	 * @return the {@linkplain Info} object
	 * @since 17.08.2018/0.2.0
	 */
	public Info getOggInfo() {
		
		return this.oggInfo;
	}
}
