package game.tron.Heuristique;

import game.tron.model.TronGame;
import game.tron.model.Player;
import game.tron.model.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe représente une heuristique basée sur la proximité des positions vides aux joueurs dans le jeu Tron.
 * L'heuristique évalue la performance des joueurs en comptant le nombre de cases vides autour 
 * d'eux et en les attribuant aux joueurs en fonction de leur proximité.
 * Plus un joueur est proche d'une position vide, plus il obtient de cases vides autour de lui.
 * L'heuristique utilise un calcul de distances pour déterminer la proximité de chaque joueur 
 * à chaque position vide, puis trie ces positions en fonction de leur proximité.
 * Enfin, elle attribue les positions vides aux joueurs en fonction de leur proximité, 
 * en comptant le nombre de cases vides attribuées à chaque joueur.
 * Cette heuristique est particulièrement adaptée pour évaluer les stratégies de jeu basées sur 
 * la domination de l'espace en contrôlant les positions vides.
 */


public class DistanceBasedHeuristic extends ProportionTerrain {

    @Override
    public Map<Player, Integer> evaluate(TronGame game) {
        Map<Player, Integer> nombreCasesOccupees = new HashMap<>();

        // Initialise le comptage des cases occupées pour chaque joueur à 0
        game.getPlayers().forEach(joueur -> nombreCasesOccupees.put(joueur, 0));

        // Calcule les distances de toutes les positions vides par rapport à chaque joueur
        Map<Player, Map<Position, Double>> distanceJoueurToutesLesCases = calculerDistances(game);

        // Trie les positions vides en fonction de leur proximité avec le joueur le plus proche
        List<Position> positionsVidesTriees = trierPositionsVidesParProximité(game, distanceJoueurToutesLesCases);

        // Met à jour le comptage des cases occupées pour chaque joueur en fonction de la proximité
        positionsVidesTriees.forEach(position -> {
            Player joueurLePlusProche = trouverJoueurLePlusProche(position, game.getPlayersAlive(), distanceJoueurToutesLesCases);
            if (joueurLePlusProche != null) {
                nombreCasesOccupees.put(joueurLePlusProche, nombreCasesOccupees.get(joueurLePlusProche) + 1);
            }
        });

        return nombreCasesOccupees;
    }

    /**
     * Calcule les distances de toutes les positions vides par rapport à chaque joueur.
     */
    private Map<Player, Map<Position, Double>> calculerDistances(TronGame game) {
        Map<Player, Map<Position, Double>> distanceJoueurToutesLesCases = new HashMap<>();
        game.getPlayers().forEach(joueur ->
                distanceJoueurToutesLesCases.put(joueur, this.distancePlayerAllCase(joueur, game.getGrille(), game))
        );
        return distanceJoueurToutesLesCases;
    }

    /**
     * Trie les positions vides en fonction de leur proximité avec le joueur le plus proche.
     */
    private List<Position> trierPositionsVidesParProximité(TronGame game, Map<Player, Map<Position, Double>> distanceJoueurToutesLesCases) {
        List<Position> positionsVides = game.getGrille().getFalsePositions();
        positionsVides.sort((p1, p2) -> {
            double distanceMin1 = getMinDistanceAuxJoueurs(p1, game.getPlayersAlive(), distanceJoueurToutesLesCases);
            double distanceMin2 = getMinDistanceAuxJoueurs(p2, game.getPlayersAlive(), distanceJoueurToutesLesCases);
            return Double.compare(distanceMin1, distanceMin2);
        });
        return positionsVides;
    }

    /**
     * Trouve le joueur le plus proche d'une position donnée.
     * 
     */
    private Player trouverJoueurLePlusProche(Position position, List<Player> joueurs, Map<Player, Map<Position, Double>> distanceJoueurToutesLesCases) {
        Player joueurLePlusProche = null;
        double distanceMin = Double.MAX_VALUE;
        for (Player joueur : joueurs) {
            Double distance = distanceJoueurToutesLesCases.get(joueur).get(position);
            if (distance != null && distance < distanceMin) {
                distanceMin = distance;
                joueurLePlusProche = joueur;
            }
        }
        return joueurLePlusProche;
    }

    /**
     * Trouve la distance minimale d'une position à tous les joueurs.
     */
    private double getMinDistanceAuxJoueurs(Position position, List<Player> joueurs, Map<Player, Map<Position, Double>> distanceJoueurToutesLesCases) {
        double distanceMin = Double.MAX_VALUE;
        for (Player joueur : joueurs) {
            Double distance = distanceJoueurToutesLesCases.get(joueur).get(position);
            if (distance != null && distance < distanceMin) {
                distanceMin = distance;
            }
        }
        return distanceMin;
    }
}
