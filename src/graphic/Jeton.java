package graphic;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Jeton extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private int line, column;
	
	/** Constructeur */
	public Jeton(int line, int column) {
		super();
		this.line = line;
		this.column = column;
		setBounds(8 + column*72, line*72, 72, 72);
		setIconJeton(0);
	}
	
	/** Retourne la ligne dans laquelle le jeton est present */
	public int getLine() {
		return column;
	}
	
	/** Retourne la colonne dans laquelle le jeton est present */
	public int getColumn() {
		return line;
	}
	
	/** Modifie la ligne du jeton */
	public void setLine(int line) {
		this.line = line;
		setLocation(getX(), line*72);
	}
	
	/** Modifie la colonne du jeton */
	public void setColumn(int column) {
		this.column = column;
		setLocation(8 + column*72, getY());
	}
	
	/** Change l'apparence du jeton */
	public void setIconJeton(int color) {
		setIcon(null);
		if (color == 1) {
			setIcon(new ImageIcon("ressources/jeton_jaune.png"));
		} else if (color == -1) {
			setIcon(new ImageIcon("ressources/jeton_rouge.png"));
		}
	}
}