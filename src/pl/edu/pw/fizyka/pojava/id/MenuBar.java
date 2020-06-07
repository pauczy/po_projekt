package pl.edu.pw.fizyka.pojava.id;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuBar extends JMenuBar implements ActionListener {
	
	JMenuItem addDest, authors, instruction;
	SettingsPanel panel;

	public MenuBar(SettingsPanel panel) {
		
		this.panel = panel;
		JMenu edit = new JMenu(panel.rb.getString("menu.edit"));
		addDest = new JMenuItem(panel.rb.getString("menu.newDestination"));
		addDest.addActionListener(this);
		edit.add(addDest);
		
		JMenu about = new JMenu(panel.rb.getString("menu.about"));
		authors = new JMenuItem(panel.rb.getString("menu.authors"));
		authors.addActionListener(this);
		about.add(authors);
		instruction = new JMenuItem(panel.rb.getString("menu.instruction"));
		instruction.addActionListener(this);
		about.add(instruction);
		
		this.add(edit);
		this.add(about);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == authors) {
			JOptionPane.showMessageDialog(null, panel.rb.getString("menu.authors.msg"));
		}
		if(e.getSource() == instruction) {
			
		}
		if(e.getSource() == addDest) {
			String name = JOptionPane.showInputDialog(
			        null, 
			        panel.rb.getString("menu.dest.name"), 
			        panel.rb.getString("menu.dest.title"), 
			        JOptionPane.QUESTION_MESSAGE
			    );
			if ((name != null) && (name.length() > 0)) {
				
				JPanel input = new JPanel(new GridLayout(3, 1));
				JTextField textField = new JTextField(10);
				JComboBox<String> units = new JComboBox<>(new String[]{"km", "au", "ly"});
				input.add(new JLabel(panel.rb.getString("menu.dest.dist")));
				input.add(textField);
				input.add(units);

				int result = JOptionPane.showConfirmDialog(null, input, panel.rb.getString("menu.dest.title"), JOptionPane.OK_CANCEL_OPTION);
				if(result == JOptionPane.OK_OPTION) {
					try {
						float distance = Float.parseFloat(textField.getText());
						String unit = (String)units.getSelectedItem();
						if (unit == "km") distance *= 1.057 * Math.pow(10, -13);
						if (unit == "au") distance *= 1.58125 * Math.pow(10, -5);
						panel.addDestination(name, distance);
					} catch (SQLException e1) {
						System.err.println(panel.rb.getString("menu.dest.err"));
					}catch (NumberFormatException e2) {
						System.err.println("Incorrect number format");
					}
				}
			}		
		}
	}

	}
