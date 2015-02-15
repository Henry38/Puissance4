

public class Jeu {
	
	private Plateau p;
	private MaFenetre fen;
	private IA_MinMax cpu1;
	private IA_MinMax cpu2;
	private MyListener listener;
	
	private int line = 0;
	private int level, type;
	private boolean fini;
	
	/** Constructeur */
	public Jeu() {
		p = new Plateau();
		fen = new MaFenetre();
		fini = false;
		listener = new MyListener();
		fen.setCoupListener(listener);
		level = 4;
		type = 1;
		fen.setTextLabel(p.getJoueur());
	}
	
	// fait jouer l'ordinateur contre l'ordinateur
	public void cpu_Joueur1() {
		// determine quelle IA doit prendre l'ordinateur
		cpu1 = new IA_MinMax(p, level);
		cpu1.start();
		
		try {
			cpu1.join(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// joue sur la colonne récupérée par l'ia
		listener.jouer(cpu1.getCoup());
	}
	
	// fait jouer l'ordinateur contre le joueur humain
	public void cpu_Joueur2() {
		// determine quelle IA doit prendre l'ordinateur
		cpu2 = new IA_MinMax(p, level);
		cpu2.start();
		
		try {
			cpu2.join(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// joue sur la colonne récupérée par l'ia
		listener.jouer(cpu2.getCoup());
	}
	
	/** ecoute sur la fenêtre */
	private class MyListener implements CoupListener {
		
		/** joue dans la colonne passee en parametre */
		public void jouer(int column) {
			if (p.coupValide(column) && !fini) {
				line = p.move_line(column);
				p.joue(column);
				fen.add_Jeton(column, line, p.getJoueur());
			}
		}
		
		/** methode appelee lorsque l'animation du jeton qui tombe est terminee */
		public void finAnimation(int column, int line) {
			if (p.complet()) {
				fini = true;
				fen.setTextLabel(0);
			} else if (p.gagner(column, line) != 0) {
				fini = true;
				if (p.getJoueur() == 1) {
					fen.setTextLabel(2);
				} else {
					fen.setTextLabel(3);
				}
			} else {
				// change de joueur sur le plateau
				p.switch_joueur();
				fen.setTextLabel(p.getJoueur());
				// Fait jouer l'ordinateur si c'est à lui
				// Type Joueur vs IA
				if (type == 1 && p.getJoueur() == -1) {
					cpu_Joueur2();
				// Type IA vs Joueur
				} else if (type == 2 && p.getJoueur() == -1) {
					cpu_Joueur2();
				// Type IA vs IA
				} else if (type == 3) {
					if (p.getJoueur() == 1) {
						cpu_Joueur1();
					} else {
						cpu_Joueur2();
					}
				}
			}
		}
		
		/** nettoie l'environnement et lance une nouvelle partie */
		public void new_game(int t) {
			fini = false;
			type = t;
			p.clear();
			p.setJoueur(1);
			fen.clear();
			fen.setTextLabel(p.getJoueur());
			
			if (type == 2 || type == 3) {
				p.setJoueur(-1);
				fen.setTextLabel(p.getJoueur());
				cpu_Joueur2();
			}
		}
		
		/** change le niveau de l'ia */
		public void change_level(int i) {
			level = i;
		}
	}
}
