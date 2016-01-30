package graphic;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import application.Controler;
import artificial_intelligence.IA_AlphaBeta;
import data.GameType;
import data.Plateau;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	
	/** Constructeur */
	public MainWindow(Controler controler) {
		super("Puissance 4");
		
		GameBoard gameBoard = new GameBoard(controler);
		controler.addGameObserver(gameBoard);
		
		panneau = new JPanel();
		panneau.setLayout(new GridBagLayout());
		panneau.add(gameBoard);
		setContentPane(panneau);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final Controler controler = new Controler();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow fen = new MainWindow(controler);
				fen.setVisible(true);
			}
		});
		
		//controler.newGame(GameType.joueur_contre_joueur);
		controler.newGame(GameType.joueur_contre_ordinateur);
		//controler.newGame(GameType.ordinateur_contre_joueur);
		//controler.newGame(GameType.ordinateur_contre_ordinateur);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread t = new Thread(controler);
		t.start();
		
//		controler.jouerCoup(3);
//		controler.jouerCoup(6);
//		controler.jouerCoup(3);
//		controler.jouerCoup(6);
//		controler.jouerCoup(3);
//		
//		IA_AlphaBeta ia = new IA_AlphaBeta(controler.p, 2);
//		ia.run();
//		int coup = ia.getCoup();
//		
//		Plateau p = controler.p;
//		System.out.println(p);
//		//System.out.println(controler.p.getJoueur() + ", " + coup);
	}
}
