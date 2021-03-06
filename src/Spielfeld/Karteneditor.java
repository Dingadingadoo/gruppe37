package Spielfeld;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Gui.Main;

/**
 * Klasse zur Erstellung des Basis-Spielfelds mit den zugewiesenen Grafiken
 * 
 * @author Gruppe37
 * @version 0.9
 * @param Main
 *            parent
 */
public class Karteneditor extends JPanel implements ChangeListener,
		MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActionListener newMapListener = null;
	private ActionListener saveListener = null;
	private ActionListener loadListener = null;
	/**
	 * Variablen der verschiedenen Stati eines Spielfeldblocks und der Grafiken
	 * bei Spielende
	 */
	private ImageIcon solidBlock;
	private ImageIcon brkbleBlock;
	private ImageIcon grndBlock;
	private ImageIcon player;
	private ImageIcon player2;
	private ImageIcon portal;
	private ImageIcon blank_field;

	/** horizontale Koordinate des Spielfelds */
	private int m = 0;
	/** vertikale Koordinate des Spielfelds */
	private int n = 0;
	private final int blank = 0;
	private final int solid = 1;
	private final int breakblock = 2;
	private final int ground = 13;
	private final int spieler = 8;
	private final int versteckterausgang = 9;
	private final int ausgang = 10;
	private final int spieler2 = 11;

	boolean brkbleButton_on;
	boolean solidButton_on;
	boolean grndButton_on;
	boolean playerButton_on;
	boolean player2Button_on;

	private boolean Spieler1_vorhanden;
	private boolean Spieler2_vorhanden;
	private boolean Spieler1_spielbar;
	private boolean Spieler2_spielbar;
	private boolean blank_vorhanden;
	int m1;
	int n1;
	int m2;
	int n2;

	static final int hoehe_MIN = 5;
	static final int hoehe_MAX = 30;
	static int hoehe_INIT = 11;

	static final int breite_MIN = 5;
	static final int breite_MAX = 30;
	static int breite_INIT = 11;

	public static int breite = breite_INIT;
	public static int hoehe = hoehe_INIT;

	private static int Feldgroesse_x = breite;
	private static int Feldgroesse_y = hoehe;

	/** Array in dem das Feld erstellt wird */
	private final JLabel fblock[][] = new JLabel[breite_MAX][hoehe_MAX];
	/** Definition der einzelnen Spielfeldelemente */
	private final static int blockStatus[][] = new int[breite_MAX][hoehe_MAX];

	private final JSlider hoehe_s = new JSlider(hoehe_MIN, hoehe_MAX,
			hoehe_INIT);
	private final JSlider breite_s = new JSlider(breite_MIN, breite_MAX,
			breite_INIT);
	JLabel nameSolid = new JLabel("Solid");
	JLabel namePlayer2 = new JLabel("Spieler2");
	JLabel nameBrkble = new JLabel("Zerst�rbar");
	JLabel namePlayer1 = new JLabel("Spieler1");
	JLabel nameGrnd = new JLabel("Boden");
	JLabel nameHoehe = new JLabel("Hoehe");
	JLabel nameBreite = new JLabel("Breite");

	final JToggleButton solidButton = new JToggleButton(solidBlock);
	final JToggleButton player2Button = new JToggleButton(player2);
	final JToggleButton playerButton = new JToggleButton(player);
	final JToggleButton brkbleButton = new JToggleButton(brkbleBlock);
	final JToggleButton grndButton = new JToggleButton(grndBlock);
	final JButton newMapButton = new JButton();
	final JButton saveButton = new JButton();
	final JButton loadButton = new JButton();

	private final Main window;

	/**
	 * Spielfeld wird im Mainpanel angezeigt
	 * 
	 * @param parent
	 *            f�hrt Methode im Mainwindow aus
	 */
	public Karteneditor(Main parent) {

		window = parent;
		window.setResizable(false);
		window.addMouseListener(this);

		loadContentImages();
	}

	/****************************************
	 * Laden der einzelnen Icons der Bl�cke *
	 ****************************************/
	private void loadContentImages() {
		solidBlock = new ImageIcon("Images/HardBlock.png");
		brkbleBlock = new ImageIcon("Images/breakstone.jpg");
		grndBlock = new ImageIcon("Images/ground.jpg");
		player = new ImageIcon("Images/Player.png");
		player2 = new ImageIcon("Images/Player2.png");
		portal = new ImageIcon("Images/portal.gif");
		blank_field = new ImageIcon("Images/bg.png");
	}

	/**
	 * zeichnet ein leeres Feld mit solidblocks als Umrandung
	 */
	public void blankfield() {
		Spieler1_vorhanden = false;
		Spieler2_vorhanden = false;
		remove();
		Feldgroesse_x = breite;
		Feldgroesse_y = hoehe;
		if (Feldgroesse_y <= 10) {
			window.setSize(((Feldgroesse_x * 30) + 160), ((11 * 30) + 80));
		} else {
			window.setSize(((Feldgroesse_x * 30) + 160),
					((Feldgroesse_y * 30) + 80));
		}
		window.gameedit
				.setBounds(0, 30, Feldgroesse_x * 30, Feldgroesse_y * 30);

		for (n = 0; n < Feldgroesse_y; n++) {
			for (m = 0; m < Feldgroesse_x; m++) {
				if (m == 0 || n == 0 || n == Feldgroesse_y - 1
						|| m == Feldgroesse_x - 1) {
					blockStatus[m][n] = solid;
				} else if (blockStatus[m][n] == 0) {
					blockStatus[m][n] = ground;
				}
			}

		}
		Button();
		Slider();
		zeichnen();
		return;

	}

	/** fragt Blockstati ab und zeichnet danach das Feld */
	public void zeichnen() {
		window.gameedit.removeAll();
		for (m = 0; m < Feldgroesse_x; m++) {
			for (n = 0; n < Feldgroesse_y; n++) {
				if (blockStatus[m][n] == ground) {
					fblock[m][n] = new JLabel(grndBlock);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				}

				else if (blockStatus[m][n] == solid) {
					fblock[m][n] = new JLabel(solidBlock);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				}

				else if (blockStatus[m][n] == breakblock) {
					fblock[m][n] = new JLabel(brkbleBlock);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				}
				/*
				 * Spieler
				 */
				else if (blockStatus[m][n] == spieler) {
					fblock[m][n] = new JLabel(player);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				}

				/*
				 * Spieler2
				 */
				else if (blockStatus[m][n] == spieler2) {
					fblock[m][n] = new JLabel(player2);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				}

				/*
				 * Ausgang
				 */
				else if (blockStatus[m][n] == ausgang) {
					fblock[m][n] = new JLabel(portal);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				} else if (blockStatus[m][n] == versteckterausgang) {
					fblock[m][n] = new JLabel(brkbleBlock);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				} else if (blockStatus[m][n] == ground) {
					fblock[m][n] = new JLabel(blank_field);
					window.gameedit.add(fblock[m][n]);
					fblock[m][n].setBounds(m * 30, n * 30, 30, 30);

				}
			}
		}
	}

	/**
	 * Bl�cke durch Mausklick setzen, gibt Fehler aus, wenn versucht wird, den
	 * Rand zu ver�ndern
	 */
	public void Blocksetzen() {

		if (m == 0 || n == 0 || n == Feldgroesse_y - 1
				|| m == Feldgroesse_x - 1) {
			if (brkbleButton_on == true || solidButton_on == true
					|| grndButton_on == true || playerButton_on == true
					|| player2Button_on == true) {
				JOptionPane.showMessageDialog(null,
						"Ver�ndern der Randbl�cke nicht m�glich.");
			}
		} else {
			if (solidButton_on == true) {
				blockStatus[m][n] = solid;
			} else if (grndButton_on == true) {
				blockStatus[m][n] = ground;
			} else if (brkbleButton_on == true) {
				blockStatus[m][n] = breakblock;
			} else if (playerButton_on == true) {
				for (int x = 0; x < Feldgroesse_x; x++) {
					for (int y = 0; y < Feldgroesse_y; y++) {
						if (blockStatus[x][y] == spieler) {
							blockStatus[x][y] = ground;
						}
					}

				}
				blockStatus[m][n] = spieler;
			} else if (player2Button_on == true) {
				for (int x = 0; x < Feldgroesse_x; x++) {
					for (int y = 0; y < Feldgroesse_x; y++) {
						if (blockStatus[x][y] == spieler2) {
							blockStatus[x][y] = ground;
						}
					}

				}
				blockStatus[m][n] = spieler2;
			}
			zeichnen();
		}
	}

	/**
	 * lokalisiert den Mauszeiger und gibt die Position als m=vertikale Position
	 * und n=horizontale Position an
	 */
	public void mausposi() {

		Point location = window.gameedit.getMousePosition();
		m = Math.round(location.x / 30);
		n = Math.round(location.y / 30);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent paramMouseEvent) {

		try {
			mausposi();
			if (m < Feldgroesse_x && n < Feldgroesse_y) {
				Blocksetzen();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void mouseReleased(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {

	}

	/** stellt die Slider f�r H�he und Breite des Spielfelds bereit */
	public void Slider() {
		window.setLayout(null);

		/*
		 * Einstellungen fuer Hoehen-Slider
		 */
		hoehe_s.setBounds(((Feldgroesse_x * 30) + 20), 230, 120, 50);
		hoehe_s.addChangeListener(this);
		hoehe_s.setPaintTicks(true);
		hoehe_s.setMajorTickSpacing(5);
		hoehe_s.setMinorTickSpacing(1);
		hoehe_s.setPaintLabels(true);

		nameHoehe.setBounds(((Feldgroesse_x * 30) + 20), 215, 80, 10);
		nameHoehe.setToolTipText("Die Hoehe des Spielfelds in Bausteinen");

		/*
		 * Einstellungen fuer Breiten-Slider
		 */
		breite_s.setBounds(((Feldgroesse_x * 30) + 20), 295, 120, 50);
		breite_s.addChangeListener(this);
		breite_s.setPaintTicks(true);
		breite_s.setMajorTickSpacing(5);
		breite_s.setMinorTickSpacing(1);
		breite_s.setPaintLabels(true);

		nameBreite.setBounds(((Feldgroesse_x * 30) + 20), 285, 80, 10);
		nameBreite.setToolTipText("Die Breite des Spielfelds in Bausteinen");

		window.add(breite_s);
		window.add(hoehe_s);
		window.add(nameBreite);
		window.add(nameHoehe);

	}

	/** �berwacht Ver�nderungen der Slider */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting()) {
			for (n = 0; n < Feldgroesse_y; n++) {
				for (m = 0; m < Feldgroesse_x; m++) {
					if (m == 0 || n == 0 || n == Feldgroesse_y - 1
							|| m == Feldgroesse_x - 1) {
						blockStatus[m][n] = ground;
					}
				}
			}
			breite = breite_s.getValue();
			hoehe = hoehe_s.getValue();
			Feldgroesse_x = breite;
			Feldgroesse_y = hoehe;
			if (Feldgroesse_y <= 10) {
				window.setSize(((Feldgroesse_x * 30) + 160), ((11 * 30) + 80));
			} else {
				window.setSize(((Feldgroesse_x * 30) + 160),
						((Feldgroesse_y * 30) + 80));
			}
			window.gameedit.setBounds(0, 30, Feldgroesse_x * 30,
					Feldgroesse_y * 30);
			blankfield();
		}

	}

	/** Auswahlbuttons f�r die einzelnen Blocks */
	public void Button() {
		/*
		 * BUTTON
		 */

		solidButton.setIcon(solidBlock);
		player2Button.setIcon(player2);
		playerButton.setIcon(player);
		brkbleButton.setIcon(brkbleBlock);
		grndButton.setIcon(grndBlock);
		grndButton.setBounds(((Feldgroesse_x * 30) + 20), 50, 30, 30);
		grndButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (grndButton.isSelected()) {
					brkbleButton.setEnabled(false);
					solidButton.setEnabled(false);
					playerButton.setEnabled(false);
					player2Button.setEnabled(false);
					grndButton_on = true;
				} else {
					brkbleButton.setEnabled(true);
					solidButton.setEnabled(true);
					grndButton.setEnabled(true);
					playerButton.setEnabled(true);
					player2Button.setEnabled(true);
					grndButton_on = false;
				}
			}
		});
		/*
		 * BUTTON
		 */

		brkbleButton.setBounds(((Feldgroesse_x * 30) + 20), 100, 30, 30);
		brkbleButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (brkbleButton.isSelected()) {
					solidButton.setEnabled(false);
					grndButton.setEnabled(false);
					playerButton.setEnabled(false);
					player2Button.setEnabled(false);
					brkbleButton_on = true;

				} else {
					brkbleButton.setEnabled(true);
					solidButton.setEnabled(true);
					grndButton.setEnabled(true);
					playerButton.setEnabled(true);
					player2Button.setEnabled(true);
					brkbleButton_on = false;
				}
			}
		});
		/*
		 * BUTTON
		 */

		playerButton.setBounds(((Feldgroesse_x * 30) + 90), 50, 30, 30);
		playerButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (playerButton.isSelected()) {
					brkbleButton.setEnabled(false);
					solidButton.setEnabled(false);
					grndButton.setEnabled(false);
					player2Button.setEnabled(false);
					playerButton_on = true;
				} else {
					brkbleButton.setEnabled(true);
					solidButton.setEnabled(true);
					grndButton.setEnabled(true);
					playerButton.setEnabled(true);
					player2Button.setEnabled(true);
					playerButton_on = false;
				}
			}
		});
		/*
		 * BUTTON
		 */
		player2Button.setBounds(((Feldgroesse_x * 30) + 90), 100, 30, 30);
		player2Button.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (player2Button.isSelected()) {
					brkbleButton.setEnabled(false);
					solidButton.setEnabled(false);
					grndButton.setEnabled(false);
					playerButton.setEnabled(false);
					player2Button_on = true;
				} else {
					brkbleButton.setEnabled(true);
					solidButton.setEnabled(true);
					grndButton.setEnabled(true);
					playerButton.setEnabled(true);
					player2Button.setEnabled(true);
					player2Button_on = false;
				}
			}
		});
		/*
		 * BUTTON
		 */

		solidButton.setBounds(((Feldgroesse_x * 30) + 20), 150, 30, 30);
		solidButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (solidButton.isSelected()) {
					brkbleButton.setEnabled(false);
					grndButton.setEnabled(false);
					playerButton.setEnabled(false);
					player2Button.setEnabled(false);
					solidButton_on = true;
				} else {
					brkbleButton.setEnabled(true);
					solidButton.setEnabled(true);
					grndButton.setEnabled(true);
					playerButton.setEnabled(true);
					player2Button.setEnabled(true);
					solidButton_on = false;
				}
			}
		});

		newMapButton.setBounds(0, 0, 120, 30);
		newMapButton.setText("Neue Map");
		newMapListener = new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				for (m = 0; m < Feldgroesse_x; m++) {
					for (n = 0; n < Feldgroesse_y; n++) {
						if (m == 0 || n == 0 || n == Feldgroesse_y - 1
								|| m == Feldgroesse_x - 1) {
							blockStatus[m][n] = solid;
						} else {
							blockStatus[m][n] = ground;
						}
					}
				}
				zeichnen();
			}
		};
		newMapButton.addActionListener(newMapListener);

		saveButton.setBounds(280, 0, 120, 30);
		saveButton.setText("Karte speichern");

		saveListener = new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Konsistenzabfrage();
			}
		};
		saveButton.addActionListener(saveListener);
		loadButton.setBounds(140, 0, 120, 30);
		loadButton.setText("Karte laden");
		loadListener = new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XMLFeld();
			}

		};
		loadButton.addActionListener(loadListener);

		nameSolid.setBounds(((Feldgroesse_x * 30) + 20), 135, 80, 10);
		nameBrkble.setBounds(((Feldgroesse_x * 30) + 20), 85, 80, 10);
		namePlayer1.setBounds(((Feldgroesse_x * 30) + 90), 35, 80, 10);
		namePlayer2.setBounds(((Feldgroesse_x * 30) + 90), 85, 80, 10);
		nameGrnd.setBounds(((Feldgroesse_x * 30) + 20), 35, 80, 10);

		window.add(nameSolid);
		window.add(nameGrnd);
		window.add(nameBrkble);
		window.add(namePlayer1);
		window.add(namePlayer2);

		window.add(solidButton);
		window.add(grndButton);
		window.add(brkbleButton);
		window.add(playerButton);
		window.add(player2Button);
		window.add(newMapButton);
		window.add(loadButton);
		window.add(saveButton);

	}

	/**
	 * pr�ft, ob jeder Blank gef�llt ist und ob beiden Spielern eine
	 * Startposition zugewiesen ist
	 */
	public void Konsistenzabfrage() {

		for (n = 0; n < Feldgroesse_y; n++) {
			for (m = 0; m < Feldgroesse_x; m++) {
				if (blockStatus[m][n] == blank) {
					blank_vorhanden = true;
				} else if (blockStatus[m][n] == spieler) {
					Spieler1_vorhanden = true;
					m1 = m;
					n1 = n;
				} else if (blockStatus[m][n] == spieler2) {
					Spieler2_vorhanden = true;
					m2 = m;
					n2 = n;
				}
			}

		}

		if ((Spieler1_vorhanden == false || Spieler2_vorhanden == false)
				&& blank_vorhanden == true) {
			blank_vorhanden = false;
			Spieler1_vorhanden = false;
			Spieler2_vorhanden = false;
			JOptionPane
					.showMessageDialog(
							null,
							"Spielfeld kann nicht gespeichert werden ,da sowohl Felder als auch Spieler fehlen.");

		}

		else if ((Spieler1_vorhanden == false || Spieler2_vorhanden == false)
				&& blank_vorhanden == false) {
			Spieler1_vorhanden = false;
			Spieler2_vorhanden = false;

			JOptionPane
					.showMessageDialog(null,
							"Spielfeld kann nicht gespeichert werden ,da mindestens ein Spieler fehlt!");
		} else if ((Spieler1_vorhanden == true || Spieler2_vorhanden == true)
				&& blank_vorhanden == true) {
			blank_vorhanden = false;
			Spieler1_vorhanden = false;
			Spieler2_vorhanden = false;

			JOptionPane
					.showMessageDialog(null,
							"Spielfeld kann nicht gespeichert werden ,da mindestens ein Feld fehlt!");
		} else if ((Spieler1_vorhanden == true || Spieler2_vorhanden == true)) {

			if ((blockStatus[m1 - 1][n1] == ground || blockStatus[m1 + 1][n1] == ground)
					&& (blockStatus[m1][n1 - 1] == ground || blockStatus[m1][n1 + 1] == ground)) {
				Spieler1_spielbar = true;

			}
			for (int o1 = 1; o1 <= 30; o1++) {

				if (blockStatus[m1 - o1][n1] == ground) {
					if (blockStatus[m1 - o1][n1 - 1] == ground) {
						Spieler1_spielbar = true;
						break;
					}
					if (blockStatus[m1 - o1][n1 + 1] == ground) {
						Spieler1_spielbar = true;
						break;
					}

				} else {
					break;

				}
			}
			for (int o2 = 1; o2 <= 30; o2++) {
				if (blockStatus[m1 + o2][n1] == ground) {
					if (blockStatus[m1 + o2][n1 + 1] == ground) {
						Spieler1_spielbar = true;
						break;
					}
					if (blockStatus[m1 + o2][n1 - 1] == ground) {
						Spieler1_spielbar = true;
						break;
					}
				} else {
					break;
				}

			}
			for (int o3 = 1; o3 <= 30; o3++) {
				if (blockStatus[m1][n1 - o3] == ground) {
					if (blockStatus[m1 + 1][n1 - o3] == ground) {
						Spieler1_spielbar = true;
						break;
					}
					if (blockStatus[m1 - 1][n1 - o3] == ground) {
						Spieler1_spielbar = true;
						break;
					}
				} else {
					break;
				}

			}
			for (int o4 = 1; o4 <= 30; o4++) {
				if (blockStatus[m1][n1 + o4] == ground) {
					if (blockStatus[m1 + 1][n1 + o4] == ground) {
						Spieler1_spielbar = true;
						break;
					}
					if (blockStatus[m1 - 1][n1 + o4] == ground) {
						Spieler1_spielbar = true;
						break;
					}
				} else {
					break;
				}

			}

			if ((blockStatus[m2 - 1][n2] == ground || blockStatus[m2 + 1][n2] == ground)
					&& (blockStatus[m2][n2 - 1] == ground || blockStatus[m2][n2 + 1] == ground)) {
				Spieler2_spielbar = true;

			}

			for (int o6 = 1; o6 <= 30; o6++) {
				if (blockStatus[m2 - o6][n2] == ground) {
					if (blockStatus[m2 - o6][n2 - 1] == ground) {
						Spieler2_spielbar = true;
						break;
					}
					if (blockStatus[m2 - o6][n2 + 1] == ground) {
						Spieler2_spielbar = true;
						break;
					}
				} else {
					break;
				}

			}
			for (int o5 = 1; o5 <= 30; o5++) {
				if (blockStatus[m2 + o5][n2] == ground) {
					if (blockStatus[m2 + o5][n2 + 1] == ground) {
						Spieler2_spielbar = true;
						break;
					}
					if (blockStatus[m2 + o5][n2 - 1] == ground) {
						Spieler2_spielbar = true;
						break;
					}
				} else {
					break;
				}

			}
			for (int o8 = 1; o8 <= 30; o8++) {
				if (blockStatus[m2][n2 - o8] == ground) {
					if (blockStatus[m2 + 1][n2 - o8] == ground) {
						Spieler2_spielbar = true;
						break;
					}
					if (blockStatus[m2 - 1][n2 - o8] == ground) {
						Spieler2_spielbar = true;
						break;
					}
				} else {
					break;
				}

			}
			for (int o9 = 1; o9 <= 30; o9++) {
				if (blockStatus[m2][n2 + o9] == ground) {
					if (blockStatus[m2 + 1][n2 + o9] == ground) {
						Spieler2_spielbar = true;
						break;
					}
					if (blockStatus[m2 - 1][n2 + o9] == ground) {
						Spieler2_spielbar = true;
						break;
					}
				} else {
					break;
				}

			}

			if (Spieler2_spielbar == false || Spieler1_spielbar == false) {
				JOptionPane
						.showMessageDialog(
								null,
								"Spielerpositionen sind nicht bespielbar, da sich die Spieler selbst zerst�ren.");
				Spieler1_spielbar = false;
				Spieler2_spielbar = false;

			}
		}
		if (Spieler2_spielbar == true && Spieler1_spielbar == true) {
			Spieler1_spielbar = false;
			Spieler2_spielbar = false;
			new Gui.Save(window);
		}
	}


	/** liest Level aus XML ein und erm�glicht Editierung */
	public void XMLFeld() {
		try {
			Spielfeld.XMLInit();
			remove();
			Spieler1_vorhanden = false;
			Spieler2_vorhanden = false;
			blank_vorhanden = false;
			Feldgroesse_x = XMLReader.breite_max;
			Feldgroesse_y = XMLReader.hoehe_max;
			breite_INIT = Feldgroesse_x;
			hoehe_INIT = Feldgroesse_y;

			if (Feldgroesse_y <= 10) {
				window.setSize(((Feldgroesse_x * 30) + 160), ((11 * 30) + 80));
			} else {
				window.setSize(((Feldgroesse_x * 30) + 160),
						((Feldgroesse_y * 30) + 80));
			}
			window.gameedit.setBounds(0, 30, Feldgroesse_x * 30,
					Feldgroesse_y * 30);

			for (int breite = 0; breite < Feldgroesse_x; breite++) {
				for (int hoehe = 0; hoehe < Feldgroesse_y; hoehe++) {
					if (XMLReader.xmlStatus[breite][hoehe] == XMLReader.solid) {
						blockStatus[breite][hoehe] = solid;
					} else if (XMLReader.xmlStatus[breite][hoehe] == XMLReader.breakblock) {
						blockStatus[breite][hoehe] = breakblock;
					} else if (XMLReader.xmlStatus[breite][hoehe] == XMLReader.ground) {
						blockStatus[breite][hoehe] = ground;
					} else if (XMLReader.xmlStatus[breite][hoehe] == XMLReader.spieler) {
						blockStatus[breite][hoehe] = spieler;
					} else if (XMLReader.xmlStatus[breite][hoehe] == XMLReader.spieler2) {
						blockStatus[breite][hoehe] = spieler2;
					}

					else {
						blockStatus[breite][hoehe] = ground;
					}

				}

			}
			Button();
			Slider();
			zeichnen();
			XMLReader.Reset();
			return;

		} catch (SAXException e) {

		} catch (IOException e) {

		} catch (ParserConfigurationException e) {

		} catch (NullPointerException e) {

		}
	}

	public void remove() {

		window.getContentPane().remove(breite_s);
		window.getContentPane().remove(hoehe_s);
		window.getContentPane().remove(nameBreite);
		window.getContentPane().remove(nameHoehe);
		window.getContentPane().remove(nameSolid);
		window.getContentPane().remove(nameGrnd);
		window.getContentPane().remove(nameBrkble);
		window.getContentPane().remove(namePlayer1);
		window.getContentPane().remove(namePlayer2);
		window.getContentPane().remove(solidButton);
		window.getContentPane().remove(grndButton);
		window.getContentPane().remove(brkbleButton);
		window.getContentPane().remove(playerButton);
		window.getContentPane().remove(player2Button);
		loadButton.removeActionListener(loadListener);
		saveButton.removeActionListener(saveListener);
		window.getContentPane().remove(loadButton);
		window.getContentPane().remove(saveButton);

	}

	public static int getFeldgroesse_x() {
		return Feldgroesse_x;
	}

	public static int getFeldgroesse_y() {
		return Feldgroesse_y;
	}

	public static int[][] getBlockStatus() {
		return blockStatus;
	}
}
