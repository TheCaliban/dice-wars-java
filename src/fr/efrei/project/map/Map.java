package fr.efrei.project.map;

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

    public Case[][] getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Carte: " + name + '\'';
    }
}
