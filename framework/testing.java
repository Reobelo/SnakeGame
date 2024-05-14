package framework;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class testing {
	public static void main(String [] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		Clip clip;
		URL[] soundURL = new URL[30];
		soundURL[0].getClass().getResource("/sounds/Dungeon.wav");
		URL url = soundURL[0];
		if (url == null) {
        	//System.out.println("Loading sound from: " + getClass().getResource("/sounds/piano.wav \n"));

            System.out.println("Resource not found: " + 0);
            return;
        }
        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();  // Start playing
        Thread.sleep(clip.getMicrosecondLength() / 1000);
        System.out.println("Audio loaded and clip opened: " + 0);
     
	/*	
		String filePath = "Dungeon.wav";
		File audioFile = new File(filePath);
		//AudioInputStream aui = new AudioInputStream((TargetDataLine) new File(filePath).getAbsoluteFile());
		
		if (!audioFile.exists()) {
            System.out.println("Error: File does not exist at " + filePath);
            return; // Stop execution if the file does not exist
        }
		
		 try {
	            AudioInputStream aui = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
	            Clip clip = AudioSystem.getClip();
	            clip.open(aui);
	            clip.start();
	            Thread.sleep(clip.getMicrosecondLength() / 1000);
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	        */
	}
	
}
