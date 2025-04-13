package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class InfoView extends Canvas {
	private int level;
	
	public InfoView() {
		level = 1;
	}
	
	public void updateLevel(int level) {
		this.level = level;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
