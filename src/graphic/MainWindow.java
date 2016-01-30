package graphic;

import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import application.Controler;
import data.GameType;


public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	
//	private JLabel sprite_select, lab;
//	private Menu menu;
//	private CoupListener listener;
//	
//	private LinkedList<JJeton> al;
//	private boolean moving;
//	int column;
//	Thread t;
	
	/** Constructeur */
	public MainWindow(Controler controler) {
		super("Puissance 4");
		
		GameBoard gameBoard = new GameBoard(controler);
		controler.addGameObserver(gameBoard);
		
		panneau = new JPanel();
		panneau.setLayout(new GridBagLayout());
		panneau.add(gameBoard);
		setContentPane(panneau);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
//		menu = new Menu();
//		setJMenuBar(menu);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final Controler controler = new Controler();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow fen = new MainWindow(controler);
				fen.setVisible(true);
			}
		});
		
		controler.newGame(GameType.joueur_contre_ordinateur);
		//controler.newGame(GameType.joueur_contre_joueur);
		
		Thread t = new Thread(controler);
		t.start();
	}
	
	
	
	
	
	
//	/** methode qui ajoute une image jeton a la fenetre et delenche l'animation */
//	public void add_Jeton(final int column, final int line, int joueur) {
//		al.add(new JJeton(column, line, joueur));
//		panneau.add(al.getLast());
//		repaint();
//		
//		t = new Animation(column, line, al.getLast());
//		t.start();
//	}
//	
//	/** supprime le dernier jeton ajoute a la fenetre */
//	public int[] remove_Jeton() throws NoSuchElementException {
//		if (al.size() == 0) {
//			throw new NoSuchElementException();
//		} else {
//			JJeton tmp = al.removeLast(); 
//			panneau.remove(tmp);
//			return new int[] {tmp.getColumn(), tmp.getLine()};
//		}
//	}
//	
//	/** nettoie la fenetre graphique */
//	public void clear() {
//		for(JJeton j : al) {
//			panneau.remove(j);
//		}
//		al.clear();
//	}
//	
//	/** initialise le listener */
//	public void setCoupListener(CoupListener listener) {
//        this.listener = listener;
//        menu.setCoupListener(listener);
//    }
//	
//	/** affichage du texte dans la fenetre */
//	public void setTextLabel(int i) {
//		if (i == 0) {
//			lab.setText("Match nul !");
//		} else if (i == -1) {
//			lab.setText("C'est à Rouge de jouer");
//		} else if (i == 1) {
//			lab.setText("C'est à Jaune de jouer");
//		} else if (i == 2) {
//			lab.setText("Le joueur Jaune a gagné !");
//		} else if (i == 3) {
//			lab.setText("Le joueur Rouge a gagné !");
//		}
//	}
//	
//	/** Classes privee Thread pour l'animation du jeton qui tombe */
//	private class Animation extends Thread {
//		
//		private int column;
//		private int line;
//		private JJeton jeton;
//		
//		/** Constructeur */
//		public Animation(int column, int line, JJeton jeton) {
//			this.column = column;
//			this.line = line;
//			this.jeton = jeton;
//		}
//		
//		/** lance l'animation */
//		public void run() {
//			moving = true;
//			for (int m=0; m<line*72; m++) {
//				jeton.setLocation(72 + column*72, 81 + m);
//				try {
//					sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			listener.finAnimation(column, line);
//			moving = false;
//		}
//	}
//	
//	/** Classe privee Listener */
//	private class damier_listener extends MouseAdapter {
//		// methode invoquee si la souris a ete cliquee sur le damier
//		public void mouseClicked(MouseEvent ev) {
//			if (!moving) {
//				column = ev.getX() / 72;
//				listener.jouer(column);
//			}
//		}
//		
//		// methode invoquee si la souris rentre dans la zone du damier
//		public void mouseEntered(MouseEvent ev) {
//			sprite_select.setVisible(true);
//		}
//		
//		// methode invoquee si la souris sort de la zone du damier
//		public void mouseExited(MouseEvent ev) {
//			sprite_select.setVisible(false);
//		}
//	}
//	
//	/** Classe privee Listener */
//	private class damier_actionlistener extends MouseAdapter {
//		// mise a jour de la surbrillance lorsque la souris se deplace au dessus du damier
//		public void mouseMoved(MouseEvent ev) {
//			int x = ev.getX() / 72;
//			sprite_select.setLocation(49 + x * 72, 80);
//		}
//	}
//	
//	/** Classe privee JJeton */
//	private class JJeton extends JLabel {
//		
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		int x, y;
//		
//		/** Constructeur */
//		public JJeton(int x, int y, int joueur) {
//			super();
//			if (joueur == 1) {
//				setIcon(new ImageIcon("jeton_jaune.png"));
//			} else if (joueur == -1) {
//				setIcon(new ImageIcon("jeton_rouge.png"));
//			}
//			this.x = x;
//			this.y = y;
//			setBounds(72 + x*72, 81 + y*72, 72, 72);
//		}
//		
//		/** renvoie la colonne dans laquelle le jeton est présent */
//		public int getColumn() {
//			return x;
//		}
//		
//		/** renvoie la ligne dans laquelle le jeton est présent */
//		public int getLine() {
//			return y;
//		}
//	}

}
