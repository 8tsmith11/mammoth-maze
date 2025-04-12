package main;

import java.awt.geom.Line2D;

public class Raycaster {
	private Player player;
	private int[][] map;
	private Line2D.Double[] rays;
	
	// How much to increment by when checking for collisions
	private static final double STEP = 0.01;
	
	public Raycaster (Player player, int[][] map) {
		this.player = player;
		this.map = map;
		rays = new Line2D.Double[90]; // One ray per 90 deg FOV
	}
	
	public Line2D.Double[] getRays() {
		return rays;
	}
	
	public void castRays() {
<<<<<<< HEAD
		// Angle of the leftmost ray
		double startAngle = player.getAngle() - Math.toRadians(rays.length / 2);
		
		for (int i = 0; i < rays.length; i++) {
			double angle = startAngle + Math.toRadians(i);
			double dx = STEP * Math.cos(angle)
			double dy = STEP * Math.sin(angle);
			double hitX = player.x;
			double hitY = player.y;
		}
=======
		
>>>>>>> 61ab9f34e7ea96c9c0e904f4edacbf3e85dc5dde
	}
}
