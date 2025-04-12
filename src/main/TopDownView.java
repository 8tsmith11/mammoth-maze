package main;

import java.awt.*;

public class TopDownView extends Canvas {
	private static final long serialVersionUID = 1L;
	private int[][] map;
	private int x, y; // Position of view
	private int width, height; // Size of view
	private Dimension screenSize;

	public TopDownView(World world, int x, int y, int width, int height) {
		map = world.getMap();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics g2 = g.create();
		g2.translate(this.x, this.y);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(x, y, width, height);
		
		// set cellSize using width or height of screen depending on which is larger
		int cellSize = ((this.width < this.height) ? this.width : this.height) / this.map.length;
		
		for (int row = 0; row < this.map.length; row++) {
			for (int col = 0; col < this.map[row].length; col++) {
				g2.setColor((this.map[row][col] == 0) ? Color.BLACK : 
					Color.RED);
				g2.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				
				// Border around each cell
				g2.setColor(Color.gray);
	            g2.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
			}
		}
        
        g2.dispose();
	}
	
}
