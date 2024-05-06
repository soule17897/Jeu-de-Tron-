package game.tron.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Cellule extends JPanel {
	
	private int x;

	private int y;
	

	public Cellule(int x , int y) {
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//System.out.println(x + " " + y);
		this.setBackground(Color.BLACK);
	}
    
	public void setColor(Color color) {
		this.setBackground(color);
	}
}
