package game.tron.Heuristique;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.TronGame;

/**
 * Cette classe représente une heuristique basée sur le diagramme de Voronoi.
 * Le diagramme de Voronoi est une partition de l'espace en cellules, où chaque
 * cellule est définie comme
 * l'ensemble des positions vides les plus proches d'un joueur.
 * L'heuristique utilise le diagramme de Voronoi pour évaluer la performance des
 * joueurs dans le jeu Tron
 * en fonction de la proximité de leurs positions avec les positions vides.
 * Elle attribue des scores aux joueurs en fonction de la taille de leur cellule
 * de Voronoi et
 * de la distance de cette cellule par rapport au reste de l'espace vide.
 * Plus la cellule d'un joueur est grande et proche du centre de l'espace vide,
 * plus le score du joueur est élevé.
 */

public class DiagramVoronoi extends ProportionTerrain {

    @Override
    public Map<Player, Integer> evaluate(TronGame game) {
        Map<Player, Integer> scores = new HashMap<>();
        Map<Player, Map<Position, Double>> playerDistances = new HashMap<>();
        Map<Player, Set<Position>> voronoiCells = new HashMap<>();

        initializeScores(game, scores);
        calculatePlayerDistances(game, playerDistances);
        initializeVoronoiCells(game, voronoiCells);
        calculateVoronoiDiagram(game, playerDistances, voronoiCells);
        updateScores(game, voronoiCells, scores);

        return scores;
    }

    /**
     * Cette methode permet d'initialiser les scores des joueurs à 0.
     * 
     * @param game
     * @param scores
     */
    private void initializeScores(TronGame game, Map<Player, Integer> scores) {
        game.getPlayers().forEach(player -> scores.put(player, 0));
    }

    /**
     * Cette methode permet de calculer les distances de toutes les positions vides
     * par rapport à chaque joueur.
     * 
     * @param game
     * @param playerDistances
     */
    private void calculatePlayerDistances(TronGame game, Map<Player, Map<Position, Double>> playerDistances) {
        game.getPlayers()
                .forEach(player -> playerDistances.put(player, distancePlayerAllCase(player, game.getGrille(), game)));
    }

    /**
     * Cette methode permet d'initialiser les cellules de Voronoi pour chaque
     * joueur.
     * une cellules de Voronoi est l'ensemble des positions vides les plus proches
     * d'un joueur par rapport à tous les autres joueurs.
     *
     * @param game
     * @param voronoiCells map contenant les cellules de Voronoi pour chaque joueur
     */
    private void initializeVoronoiCells(TronGame game, Map<Player, Set<Position>> voronoiCells) {
        game.getPlayers().forEach(player -> voronoiCells.put(player, new HashSet<>()));
    }

    /**
     * Cette methode permet de calculer le diagramme de Voronoi pour chaque joueur.
     * un diagramme de Voronoi est une partition de l'espace en cellules, où chaque
     * cellule est l'ensemble des positions vides les plus proches d'un joueur.
     * 
     * @param game            l'etat du jeu
     * @param playerDistances map contenant les distances de toutes les positions
     *                        vides par rapport à chaque joueur
     * @param voronoiCells    map contenant les cellules de Voronoi pour chaque
     *                        joueur
     */
    private void calculateVoronoiDiagram(TronGame game, Map<Player, Map<Position, Double>> playerDistances,
            Map<Player, Set<Position>> voronoiCells) {
        // on parcourt toutes les positions vides
        game.getEmptyPoints().forEach(point -> {
            // on récupère le joueur le plus proche de la position point
            Player closestPlayer = getClosestPlayer(game, playerDistances, point);
            // si le joueur le plus proche est trouvé, on ajoute la position point à la
            // cellule de Voronoi du joueur
            if (closestPlayer != null) {
                voronoiCells.get(closestPlayer).add(point);
            }
        });
    }

    /**
     * Cette methode permet de récupérer le joueur le plus proche d'une position
     * donnée.
     * 
     * @param game
     * @param playerDistances map contenant les distances de toutes les positions
     *                        vides par rapport à chaque joueur
     * @param point           la position donnée
     * @return
     */
    private Player getClosestPlayer(TronGame game, Map<Player, Map<Position, Double>> playerDistances, Position point) {
        Player closestPlayer = null;
        double minDistance = Double.MAX_VALUE;

        // on parcours la liste des joeurs en vie
        for (Player player : game.getPlayers()) {
            // si la distance entre le joueur et la position point est calculée
            if (playerDistances.get(player).containsKey(point)) {
                double distance = playerDistances.get(player).get(point);
                // on met à jour le joueur le plus proche et la distance minimale
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPlayer = player;
                }
            }
        }

        return closestPlayer;
    }

    /**
     * Cette methode permet de mettre à jour les scores des joueurs.
     * 
     * @param game
     * @param voronoiCells map contenant les cellules de Voronoi pour chaque joueur
     * @param scores       map contenant les scores des joueurs
     */
    private void updateScores(TronGame game, Map<Player, Set<Position>> voronoiCells, Map<Player, Integer> scores) {
        // on parcours la liste des joeurs en vie
        for (Player player : game.getPlayers()) {
            int diff = Math.abs(voronoiCells.get(player).size() - game.getEmptyPoints().size() / 2);
            scores.put(player, voronoiCells.get(player).size() - diff);
        }
    }
}
