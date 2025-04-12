package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class RaycastView extends Canvas {
	private static final long serialVersionUID = 1L;
	private int[][] map;
	private int xOffset, yOffset; // Position of view
	private Dimension screenSize;
	
	public RaycastView(World world, int x, int y, Dimension screenSize) {
		map = world.getMap();
		xOffset = x;
		yOffset = y;
		this.screenSize = screenSize;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics g2 = g.create();
		g2.translate(xOffset, yOffset);
		
		g2.setColor(Color.RED);
		g2.fillRect(0, 0, screenSize.width - xOffset, screenSize.height);
        
        g2.dispose();
	}
}
