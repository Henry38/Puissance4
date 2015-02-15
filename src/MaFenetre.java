
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.swing.*;


public class MaFenetre extends JFrame {
	
	private JPanel panneau;
	private JLabel sprite_damier, sprite_select, lab;
	private Menu menu;
	private CoupListener listener;
	
	private LinkedList<JJeton> al;
	private boolean moving;
	int column;
	Thread t;
	
	/** Constructeur */
	public MaFenetre() {
		// Initialisation de la fenetre
		super("puissance 4");
		moving = false;
		al = new LinkedList<JJeton>();
		listener = null;
		
		setLocation(50, 50);
		setSize(632, 576);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panneau = new JPanel();
		panneau.setLayout(null);
		
		
		// Label pour afficher du texte sur la fenetre
		lab = new JLabel(" ");
		lab.setBounds(100,20,300,20);
		panneau.add(lab);
		
		// Image du puissance 4
		sprite_damier = new JLabel();
		sprite_damier.setIcon(new ImageIcon("Damier.png"));
		sprite_damier.setBounds(64, 80, 504, 432);
		panneau.add(sprite_damier);
		
		// Image de la surbrillance
		sprite_select = new JLabel();
		sprite_select.setIcon(new ImageIcon("Surbrillance.png"));
		sprite_select.setBounds(49, 80, 504, 432);
		sprite_select.setVisible(false);
		panneau.add(sprite_select);
		
		// Definit les priorites de superposition des images
		panneau.setComponentZOrder(sprite_damier, 1);
		panneau.setComponentZOrder(sprite_select, 0);
		
		// Definit les listeners sur le damier
		sprite_damier.addMouseListener(new damier_listener());
		sprite_damier.addMouseMotionListener(new damier_actionlistener());
		
		menu = new Menu();
		setJMenuBar(menu);
		
		setContentPane(panneau);
		setVisible(true);
	}
	
	/** methode qui ajoute une image jeton a la fenetre et delenche l'animation */
	public void add_Jeton(final int column, final int line, int joueur) {
		al.add(new JJeton(column, line, joueur));
		panneau.add(al.getLast());
		repaint();
		
		t = new Animation(column, line, al.getLast());
		t.start();
	}
	
	/** supprime le dernier jeton ajoute a la fenetre */
	public int[] remove_Jeton() throws NoSuchElementException {
		if (al.size() == 0) {
			throw new NoSuchElementException();
		} else {
			JJeton tmp = al.removeLast(); 
			panneau.remove(tmp);
			return new int[] {tmp.getColumn(), tmp.getLine()};
		}
	}
	
	/** nettoie la fenetre graphique */
	public void clear() {
		for(JJeton j : al) {
			panneau.remove(j);
		}
		al.clear();
	}
	
	/** initialise le listener */
	public void setCoupListener(CoupListener listener) {
        this.listener = listener;
        menu.setCoupListener(listener);
    }
	
	/** affichage du texte dans la fenetre */
	public void setTextLabel(int i) {
		if (i == 0) {
			lab.setText("Match nul !");
		} else if (i == -1) {
			lab.setText("C'est à Rouge de jouer");
		} else if (i == 1) {
			lab.setText("C'est à Jaune de jouer");
		} else if (i == 2) {
			lab.setText("Le joueur Jaune a gagné !");
		} else if (i == 3) {
			lab.setText("Le joueur Rouge a gagné !");
		}
	}
	
	/** Classes privee Thread pour l'animation du jeton qui tombe */
	private class Animation extends Thread {
		
		private int column;
		private int line;
		private JJeton jeton;
		
		/** Constructeur */
		public Animation(int column, int line, JJeton jeton) {
			this.column = column;
			this.line = line;
			this.jeton = jeton;
		}
		
		/** lance l'animation */
		public void run() {
			moving = true;
			for (int m=0; m<line*72; m++) {
				jeton.setLocation(72 + column*72, 81 + m);
				try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			listener.finAnimation(column, line);
			moving = false;
		}
	}
	
	/** Classe privee Listener */
	private class damier_listener extends MouseAdapter {
		// methode invoquee si la souris a ete cliquee sur le damier
		public void mouseClicked(MouseEvent ev) {
			if (!moving) {
				column = ev.getX() / 72;
				listener.jouer(column);
			}
		}
		
		// methode invoquee si la souris rentre dans la zone du damier
		public void mouseEntered(MouseEvent ev) {
			sprite_select.setVisible(true);
		}
		
		// methode invoquee si la souris sort de la zone du damier
		public void mouseExited(MouseEvent ev) {
			sprite_select.setVisible(false);
		}
	}
	
	/** Classe privee Listener */
	private class damier_actionlistener extends MouseAdapter {
		// mise a jour de la surbrillance lorsque la souris se deplace au dessus du damier
		public void mouseMoved(MouseEvent ev) {
			int x = ev.getX() / 72;
			sprite_select.setLocation(49 + x * 72, 80);
		}
	}
	
	/** Classe privee JJeton */
	private class JJeton extends JLabel {
		
		int x, y;
		
		/** Constructeur */
		public JJeton(int x, int y, int joueur) {
			super();
			if (joueur == 1) {
				setIcon(new ImageIcon("jeton_jaune.png"));
			} else if (joueur == -1) {
				setIcon(new ImageIcon("jeton_rouge.png"));
			}
			this.x = x;
			this.y = y;
			setBounds(72 + x*72, 81 + y*72, 72, 72);
		}
		
		/** renvoie la colonne dans laquelle le jeton est présent */
		public int getColumn() {
			return x;
		}
		
		/** renvoie la ligne dans laquelle le jeton est présent */
		public int getLine() {
			return y;
		}
	}

}
