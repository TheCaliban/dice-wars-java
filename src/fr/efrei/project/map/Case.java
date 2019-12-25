package fr.efrei.project.map;

import fr.efrei.project.player.Player;

import java.util.Objects;

public class Case {

    private static int global_mapid = 0;

    private final int id;
    private final String name;
    private Player owner;
    private int strength; // En nombre de dés

    public Case(Player owner)
    {
        this.id = global_mapid++;
        this.name = "map_" + id;
        this.owner = owner;
        this.strength = 1;
    }

    /*
     * Le player 0 sera considéré comme terrain neutre
     */
    public Case()
    {
        this(Player.getNeutre());
    }

     public int augmentStrength(Player p, int dice)
     {
         System.out.println(p);
         System.out.println(this.owner);
         System.out.println(p.getDiceFree());
         System.out.println(dice);
         if(p.equals(this.owner) && p.getDiceFree() >= dice)
         {
             this.strength += dice;
         }
         else
         {
             System.out.println("Vous n'avez pas assez de dé ou vous n'êtes pas propriétaire de cette case");
         }
         return strength;
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

    public int getStrength() {
        return strength;
    }

    public void setOwner(Player p)
    {
        this.owner = p;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return id == aCase.id &&
                Objects.equals(name, aCase.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        if(owner.equals(Player.getNeutre())) {
            return "(" + id + ", (****** *)" + " - " + strength + ")";
        }
        return "(" + id + ", " + owner.getPlayerInfo() + " - " + strength + ")";
    }

    public String getColoredCase(int player) {

        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_WHITE = "\u001B[37m";

        return "(" + id + ", " +
                owner.getPlayerInfoColored(player) +
                " - " + strength +
                ')';
    }
}
