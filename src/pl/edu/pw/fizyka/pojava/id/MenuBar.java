package pl.edu.pw.fizyka.pojava.id;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	public MenuBar() {
		
		JMenu plik = new JMenu("plik");
		JMenuItem zapisz = new JMenuItem("zapisz");
		
		plik.add(zapisz);
		this.add(plik);
	}

}
