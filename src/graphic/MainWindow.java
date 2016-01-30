package graphic;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import application.Controler;
import data.GameType;

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
		
//		JMenuBar menu = new Menu();
//		setJMenuBar(menu);
		
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
		
		controler.newGame(GameType.joueur_contre_ordinateur);
		//controler.newGame(GameType.joueur_contre_joueur);
		//controler.newGame(GameType.ordinateur_contre_ordinateur);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread t = new Thread(controler);
		t.start();
	}
}
