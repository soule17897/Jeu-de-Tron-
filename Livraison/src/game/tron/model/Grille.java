package game.tron.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grille {

    private boolean[][] grille;
    private int taille;

    /**
     * Constructeur de la classe Grille
     *
     * @param taille la taille de la grille
     */
    public Grille(int taille) {
        this.taille = taille;
        this.grille = new boolean[taille][taille];
    }

    /**
     * Renvoie la taille de la grille
     *
     */
    public int getTaille() {
        return this.taille;
    }

    /**
     * Renvoie la valeur de la case de la grille
     *
     * @param x la position x de la case
     * @param y la position y de la case
     */
    public boolean getCase(int x, int y) {
        return this.grille[x][y];
    }

    /**
     * Renvoie la valeur de la position de la grille
     *
     * @param pos la position de la case
     */
    public boolean getCase(Position pos) {
        return this.grille[pos.getX()][pos.getY()];
    }

    /**
     * Modifie la valeur de la case de la grille
     *
     * @param x   la position x de la case
     * @param y   la position y de la case
     * @param val la valeur de la case
     */
    public void setCase(int x, int y, boolean val) {
        this.grille[x][y] = val;
    }

    /**
     * Verifie si la case est vide
     * une case est vide si elle contient la valeur false
     *
     * @param x la position x de la case
     * @param y la position y de la case
     */
    public boolean estVide(int x, int y) {

        return estDansGrille(x, y) && this.grille[x][y] == false;

    }



    /**
     * Verifie si la case est dans la grille
     * une case est dans la grille si elle est dans les bornes de la grille
     *
     * @param x la position x de la case
     * @param y la position y de la case
     */
    public boolean estDansGrille(int x, int y) {
        return x >= 0 && x < this.taille && y >= 0 && y < this.taille;
    }

    /**
     * Affiche la grille
     */

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille; j++) {
                res += this.grille[i][j] + "_";
            }
            res += "\n";
        }
        return res;
    }

    public boolean[][] getGrille() {
        return grille;
    }

    public void setGrille(boolean[][] grille) {
        this.grille = grille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public Grille copy() {
        Grille copyGrille = new Grille(taille);
        for (int i = 0; i < taille; i++) {
            System.arraycopy(this.grille[i], 0, copyGrille.grille[i], 0, taille);
        }
        return copyGrille;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(grille);
        result = prime * result + taille;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Grille other = (Grille) obj;
        if (!Arrays.deepEquals(grille, other.grille))
            return false;
        if (taille != other.taille)
            return false;
        return true;
    }

    /**

     * Retourne toutes les positions avec la valeur false dans la grille

     *

     * @return une liste de positions avec la valeur false

     */

    public List<Position> getFalsePositions() {
        List<Position> falsePositions = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (!grille[i][j]) {
                    falsePositions.add(new Position(i, j));
                }
            }
        }
        return falsePositions;
    }

    public int getFreePositions() {
        int count = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (!grille[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

}
