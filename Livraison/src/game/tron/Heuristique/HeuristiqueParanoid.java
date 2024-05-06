package game.tron.Heuristique;

import java.util.HashMap;
import java.util.Map;

import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.TronGame;

public class HeuristiqueParanoid extends ProportionTerrain implements Heuristic {

    @Override
    // Méthode pour évaluer la répartition des terrains entre les joueurs
    public Map<Player, Integer> evaluate(TronGame game) {

        // Initialisation de la carte du nombre de cases occupées par chaque joueur
        Map<Player, Integer> nbreCaseOcup = new HashMap<>();

        // Initialiser le nombre de cases occupées à 0 pour chaque joueur
        for (Player player : game.getPlayers()) {
            nbreCaseOcup.put(player, 0);
        }

        // Calcul de la distance entre chaque joueur et chaque case sur la grille
        Map<Player, Map<Position, Double>> distancePlayerAllCases = new HashMap<>();
        for (Player player : game.getPlayers()) {
            distancePlayerAllCases.put(player, this.distancePlayerAllCase(player, game.getGrille(), game));
        }

        // Parcours de toutes les positions libres sur la grille
        for (Position pos : game.getGrille().getFalsePositions()) {
            // Initialisation de la distance minimale à une valeur maximale
            Double minDistance = Double.MAX_VALUE;

            // Joueur le plus proche de la position
            Player plusProchePlayer = null;

            // Parcours de chaque joueur et de sa distance par rapport à la position
            for (Map.Entry<Player, Map<Position, Double>> entry : distancePlayerAllCases.entrySet()) {
                Double distance;

                // Si la distance est nulle, la mettre à la valeur maximale
                if (entry.getValue().get(pos) == null) {
                    distance = Double.MAX_VALUE;
                } else {
                    distance = entry.getValue().get(pos);
                }

                // Vérifier si le joueur est plus proche de la case que le précédent
                if (distance < minDistance) {
                    minDistance = distance;
                    plusProchePlayer = entry.getKey();
                } else if (minDistance == distance && distance != Double.MAX_VALUE) {
                    plusProchePlayer = null;
                }

                // Si le joueur le plus proche est identifié, incrémenter le nombre de cases occupées par ce joueur
                if (plusProchePlayer != null) {
                    int poidCase = this.poidsCase(pos, plusProchePlayer, game);
                    nbreCaseOcup.put(plusProchePlayer, nbreCaseOcup.get(plusProchePlayer) + poidCase);
                }
            }
        }

        return nbreCaseOcup;
    }

    // Méthode pour calculer le poids de la case en fonction de sa position et du joueur le plus proche
    private int poidsCase(Position pos, Player player, TronGame game) {
        int distanceSum = 0;

        // Parcourir tous les joueurs sauf le joueur donné
        for (Player otherPlayer : game.getPlayers()) {
            if (otherPlayer != player) {
                // Calculer la distance entre la position et le joueur
                double distance = distanceBetweenPositions(pos, game.getPlayerLastPosition(otherPlayer));
                // Ajouter la distance à la somme des distances
                distanceSum += distance;
            }
        }

        // Soustraire la distance du joueur à la somme des distances des autres joueurs
        double playerDistance = distanceBetweenPositions(pos, game.getPlayerLastPosition(player));
        distanceSum += playerDistance;

        return distanceSum;
    }

    // Méthode pour calculer la distance euclidienne entre deux positions
    private double distanceBetweenPositions(Position pos1, Position pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
    }

}
