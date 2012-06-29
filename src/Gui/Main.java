package Gui;

import javax.swing.JFrame;

import Objects.Spieler;
import Spielfeld.Karteneditor;
import Spielfeld.Spielfeld;

/** Main Klasse, die das gesamte Spiel beinhaltet */
public class Main extends JFrame {

	/** Spielfeld in dem das Spiel stattfindet */
	public Spielfeld gamepanel;

	/** Spielfeld in dem das Spiel stattfindet */
	public Karteneditor gameedit;

	/** Men�leiste des Programms */
	public Menue gamemenue;

	/** Steuerung des 1. Spielers */
	public Spieler Spieler1;

	/** Steuerung des 2. Spielers */
	public Spieler Spieler2;

	private static final long serialVersionUID = 1L;

	/** Start des Programms mit den Fenstereigenschaften */
	public Main() {

		super("Bomberman");
		/************************
		 * Fenstereinstellungen *
		 ************************/
		gamemenue = new Menue(this);
		this.setSize(500, 500);
		setJMenuBar(Menue.menubar);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		Spieler1 = new Spieler(this, 0);
		Spieler2 = new Spieler(this, 1);

	}

	/***********************************************************
	 * Erstellen eines Neuen Spiels und einf�gen der Steuerung *
	 ***********************************************************/
	public void createGame() {

		getContentPane().removeAll();
		gamepanel = new Spielfeld(this);
		gamepanel.setVisible(true);
		gamepanel.setLayout(null);
		add(gamepanel);
		gamepanel.standardfeld();
		gamepanel.zeichnen();
		requestFocusInWindow();

	}

	public void Leveleditor() {

		getContentPane().removeAll();
		gameedit = new Karteneditor(this);
		gameedit.setVisible(true);
		gameedit.setLayout(null);
		add(gameedit);
		gameedit.blankfield();
		gameedit.zeichnen();
		requestFocusInWindow();

	}

	/** Aufruf der Main Methode */
	public void Einstellungen() {
		getContentPane().removeAll();
		new Einstellungen(this);
	}

	public void Dateibrowser() {
		getContentPane().removeAll();
		gamepanel = new Spielfeld(this);
		gamepanel.setVisible(true);
		gamepanel.setLayout(null);
		add(gamepanel);
		gamepanel.XMLFeld();
	}

	public void Save() {
		getContentPane().removeAll();
		gamepanel = new Spielfeld(this);
		gamepanel.setVisible(true);
		gamepanel.setLayout(null);
		add(gamepanel);
		new Save(this);
	}

	public static void main(String[] args) {
		new Main();
	}
}
