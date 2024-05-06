package game.tron.Heuristique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.TronGame;

/**
 * Heuristic2 implémente l'interface Heuristic et fournit une fonction
 * d'évaluation basée sur le nombre de mouvements valides disponibles pour
 * chaque joueur.
 */

public class Heuristic2 implements Heuristic {

    /**
     * Évalue l'état actuel du jeu en fonction du nombre de mouvements valides
     * disponibles pour chaque joueur.
     *
     * @param game L'état actuel du jeu Tron.
     * @return Une Map contenant chaque joueur et son score correspondant basé sur
     *         le nombre de mouvements valides.
     */
    @Override
    public Map<Player, Integer> evaluate(TronGame game) {
        Map<Player, Integer> scores = new HashMap<>();
        for (Player player : game.getPlayers()) {
           
            int numberOfValidMoves = game.validMoves(player).size();



            if (game.isOver() && game.getWinner() != null && !game.getWinner().equals(player)) {
                scores.put(player, -1);

            }
            
            List<Position> posVisited = new ArrayList<>();
            Position pos = game.getPlayerLastPosition(player);
            scores.put(player,
                    comptVolume(game, pos.getX(), pos.getY(), posVisited, 15) - 1);

        }
        return scores;
    }

    public int comptVolume(TronGame game, int x, int y, List<Position> posVisited, int profondeurMax) {


        if ((profondeurMax == 0 || posVisited.contains(new Position(x, y)) || !game.getGrille().estVide(x, y))
                && !posVisited.isEmpty()) {
            return 0;
        }

        posVisited.add(new Position(x, y));
        return 1 + comptVolume(game, x + 1, y, posVisited, profondeurMax - 1)
                + comptVolume(game, x - 1, y, posVisited, profondeurMax - 1)
                + comptVolume(game, x, y + 1, posVisited, profondeurMax - 1)
                + comptVolume(game, x, y - 1, posVisited, profondeurMax - 1);
    }

    @Override
    public Integer estimate(TronGame game, Player player) {
        return 0;
    }

    @Override
    public void setPlayer(Player p) {

    }
}
