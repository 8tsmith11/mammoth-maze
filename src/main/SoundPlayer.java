package main;

import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class SoundPlayer {
	private long currentFrame = 0L;
	private Clip clip;
	private AudioInputStream inputStream;
	private boolean isPlaying;
	
	public SoundPlayer(String fileName) {
	    try {
	        File audioFile = new File(fileName);
	        this.inputStream = AudioSystem.getAudioInputStream(audioFile);
	        this.clip = AudioSystem.getClip();
	        this.clip.open(this.inputStream);
	    } catch(Exception e) {
	        System.err.println("Audio file not found.");
	        e.printStackTrace();    
	    }    
	}
	
	public void playSound() {
	    try {
	        if (!this.isPlaying) {
	            this.clip.setMicrosecondPosition(this.currentFrame);
	            this.clip.start();
	            this.isPlaying = true;
	        }
	    } catch (Exception e) {
	        System.err.println("Error playing audio.");
	        e.printStackTrace();
	    }
	}
	
	public void pause() {
		this.currentFrame = this.clip.getMicrosecondPosition();
		this.isPlaying = false;
		this.clip.stop();
	}
	
	private void resetClip() {
		if (this.clip.getMicrosecondPosition() >= this.clip.getMicrosecondLength()) {
			this.clip.setMicrosecondPosition(0);
		}
	}
}
