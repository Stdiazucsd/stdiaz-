///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA4
// Files:              Ooze.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////
/**
 * Ooze is an abstract subclass of Monster that represents slimy monsters
 * with volume and acidity. It adds slime-specific traits and logic for
 * performing special corrosive abilities.
 *
 * Bugs: None known
 * 
 * @author Steve Diaz
 */
public abstract class Ooze extends Monster {
    
    private int volume;
    private int acidity;

    /**
     * No-arg constructor that initializes all fields to default values.
     */
    protected Ooze(){
        super();
        this.volume = 0;
        this.acidity = 0;
    }

      /**
     * Constructor to initialize all Monster and Ooze fields.
     *
     * @param armor    the monster's armor value
     * @param vitality the monster's vitality value
     * @param speed    the monster's speed value
     * @param volume   the volume of the ooze
     * @param acidity  the acidity level of the ooze
     */
    protected Ooze(int armor, int vitality, double speed, int volume, 
        int acidity){
            super(armor, vitality, speed);
            this.volume = volume;
            this.acidity = acidity;
        }
    
     /**
     * Gets the volume of the ooze.
     *
     * @return the volume
     */
    public int getVolume (){
        return this.volume;
    }

    /**
     * Gets the acidity level of the ooze.
     *
     * @return the acidity
     */
    public int getAcidity(){
        return this.acidity;
    }

     /**
     * Sets the volume of the ooze.
     *
     * @param volume the new volume
     */
    public void setVolume(int volume){
        this.volume = volume;
    }

    
    /**
     * Sets the acidity level of the ooze.
     *
     * @param acidity the new acidity level
     */
    public void setAcidity(int acidity){
        this.acidity = acidity;
    }

    /**
     * Attempts to corrode the target monster.
     * If corrosion succeeds:
     *   - and target has armor > 0, set armor to 0.
     *   - else, poison the target.
     *
     * @param target the monster being targeted
     */
    @Override
    public void performSpecialAbility(Monster target) {
        if (corrode()) {
            if (target.getArmor() > 0) {
                target.setArmor(0);
            } else {
                target.applyPoison();
            }
        }
    }

    /**
     * Ooze armory effect doubles its volume.
     */
    @Override
    public void applyArmoryEffect() {
        setVolume(this.volume * 2);
    }

    /**
     * Ooze has no special deathrattle behavior.
     *
     * @return false always
     */
    @Override
    public boolean handleDeathrattle() {
        return false;
    }

    /**
     * Abstract method to determine whether corrosion occurs.
     *
     * @return true if corrosion succeeds, false otherwise
     */
    public abstract boolean corrode();
    
}
