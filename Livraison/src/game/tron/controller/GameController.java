package game.tron.controller;

import game.tron.model.Grille;
import game.tron.model.Move;
import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.TronGame;
import game.tron.observer.ModelListener;
import game.tron.view.GameSwingView;
import game.tron.view.GameViewable;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.SwingUtilities;

public class GameController implements ModelListener{

	private TronGame game;
	private GameViewable view;
	private Map<Player, Color> playersColor;
    private Thread t;
    
	public GameController(TronGame game, GameViewable view) {
		this.game = game;
		this.view = view;
		this.game.addModelListener(this);
		this.view.setController(this);
		this.playersColor = new HashMap<Player, Color>();
	}


	public void play() {

		startGame();
		
		t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean gameIsOver = false;

				while (!gameIsOver) {

					Map<Player, Move> moves = new HashMap<>();

					for (Player player : game.getPlayersAlive()) {
						Move move = player.chooseMove(game);
						moves.put(player, move);
					}

					if (!moves.isEmpty()) {
						game.execute(moves);
					}

					if (game.isOver()) {
						gameIsOver = true;
						if (game.getWinner() != null) {
							view.showWinner(game.getWinner().toString());
						} else {
							view.showWinner("aucun gagnant");
						}
					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(" game state : "+ game.getPlayersAlive().size());
				System.out.println(" la state of the game  "+ game.getPlayersAlive());

			}
		});
		   
		  t.start();
		}


	public void startGame() {
		//System.out.println(game.getPlayerPositions());
		for (Player p : game.getPlayers()) {
			Random r =  new Random();
			playersColor.put(p,new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
			game.getGrille().setCase(game.getPlayerLastPosition(p).getX(), game.getPlayerLastPosition(p).getY(), true);
		}
	}


	public boolean[][] getGameState() {
		return game.getGrille().getGrille();
	}


	public ArrayList<Position> playersLastPosition(){
		ArrayList<Position> positions = new ArrayList<Position>();
		for (Player p : game.getPlayers()) {
			positions.add(game.getPlayerLastPosition(p));
		}
		return positions;
	}

	public  Map<Color,List<Position>> playersOccupedPosition(){
		Map<Color,List<Position>> listesColors = new HashMap<Color, List<Position>>();
		for (Map.Entry<Player,List<Position>> set :  game.getPlayerPositions().entrySet()) {
			listesColors.put(playersColor.get(set.getKey()),set.getValue());
		}
		return  listesColors; 
	}

	public Map<Player,String> getPlayersColor(){
		return this.getPlayersColor();
	}

	@Override
	public void somethingHasChanged(Object source) {
        
		
		view.updateGameState(source);	

	}

	public void exitGame() {
		System.exit(0);
	}

	public void restartGame() {

		this.view.setGridDimensionPrompt();
		this.game.setPlayers(new ArrayList<Player>());
		this.game.setPlayersAlive(new ArrayList<Player>());
		this.game.setMoveOfPlayer(new HashMap<>());
		this.game.setPlayerPositions(new HashMap<>());
		this.view = new GameSwingView(getTailleGrille());
		this.view.setController(this);
		this.playersColor = new HashMap<Player, Color>();

		System.out.println(this.game.toString());
	}

	public void setGrid(int taille) {
		Grille grille = new Grille(taille);
		this.game.setGrille(grille);
	}

	public void addPlayer(Player p ) {
		this.game.getPlayers().add(p);
		this.game.getPlayersAlive().add(p);
		for(Player ply : game.getPlayers()) {
			System.out.println(ply.toString());
		}
		Random rand = new Random(System.currentTimeMillis());
		int x  = rand.nextInt(getTailleGrille());
		int y  = rand.nextInt(getTailleGrille());
		game.getGrille().setCase(x,y, true);
		this.game.initPlayerPosition(p);
		game.setPlayerLastPosition(p, new Position(x, y));

	}

	public int getTailleGrille() {
		return this.game.getGrille().getTaille();
	}

}