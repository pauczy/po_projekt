package pl.edu.pw.fizyka.pojava.id;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// by Julian
public class SoundPlayer implements Runnable{
	Clip audioClip = null;
	AudioInputStream audioStream = null;
	File file;
    boolean playCompleted = false;
    InputStream inputStream = null;
    
    SoundPlayer(){
    	inputStream = SoundPlayer.class.getResourceAsStream("/music.wav");
    }
	
public void run() {
    	
        try {
        	InputStream bufferedInput = new BufferedInputStream(inputStream);
            audioStream = AudioSystem.getAudioInputStream(bufferedInput);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
           	while(!playCompleted){  
           		audioClip.start();
           		audioClip.loop(10);
     	    }
     	    audioClip.close();
     	    playCompleted = false;
            try {
				audioStream.close();
				 audioClip.open(audioStream);
            } catch (IOException e) {
            	e.printStackTrace();
 			}
         
            

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error playing the audio file.");
			e1.printStackTrace();
		} 
    }
  

}
