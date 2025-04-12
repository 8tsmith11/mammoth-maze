package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class RaycastView extends Canvas {
	private static final long serialVersionUID = 1L;
	private int[][] map;
	private int x, y; // Position of view
	private int width, height; // Size of view
	
	public RaycastView(World world, int x, int y, int width, int height) {
		map = world.getMap();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics g2 = g.create();
		g2.translate(x, y);
		
		g2.setColor(Color.RED);
		g2.fillRect(0, 0, width, height);
        
        g2.dispose();
	}
}
