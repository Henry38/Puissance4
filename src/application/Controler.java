package application;

import java.util.ArrayList;

import artificial_intelligence.IA_AlphaBeta;
import data.GameType;
import data.Plateau;

public class Controler implements Runnable {
	
	public Plateau p;
	private int coup;
	private boolean endGame, cpuPhase;
	private GameType gameType;
	private ArrayList<GameObserver> listObservers;
	
	/** Constructeur */
	public Controler() {
		this.p = null;
		this.coup = -1;
		this.endGame = false;
		this.cpuPhase = false;
		this.gameType = null;
		this.listObservers = new ArrayList<GameObserver>();
	}
	
	/** Retourne le plateau de jeu courant */
	public void newGame(GameType gameType) {
		p = new Plateau();
		coup = -1;
		endGame = false;
		cpuPhase = (gameType.isCpuTurn(p));
		this.gameType = gameType;
		firePlateauCleared();
	}
	
	
	/** Ajoute un GameObserver a la liste */
	public void addGameObserver(GameObserver obs) {
		listObservers.add(obs);
	}
	
	/** Notifie les observers d'un changement d'une colonne */
	private void firePlateauUpdated(int line, int column, int color) {
		for (GameObserver obs : listObservers) {
			obs.updatePlateau(line, column, color);
		}
	}
	
	/** Notifie les observers d'un nettoyage du plateau */
	private void firePlateauCleared() {
		for (GameObserver obs : listObservers) {
			obs.clearPlateau();
		}
	}
	
	
	/** Cherche le meilleur coup pour l'ordinateur */
	private void searchCoup() {
		cpuPhase = true;
		// Calcul du meilleur coup
		IA_AlphaBeta t = new IA_AlphaBeta(p, 4);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		coup = t.getCoup();
	}
	
	/** Joue le coup passe en parametre */
	public void jouerCoup(int coup) {
		int line = p.jouerCoup(coup);
		firePlateauUpdated(line, coup, p.getColor(line, coup));
	}
	
	/** Verifie l'etat du plateau une fois le coup joue */
	private void resultCoup() {
		// Si la partie est finie
		if (p.isFull()) {
			endGame = true;
		}
		if (p.hasWon(p.getJoueurAdverse())) {
			endGame = true;
		}
		// Mise a jour de la variable cpuPhase
		cpuPhase = (gameType.isCpuTurn(p));
	}
	
	
	/** Retourne vrai si c'est au tour d'un joueur humain de jouer
	 * independamment de l'etat du plateau */
	public boolean isHumanPhase() {
		return (!endGame && !cpuPhase);
	}
	
	/** Declenche les evenements lies au clic sur une colonne */
	public void columnClicked(int column) {
		if (isHumanPhase() && p.isCoupValid(column) && coup == -1) {
			coup = column;
			synchronized (this) {
				this.notify();
			}
		}
	}
	
	
	/** Gestion du coup joue au tour par tour */
	@Override
	public void run() {
		synchronized (this) {
			while (!endGame) {
				// Fait jouer l'ordinateur
				if (gameType.isCpuTurn(p)) {
					searchCoup();
					jouerCoup(coup);
				// Sinon attend qu'un humain joue un coup
				} else {
					try {
						wait();
						jouerCoup(coup);
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				resultCoup();
				coup = -1;
			}
		}
	}
}
