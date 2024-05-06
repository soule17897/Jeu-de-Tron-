package game.tron.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public interface Game {
    // Renvoie la liste des mouvements valides pour le joueur actuel

    List<Move> validMoves(Player player);

    // Vérifie si le jeu est terminé

    boolean isOver();

    // Exécute le mouvement à la position spécifiée

    void execute(Map<Player, Move> moves);
    // Vérifie si un mouvement à la position spécifiée est valide

    boolean isValid(Move move , Player player);

    // Renvoie le joueur gagnant du jeu (peut être nul si le jeu n'est pas terminé

    // ou s'il y a égalité)

    Player getWinner();

    // Crée une copie du jeu

    TronGame copy();

}
