package graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import data.CoupListener;

public class Menu extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	JMenu fichier, start;
	JMenuItem end, hvh, hvo, ovh, ovo, credits;
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
		
		// Ajout des menus au menu "this"
		this.add(fichier);
		
		
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
	}
	
	/** initialise le listener */
	public void setCoupListener(CoupListener listener) {
        this.listener = listener;
    }
}
