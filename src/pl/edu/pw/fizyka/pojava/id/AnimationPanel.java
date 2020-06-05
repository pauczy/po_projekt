package pl.edu.pw.fizyka.pojava.id;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


enum Location {
		EARTH, ROCKET, SPACE, TARGET;
	}

enum Reference {
	EARTH, ROCKET
}

public class AnimationPanel extends JPanel implements Runnable{
	
	boolean dziala, reachedTarget;
	public static Location loc;
	public  static Reference ref;
	int xPos, yPos, yBg;
	BufferedImage rakieta[], rakietaStart[], rakietaLot[], currentImage;
	ImageIcon bgImage, bgImageScaled, startBg, spaceBg, targetBg, currentIcon;
	Target target;
	double velocity;
	ResourceBundle rb;

	public AnimationPanel()  {
		dziala = true;
		loc = Location.EARTH;
		ref = Reference.EARTH;
		rakietaStart = loadImg("/start/rakieta", 3);
		rakietaLot = loadImg("/lot/rakieta", 3);
		rakieta = rakietaStart;
		startBg = new ImageIcon(AnimationPanel.class.getResource("/start.png"));
		spaceBg = new ImageIcon(AnimationPanel.class.getResource("/niebo.png"));
		targetBg = new ImageIcon(AnimationPanel.class.getResource("/target.jpg"));
		yBg = AnimationPanel.this.getSize().width;
		
		Locale currentLocale = new Locale("fr");
		rb = ResourceBundle.getBundle("LabelsBundle",currentLocale);
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int panelWidth = (int)AnimationPanel.this.getSize().width;
		int panelHeight = (int)AnimationPanel.this.getSize().height;
		if (loc == Location.EARTH) {
			rakieta = rakietaStart;
			bgImage = startBg;
			Image bgImageS = bgImage.getImage();
			bgImageS = bgImageS.getScaledInstance(panelWidth, panelHeight,  Image.SCALE_SMOOTH); 
			bgImageScaled = new ImageIcon(bgImageS);
			g2d.drawImage(bgImageScaled.getImage(), 0, 0, null);
			Image currentImageScaled =  ((Image) currentImage).getScaledInstance((int) (panelWidth*0.2), (int) (currentImage.getHeight()/currentImage.getWidth()*panelWidth*0.3), Image.SCALE_SMOOTH);
			g2d.drawImage(currentImageScaled, (int) (panelWidth*0.45), (int) (AnimationPanel.this.getSize().height*0.45), null);
		}
		if (loc == Location.SPACE) {
			rakieta = rakietaLot;
			bgImage = spaceBg;
			Image bgImageS = bgImage.getImage();
			bgImageS = bgImageS.getScaledInstance(panelWidth, panelHeight,  Image.SCALE_SMOOTH); 
			bgImageScaled = new ImageIcon(bgImageS);
			g2d.drawImage(bgImageScaled.getImage(), 0, 0, null);
			Image currentImageScaled =  ((Image) currentImage).getScaledInstance((int) (panelWidth*0.09), (int) (currentImage.getWidth()/currentImage.getHeight()*panelWidth*0.06), Image.SCALE_SMOOTH);
			yPos = (int) (panelHeight*0.2);
			g2d.drawImage(currentImageScaled, xPos, yPos, null);
		}
		if (loc == Location.TARGET) {
			rakieta = rakietaLot;
			bgImage = targetBg;
			Image bgImageS = bgImage.getImage();
			bgImageS = bgImageS.getScaledInstance(panelWidth, panelHeight,  Image.SCALE_SMOOTH); 
			bgImageScaled = new ImageIcon(bgImageS);
			g2d.drawImage(bgImageScaled.getImage(), 0, 0, null);
			Image currentImageScaled =  ((Image) currentImage).getScaledInstance((int) (panelWidth*0.27), (int) (currentImage.getWidth()/currentImage.getHeight()*panelWidth*0.18), Image.SCALE_SMOOTH);
			g2d.drawImage(currentImageScaled, (int) (panelWidth*0.3), (int) (panelHeight*0.25), null);
		}
		if (loc == Location.ROCKET) {
			rakieta = rakietaLot;
			bgImage = spaceBg;
			Image bgImageS = bgImage.getImage();
			bgImageS = bgImageS.getScaledInstance(panelWidth*3, panelHeight,  Image.SCALE_SMOOTH); 
			bgImageScaled = new ImageIcon(bgImageS);
			g2d.drawImage(bgImageScaled.getImage(), yBg, 0, null);
			Image currentImageScaled =  ((Image) currentImage).getScaledInstance((int) (panelWidth*0.27), (int) (currentImage.getWidth()/currentImage.getHeight()*panelWidth*0.18), Image.SCALE_SMOOTH);
			g2d.drawImage(currentImageScaled, (int) (panelWidth*0.3), (int) (panelHeight*0.25), null);
		}
	}
	
