package game.tron.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import game.tron.Heuristique.DiagramVoronoi;
import game.tron.Heuristique.Heuristic;
import game.tron.Heuristique.Heuristic2;
import game.tron.Heuristique.HeuristiqueParanoid;
import game.tron.Heuristique.ProportionTerrain;
import game.tron.controller.GameController;
import game.tron.model.Grille;
import game.tron.model.MaxND;
import game.tron.model.Paranoid;
import game.tron.model.Player;
import game.tron.model.Position;
import game.tron.model.TronGame;
import game.tron.view.GameViewTerminal;
import game.tron.view.GameViewable;

/**
 * La classe Experimentation est responsable de la réalisation
 * d'expérimentations
 * pour évaluer les performances des joueurs dans le jeu Tron. Elle permet de
 * générer des données d'analyse sur les performances des joueurs en fonction
 * de différents paramètres tels que la taille de la grille,
 * la profondeur de recherche, etc. Les résultats de l'expérimentation sont
 * sauvegardés dans un fichier JSON pour une analyse ultérieure.
 */
public class Experimentation {

    // Méthode pour placer un joueur sur la grille
    public static Position putPlayer(Map<Position, Boolean> positionsChoose, int taille) {
        while (true) {
            Random random = new Random();
            int x = random.nextInt(taille);
            int y = random.nextInt(taille);
            Position position = new Position(x, y);
            if (positionsChoose.get(position) == null) {
                positionsChoose.put(position, true);
                return position;
            }

        }
    }

    public static void main(String[] args) {
        donneesParties();
    }

