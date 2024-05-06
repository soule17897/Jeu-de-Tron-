package game.tron.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ModuleLayer.Controller;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import game.tron.Heuristique.Heuristic2;
import game.tron.Heuristique.ParanoidScoringHeuristic;
import game.tron.controller.GameController;
import game.tron.model.PlayerFactory;
import game.tron.model.Position;
import game.tron.model.PlayerFactory.PlayerType;

public class GameSwing2 extends JFrame  implements GameViewable,ActionListener{

	
	private SwingTwo pan;
    private GameController controller;
    private int TAILLE;
    private JMenuBar menu;
	private JMenu quit;
	private JMenu restart;
	private JMenuItem paranoidPlayer = new JMenuItem("Maxn");
	private JMenuItem MaxnPlayer = new JMenuItem("Paranoid");
	private JButton run;
	private JMenu addPlayers;
	private JTextField textField = new JTextField();
	
	public GameSwing2(int taille) {
        
		this.TAILLE = taille;
		this.pan = new SwingTwo(taille);		
		this.setTitle("JEU DE TRON MULTI-PLAYERS  ");
		this.setSize(900,900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		this.menu = new JMenuBar();
		this.quit = new JMenu("quit");
		this.run = new JButton("run");
		this.run.addActionListener(this);
		this.paranoidPlayer.addActionListener(this);
		this.MaxnPlayer.addActionListener(this);

		this.restart = new JMenu("restart");
		this.addPlayers = new JMenu("New Player");
		this.addPlayers.add(paranoidPlayer);
		this.addPlayers.add(MaxnPlayer);
	
		this.quit.addMenuListener(new MenuListener() { 
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				
				controller.exitGame();

			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub

			}
		});

		this.restart.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				controller.restartGame();
			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub

			}
		});

		this.menu.add(quit);
		this.menu.add(restart);
		this.menu.add(run);
		this.menu.add(addPlayers);

		this.setJMenuBar(menu);
		this.setContentPane(pan);
		this.setVisible(true);		
	}

	@Override
	public void setController(GameController controller) {
		this.controller = controller;
		this.pan.setController(controller);
	}

	@Override
	public void showWinner(String name) {
		JOptionPane.showMessageDialog(null, " le joueur  "+ name  , " a gagné ", JOptionPane.INFORMATION_MESSAGE);
		//dispose();
	}
    

	@Override
	public void updateGameState(Object o) {
		this.pan.repaint();
	}

	@Override
	public void setGridDimensionPrompt() {
		// TODO Auto-generated method stub
		//this.dispose();
		String res  = JOptionPane.showInputDialog(this, "Donner la taille de la grille : ");
		int taille  = Integer.parseInt(res);
		this.controller.setGrid(taille);
		System.out.println("la taille "+taille);
		this.pan.setUNISIZE(taille);
	}

	@Override
	public void addPlayerPrompt() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(run)) {
			//this.grille = new Grille(this.controller.getTailleGrille()); 
			this.controller.play();
		}
		else if(e.getSource().equals(MaxnPlayer)) {
			String res  = JOptionPane.showInputDialog(this, "Donner la  pronfondeur : ");
			int depth = Integer.parseInt(res);
			controller.addPlayer(PlayerFactory.makePlayer(PlayerType.Maxn, depth, new Heuristic2()));
		}
		else if(e.getSource().equals(paranoidPlayer)) {
			String res  = JOptionPane.showInputDialog(this, "Donner la  pronfondeur ");
			int depth = Integer.parseInt(res);
			controller.addPlayer(PlayerFactory.makePlayer(PlayerType.Paranoid, depth, new ParanoidScoringHeuristic()));
		}
	}

	

}