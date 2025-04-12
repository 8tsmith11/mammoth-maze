package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

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
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
