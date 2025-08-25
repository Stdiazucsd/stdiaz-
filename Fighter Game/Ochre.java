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
import java.util.ArrayList;
import java.util.Random;
/**
 * Ochre is a cloneable ooze that can split itself into smaller clones.
 * It calculates total power using all clones and may revive using a clone.
 *
 * Bugs: None known
 * 
  * @author Steve Diaz
 */
public class Ochre extends Ooze implements Cloneable{

    private ArrayList<Ochre> clones;   
    
    /**
     * No-arg constructor that calls superclass and initializes clones list.
     */
    public Ochre(){
        super();
        this.clones = new ArrayList<>();
    }

     /**
     * Constructs an Ochre with full stats and empty clone list.
     *
     * @param armor    the armor value
     * @param vitality the vitality value
     * @param speed    the speed value
     * @param volume   the ooze's volume
     * @param acidity  the ooze's acidity
     */
    public Ochre(int armor, int vitality, double speed, int volume, 
        int acidity) {
        super(armor, vitality, speed, volume, acidity);
        this.clones = new ArrayList<>();
    }

    /**
     * Gets the list of Ochre clones.
     *
     * @return the clones list
     */
    public ArrayList<Ochre> getClones() {
        return this.clones;
    }

    /**
     * Determines if the Ochre or any clones are alive.
     *
     * @return true if alive
     */
    @Override
    public boolean isAlive() {
        return getVitality() > 0 || !clones.isEmpty();
    }

    /**
     * Rest adds armor to self and recursively rests each clone.
     */
    @Override
    public void rest() {
        setArmor(getArmor() + 20);
        for (Ochre clone : clones) {
            clone.rest();
        }
    }

    /**
     * Calculates power from self and recursively from clones.
     *
     * @return total calculated power
     */
    @Override
    public double calculatePower() {
        double power = 0.7 * getVitality() + 0.35 * getVolume() + getAcidity();

        for (Ochre clone : clones) {
            power += clone.calculatePower();
        }

        return power;
    }

    /**
     * Corrode has 9.5% chance per clone or self.
     *
     * @return true if any succeed
     */
    @Override
    public boolean corrode() {
        Random rand = new Random();
        if (rand.nextDouble() < 0.095) {
            return true;
        }

        for (Ochre clone : clones) {
            if (clone.corrode()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Attack another monster using power and volume-based variance.
     *
     * @param monster the target
     * @return damage dealt
     */
    @Override
    public int attack(Monster monster) {
        double power = calculatePower();
        int volume = getVolume();
        Random rand = new Random();

        double min = power - 0.5 * volume;
        double max = power + 0.5 * volume;
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
     * Applies armory effect: doubles volume, then adds 0–5 clones.
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect(); // doubles volume
        Random rand = new Random();
        int numClones = (int) Math.floor(rand.nextDouble() * 6);

        for (int i = 0; i < numClones; i++) {
            try {
                Ochre newClone = this.clone();
                clones.add(newClone);
            } catch (CloneNotSupportedException e) {
                // skip clone if error
            }
        }
    }

    /**
     * Returns a clone with same stats but half volume, no clone list.
     *
     * @return cloned Ochre
     * @throws CloneNotSupportedException if volume too small
     */
    @Override
    protected Ochre clone() throws CloneNotSupportedException {
        if (getVolume() == 1) {
            throw new CloneNotSupportedException("Too small to clone.");
        }

        int halfVolume = getVolume() / 2;
        setVolume(halfVolume);

        return new Ochre(
            getArmor(),
            getVitality(),
            getSpeed(),
            halfVolume,
            getAcidity()
        );
    }

    /**
     * If dead and has clones, replace self with first clone's stats.
     *
     * @return true if revival occurred
     */
    @Override
    public boolean handleDeathrattle() {
        if (getVitality() <= 0 && !clones.isEmpty()) {
            Ochre replacement = clones.remove(0);

            setArmor(replacement.getArmor());
            setVitality(replacement.getVitality());
            setSpeed(replacement.getSpeed());
            setVolume(replacement.getVolume());
            setAcidity(replacement.getAcidity());
            clearPoison();
            return true;
        }

        return false;
    }


}
