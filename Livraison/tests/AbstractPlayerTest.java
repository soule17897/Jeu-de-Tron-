package game.tron.tests;
/***
 * cette classe représente la classe de test qui permet de tester toutes les methodes de la classe AbstractPlayer
 */

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import game.tron.model.AbstractPlayer;
import game.tron.model.RandomPlayer;

public class AbstractPlayerTest {
	//declaration de variable 
	Random random =new Random();	
	private AbstractPlayer player = new RandomPlayer("Player1", "X", "O",random);
	@Test
    public void testGetName() {
        assertEquals("le nom du joueur doit être player1","Player1", player.getName());
    }

    @Test
    public void testGetSymbol() {
        assertEquals("le symbole du joueur doit être X","X", player.getSymbol());
    }

    @Test
    public void testGetLastSymbol() {
        assertEquals("le lastSymbole doit être 0","O", player.getLastSymbol());
    }
    
    @Test
    public void testEqualsSameInstance() {
        assertTrue("l'instance doit être la même",player.equals(player));
    }
    
    @Test
    public void testEqualsDifferentClass() {
        assertFalse("la classe n'est pas la même",player.equals("Player"));
    }

    @Test
    public void testEqualsDifferentName() {
        assertFalse("les deux players doivent être différent",player.equals(new RandomPlayer("Player2", "X", "O",random)));
    }

    @Test
    public void testEqualsSameName() {
        assertTrue("les deux dooivent avoir le même nom",player.equals(new RandomPlayer("Player1", "X", "O",random)));
    }




}
