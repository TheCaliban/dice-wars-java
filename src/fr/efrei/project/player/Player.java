package fr.efrei.project.player;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Player {

    private static int globalId = 0;

    private int id;

    public Player()
    {
        this.id = ++globalId;
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


        Player.turn(this);

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

    @Override
    public String toString() {
        return "(Joueur " + id + ")";
    }
}
