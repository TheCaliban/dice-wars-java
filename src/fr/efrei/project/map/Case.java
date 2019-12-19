package fr.efrei.project.map;

import fr.efrei.project.player.Player;

import java.util.Objects;

public class Case {

    private static int global_mapid = 0;

    private String id;
    private Player owner;

    public Case(Player owner)
    {
        this.id = "map_" + ++global_mapid;
        this.owner = owner;
    }

    public Case()
    {
        this(null);
    }

    public void setOwner(Player p)
    {
        this.owner = p;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        if(Objects.isNull(owner)) {
            return "(" + id + ", *" + ")";
        }
        return "(" + id + ", " + owner + ")";
    }
}
