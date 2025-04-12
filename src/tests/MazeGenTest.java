package tests;

import main.MazeGen;

public class MazeGenTest {
	
	public static void main(String[] args) {
		MazeGen.initMaze();
		MazeGen.setPortal();
		MazeGen.generateMaze(1, 1);
		MazeGen.addMorePaths(20); // can set input to something else
		MazeGen.printMaze();
		
	}
	
}