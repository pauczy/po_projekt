package pl.edu.pw.fizyka.pojava.id;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer implements Runnable{
	String audioFilePath;
	Clip audioClip = null;
	File audioFile = null;
	AudioInputStream audioStream = null;
	File file;
    boolean playCompleted = false;
    
    SoundPlayer(){
    	
		audioFilePath = SoundPlayer.class.getResource("/music.wav").getPath();
    }
	
public void run() {
    	
        try {
            audioFile = new File(audioFilePath);
            audioStream = AudioSystem.getAudioInputStream(audioFile);
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
