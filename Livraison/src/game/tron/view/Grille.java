package game.tron.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import game.tron.model.Position;

public class Grille  extends JPanel {
   
	
	private  int taille;

	private JPanel grille;

    private Cellule[][] cellules;

    public Grille(int taille) {
        
    	this.cellules = new Cellule[taille][taille];
		setLayout(new GridLayout(taille,taille));
		for (int i = 0; i < taille ; i++) {
			for (int j = 0; j < taille ; j++) {
				this.cellules[i][j] = new Cellule(i, j);
				add(this.cellules[i][j]);
			}
		}
	}

    public Cellule getCase(int x, int y) {
        return this.cellules[x][y];
    }
    
    public int getTaille() {
    	return this.taille;
    }
    public void setTaille(int taille) {
    	this.taille = taille;
    }
}
