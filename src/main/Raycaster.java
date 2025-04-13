package main;

import java.awt.geom.Line2D;

public class Raycaster {
	private Player player;
	private int[][] map;
	private Mammoth mammoth;
	private Line2D.Double[] rays;
	
	// How much to increment by when checking for collisions
	private static final double STEP = 0.01;
	
	public Raycaster (Player player, int[][] map, Mammoth m) {
		this.player = player;
		mammoth = m;
		this.map = map;
		rays = new Line2D.Double[90]; // One ray per 90 deg FOV
	}
	
	public Line2D.Double[] getRays() {
		return rays;
	}
	
	public void castRays() {
		// Angle of the leftmost ray
		double startAngle = player.getAngle() - Math.toRadians(rays.length / 2);
		
		for (int i = 0; i < rays.length; i++) {
			double angle = startAngle + Math.toRadians(i);
			double dx = STEP * Math.cos(angle);
			double dy = STEP * Math.sin(angle);
			double hitX = player.x;
			double hitY = player.y;
			while (hitX > 0 && hitX < map[0].length && hitY > 0 && hitY < map.length
				&& map[(int)hitY][(int)hitX] == 0 && (!mammoth.isActive() || !hitMammoth(hitX, hitY))) {
				hitX += dx;
				hitY += dy;
			}
			rays[i] = new Line2D.Double(player.x, player.y, hitX, hitY);
 		}
	}
	
	private boolean hitMammoth(double x, double y) {
		return (x >= mammoth.getX() && x <= mammoth.getX() + 1 && 
				y >= mammoth.getY() && y <= mammoth.getY() + 1);
	}
}
