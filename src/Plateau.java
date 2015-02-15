

public class Plateau {
	
	public int[][] table;
	public int joueur;
	
	/** Constructeur */
	public Plateau() {
		table = new int[6][7];
		joueur = 1;
	}
	
	/** methode qui renvoie le joueur courant */
	public int getJoueur() {
		return joueur;
	}
	
	/** definie le joueur */
	public void setJoueur(int i) {
		joueur = i;
	}
	
	/** methode qui change le joueur courant */
	public void switch_joueur() {
		joueur = -joueur;
	}
	
	/** nettoie entierement le plateau du jeu et met le joueur courant a 1 */
	public void clear() {
		joueur = 1;
		for (int i=0; i<7; i++) {
			for (int j=0; j<6; j++) {
				table[j][i] = 0;
			}
		}
	}
	
	/** annule le coup passe en parametre */
	public void getBack(int column, int line) {
		table[line][column] = 0;
	}
	
	/** clone et renvoie le plateau courant */
	public Plateau clone() {
		Plateau p = new Plateau();
		for (int i=0; i<7; i++) {
			for (int j=0; j<6; j++) {
				p.table[j][i] = table[j][i];
			}
		}
		return p;
	}
	
	/** methode d'evaluation pour l'IA */
	public int eval(int column, int line) {
		return chaine_max(column, line) * 10;
	}
	
	/** methode qui donne l'appartenance d'une case a un joueur */
	public int case_joueur(int column, int line) {
		if (line < 0 || line > 5 || column < 0 || column > 6) {
			return 0;
		}
		return table[line][column];
	}
	
	/** methode qui renvoie le joueur qui a gagner sur le dernier jouer sinon nul renvoie 0 */
	public int gagner(int column, int line) {
		if (chaine_max(column, line) >= 4) {
			return joueur;
		}
		return 0;
	}
	
	/** methode qui renvoie la chaine maximum du jeton donne en parametre */
	public int chaine_max(int column, int line) {
   		int res = 0;
   		int i, j, chaine;
   		
   		// Recupere le joueur qui a joue le dernier jeton
   		int joueur_jeton = case_joueur(column, line);
   		if (joueur_jeton == 0) {
   			return 0;
   		}
   		
   		// Test en ligne
   		chaine = 1;
   		i = column+1;
   		while (case_joueur(i, line) == joueur_jeton) {
   			chaine++;
   			i++;
   		}
   		i = column-1;
   		while(case_joueur(i, line) == joueur_jeton) {
   			chaine++;
   			i--;
   		}
   		// Mise a jour
   		if (res < chaine) {
   			res = chaine;
   		}
   		
   		// Test en colonne
   		chaine = 1;
   		j = line+1;
   		while(case_joueur(column, j) == joueur_jeton) {
   			chaine++;
   			j++;
   		}
   		j = line-1;
   		while(case_joueur(column, j) == joueur_jeton) {
   			chaine++;
   			j--;
   		}
   		// Mise a jour
   		if (res < chaine) {
   			res = chaine;
   		}
   		
   		
   		// Test sur l'anti diagonale
   		chaine = 1;
   		i = column+1;
   		j = line+1; 		
   		while(case_joueur(i, j) == joueur_jeton) {
   			chaine++;
   			i++;
   			j++;
   		}
   		i = column-1;
   		j = line-1;
   		while(case_joueur(i, j) == joueur_jeton) {
   			chaine++;
   			i--;
   			j--;
   		}
   		// Mise a jour
   		if (res < chaine) {
   			res = chaine;
   		}
   		
   		
   		// Test dur la diagonale
   		chaine = 1;
   		i = column+1;
   		j = line-1;	
   		while(case_joueur(i,j) == joueur_jeton) {
   			chaine++;
   			i++;
   			j--;
   		}
   		i = column-1;
   		j = line+1;
   		while(case_joueur(i,j) == joueur_jeton) {
   			chaine++;
   			i--;
   			j++;
   		}
   		// Mise a jour
   		if (res < chaine) {
   			res = chaine;
   		}
   		
   		return res;
   	}
	
	/** methode qui donne la ligne du pion joue en colonne i */
	public int move_line(int column) {
		int line = 0;
		do {
			line++;
		} while(line < 6 && table[line][column] == 0);
		return line-1;
	}
	
	/** methode qui joue un coup pour le joueur courant dans la colonne passe en parametre */
	public int joue(int column) {
		int line = move_line(column);
		table[line][column] = joueur;
		return line;
	}
	
	/** renvoie vrai si le coup joue dans la colonne passe en parametre est un coup jouable */
	public boolean coupValide(int column) {
		if (column < 0 || column > 6) {
			return false;
		} else {
			return (table[0][column] == 0);
		}
	}
	
	/** renvoie vrai si le plateau de jeu est complet */
	public boolean complet() {
		boolean flag = true;
		int column = 0;
		while (flag && column <= 6) {
			flag &= (table[0][column] != 0);
			column++;
		}
		return flag;
	}
}
