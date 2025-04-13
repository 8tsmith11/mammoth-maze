package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameOver extends JPanel {
	
	public static void showImage(String imagePath, int width, int height) {
		
		JFrame frame = new JFrame("Image Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// can adjust size later
		frame.setSize(width, height);
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			System.out.println("Failed Retrieval: " + e.getMessage());
			return;
			
		}
		
		BufferedImage finalImg = img;
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(finalImg, 0, 0, this.getWidth(), 
						this.getHeight(), null);
				
			}
		};
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	// example of how this would be written in main
	
	
//	public static void main(String[] args) {
//		GameOver.showImage("/Users/pranavsaiakurati/Desktop/example.jpeg", 
//				500, 500);
//	}
	
}