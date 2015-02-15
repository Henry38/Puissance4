import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class Menu extends JMenuBar {
	
	JMenu fichier, difficulte, start, tag;
	JMenuItem end, niveau1, niveau2, niveau3, hvh, hvo, ovh, ovo, credits;
	private CoupListener listener;
	
	/** Constructeur */
	public Menu() {
		super();
		
		// Creation du sous-menu nouvelle partie
		start = new JMenu("Nouvelle partie");
		end = new JMenuItem("Quitter");
		hvh = new JMenuItem("Joueur contre Joueur");
		hvo = new JMenuItem("Joueur contre ordinateur");
		ovh = new JMenuItem("Ordinateur contre Joueur");
		ovo = new JMenuItem("AlphaBeta vs MinMax");
		start.add(hvh);
		start.add(hvo);
		start.add(ovh);
		start.add(ovo);
		
		// Creation du menu fichier avec ses sous menus
		fichier = new JMenu("Fichier");
		fichier.add(start);
		fichier.add(end);
		
		// Creation du menu difficulté IA avec ses sous menus
		difficulte = new JMenu("Difficulté IA");		
		niveau1 = new JMenuItem("facile");
		niveau2 = new JMenuItem("intermédiaire");
		niveau3 = new JMenuItem("difficile");
		difficulte.add(niveau1);
		difficulte.add(niveau2);
		difficulte.add(niveau3);

		
		tag = new JMenu("?");
		credits = new JMenuItem("Credits");
		tag.add(credits);
		
		
		// Ajout des menus au menu "this"
		this.add(fichier);
		this.add(difficulte);
		this.add(tag);
		
		
		
		// Creation des listener pour chaque sous menu
		hvh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.new_game(0);
			}
		});
		
		hvo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.new_game(1);
			}
		});
		
		ovh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.new_game(2);
			}
		});
		
		ovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.new_game(3);
			}
		});
		
		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		niveau1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.change_level(2);
			}
		});
		
		niveau2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.change_level(4);
			}
		});
		
		niveau3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.change_level(6);
			}
		});
		
		credits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame fenetre = new JFrame("Crédits");
				JPanel panneau = new JPanel();
				panneau.setLayout(null);
				
				fenetre.setLocation(200, 200);
				fenetre.setSize(250, 250);
				fenetre.setResizable(false);
				//fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JLabel label1 = new JLabel("Projet de puissance 4");
				label1.setBounds(30, 10, 200, 20);
				JLabel label2 = new JLabel("--- réalisé par --- ");
				label2.setBounds(50, 50, 200, 20);
				JLabel label3 = new JLabel("Lefèvre Henry");
				label3.setBounds(70, 90, 200, 20);
				JLabel label4 = new JLabel("~~~  et  ~~~ ");
				label4.setBounds(90, 130, 200, 20);
				JLabel label5 = new JLabel("Mommessin Clément");
				label5.setBounds(110, 170, 200, 20);
				panneau.add(label1);
				panneau.add(label2);
				panneau.add(label3);
				panneau.add(label4);
				panneau.add(label5);
				
				fenetre.setContentPane(panneau);
				fenetre.setVisible(true);
				
			}
		});
	}
	
	/** initialise le listener */
	public void setCoupListener(CoupListener listener) {
        this.listener = listener;
    }
}
