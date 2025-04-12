package main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;


public class Game {
	
	public static void main(String[] args) {
		
//		int[][] map = {
//	            {1, 0, 0, 0},
//	            {1, 0, 1, 0},
//	            {1, 0, 0, 1},
//	            {1, 1, 1, 1}
//	    };
		
		MazeGen.initMaze();
		MazeGen.generateMaze(1, 1);
		int[][] map = MazeGen.maze;

		World world = new World(map);
		Player player = new Player(world);
		Raycaster raycaster = new Raycaster(player, world.getMap());
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		RaycastView raycastView = new RaycastView(world, player, raycaster);
		TopDownView topdownView = new TopDownView(world, player, raycaster, 0, 0, width / 
				2, height);
		
		// Input handler
        InputHandler input = new InputHandler();	

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		Frame frame = new Frame(gd.getDefaultConfiguration());
		frame.setLayout(null);
		
		frame.add(raycastView);
		frame.add(topdownView);
		topdownView.setBounds(0, 0, width / 2, height);
        raycastView.setBounds(width / 2, 0, width / 2, height);
		frame.addKeyListener(input);
		raycastView.setFocusable(false);
        topdownView.setFocusable(false);

		
		frame.setUndecorated(true); // No window decorations
		gd.setFullScreenWindow(frame);
		frame.setVisible(true);
		frame.requestFocus();

		
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


		// MAIN GAME LOOP
        long lastTime = System.nanoTime();
        while (true) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;
            
            raycaster.castRays();

            // Movement input
            if (input.isKeyDown(KeyEvent.VK_W)) {
            	player.moveForward(deltaTime);
            }
            if (input.isKeyDown(KeyEvent.VK_S)) {
            	player.moveBackward(deltaTime);
            }
            if (input.isKeyDown(KeyEvent.VK_A)) {
            	player.rotateLeft(deltaTime);
            }
            if (input.isKeyDown(KeyEvent.VK_D)) {
            	player.rotateRight(deltaTime);
            }

            
            topdownView.repaint();
            raycastView.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


	}
}
 