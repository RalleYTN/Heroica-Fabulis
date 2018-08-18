package de.ralleytn.games.heroicafabulis.engine.io.png;

// TODO

public class IHDR extends PngChunk {

	private int width;
	
	public IHDR(long length, byte[] data) {
		
		super(length, CHUNK_TYPE_IHDR, data);
		
		this.width = (int)((((long)data[3]) & 0x00000000000000FFL) |
				 		  ((((long)data[2]) & 0x00000000000000FFL) << 8) |
				 		  ((((long)data[1]) & 0x00000000000000FFL) << 16) |
				 		  ((((long)data[0]) & 0x00000000000000FFL) << 24));
		
	}

	public int getWidth() {

		return this.width;
	}
	
	public int getHeight() {
		
		return 0;
	}
	
	public byte getBitDepth() {
		
		return 0;
	}
	
	public byte getColorType() {
		
		return 0;
	}
	
	public byte getCompressionMethod() {
		
		return 0;
	}
	
	public byte getFilterMethod() {
		
		return 0;
	}
	
	public byte getInterlaceMethod() {
		
		return 0;
	}
}
