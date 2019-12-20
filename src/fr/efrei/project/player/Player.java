package fr.efrei.project.player;

import fr.efrei.project.exception.InsufficientDiceAttackException;
import fr.efrei.project.exception.UnknownCaseInMap;
import fr.efrei.project.map.Case;
import fr.efrei.project.map.Map;

import java.util.*;

public class Player {

    private static int globalId = 0;

    private int id;
    private HashSet<Case> listOwnedLand;
    private int dice;
    private int diceFree;

    public Player(int dice)
    {
        this.id = ++globalId;
        this.listOwnedLand = new HashSet<>();
        this.dice = dice;
        this.diceFree = dice;
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
        this.listOwnedLand.addAll(listOwnedLand);
        diceFree -= this.listOwnedLand.size();
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
        System.out.println("Votre tour commence (option possible: 'a'|'q'|'augment'|'free')");

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
                case "augment":
                    System.out.println("Augmentation puissance");
                    augmentStrenght(map);
                    break;
                case "free":
                    System.out.println("Dé(s) restant: " + diceFree);
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
        System.out.println(listOwnedLand);

         int choice_atk = sc.nextInt();
         try {
            Case case_atk = Map.getCaseFromId(map, choice_atk);
            System.out.println("Case attaquant: " + case_atk);
            if(listOwnedLand.contains(case_atk))
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
        listOwnedLand.add(c);
    }

    private static void loseLand(Case c)
    {

    }

    public int augmentStrenght(Map map)
    {
        System.out.println("Il vous reste " + diceFree + "dé(s), combien sougaitez vous en mettre ?");
        Scanner sc = new Scanner(System.in);
        int diceQty = sc.nextInt();

        System.out.println("Quelle case souhaité vous rendre plus forte ? (id)");
        System.out.println(listOwnedLand);
        int idCase = sc.nextInt();

        Case c = null;
        try {
            c = Map.getCaseFromId(map, idCase);
            return c.augmentStrength(this, dice);

        } catch (UnknownCaseInMap unknownCaseInMap) {
            unknownCaseInMap.printStackTrace();
        }

        return 0;
    }

    public HashSet<Case> getListOwnedLand() {
        return listOwnedLand;
    }

    public String getPlayerInfo () {
        return "(Joueur " + id + ")";
    }

    public int getDiceFree() {
        return diceFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id &&
                dice == player.dice &&
                diceFree == player.diceFree &&
                Objects.equals(listOwnedLand, player.listOwnedLand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listOwnedLand, dice, diceFree);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", listLand=" + listOwnedLand +
                ", dice=" + dice +
                '}';
    }
}
