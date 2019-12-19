package fr.efrei.project.main;

import fr.efrei.project.map.Map;
import fr.efrei.project.player.Player;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Game {

    private Map map;
    private Player[] player;

    public Game(int nbPlayer, int size)
    {
        this.map = new Map(size);
        this.player = Player.createMultiplePlayer(nbPlayer);
    }

    public Player[] getListPlayer() {
        return player;
    }

    public void initGame() {

        System.out.println("La partie commence sur la carte de " + '\'' + map.getName() + '\'');

    }

}
