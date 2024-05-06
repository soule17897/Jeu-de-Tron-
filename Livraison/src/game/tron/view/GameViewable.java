package game.tron.view;

import game.tron.controller.GameController;
import game.tron.observer.ModelListener;

public interface GameViewable {
	
	void setController(GameController controller );
	void showWinner(String name);
	void updateGameState(Object o);
	void setGridDimensionPrompt();
	void addPlayerPrompt();
	
	
}
