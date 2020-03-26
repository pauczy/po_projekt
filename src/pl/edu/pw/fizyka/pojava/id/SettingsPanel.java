package pl.edu.pw.fizyka.pojava.id;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.Hashtable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class SettingsPanel extends JPanel {

	public SettingsPanel() {
		
		ImageIcon earthIcon = new ImageIcon("img/ziemia.png");
		ImageIcon rocketIcon = new ImageIcon("img/rakieta.jpg");
		
		JLabel vLabel = new JLabel("prędkość: ");
		JLabel refLabel = new JLabel("układ osniesienia: ");
		JLabel destLabel = new JLabel("cel: ");
		vLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		refLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		destLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		JSlider vSlider = new JSlider(0,100);
		vSlider.setMajorTickSpacing(25);
		vSlider.setMinorTickSpacing(5);
		vSlider.setPaintTicks(true);
		Hashtable<Integer,JLabel> labelTable = new java.util.Hashtable<Integer,JLabel>();
	    labelTable.put(100, new JLabel("c"));
	    labelTable.put(75, new JLabel("0.75"));
	    labelTable.put(50, new JLabel("0.50"));
	    labelTable.put(25, new JLabel("0.25"));
	    labelTable.put(0, new JLabel("0.0"));
	    vSlider.setLabelTable( labelTable );
	    vSlider.setPaintLabels(true);
	    
	    JTextField vText = new JTextField(labelTable.get(vSlider.getValue()).getText() + "c");
	    vText.setColumns(5);
	    JPanel vPanel = new JPanel();
	    vPanel.add(vSlider);
	    vPanel.add(vText);
	    vPanel.setBackground(Color.WHITE);
	    
		
		JButton rocketB = new JButton(rocketIcon);
		JButton earthB = new JButton(earthIcon);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		buttonsPanel.add(earthB);
		buttonsPanel.add(rocketB);
		buttonsPanel.setBackground(Color.WHITE);
		
		
		
		JComboBox destinations = new JComboBox();
		
		JButton goButton = new JButton("w drogę!");
		goButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		goButton.setPreferredSize(new Dimension(150, 50));
		
	
		this.setBackground(Color.WHITE);
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(Box.createVerticalStrut(30));
		this.add(vLabel);
		this.add(Box.createVerticalStrut(20));
		this.add(vPanel);
		this.add(Box.createVerticalStrut(50));
		this.add(refLabel);
		this.add(Box.createVerticalStrut(20));
		this.add(buttonsPanel);
		this.add(destLabel);
		this.add(destinations);
		this.add(Box.createVerticalGlue());
		this.add(goButton);
		this.add(Box.createVerticalStrut(30));
		
		
		
		
	}

	

}
