package game.tron.Heuristique;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.tron.model.Player;
import game.tron.model.TronGame;
import game.tron.model.Move;
import game.tron.model.Player;

/**
 * Cette heuristic permet de compter le nombre de mouvements consécutifs qui ne
 * changent pas la direction du serpent.
 * Plus le nombre de mouvements consécutifs est grand, plus le serpent est en
 * danger car il est plus probable qu'il se retrouve dans une situation
 * bloquante.
 */
public class CaseConsecutif implements Heuristic {

    @Override
    public Map<Player, Integer> evaluate(TronGame game) {
        Map<Player, Integer> playerScores = new HashMap<>();

        for (Player player : game.getPlayersAlive()) {
            int consecutiveMoves = 0;
            // on recupere la liste des derniers mouvements du joueur
            List<Move> playerMoves = game.getPlayerLastMoves(player);

            for (Move move : playerMoves) {
                // on teste si le mouvement est le meme que le dernier mouvement du joueur
                if (move == player.getLastMove(game)) {
                    consecutiveMoves++;
                } else {
                    break;
                }
            }
            playerScores.put(player, consecutiveMoves);
        }

        return playerScores;
    }

    @Override
    public Integer estimate(TronGame game, Player player) {
        return null;
    }

    @Override
    public void setPlayer(Player p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPlayer'");
    }

}
