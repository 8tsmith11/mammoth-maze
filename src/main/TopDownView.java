package main;

import java.awt.*;

public class TopDownView {
	private int[][] map;

	public TopDownView(World world) {
		this.map = world.getMap();
	}

	/* draws view at (x, y) on the main view */
	public void drawView(Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(x, y);

		// screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int cellSize = (width / 2 > height) ? width : height;
		
		for (int row = 0; row < this.map.length; row++ ) {
			for (int col = 0; col < this.map[row].length; col++ ) {
				g2.drawRect(row, col, cellSize * row, cellSize * col);
			}
		}
		g2.dispose();
	}
	
}
