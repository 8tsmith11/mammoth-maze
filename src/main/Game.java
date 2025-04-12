package main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game {
	
	public static void main(String[] args) {
		int[][] map = {
	            {1, 0, 0, 0},
	            {1, 0, 1, 0},
	            {1, 1, 1, 1}
	        };
		World world = new World(map);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		RaycastView raycastView = new RaycastView(world, screenSize.width / 2, 0, screenSize);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		Frame frame = new Frame(gd.getDefaultConfiguration());
		frame.add(raycastView);
		frame.setUndecorated(true); // No window decorations
		gd.setFullScreenWindow(frame);
		
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
	}
}
 