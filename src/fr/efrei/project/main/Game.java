package fr.efrei.project.main;

import fr.efrei.project.map.Case;
import fr.efrei.project.map.Map;
import fr.efrei.project.player.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

    private Map map;
    private Player[] player;
    private long timerGame;
    private HashMap<Player, ArrayList<Case>> listOwnedLand;
    private int numTurn;

    public Game(int nbPlayer, int size)
    {
        this.map = new Map(size);
        this.player = Player.createMultiplePlayer(nbPlayer);
        this.listOwnedLand = new HashMap<>();
        this.numTurn = 0;
    }

    public Player[] getListPlayer() {
        return player;
    }

    public void initGame() {

        this.timerGame = System.currentTimeMillis() - 1000000;
        System.out.println("La partie commence sur la carte de " + '\'' + map.getName() + '\'');
        map.initMap(player, listOwnedLand);
        Player.initPlayer(player, map);
    }

    public String gameTime()
    {
        return String.valueOf(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - timerGame));
    }

    public Map getMap() {
        return map;
    }

    public Player[] getPlayer() {
        return player;
    }

    public void newTurn()
    {
        for(int i = 0; i < player.length; i++)
        {
            player[i].play();
        }
        numTurn++;
    }

    public void endGame()
    {
        /*
         * Fonction d'arrêt du jeu, sérialisation
         */
    }

    @Override
    public String toString() {
        return "Jeu, durée : " + gameTime() + " minute(s)\n" +
                map.toString() +
                "Liste des joueurs: {" + Arrays.toString(player) + "}\n";
    }
}
