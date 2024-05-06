package game.tron.tests;
/***
 * cette classe représente la classe de test qui permet de tester toutes les méthodes de la classe Grille
 */

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import game.tron.model.Grille;
import game.tron.model.Position;

public class GrilleTest {
	//creer une grille avec une taillr
   private Grille grille = new Grille(5);

    @Test
    public void testGetTaille() {
        assertEquals(5, grille.getTaille());
    }

    @Test
    public void testGetCase() {
        assertFalse(grille.getCase(0, 0));
    }
    
    @Test
    public void testGetCaseWithPosition() {
        Position position = new Position(0, 0);
        assertFalse(grille.getCase(position));
    }
    
    @Test
    public void testEstVideTrue() {
        assertTrue(grille.estVide(0, 0));
    }

    @Test
    public void testEstVideFalse() {
        grille.setCase(0, 0, true);
        assertFalse(grille.estVide(0, 0));
    }
    
    @Test
    public void testCopy() {
        Grille copiedGrille = grille.copy();
        assertEquals(grille.getTaille(), copiedGrille.getTaille());
        assertArrayEquals(grille.getGrille(), copiedGrille.getGrille());
    }
    
    @Test
    public void testGetFalsePositions() {
        List<Position> falsePositions = grille.getFalsePositions();
        assertEquals(25, falsePositions.size());
    }
    
    @Test
    public void testGetFreePositions() {
        assertEquals(25, grille.getFreePositions());
    }

}
