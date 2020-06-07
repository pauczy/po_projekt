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
				
				AnimationPanel animation = new AnimationPanel();
				JFrame f = new JFrame(animation.rb.getString("title"));
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				f.add(animation, BorderLayout.CENTER);
				SettingsPanel settings = new SettingsPanel(animation);
				f.add(settings, BorderLayout.LINE_END);	
				MenuBar menu = new MenuBar(settings);
				f.setJMenuBar(menu);
					
				f.setSize(1200, 700);
				f.setVisible(true);
				SoundPlayer player = new SoundPlayer();
				ExecutorService exec = Executors.newFixedThreadPool(2);
				exec.execute(animation);
				exec.execute(player);
				exec.shutdown();
					
				}
			});
		}

}
