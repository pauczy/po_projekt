package pl.edu.pw.fizyka.pojava.id;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel{

	public AnimationPanel()  {	
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent( g );
		ImageIcon bgImage = new ImageIcon("img/niebo.png");
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bgImage.getImage(), 0, 0, null);
	}
}
