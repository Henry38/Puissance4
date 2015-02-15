import java.util.ArrayList;
import java.util.Collections;


public class IA_AlphaBeta extends Thread {
	
	private int coup, level;
	private Plateau p;
	
	/** Constucteur */
	public IA_AlphaBeta(Plateau p, int depth) {
		this.coup = -1;
		this.level = depth;
		this.p = p;
	}
	
	/** r�cup�re le meilleur coup que l'ia a calcul� */
	public int getCoup() {
		return coup;
	}
	
	/** lance le calcul du meilleur coup pour le plateau courant */
	public void run() {
		coup = -1;
		ab_min_max(p, -1, -1, Integer.MIN_VALUE, Integer.MAX_VALUE, level);
	}
	
	/** Algorithme Alpha-Beta
	* Cet algorithme est une optmisation de l'algorithme min-max.
	* Sauf qu'ici on ne parcours pas tout les noeuds de l'arbre. Sous
	* certaines conditions on sait qu'il n'est pas n�cessaire de prsuivre
	* la cherche du meilleur coup. Les deux param�tres en plus alpha et beta
	* permettent de l'�lagage alpha-beta
	*/
	public int ab_min_max(Plateau p_courant, int column, int line, int alpha, int beta, int depth) {
		
		// Si le joueur humain a gagn�
		if (p_courant.gagner(column, line) == 1) {
			return 1000 + depth;
		} else if (depth == 0) {
			return p_courant.eval(column, line);
		}
		
		int j, value;
		
		// p_child servira comme support pour tester tout les coups possibles
		Plateau p_child = p_courant.clone();
		p_child.setJoueur(-1);
		
		// creation de la liste des coups possibles avec m�lange pour cr�er un caract�re al�atoire
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
			value = ab_max_min(p_child, i, j, alpha, beta, depth - 1);
			
			if (value < beta) {
				beta = value;
				// Mise � jour du coup � retourner
				if (depth == level) {
					coup = i;
				}
				// Si alpha est sup�rieur � beta cela ne sert � rien de cherche un meilleur beta puisque
				// le joueur 2 prefera remonter alpha plutot que beta
				if (alpha > beta) {
					return beta;
				}
			}
			
			// On anuule le coup jou� por simuler le prochain fils
			p_child.getBack(i, j);
		}
		
		return beta;
	}
	
	/** Algorithme Alpha-Beta
	* Ici on cherche � maximiser le score du joueur 1 en utilisant le m�me
	* d'�lagage que dans la fonction pr�c�dante
	*/
	public int ab_max_min(Plateau p_courant, int column, int line, int alpha, int beta, int depth) {

		// Si l'ordinateur a gagn�
		if (p_courant.gagner(column, line) == -1) {
			return -1000 - depth;
		} else if (depth == 0) {
			return -p_courant.eval(column, line);
		}
		
		int j, value;
		
		// p_child servira comme support pour tester tout les coups possibles
		Plateau p_child = p_courant.clone();
		p_child.setJoueur(1);
		
		// creation de la liste des coups possibles avec m�lange pour cr�er un caract�re al�atoire
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
			value = ab_min_max(p_child, i, j, alpha, beta, depth - 1);
			
			if (value > alpha) {
				alpha = value;
				if (alpha > beta) {
					return alpha;
				}
			}
			
			// On anuule le coup jou� por simuler le prochain fils
			p_child.getBack(i, j);
		}
		
		return alpha;
	}
}
