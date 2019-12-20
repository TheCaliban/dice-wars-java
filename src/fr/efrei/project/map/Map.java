package fr.efrei.project.map;

import fr.efrei.project.exception.UnknownCaseInMap;
import fr.efrei.project.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    public void initMap(Player[] p, HashMap<Player, HashSet<Case>> listOwnedLand)
    {
        Random r = new Random();
        int tmp;

        /*
         * Attribution des territoires aux joueurs
         */

        for(int i = 0; i < listCase.length; i++)
        {
            for(int j = 0; j < listCase[i].length; j++)
            {
                tmp = r.nextInt(p.length + 1);
                /*
                 * Le territoire n'est attribué à personne
                 */
                if(tmp > p.length - 1)
                {
                    listCase[i][j] = new Case();
                }
                else
                {

                    try {
                        listCase[i][j] = new Case(p[tmp]);
                        HashSet<Case> tmpList = new HashSet<>();

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

    public static Case getCaseFromId(Map map, int id) throws UnknownCaseInMap {
        if(id > 0 && id < Math.pow(map.getListCase().length, 2)) {
            Integer[] index = Map.getIndexFromId(map, id);
            System.out.println("getCaseFromId id: " + id);
            System.out.println(index[0] + " " + index[1]);
            return map.getListCase()[index[0]][index[1]];
        } else
        {
            throw new UnknownCaseInMap();
        }
    }

    public static Integer[] getIndexFromId(Map map, int id)
    {
        Integer[] coordinates = new Integer[2];

        coordinates[0] = id / (map.getListCase().length);
        coordinates[1] = (id % (map.getListCase().length) - 1);

        return coordinates;

    }

    public static ArrayList<Case> getNeighbors(Map map, int id)
    {
        ArrayList<Case> neighbors = new ArrayList<>();

        Integer[] index = Map.getIndexFromId(map, id);
        int x = index[0];
        int y = index[1];

        //to refactor

        if(x > 0)
        {
            //left
            if(y > 0)
            {
                neighbors.add(map.getListCase()[x - 1][y - 1]);
                //top
            }
            if(y < 8)
            {
                neighbors.add(map.getListCase()[x - 1][y + 1]);
                //bottom
            }
        }
        if(x < 8)
        {
            //right
            if(y > 0)
            {
                neighbors.add(map.getListCase()[x + 1][y - 1]);
                //top
            }
            if(y < 8)
            {
                neighbors.add(map.getListCase()[x + 1][y + 1]);
                //bottom
            }
        }
        return neighbors;
    }

    public static ArrayList<Case> getNeighbors(Map map, Case c)
    {
        return Map.getNeighbors(map, c.getId());
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
