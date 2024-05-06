package game.tron.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.ModuleLayer.Controller;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.tron.controller.GameController;
import game.tron.model.Position;
import game.tron.model.TronGame;
import game.tron.observer.ModelListener;

public class SwingTwo extends  JPanel  {
	
    private int WIDTH  =  600;
    private int HEIGHT  =  600;
    private int UNITSIZE;
    
    //private int nombreInits = (WIDTH * HEIGHT) / UNITSIZE; 
    private GameController controller;
    
	public SwingTwo(int taille) {
		this.UNITSIZE = WIDTH /taille;
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	
	public int getUNITSIZE() {
		return UNITSIZE;
	}
	
	public void setUNISIZE(int taille) {
		this.UNITSIZE =taille;
		this.repaint();
	}
	
	public void draw(Graphics g ) {
		
		for(int i =0; i< HEIGHT/UNITSIZE; i++) {
			g.drawLine(i*UNITSIZE, 0 , i*UNITSIZE ,HEIGHT);
		}
		
		for(int i =0; i< WIDTH/UNITSIZE; i++) {
			g.drawLine(0,i*UNITSIZE,WIDTH ,i*UNITSIZE);
		}
		
		
		for (Map.Entry<Color,List<Position> > set :  controller.playersOccupedPosition().entrySet()) {
			for(Position  pos : set.getValue()) {
				g.fillOval(pos.getX() * UNITSIZE, pos.getY()* UNITSIZE, UNITSIZE, UNITSIZE);
				g.setColor(Color.GREEN);
			}
		}
		
		for(Position p : controller.playersLastPosition()) {
			//System.out.println(controller.playersLastPosition().size());
			System.out.println(p.toString());
			g.fillOval(p.getX() * UNITSIZE, p.getY()* UNITSIZE, UNITSIZE, UNITSIZE);
			g.setColor(Color.red);
		}
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}
		
}
