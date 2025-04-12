package main;

import java.awt.geom.Line2D;

public class Raycaster {
	private Player player;
	private int[][] map;
	private Line2D.Double[] rays;
	
	public Raycaster (Player player, int[][] map) {
		this.player = player;
		this.map = map;
		rays = new Line2D.Double[90]; // One ray per 90 deg FOV
	}
	
	public Line2D.Double[] getRays() {
		return rays;
	}
	
	public void castRays() {
		for (int i = 0; i < rays.length; i++) {
			double angle = 
		}
	}
}
