package game.tron.Heuristique;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import game.tron.model.Grille;
import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.TronGame;

public class ParanoidScoringHeuristic  implements Heuristic{

	private Player player;

	public ParanoidScoringHeuristic() {

	}

	/**
	 * une methode qui calcule le nombre de position atteignable a partir d'une position 
	 */

	@Override
	public Integer estimate(TronGame game, Player player) {

		TronGame gameCopy = game.copy();
		Grille grille =    gameCopy.getGrille();
		Position startPosition = game.getPlayerLastPosition(player);
		LinkedList<Position> queue = new LinkedList<Position>();

		queue.add(startPosition);

		int temps = 0;
		Integer TerritoryControled = 0;

		while(!queue.isEmpty()) {


			Position pos = queue.poll();

			if(grille.estVide(pos.getX()+1, pos.getY())) {
				TerritoryControled++;
				grille.setCase(pos.getX()+1, pos.getY(),true);
				queue.add(new Position(pos.getX()+1, pos.getY()));
			}
			if(grille.estVide(pos.getX()-1, pos.getY())) {
				TerritoryControled++;
				grille.setCase(pos.getX()-1, pos.getY(),true);
				queue.add(new Position(pos.getX()-1, pos.getY()));
			}
			if(grille.estVide(pos.getX(), pos.getY()+1)) {
				TerritoryControled++;
				grille.setCase(pos.getX(), pos.getY()+1,true);
				queue.add(new Position(pos.getX(), pos.getY()+1));
			}
			if(grille.estVide(pos.getX(), pos.getY()-1)) {
				TerritoryControled++;
				grille.setCase(pos.getX(), pos.getY()-1,true);
				queue.add(new Position(pos.getX(), pos.getY()-1));
			}
		}

		return TerritoryControled;
	}

	@Override
	public Map<Player, Integer> evaluate(TronGame game) {

		Map<Player, Integer> scoring = new HashMap<Player, Integer>();

		int nbrAjout = 0;

		for(Player p : game.getPlayers()) {
			if(this.player.equals(p)) {
				nbrAjout++;
				scoring.put(p, estimate(game, p));
			}
			else 
			{
				scoring.put(p, -estimate(game, p));
				nbrAjout++;
			}
		}

		return scoring;
	}


	@Override
	public void setPlayer(Player p) {
		this.player = p;
	}



}