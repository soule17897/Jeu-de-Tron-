package game.tron.model;

import java.util.*;

import game.tron.observer.AbstractListenableModel;

public class TronGame extends AbstractListenableModel implements Game {

	private Grille grille;
	private List<Player> players;
	private List<Player> playersAlive;
	/**
	 * Map qui associe à chaque joueur la liste de ses mouvements
	 */
	private Map<Player, List<Move>> moveOfPlayer;

	private  Map<Player, List<Position>> playerPositions;



	public TronGame(List<Player> players) {
		this.players = players;
		this.playersAlive = new ArrayList<>(players);
		this.moveOfPlayer = new HashMap<>();
		this.playerPositions = new HashMap<>();
		for (Player p : players) {
			this.moveOfPlayer.put(p, new ArrayList<>());
			this.playerPositions.put(p, new ArrayList<>());
		}
	}

	/**
	 * Constructeur de la classe TronGame
	 * 
	 * @param grille  la grille du jeu
	 * @param players la liste des joueurs
	 */
	public TronGame(Grille grille, List<Player> players) {
		this.grille = grille;
		this.players = players;
		this.playersAlive = new ArrayList<>(players);
		this.moveOfPlayer = new HashMap<>();
		this.playerPositions = new HashMap<>();

		for (Player p : players) {
			this.moveOfPlayer.put(p, new ArrayList<>());
			this.playerPositions.put(p, new ArrayList<>());
		}

	}

	public void initPlayerPosition(Player player) {

		this.moveOfPlayer.put(player, new ArrayList<>());
		this.playerPositions.put(player, new ArrayList<>());
	}

	/**
	 * Methode qui retourne la grille du jeu
	 *
	 * @ensures this.grille = grille
	 *
	 * @return la grille du jeu
	 */
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Methode qui retourne la liste des joueurs
	 *
	 * @ensures this.players = players
	 *
	 * @return la liste des joueurs
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Methode qui retourne la liste des joueurs en vie
	 *
	 * @ensures this.playersAlive = playersAlive
	 *
	 * @return la liste des joueurs en vie
	 */
	public List<Player> getPlayersAlive() {
		return playersAlive;
	}

	/**
	 * Methode qui modifie la liste des joueurs en vie
	 *
	 * @requires playersAlive != null
	 * @ensures this.playersAlive = playersAlive
	 *
	 * @param playersAlive la liste des joueurs en vie
	 */
	public void setPlayersAlive(List<Player> playersAlive) {
		this.playersAlive = playersAlive;
	}

	/**
	 * Methode qui met à jour la derniere position du joueur
	 *
	 * @requires player != null
	 * @requires position != null
	 *
	 * @ensures this.playerPositions.get(player).add(position)
	 * @ensures this.playerPositions.get(player).get(this.playerPositions.get(player).size()
	 *          - 1) = position // on est sur que la derniere position est bien
	 *          celle qu'on vient d'ajouter
	 *
	 * @param player   le joueur
	 * @param position la position du joueur
	 */
	public void setPlayerLastPosition(Player player, Position position) {
		this.playerPositions.get(player).add(position);
	}

	/**
	 * Methode qui met à jour la liste des positions du joueur
	 *
	 * @requires player != null
	 * @requires positions != null
	 *
	 * @ensures this.playerPositions.get(player) = positions
	 * @param player    le joueur
	 * @param positions la liste des positions
	 */
	public void setPlayerAllPositions(Player player, List<Position> positions) {
		this.playerPositions.put(player, positions);
	}

	/**
	 * Methode qui retourne la liste des mouvements du joueur
	 *
	 * @ensures this.playerPositions = playerPositions
	 *
	 * @return une map qui associe à chaque joueur la liste de ses positions
	 */
	public Map<Player, List<Position>> getPlayerPositions() {

		return playerPositions;
	}


	public Map<Player, List<Move>> getMoveOfPlayer() {
		return moveOfPlayer;
	}

	public void setPlayerPositions(Map<Player, List<Position>> playerPositions) {
		this.playerPositions = playerPositions;
	}


	/**
	 * Methode qui retourne la derniere position du joueur
	 *
	 * @requires player != null
	 * @requires playerPositions.get(player) != null
	 * @requires playerPositions.get(player).size() > 0
	 *
	 * @param player le joueur
	 *
	 * @return la derniere position du joueur
	 */

