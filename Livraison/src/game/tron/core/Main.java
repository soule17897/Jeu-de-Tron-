  package game.tron.core;

import java.util.ArrayList;

import game.tron.Heuristique.Heuristic;
import game.tron.Heuristique.Heuristic2;
import game.tron.Heuristique.ProportionTerrain;
import game.tron.controller.GameController;
import game.tron.model.Grille;
import game.tron.model.Player;
import game.tron.model.PlayerFactory;
import game.tron.model.PlayerFactory.PlayerType;
import game.tron.model.Position;
import game.tron.model.TronGame;
import game.tron.view.GameSwingView;
import game.tron.view.GameViewable;

public class Main {

	public static void main(String[] args) {
        
		int taille = 20;
		Grille grille = new Grille(taille);
		Heuristic heuristique = new Heuristic2();
		Heuristic heuristique1 = new ProportionTerrain();
		ArrayList<Player> gamePlayers = new ArrayList<>();
		
		Player p2 = PlayerFactory.makePlayer(PlayerType.Maxn, 3 ,heuristique1);
		
		Player p3 = PlayerFactory.makePlayer(PlayerType.Paranoid, 3 ,heuristique1);
		
		gamePlayers.add(p2);
		gamePlayers.add(p3);
		
		TronGame game = new TronGame(grille, gamePlayers);
		
		game.setPlayerLastPosition(p2, new Position(10, 0));
		game.setPlayerLastPosition(p3, new Position(10, 19));

		
		GameViewable view = new GameSwingView(taille);
		GameController controller = new GameController(game, view);
		controller.play();
	
	}
}