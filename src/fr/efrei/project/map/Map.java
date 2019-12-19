package fr.efrei.project.map;

import fr.efrei.project.player.Player;

import java.util.Random;

public class Map
{

    private Case[][] map;
    private String name;

    public Map(int size)
    {
        map = new Case[size][size];
        this.name = MapName.values()[new Random().nextInt(MapName.values().length)].toString();
    }

    public void initializeEarlyMap(Player[] p)
    {
        Random r = new Random();
        int tmp;

        for(int i = 0; i < map.length; i++)
        {
            for(int j = 0; j < map[i].length; j++)
            {
                tmp = r.nextInt(p.length + 1);
                if(tmp > p.length - 1)
                {
                    map[i][j] = new Case();
                }
                else {
                    map[i][j] = new Case(p[tmp]);
                }
            }
        }
        System.out.println("Initialisation finie");
    }

    public Case[][] getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("Carte: " + name + "\n");

        for(Case[] tabCase : this.map)
        {
            tmp.append("[ ");
            for(Case c : tabCase)
            {
                tmp.append("(").append(c).append(") ");
            }
            tmp.append("]\n");
        }
        tmp.append("\n");

        return tmp.toString();
    }
}