    public static void donneesParties() {
        // Paramètres de l'expérimentation
        int taille = 17; // Taille de la grille
        int profondeurDeRecherche = 3; // Profondeur de recherche

        int nbDePlayerAdverses = 4; // Nombre de joueurs adverses max (de 1 à 4)
        int nbParties = 8; // Nombre de parties

        // Initialisation des différentes heuristiques
        Heuristic heuristiqueParanoid = new HeuristiqueParanoid();
        Heuristic heuristique = new ProportionTerrain();
        Heuristic heuristique5 = new DiagramVoronoi();

        // Création du joueur principal (Joueur qui réfléchit beaucoup plus)
        Player p3 = new Paranoid("Solo", "C", ">", profondeurDeRecherche,
                heuristiqueParanoid);
        try {
            // Créer un fichier pour la sortie
            File file = new File("analyses.json");
            FileOutputStream fos = new FileOutputStream(file);

            // Rediriger la sortie standard vers le fichier
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            System.out.println("{");
            System.out.println("\"Taille_grille\" : " + taille + ",");
            System.out.println("\"Nombre_de_parties\" : " + nbParties + ",");
            System.out.println("\"Profondeur_de_recherche\" : " + profondeurDeRecherche + ",");
            System.out.println("\"Nom_du_Joueur\" : \"" + p3 + "\",");

            System.out.println("\"Donnees_de_toutes_les_parties\" :[");

            // Boucle pour chaque nombre de joueurs adverses
            for (int k = 1; k <= nbDePlayerAdverses; k++) {
                System.out.println("{");
                System.out.println("\"Nombre_de_joueurs_adverses\" : " + k + ",");
                System.out.println("\"Donnees_de_la_partie\" :[");

                // Boucle pour chaque partie jusqu'à nombre de partie
                for (int j = 0; j < nbParties; j++) {
                    Grille grille = new Grille(taille);
                    ArrayList<Player> gamePlayers = new ArrayList<>();

                    Map<Position, Boolean> allPositions = new HashMap<>();

                    // Ajout du joueur principal
                    gamePlayers.add(p3);

                    // Ajout des joueurs adverses
                    for (int i = 0; i < k; i++) {
                        Player p = new Paranoid("Adversaire-" + i, "E" + i, ">", 1, heuristiqueParanoid);
                        gamePlayers.add(p);
                    }

                    // Initialisation du jeu
                    TronGame game = new TronGame(grille, gamePlayers);

                    // Placement initial des joueurs sur la grille aléatoirement
                    for (Player p : gamePlayers) {
                        game.setPlayerLastPosition(p, putPlayer(allPositions, taille));
                    }

                    GameViewable view = new GameViewTerminal();
                    // GameViewable view = new GameSwingView(taille);

                    System.out.println("{");

                    System.out.println("\"Nombre_de_joueurs\" : " + game.getPlayers().size() + ",");

                    // Imprimer quelque chose
                    // GameViewable view = new GameSwingView(taille);
                    GameController controller = new GameController(game, view);

                    long startTime = System.nanoTime();
                    controller.play();
                    long endTime = System.nanoTime();
                    long elapsedTime = endTime - startTime;
                    System.out.println("\"Temps écoulé\" : " + elapsedTime + ",");
                    System.out.println("\"Nombre_de_noeuds_visites\" : " + p3.getNombreDeNoeud());

                    if (j == nbParties - 1) {
                        System.out.println("}]");

                    } else {
                        System.out.println("},");

                    }

                }

                if (k == nbDePlayerAdverses) {
                    System.out.println("}");

                } else {
                    System.out.println("},");

                }

            }

            System.out.println("]}");

            // Fermer le flux de sortie
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void donneesNoeud_Profondeur() {
        // Paramètres de l'expérimentation
        int taille = 10; // Taille de la grille
        int profondeurDeRecherche = 10; // Profondeur de recherche

        // Initialisation des différentes heuristiques
        Heuristic heuristiqueParanoid = new HeuristiqueParanoid();
        Heuristic heuristique = new ProportionTerrain();
        Heuristic heuristique5 = new DiagramVoronoi();
        Heuristic heuristicRobert = new Heuristic2();

        // Création du joueur principal (Joueur qui réfléchit beaucoup plus)

        try {
            // Créer un fichier pour la sortie
            File file = new File("analysesNoeudVisite.json");
            FileOutputStream fos = new FileOutputStream(file);

            // Rediriger la sortie standard vers le fichier
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            System.out.println("{\"Taille_grille\" : " + taille + ",");

            System.out.println("\"Donnees_de_toutes_les_parties\" :[");

            // Boucle pour chaque nombre de joueurs adverses
            for (int k = 1; k <= profondeurDeRecherche; k++) {
                Player p3 = new MaxND("Solo", "C", ">", k,
                        heuristicRobert);

                System.out.println("{");
                System.out.println("\"Nom_du_Joueur\" : \"" + p3 + "\",");
                System.out.println("\"Profondeur\" : " + k + ",");

                // Boucle pour chaque partie jusqu'à nombre de partie

                Grille grille = new Grille(taille);
                ArrayList<Player> gamePlayers = new ArrayList<>();

                Map<Position, Boolean> allPositions = new HashMap<>();

                // Ajout du joueur principal
                gamePlayers.add(p3);

                // Ajout des joueurs adverses
                for (int i = 0; i < 3; i++) {
                    Player p = new MaxND("Adversaire-" + i, "E" + i, ">", 1, heuristicRobert);
                    gamePlayers.add(p);
                }

                // Initialisation du jeu
                TronGame game = new TronGame(grille, gamePlayers);

                // Placement initial des joueurs sur la grille aléatoirement
                for (Player p : gamePlayers) {
                    game.setPlayerLastPosition(p, putPlayer(allPositions, taille));
                }

                GameViewable view = new GameViewTerminal();

                // Imprimer quelque chose
                // GameViewable view = new GameSwingView(taille);
                GameController controller = new GameController(game, view);

                long startTime = System.nanoTime();
                controller.play();
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                System.out.println("\"Temps écoulé\" : " + elapsedTime + ",");
                System.out.println("\"Nombre_de_noeuds_visites\" : " + p3.getNombreDeNoeud());

                if (k == profondeurDeRecherche) {
                    System.out.println("}");

                } else {
                    System.out.println("},");

                }

            }

            System.out.println("]}");

            // Fermer le flux de sortie
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}