	public Position getPlayerLastPosition(Player player) {
		return playerPositions.get(player).get(playerPositions.get(player).size() - 1);
	}

	public void setPlayers(List<Player> players) {

		this.players = players;

	}


	public void setGrille(Grille grille) {

		this.grille = grille;

	}


	/**
	 * Methode qui trouve la position du joueur grace à son mouvement
	 *
	 * @requires move != null
	 * @requires player != null
	 *
	 * @param move   le mouvement
	 * @param player le joueur
	 *
	 * @return la position du joueur
	 */
	public Position findMovePosition(Move move, Player player) {
		Position p = getPlayerLastPosition(player);
		if (move == Move.NORTH) {
			return new Position(p.getX() - 1, p.getY());
		} else if (move == Move.SOUTH) {
			return new Position(p.getX() + 1, p.getY());
		} else if (move == Move.WEST)
			return new Position(p.getX(), p.getY() - 1);
		else {
			return new Position(p.getX(), p.getY() + 1);
		}
	}

	/**
	 * Methode qui retourne les mouvements valides du joueur
	 *
	 * @requires player != null
	 * @requires playerPositions.get(player) != null
	 *
	 * @param player le joueur
	 *
	 * @return la liste des mouvements valides
	 */
	@Override
	public List<Move> validMoves(Player player) {
		List<Move> validMoves = new ArrayList<>();

		// Vérifier les mouvements possibles à partir de la position actuelle
		for (Move move : Move.values()) {
			Position nextPosition = findMovePosition(move, player);

			// Vérifier si le mouvement est valide
			if (isValid(move, player) && !playerPositions.get(player).contains(nextPosition)) {
				validMoves.add(move);
			}
		}
		return validMoves;
	}

	/**
	 * Methode qui verifie si le jeu est terminé
	 *
	 * @requires this.playersAlive != null
	 *
	 * @ensures this.playersAlive.size() <= 1
	 *
	 * @return true si le jeu est terminé, false sinon
	 */
	@Override
	public boolean isOver() {

		return this.playersAlive.size() <= 1;
	}

	/**
	 * Methode qui execute les mouvements des joueurs
	 *
	 * @requires moves != null
	 * @requires moves.size() == this.getPlayersAlive().size()
	 * @requires this.players.contains(player) == true
	 * @requires this.isValid(move, player) == true
	 *
	 * @ensures this.playersAlive.size() <= this.players.size()
	 * @ensures this.moveOfPlayer.get(player).size() > 0
	 * @ensures this.grille.getCase(pos.getX(), pos.getY()) == true
	 * @ensures this.playerPositions.get(player).size() > 0
	 *
	 * @param moves la liste des mouvements
	 */
	@Override
	public void execute(Map<Player, Move> moves) {

		if (moves.size() != this.getPlayersAlive().size()) {
			throw new IllegalArgumentException(
					"La taille de la liste de mouvements ne correspond pas au nombre de joueurs en vie.");
		}

		Map<Player, Move> moves2 = new HashMap<>();

		for (Map.Entry<Player, Move> entry : moves.entrySet()) {
			Player player = entry.getKey();
			Move move = entry.getValue();

			Position pos = findMovePosition(move, player);
			for (Map.Entry<Player, Move> entry2 : moves.entrySet()) {
				Player player2 = entry2.getKey();
				Move move2 = entry2.getValue();

				Position pos2 = findMovePosition(move2, player2);

				if (!player.equals(player2) && pos.equals(pos2)) {
					this.playersAlive.remove(player);
					this.playersAlive.remove(player2);
					if (isValid(move, player)) {
						this.grille.setCase(pos.getX(), pos.getY(), true);
					}
				} else {
					moves2.put(player, move);
					moves2.put(player2, move2);
				}

			}
		}

		// Appliquer les mouvements pour chaque joueur
		for (Map.Entry<Player, Move> entry : moves2.entrySet()) {
			Player player = entry.getKey();
			Move move = entry.getValue();

			Position pos = findMovePosition(move, player);
			if (isValid(move, player) && this.players.contains(player)) {

				setPlayerLastPosition(player, pos);

				this.grille.setCase(pos.getX(), pos.getY(), true);
				moveOfPlayer.computeIfAbsent(player, k -> new ArrayList<>()).add(move);
			} else {
				this.playersAlive.remove(player);
			}
		}
		fireChange();
	}

