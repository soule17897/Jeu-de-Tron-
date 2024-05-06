package game.tron.model;

public interface Player {

	Move chooseMove(TronGame game);

	String getName();

	String getSymbol();

	String getLastSymbol();

	int getNombreDeNoeud();

    Move getLastMove(TronGame game);


}
