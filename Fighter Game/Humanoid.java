///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA4
// Files:              Humanoid.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////
/**
 * Humanoid is an abstract subclass of Monster that represents
 * intelligent, weapon-using creatures. It adds intelligence and weapon
 * fields and declares methods for subclasses to define weapon behavior.
 *
 * Bugs: None known
 * 
 * @author Steve Diaz
 */

 /**
     * No-arg constructor that initializes all fields to default values.
     */
public abstract class Humanoid extends Monster{

    private int intelligence;
    private String weapon; 

    protected Humanoid(){
        super();
        this.intelligence = 0;
        this.weapon = null;
    } 

    /**
     * Constructor to initialize all Monster and Humanoid fields.
     *
     * @param armor       the monster's armor value
     * @param vitality    the monster's vitality value
     * @param speed       the monster's speed value
     * @param intelligence the monster's intelligence value
     * @param weapon      the monster's weapon
     */
    protected Humanoid(int armor, int vitality, double speed, int intelligence,
        String weapon){
        super (armor, vitality, speed);
        this.intelligence = intelligence;
        this.weapon = weapon;
        }
    
    /**
     * Gets the intelligence value.
     *
     * @return the intelligence
     */
    public int getIntelligence(){
        return this.intelligence;
    }

    /**
     * Gets the current weapon.
     *
     * @return the weapon
     */
    public String getWeapon(){
        return this.weapon;
    }

    /**
     * Sets the intelligence value.
     *
     * @param intelligence the new intelligence value
     */
    public void setIntelligence(int intelligence){
        this.intelligence = intelligence;
    }

    /**
     * Sets the weapon.
     *
     * @param weapon the new weapon
     */
    public void setWeapon(String weapon){
        this.weapon = weapon;
    }

    /**
     * Humanoids randomly select a weapon from the armory.
     */
    @Override
    public void applyArmoryEffect() {
        String[] weapons = getAvailableWeapons();
        int index = new java.util.Random().nextInt(weapons.length);
        setWeapon(weapons[index]);
    }

    /**
     * Humanoids have no deathrattle behavior by default.
     *
     * @return false always
     */
    @Override
    public boolean handleDeathrattle() {
        return false;
    }

    /**
     * Abstract method for striking a target monster.
     *
     * @param monster the monster to strike
     * @return damage dealt
     */
    public abstract int strike(Monster monster);

    /**
     * Abstract method for getting available weapons.
     *
     * @return an array of weapon options
     */
    protected abstract String[] getAvailableWeapons();
}