	/**
	 * Methode qui verifie si le mouvement est valide
	 *
	 * @requires player != null
	 * @requires move != null
	 *
	 * @param move   le mouvement
	 * @param player le joueur
	 * @return true si le mouvement est valide, false sinon
	 */
	@Override
	public boolean isValid(Move move, Player player) {
		Position pos = findMovePosition(move, player);

		int row = pos.getX();
		int column = pos.getY();

		return grille.estDansGrille(row, column) && grille.estVide(row, column);

	}

	/**
	 * Methode qui retourne le gagnant
	 *
	 * @requires this.playersAlive != null
	 * @requires this.players != null
	 * @requires this.playersAlive.size() <= 1
	 *
	 * @ensures this.playersAlive.size() == 1
	 * @ensures this.players.contains(player) == true
	 * @ensures this.playersAlive.contains(player) == true
	 * @ensures this.playersAlive.get(0) == player
	 *
	 *
	 * @return le gagnant
	 */
	@Override
	public Player getWinner() {
		if (playersAlive.size() == 1)
			return playersAlive.get(0);
		else
			return null;
	}

	/**
	 * Methode qui met à jour la liste des mouvements du joueur
	 *
	 * @requires moveofplayer != null
	 *
	 * @ensures this.moveOfPlayer = moveofplayer
	 *
	 * @param moveofplayer la liste des mouvements du joueur
	 */
	public void setMoveOfPlayer(Map<Player, List<Move>> moveofplayer) {
		this.moveOfPlayer = moveofplayer;
	}

	/**
	 * Methode qui fait une copie du jeu
	 *
	 * 
	 * @return une copie du jeu
	 */
	@Override

	public TronGame copy() {

		List<Player> copyPlayers = new ArrayList<>(this.players);

		List<Player> copyPlayersAlive = new ArrayList<>(this.playersAlive);

		Map<Player, List<Move>> moveofplayerCopy = new HashMap<>();

		for (Player p : this.moveOfPlayer.keySet()) {
			// pour chaque joueur, on copie sa liste de mouvements
			moveofplayerCopy.put(p, new ArrayList<>(this.moveOfPlayer.get(p)));

		}

		TronGame copyTronGame = new TronGame(grille.copy(), copyPlayers);

		copyTronGame.setPlayersAlive(copyPlayersAlive);

		for (Player p : this.playerPositions.keySet()) {
			// pour chaque joueur, on copie sa liste de positions
			copyTronGame.setPlayerAllPositions(p, new ArrayList<>(this.playerPositions.get(p)));
		}
		copyTronGame.setMoveOfPlayer(moveofplayerCopy);

		return copyTronGame;

	}

	@Override
	public String toString() {
		System.out.println(playerPositions);
		System.out.println(playersAlive);
		System.out.println(moveOfPlayer);
		System.out.println(players);
		Map<Position, String> positionToSymbolMap = new HashMap<>();
		for (Player p : players) {
			for (Position pos : playerPositions.get(p)) {
				if (playerPositions.get(p).indexOf(pos) == playerPositions.get(p).size() - 1) {
					positionToSymbolMap.put(pos, p.getLastSymbol());
				} else {
					positionToSymbolMap.put(pos, p.getSymbol());
				}
			}

		}

		StringBuilder showGrille = new StringBuilder("   ");

		for (int i = 0; i < this.grille.getTaille(); i++) {
			showGrille.append(String.format("%3d", i));
		}
		showGrille.append("\n");

		for (int i = 0; i < this.grille.getTaille(); i++) {
			String ligne = String.format("%2d ", i);

			for (int j = 0; j < grille.getTaille(); j++) {
				Position currentPos = new Position(i, j);
				if (grille.getCase(i, j) && positionToSymbolMap.containsKey(currentPos)) {
					ligne += " " + positionToSymbolMap.get(currentPos) + " ";
				} else if (!grille.getCase(i, j)) {
					ligne += " . ";
				}
			}
			showGrille.append(ligne).append("\n");
		}
		return showGrille.toString();
	}



	public List<Move> getPlayerLastMoves(Player player) {
		return moveOfPlayer.get(player);
	}
	public Collection<Position> getEmptyPoints() {
		List<Position> emptyPoints = new ArrayList<>();
		for (int i = 0; i < grille.getTaille(); i++) {
			for (int j = 0; j < grille.getTaille(); j++) {
				if (!grille.getCase(i, j)) {
					emptyPoints.add(new Position(i, j));
				}
			}
		}
		return emptyPoints;
	}
}
