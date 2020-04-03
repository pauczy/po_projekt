package pl.edu.pw.fizyka.pojava.id;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar {
	
	JMenuItem authors, instruction;

	public MenuBar() {
		
		JMenu file = new JMenu("Plik");
		JMenuItem save = new JMenuItem("Zapisz");
		file.add(save);
		
		JMenu edit = new JMenu("Edycja");
		JMenuItem addDest = new JMenuItem("Dodaj nowy cel");
		edit.add(addDest);
		
		this.add(file);
		this.add(edit);
		
		JMenu about = new JMenu("O aplikacji");
		authors = new JMenuItem("Autorzy");
		instruction = new JMenuItem("Instrukcja");
		about.add(instruction);
		about.add(authors);
		ActionListener aboutListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == authors){
					JOptionPane.showMessageDialog(null, "Autorzy:\nPaulina Czyż\nJulian Nowak");

				}
				
			}
		};
		authors.addActionListener(aboutListener);
		instruction.addActionListener(aboutListener);

		this.add(about);
	}

}
