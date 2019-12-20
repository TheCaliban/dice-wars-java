package fr.efrei.project.player;

import fr.efrei.project.exception.InsufficientDiceAttackException;
import fr.efrei.project.exception.InsufficientDiceException;
import fr.efrei.project.exception.UnknownCaseInMap;
import fr.efrei.project.map.Case;
import fr.efrei.project.map.Map;

import java.util.*;

public class Player {

    private static int globalId = 0;

    private int id;
    private HashSet<Case> listLand;
    private int dice;
    private int diceUsed;

    public Player(int dice)
    {
        this.id = ++globalId;
        this.listLand = new HashSet<>();
        this.dice = dice;
        this.diceUsed = 0;
    }

    //Crée les joueurs de la partie
    public static Player[] createMultiplePlayer(int nbPlayer, int nbDice)
    {
        Player[] tmp = new Player[nbPlayer];

        for(int i = 0; i <= nbPlayer - 1; i++)
        {
            tmp[i] = new Player(nbDice);
        }

        return tmp;
    }


    // Initialise la liste des territoires du joueur
    public void initPlayer(HashSet<Case> listOwnedLand)
    {
        this.listLand.addAll(listOwnedLand);
    }

    //L'ensemble des actions qui doivent être calculées au tour du joueur
    public void play(Map map)
    {
        System.out.println(this.getPlayerInfo() + " C'est à vous !");
        this.turn(map);
    }

    private void turn(Map map)
    {
        /*
         * Concentre les actions sur lesquelles le joueurs à la main(attack, fin de tour, déplacement de dés)
         */
        System.out.println("Votre tour commence (option possible: 'a'|'q')");

        Scanner sc = new Scanner(System.in);
        boolean end;

        do {
            String choice = sc.nextLine();
            end = true;

            switch (choice)
            {
                case "q":
                    System.out.println("Tour terminé");
                    break;
                case "a":
                    System.out.println("Attaquer territoire");
                    dialogAttack(map);
                    break;
                default:
                    end = false;
                    System.out.println("Faites un choix");
                    break;
            }

        } while(!end);
    }

    private void dialogAttack(Map map) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Avec quel territoire souhaitez vous attaquer ? (id)");
        System.out.println(listLand);

         int choice_atk = sc.nextInt();
        try {
            Case case_atk = Map.getCaseFromId(map, choice_atk);
            System.out.println("Case attaquant: " + case_atk);
            if(listLand.contains(case_atk))
            {
                ArrayList<Case> neighbors = Map.getNeighbors(map, choice_atk);
                System.out.println(neighbors);
                System.out.println("Choisissez un terrain à attaquer: (id)");

                int choice_def = sc.nextInt();
                Case case_def = Map.getCaseFromId(map, choice_def);

                if(neighbors.contains(case_def))
                {
                    attack(map, case_atk, case_def);
                }

            }
            else
            {
                System.out.println("Vous ne possédez pas cette case");
            }
        } catch (UnknownCaseInMap | InsufficientDiceAttackException unknownCaseInMap) {
            unknownCaseInMap.printStackTrace();
        }
    }

    public void addLand(Case c)
    {
        listLand.add(c);
    }

    public Case attack(Map map, Case attacker, Case defender) throws InsufficientDiceAttackException {

        if(attacker.getStrength() < 2) {
            throw new InsufficientDiceAttackException();
        }

        if(attacker.getStrength() > defender.getStrength())
        {
            return attacker;
        }
        else if(attacker.getStrength() < defender.getStrength())
        {
            return defender;
        }
        return null;
    }

    public void conqueerNewLand(Case c)
    {
        listLand.add(c);
    }

    private static void loseLand(Case c)
    {

    }

    public HashSet<Case> getListLand() {
        return listLand;
    }

    public String getPlayerInfo () {
        return "(Joueur " + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id &&
                dice == player.dice &&
                diceUsed == player.diceUsed &&
                Objects.equals(listLand, player.listLand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listLand, dice, diceUsed);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", listLand=" + listLand +
                ", dice=" + dice +
                '}';
    }
}
