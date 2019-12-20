package fr.efrei.project.main;

import fr.efrei.project.exception.InsufficientDiceException;
import fr.efrei.project.exception.NotEnoughPlayerException;
import fr.efrei.project.exception.UnknownCaseInMap;
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

        Game g;

        try {
            g = new Game(nbPlayer, sizeMap);
            g.initGame();
            g.playGame();
//            System.out.println(g);

        } catch (InsufficientDiceException | NotEnoughPlayerException e) {
            e.printStackTrace();
        }

/*
        try {
            for(int i = 1; i < 81; i++)
            System.out.println(g.getMap().getCaseFromId(g.getMap().getListCase(), i));
        } catch (UnknownCaseInMap unknownCaseInMap) {
            unknownCaseInMap.printStackTrace();
        }
*/
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
