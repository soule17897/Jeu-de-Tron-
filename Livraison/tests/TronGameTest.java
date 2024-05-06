package game.tron.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import game.tron.model.AbstractPlayer;
import game.tron.model.Grille;
import game.tron.model.Move;
import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.RandomPlayer;
import game.tron.model.TronGame;

public class TronGameTest {

	   private Grille grille;
	    private List<Player> players = new ArrayList<Player>();
	    private TronGame game;
		Random random =new Random();	
		private AbstractPlayer player1 = new RandomPlayer("Player1", "X", "O",random);
		private AbstractPlayer player2 = new RandomPlayer("Player2", "X", "O",random);
		
		@Test
	    public void testConstructeur() {
	        // Ajouter les joueurs à la liste des joueurs
	        players.add(player1);
	        players.add(player2);

	        // Initialiser la grille avec une taille spécifique
	        grille = new Grille(5);

	        // Créer le jeu avec la grille et les joueurs
	        game = new TronGame(grille, players);

	        // Assurez-vous que la grille et la liste des joueurs sont correctement initialisées
	        assertNotNull("la grille doit être initialisée",game.getGrille());
	        assertEquals("il doit y avoir deux joueurs sur la liste",2, game.getPlayers().size());
	    }
		
		 @Test
		    public void testGetGrille() {
			 	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);

		        assertEquals("la grille doit être celle passée en paramètre",grille, game.getGrille());
		    }
		 
		 @Test
		    public void testGetPlayers() {
			 	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        assertEquals("la liste des joueurs doit être celle passée en paramètre",players, game.getPlayers());
		    }
		 
		 @Test
		    public void testGetPlayersAlive() {
				players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        assertEquals("tous les joueurs doivent être en vie au début",players, game.getPlayersAlive());
		    }
		 
		 @Test
		    public void testSetPlayersAlive() {
			 	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        List<Player> updatedPlayersAlive = new ArrayList<>();
		        
		        updatedPlayersAlive.add(players.get(0));
		        game.setPlayersAlive(updatedPlayersAlive);
		        assertEquals(updatedPlayersAlive, game.getPlayersAlive());
		    }
		 @Test
		    public void testSetPlayerLastPosition() {
			 	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        Player player = players.get(0);
		        Position position = new Position(2, 3);
		        game.setPlayerLastPosition(player, position);
		        assertEquals(position, game.getPlayerLastPosition(player));
		    }
		 
		    @Test
		    public void testSetPlayerAllPositions() {
		    	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        
		        Player player = players.get(0);
		        List<Position> positions = Arrays.asList(new Position(1, 1), new Position(1, 2), new Position(1, 3));
		        game.setPlayerAllPositions(player, positions);
		        assertEquals(positions, game.getPlayerPositions().get(player));
		    }
		    
		    @Test
		    public void testFindMovePosition() {
		    	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        Player player = players.get(0);
		        game.setPlayerLastPosition(player, new Position(3, 3));
		        assertEquals(new Position(2, 3), game.findMovePosition(Move.NORTH, player));
		    }
		    
		    @Test
		    public void testValidMoves() {
		    	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        Player player = players.get(0);
		        game.setPlayerLastPosition(player, new Position(3, 3));
		        assertEquals(Arrays.asList(Move.NORTH, Move.SOUTH,Move.EAST, Move.WEST), game.validMoves(player));
		    }
		    
		    @Test
		    public void testIsOver() {
		    	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        assertFalse(game.isOver());
		        List<Player> playersAlive = new ArrayList<>();
		        playersAlive.add(players.get(0));
		        game.setPlayersAlive(playersAlive);
		        assertTrue(game.isOver());
		    }
		    
		    @Test
		    public void testGetWinner() {
		    	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        assertNull(game.getWinner());
		        List<Player> playersAlive = new ArrayList<>();
		        playersAlive.add(players.get(0));
		        game.setPlayersAlive(playersAlive);
		        assertEquals(players.get(0), game.getWinner());
		    }
		    
		    @Test
		    public void testSetMoveOfPlayer() {
		        players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        Player player = players.get(0);
		        Map<Player, List<Move>> moveOfPlayer = new HashMap<>();
		        moveOfPlayer.put(player, Arrays.asList(Move.NORTH, Move.EAST, Move.SOUTH));
		        game.setMoveOfPlayer(moveOfPlayer);
		        
		        // Récupérer les mouvements du joueur
		        List<Move> actualMoves = game.getPlayerLastMoves(player);

		        // Comparer les mouvements avec ceux attendus
		        assertEquals(Arrays.asList(Move.NORTH, Move.EAST, Move.SOUTH), actualMoves);
		    }
		    
		    @Test
		    public void testCopy() {
		    	players.add(player1);
		        players.add(player2);

		        // Initialiser la grille avec une taille spécifique
		        grille = new Grille(5);

		        // Créer le jeu avec la grille et les joueurs
		        game = new TronGame(grille, players);
		        TronGame copyGame = game.copy();
		        assertNotSame(game, copyGame);
		        assertEquals(game.getGrille().getTaille(), copyGame.getGrille().getTaille());
		        assertEquals(game.getPlayers().size(), copyGame.getPlayers().size());
		        assertEquals(game.getPlayersAlive().size(), copyGame.getPlayersAlive().size());
		        assertEquals(game.getPlayerPositions(), copyGame.getPlayerPositions());
		    }
		    
		        
		    
		
    

}
