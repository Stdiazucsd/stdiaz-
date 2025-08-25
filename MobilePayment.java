// Title:              PA6
// Files:            MobilePayment.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
/**
 * The MobilePayment class represents a payment made using a mobile phone.
 * It includes a phone number and star rating to determine digital risk.
 * This class extends ElectronicPayment.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class MobilePayment extends ElectronicPayment {
    private String phoneNumber;
    private int starRating;
    private static final String TYPE = "MobilePayment";

    private static final double MAX_STAR_RATING = 10.0;

    /**
     * No-arg constructor of the MobilePayment class
     */
    public MobilePayment() {}

    /**
 * Constructs a MobilePayment with the specified details.
 *
 * @param transactionName the transaction name
 * @param amount the amount of the payment
 * @param accountId the account ID
 * @param emailAddress the email address
 * @param phoneNumber the phone number for the mobile payment
 * @param starRating the star rating of the user (0–10)
 */
    public MobilePayment(String transactionName, double amount,
                                long accountId, String emailAddress,
                                String phoneNumber, int starRating) {
        super ( transactionName, amount, accountId, emailAddress );
        this.phoneNumber = phoneNumber;
        this.starRating = starRating;
    }

    /**
 * Gets the phone number associated with this mobile payment.
 * @return the phone number
 */
    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
 * Gets the star rating of the user making the payment.
 * @return the star rating
 */
    @Override
    public int getStarRating() {
        return this.starRating;
    }

    /**
 * Gets the specific type of this mobile payment.
 * @return the string "MobilePayment"
 */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
 * Compares this MobilePayment to another object for equality.
 * @param object the object to compare
 * @return true if all fields match, false otherwise
 */
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;
        if (!(object instanceof MobilePayment)) return false;
        MobilePayment other = (MobilePayment) object;
        return this.phoneNumber.equals(other.phoneNumber) &&
           this.starRating == other.starRating;
    }

    /**
 * Calculates the digital risk of this mobile payment based on amount
 *  and star rating.
 * @return the calculated risk
 */
    @Override
    public double calculateDigitalRisk() {
        return getAmount() / (starRating / MAX_STAR_RATING);
    }

    /**
 * Returns a string representation of this MobilePayment, including the
 * transaction name, phone number, and star rating.
 *
 * @return a formatted string describing the mobile payment
 */
    @Override
    public String toString() {
        return "MobilePayment (" + getTransactionName() +
            ") phoneNumber: " + getPhoneNumber() + ", starRating: " +
                getStarRating();
    }
}
