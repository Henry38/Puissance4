package data;


public class Plateau {
	
	private int[][] table;
	private int joueur;
	
	/** Constructeur */
	public Plateau() {
		this.table = new int[6][7];
		this.joueur = 1;
		clear();
	}
	
	/** Clone le plateau */
	public Plateau clone() {
		Plateau p = new Plateau();
		for (int j=0; j<6; j++) {
			for (int i=0; i<7; i++) {
				p.table[j][i] = table[j][i];
			}
		}
		return p;
	}
	
	/** Retourne le numero du joueur courant */
	public int getJoueur() {
		return joueur;
	}
	
	/** Retourne le numero du joueur adverse */
	public int getJoueurAdverse() {
		return joueur * -1;
	}
	
	/** Retourne vrai si la cellule pointee n'est pas en dehors du plateau */
	public boolean valid(int line, int column) {
		return (line >= 0 && line < 6 && column >= 0 && column < 7);
	}
	
	/** Retourne la couleur de la cellule pointee */
	public int getColor(int line, int column) {
		return table[line][column];
	}
	
	/** Retourne vrai si le coup passe en parametre est jouable */
	public boolean isCoupValid(int column) {
		return (table[0][column] == 0);
	}
	
	/** Retourne la derniere ligne vide la colonne, -1 si la colonne est pleinne */
	public int getLineValid(int column) {
		if (!isCoupValid(column)) {
			return -1;
		}
		int line = 5;
		while (line >= 0 && getColor(line,column) != 0) {
			line--;
		}
		return line;
	}
	
	/** Retourne vrai si le plateau de jeu est complet */
	public boolean isFull() {
		boolean flag = true;
		int column = 0;
		while (flag && column <= 6) {
			flag &= (table[0][column] != 0);
			column++;
		}
		return flag;
	}
	
	
	/** Set la couleur de la cellule pointee */
	public void setColor(int line, int column, int color) {
		table[line][column] = color;
	}

	/** Nettoie le plateau de jeu */
	public void clear() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				setColor(i, j, 0);
			}
		}
	}
	
	
	/** Joue sur le plateau le coup passe en parametre et switch de joueur */
	public int jouerCoup(int column) {
		int line = getLineValid(column);
		setColor(line, column, joueur);
		switchJoueur();
		return line;
	}
	
	/** Annule le coup passe en parametre */
	public void getBack(int line, int column) {
		switchJoueur();
		table[line][column] = 0;
	}
	
	/** Change le joueur du plateau */
	public void switchJoueur() {
		this.joueur *= -1;
	}
	
	
	
	
	/** Retourne vrai si le joueur passe en parametre a gagner */
	public boolean hasWon(int joueur) {
		boolean res = false;
		for (int line = 0; line < 6 && !res; line++) {
			for (int column = 0; column < 7 && !res; column++) {
				if (getColor(line, column) == joueur) {
					res = (chaine_max(line, column) >= 4);
				}
			}
		}
		return res;
	}
	
	/** Fonction d'evaluation */
	public int evaluation() {
		int res = 0;
		int color;
		for (int line = 0; line < 6; line++) {
			for (int column = 0; column < 7; column++) {
				color = getColor(line, column);
				if (color != 0) {
					res += color * chaine_max(line, column);
				}
			}
		}
		return res;
	}
	
	/** methode qui renvoie la chaine maximum du jeton donne en parametre */
	public int chaine_max(int line, int column) {
   		// Recupere le joueur qui a joue le dernier jeton
   		int joueur_jeton = getColor(line, column);
   		if (joueur_jeton == 0) {
   			return 0;
   		}
   		
   		boolean loop;
   		int chaineMax = 0;
   		int chaine = 1;
   		for (int dy : new int[] {-1, 0, 1}) {
   			for (int dx : new int[] {-1, 0, 1}) {
   				chaine = 1;
   				if (dy != 0 || dx != 0) {
   					for (int sign : new int[] {-1, 1}) {
   						int i = line;
   						int j = column;
   						do {
   							loop = false;
   							i += sign * dy;
   							j += sign * dx;
   							if (valid(i, j) && getColor(i, j) == joueur_jeton) {
   								chaine++;
   								loop = true;
   							}
   						} while (loop);
   						if (chaine > chaineMax) {
   							chaineMax = chaine;
   						}
   					}
   				}
   			}
   		}
   		
   		return chaineMax;
   	}
	
}
