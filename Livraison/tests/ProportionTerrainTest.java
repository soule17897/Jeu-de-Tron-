package game.tron.tests;
/***
 * cette classe représente la classe test qui permet de tester toutes les méthodes de la classe ProportionTerrain  
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import game.tron.Heuristique.ProportionTerrain;
import game.tron.model.Grille;
import game.tron.model.TronGame;
import game.tron.model.TronGame;
import game.tron.model.*;


public class ProportionTerrainTest {
    // Initialisation de l'heuristique
    ProportionTerrain heuristic  = new ProportionTerrain();
    private Grille grille;
    private TronGame game;
	Random random =new Random();	
	 private List<Player> players = new ArrayList<Player>();
	 private AbstractPlayer player1 = new RandomPlayer("Player1", "X", "O",random);
	private AbstractPlayer player2 = new RandomPlayer("Player2", "X", "O",random);
	@Test
	public void testEvaluate() {
	    players.add(player1);
	    players.add(player2);

	    // Initialiser la grille avec une taille spécifique
	    grille = new Grille(5);

	    // Créer le jeu avec la grille et les joueurs
	    game = new TronGame(grille, players);

	    // Simuler un état de jeu avec une position pour chaque joueur
	    game.setPlayerLastPosition(player1, new Position(0, 0));
	    game.setPlayerLastPosition(player2, new Position(4, 4));

	    // Exécution de l'heuristique
	    Map<Player, Integer> result = heuristic.evaluate(game);

	    // Vérification du résultat
	    assertNotNull(result);
	    assertEquals(2, result.size()); // Vérifie si le nombre de joueurs est correct

	    for (Player player : game.getPlayers()) {
	        assertNotNull(result.get(player)); // Vérifie si chaque joueur a une valeur associée
	    }
	}
	
	@Test
	public void testEstimate() {
		players.add(player1);
	    players.add(player2);

	    // Initialiser la grille avec une taille spécifique
	    grille = new Grille(5);

	    // Créer le jeu avec la grille et les joueurs
	    game = new TronGame(grille, players);
	    Player player1 = game.getPlayers().get(0); 
	    // Simuler un état de jeu avec une position pour chaque joueur
	    game.setPlayerLastPosition(player1, new Position(0, 0));
	    game.setPlayerLastPosition(player2, new Position(4, 4));


	    // Exécution de l'estimation pour le joueur choisi
	    Integer estimationPlayer1 = heuristic.estimate(game, player1);

	    // Vérification de l'estimation
	    assertNotNull(estimationPlayer1);
	   
	}

}

   
	
    		


