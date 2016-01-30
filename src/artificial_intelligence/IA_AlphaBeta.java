package artificial_intelligence;

import java.util.ArrayList;
import data.Plateau;

public class IA_AlphaBeta extends Thread {
	
	private int coup, level;
	private Plateau p;
	
	/** Constucteur */
	public IA_AlphaBeta(Plateau p, int depth) {
		this.coup = -1;
		this.level = depth;
		this.p = p;
	}
	
	/** récupère le meilleur coup que l'ia a calculé */
	public int getCoup() {
		return coup;
	}
	
	/** Meilleur coup des noirs */
	private void jouerAlphaBeta() {
		int value, value_min = Integer.MAX_VALUE;
		
		ArrayList<Integer> listCoup = new ArrayList<Integer>();
		for(int i = 0; i < 7; i++){
			if (p.isCoupValid(i)) {
				listCoup.add(i);
			}
		}
		
		for (int coup : listCoup) {
			int line = p.jouerCoup(coup);
			
			value = ab_max_min(p, Integer.MIN_VALUE, Integer.MAX_VALUE, level-1);
			
			p.getBack(line, coup);
			
			if (value <= value_min) {
				value_min = value;
				this.coup = coup;
			}
		}
	}
	
	/** Meilleur coup des blancs */
	private void jouerBetaAlpha() {
		int value, value_max = Integer.MIN_VALUE;
		
		ArrayList<Integer> listCoup = new ArrayList<Integer>();
		for(int i = 0; i < 7; i++){
			if (p.isCoupValid(i)) {
				listCoup.add(i);
			}
		}
		
		for (int coup : listCoup) {
			int line = p.jouerCoup(coup);
			
			value = ab_min_max(p, Integer.MIN_VALUE, Integer.MAX_VALUE, level-1);
			
			p.getBack(line, coup);
			
			if (value >= value_max) {
				value_max = value;
				this.coup = coup;
			}
		}
	}
	
	/** Algorithme Alpha Beta MinMax */
	public int ab_min_max(Plateau p_courant, int alpha, int beta, int depth) {
		
		// p_courant.joueur == -1
		if (p_courant.hasWon(-1)) {
			return Integer.MAX_VALUE;
		} else if (depth == 0) {
			return p_courant.evaluation();
		}
		
		// p_child servira comme support pour tester tout les coups possibles
		Plateau p_child = p_courant;
		// Creation de la liste des coups possibles
		ArrayList<Integer> listCoup = new ArrayList<Integer>();
		for(int i = 0; i < 7; i++){
			if (p_child.isCoupValid(i)) {
				listCoup.add(i);
			}
		}
		int value;
		
		// Boucle de parcourt de tout les enfants
		for (int coup : listCoup) {
			int line = p_child.jouerCoup(coup);
			
			value = ab_max_min(p_child, alpha, beta, depth-1);
			
			p_child.getBack(line, coup);
			
			if (value < beta) {
				beta = value;
				if (alpha > beta) {
					return beta;
				}
			}
		}
		
		return beta;
	}
	
	/** Algorithme Alpha Beta MaxMin */
	public int ab_max_min(Plateau p_courant, int alpha, int beta, int depth) {
		
		// p_courant.joueur == 1
		if (p_courant.hasWon(1)) {
			return Integer.MIN_VALUE;
		} else if (depth == 0) {
			return p_courant.evaluation();
		}
		
		// p_child servira comme support pour tester tout les coups possibles
		Plateau p_child = p_courant;
		// Creation de la liste des coups possibles
		ArrayList<Integer> listCoup = new ArrayList<Integer>();
		for(int i = 0; i < 7; i++){
			if (p_child.isCoupValid(i)) {
				listCoup.add(i);
			}
		}
		int value;
		
		for (int coup : listCoup) {
			int line = p_child.jouerCoup(coup);
			
			value = ab_min_max(p_child, alpha, beta, depth-1);
			
			p_child.getBack(line, coup);
			
			if (value > alpha) {
				alpha = value;
				if (alpha > beta) {
					return alpha;
				}
			}
		}
		
		return alpha;
	}
	
	/** Lance le calcul du meilleur coup sur plateau */
	public void run() {
		coup = -1;
		if (p.getJoueur() == 1) {
			jouerBetaAlpha();
		} else {
			jouerAlphaBeta();
		}
	}
}
