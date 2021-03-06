package TestKrempel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Testgame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Testgame();
	}

	public Testgame() {

		/*********
		 * Menue *
		 *********/

		JMenuBar menubar = new JMenuBar();
		JMenu filemenu = new JMenu("Spiel");
		filemenu.add(new JSeparator());

		/*****************
		 * Erster Button *
		 *****************/

		JMenuItem fileItem1 = new JMenuItem("Neues Spiel");

		fileItem1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {
					JPanel Testplayer = new Testplayer();
					Testplayer.setVisible(true);
					Testplayer.setLayout(null);
					add(Testplayer);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		/******************
		 * Zweiter Button *
		 ******************/

		JMenuItem fileItem2 = new JMenuItem("Einstellungen");

		fileItem2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {
					Einstellungen frame = new Einstellungen();
					frame.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		/******************
		 * Dritter Button *
		 ******************/
		JMenuItem fileItem3 = new JMenuItem("Schliessen");
		fileItem3.setMnemonic('x');
		fileItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		fileItem3.add(new JSeparator());
		filemenu.add(fileItem1);
		filemenu.add(fileItem2);
		filemenu.add(fileItem3);
		menubar.add(filemenu);

		/************************
		 * Fenstereinstellungen *
		 ************************/

		JFrame frame = new JFrame("Bomberman");

		frame.setJMenuBar(menubar);
		frame.setSize(800, 600);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);

		/************
		 * Textfeld *
		 ************/

		JPanel field = new JPanel(new GridLayout(400, 300));
		field.setPreferredSize(new Dimension(800, 600));
		JLabel label = new JLabel(
				"Hier entsteht unser Bomberman, aber sowas von ;)",
				JLabel.CENTER);
		label.setBounds(0, 0, 784, 541);
		frame.getContentPane().add(label);

	}
}