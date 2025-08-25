///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA4
// Files:              Monster.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////
    /**
    * The Monster class is the superclass for all monsters in thegame.It stores
    * basic attributes like armor, vitality, speed, and poison status.Thisclass
    * also implements comparison based on a monster's average stats.
    *
    * Bugs: None known
    *
    * @author Steve Diaz
    */
    public abstract class Monster implements Comparable<Monster> {

    private int armor;
    private int vitality;
    private double speed;
    protected boolean poisoned;

    /**
     * No-arg constructor that initializes all fields to default values.
     */
    protected Monster() {
        this.armor = 0;
        this.vitality = 0;
        this.speed = 0.0;
        this.poisoned = false;
    }

    /**
     * Constructor that sets armor, vitality, and speed to given values.
     *
     * @param armor the monster's armor value
     * @param vitality the monster's vitality value
     * @param speed the monster's speed value
     */
    protected Monster(int armor, int vitality, double speed) {
        this.armor = armor;
        this.vitality = vitality;
        this.speed = speed;
        this.poisoned = false;
    }

    /**
     * Returns the armor value.
     *
     * @return the armor
     */
    public int getArmor(){
        return this.armor;
    }

    /**
     * Returns the vitality value.
     *
     * @return the vitality
     */
    public int getVitality(){
        return this.vitality;
    }

    /**
     * Returns the speed value.
     *
     * @return the speed
     */
    public double getSpeed(){
        return this.speed;
    }

    /**
     * Returns the poisoned status.
     *
     * @return true if poisoned, false otherwise
     */
    public boolean isPoisoned(){
        return this.poisoned;
    }

    /**
     * Sets the armor value.
     *
     * @param armor the new armor value
     */
    public void setArmor(int armor){
        this.armor = armor;
    }

    /**
     * Sets the armor value.
     *
     * @param armor the new armor value
     */
    public void setVitality(int vitality){
        this.vitality = vitality;
    }

    /**
     * Sets the speed value.
     *
     * @param speed the new speed value
     */
    public void setSpeed(double speed){
        this.speed = speed;
    }

    /**
     * Applies poison to this monster.
     */
    public void applyPoison() {
        this.poisoned = true;
    }

    /**
     * Clears the poison status.
     */
    public void clearPoison() {
        this.poisoned = false;
    }

    /**
     * Checks if the monster is alive (vitality > 0).
     *
     * @return true if alive, false otherwise
     */
    public boolean isAlive() {
        return this.vitality > 0;
    }

     /**
     * Placeholder for special ability. Does nothing by default.
     *
     * @param target the target monster
     */
    public void performSpecialAbility(Monster target) {
        // Do nothing
    }

    /**
     * Compares this monster to another based on average of armor, 
     * vitality, and speed.
     *
     * @param monster the other monster
     * @return -1 if this < other, 0 if equal, 1 if this > other
     */
    @Override
    public int compareTo(Monster monster) {
        double thisAvg = (this.armor + this.vitality + this.speed) / 3.0;
        double otherAvg = (monster.armor + monster.vitality +
             monster.speed) / 3.0;

        if (thisAvg < otherAvg) return -1;
        else if (thisAvg > otherAvg) return 1;
        return 0;
    }

    /**
     * Returns a string representation of the monster.
     *
     * @return the monster's details as a string
     */
    @Override
    public String toString() {
        return "(" + getClass().getName() + ")" +
                " armor: " + getArmor() +
                "; vitality: " + getVitality() +
                "; speed: " + getSpeed();
    }

    // Abstract methods to be implemented by subclasses
    /**
     * Restores some stats based on monster type.
     */
    public abstract void rest();

    /**
     * Calculates this monster's power.
     *
     * @return the calculated power value
     */
    public abstract double calculatePower();

    /**
     * Performs an attack on another monster.
     *
     * @param monster the target monster
     * @return the amount of damage done
     */
    public abstract int attack(Monster monster);

    /**
     * Applies effects from the armory.
     */
    public abstract void applyArmoryEffect();

    /**
     * Handles resurrection-like behavior if available.
     *
     * @return true if monster is revived, false otherwise
     */
    public abstract boolean handleDeathrattle();
}
