package fr.efrei.project.main;

import fr.efrei.project.player.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int nbPlayer = 0;

        if(args.length < 1)
        {
            Scanner sc = new Scanner(System.in);
            boolean end;
            do {
                System.out.println("Renseigner nombre de joueurs: ");
                try {
                    nbPlayer = sc.nextInt();
                    end = true;
                } catch (InputMismatchException e) {
                    System.out.println("Saisie invalide, nombre entier requis");
                    end = false;
                }
            } while(!end);
        }
        else
        {
            nbPlayer = Integer.parseInt(args[1]);
        }

        Player[] listPlayer  =Player.createMultiplePlayer(nbPlayer);

        for(Player p : listPlayer)
        {
            System.out.println(p.toString());
        }


    }
}
