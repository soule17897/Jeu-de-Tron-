package game.tron.model;

public abstract class StrategyPlayer{

    private Player strategy;

    public StrategyPlayer(Player strategy){
        this.strategy = strategy;
    }

    public Move chooseMove(TronGame game){
        return strategy.chooseMove(game);
    }

}
