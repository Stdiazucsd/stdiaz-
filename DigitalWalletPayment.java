// Title:              PA6
// Files:            DigitalWalletPayment.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
/**
 * The DigitalWalletPayment class represent a payment made usingdigital wallet.
 * includes a wallet ID and number of past transactions to assess digital risk.
 * This class extends ElectronicPayment.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */

public class DigitalWalletPayment extends ElectronicPayment{
    private String walletId;
    private int numTransactions;
    private static final String TYPE = "DigitalWalletPayment";
    
    
    /**
 * Default constructor for DigitalWalletPayment with an empty body.
 */
    public DigitalWalletPayment() {
    // no-op
    }   

    /**
 * Constructs a DigitalWalletPayment with the specified details.
 *
 * @param transactionName the name of the transaction
 * @param amount the amount of the payment
 * @param accountId the account ID
 * @param emailAddress the email address
 * @param walletId the wallet ID
 * @param numTransactions the number of past transactions
 */
    public DigitalWalletPayment(String transactionName, double amount,
                            long accountId, String emailAddress,
                            String walletId, int numTransactions) {
    super(transactionName, amount, accountId, emailAddress);
    this.walletId = walletId;
    this.numTransactions = numTransactions;
    }

    /**
 * Gets the wallet ID associated with this digital wallet payment.
 * @return the wallet ID
 */
    public String getWalletId() {
        return this.walletId;
    }

    /**
    * Gets the number of previous transactions for the wallet.
    * @return the number of transactions
    */
    public int getNumTransactions() {
        return this.numTransactions;
    }

    /**
 * Gets the specific type of this digital wallet payment.
 * @return the string "DigitalWalletPayment"
 */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
 * Compares this DigitalWalletPayment to another object for equality.
 * @param object the object to compare
 * @return true if all fields match, false otherwise
 */

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;
        if (!(object instanceof DigitalWalletPayment)) return false;
        DigitalWalletPayment other = (DigitalWalletPayment) object;
        return this.walletId.equals(other.walletId) &&
           this.numTransactions == other.numTransactions;
    }

    /**
* Calculates the digital risk of this digital wallet payment based on
 * amount and number of past transactions.
 * @return the calculated risk
 */
    @Override
    public double calculateDigitalRisk() {
        return getAmount() / Math.log10(numTransactions);
    }

    /**
 * Returns a string representation of this DigitalWalletPayment, including the
 * transaction name, wallet ID, and number of transactions.
 *
 * @return a formatted string describing the digital wallet payment
 */
    @Override
    public String toString() {
        return "DigitalWalletPayment (" + getTransactionName() +
            ") walletId: " + getWalletId() +
            ", numTransactions: " + getNumTransactions();
    }









}
