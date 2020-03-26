package pl.edu.pw.fizyka.pojava.id;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainClass {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JFrame f = new JFrame("Symulator podróży międzygwiezdnych");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				f.add(new SettingsPanel(), BorderLayout.LINE_END);
				f.add(new AnimationPanel(), BorderLayout.CENTER);
				f.setJMenuBar(new MenuBar());
					
				f.setSize(800, 500);
				f.setVisible(true);
					
				}
			});
		}

}
