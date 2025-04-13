package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameOver extends Canvas implements KeyListener {
    private BufferedImage image;
    public boolean reset;

    public GameOver(String imagePath, int width, int height) {
        try {
            image = ImageIO.read(new File(imagePath));
            this.setSize(width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.setFocusable(true);
        this.addKeyListener(this);
        this.requestFocusInWindow();
        reset = false;
    }

    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
        
        g.setColor(Color.BLACK);
        String text = "You've been Mammothed!";
	    Font font = new Font("Papyrus", Font.BOLD, 50);
	    g.setFont(font);
	    FontMetrics fm = g.getFontMetrics();
	    int x = (int)(getWidth() * 0.57);
	    int y = (fm.getAscent() + getHeight() / 30);

	    g.drawString(text, x, y);
	    
	    x += 20;
	    y += fm.getAscent() + 10; 
	    g.drawString("Press SPACE to restart!", x, y);
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        // Reset if space was pressed
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            reset = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}
