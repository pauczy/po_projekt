package pl.edu.pw.fizyka.pojava.id;

import java.awt.BorderLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainClass {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JFrame f = new JFrame("Symulator podróży międzygwiezdnych");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				SettingsPanel settings = new SettingsPanel();
				f.add(settings, BorderLayout.LINE_END);
				AnimationPanel animation = new AnimationPanel();
				f.add(animation, BorderLayout.CENTER);
				MenuBar menu = new MenuBar();
				f.setJMenuBar(menu);
					
				f.setSize(1200, 700);
				f.setVisible(true);
				
				ExecutorService exec = Executors.newFixedThreadPool(3);
				exec.execute(animation);
				exec.execute(settings);
				exec.execute(menu);
				exec.shutdown();
					
				}
			});
		}

}
