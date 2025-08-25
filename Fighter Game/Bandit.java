///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA4
// Files:              Bandit.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Random;

/**
 * Bandit is a concrete subclass of Humanoid that uses weapons like axes,
 * shields, and crossbows. It has random critical strike modifiers and
 * modifies stats through rest and armory.
 *
 * Bugs: None known
 * 
 * @author Steve Diaz
 */
public class Bandit extends Humanoid{
     /**
     
     * No-arg constructor that calls Humanoid's no-arg constructor.
     */
    public Bandit() {
        super();
    }

    /**
     * Constructs a Bandit with specific Monster and Humanoid stats.
     *
     * @param armor        the armor value
     * @param vitality     the vitality value
     * @param speed        the speed value
     * @param intelligence the intelligence value
     * @param weapon       the starting weapon
     */
    public Bandit(int armor, int vitality, double speed, int intelligence, 
        String weapon) {
        super(armor, vitality, speed, intelligence, weapon);
    }

    /**
     * Bandit gains 30 vitality when resting.
     */
    @Override
    public void rest() {
        setVitality(getVitality() + 30);
    }
    
    /**
     * Calculates power based on weapon type and random crit chance.
     *
     * @return the calculated power
     */
    @Override
    public double calculatePower() {
        double power = 0;
        String weapon = getWeapon();
        int vitality = getVitality();
        int intelligence = getIntelligence();
        double speed = getSpeed();
        int armor = getArmor();

        if ("Axe".equals(weapon)) {
            power = 0.65 * vitality + 0.35 * intelligence - 0.1 * speed;
        } else if ("Crossbow".equals(weapon)) {
            power = 0.25 * vitality + 0.5 * intelligence + 0.25 * speed;
        } else if ("Shield".equals(weapon)) {
            power = 0.7 * armor + 0.2 * vitality + 0.1 * speed - 0.2 *
                intelligence;
        } else {
            power = 0;
        }

        Random rand = new Random();
        double critChance = rand.nextDouble(); // 0.0 to 1.0
        if (critChance > 0.6) {
            power *= 2;
        }

        return power;
    }

    /**
     * Strike another monster based on calculated power.
     *
     * @param monster the target monster
     * @return damage dealt
     */
    @Override
    public int strike(Monster monster) {
        double power = calculatePower();
        int intelligence = getIntelligence();
        Random rand = new Random();

        double min = power - 0.15 * intelligence;
        double max = power + 0.25 * intelligence;
        double randomValue = min + rand.nextDouble() * (max - min);
        int strikeValue = (int) Math.floor(randomValue);

        if (strikeValue <= 0) return 0;

        if (strikeValue < monster.getArmor()) {
            monster.setArmor(monster.getArmor() - strikeValue);
        } else {
            int remainingDamage = strikeValue - monster.getArmor();
            monster.setArmor(0);
            monster.setVitality(monster.getVitality() - remainingDamage);
        }

        return strikeValue;
    }

    /**
     * Attack another monster by striking.
     *
     * @param monster the target monster
     * @return damage dealt
     */
    @Override
    public int attack(Monster monster) {
        return strike(monster);
    }

    /**
     * Available weapons for a Bandit.
     *
     * @return array of weapon names
     */
    @Override
    protected String[] getAvailableWeapons() {
        return new String[] {"Axe", "Crossbow", "Shield", "Stick"};
    }

    /**
     * Applies Humanoid armory effect and then doubles a random stat.
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect();
        Random rand = new Random();
        int statIndex = rand.nextInt(3); //0 = armor, 1=vitality,2=speed

        if (statIndex == 0) {
            setArmor(getArmor() * 2);
        } else if (statIndex == 1) {
            setVitality(getVitality() * 2);
        } else {
            setSpeed(getSpeed() * 2);
        }
    }  
}
