///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA4
// Files:              Doppelganger.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////
/**
 * Doppelganger is a Humanoid monster that can create and manage clones of
 * itself. Its power and resting behavior are influenced by its clone list.
 * Doppelgangers may revive using the stats of a clone when they die.
 *
 * Bugs: None known
 * 
  * @author Steve Diaz
 */

import java.util.ArrayList;
import java.util.Random;
public class Doppelganger extends Humanoid implements Cloneable{

    private ArrayList<Doppelganger> clones;

    /**
     * No-arg constructor that calls superclass and initializes clone list.
     */
    public Doppelganger(){
        super();
        this.clones = new ArrayList<>();
    }

     /**
     * Constructs a Doppelganger with all fields and an empty clone list.
     *
     * @param armor        armor stat
     * @param vitality     vitality stat
     * @param speed        speed stat
     * @param intelligence intelligence stat
     * @param weapon       equipped weapon
     */
    public Doppelganger(int armor, int vitality, double speed, 
        int intelligence, String weapon){
            super(armor, vitality, speed, intelligence, weapon);
            this.clones = new ArrayList<>();
    }
    
    /**
     * Returns the list of clones.
     *
     * @return list of Doppelganger clones
     */
    public ArrayList<Doppelganger> getClones(){
        return this.clones;
    }

    /**
     * Determines if this Doppelganger or any of its clones are alive.
     *
     * @return true if self or clones are alive
     */
    @Override
    public boolean isAlive() {
        return getVitality() > 0 || !clones.isEmpty();
    }

    /**
     * Rest adds vitality to self and calls rest on all clones.
     */
    @Override
    public void rest() {
        setVitality(getVitality() + 10);
        for (Doppelganger clone : clones) {
            clone.rest();
        }
    }

    /**
     * Calculates total power from self and all clones.
     *
     * @return total calculated power
     */
    @Override
    public double calculatePower() {
        double power = 0;
        String weapon = getWeapon();
        int vitality = getVitality();
        int intelligence = getIntelligence();
        double speed = getSpeed();
        int armor = getArmor();

        if ("Staff".equals(weapon)) {
            power = 0.35 * vitality + 0.3 * intelligence - 0.6 * speed;
        } else if ("Dagger".equals(weapon)) {
            power = 0.05 * vitality + 0.15 * intelligence + 0.8 * speed;
        } else if ("Rapier".equals(weapon)) {
            power = 0.4 * armor + 0.2 * intelligence + 0.5 * speed;
        }

        for (Doppelganger clone : clones) {
            power += clone.calculatePower();
        }

        return power;
    }

    /**
     * Strike another monster using calculated power and random variation.
     *
     * @param monster the target
     * @return strike damage dealt
     */
    @Override
    public int strike(Monster monster) {
        double power = calculatePower();
        int intelligence = getIntelligence();
        Random rand = new Random();

        double min = power - 0.5 * intelligence;
        double max = power + 0.5 * intelligence;
        double randomValue = min + rand.nextDouble() * (max - min);
        int strikeValue = (int) Math.floor(randomValue);

        if (strikeValue <= 0) return 0;

        if (strikeValue < monster.getArmor()) {
            monster.setArmor(monster.getArmor() - strikeValue);
        } else {
            int damage = strikeValue - monster.getArmor();
            monster.setArmor(0);
            monster.setVitality(monster.getVitality() - damage);
        }

        return strikeValue;
    }

    /**
     * Attacks by calling strike.
     *
     * @param monster target to attack
     * @return damage dealt
     */
    @Override
    public int attack(Monster monster) {
        return strike(monster);
    }

    /**
     * Returns list of available weapons.
     *
     * @return string array of weapons
     */
    @Override
    protected String[] getAvailableWeapons() {
        return new String[] {"Staff", "Dagger", "Rapier", "Stick"};
    }

    /**
     * Applies base armory effect, then adds up to 5 clones.
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect();
        Random rand = new Random();
        int numClones = (int) Math.floor(rand.nextDouble() * 6);

        for (int i = 0; i < numClones; i++) {
            try {
                Doppelganger newClone = this.clone();
                clones.add(newClone);
            } catch (CloneNotSupportedException e) {
                // ignore if cloning fails
            }
        }
    }

    /**
     * Clone method makes a deep copy without cloning the clone list.
     *
     * @return new Doppelganger with same stats and empty clone list
     * @throws CloneNotSupportedException if cloning fails
     */
    @Override
    protected Doppelganger clone() throws CloneNotSupportedException {
        return new Doppelganger(
            getArmor(),
            getVitality(),
            getSpeed(),
            getIntelligence(),
            getWeapon()
        );
    }

    /**
     * Replaces self with first clone if dead and clones exist.
     *
     * @return true if revived using a clone
     */
    @Override
    public boolean handleDeathrattle() {
        if (getVitality() <= 0 && !clones.isEmpty()) {
            Doppelganger replacement = clones.remove(0);

            setArmor(replacement.getArmor());
            setVitality(replacement.getVitality());
            setSpeed(replacement.getSpeed());
            setIntelligence(replacement.getIntelligence());
            setWeapon(replacement.getWeapon());
            clearPoison();
            return true;
        }
        return false;
    }

}
