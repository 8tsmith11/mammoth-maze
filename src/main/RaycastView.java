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
	private Mammoth mammoth;
	private Raycaster rc;
	

	
	// pranav
	private BufferedImage portal;
	BufferedImage mammothSprite;
	
	
	public RaycastView(World world, Player p, Mammoth m, Raycaster r) {
		map = world.getMap();
		player = p;
		this.rc = r;
		mammoth = m;
		
		try { 
			// put in path from git
			portal = ImageIO.read(new File("yugioh_portal.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mammothSprite = ImageIO.read(new File("mammoth.png"));
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
		
		// Draw Mammoth
		// Draw Mammoth
		if (mammoth != null && mammoth.isActive()) {
		    double dx = mammoth.getX() - player.x;
		    double dy = mammoth.getY() - player.y;

		    // Enforce tile-centered alignment: snap either horizontally or vertically
		    double mammothRenderX = mammoth.getX();
		    double mammothRenderY = mammoth.getY();

		    if (Math.abs(dx) > Math.abs(dy)) {
		        // Horizontal movement dominant: snap vertically (centered vertically)
		        mammothRenderY = Math.floor(mammoth.getY()) + 0.5;
		    } else {
		        // Vertical movement dominant: snap horizontally (centered horizontally)
		        mammothRenderX = Math.floor(mammoth.getX()) + 0.5;
		    }

		    dx = mammothRenderX - player.x;
		    dy = mammothRenderY - player.y;

		    double dirX = player.dirX;
		    double dirY = player.dirY;
		    double planeX = player.planeX;
		    double planeY = player.planeY;

		    double invDet = 1.0 / (planeX * dirY - dirX * planeY);
		    double transformX = invDet * (dirY * dx - dirX * dy);
		    double transformY = invDet * (-planeY * dx + planeX * dy);

		    if (transformY > 0.1) {
		        int spriteHeight = (int)(getHeight() / transformY);
		        int spriteWidth = (int)(spriteHeight * 0.75);

		        double screenCenter = (getWidth() / 2.0) * (1 + transformX / transformY);
		        int drawStartX = (int)Math.round(screenCenter - spriteWidth / 2.0);
		        int drawStartY = getHeight() / 2 - spriteHeight / 2;
		        int drawEndY = drawStartY + spriteHeight;

		        // Simple drawImage for clarity
		        g.drawImage(
		            mammothSprite,
		            drawStartX, drawStartY, drawStartX + spriteWidth, drawEndY,
		            0, 0, mammothSprite.getWidth(), mammothSprite.getHeight(),
		            null
		        );
		    }
		}

	}
}
