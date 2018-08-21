package de.ralleytn.games.heroicafabulis;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import de.ralleytn.games.heroicafabulis.engine.io.textures.DefaultTextureReader;
import de.ralleytn.games.heroicafabulis.engine.io.textures.TextureData;
import de.ralleytn.games.heroicafabulis.engine.io.textures.XImgTextureReader;
import de.ralleytn.games.heroicafabulis.engine.io.textures.XImgTextureWriter;

public class TextureConverter {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		DefaultTextureReader reader = new DefaultTextureReader();
		XImgTextureWriter writer = new XImgTextureWriter();
		
		try {
			long rT1 = System.currentTimeMillis();
			TextureData data = reader.read(new FileInputStream("res/textures/stall.png"));
			System.out.println(System.currentTimeMillis() - rT1);
			writer.write(new FileOutputStream("res/textures/stall.ximg"), data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long rt2 = System.currentTimeMillis();
		TextureData data = new XImgTextureReader().read(new FileInputStream("res/textures/stall.ximg"));
		System.out.println(System.currentTimeMillis() - rt2);
		BufferedImage image = new BufferedImage(data.getWidth(), data.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int index = 0;
		
		for(int y = 0; y < data.getHeight(); y++) {
			
			for(int x = 0; x < data.getWidth(); x++) {
				
				int pixel = data.getPixels()[index++];
				
				int alpha = (pixel >> 24) & 0xFF;
				int blue = (pixel >> 16) & 0xFF;
				int green = (pixel >> 8) & 0xFF;
				int red = pixel & 0xFF;
				
				image.setRGB(x, y, ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF));
			}
		}
		
		JFrame frame = new JFrame();
		frame.add(new JLabel(new ImageIcon(image)));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
