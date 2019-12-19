package fr.efrei.project.main;

import fr.efrei.project.map.Map;
import fr.efrei.project.player.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

    private Map map;
    private Player[] player;
    private long timerGame;

    public Game(int nbPlayer, int size)
    {
        this.map = new Map(size);
        this.player = Player.createMultiplePlayer(nbPlayer);
        this.timerGame = System.currentTimeMillis() - 10000000;
    }

    public Player[] getListPlayer() {
        return player;
    }

    public void initGame() {

        System.out.println("La partie commence sur la carte de " + '\'' + map.getName() + '\'');
        map.initializeEarlyMap(player);
    }

    public String gameTime()
    {
        return String.valueOf(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - timerGame));
    }

    @Override
    public String toString() {
        return "Jeu, durée : " + gameTime() + " minute(s)\n" +
                map.toString() +
                "Liste des joueurs: {" + Arrays.toString(player) + "}";
    }
}
