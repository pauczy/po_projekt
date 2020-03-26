package pl.edu.pw.fizyka.pojava.id;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	public MenuBar() {
		
		JMenu file = new JMenu("plik");
		JMenuItem save = new JMenuItem("zapisz");
		file.add(save);
		
		JMenu edit = new JMenu("edycja");
		JMenuItem addDest = new JMenuItem("dodaj nowy cel");
		edit.add(addDest);
		
		this.add(file);
		this.add(edit);
	}

}
