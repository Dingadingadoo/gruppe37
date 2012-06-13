package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Einstellungen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Einstellungen() {

		initUI();

	}

	/************************************************************************
	 * Erstellen eines Fensters und implementierung eines Schlie�en Buttons *
	 ************************************************************************/
	public final void initUI() {

		JPanel panel = new JPanel();
		getContentPane().add(panel);

		panel.setLayout(null);

		JButton quitButton = new JButton("Beenden");
		quitButton.setBounds(490, 315, 90, 40);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Einstellungen.this.dispose();
			}
		});

		panel.add(quitButton);

		setTitle("Einstellungen");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

	}

}