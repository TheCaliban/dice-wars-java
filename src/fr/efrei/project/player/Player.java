package fr.efrei.project.player;

import fr.efrei.project.exception.InsufficientDiceAttackException;
import fr.efrei.project.exception.UnknownCaseInMapException;
import fr.efrei.project.map.Case;
import fr.efrei.project.map.Map;

import java.util.*;

public class Player {

    private static int globalId = 0;

    private int id;
    private HashSet<Case> listOwnedLand;
    private int dice;
    private int diceFree;

    public final static Player neutre = new Player();


    public Player(int dice)
    {
        this.id = ++globalId;
        this.listOwnedLand = new HashSet<>();
        this.dice = dice;
        this.diceFree = dice;
    }

    /*
     * Le player 0 sera considéré comme terrain neutre
     * Il est obligatoirement crée à chaque partie
     * createMultiplayer()
     */
    private Player()
    {
        this.id = 0;
        this.listOwnedLand = null;
        this.dice = 0;
        this.diceFree = 0;
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
        System.out.print("Votre tour commence ");

        Scanner sc = new Scanner(System.in);
        boolean end;

        do {
            System.out.println("(option possible: 'a'|'q'|'augment'|'free'|'afficher')");
            String choice = sc.nextLine();
            end = false;

            switch (choice)
            {
                case "q":
                    System.out.println("Tour terminé");
                    end = true;
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
                case "afficher":
                    System.out.println(map.getColoredMap(id));
                    break;
                default:
                    System.out.println("Faites un choix");
                    break;
            }

        } while(!end);
    }

    private void dialogAttack(Map map) {

        Scanner sc = new Scanner(System.in);

        boolean okAtk = false;
        boolean okDef = false;
        int choice_atk;
        int choice_def;
        ArrayList<Case> neighbors;

        do {

            System.out.println("Avec quel territoire souhaitez vous attaquer ? (id)");
            System.out.println(listOwnedLand);

            Case case_atk;
            try {
                choice_atk = sc.nextInt();
                case_atk = Map.getCaseFromId(map, choice_atk);

                if(case_atk.getStrength() < 2) {
                    throw new InsufficientDiceAttackException();
                }
            } catch (UnknownCaseInMapException | InsufficientDiceAttackException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (InputMismatchException e) {
                System.out.println("Veuillez renseigner un nombre");
                continue;
            }

            if (!listOwnedLand.contains(case_atk)) {
                System.out.println("Vous ne possédez pas cette case");
                continue;
            }

            okAtk = true;
            neighbors = Map.getNeighbors(map, choice_atk);

            do {

                System.out.println(neighbors);
                System.out.println("Choisissez un terrain à attaquer: (id)");

                Case case_def;
                try {
                    choice_def = sc.nextInt();
                    case_def = Map.getCaseFromId(map, choice_def);
                } catch (UnknownCaseInMapException e) {
                    System.out.println(e.getMessage());
                    continue;
                } catch (InputMismatchException e) {
                    System.out.println("Veuillez renseigner un nombre");
                    continue;
                }

                if (neighbors.contains(case_def)) {
                    attack(map, case_atk, case_def);
                    okDef = true;
                } else {
                    System.out.println("Vous ne possédez pas cette case");
                }

            } while (!okDef);

        } while (!okAtk);
    }

    public Case attack(Map map, Case attacker, Case defender) {

        int totalStrengthAtk = calculTotalStrength(attacker.getStrength());
        int totalStrengthDef = calculTotalStrength(defender.getStrength());

        System.out.println("Vous avez fait " + totalStrengthAtk + " points de dégats totaux.");
        System.out.println("Votre adversaire a fait " + totalStrengthDef + " points de dégats totaux.");


        if(totalStrengthAtk > totalStrengthDef)
        {
            return attacker;
        }
        else
        {
            return defender;
        }
    }

    private int calculTotalStrength(int size)
    {
        Integer[] diceTotalStrength = new Integer[size]; // Ne sert pas pour le moment mais pour le suivi des jets de dés
        int totalStrength = 0;

        for(int i = 0; i < size; i++)
        {
            int tmp = new Random().nextInt(6) + 1;
            diceTotalStrength[i] = tmp;
            totalStrength += tmp;
        }

        System.out.println(diceTotalStrength);

        return totalStrength;
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
        Scanner sc = new Scanner(System.in);
        int diceQty;
        do {
            System.out.println("Il vous reste " + diceFree + " dé(s), combien souhaitez vous en mettre ?");
            diceQty = sc.nextInt();
        } while(diceQty > diceFree);

        System.out.println("Quelle case souhaité vous rendre plus forte ? (id)");
        System.out.println(listOwnedLand);
        int idCase = sc.nextInt();

        Case c;
        try {
            c = Map.getCaseFromId(map, idCase);
            System.out.println(c);
            useDice(diceQty);
            return c.augmentStrength(this, diceQty);

        } catch (UnknownCaseInMapException unknownCaseInMap) {
            unknownCaseInMap.printStackTrace();
        }

        return 0;
    }

    public void useDice(int dice)
    {
        this.diceFree -= dice;
        System.out.println(this.dice);
        System.out.println(diceFree);
    }

    public HashSet<Case> getListOwnedLand() {
        return listOwnedLand;
    }

    public int getId() {
        return id;
    }

    public int getDice() {
        return dice;
    }

    public String getPlayerInfo() {
        if(this.getId() == 0)
        return "(****** *)";
        return "(Joueur " + id + ")";
    }

    public String getPlayerInfoColored(int player) {

        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_WHITE = "\u001B[37m";

        if(this.getId() == 0)
        return ANSI_GREEN + "(****** *)" + ANSI_RESET;
        if(player == id)
        return ANSI_RED + "(Joueur " + id + ")" + ANSI_RESET;
        return "(Joueur " + id + ")";
    }

    public int getDiceFree() {
        return diceFree;
    }

    public static Player getNeutre()
    {
        return neutre;
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
