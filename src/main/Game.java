package main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game {
	
	public static void main(String[] args) {
		int[][] map = {
	            {1, 0, 0, 0},
	            {1, 0, 1, 0},
	            {1, 0, 0, 1},
	            {1, 1, 1, 1}
	        };
		World world = new World(map);
		Player player = new Player(world);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		RaycastView raycastView = new RaycastView(world, player, width / 2, 0, width / 
				2, height);
		TopDownView topdownView = new TopDownView(world, player, 0, 0, width / 
				2, height);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		Frame frame = new Frame(gd.getDefaultConfiguration());
		
		frame.add(raycastView);
		frame.add(topdownView);
		
		frame.setUndecorated(true); // No window decorations
		gd.setFullScreenWindow(frame);
		frame.setVisible(true);

		
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
	}
}
 