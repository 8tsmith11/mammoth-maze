package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RaycastView extends Canvas {
	private int[][] map;
	
	public RaycastView(World world) {
		map = world.getMap();
	}
	
	/* draws view at (x, y) on the main view */
	public void drawView(Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(x, y);
		
		g2.dispose();
	}
}
