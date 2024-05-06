package game.tron.view;

import java.util.Scanner;

import game.tron.Heuristique.Heuristic;
import game.tron.Heuristique.Heuristic2;
import game.tron.Heuristique.ParanoidScoringHeuristic;
import game.tron.controller.GameController;
import game.tron.model.Player;
import game.tron.model.PlayerFactory;
import game.tron.model.PlayerFactory.PlayerType;

public class GameViewTerminal implements GameViewable {

	private GameController controller;

	public GameViewTerminal() {

	}

	@Override
	public void setController(GameController controller) {
		this.controller = controller;
	}

	@Override
	public void showWinner(String name) {

		// System.out.println("Le joueur gagnant est : " + name);

		if (name != null) {
			// System.out.println("Le joueur gagnant est : " + name);
		} else {
			// System.out.println("Aucun joueur n'a gagné.");
		}

	}

	@Override
	public void updateGameState(Object o) {
		// System.out.println(o.toString());
	}

	

	@Override
	public void setGridDimensionPrompt() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Donner la taille de la grille : ");
        int taille = sc.nextInt();
        this.controller.setGrid(taille);
	}

	@Override
	public void addPlayerPrompt() {
	    
//		Scanner sc = new Scanner(System.in);
//		PlayerType player = null;
//		System.out.println(" *********** CHOIX DU TYPE DU JOUEUR ************* ");
//		System.out.println("Tapez 1 pour Maxn ");
//		System.out.println("Tapez 2 pour Paranoid");
//		int choix = sc.nextInt();
//		Heuristic h = null;
//		if(choix == 1) {
//			player = PlayerType.Maxn;
//			h = new Heuristic2();
//		}
//		else if(choix == 2) {
//			player = PlayerType.Paranoid;
//			h = new ParanoidScoringHeuristic();
//		}
//		
//		System.out.println("Donner le nom joueur  : ");
//		String name = sc.nextLine();
//		System.out.println("Donner Symbole  : ");
//		String symbol = sc.nextLine();
//		System.out.println("Donner le last symbol  : ");
//		String lastSymbol = sc.next();
//		System.out.println("Donner la profondeur maximale  : ");
//		int depth = sc.nextInt();
//		
//	    //Player plyr = PlayerFactory.makePlayer(player, name, symbol, lastSymbol, depth, h);
//	   // this.controller.addPlayer(plyr);
	}
	
	

}
