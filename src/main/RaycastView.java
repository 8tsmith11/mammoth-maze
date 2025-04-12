package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RaycastView extends Canvas {
	private int[][] map;
	private int xOffset, yOffset; // Position of view
	
	public RaycastView(World world, int x, int y) {
		map = world.getMap();
		xOffset = x;
		yOffset = y;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics g2 = g.create();
		g2.translate(xOffset, yOffset);
		
		g2.setColor(Color.RED);
		g2.fillRect(0, 0, 100, 100);
        
        g2.dispose();
	}
}
