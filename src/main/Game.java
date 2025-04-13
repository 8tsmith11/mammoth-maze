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
		MazeGen.setPortal();
		int[][] map = MazeGen.maze;
		map[1][0] = 2;

		World world = new World(map);
		Player player = new Player(world);
		Mammoth mammoth = new Mammoth(world, player, map[0].length - 2, map.length - 2);
		Raycaster raycaster = new Raycaster(player, world.getMap());
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		RaycastView raycastView = new RaycastView(world, player, raycaster);
		TopDownView topdownView = new TopDownView(world, player, mammoth, raycaster, 0, 0, width / 
				4, height);
		InfoView infoView = new InfoView();
		
		// Input handler
        InputHandler input = new InputHandler();	

		SoundPlayer footstepSound = new SoundPlayer("footsteps.wav");
		SoundPlayer impactSound = new SoundPlayer("impact.wav");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		Frame frame = new Frame(gd.getDefaultConfiguration());
		frame.setLayout(null);
		
		frame.add(raycastView);
		frame.add(topdownView);
		frame.add(infoView);
		topdownView.setBounds(0, 0, width / 4, width / 4);
        raycastView.setBounds(width / 4, 0, 3 * width / 4, height);
        infoView.setBounds(0, width / 4, width / 4, height - width / 4);
		frame.addKeyListener(input);
		raycastView.setFocusable(false);
        topdownView.setFocusable(false);
        infoView.setFocusable(false);

		
		frame.setUndecorated(true); // No window decorations
		gd.setFullScreenWindow(frame);
		frame.setVisible(true);
		frame.requestFocus();

		
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
		
		// load in the level screen showing level 1 and controls


		// MAIN GAME LOOP
        long lastTime = System.nanoTime();
        while (true) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;
            
            raycaster.castRays();
            mammoth.update(deltaTime, raycaster.getRays());
            footstepSound.resetClip();

            // Movement input
            if (input.isKeyDown(KeyEvent.VK_W)) {
            	player.moveForward(deltaTime);
            	footstepSound.playSound();
            }
            if (input.isKeyDown(KeyEvent.VK_S)) {
            	player.moveBackward(deltaTime);
            	footstepSound.playSound();
            }
            if (input.isKeyDown(KeyEvent.VK_A)) {
            	player.rotateLeft(deltaTime);
            }
            if (input.isKeyDown(KeyEvent.VK_D)) {
            	player.rotateRight(deltaTime);
            }
            if (!(input.isKeyDown(KeyEvent.VK_W) || input.isKeyDown(KeyEvent.VK_S))) {
            	footstepSound.pause();
            }

            
            topdownView.repaint();
            raycastView.repaint();

            if (world.getMap()[(int)player.y][(int)player.x] == 2) {
        		MazeGen.initMaze();
    			MazeGen.generateMaze(1, 1);
    			MazeGen.setPortal();
    			map = MazeGen.maze;
    			player.x = 1.5;
    			player.y = 1.5;
    			
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


	}
}
 