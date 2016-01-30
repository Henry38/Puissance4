package data;
import java.util.EventListener;


public interface CoupListener extends EventListener {
		public void jouer(int column);
		public void finAnimation(int column, int line);
		public void new_game(int type);
}