	public void run() {
		int i = 0;
		xPos= 0;
		reachedTarget = false;
		boolean revertAnimation = false;
		while (dziala == true) {
			if (loc == Location.EARTH) {//animacja na ziemi
				currentImage = rakieta[i];				
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
			if (loc == Location.SPACE) {//animacja w ukladzie ziemi				
				currentImage = rakieta[i];				
				this.repaint();
				try {
					int sleep =  (int) (15 / SettingsPanel.velocity);
					Thread.sleep(sleep);
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
				if (xPos < AnimationPanel.this.getSize().width - 50)
					xPos +=10;
				else {
					loc = Location.TARGET;
					xPos = 0;
					showResults(target, velocity);
				}
			}
			if (loc == Location.ROCKET) {//animacja w ukladzie rakiety				
				currentImage = rakieta[i];				
				this.repaint();
				try {
					int sleep =  (int) (15 / SettingsPanel.velocity);
					Thread.sleep(sleep);
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
				if (yBg > -bgImage.getIconWidth())
					yBg -=10;
				else {
					loc = Location.TARGET;
					xPos = 0;
					yBg = AnimationPanel.this.getSize().width;
					showResults(target, velocity);
				}
			}
				if (loc == Location.TARGET) {//animacja w ukladzie ziemi				
					currentImage = rakieta[i];				
					this.repaint();
					try {
						Thread.sleep(150);
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
				animation[i] = ImageIO.read(AnimationPanel.class.getResource(string));
			} catch (IOException e) {
				System.err.println("Error loading images");
			}
    	}
    	return animation;
    }
	
	public void showResults(Target target, double velocity) {
		double v = velocity * Calculator.C;
		double t0 = target.getDistanceInMetres() / v;
		double t = Calculator.dilation(t0, v);
		double l = Calculator.contraction(target.getDistanceInMetres(), v);
		String options[] = {rb.getString("btn.quit"), rb.getString("btn.save")};
		String wynik;
		wynik = String.format(rb.getString("msg.dest") + " %s!\n" + rb.getString("msg.time") + " %.3e s.\n" + rb.getString("msg.timeE") + " %.3e s.\n" + rb.getString("msg.dist") +"%.3e km."
				+ rb.getString("msg.distR") + "%.3e km.", 
				target.getName(),t, t0, l*0.001, target.getDistanceInMetres()*0.001 );
		int result = JOptionPane.showOptionDialog(null, wynik, "wynik", JOptionPane.YES_NO_OPTION,
	               JOptionPane.INFORMATION_MESSAGE, null, options, options[0] );
	    if(result == JOptionPane.YES_OPTION){
	            JFileChooser fc = new JFileChooser();  
	 			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	 				try {
	 		            File outputFile = fc.getSelectedFile();
	 		            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputFile),
	 			                Charset.forName("UTF-8").newEncoder());
	 					osw.write(wynik);
	 					osw.close();
	 				}catch (IOException e) {
	 				System.out.println(e.getMessage());}
	 			}
	 			
	    }
	    	loc = Location.EARTH;
	    	rakieta = rakietaStart;

	}
}

