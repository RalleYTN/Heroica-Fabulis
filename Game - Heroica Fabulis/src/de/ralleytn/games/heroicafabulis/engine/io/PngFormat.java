package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;
import java.io.InputStream;

import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;
import static de.ralleytn.games.heroicafabulis.engine.util.IOUtil.*;

public final class PngFormat {

	private static final String SIGNATURE = "\211PNG\r\n\32\n";

    private static final int IHDR = 0x49484452;
    private static final int PLTE = 0x504C5445;
    private static final int tRNS = 0x74524E53;
    private static final int IDAT = 0x49444154;
    private static final int IEND = 0x49454E44;
    
    private static final byte COLOR_GREYSCALE = 0;
    private static final byte COLOR_TRUECOLOR = 2;
    private static final byte COLOR_INDEXED = 3;
    private static final byte COLOR_GREYALPHA = 4;
    private static final byte COLOR_TRUEALPHA = 6;  
	
	private PngFormat() {}
	
	public static final void readSignature(InputStream imageStream) throws IOException {
		
		for(int index = 0; index < SIGNATURE.length(); index++) {
			
			if(imageStream.read() != SIGNATURE.charAt(index)) {
				
				throw new IOException("Not a PNG file!");
			}
		}
	}
	
	private static enum ColorModel {
		
		ALPHA(1, true),
		LUMINANCE(1, false),
		LUMINANCE_ALPGA(2, true),
		RGB(3, false),
		RGBA(4, true),
		BGRA(4, true),
		ABGR(4, true);
		
		private final int componentCount;
		private final boolean alpha;
		
		private ColorModel(int componentCount, boolean alpha) {
			
			this.componentCount = componentCount;
			this.alpha = alpha;
		}
		
		private final int getComponentCount() {
			
			return this.componentCount;
		}
		
		private final boolean hasAlpha() {
			
			return this.alpha;
		}
	}
}
