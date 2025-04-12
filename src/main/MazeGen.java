package main;

import java.util.*;


public class MazeGen {
	
	// just set random numbers for width and height, can change later
	private static final int WIDTH = 31;
	private static final int HEIGHT = 31;
	
	private static final int WALL = 1;
	private static final int SPACE = 0;
	
	private static final Random random = new Random();
	
	private static int[][] maze = new int[HEIGHT][WIDTH];
	
	
	public static void generateMaze(int x, int y ) {
	
		// mark the current cell as visited
		maze[y][x] = SPACE;
		
		// up right down left
		int[][] directions = { {0, -2}, {2, 0}, {0, 2}, {-2, 0} };
		
		shuffleArray(directions);
		
		for (int[] dir : directions) {
			// could try to move two steps at a time using * 2
			int nx = x + dir[0];
			int ny = y + dir[1];
			
			if (inBounds(nx, ny) && (maze[ny][nx] == 1)) {
				maze[y + dir[1] / 2][x + dir[0] / 2] = SPACE;
				generateMaze(nx, ny);
			}
			
		}
		
	}
	
	
	private static boolean inBounds(int x, int y) {
		return (x > 0 && x < WIDTH - 1) && (y > 0 && y < HEIGHT - 1);
		
	}
	
	
	private static void shuffleArray(int[][] array) {
		for (int i = array.length - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			int[] temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		
	}
	
	
	
	public static void initMaze() { 
		for (int y = 0; y < HEIGHT; y++)
			for (int x = 0; x < WIDTH; x++)
				maze[y][x] = WALL;
		
	}
	
	public static void printMaze() {
		// test function
		for (int[] row : maze) {
			for (int i : row) {
				System.out.print(i);
			}
			System.out.println();
		}
			
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

