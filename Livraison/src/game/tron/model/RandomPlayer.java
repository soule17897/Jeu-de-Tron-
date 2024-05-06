package game.tron.model;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomPlayer extends AbstractPlayer {

	private Random random;

	private int nombreDeNoeud;

	public RandomPlayer(String name, String symbol, String lastSymbol, Random random) {
		super(name, symbol, lastSymbol);
		this.random = random;
		this.nombreDeNoeud = 0;
	}

	@Override
	public Move chooseMove(TronGame game) {
		List<Move> validMoves = game.validMoves(this);
		// System.out.println("Valides moves : " + validMoves);

		if (!validMoves.isEmpty()) {
			int randomIndex = this.random.nextInt(validMoves.size());
			return validMoves.get(randomIndex);
		} else {
			// Aucun mouvement valide disponible, renvoyer un mouvement par défaut
			return Move.NORTH; // Vous pouvez choisir un mouvement par défaut ou gérer cela selon vos besoins
		}
	}

	@Override
	public String toString() {
		return "Joueur aléatoire " + this.name;
	}

	@Override
	public int getNombreDeNoeud() {
		return nombreDeNoeud;
	}

	@Override
	public Move getLastMove(TronGame game) {
		// Récupérer la liste des derniers mouvements effectués par ce joueur
		List<Move> lastMoves = game.getPlayerLastMoves(this);

		// Vérifier si la liste n'est pas vide
		if (lastMoves != null && !lastMoves.isEmpty()) {
			// Renvoyer le dernier mouvement de la liste
			return lastMoves.get(lastMoves.size() - 1);
		} else {
			// Aucun mouvement n'a été effectué
			return null;
		}
	}
}
