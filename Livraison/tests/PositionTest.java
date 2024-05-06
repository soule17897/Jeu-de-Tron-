/**
 * cette classe représente la classe de test qui permet de tester toutes les méthodes de la classe Position
 */
package game.tron.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import game.tron.model.Move;
import game.tron.model.Position;

public class PositionTest {
	//declaration de variables
	private Position position = new Position(5, 7);
	
	@Test
	public void testConstructeur() {
		Position position = new Position(5,7);
		assertEquals("le nombre de lignes doit etre le meme que position.getX",5, position.getX());
		assertEquals("le nombre de colones doit etre le meme que position.getY",7, position.getY());
		
	}

	 @Test
	    public void testGetX() {
	        assertEquals("le nombre de ligne doit être le meme que position.getX",5, position.getX());
	    }
	 
	  @Test
	    public void testGetY() {
	        assertEquals("le nombre de colones doit être le meme que position.getY",7, position.getY());
	    }
	  
	  @Test
	    public void testGetMoveEast() {
	        Position neighbor = new Position(5, 8);
	        assertEquals("le move doit être east",Move.EAST, position.getMove(neighbor));
	    }
	  
	  @Test
	    public void testGetMoveWest() {
	        Position neighbor = new Position(5, 6);
	        assertEquals(Move.WEST, position.getMove(neighbor));
	    }

	    @Test
	    public void testGetMoveSouth() {
	        Position neighbor = new Position(6, 7);
	        assertEquals(Move.SOUTH, position.getMove(neighbor));
	    }

	    @Test
	    public void testGetMoveNorth() {
	        Position neighbor = new Position(4, 7);
	        assertEquals(Move.NORTH, position.getMove(neighbor));
	    }

	    @Test
	    public void testGetMoveNoDirection() {
	        Position neighbor = new Position(6, 8);
	        assertNull(position.getMove(neighbor));
	    }
	    
	    @Test
	    public void testGetNeighbors() {
	        List<Position> neighbors = new ArrayList<>();
	        neighbors.add(new Position(6, 7)); // Right
	        neighbors.add(new Position(4, 7)); // Left
	        neighbors.add(new Position(5, 8)); // Down
	        neighbors.add(new Position(5, 6)); // Up
	        List<Position> actualNeighbors = position.getNeighbors();
	        assertEquals(neighbors.size(), actualNeighbors.size());
	        for (Position neighbor : neighbors) {
	            assertTrue(actualNeighbors.contains(neighbor));
	        }
	    }

}
