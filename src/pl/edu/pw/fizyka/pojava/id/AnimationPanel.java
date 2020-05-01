package pl.edu.pw.fizyka.pojava.id;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


enum Location {
		EARTH, ROCKET, SPACE, TARGET;
	}

public class AnimationPanel extends JPanel implements Runnable{
	boolean dziala;
	public static Location loc;
	BufferedImage rakieta[], currentImage;
	ImageIcon bgImage, startBg;

	public AnimationPanel()  {
		dziala = true;
		loc = Location.EARTH;
		rakieta = loadImg("img/start/rakieta", 3);
		startBg = new ImageIcon("img/start1.png");
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (loc == Location.EARTH) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(bgImage.getImage(), 0, 0, null);
			g2d.drawImage(currentImage, 400, 100, null);
		}
	}
	
	public void run() {
		int i = 0;
		boolean revertAnimation = false;
		while (dziala == true) {
			if (loc == Location.EARTH) {//animacja na ziemi
				currentImage = rakieta[i];
				bgImage = startBg;
				this.repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i == rakieta.length - 1)
					revertAnimation = true;
				if (i == 0)
					revertAnimation = false;
				if (revertAnimation == false)
					++i;
				if (revertAnimation == true)
					--i;
			}
			
		}
			
	}	
	
	private BufferedImage[] loadImg(String s, int nmbImages){
		BufferedImage animation[] = new BufferedImage[nmbImages];
    	for (int i = 0; i<nmbImages; ++i){
    		try {
    			String string = s+String.valueOf(i+1)+".png";
				animation[i] = ImageIO.read(new File(string));
			} catch (IOException e) {
				System.err.println("Error loading images");
			}
    	}
    	return animation;
    }
}

