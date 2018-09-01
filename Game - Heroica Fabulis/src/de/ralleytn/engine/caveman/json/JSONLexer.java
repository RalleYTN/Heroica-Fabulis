package de.ralleytn.engine.caveman.json;

import java.io.IOException;
import java.io.Reader;

/**
 * Lexer for the JSON parser.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 24.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public final class JSONLexer {

	/** @since 17.08.2018/0.2.0 */ public static final int STATE_INITIAL = 0;
	/** @since 17.08.2018/0.2.0 */ public static final int STATE_STRING_BEGIN = 2;
	/** @since 17.08.2018/0.2.0 */ public static final int STATE_EOF = -1;
	
	private static final int BUFFERSIZE = 16384;
	private static final int LEXSTATE[] = {0, 0, 1, 1};
	private static final String CHARACTER_MAP_PACKED = "\11\0\1\7\1\7\2\0\1\7\22\0\1\7\1\0\1\11\10\0\1\6\1\31\1\2\1\4\1\12\12\3\1\32\6\0\4\1\1\5\1\1\24\0\1\27\1\10\1\30\3\0\1\22\1\13\2\1\1\21\1\14\5\0\1\23\1\0\1\15\3\0\1\16\1\24\1\17\1\20\5\0\1\25\1\0\1\26\uff82\0";
	private static final char[] CHARACTER_MAP = unpackCharacterMap(CHARACTER_MAP_PACKED);
	private static final int[] ACTION = unpackAction();
	private static final String ACTION_PACKED = "\2\0\2\1\1\2\1\3\1\4\3\1\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\5\0\1\14\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\0\1\25\1\0\1\25\4\0\1\26\1\27\2\0\1\30";
	private static final int[] ROWMAP = unpackRowMap();
	private static final String ROWMAP_PACKED = "\0\0\0\33\0\66\0\121\0\154\0\207\0\66\0\242\0\275\0\330\0\66\0\66\0\66\0\66\0\66\0\66\0\363\0\u010e\0\66\0\u0129\0\u0144\0\u015f\0\u017a\0\u0195\0\66\0\66\0\66\0\66\0\66\0\66\0\66\0\66\0\u01b0\0\u01cb\0\u01e6\0\u01e6\0\u0201\0\u021c\0\u0237\0\u0252\0\66\0\66\0\u026d\0\u0288\0\66";
	private static final int ERR_TYPE_UNKNOWN_ERROR = 0;
	private static final int ERR_TYPE_NO_MATCH = 1;
	private static final String[] ERROR_MESSAGES = {
		
		"Unkown internal scanner error",
		"Error: could not match input",
		"Error: pushback value was too large"
	};
	private static final int[] ATTRIBUTE = unpackAttribute();
	private static final String ATTRIBUTE_PACKED = "\2\0\1\11\3\1\1\11\3\1\6\11\2\1\1\11\5\0\10\11\1\0\1\1\1\0\1\1\4\0\2\11\2\0\1\11";
	private static final int[] TRANS = {
			
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
	
	private Reader reader;
	private int lexicalState = STATE_INITIAL;
	private char buffer[] = new char[BUFFERSIZE];
	private int markedPos;
	private int currentPos;
	private int startRead;
	private int endRead;
	private int charPos;
	private boolean eof;
	private StringBuffer sbuffer = new StringBuffer();
	
	/**
	 * 
	 * @param in
	 * @since 17.08.2018/0.2.0
	 */
	public JSONLexer(Reader in) {

		this.reader = in;
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	private static final int[] unpackAction() {
    
		int[] result = new int[45];
		unpackAction(ACTION_PACKED, 0, result);
		return result;
	}

	/**
	 * 
	 * @param packed
	 * @param offset
	 * @param result
	 * @since 17.08.2018/0.2.0
	 */
	private static final void unpackAction(String packed, int offset, int[] result) {

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
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	private static final int[] unpackRowMap() {

		int[] result = new int[45];
		unpackRowMap(ROWMAP_PACKED, 0, result);
		return result;
	}

	/**
	 * 
	 * @param packed
	 * @param offset
	 * @param result
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	private static final void unpackRowMap(String packed, int offset, int[] result) {

		int i = 0;
		int j = offset;
		int l = packed.length();

		while(i < l) {

			int high = packed.charAt(i++) << 16;
			result[j++] = high | packed.charAt(i++);
		}
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	private static final int[] unpackAttribute() {

		int[] result = new int[45];
		unpackAttribute(ATTRIBUTE_PACKED, 0, result);
		return result;
	}

	/**
	 * 
	 * @param packed
	 * @param offset
	 * @param result
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	private static final void unpackAttribute(String packed, int offset, int [] result) {

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
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public final int getCharacterPosition() {

		return this.charPos;
	}

	/**
	 * 
	 * @param packed
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	private static final char[] unpackCharacterMap(String packed) {

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

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @since 17.08.2018/0.2.0
	 */
	private final boolean refill() throws IOException {

		if(this.startRead > 0) {

			System.arraycopy(this.buffer, this.startRead, this.buffer, 0, this.endRead - this.startRead);

			this.endRead -= this.startRead;
			this.currentPos -= this.startRead;
			this.markedPos -= this.startRead;
			this.startRead = 0;
		}

		if(this.currentPos >= this.buffer.length) {

			char[] newBuffer = new char[this.currentPos * 2];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.buffer.length);
			this.buffer = newBuffer;
		}

		int numRead = this.reader.read(this.buffer, this.endRead, this.buffer.length - this.endRead);

		if(numRead > 0) {

			this.endRead += numRead;
			return false;
		}

		if(numRead == 0) {

			int c = this.reader.read();

			if(c == -1) {

				return true;

			} else {

				this.buffer[this.endRead++] = (char)c;
				return false;
			}     
		}

		return true;
	}

	/**
	 * 
	 * @param newState
	 * @since 17.08.2018/0.2.0
	 */
	public final void begin(int newState) {

		this.lexicalState = newState;
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public final String getText() {

		return new String(this.buffer, this.startRead, this.markedPos - this.startRead);
	}

	/**
	 * 
	 * @param pos
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public final char charAt(int pos) {

		return this.buffer[this.startRead + pos];
	}

	/**
	 * 
	 * @param errorCode
	 * @since 17.08.2018/0.2.0
	 */
	private final void error(int errorCode) {

		String message;

		try {
			
			message = ERROR_MESSAGES[errorCode];
			
		} catch(ArrayIndexOutOfBoundsException exception) {

			message = ERROR_MESSAGES[ERR_TYPE_UNKNOWN_ERROR];
		}

		throw new Error(message);
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONParseException
	 * @since 17.08.2018/0.2.0
	 */
	public final JSONToken nextToken() throws IOException, JSONParseException {

		int input;
		int action;
		int currentPos;
		int markedPos;
		int endRead = this.endRead;
		char[] buffer = this.buffer;
		char[] characterMap = CHARACTER_MAP;
		int[] trans = TRANS;
		int[] rowMap = ROWMAP;
		int[] attribute = ATTRIBUTE;

		while(true) {

			markedPos = this.markedPos;
			this.charPos += markedPos - this.startRead;
			action = -1;
			currentPos = this.currentPos = this.startRead = markedPos;
			int state = LEXSTATE[this.lexicalState];

			while(true) {
	    
				if(currentPos < endRead) {
	
					input = buffer[currentPos++];
					
				} else if(this.eof) {
	
					input = STATE_EOF;
					break;
					
				} else {
	            
					this.currentPos = currentPos;
					this.markedPos = markedPos;
					boolean eof = this.refill();
	
					currentPos = this.currentPos;
					markedPos = this.markedPos;
					buffer = this.buffer;
					endRead = this.endRead;
	
					if(eof) {
	            
						input = STATE_EOF;
						break;
						
					} else {
	            
						input = buffer[currentPos++];
					}
				}
				
				int next = trans[rowMap[state] + characterMap[input]];
	          
				if(next == -1) {
	        	
					break;
				}
				
				state = next;
	
				int attributes = attribute[state];
	          
				if((attributes & 1) == 1) {
	            
					action = state;
					markedPos = currentPos;
					
					if((attributes & 8) == 8) {
						
						break;
					}
				}
			}

			this.markedPos = markedPos;
			int value = action < 0 ? action : ACTION[action];
			
			if(value < 25 || value > 48) {
				
				switch(value) {
				
					case 11:
						this.sbuffer.append(this.getText());
						break;
						
					case 4:
						this.sbuffer = new StringBuffer();
						this.begin(STATE_STRING_BEGIN);
						break;
						
					case 16:
						this.sbuffer.append('\b');
						break;
						
					case 6:
						return new JSONToken(JSONToken.TYPE_RIGHT_BRACE, null);
						
					case 23:
						return new JSONToken(JSONToken.TYPE_VALUE, Boolean.valueOf(this.getText()));
						
					case 22:
						return new JSONToken(JSONToken.TYPE_VALUE, null);
						
					case 13:
						this.begin(STATE_INITIAL);
						return new JSONToken(JSONToken.TYPE_VALUE, this.sbuffer.toString());
						
					case 12:
						this.sbuffer.append('\\');
						break;
						
					case 21:
						return new JSONToken(JSONToken.TYPE_VALUE, Double.valueOf(this.getText()));
						
					case 1:
						throw new JSONParseException(this.charPos, JSONParseException.ERROR_UNEXPECTED_CHAR, this.charAt(0));
						
					case 8:
						return new JSONToken(JSONToken.TYPE_RIGHT_SQUARE, null);
						
					case 19:
						this.sbuffer.append('\r');
						break;
						
					case 15:
						this.sbuffer.append('/');
						break;
						
					case 10:
						return new JSONToken(JSONToken.TYPE_COLON, null);
						
					case 14:
						this.sbuffer.append('"');
						break;
						
					case 5:
						return new JSONToken(JSONToken.TYPE_LEFT_BRACE, null);
						
					case 17:
						this.sbuffer.append('\f');
						break;
						
					case 24:
						try {
							
							int ch = Integer.parseInt(this.getText().substring(2), 16);
							this.sbuffer.append((char)ch);
							
						} catch(Exception exception) {
							
							throw new JSONParseException(this.charPos, JSONParseException.ERROR_UNEXPECTED_EXCEPTION, exception);
						}
						break;
						
					case 20:
						this.sbuffer.append('\t');
						break;
						
					case 7:
						return new JSONToken(JSONToken.TYPE_LEFT_SQUARE, null);
						
					case 2:
						return new JSONToken(JSONToken.TYPE_VALUE, Long.valueOf(this.getText()));
						
					case 18:
						this.sbuffer.append('\n');
						break;
						
					case 9:
						return new JSONToken(JSONToken.TYPE_COMMA, null);
						
					default:
						if(value != 3) {
							
							if(input == STATE_EOF && this.startRead == this.currentPos) {
					        
								this.eof = true;
								return null;
								
							} else {

								this.error(ERR_TYPE_NO_MATCH);
							}
						}
				}
			}
		}
	}
}
