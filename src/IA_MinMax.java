import java.util.ArrayList;
import java.util.Collections;


public class IA_MinMax extends Thread {
	
	private int coup, level;
	private Plateau p;
	
	/** Constucteur */
	public IA_MinMax(Plateau p, int level) {
		this.coup = -1;
		this.level = level;
		this.p = p;
	}
	
	/** récupère le meilleur coup que l'ia a calculé */
	public int getCoup() {
		return coup;
	}
	
	/** lance le calcul du meilleur coup pour le plateau courant */
	public void run() {
		coup = -1;
		min_max(p, -1, -1, level);
	}
	
	/** joue aléatoirement sur le plateau */
	public void jouer_random() { 
		coup = -1;
		do {
			coup = (int)(Math.random()*7); 
		} while(!p.coupValide(coup));
	}
	
	/** Algorithme glouton */
	public void jouer_glouton() {
		Plateau p_child = p.clone();
		int value, j;
		int value_min = Integer.MAX_VALUE;
		
		// Boucle de parcourt des 7 enfants
		for(int i=0; i<7; i++) {
			// Si on peut jouer dans cette colonne
			if (p_child.coupValide(i)) {
				// On simule le coup joué en colonne i
				j = p_child.joue(i);
				value = p_child.eval(i, j);
				
				// On met à jour les variables si le résultat trouvé est meilleur qui celui trouvé jusqu'à maintenant
				if (value_min > value) {
					value_min = value;
					coup = i;
				}
				
				// Et on annule le coup qui vient d'être joué sur le plateau avant de simuler le prochain fils
				p.getBack(i, j);
			}
		}
	}
	
	/** Algorithme min_max qui simule les différents coups du joueur 2 (les rouges)
	* Le principe est le suivant, on simule tout les états qu'il est possibles
	*  d'attiendre à partir d'un état initial. Chaque simulation retourne un score.
	* Le but est de minimser ce score afin d'empêcher le joueur 1 de gagner.
	*/
	public int min_max(Plateau p_courant, int column, int line, int depth) {
		
		// Si le joueur humain a gagné
		if (p_courant.gagner(column, line) == 1) {
			return 1000;
		} else if (depth == 0) {
			return p_courant.eval(column, line);
		}
		
		int j, value;
		int value_min = Integer.MAX_VALUE;
		
		// p_child servira comme support pour tester tout les coups possibles
		Plateau p_child = p_courant.clone();
		p_child.setJoueur(-1);
		
		// Création de la liste des coups possibles avec mélange pour créer un caractère aléatoire
		ArrayList<Integer> list_coup = new ArrayList<Integer>();
		for(int i=0; i<7; i++){
			if (p_child.coupValide(i)) {
				list_coup.add(i);
			}
		}
		Collections.shuffle(list_coup);
		
		// Boucle de parcourt des 7 enfants
		for (int i : list_coup) {
			// On joue sur le plateau cloné dans la colonne i 
			j = p_child.joue(i);
			value = max_min(p_child, i, j, depth - 1);
			
			// On teste si la valeur minimum trouvée jusqu'a maintenant est supérieur à la valeur renvoyé par le fils
			if (value_min > value) {
				// Si oui, on met à jour les différentes variables
				value_min = value;
				// Si on est sur le premier étage de l'agorithme min_max, on met à jour la variable coup
				if (depth == level) {
					coup = i;
				}
			}
			
			// Et on annule le coup qui vient d'être joué sur le plateau avant de simuler le prochain fils
			p_child.getBack(i, j);
		}
		
		// Retourne la valeur minimale trouvée
		return value_min - depth;
	}
	
	/** Algorithme max_min qui simule les différents coups du joueur 1 (les jaunes)
	* Dans cette fonction on cherche à maximiser le score du joueur 1
	*/
	public int max_min(Plateau p_courant, int column, int line, int depth) {
		
		// Si l'ordinateur a gagné
		if (p_courant.gagner(column, line) == -1) {
			return -1000;
		} else if (depth == 0) {
			return -p_courant.eval(column, line);
		}
		
		int j, value;
		int value_max = Integer.MIN_VALUE;
		
		// p_child servira comme support pour tester tout les coups possibles
		Plateau p_child = p_courant.clone();
		p_child.setJoueur(1);
		
		// Création de la liste des coups possibles avec mélange pour créer un caractère aléatoire
		ArrayList<Integer> list_coup = new ArrayList<Integer>();
		for(int i=0; i<7; i++){
			if (p_child.coupValide(i)) {
				list_coup.add(i);
			}
		}
		Collections.shuffle(list_coup);
		
		for (int i : list_coup) {
			// On joue sur le plateau cloné dans la colonne i
			j = p_child.joue(i);
			value = min_max(p_child, i, j, depth - 1);
			
			// On teste si la valeur maximale trouvée jusqu'a maintenant est inférieur à la valeur renvoyé par le fils
			if (value_max < value) {
				// Si oui, on met à jour les différentes variables
				value_max = value;
			}
			
			// On annule le coup qui vient d'être joué sur le plateau
			p_child.getBack(i, j);
		}
		
		// Retourne la valeur maximale trouvée
		return value_max + depth;
	}
}
