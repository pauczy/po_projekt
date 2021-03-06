package pl.edu.pw.fizyka.pojava.id;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.h2.tools.Server;

public class SettingsPanel extends JPanel implements ActionListener{
	
	public static double velocity;
	JTextField vText;
	JComboBox<String> destinations;
	JToggleButton earthB, rocketB;
	JButton goButton;
	JSlider vSlider;
	Connection conn = null;
	AnimationPanel animation;
	ResourceBundle rb;
	Server server;
	
	public SettingsPanel(AnimationPanel animation) {
		
		this.animation = animation;
		
		ImageIcon earthIcon = new ImageIcon(SettingsPanel.class.getResource("/ziemia.png"));
		ImageIcon rocketIcon = new ImageIcon(SettingsPanel.class.getResource("/rakieta.jpg"));
		
		
		rb = animation.rb;
		
		//labels
		JLabel vLabel = new JLabel(rb.getString("lbl.velocity"));
		JLabel refLabel = new JLabel(rb.getString("lbl.reference"));
		JLabel destLabel = new JLabel(rb.getString("lbl.destination"));
		vLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		refLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		destLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		//setting velocity
		velocity = 0.5;
		JSlider vSlider = new JSlider(0,100, 50);
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
	    ChangeListener SliderListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
		        JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		             velocity = (double) source.getValue()/100;  
		             vText.setText(String.valueOf(String.valueOf(velocity)+ "c"));
		        }
			}
		 };
		 vSlider.addChangeListener(SliderListener);
	    
	    vText = new JTextField(labelTable.get(vSlider.getValue()).getText() + "c");
	    vText.setColumns(5);
	    JPanel vPanel = new JPanel();
	    vPanel.add(vSlider);
	    vPanel.add(vText);
	    vPanel.setBackground(Color.WHITE);
	    
		//setting reference frame
		rocketB = new JToggleButton(rocketIcon);
		rocketB.addActionListener(this);
		earthB = new JToggleButton(earthIcon, true);
		earthB.addActionListener(this);
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(rocketB);
		buttons.add(earthB);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		buttonsPanel.add(earthB);
		buttonsPanel.add(rocketB);
		buttonsPanel.setBackground(Color.WHITE);
		
		//adding destinations
		destinations = new JComboBox<String>();
		try {
			loadDestinations();
		}catch(SQLException ex) {
			System.err.println("błąd wczytywania destynacji");
		}
		
		//starting animation
		goButton = new JButton(rb.getString("btn.go"));
		goButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		goButton.setPreferredSize(new Dimension(150, 50));
		goButton.addActionListener(this);
	
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
	
	//from here by Paulina
	public void loadDestinations() throws SQLException{
				try {
					server = Server.createTcpServer().start();
					conn = DriverManager.getConnection(	"jdbc:h2:tcp://localhost/~/test", "sa", "");
						Statement stmt = conn.createStatement();
						stmt.execute("SELECT `name` FROM `destinations`");
						ResultSet rs = stmt.getResultSet();
						while(rs.next()) {
							destinations.addItem(String.valueOf(rs.getObject(1)));	
						}
						
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					if (conn!= null){
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
			}	

		
	
	
	public void addDestination(String name, float distance) throws SQLException{
		try {
			server = Server.createTcpServer().start();
			conn = DriverManager.getConnection(	"jdbc:h2:tcp://localhost/~/test", "sa", "");
			PreparedStatement prep = conn.prepareStatement("INSERT into destinations(name, distance) values (?, ?)");
			prep.setString(1, name);
			prep.setString(2, String.valueOf(distance));
			prep.executeUpdate();
			Statement stmt = conn.createStatement();
			stmt.execute("SELECT `name` FROM `destinations` ORDER BY `id` DESC ");
			ResultSet rs = stmt.getResultSet();
			if(rs.next()) destinations.addItem(String.valueOf(rs.getObject("name")));		
		}finally {
			if (conn!= null){
				conn.close();
			}
		}
	}
	
	public Target getTarget(int i) throws SQLException{
		String name = "";
		float distance = 0;
		try {
			 server = Server.createTcpServer().start();
			conn = DriverManager.getConnection(	"jdbc:h2:tcp://localhost/~/test", "sa", "");
			PreparedStatement prep = conn.prepareStatement("SELECT `name`, `distance` FROM `destinations` WHERE `id`= ?");
			prep.setString(1, String.valueOf(i+1));
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
				distance = rs.getFloat("distance");
			}
		}finally {
			if (conn!= null){
				conn.close();
			}
		}
		Target target = new Target(name, distance);
		return target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == earthB) {
			animation.ref = Reference.EARTH;
		}
		if(e.getSource() == rocketB) {
			animation.ref = Reference.ROCKET;
		}
		if(e.getSource() == goButton){
			int targetIndex = destinations.getSelectedIndex();
			Target target = null;
			if (velocity == 1){
				JOptionPane.showMessageDialog(null, rb.getString("vmsg1"), "błąd", JOptionPane.ERROR_MESSAGE);
			}else if (velocity == 0){
				JOptionPane.showMessageDialog(null, rb.getString("vmsg2"), "błąd", JOptionPane.ERROR_MESSAGE);
			}else {
				try {
					if (animation.ref == Reference.EARTH)
							animation.loc = Location.SPACE;
					if (animation.ref == Reference.ROCKET)
						animation.loc = Location.ROCKET;
					target = getTarget(targetIndex);
					animation.target = target;
					animation.velocity = velocity;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	
}
