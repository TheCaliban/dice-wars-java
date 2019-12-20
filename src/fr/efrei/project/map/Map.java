package fr.efrei.project.map;

import fr.efrei.project.exception.UnknownCaseInMap;
import fr.efrei.project.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Map
{

    private Case[][] listCase;
    private String name;
//    private HashMap<Player, ArrayList<Case>> listOwnedLand;

    public Map(int size)
    {
        this.listCase = new Case[size][size];
        this.name = MapName.values()[new Random().nextInt(MapName.values().length)].toString();
//        this.listOwnedLand = new HashMap<>();
    }

    public void initMap(Player[] p, HashMap<Player, ArrayList<Case>> listOwnedLand)
    {
        Random r = new Random();
        int tmp;

        for(int i = 0; i < listCase.length; i++)
        {
            for(int j = 0; j < listCase[i].length; j++)
            {
                tmp = r.nextInt(p.length + 1);
                if(tmp > p.length - 1)
                {
                    listCase[i][j] = new Case();
                }
                else
                {
                    try {
                        listCase[i][j] = new Case(p[tmp]);
                        ArrayList<Case> tmpList = new ArrayList<>();

                        if(listOwnedLand.containsKey(p[tmp]))
                        {
                            tmpList = listOwnedLand.get(p[tmp]);
                        }
                        tmpList.add(getCaseFromIndex(this, i, j));
                        listOwnedLand.put(p[tmp], tmpList);

                    } catch (UnknownCaseInMap unknownCaseInMap) {
                        unknownCaseInMap.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Initialisation finie");
    }

    public static Case getCaseFromIndex(Map map, int x, int y) throws UnknownCaseInMap {
        if(x < map.getListCase().length && y < map.getListCase()[0].length) {
            return map.getListCase()[x][y];
        } else {
            throw new UnknownCaseInMap();
        }
    }

    public static Case getCaseFromId(Case[][] map, int id) throws UnknownCaseInMap {
        if(id > 0 && id < Math.pow(map.length, 2)) {
            int indexX;
            int indexY;

            indexX = id / (map.length);
            indexY = (id % (map.length));

            return map[indexX][indexY];
        } else
        {
            throw new UnknownCaseInMap();
        }
    }

    public Case[] getNeighbors(Case c)
    {

        Case[] listNeighbors = new Case[4];

        return null;
    }

    public Case[][] getListCase() {
        return listCase;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("Carte: " + name + "\n");

        for(Case[] tabCase : this.listCase)
        {
            tmp.append("[ ");
            for(Case c : tabCase)
            {
                tmp.append("(").append(c).append(") \t");
            }
            tmp.append("]\n");
        }
        tmp.append("\n");

        return tmp.toString();
    }
}
