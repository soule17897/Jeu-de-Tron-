package game.tron.model;

import game.tron.Heuristique.Heuristic;

import java.util.*;

/**
 * Cette classe permet d'implémenter l'algorithme SOS (Socially Oriented Search) pour le jeu Tron.
 * L'algorithme SOS modélise les relations inter-acteurs avec une matrice de gamme sociale et les intègre dans la recherche
 * en pondérant les valeurs de l'heuristique dans les nœuds de l'arbre de recherche pour obtenir l'utilité perçue.
 * L'utilité perçue est la somme des valeurs de l'heuristique pondérées par la matrice de gamme sociale.
 */
public class SOS {

    private double[][] matriceGammeSociale;

    public SOS(double[][] matriceGammeSociale) {
        this.matriceGammeSociale = matriceGammeSociale;
    }

    public Move chooseMove(TronGame game , Heuristic eval , int profondeur, Player currentPlayer) {

        // Obtention de la liste des joueurs encore en vie
        List<Player> players = game.getPlayersAlive();

        // Initialisation de la meilleure évaluation
        double bestEvaluation = Double.NEGATIVE_INFINITY;
        Move bestMove = null;

        // Parcours des mouvements possibles pour le joueur courant
        for (Move move : game.validMoves(currentPlayer)) {
            // Copie de l'état du jeu avec le mouvement appliqué
            TronGame newState = game.copy();
            Map<Player, Move> moves = new HashMap<>();
            moves.put(currentPlayer, move);
            newState.execute(moves);

            // Calcul de l'utilité perçue pour le joueur courant
            Map<Player, Double> perceivedUtilities = computePerceivedUtility(newState, eval, profondeur, players, moves);
            double evaluation = perceivedUtilities.get(currentPlayer);

            // Sélection du meilleur mouvement
            if (evaluation > bestEvaluation) {
                bestEvaluation = evaluation;
                bestMove = move;
            }
        }

        return bestMove;
    }




    /**
     * Méthode qui calcule l'utilité perçue de l'état donné pour chaque joueur en utilisant l'algorithme SOS.
     *
     * @param game          L'état du jeu.
     * @param eval           La fonction d'évaluation.
     * @param profondeur          La profondeur de recherche.
     * @param players        La liste des joueurs.
     * @return Une carte des joueurs à leurs utilités perçues.
     */
    public Map<Player, Double> computePerceivedUtility(TronGame game, Heuristic eval, int profondeur, List<Player> players, Map<Player, Move> moves) {
        Map<Player, Double> perceivedUtilities = new HashMap<>();

        // Vérification de la condition d'arrêt
        if (profondeur == 0 || game.isOver()) {
            for (Player player : game.getPlayersAlive()) {
                perceivedUtilities.put(player, evaluate(game, eval));
            }
            return perceivedUtilities;
        }

        // Initialisation de la meilleure évaluation
        double bestEvaluation = Double.NEGATIVE_INFINITY;

        System.out.println("joeurs sos" + players);
        if (players.isEmpty()) {
            TronGame gameCopy = game.copy();
            gameCopy.execute(moves);
            return computePerceivedUtility(gameCopy, eval, profondeur - 1, gameCopy.getPlayersAlive(), new HashMap<>());
        }

        // Parcours des mouvements possibles pour le premier joueur de la liste
        Player currentPlayer = players.get(0);
        for (Move move : game.validMoves(currentPlayer)) {
            // Copie de l'état du jeu avec le mouvement appliqué
            TronGame newState = game.copy();
            Map<Player, Move> newMoves = new HashMap<>(moves);
            newMoves.put(currentPlayer, move);
            newState.execute(newMoves);

            // Calcul de l'utilité perçue pour le prochain joueur
            Map<Player, Double> nextPerceivedUtilities = computePerceivedUtility(newState, eval, profondeur - 1, players.subList(1, players.size()), newMoves);

            // Sélection de la meilleure évaluation
            double evaluation = nextPerceivedUtilities.get(currentPlayer);
            if (evaluation > bestEvaluation) {
                bestEvaluation = evaluation;
            }
        }

        // Attribution de la meilleure évaluation au joueur courant
        perceivedUtilities.put(currentPlayer, bestEvaluation);

        return perceivedUtilities;
    }



    /**
     * Méthode utilitaire pour obtenir le joueur suivant dans l'ordre de jeu.
     *
     * @param currentPlayer Le joueur actuel.
     * @param numberOfPlayers Le nombre total de joueurs dans le jeu.
     * @return Le joueur suivant.
     */
    private Player getNextPlayer(TronGame state,Player currentPlayer, int numberOfPlayers) {
        List<Player> players = state.getPlayersAlive();
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % numberOfPlayers;
        return players.get(nextIndex);
    }

    /**
     * Méthode pour évaluer un état de jeu en utilisant la fonction d'évaluation.
     *
     * @param state L'état du jeu à évaluer.
     * @param eval  La fonction d'évaluation.
     * @return L'évaluation de l'état de jeu.
     */
    private double evaluate(TronGame state, Heuristic eval) {
        Map<Player, Integer> evaluation = eval.evaluate(state);
        double utility = 0.0;

        for (Map.Entry<Player, Integer> entry : evaluation.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();
            utility += score * matriceGammeSociale[state.getPlayersAlive().size() - 1][state.getPlayersAlive().indexOf(player)];
        }

        return utility;
    }
}
