package de.ralleytn.games.heroicafabulis.engine.util.math;

import java.util.Random;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
 * @since 21.08.2018/0.2.0
 */
public class SimplexNoise {

	private SimplexNoiseOctave[] octaves;
	private double[] frequencys;
	private double[] amplitudes;
	private int largestFeature;
	private double persistance;
	private long seed;
	private int swapCount;
	
	/**
	 * 
	 * @param largestFeature
	 * @param persistance
	 * @param seed
	 * @param swapCount
	 * @since 21.08.2018/0.2.0
	 */
	public SimplexNoise(int largestFeature, double persistance, long seed, int swapCount) {
		
		this.largestFeature = largestFeature;
		this.persistance = persistance;
		this.seed = seed;
		
		int octaveCount = (int)Math.ceil(Math.log10(largestFeature) / Math.log10(2.0D));
		this.octaves = new SimplexNoiseOctave[octaveCount];
		this.frequencys = new double[octaveCount];
		this.amplitudes = new double[octaveCount];
		
		Random random = new Random(seed);
		
		for(int index = 0; index < octaveCount; index++) {
			
			this.octaves[index] = new SimplexNoiseOctave(random.nextLong(), swapCount);
			this.frequencys[index] = Math.pow(2, index);
			this.amplitudes[index] = Math.pow(persistance, octaveCount - index);
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param resX
	 * @param resY
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public float[][] generateHeightMap(int x, int y, int width, int height, int resX, int resY) {
		
		int xEnd = x + width;
		int yEnd = y + height;
		
		float[][] result = new float[resX][resY];
		
		for(int i = 0; i < resX; i++) {
			
			for(int j = 0; j < resY; j++) {
				
				int posX = x + i * ((xEnd - x) / resX);
				int posY = y + j * ((yEnd - y) / resY);
				result[i][j] = Math.min(1.0F, Math.max(0.0F, (float)(0.5D * (1 + this.getNoise(posX, posY)))));
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public double getNoise(int x, int y) {
		
		double result = 0;
		
		for(int index = 0; index < this.octaves.length; index++) {
			
			result += this.octaves[index].noise(x / this.frequencys[index], y / this.frequencys[index]) * this.amplitudes[index];
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public double getNoise(int x, int y, int z) {
		
		double result = 0;
		
		for(int index = 0; index < this.octaves.length; index++) {
			
			double frequency = Math.pow(2, index);
	        double amplitude = Math.pow(this.persistance, this.octaves.length - index);
	        
	        result += this.octaves[index].noise(x / frequency, y / frequency, z / frequency) * amplitude;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public int getLargestFeature() {
		
		return this.largestFeature;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public double getPersistance() {
		
		return this.persistance;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public long getSeed() {
		
		return this.seed;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public int getSwapCount() {
		
		return this.swapCount;
	}
}
