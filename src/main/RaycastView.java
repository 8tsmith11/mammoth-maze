package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

public class RaycastView extends Canvas {
	private static final long serialVersionUID = 1L;
	private int[][] map;
	private Player player;
	private Raycaster rc;
	private int x, y; // Position of view
	private int width, height; // Size of view
	
	public RaycastView(World world, Player p, Raycaster r) {
		map = world.getMap();
		player = p;
		this.rc = r;
	}
	
	@Override
	public void paint(Graphics g) {
		// Draw Floor
		g.setColor(Color.WHITE);
		g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
		
		// Draw Ceiling
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight() / 2);
		
		// Draw Walls
		
		Line2D.Double[] rays = rc.getRays();
		
		int wallWidth = getWidth() / rays.length; // Width of wall segment in pixels
		
		for(int i = 0; i < rays.length; i++) {
			Line2D.Double ray = rays[i];
			
			// Ray length (Pythagorean Theorem)
			double d = Math.sqrt((ray.x2 - ray.x1) * (ray.x2 - ray.x1) + 
					((ray.y2 - ray.y1) * (ray.y2 - ray.y1)));
			
			// Perpendicular Distance to correct for fish-eye effect
			double angle = Math.toRadians(-rays.length / 2 + i);
			double pd = d * Math.cos(angle - player.getAngle());
			
			int wallHeight = (int)(getHeight() / d); // Height of wall segment in pixels
			
			int top = Math.max(0, getHeight() / 2 - wallHeight / 2);
			
			// Walls get darker with distance
			int shade = Math.max(0, 255 - (int)(d * 50));
			g.setColor(new Color(shade, 0, 0));

			g.fillRect(i * wallWidth, top, wallWidth, wallHeight);
		}
		
		
	}
}
