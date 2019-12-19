package fr.efrei.project.main;

import fr.efrei.project.player.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        int nbPlayer;
        int sizeMap;

        if(args.length < 2)
        {
            nbPlayer = pickPlayer();
            sizeMap = pickSizeMap();
        }
        else
        {
            nbPlayer = Integer.parseInt(args[0]);
            sizeMap = Integer.parseInt(args[1]);
        }

        Game g = new Game(nbPlayer, sizeMap);
        Player[] listPlayer  = g.getListPlayer();

        for(Player p : listPlayer)
        {
            System.out.println(p.toString());
            p.play();
        }

        g.initGame();
        System.out.println(g);
    }

    private static int pickPlayer()
    {
        Scanner sc = new Scanner(System.in);
        boolean end;
        int nbPlayer = 0;

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

        return nbPlayer;
    }

    private static int pickSizeMap() {

        Scanner sc = new Scanner(System.in);
        boolean end;
        int sizeMap = 0;

        do {
            System.out.println("Renseigner taille map: ");
            try {
                sizeMap = sc.nextInt();
                end = true;
            } catch (InputMismatchException e) {
                System.out.println("Saisie invalide, nombre entier requis");
                end = false;
            }
        } while(!end);

        return sizeMap;

    }
}
