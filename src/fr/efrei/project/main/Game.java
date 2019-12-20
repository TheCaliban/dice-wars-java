package fr.efrei.project.main;

import fr.efrei.project.exception.InsufficientDiceException;
import fr.efrei.project.exception.NotEnoughPlayerException;
import fr.efrei.project.map.Case;
import fr.efrei.project.map.Map;
import fr.efrei.project.player.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

    private final int maxTurn;

    private Map map;
    private Player[] player;
    private long timerGame;
    private HashMap<Player, HashSet<Case>> listOwnedLand;
    private int numTurn;
    private int totalDice;

    public Game(int nbPlayer, int size, int totalDice) throws InsufficientDiceException {
        if(totalDice < Math.pow(size, 2))
        {
            throw new InsufficientDiceException();
        }
        this.map = new Map(size);
        this.player = Player.createMultiplePlayer(nbPlayer, (totalDice / nbPlayer));
        this.listOwnedLand = new HashMap<>();
        this.numTurn = 0;
        this.totalDice = totalDice;
        this.maxTurn = 20;
    }

    public Game(int nbPlayer, int size) throws InsufficientDiceException {
        this(nbPlayer, size, new Random().nextInt(20) + (int) Math.pow(size, 2));
    }

    public Player[] getListPlayer() {
        return player;
    }

    public void initGame() {

        this.timerGame = System.currentTimeMillis() - 100000;
        System.out.println("La partie commence sur la carte de " + '\'' + map.getName() + '\'');
        map.initMap(player, listOwnedLand);

        for (Player value : player) {
            value.initPlayer(listOwnedLand.get(value));
        }

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

    public void playGame() throws NotEnoughPlayerException {

        boolean end = true;
        Scanner sc = new Scanner(System.in);

        if(player.length < 1)
        {
            throw new NotEnoughPlayerException();
        }
        while(numTurn < maxTurn && end)
        {
            System.out.println("Tour numéro " + numTurn);
            if(numTurn == 0)
            {
                newTurn();
                numTurn++;
            }
            else
            {
                end = false;
                System.out.println("Voulez vous continuer à jouer ? (y/n)");
                String choice = sc.nextLine();
                switch (choice) {
                    case "y":
                        newTurn();
                        numTurn++;
                        break;
                    case "n":
                        end = false;
                        break;
                    default:
                        System.out.println("Entrée non prise en charge");
                        break;
                }
            }

        }

        endGame();

    }

    private void newTurn() {
        for(Player p : player)
        {
            p.play(map);
        }
    }

    private void endGame()
    {
        /*
         * Fonction d'arrêt du jeu, sérialisation
         */

        System.out.println("Votre partie aura durée " + gameTime() + " minute(s)");

        System.out.println(map.toString());

        for(Player p : player)
        {
            p.getPlayerInfo();
        }
    }

    @Override
    public String toString() {
        return "Jeu, durée : " + gameTime() + " minute(s)\n" +
                map.toString() +
                "Liste des joueurs: {" + Arrays.toString(player) + "}\n";
    }
}
