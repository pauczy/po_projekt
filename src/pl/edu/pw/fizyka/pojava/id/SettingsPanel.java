package pl.edu.pw.fizyka.pojava.id;

import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsPanel extends JPanel {

	public SettingsPanel() {
		
		JSlider vSlider = new JSlider(0,1);
	
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JLabel("predkość: "));
		this.add(vSlider);
		
	}

	

}
