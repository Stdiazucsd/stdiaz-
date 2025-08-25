///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA8
// Files:              Assignment8.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Assignment8 is the main test runner for the PA8 monster game.
 * It contains at least 5 tests that exercise core functionality.
 *
 * Bugs: None known
 * 
 * @author Steve Diaz
 */

public class Assignment8 {
    
    /**
     * Main method to trigger test cases.
     *
     * @param args command-line args
     */
    public static void main(String[] args) {
        System.out.println("All tests passed? " + unitTests());
    }

    /**
     * Runs all required test cases for PA8.
     *
     * @return true if all tests pass, false otherwise
     */
    public static boolean unitTests() {
        boolean passed = true;

        try {
            // Test 1: clone() - Doppelganger clone list starts empty
            Doppelganger d1 = new Doppelganger(5, 20, 3.0,
                 10, "Staff");
            Doppelganger dClone = d1.clone();
            if (dClone.getClones().size() != 0) {
                System.out.println("FAIL: Doppelganger clone() not empty.");
                passed = false;
            }

            // Test 2: compareTo() - different averages
            Bandit b1 = new Bandit(5, 10, 1.0, 
                3, "Axe");
            Bandit b2 = new Bandit(5, 10, 10.0, 
                3, "Axe");
            if (b1.compareTo(b2) >= 0) {
                System.out.println
                    ("FAIL: compareTo() didn't detect lower avg.");
                passed = false;
            }

            // Test 3: calculateBettingOdds() shifts odds based on compareTo
            double odds = Dungeon.calculateBettingOdds(b1, b2);
            if (odds >= 1.0) {
                System.out.println("FAIL: Betting odds too high.");
                passed = false;
            }

            // Test 4: armory() sets weapon for humanoid
            Bandit b3 = new Bandit();
            Dungeon.armory(b3);
            if (b3.getWeapon() == null) {
                System.out.println("FAIL: Armory did not equip weapon.");
                passed = false;
            }

            // Test 5: showdown() resolves to 0/1/2 correctly
            Bandit attacker = new Bandit(10, 20, 1.0, 
                5, "Axe");
            Jubilex tank = new Jubilex(0, 1, 0.5, 
                1, 1); // very weak on purpose
            int result = Dungeon.showdown(attacker, tank);
            if (result != 1 && result != 0) {
                System.out.println("FAIL: Showdown unexpected result.");
                passed = false;
            }

        } catch (Exception e) {
            System.out.println("FAIL: Exception during test: " +  
                e.getMessage());
            passed = false;
        }

        return passed;
    }
}
