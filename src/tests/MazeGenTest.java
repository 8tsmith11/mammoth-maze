package tests;

import main.MazeGen;

public class MazeGenTest {
	
	public static void main(String[] args) {
		MazeGen.initMaze();
		MazeGen.generateMaze(1, 1);
		MazeGen.printMaze();
		
	}
	
}