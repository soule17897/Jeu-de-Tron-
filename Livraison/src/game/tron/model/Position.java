package game.tron.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Une classe representant une position 
 * 
 */
public class Position {
	
	/**
	 * l'abscisse de la position 
	 */
	private int x;
	/**
	 * l'ordonné de la position 
	 */
	private int y;
	
	
	/**
	 * Crée une nouvelle instance de Position 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public Move getMove(Position pos) {
		if(pos.getX() == this.x && pos.getY() == this.y+1) {
			return Move.EAST;
		}
		else if(pos.getX() == this.x && pos.getY() == this.y-1) {
			return Move.WEST;
		}
		else if(pos.getX() == this.x+1 && pos.getY() == this.y) {
			return Move.SOUTH;
		}
		else if(pos.getX() == this.x-1 && pos.getY() == this.y) {
			return Move.NORTH;
		}
		return null;

	}

	/**

	 * permet de retourner tous les voisins d'une position

	 * @return une ArrayList de Position

	 */

	public List<Position> getNeighbors() {
		List<Position> neighbors = new ArrayList<>();
		neighbors.add(new Position(x + 1, y)); // Droite
		neighbors.add(new Position(x - 1, y)); // Gauche
		neighbors.add(new Position(x, y + 1)); // Bas
		neighbors.add(new Position(x, y - 1)); // Haut
		return neighbors;
	}

	@Override
	public String toString(){
		return "("+this.x+","+this.y+")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
