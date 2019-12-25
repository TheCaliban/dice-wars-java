package fr.efrei.project.map;

import fr.efrei.project.exception.UnknownCaseInMapException;
import fr.efrei.project.player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Map {
    private Case[][] listCase;
    private String name;
//    private HashMap<Player, ArrayList<Case>> listOwnedLand;

    public Map(int size) {
        this.listCase = new Case[size][size];
        this.name = MapName.values()[new Random().nextInt(MapName.values().length)].toString();
//        this.listOwnedLand = new HashMap<>();
    }

    public void initMap(Player[] p, HashMap<Player, HashSet<Case>> listOwnedLand) {
        Random r = new Random();
        int tmp;
        int maxLand = listCase.length * listCase[0].length;
        int maxLandByP = maxLand / (p.length + 1);
        int[] landByP = new int[p.length];

        /*
         * Attribution des territoires aux joueurs
         */

        for (int i = 0; i < listCase.length; i++) {
            for (int j = 0; j < listCase[i].length; j++) {
                tmp = r.nextInt(p.length + 1);
                /*
                 * Le territoire n'est attribué à personne
                 */

                if (tmp < p.length) {
                    while (true) {
                        if (landByP[tmp] < maxLandByP) {
                            try {
                                listCase[i][j] = new Case(p[tmp]);
                                HashSet<Case> tmpList = new HashSet<>();

                                if (listOwnedLand.containsKey(p[tmp])) {
                                    tmpList = listOwnedLand.get(p[tmp]);
                                }
                                tmpList.add(getCaseFromIndex(this, i, j));
                                listOwnedLand.put(p[tmp], tmpList);

                                landByP[tmp] += 1;
                                break;
                            } catch (UnknownCaseInMapException unknownCaseInMap) {
                                unknownCaseInMap.printStackTrace();
                                break;
                            }
                        }
                        else{
                            boolean valid = true;
                            for(int l : landByP){
                                valid = (valid && l == maxLandByP);
                            }
                            if(valid){
                                listCase[i][j] = new Case();
                                break;
                            }
                            tmp = r.nextInt(p.length);
                        }
                    }
                } else {
                    listCase[i][j] = new Case();
                }
            }
        }
        System.out.println("Initialisation finie");
    }

    public static Case getCaseFromIndex(Map map, int x, int y) throws UnknownCaseInMapException {
        if (x < map.getListCase().length && y < map.getListCase()[0].length) {
            return map.getListCase()[x][y];
        } else {
            throw new UnknownCaseInMapException();
        }
    }

    public static Case getCaseFromId(Map map, int id) throws UnknownCaseInMapException {
        if (id >= 0 && id < Math.pow(map.getListCase().length, 2)) {
            Integer[] index = Map.getIndexFromId(map, id);
/*
            System.out.println("getCaseFromId id: " + id);
            System.out.println(index[0] + " " + index[1]);
*/
            return map.getListCase()[index[0]][index[1]];
        } else {
            throw new UnknownCaseInMapException();
        }
    }

    /*
     * Renvoie les coordonnées i,j calculé sur l'id
     */
    public static Integer[] getIndexFromId(Map map, int id) {
        Integer[] coordinates = new Integer[2];

        coordinates[0] = id / (map.getListCase().length);
        coordinates[1] = (id % (map.getListCase().length));

        return coordinates;

    }

    public static ArrayList<Case> getNeighbors(Map map, int id) {
        ArrayList<Case> neighbors = new ArrayList<>();

        Integer[] index = Map.getIndexFromId(map, id);
        int x = index[0];
        int y = index[1];

        System.out.println("x: " + x);
        System.out.println("y: " + y);

        //to refactor

        if (x > 0) {
            neighbors.add(map.getListCase()[x - 1][y]);
        }
        if(x < 8) {
            neighbors.add(map.getListCase()[x + 1][y]);
        }
        if(y > 0)
        {
            neighbors.add(map.getListCase()[x][y - 1]);
        }
        if (y < 8) {
            neighbors.add(map.getListCase()[x][y + 1]);
        }
        return neighbors;
    }

    public static ArrayList<Case> getNeighbors(Map map, Case c) {
        return Map.getNeighbors(map, c.getId());
    }

    public Case[][] getListCase() {
        return listCase;
    }

    public String getName() {
        return name;
    }

    public void loadFromFile(String path, Map map) throws FileNotFoundException {
        Path p = Paths.get(path);
        if(Files.exists(p))
        {
            File f = new File(path);
            Scanner reader = new Scanner(f);

            while(reader.hasNext())
            {
                String[] tmpStr = reader.nextLine().split(";");
            }

            reader.close();
        }
        else
        {
            System.out.println("Le chemin d'accès n'a pas été trouvé.");
        }
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("Carte: " + name + "\n");

        for (Case[] tabCase : this.listCase) {
            tmp.append("[ ");
            for (Case c : tabCase) {
                tmp.append("(").append(c).append(") \t");
            }
            tmp.append("]\n");
        }
        tmp.append("\n");

        return tmp.toString();
    }



    public String getColoredMap(int player) {
        StringBuilder tmp = new StringBuilder("Carte: " + name + "\n");

        for (Case[] tabCase : this.listCase) {
            tmp.append("[ ");
            for (Case c : tabCase) {
                tmp.append("(").append(c.getColoredCase(player)).append(") \t");
            }
            tmp.append("]\n");
        }
        tmp.append("\n");

        return tmp.toString();
    }
}
