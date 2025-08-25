// Title:              PA6
// Files:             Payment.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors

/**
 * The Payment class is the base class for all types of payments.
 * It stores general information about a payment, such as its transaction
 *  name and amount.
 * Subclasses will define more specific behavior and data for different
 *  types of payments.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class Payment {
    private String transactionName;
    private double amount;

    private static final String HIGH_LEVEL_TYPE = "Untyped High Level Payment";
    private static final String TYPE = "Untyped Payment";

    /**
     * No-arg constructor of the Payment class
     */
    public Payment() {}

    /**
 * Constructs a Payment with the specified transaction name and amount.
 *
 * @param transactionName the unique name of the transaction
 * @param amount the amount of the payment
 */
    public Payment(String transactionName, double amount) {
        this.transactionName = transactionName;
        this.amount = amount;
    }

    /**
 * Gets the transaction name of this payment.
 * @return the transaction name
 */
    public String getTransactionName() {
        return this.transactionName;
    }

    /**
 * Gets the amount of this payment.
 * @return the amount
 */
    public double getAmount() {
        return this.amount;
    }

    /**
 * Gets the high-level type of this payment.
 * @return the high-level type string
 */
    public String getHighLevelType() {
        return HIGH_LEVEL_TYPE;
    }

   /**
 * Gets the specific type of this payment.
 * @return the type string
 */
    public String getType() {
        return TYPE;
    }

    /**
 * Sets the amount of this payment.
 * @param amount the new amount
 */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
 * Compares this payment to another object for equality.
 * @param object the object to compare
 * @return true if object is a Payment with the same transactionName and amount
 * otherwise
 */
    @Override
    public boolean equals(Object object) {
        if( this == object) return true;
        if ( ! ( object instanceof Payment)) return false;
        Payment other = (Payment) object;
        return this.transactionName.equals(other.transactionName)
            && this.amount == other.amount;
    }

    /**
 * Returns a string representation of this payment, including the
 * transaction name and amount.
 *
 * @return a formatted string describing the payment
 */
    @Override
    public String toString() {
        return "Payment (" + getTransactionName() +
            ") amount: " + getAmount();
    }

    /**
     * Card number of a CardPayment instance
     * @return the long representation of the card number
     */
    public long getCardNumber() {
        return 0;
    }

    /**
     * Expiry month of a card of a CardPayment instance
     * @return the month of the card's expiration date
     */
    public int getExpiryMonth() {
        return 0;
    }

    /**
     * Expiry year of a card of a CardPayment instance
     * @return the year of the card's expiration date
     */
    public int getExpiryYear() {
        return 0;
    }

    /**
     * Account ID of an ElectronicPayment instance
     * @return the long representation of the account ID
     */
    public long getAccountId() {
        return 0;
    }

    /**
     * Email address of an ElectronicPayment instance
     * @return the String representation of the email
     */
    public String getEmailAddress() {
        return null;
    }

    /**
     * Bank balance of a bank account linked to a DebitPayment
     * @return the double representation of the bank balance
     */
    public double getBankBalance() {
        return 0;
    }

    /**
     * Monthly income of the individual linked to a DebitPayment
     * @return the double representation of the monthly income
     */
    public double getMonthlyIncome() {
        return 0;
    }

    /**
     * Credit limit of a card linked to a CreditPayment
     * @return the double representation of the credit limit
     */
    public double getCreditLimit() {
        return 0;
    }

    /**
     * Outstanding balance of a card linked to a CreditPayment
     * @return the double representation of the outstanding balance
     */
    public double getCardBalance() {
        return 0;
    }

    /**
     * Interest rate of the CreditPayment
     * @return the double representation of the interest rate
     */
    public double getInterestRate() {
        return 0;
    }

    /**
     * Phone number linked to a MobilePayment
     * @return the String representation of the phone number
     */
    public String getPhoneNumber() {
        return null;
    }

    /**
     * From 1 to 10 stars, the star rating of the user of the MobilePayment
     * @return the integer representation of the star rating
     */
    public int getStarRating() {
        return 0;
    }

    /**
     * The digital wallet ID linked to the DigitalWalletPayment
     * @return the String representation of the wallet ID
     */
    public String getWalletId() {
        return null;
    }

    /**
     * The number of transactions made by the digital wallet
     * of the DigitalWalletPayment
     * @return the integer representation of the number of transactions
     */
    public int getNumTransactions() {
        return 0;
    }

    /**
     * The risk associated with an instance of a CardPayment
     * @return the risk represented as a double
     */
    public double calculateCardRisk() {
        return 0;
    }

    /**
     * The risk associated with an instance of an ElectronicPayment
     * @return the risk represented as a double
     */
    public double calculateDigitalRisk() {
        return 0;
    }
}
