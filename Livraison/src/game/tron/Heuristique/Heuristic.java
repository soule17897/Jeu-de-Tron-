package game.tron.Heuristique;

import java.util.Map;

import game.tron.model.Player;
import game.tron.model.TronGame;

public interface Heuristic {

    Map<Player, Integer> evaluate(TronGame game);

    Integer estimate(TronGame game, Player player);

    void setPlayer(Player p);
}
