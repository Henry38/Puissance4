package application;

public interface GameObserver {
	public void updatePlateau(int line, int column, int color);
	public void clearPlateau();
}
