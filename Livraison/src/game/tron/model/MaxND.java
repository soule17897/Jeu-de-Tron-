package game.tron.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import game.tron.Heuristique.Heuristic;

public class MaxND extends AbstractPlayer {

	private int maxProfondeur;
	/**
	 * L'heuristique du joueur
	 */
	private Heuristic heuristic;

	private int nombreDeNoeud;

	public MaxND(String name, String symbol, String lastSymbol, int maxProfondeur, Heuristic heuristic) {
		super(name, symbol, lastSymbol);
		this.maxProfondeur = maxProfondeur;
		this.heuristic = heuristic;
		this.nombreDeNoeud = 0;
		this.heuristic.setPlayer(this);
	}

	public MaxND(String name , int maxProfondeur, Heuristic heuristic) {
		super(name, new String(), new String());
		this.maxProfondeur = maxProfondeur;
		this.heuristic = heuristic;
		this.heuristic.setPlayer(this);

	}


	@Override
	public Move chooseMove(TronGame game) {
		Integer bestScore = null;
		Move bestMove = null;
		List<Move> bestMoves = new ArrayList<>();

		for (Move move : game.validMoves(this)) {

			// on recupere la liste des joueurs vivants
			List<Player> players = new ArrayList<>(game.getPlayersAlive());
			// on enleve le joueur courant
			players.remove(this);
			Map<Player, Move> moves = new HashMap<>();
			// on ajoute le joueur courant et son mouvement
			moves.put(this, move);
			// on recupere le score courant
			long startTime = System.nanoTime();
			Map<Player, Integer> currentScore = bestScore(game, maxProfondeur, players, moves);
			long endTime = System.nanoTime();
			long elapsedTime = endTime - startTime;
			// Affichage du temps écoulé
			// System.out.println("Temps écoulé : " + elapsedTime + " nanosecondes");

			if (currentScore != null) {
				// on recupere le score du joueur courant
				Integer playerScore = currentScore.get(this);
				// on compare le score du joueur courant avec le meilleur score
				if (bestScore == null || (playerScore != null && playerScore > bestScore)) {
					bestScore = playerScore;
					bestMoves.clear();
					bestMoves.add(move);

				} else if (playerScore != null && playerScore.equals(bestScore)) {
					// sinon si le score du joueur courant est egal au meilleur score on ajoute le
					// mouvement a la liste des meilleurs mouvements
					bestMoves.add(move);
				}
			}
		}

		// pour faire une variation des choix des joueurs
		if (!bestMoves.isEmpty()) {

			Random random = new Random();
			bestMove = bestMoves.get(random.nextInt(bestMoves.size()));
		}

		return bestMove;
	}

	private Map<Player, Integer> bestScore(TronGame game, int profondeur, List<Player> playersNotPlayed,
			Map<Player, Move> moves) {
		this.nombreDeNoeud++;
		if (game.isOver() || profondeur == 0) {

			return heuristic.evaluate(game);
		}

		if (playersNotPlayed.isEmpty()) {

			TronGame gameCopy = game.copy();
			gameCopy.execute(moves);
			return bestScore(gameCopy, profondeur - 1, gameCopy.getPlayersAlive(), new HashMap<>());
		}

		else {

			Player currentPlayer = playersNotPlayed.get(0);
			if (game.validMoves(currentPlayer).isEmpty()) {
				return heuristic.evaluate(game);
			}

			Map<Player, Integer> bestScore = null;

			for (Move move : game.validMoves(currentPlayer)) {

				Map<Player, Move> newMoves = new HashMap<>(moves);

				newMoves.put(currentPlayer, move);

				List<Player> newPlayers = new ArrayList<>(playersNotPlayed.subList(1, playersNotPlayed.size()));

				Map<Player, Integer> currentScore = bestScore(game, profondeur, newPlayers, newMoves);

				if (bestScore == null
						|| (currentScore != null && currentScore.get(currentPlayer) > bestScore.get(currentPlayer))) {

					bestScore = currentScore;
				}
			}

			return bestScore;

		}
	}

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

	@Override
	public String toString() {
		// return "Joueur MaxN: " + this.name + " n° " + hashCode();
		return "MaxN-" + this.name;

	}
}