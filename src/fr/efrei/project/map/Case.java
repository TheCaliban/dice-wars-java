package fr.efrei.project.map;

import fr.efrei.project.player.Player;

import java.util.Objects;

public class Case {

    private static int global_mapid = 0;

    private final int id;
    private final String name;
    private Player owner;
    private int diceQty;

    public Case(Player owner)
    {
        this.id = ++global_mapid;
        this.name = "map_" + id;
        this.owner = owner;
        this.diceQty = 1;
    }

    public Case()
    {
        this(null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public int getDiceQty() {
        return diceQty;
    }

    public void setOwner(Player p)
    {
        this.owner = p;
    }

    public void setDiceQty(int diceQty) {
        this.diceQty = diceQty;
    }

    @Override
    public String toString() {
        if(Objects.isNull(owner)) {
            return "(" + id + ", (****** *)" + ")";
        }
        return "(" + id + ", " + owner + ")";
    }
}
