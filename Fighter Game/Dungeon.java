///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA4
// Files:             Ochre.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Dungeon is a utility class that manages the monster fighting game.
 * It calculates odds, equips monsters from the armory, and runs battles.
 *
 * Bugs: None known
 * 
 * @author Steve Diaz
 */

 public class Dungeon {

    // Constants for spacing and output
    private final static int SPACING = 17;
    private final static String LEFT = "Left";
    private final static String RIGHT = "Right";


    private Dungeon(){

    }

     /**
     * Calculates odds of monster1 winning against monster2.
     *
     * @param monster1 the first monster
     * @param monster2 the second monster
     * @return calculated odds
     */
    public static double calculateBettingOdds(Monster monster1, 
        Monster monster2) {
        double power1 = monster1.calculatePower();
        double power2 = monster2.calculatePower();
        double probability = power1 / (power1 + power2);
        double odds = probability / (1 - probability);

        int compare = monster1.compareTo(monster2);
        if (compare < 0) {
            odds *= 0.8;
        } else if (compare > 0) {
            odds *= 1.2;
        }

        return odds;
    }

    /**
     * Equips a monster by applying its armory effect.
     *
     * @param monster the monster to modify
     */
    public static void armory(Monster monster) {
        monster.applyArmoryEffect();
    }

    /**
     * Runs a battle between two monsters and prints each round.
     *
     * @param monster1 the left-side monster
     * @param monster2 the right-side monster
     * @return 0 = tie, 1 = left wins, 2 = right wins
     */
    public static int showdown(Monster monster1, Monster monster2) {
        int round = 0;
        boolean poisoned = false;

        while (monster1.isAlive() && monster2.isAlive()) {
            printRound(round);
            printBothMonsters(monster1, monster2);

            monster1.performSpecialAbility(monster2);
            monster2.performSpecialAbility(monster1);

            int damage1 = monster1.attack(monster2);
            int damage2 = monster2.attack(monster1);

            printAttack(LEFT, damage1);
            printAttack(RIGHT, damage2);

            if (monster1.isPoisoned() || monster2.isPoisoned()) {
                poisoned = true;
            }

            monster1.handleDeathrattle();
            monster2.handleDeathrattle();

            if (monster1.isAlive()) monster1.rest();
            if (monster2.isAlive()) monster2.rest();

            round++;
        }

        printFinalStats(monster1, monster2, poisoned);

        if (!monster1.isAlive() && !monster2.isAlive()) {
            printTieGame();
            return 0;
        } else if (monster1.isAlive()) {
            printWinner(LEFT);
            return 1;
        } else {
            printWinner(RIGHT);
            return 2;
        }
    }

    /* ========== Provided Helper Methods ========== */

    public static void printBothMonsters(Monster monster1, Monster monster2) {
        int armorSpacing = calcSpacing
        (Integer.toString(monster1.getArmor()));
        int healthSpacing = calcSpacing
        (Integer.toString(monster1.getVitality()));
        int speedSpacing = calcSpacing
        (String.format("%.2f", monster1.getSpeed()));
        int monsterSpacing = calcSpacing(monster1.getClass().getName());

        String str = String.format(
            "(%s) %s (%s)\n" +
            "----------        ----------\n" +
            "A: %d %s A: %d\n" +
            "V: %d %s V: %d\n" +
            "S: %.2f %s S: %.2f\n",
            monster1.getClass().getName(),
            " ".repeat(monsterSpacing),
            monster2.getClass().getName(),
            monster1.getArmor(), " ".repeat(armorSpacing),
             monster2.getArmor(),
            monster1.getVitality(), " ".repeat(healthSpacing), 
            monster2.getVitality(),
            monster1.getSpeed(), " ".repeat(speedSpacing), monster2.getSpeed()
        );

        System.out.println(str);
    }

    public static int calcSpacing(String str) {
        int totalWidth = SPACING;
        int spacing = totalWidth - str.length();
        return Math.max(spacing, 0);
    }

    public static void printRound(int round) {
        System.out.println();
        System.out.println("Round " + round + ":");
    }

    public static void printAttack(String side, int damage) {
        System.out.printf("%s does %d damage!\n", side, damage);
    }

    public static void printFinalStats(Monster monster1, Monster monster2, 
        boolean poisoned) {
        System.out.println();
        printBothMonsters(monster1, monster2);
        if (poisoned) {
            System.out.println("A monster was poisoned.");
        }
    }

    public static void printTieGame() {
        System.out.println("-------GAME OVER-------");
        System.out.println("TIE: Both monsters died!");
    }

    public static void printWinner(String side) {
        System.out.println("-------GAME OVER-------");
        System.out.println(side + " monster wins!");
    }

 }
