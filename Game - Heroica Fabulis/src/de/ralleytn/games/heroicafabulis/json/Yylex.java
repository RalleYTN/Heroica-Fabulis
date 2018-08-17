package de.ralleytn.games.heroicafabulis.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Yylex {

	public static final int YYINITIAL = 0;
	public static final int STRING_BEGIN = 2;
	public static final int YYEOF = -1;
	
	private static final int ZZ_BUFFERSIZE = 16384;
	private static final int ZZ_LEXSTATE[] = {0, 0, 1, 1};
	private static final String ZZ_CMAP_PACKED = "\11\0\1\7\1\7\2\0\1\7\22\0\1\7\1\0\1\11\10\0\1\6\1\31\1\2\1\4\1\12\12\3\1\32\6\0\4\1\1\5\1\1\24\0\1\27\1\10\1\30\3\0\1\22\1\13\2\1\1\21\1\14\5\0\1\23\1\0\1\15\3\0\1\16\1\24\1\17\1\20\5\0\1\25\1\0\1\26\uff82\0";
	private static final char[] ZZ_CMAP = Yylex.zzUnpackCMap(Yylex.ZZ_CMAP_PACKED);
	private static final int[] ZZ_ACTION = Yylex.zzUnpackAction();
	private static final String ZZ_ACTION_PACKED_0 = "\2\0\2\1\1\2\1\3\1\4\3\1\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\5\0\1\14\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\0\1\25\1\0\1\25\4\0\1\26\1\27\2\0\1\30";
	private static final int[] ZZ_ROWMAP = Yylex.zzUnpackRowMap();
	private static final String ZZ_ROWMAP_PACKED_0 = "\0\0\0\33\0\66\0\121\0\154\0\207\0\66\0\242\0\275\0\330\0\66\0\66\0\66\0\66\0\66\0\66\0\363\0\u010e\0\66\0\u0129\0\u0144\0\u015f\0\u017a\0\u0195\0\66\0\66\0\66\0\66\0\66\0\66\0\66\0\66\0\u01b0\0\u01cb\0\u01e6\0\u01e6\0\u0201\0\u021c\0\u0237\0\u0252\0\66\0\66\0\u026d\0\u0288\0\66";
	private static final int ZZ_UNKNOWN_ERROR = 0;
	private static final int ZZ_NO_MATCH = 1;
	private static final int ZZ_PUSHBACK_2BIG = 2;
	private static final String[] ZZ_ERROR_MSG = {"Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"};
	private static final int[] ZZ_ATTRIBUTE = Yylex.zzUnpackAttribute();
	private static final String ZZ_ATTRIBUTE_PACKED_0 = "\2\0\1\11\3\1\1\11\3\1\6\11\2\1\1\11\5\0\10\11\1\0\1\1\1\0\1\1\4\0\2\11\2\0\1\11";
	private static final int[] ZZ_TRANS = {
			
		2, 2, 3, 4, 2, 2, 2, 5, 2, 6, 
		2, 2, 7, 8, 2, 9, 2, 2, 2, 2, 
		2, 10, 11, 12, 13, 14, 15, 16, 16, 16, 
		16, 16, 16, 16, 16, 17, 18, 16, 16, 16, 
		16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 
		16, 16, 16, 16, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, 4, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, 4, 19, 20, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, 20, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, 5, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		21, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, 22, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		23, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, 16, 16, 16, 16, 16, 16, 16, 
		16, -1, -1, 16, 16, 16, 16, 16, 16, 16, 
		16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 
		-1, -1, -1, -1, -1, -1, -1, -1, 24, 25, 
		26, 27, 28, 29, 30, 31, 32, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		33, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, 34, 35, -1, -1, 
		34, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		36, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, 37, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, 38, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, 39, -1, 39, -1, 39, -1, -1, 
		-1, -1, -1, 39, 39, -1, -1, -1, -1, 39, 
		39, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, 33, -1, 20, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, 20, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, 35, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, 38, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, 40, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, 41, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, 42, -1, 42, -1, 42, 
		-1, -1, -1, -1, -1, 42, 42, -1, -1, -1, 
		-1, 42, 42, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, 43, -1, 43, -1, 43, -1, -1, -1, 
		-1, -1, 43, 43, -1, -1, -1, -1, 43, 43, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, 44, 
		-1, 44, -1, 44, -1, -1, -1, -1, -1, 44, 
		44, -1, -1, -1, -1, 44, 44, -1, -1, -1, 
		-1, -1, -1, -1, -1, 
	};
	
	private Reader zzReader;
	private int zzLexicalState = Yylex.YYINITIAL;
	private char zzBuffer[] = new char[Yylex.ZZ_BUFFERSIZE];
	private int zzMarkedPos;
	private int zzCurrentPos;
	private int zzStartRead;
	private int zzEndRead;
	private int yychar;
	private boolean zzAtEOF;
	private StringBuffer sb = new StringBuffer();
	
	private static int[] zzUnpackAction() {
    
		int[] result = new int[45];
		int offset = 0;
		offset = Yylex.zzUnpackAction(Yylex.ZZ_ACTION_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAction(String packed, int offset, int[] result) {

		int i = 0;
		int j = offset;
		int l = packed.length();
		
		while(i < l) {
			
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			
			do {
				
				result[j++] = value;
				
			} while(--count > 0);
		}

		return j;
	}

	private static int[] zzUnpackRowMap() {

		int[] result = new int[45];
		int offset = 0;
		offset = Yylex.zzUnpackRowMap(Yylex.ZZ_ROWMAP_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackRowMap(String packed, int offset, int[] result) {

		int i = 0;
		int j = offset;
		int l = packed.length();

		while(i < l) {

			int high = packed.charAt(i++) << 16;
			result[j++] = high | packed.charAt(i++);
		}

		return j;
	}

	private static int[] zzUnpackAttribute() {

		int[] result = new int[45];
		int offset = 0;
		offset = Yylex.zzUnpackAttribute(Yylex.ZZ_ATTRIBUTE_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAttribute(String packed, int offset, int [] result) {

		int i = 0;
		int j = offset;
		int l = packed.length();

		while(i < l) {

			int count = packed.charAt(i++);
			int value = packed.charAt(i++);

			do {
				
				result[j++] = value;
				
			} while(--count > 0);
		}

		return j;
	}

	public int getPosition() {

		return this.yychar;
	}

	public Yylex(Reader in) {

		this.zzReader = in;
	}

	public Yylex(InputStream in) {

		this(new InputStreamReader(in));
	}

	private static char[] zzUnpackCMap(String packed) {

		char[] map = new char[0x10000];
		int i = 0;
		int j = 0;

		while(i < 90) {

			int count = packed.charAt(i++);
			char value = packed.charAt(i++);
			
			do {
				
				map[j++] = value;
				
			} while(--count > 0);
		}

		return map;
	}

	private boolean zzRefill() throws IOException {

		if(this.zzStartRead > 0) {

			System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);

			this.zzEndRead -= this.zzStartRead;
			this.zzCurrentPos -= this.zzStartRead;
			this.zzMarkedPos -= this.zzStartRead;
			this.zzStartRead = 0;
		}

		if(this.zzCurrentPos >= this.zzBuffer.length) {

			char[] newBuffer = new char[this.zzCurrentPos * 2];
			System.arraycopy(this.zzBuffer, 0, newBuffer, 0, this.zzBuffer.length);
			this.zzBuffer = newBuffer;
		}

		int numRead = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);

		if(numRead > 0) {

			this.zzEndRead += numRead;
			return false;
		}

		if(numRead == 0) {

			int c = this.zzReader.read();

			if(c == -1) {

				return true;

			} else {

				this.zzBuffer[this.zzEndRead++] = (char)c;
				return false;
			}     
		}

		return true;
	}

	public final void yyclose() throws IOException {

		this.zzAtEOF = true;
		this.zzEndRead = this.zzStartRead;

		if(this.zzReader != null) {

			this.zzReader.close();
		}
	}

	public final void yyreset(Reader reader) {

		this.zzReader = reader;
		this.zzAtEOF  = false;
		this.zzEndRead = this.zzStartRead = 0;
		this.zzCurrentPos = this.zzMarkedPos = 0;
		this.yychar = 0;
		this.zzLexicalState = Yylex.YYINITIAL;
	}

	public final int yystate() {

		return this.zzLexicalState;
	}

	public final void yybegin(int newState) {

		this.zzLexicalState = newState;
	}

	public final String yytext() {

		return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
	}

	public final char yycharat(int pos) {

		return this.zzBuffer[this.zzStartRead + pos];
	}

	public final int yylength() {

		return this.zzMarkedPos - this.zzStartRead;
	}

	private void zzScanError(int errorCode) {

		String message;

		try {
			
			message = Yylex.ZZ_ERROR_MSG[errorCode];
			
		} catch(ArrayIndexOutOfBoundsException exception) {

			message = Yylex.ZZ_ERROR_MSG[Yylex.ZZ_UNKNOWN_ERROR];
		}

		throw new Error(message);
	} 

	public void yypushback(int number)  {

		if(number > yylength()) {
      
			this.zzScanError(Yylex.ZZ_PUSHBACK_2BIG);
		}

		this.zzMarkedPos -= number;
	}

	public Yytoken yylex() throws IOException, JSONParseException {

		int zzInput;
		int zzAction;
		int zzCurrentPosL;
		int zzMarkedPosL;
		int zzEndReadL = this.zzEndRead;
		char[] zzBufferL = this.zzBuffer;
		char[] zzCMapL = Yylex.ZZ_CMAP;
		int[] zzTransL = Yylex.ZZ_TRANS;
		int[] zzRowMapL = Yylex.ZZ_ROWMAP;
		int[] zzAttrL = Yylex.ZZ_ATTRIBUTE;

		while(true) {

			zzMarkedPosL = this.zzMarkedPos;
			this.yychar += zzMarkedPosL - this.zzStartRead;
			zzAction = -1;
			zzCurrentPosL = this.zzCurrentPos = this.zzStartRead = zzMarkedPosL;
			int zzState = Yylex.ZZ_LEXSTATE[this.zzLexicalState];

			zzForAction: {
        
				while(true) {
    
					if(zzCurrentPosL < zzEndReadL) {

						zzInput = zzBufferL[zzCurrentPosL++];
						
					} else if(this.zzAtEOF) {

						zzInput = Yylex.YYEOF;
						break zzForAction;
						
					} else {
            
						// store back cached positions
						this.zzCurrentPos = zzCurrentPosL;
						this.zzMarkedPos = zzMarkedPosL;
						boolean eof = this.zzRefill();

						// get translated positions and possibly new buffer
						zzCurrentPosL = this.zzCurrentPos;
						zzMarkedPosL = this.zzMarkedPos;
						zzBufferL = this.zzBuffer;
						zzEndReadL = this.zzEndRead;

						if(eof) {
              
							zzInput = Yylex.YYEOF;
							break zzForAction;
							
						} else {
              
							zzInput = zzBufferL[zzCurrentPosL++];
						}
					}
					
					int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
          
					if(zzNext == -1) {
        	  
						break zzForAction;
					}
					
					zzState = zzNext;

					int zzAttributes = zzAttrL[zzState];
          
					if((zzAttributes & 1) == 1) {
            
						zzAction = zzState;
						zzMarkedPosL = zzCurrentPosL;
						
						if((zzAttributes & 8) == 8) {
							
							break zzForAction;
						}
					}
				}
			}

			this.zzMarkedPos = zzMarkedPosL;
			int value = zzAction < 0 ? zzAction : ZZ_ACTION[zzAction];
			
			if(value < 25 || value > 48) {
				
				if(value == 11) {
					
					this.sb.append(this.yytext());
					
				} else if(value == 4) {
					
					this.sb = new StringBuffer();
					this.yybegin(Yylex.STRING_BEGIN);
					
				} else if(value == 16) {
					
					this.sb.append('\b');
					
				} else if(value == 6) {
					
					return new Yytoken(Yytoken.TYPE_RIGHT_BRACE, null);
				
				} else if(value == 23) {
					
					return new Yytoken(Yytoken.TYPE_VALUE, Boolean.valueOf(this.yytext()));
				
				} else if(value == 22) {
					
					return new Yytoken(Yytoken.TYPE_VALUE, null);
					
				} else if(value == 13) {
					
					this.yybegin(Yylex.YYINITIAL);
					return new Yytoken(Yytoken.TYPE_VALUE, this.sb.toString());
					
				} else if(value == 12) {
					
					this.sb.append('\\');
					
				} else if(value == 21) {
					
					return new Yytoken(Yytoken.TYPE_VALUE, Double.valueOf(yytext()));
				
				} else if(value == 1) {
					
					throw new JSONParseException(this.yychar, JSONParseException.ERROR_UNEXPECTED_CHAR, this.yycharat(0));
				
				} else if(value == 8) {
					
					return new Yytoken(Yytoken.TYPE_RIGHT_SQUARE, null);
					
				} else if(value == 19) {
					
					this.sb.append('\r');
				
				} else if(value == 15) {
					
					this.sb.append('/');
					
				} else if(value == 10) {
					
					return new Yytoken(Yytoken.TYPE_COLON, null);
					
				} else if(value == 14) {
					
					this.sb.append('"');
					
				} else if(value == 5) {
					
					return new Yytoken(Yytoken.TYPE_LEFT_BRACE, null);
					
				} else if(value == 17) {
					
					this.sb.append('\f');
					
				} else if(value == 24) {
					
					try {
						
						int ch = Integer.parseInt(this.yytext().substring(2), 16);
						this.sb.append((char)ch);
						
					} catch(Exception exception) {
						
						throw new JSONParseException(this.yychar, JSONParseException.ERROR_UNEXPECTED_EXCEPTION, exception);
					}
					
				} else if(value == 20) {
					
					this.sb.append('\t');
					
				} else if(value == 7) {
					
					return new Yytoken(Yytoken.TYPE_LEFT_SQUARE, null);
					
				} else if(value == 2) {
					
					return new Yytoken(Yytoken.TYPE_VALUE, Long.valueOf(this.yytext()));
					
				} else if(value == 18) {
					
					this.sb.append('\n');
					
				} else if(value == 9) {
					
					return new Yytoken(Yytoken.TYPE_COMMA, null);
					
				} else if(value == 3) {
				} else {
				
					if(zzInput == Yylex.YYEOF && this.zzStartRead == this.zzCurrentPos) {
			        
						this.zzAtEOF = true;
						return null;
						
					} else {

						this.zzScanError(Yylex.ZZ_NO_MATCH);
					}
				}
			}
		}
	}
}
