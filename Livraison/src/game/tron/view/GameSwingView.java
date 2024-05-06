package game.tron.view;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import game.tron.Heuristique.Heuristic2;
import game.tron.Heuristique.ParanoidScoringHeuristic;
import game.tron.controller.GameController;
import game.tron.model.PlayerFactory;
import game.tron.model.PlayerFactory.PlayerType;
import game.tron.model.Position;

public class GameSwingView extends JFrame   implements GameViewable {

	private static final long serialVersionUID = 1L;
	private GameController controller;
	private Grille grille;
	private JMenuBar menu;
	private JButton  quit = new JButton("quit");;
	private JButton restart = new JButton("restart");
	private JMenuItem paranoidPlayer = new JMenuItem("Paranoid");
	private JMenuItem MaxnPlayer = new JMenuItem("Maxn");
	private JMenuItem ParanoidPruning  = new JMenuItem("Paranoid with purunig ");
	private JMenuItem MaxnPruning = new JMenuItem("Maxn with Pruning");
	private JButton run = new JButton("run");;
	private JMenu addPlayers;
	

	public GameSwingView(int taille) {

		this.setTitle("JEU DE TRON MULTI-PLAYERS  ");
		this.setSize(900, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.grille = new Grille(taille);
		this.menu = new JMenuBar();

		this.addPlayers = new JMenu("New Player");
		this.addPlayers.add(paranoidPlayer);
		this.addPlayers.add(MaxnPlayer);
        this.addPlayers.add(MaxnPruning);
        this.addPlayers.add(ParanoidPruning);
        
		initButton();
		initMenu();

		this.menu.add(quit);
		this.menu.add(restart);
		this.menu.add(run);
		this.menu.add(addPlayers);

		this.setJMenuBar(menu);
		this.setContentPane(grille);
		this.setVisible(true);		
	}

	@Override
	public void setController(GameController controller) {
		this.controller = controller;
	}


	@Override
	public void showWinner(String name) {
		JOptionPane.showMessageDialog(null, " le joueur  "+ name  , " a gagné ", JOptionPane.INFORMATION_MESSAGE);
		//dispose();
	}


	public void somethingHasChanged(Object source) {

		boolean[][] grid = controller.getGameState();
		for (Map.Entry<Color,List<Position> > set :  controller.playersOccupedPosition().entrySet()) {
			for(Position  pos : set.getValue()) {
				this.grille.getCase(pos.getX(),pos.getY()).setColor(set.getKey());
			}
		}

		for(Position p : controller.playersLastPosition()) {
			this.grille.getCase(p.getX(), p.getY()).setColor(Color.RED);
		}

	}


	@Override
	public void updateGameState(Object o) {
		this.somethingHasChanged(o);
	}



	@Override 
	public void setGridDimensionPrompt() {
		this.dispose();
		String res  = JOptionPane.showInputDialog(this, "Donner la taille de la grille : ");
		if(res != null ) {
			int taille  = Integer.parseInt(res);
			this.controller.setGrid(taille);
			System.out.println("la taille "+taille);	
		}

	}

	@Override
	public void addPlayerPrompt() {

	}

	private void initMenu() {

		this.ParanoidPruning.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String res  = JOptionPane.showInputDialog(null, "Donner la  pronfondeur ");
				if(res != null ) {
					int depth = Integer.parseInt(res);
					controller.addPlayer(PlayerFactory.makePlayer(PlayerType.ParanoidWithPruning, depth, new ParanoidScoringHeuristic()));	
				}

			}
		});

		this.MaxnPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String res  = JOptionPane.showInputDialog(null, "Donner la  pronfondeur : ");
				if(res != null ) {
					int depth = Integer.parseInt(res);
					controller.addPlayer(PlayerFactory.makePlayer(PlayerType.Maxn, depth, new Heuristic2()));	
				}
			}
		}); 
		
		this.MaxnPruning.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String res  = JOptionPane.showInputDialog(null, "Donner la  pronfondeur : ");
				if(res != null ) {
					int depth = Integer.parseInt(res);
					controller.addPlayer(PlayerFactory.makePlayer(PlayerType.MaxnPruning, depth, new Heuristic2()));	
				}
			}
		});
		
		this.paranoidPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String res  = JOptionPane.showInputDialog(null, "Donner la  pronfondeur ");
				if(res != null ) {
					int depth = Integer.parseInt(res);
					controller.addPlayer(PlayerFactory.makePlayer(PlayerType.Paranoid, depth, new Heuristic2()));	
				}

			}
		});
	}

	private  void initButton() {

		this.quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.exitGame();		
			}
		});

		this.restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.restartGame();		
			}
		});
		this.run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.play();
			}
		});
	}


}