package game.tron.Heuristique;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import game.tron.model.Grille;
import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.TronGame;

public class ProportionTerrain implements Heuristic {

    /*
     * Évalue le nombre de cases couvertes par chaque joueur.
     * Une case est considérée comme couverte si le joueur est le plus proche de cette case.
     * @param game le jeu Tron
     * @return une Map associant chaque joueur au nombre de cases qu'il couvre
     */
    @Override

    public Map<Player, Integer> evaluate(TronGame game) {
        // Initialisation de la carte du nombre de cases occupées par chaque joueur
        Map<Player, Integer> nbreCaseOcup = new HashMap<>();
        // Initialiser le nombre de cases occupées à 0 pour chaque joueur
        for(Player player : game.getPlayers()){
            nbreCaseOcup.put(player, 0);
        }
        // Calcul de la distance entre chaque joueur et chaque case sur la grille
        Map<Player, Map<Position, Double>> distancePlayerAllCases = new HashMap<>();
        for(Player player : game.getPlayers()){
            distancePlayerAllCases.put(player, this.distancePlayerAllCase(player, game.getGrille(),game));
        }
        // Parcours de toutes les positions libres sur la grille
        for(Position pos : game.getGrille().getFalsePositions()){
            // Initialisation de la distance minimale à une valeur maximale

            Double minDistance = Double.MAX_VALUE;

            // Joueur le plus proche de la position

            Player plusProchePlayer = null;

            // Parcours de chaque joueur et de sa distance par rapport à la position

            for(Map.Entry<Player, Map<Position, Double>> entry : distancePlayerAllCases.entrySet()){

                Double distance;

                // Si la distance est nulle, la mettre à la valeur maximale

                if(entry.getValue().get(pos) == null){

                    distance = Double.MAX_VALUE;

                } else {

                    distance = entry.getValue().get(pos);

                }

                // Vérifier si le joueur est plus proche de la case  que le précédent

                if(distance < minDistance){

                    minDistance = distance;

                    plusProchePlayer = entry.getKey();

                } else if(minDistance == distance && distance != Double.MAX_VALUE){

                    plusProchePlayer = null;
                }
                // Si le joueur le plus proche est identifié, incrémenter le nombre de cases occupées par ce joueur
                if(plusProchePlayer != null){
                    nbreCaseOcup.put(plusProchePlayer, nbreCaseOcup.get(plusProchePlayer) + 1);
                }
            }
        }
        return nbreCaseOcup;
    }

    /*
     * Calcule la distance entre le joueur et toutes les cases accessibles dans la grille.
     * @param player le joueur
     * @param grille la grille de jeu
     * @return une map associant chaque position à sa distance par rapport au joueur
     */
    protected Map<Position, Double> distancePlayerAllCase(Player player, Grille grille, TronGame game) {
        Map<Position, Double> distances = new HashMap<>();

        //initialiser la distance de chaque case a l'infini

        Position posInitial = game.getPlayerLastPosition(player);

        distances.put(posInitial, 0.0);

        // Définir la classe interne Comparator

        class PositionComparator implements Comparator<Position> {
            @Override
            public int compare(Position o1, Position o2) {
                double distance1 = distances.getOrDefault(o1, Double.MAX_VALUE);
                double distance2 = distances.getOrDefault(o2, Double.MAX_VALUE);
                return Double.compare(distance1, distance2);
            }

        }
        // Initialiser la file de priorité avec le Comparator
        PriorityQueue<Position> ouvert = new PriorityQueue<>(new PositionComparator());
        ouvert.add(posInitial);
        while (!ouvert.isEmpty()) {
            Position actual = ouvert.poll();
            for (Position voisin : actual.getNeighbors()) {
                if (grille.estDansGrille(voisin.getX(), voisin.getY()) && grille.estVide(voisin.getX(), voisin.getY())) {
                    distances.putIfAbsent(voisin, Double.MAX_VALUE);
                    if (distances.get(voisin) > distances.get(actual) + 1) {
                        distances.put(voisin, distances.get(actual) + 1);
                        ouvert.add(voisin);
                    }
                }
            }
        }
        return distances;
    }
   

    @Override
    public Integer estimate(TronGame game, Player player) {
        return evaluate(game).get(player);
    }

    @Override
    public void setPlayer(Player p) {

    }


}