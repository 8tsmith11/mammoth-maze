package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
		
		g.setColor(Color.RED);
		g.drawRect(0, 0, getWidth(), getHeight());
		String text = "Mammoth Maze";
	    Font font = new Font("Papyrus", Font.BOLD, 36);
	    g.setFont(font);
	    FontMetrics fm = g.getFontMetrics();

	    int x = (getWidth() - fm.stringWidth(text)) / 2;
	    int y = (fm.getAscent() + getHeight() / 30);

	    g.drawString(text, x, y);
	    
	    font = new Font("Papyrus", Font.BOLD, 24);
	    text = "'WASD' to Move";
	    g.setFont(font);
	    fm = g.getFontMetrics();

	    x = (getWidth() - fm.stringWidth(text)) / 2;
	    y += getHeight() / 10;
	    g.drawString(text, x, y);
	    
	    text = "Find the exit.";
	    g.setFont(font);
	    fm = g.getFontMetrics();

	    x = (getWidth() - fm.stringWidth(text)) / 2;
	    y += getHeight() / 10;
	    g.drawString(text, x, y);
	    
	    text = "Don't get Mammothed!";
	    g.setFont(font);
	    fm = g.getFontMetrics();

	    x = (getWidth() - fm.stringWidth(text)) / 2;
	    y += getHeight() / 10;
	    g.drawString(text, x, y);
	    
	    font = new Font("Papyrus", Font.BOLD, 50);
	    text = "Level: " + level;
	    g.setFont(font);
	    fm = g.getFontMetrics();

	    x = (getWidth() - fm.stringWidth(text)) / 2;
	    y = getHeight() - 100;
	    g.drawString(text, x, y);
	}
}
