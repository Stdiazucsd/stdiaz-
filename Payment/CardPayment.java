// Title:              PA6
// Files:             CardPayment.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
/**
 * The CardPayment class represents a payment made using a physical or
 *  virtual card.
 * It includes card-specific details such as the card number and expiry
 *  date.
 * This class extends the Payment superclass.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class CardPayment extends Payment {
    private long cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private static final String HIGH_LEVEL_TYPE = "CardPayment";
    private static final String TYPE = "Untyped CardPayment";

    /**
     * No-arg constructor of the CardPayment class
     */
    public CardPayment() {}

    /**
 * Constructs a CardPayment with the specified details.
 *
 * @param transactionName the name of the transaction
 * @param amount the amount of the transaction
 * @param cardNumber the card number
 * @param expiryMonth the card's expiration month
 * @param expiryYear the card's expiration year
 */
    public CardPayment(String transactionName, double amount,
                             long cardNumber, int expiryMonth,
                             int expiryYear) {
        super ( transactionName, amount);
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    /**
 * Gets the card number associated with this payment.
 * @return the card number
 */
    @Override
    public long getCardNumber() {
        return this.cardNumber;
    }

    /**
 * Gets the expiration month of this card.
 * @return the expiration month
 */
    @Override
    public int getExpiryMonth() {
        return this.expiryMonth;
    }

    /**
 * Gets the expiration year of this card.
 * @return the expiration year
 */
    @Override
    public int getExpiryYear() {
        return this.expiryYear;
    }

    /**
 * Gets the high-level type of this payment.
 * @return the high-level type
 */
    @Override
    public String getHighLevelType() {
        return HIGH_LEVEL_TYPE;
    }

    /**
 * Gets the specific type of this card payment.
 * @return the type
 */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
 * Compares this CardPayment to another object for equality.
 * @param object the object to compare
 * @return true if all fields match, false otherwise
 */
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;
        if (!(object instanceof CardPayment)) return false;
        CardPayment other = (CardPayment) object;
        return this.cardNumber == other.cardNumber &&
           this.expiryMonth == other.expiryMonth &&
           this.expiryYear == other.expiryYear;
    }

    /**
 * Returns a string representation of this CardPayment, including the
 * transaction name, card number, and expiration date.
 *
 * @return a formatted string describing the card payment
 */
    @Override
    public String toString() {
        return "CardPayment (" + getTransactionName() +
            ") cardNumber: " + getCardNumber() + ", expiryMonth: " +
                getExpiryMonth() + ", expiryYear: " + getExpiryYear();
    }
}
