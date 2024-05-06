package game.tron.model;

import java.util.Objects;

public abstract class AbstractPlayer implements Player {

	protected String name;
	protected String symbol;

	protected String lastSymbol;

	public AbstractPlayer(String name, String symbol, String lastSymbol) {
		this.name = name;
		this.symbol = symbol;
		this.lastSymbol = lastSymbol;
	}
    
	@Override
	public String toString() {
		return "Joueur " + this.name;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getLastSymbol() {
		return lastSymbol;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AbstractPlayer)) {
			return false;
		}
		AbstractPlayer other = (AbstractPlayer) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

}
