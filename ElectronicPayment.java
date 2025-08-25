// Title:              PA6
// Files:            Electronic Payment.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
/**
 * The ElectronicPayment class represents a payment made through an
 *  electronic account.
 * It includes electronic-specific details such as account ID and email address.
 * This class extends the Payment superclass.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class ElectronicPayment extends Payment {
    private long accountId;
    private String emailAddress;
    private static final String HIGH_LEVEL_TYPE = "ElectronicPayment";
    private static final String TYPE = "Untyped ElectronicPayment";

    /**
     * No-arg constructor of the ElectronicPayment class
     */
    public ElectronicPayment() {}

    /**
 * Constructs an ElectronicPayment with the specified details.
 *
 * @param transactionName the name of the transaction
 * @param amount the amount of the payment
 * @param accountId the account ID
 * @param emailAddress the email address for the payment
 */
    public ElectronicPayment(String transactionName, double amount,
                             long accountId, String emailAddress) {
        super ( transactionName, amount);
        this.accountId = accountId;
        this.emailAddress = emailAddress;
    }

    /**
 * Gets the account ID associated with this payment.
 * @return the account ID
 */
    @Override
    public long getAccountId() {
        return this.accountId;
    }

    /**
 * Gets the email address for this payment.
 * @return the email address
 */
    @Override
    public String getEmailAddress() {
        return this.emailAddress;
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
 * Gets the specific type of this payment.
 * @return the type
 */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
 * Compares this ElectronicPayment to another object for equality.
 * @param object the object to compare
 * @return true if all fields match, false otherwise
 */
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;
        if (!(object instanceof ElectronicPayment)) return false;
        ElectronicPayment other = (ElectronicPayment) object;
        return this.accountId == other.accountId &&
           this.emailAddress.equals(other.emailAddress);
    }

    /**
 * Returns a string representation of this ElectronicPayment, including the
 * transaction name, account ID, and email address.
 *
 * @return a formatted string describing the electronic payment
 */
    @Override
    public String toString() {
        return "ElectronicPayment (" + getTransactionName() +
            ") accountId: " + getAccountId() + ", emailAddress: " +
                getEmailAddress();
    }

}
