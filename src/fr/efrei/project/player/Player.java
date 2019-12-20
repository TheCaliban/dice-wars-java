package fr.efrei.project.player;

import fr.efrei.project.map.Case;
import fr.efrei.project.map.Map;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class Player {

    private static int globalId = 0;

    private int id;
    private HashSet<Case> listLand;

    public Player()
    {
        this.id = ++globalId;
        this.listLand = new HashSet<>();
    }

    public static Player[] createMultiplePlayer(int nbPlayer)
    {
        Player[] tmp = new Player[nbPlayer];

        for(int i = 0; i <= nbPlayer - 1; i++)
        {
            tmp[i] = new Player();
        }

        return tmp;
    }


    public void play()
    {
        boolean end;
        Scanner sc = new Scanner(System.in);
        do {

            end = false;
            System.out.println("Voulez vous continuer à jouer ? (y/n)");
            String choice = sc.nextLine();
            switch(choice)
            {
                case "y":
                    Player.turn(this);
                    break;
                case "n":
                    end = true;
                    break;
                default:
                    System.out.println("Entrée non prise en charge");
                    break;
            }

        } while(!end);

    }

    public static void initPlayer(Player[] player, Map map)
    {

    }

    private static void turn(Player player)
    {
        /*
         * Concentre les actions concernant le tour du joueur
         */
        System.out.println("Votre tour commence");

        Scanner sc = new Scanner(System.in);
        boolean end;

        do {
            String choice = sc.nextLine();
            end = true;

            switch (choice)
            {
                case "q":
                    System.out.println("Tour terminé");
                    break;
                default:
                    end = false;
                    System.out.println("Faites un choix");
                    break;
            }

        } while(!end);
    }

    public void attack(int attacker, int challenger)
    {

    }
    public void conqueerNewLand(Case c)
    {
        listLand.add(c);
    }

    private static void loseLand(Case c)
    {

    }

    @Override
    public String toString() {
        return "(Joueur " + id + ")";
    }
}
