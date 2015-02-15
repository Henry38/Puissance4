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
	
	/** r�cup�re le meilleur coup que l'ia a calcul� */
	public int getCoup() {
		return coup;
	}
	
	/** lance le calcul du meilleur coup pour le plateau courant */
	public void run() {
		coup = -1;
		min_max(p, -1, -1, level);
	}
	
	/** joue al�atoirement sur le plateau */
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
				// On simule le coup jou� en colonne i
				j = p_child.joue(i);
				value = p_child.eval(i, j);
				
				// On met � jour les variables si le r�sultat trouv� est meilleur qui celui trouv� jusqu'� maintenant
				if (value_min > value) {
					value_min = value;
					coup = i;
				}
				
				// Et on annule le coup qui vient d'�tre jou� sur le plateau avant de simuler le prochain fils
				p.getBack(i, j);
			}
		}
	}
	
	/** Algorithme min_max qui simule les diff�rents coups du joueur 2 (les rouges)
	* Le principe est le suivant, on simule tout les �tats qu'il est possibles
	*  d'attiendre � partir d'un �tat initial. Chaque simulation retourne un score.
	* Le but est de minimser ce score afin d'emp�cher le joueur 1 de gagner.
	*/
	public int min_max(Plateau p_courant, int column, int line, int depth) {
		
		// Si le joueur humain a gagn�
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
		
		// Cr�ation de la liste des coups possibles avec m�lange pour cr�er un caract�re al�atoire
		ArrayList<Integer> list_coup = new ArrayList<Integer>();
		for(int i=0; i<7; i++){
			if (p_child.coupValide(i)) {
				list_coup.add(i);
			}
		}
		Collections.shuffle(list_coup);
		
		// Boucle de parcourt des 7 enfants
		for (int i : list_coup) {
			// On joue sur le plateau clon� dans la colonne i 
			j = p_child.joue(i);
			value = max_min(p_child, i, j, depth - 1);
			
			// On teste si la valeur minimum trouv�e jusqu'a maintenant est sup�rieur � la valeur renvoy� par le fils
			if (value_min > value) {
				// Si oui, on met � jour les diff�rentes variables
				value_min = value;
				// Si on est sur le premier �tage de l'agorithme min_max, on met � jour la variable coup
				if (depth == level) {
					coup = i;
				}
			}
			
			// Et on annule le coup qui vient d'�tre jou� sur le plateau avant de simuler le prochain fils
			p_child.getBack(i, j);
		}
		
		// Retourne la valeur minimale trouv�e
		return value_min - depth;
	}
	
	/** Algorithme max_min qui simule les diff�rents coups du joueur 1 (les jaunes)
	* Dans cette fonction on cherche � maximiser le score du joueur 1
	*/
	public int max_min(Plateau p_courant, int column, int line, int depth) {
		
		// Si l'ordinateur a gagn�
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
		
		// Cr�ation de la liste des coups possibles avec m�lange pour cr�er un caract�re al�atoire
		ArrayList<Integer> list_coup = new ArrayList<Integer>();
		for(int i=0; i<7; i++){
			if (p_child.coupValide(i)) {
				list_coup.add(i);
			}
		}
		Collections.shuffle(list_coup);
		
		for (int i : list_coup) {
			// On joue sur le plateau clon� dans la colonne i
			j = p_child.joue(i);
			value = min_max(p_child, i, j, depth - 1);
			
			// On teste si la valeur maximale trouv�e jusqu'a maintenant est inf�rieur � la valeur renvoy� par le fils
			if (value_max < value) {
				// Si oui, on met � jour les diff�rentes variables
				value_max = value;
			}
			
			// On annule le coup qui vient d'�tre jou� sur le plateau
			p_child.getBack(i, j);
		}
		
		// Retourne la valeur maximale trouv�e
		return value_max + depth;
	}
}
