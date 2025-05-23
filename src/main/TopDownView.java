package main;

import java.awt.*;
import java.awt.geom.Line2D;

public class TopDownView extends Canvas {
	private static final long serialVersionUID = 1L;
	private int[][] map;
	private Player player;
	private Mammoth mammoth;
	private Raycaster raycaster;
	private int x, y; // Position of view
	private int width, height; // Size of view
	private Dimension screenSize;

	public TopDownView(World world, Player p, Mammoth m, Raycaster r, int x, int y, int width, int height) {
		map = world.getMap();
		player = p;
		raycaster = r;
		mammoth = m;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(this.x, this.y);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(x, y, width, height);
		
		// set cellSize using width or height of screen depending on which is larger
		int cellSize = ((this.width < this.height) ? this.width : this.height) / this.map.length;
		
		for (int row = 0; row < this.map.length; row++) {
			for (int col = 0; col < this.map[row].length; col++) {
				g2.setColor((this.map[row][col] == 0) ? Color.BLACK : 
					Color.RED);
				if (this.map[row][col] == 2)
					g2.setColor(Color.YELLOW);
				g2.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				
				// Border around each cell
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.DARK_GRAY);
	            g2.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
	            
	            
			}
		}
		
		// Draw Rays
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.YELLOW);
		for (Line2D.Double ray : raycaster.getRays()) {
			g2.drawLine((int)(ray.x1 * cellSize), (int)(ray.y1 * cellSize), 
					(int)(ray.x2 * cellSize), (int)(ray.y2 * cellSize));
		}

		// Draw Player
		g2.setColor(Color.BLUE);
		int s = (int) (Player.SIZE * cellSize); // Player size in pixels
		g2.fillOval((int)(player.x * cellSize - s / 2), (int)(player.y * cellSize - s / 2), s, s);
		
		
		
		// Player Direction Indicator
		g2.setStroke(new BasicStroke(10));
		g2.drawLine((int)(player.x * cellSize), (int)(player.y * cellSize), 
				(int)((player.x + player.dirX / 4) * cellSize), (int)((player.y + player.dirY / 4) * cellSize));
		
		// Draw Mammoth
		if (mammoth.isActive()) {
			g2.setColor(Color.WHITE);
			g2.fillRect((int)(mammoth.getX() * cellSize), (int)(mammoth.getY() * cellSize), cellSize, cellSize);
		}
        
        g2.dispose();
	}
	
}
