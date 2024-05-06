package game.tron.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class playersView  extends JPanel {
    
	private String[] players;
	
	public playersView(String[] players) {
		
		this.players = players;
		this.setPreferredSize(new Dimension(300,300));
		this.setBackground(Color.YELLOW);
		
	}
	
	
	
}
