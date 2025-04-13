package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class RaycastView extends Canvas {
	private static final long serialVersionUID = 1L;
	private int[][] map;
	private Player player;
	private Raycaster rc;

	
	// pranav
	private BufferedImage portal;
	
	
	public RaycastView(World world, Player p, Raycaster r) {
		map = world.getMap();
		player = p;
		this.rc = r;
		
		try { 
			// put in path from git
			portal = ImageIO.read(new File("yugioh_portal.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			double angle = player.getAngle() - Math.toRadians(rays.length / 2) + Math.toRadians(i);
			double pd = d * Math.cos(angle - player.getAngle());
			
			int wallHeight = (int)(getHeight() / pd); // Height of wall segment in pixels
			
			int top = Math.max(0, getHeight() / 2 - wallHeight / 2);

			
			int mapX = (int)Math.floor(ray.x2);
			int mapY = (int)Math.floor(ray.y2);
			
			// Walls get darker with distance
			int shade = Math.max(0, 255 - (int)(pd * 80));
			g.setColor(new Color(shade, 0, 0));

			
			int cellType = 0;
			if ((mapY >= 0 && mapY < map.length) && 
			(mapX >= 0 && mapX < map[0].length)) {
				cellType = map[mapY][mapX];
			}
			
			if (cellType == 2 && portal != null) {
				int texWidth = portal.getWidth();
				int texHeight = portal.getHeight();
				
				boolean hitVertical = Math.abs(ray.x2 - ray.x1) > Math.abs(ray.y2 - ray.y1);
				double wallX;
				if (hitVertical) {
					wallX = ray.y2 - Math.floor(ray.y2); // Vertical wall: use Y
				} else {
					wallX = ray.x2 - Math.floor(ray.x2); // Horizontal wall: use X
				}
				int texX = (int)(wallX * texWidth);
				texX = Math.min(Math.max(texX, 0), texWidth - 1);
				
				g.drawImage(portal,
					    i * wallWidth, top, i * wallWidth + wallWidth, top + wallHeight,
					    texX, 0, texX + 1, portal.getHeight(),
					    null);
				
			} else {
				g.setColor(new Color(shade, 0, 0));
				g.fillRect(i * wallWidth, top, wallWidth, wallHeight);
			}
		}
		
		
	}
}
