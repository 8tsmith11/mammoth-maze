package main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.event.KeyEvent;


public class Game {
	
	public static void main(String[] args) {
		
		// Initialize maze 
		MazeGen.initMaze();
		MazeGen.generateMaze(1, 1);
		MazeGen.setPortal();
		int[][] map = MazeGen.maze;

		World world = new World(map);
		Player player = new Player(world);
		Mammoth mammoth = new Mammoth(world, player, map[0].length - 2, map.length - 2);
		Raycaster raycaster = new Raycaster(player, world.getMap(), mammoth);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		RaycastView raycastView = new RaycastView(world, player, mammoth, raycaster);
		TopDownView topdownView = new TopDownView(world, player, mammoth, raycaster, 0, 0, width / 
				4, height);
		InfoView infoView = new InfoView();
		
		// Input handler
        InputHandler input = new InputHandler();
        
        // For playing the sound when mammoth spawns
        boolean mammothActive = false;
        
        // Player got mammothed
        boolean mammothed = false;

		SoundPlayer footstepSound = new SoundPlayer("footsteps.wav");
		SoundPlayer impactSound = new SoundPlayer("impact.wav");
		SoundPlayer mammothSound = new SoundPlayer("mammoth.wav");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		Frame frame = new Frame(gd.getDefaultConfiguration());
		frame.setLayout(null);
		
		// Views
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
        
        GameOver gameOver = new GameOver("gameover.jpeg", width, height);
        gameOver.setVisible(false);
        frame.add(gameOver);
        gameOver.setBounds(0, 0, width, height);
        
        // put gameover on top
        frame.setComponentZOrder(gameOver, 0);
        frame.validate();

		
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
        	if (mammothed) {      	
        		System.out.println("mammothed");
        		if (gameOver.reset) {
        			// Restart Game
                	mammothed = false;
                	gameOver.setVisible(false);
                	frame.requestFocusInWindow();
                	infoView.reset();
                	MazeGen.initMaze();
        			MazeGen.generateMaze(1, 1);
        			MazeGen.setPortal();
        			map = MazeGen.maze;
        			player.x = 1.5;
        			player.y = 1.5;
        			infoView.repaint();
        			gameOver.reset = false;
                }
        	}
        	
        	
        	
        	else {
        		long now = System.nanoTime();
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                
                raycaster.castRays();
                mammoth.update(deltaTime, raycaster.getRays());
                footstepSound.resetClip();

                // Movement input
                if (input.isKeyDown(KeyEvent.VK_W)) {
                	player.moveForward(deltaTime);
                	footstepSound.loopSound();
                }
                if (input.isKeyDown(KeyEvent.VK_S)) {
                	player.moveBackward(deltaTime);
                	footstepSound.loopSound();
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

                if (mammoth.isActive()) {
                	if (!mammothActive) {
                		impactSound.playSound();
                		mammothActive = true;
                	}
                } else {
                	mammothActive = false;
                }
                
                topdownView.repaint();
                raycastView.repaint();
                
                // Lose Condition
                if (mammoth.isActive() && Math.hypot(player.x - mammoth.getX(), player.y - mammoth.getY()) <= 1) {
                    mammothed = true;
                    mammothSound.playSound();
                    footstepSound.pause();
                    gameOver.setVisible(true);
                    gameOver.repaint();
                    gameOver.requestFocusInWindow();
                }

                

                // Go to next level when player reaches portal
                if (world.getMap()[(int)player.y][(int)player.x] == 2) {
            		MazeGen.initMaze();
        			MazeGen.generateMaze(1, 1);
        			MazeGen.setPortal();
        			map = MazeGen.maze;
        			player.x = 1.5;
        			player.y = 1.5;
        			infoView.incrementLevel();
        			infoView.repaint();
        			frame.requestFocus();
                }
                
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        	}
        	
        }


	}
}
 