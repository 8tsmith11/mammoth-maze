package main;

import javax.sound.sampled.*;
import java.io.*;
import java.net.*;


public class SoundPlayer {

	
	public void playFootsteps() {
		playSound("footsteps.wav");
	}
	
	public void playEerie () {
		playSound("eerie.wav");
	}
	
	public void playSound(String fileName) {
		try {
			URL audioUrl = getClass().getClassLoader().getResource("resources/" + fileName);
			//File audioFile = new File("resources/" + fileName);
			System.out.println(audioUrl);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioUrl);
			
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			while (!clip.isRunning()) {
				Thread.sleep(10);
			}
			while (clip.isRunning()) {
				Thread.sleep(10);
			}
			clip.close();
		} catch(Exception e) {
			System.err.println("Audio file not found.");
			e.printStackTrace();	
		}

		
	}
	
	public static void main(String[] args) {
		SoundPlayer s = new SoundPlayer();
		s.playFootsteps();


	}

}
