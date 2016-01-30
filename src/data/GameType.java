package data;

public enum GameType {
	
	joueur_contre_joueur(0),
	joueur_contre_ordinateur(1),
	ordinateur_contre_joueur(2),
	ordinateur_contre_ordinateur(3);
	
	private int type;
	
	GameType(int type) {
		this.type = type;
	}
	
	public boolean isCpuTurn(Plateau p) {
		if (type == 0) {
			return false;
		} else if (type == 1) {
			return (p.getJoueur() == -1);
		} else if (type == 2) {
			return (p.getJoueur() == 1);
		} else {
			return true;
		}
	}
}
