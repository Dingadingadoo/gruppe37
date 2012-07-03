package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * Erstellung der Men�leiste mit den Buttons zum Spielstart, Einstellungsmenue
 * und Beenden des Programms
 * 
 * @author Johannes
 * 
 */
public class Menue extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final JMenuBar menubar = new JMenuBar(); // Neue Men�leiste
															// wird
															// initialisiert
	private final Main window;

	public Menue(Main parent) {

		window = parent;

		/*******************
		 * Menue erstellen *
		 *******************/
		JMenu filemenu = new JMenu("Spiel");
		filemenu.add(new JSeparator());

		/*******************************
		 * Erster Button, gennant "Neues Spiel", startet Spiel *
		 *******************************/

		JMenuItem fileItem1 = new JMenuItem("Neues Spiel");

		fileItem1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {

					window.createGame();
				}

				catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		/*************************************
		 * Zweiter Button, "Level ausw�hlen, �ffnet Dateibrowser zur Auswahl
		 * einer g�ltigen XML-Datei *
		 *************************************/

		JMenuItem fileItem2 = new JMenuItem("Level ausw�hlen");

		fileItem2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {
					window.Dateibrowser();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		/*************************************
		 * Dritter Button - Speichern *
		 *************************************/

		JMenuItem fileItem3 = new JMenuItem("Speichern");

		fileItem3.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {
					window.Save();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		/*************************************
		 * Vierter Button - Einstellungsmenue *
		 *************************************/

		JMenuItem fileItem4 = new JMenuItem("Einstellungen");

		fileItem4.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {
					window.Einstellungen();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		/*************************************
		 * F�nfter Button - Karteneditor *
		 *************************************/

		JMenuItem fileItem5 = new JMenuItem("Karteneditor");

		fileItem5.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {
					window.Leveleditor();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		/*************************
		 * Sechster Button - Exit *
		 *************************/
		JMenuItem fileItem6 = new JMenuItem("Schliessen");
		fileItem6.setMnemonic('x');
		fileItem6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		fileItem6.add(new JSeparator());
		filemenu.add(fileItem1);
		filemenu.add(fileItem2);
		filemenu.add(fileItem3);
		filemenu.add(fileItem4);
		filemenu.add(fileItem5);
		filemenu.add(fileItem6);
		menubar.add(filemenu);

	}
}
