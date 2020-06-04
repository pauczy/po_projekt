package pl.edu.pw.fizyka.pojava.id;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar implements ActionListener{
	
	JMenuItem addDest, authors, instruction;
	SettingsPanel panel;

	public MenuBar(SettingsPanel panel) {
		
		this.panel = panel;
		JMenu edit = new JMenu("Edycja");
		addDest = new JMenuItem("Dodaj nowy cel");
		addDest.addActionListener(this);
		edit.add(addDest);
		
		JMenu about = new JMenu("O aplikacji");
		authors = new JMenuItem("Autorzy");
		authors.addActionListener(this);
		about.add(authors);
		instruction = new JMenuItem("Instrukcja");
		instruction.addActionListener(this);
		about.add(instruction);
		
		this.add(edit);
		this.add(about);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == authors) {
			JOptionPane.showMessageDialog(null, "Autorzy:\nPaulina Czyż\nJulian Nowak");
		}
		if(e.getSource() == instruction) {
			
		}
		if(e.getSource() == addDest) {
			String name = JOptionPane.showInputDialog(
			        null, 
			        "podaj nazwę nowego celu: ", 
			        "nowy cel", 
			        JOptionPane.QUESTION_MESSAGE
			    );
			float distance = Float.parseFloat(JOptionPane.showInputDialog(
			        null, 
			        "podaj odległość od Ziemi do celu (w latach świetlnych): ", 
			        "nowy cel", 
			        JOptionPane.QUESTION_MESSAGE
			    ));
			try {
				panel.addDestination(name, distance);
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.err.println("nie udało się dodać nowego celu");
			}
		}
	}

}
