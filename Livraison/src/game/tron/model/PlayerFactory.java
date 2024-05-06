package game.tron.model;

import game.tron.Heuristique.Heuristic;

public class PlayerFactory {
	
	static  int numberPlayer = 0;
	
	public enum PlayerType {
		Maxn,
		Paranoid,
		MaxnPruning,
		ParanoidWithPruning
	}
	
	public static Player makePlayer(PlayerType type,int maxProfondeur, Heuristic heuristic) {
		
		numberPlayer++;
		if(type == PlayerType.Maxn) {
			 return new MaxND("Joueur_"+numberPlayer+"_MaxN",maxProfondeur,heuristic);
		}
		else if(type == PlayerType.ParanoidWithPruning) {
			return new ParanoidWithPruning("Joueur_"+numberPlayer+"_AlphaBeta_Paranoid",maxProfondeur,heuristic);
		}
		else if(type == PlayerType.Paranoid) {
			return new Paranoid("Joueur_"+numberPlayer+"_Paranoid",maxProfondeur,heuristic);
		}
		else if(type == PlayerType.MaxnPruning) {
			return new MaxNDWithPruning("Joueur_"+numberPlayer+"_Pruning_Maxn",maxProfondeur,heuristic);
		}
		else {
			return new MaxND("Joueur_"+numberPlayer+"_MaxN",maxProfondeur,heuristic);
		}
	}

}
