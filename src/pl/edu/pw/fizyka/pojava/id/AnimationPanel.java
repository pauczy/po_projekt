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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

//by Julian
public class AnimationPanel extends JPanel implements Runnable{
	
	boolean runs, reachedTarget;
	public static Location loc;
	public  static Reference ref;
	int xPos, yPos, yBg;
	BufferedImage rocket[], rocketStart[], rocketFlight[], currentImage;
	ImageIcon bgImage, bgImageScaled, startBg, spaceBg, targetBg, currentIcon;
	Target target;
	double velocity;
	ResourceBundle rb;

	public AnimationPanel()  {
		runs = true;
		loc = Location.EARTH;
		ref = Reference.EARTH;
		rocketStart = loadImg("/start/rakieta", 3);
		rocketFlight = loadImg("/lot/rakieta", 3);
		rocket = rocketStart;
		startBg = new ImageIcon(AnimationPanel.class.getResource("/start.png"));
		spaceBg = new ImageIcon(AnimationPanel.class.getResource("/niebo.png"));
		targetBg = new ImageIcon(AnimationPanel.class.getResource("/target.jpg"));
		yBg = AnimationPanel.this.getSize().width;
		
		Locale currentLocale = new Locale(System.getProperty("user.language"));
		rb = ResourceBundle.getBundle("LabelsBundle",currentLocale);
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int panelWidth = (int)AnimationPanel.this.getSize().width;
		int panelHeight = (int)AnimationPanel.this.getSize().height;
		if (loc == Location.EARTH) {
			rocket = rocketStart;
			bgImage = startBg;
			Image bgImageS = bgImage.getImage();
			bgImageS = bgImageS.getScaledInstance(panelWidth, panelHeight,  Image.SCALE_SMOOTH); 
			bgImageScaled = new ImageIcon(bgImageS);
			g2d.drawImage(bgImageScaled.getImage(), 0, 0, null);
			Image currentImageScaled =  ((Image) currentImage).getScaledInstance((int) (panelWidth*0.2), (int) (currentImage.getHeight()/currentImage.getWidth()*panelWidth*0.3), Image.SCALE_SMOOTH);
			g2d.drawImage(currentImageScaled, (int) (panelWidth*0.45), (int) (AnimationPanel.this.getSize().height*0.45), null);
		}
		if (loc == Location.SPACE) {
			rocket = rocketFlight;
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
			rocket = rocketFlight;
			bgImage = targetBg;
			Image bgImageS = bgImage.getImage();
			bgImageS = bgImageS.getScaledInstance(panelWidth, panelHeight,  Image.SCALE_SMOOTH); 
			bgImageScaled = new ImageIcon(bgImageS);
			g2d.drawImage(bgImageScaled.getImage(), 0, 0, null);
			Image currentImageScaled =  ((Image) currentImage).getScaledInstance((int) (panelWidth*0.27), (int) (currentImage.getWidth()/currentImage.getHeight()*panelWidth*0.18), Image.SCALE_SMOOTH);
			g2d.drawImage(currentImageScaled, (int) (panelWidth*0.3), (int) (panelHeight*0.25), null);
		}
		if (loc == Location.ROCKET) {
			rocket = rocketFlight;
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
		while (runs == true) {
			if (loc == Location.EARTH) {//animacja na ziemi
				currentImage = rocket[i];				
				this.repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i == rocket.length - 1)
					revertAnimation = true;
				if (i == 0)
					revertAnimation = false;
				if (revertAnimation == false)
					++i;
				if (revertAnimation == true)
					--i;
			}
			if (loc == Location.SPACE) {//animacja w ukladzie ziemi				
				currentImage = rocket[i];				
				this.repaint();
				try {
					int sleep =  (int) (15 / SettingsPanel.velocity);
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i == rocket.length - 1)
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
				currentImage = rocket[i];				
				this.repaint();
				try {
					int sleep =  (int) (15 / SettingsPanel.velocity);
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i == rocket.length - 1)
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
					currentImage = rocket[i];				
					this.repaint();
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (i == rocket.length - 1)
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
	//by Paulina
	public void showResults(Target target, double velocity) {
		
				double v = velocity * Calculator.C;
				double t0 = target.getDistanceInMetres() / v;
				double t = Calculator.dilation(t0, v);
				double l = Calculator.contraction(target.getDistanceInMetres(), v);
				String options[] = {rb.getString("btn.save"), rb.getString("btn.quit")};
				String wynik;
				wynik = String.format(rb.getString("msg.dest") + target.getName() +"!\n" 
						+ rb.getString("msg.time") +Calculator.timeToString(t) + "\n" 
						+ rb.getString("msg.timeE") + Calculator.timeToString(t0) + "\n" 
						+ rb.getString("msg.dist") +" %.3e km  / %.3e au.\n"
						+ rb.getString("msg.distR") + " %.3e km / %.3e au.\n", 
						 l*0.001, Calculator.distanceAu(l),
						 target.getDistanceInMetres()*0.001, Calculator.distanceAu(target.getDistanceInMetres()) );
				int result = JOptionPane.showOptionDialog(null, wynik, rb.getString("msg.result"), JOptionPane.YES_NO_OPTION,
			               JOptionPane.INFORMATION_MESSAGE, null, options, options[0] );
			    if(result == JOptionPane.YES_OPTION){
			    	ExecutorService exec = Executors.newSingleThreadExecutor();
			    	exec.execute(new Runnable() {
			    		public void run() {
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
			    	});
			            
			 			
			    }
				
			
		
	    	loc = Location.EARTH;
	    	rocket = rocketStart;

	}
}

