///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA4
// Files:             Jubilex.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Random;

/**
 * Jubilex is an overpowered ooze monster known for extreme values
 * in armor and damage. It can corrode targets and grow rapidly.
 *
 * Bugs: None known
 * 
 * @author Steve Diaz
 */
public class Jubilex extends Ooze{

    /**
     * No-arg constructor that initializes fields using superclass.
     */
    public Jubilex(){
        super();
    }

    
    /**
     * Constructs a Jubilex with all Monster and Ooze fields.
     *
     * @param armor    the armor value
     * @param vitality the vitality value
     * @param speed    the speed value
     * @param volume   the ooze's volume
     * @param acidity  the ooze's acidity
     */
    public Jubilex(int armor, int vitality, double speed, int volume,
        int acidity){
            super(armor, vitality, speed, volume, acidity);
        }
     /**
     * Rest increases armor by 10000.
     */
    @Override
    public void rest(){
        setArmor(getArmor() + 10000);
    }

   /**
     * Calculates Jubilex power and applies a massive multiplier if crit.
     *
     * @return calculated power
     */
    @Override
    public double calculatePower() {
        double power = 70 * getVitality() + 350 * getVolume() + 100 *
             getAcidity();
        Random rand = new Random();
        double critChance = rand.nextDouble(); // 0.0 to 1.0

        if (critChance > 0.01) {
            power *= 100;
        }

        return power;
    }

    /**
     * Corrode succeeds 95% of the time.
     *
     * @return true 95% of the time, false otherwise
     */
    @Override
    public boolean corrode() {
        return new Random().nextDouble() < 0.95;
    }

    /**
     * Attack another monster using random variation of calculated power.
     *
     * @param monster the target
     * @return strike damage dealt
     */
    @Override
    public int attack(Monster monster) {
        double power = calculatePower();
        int volume = getVolume();
        Random rand = new Random();

        double min = power - 0.005 * volume;
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
     * Applies ooze volume-doubling, then randomly doubles one base stat.
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect(); // doubles volume
        Random rand = new Random();
        int statIndex = rand.nextInt(3);//0 =armor,1 = vitality,2 = speed

        if (statIndex == 0) {
            setArmor(getArmor() * 2);
        } else if (statIndex == 1) {
            setVitality(getVitality() * 2);
        } else {
            setSpeed(getSpeed() * 2);
        }
    } 

}
