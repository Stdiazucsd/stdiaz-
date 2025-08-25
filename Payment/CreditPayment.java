// Title:              PA6
// Files:            CreditPayment.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
/**
 * The CreditPayment class represents a payment made using a credit card.
 * It includes details such as credit limit, card balance, and interest rate,
 * and calculates card risk based on credit utilization.
 * This class extends CardPayment.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class CreditPayment extends CardPayment{


    private double creditLimit;
    private double cardBalance;
    private double interestRate;
    private static final String TYPE = "CreditPayment";


    /**
* Default constructor for CreditPayment with an empty body.
 */
    public CreditPayment() {
        // no-op
    }


    /**
 * Constructs a CreditPayment with the specified details.
 *
 * @param transactionName the transaction name
 * @param amount the amount of the payment
 * @param cardNumber the card number
 * @param expiryMonth the card expiry month
 * @param expiryYear the card expiry year
 * @param creditLimit the credit limit on the card
 * @param cardBalance the current card balance
 * @param interestRate the interest rate
 */
    public CreditPayment(String transactionName, double amount,
            long cardNumber, int expiryMonth, int expiryYear,
            double creditLimit, double cardBalance, double interestRate) {
    super(transactionName, amount, cardNumber, expiryMonth, expiryYear);
    this.creditLimit = creditLimit;
    this.cardBalance = cardBalance;
    this.interestRate = interestRate;
}

    /**
 * Gets the credit limit associated with this credit payment.
 * @return the credit limit
 */
    public double getCreditLimit() {
        return this.creditLimit;
    }

/**
 * Gets the current card balance.
 * @return the card balance
 */
    public double getCardBalance() {
        return this.cardBalance;
    }

/**
 * Gets the interest rate for the credit card.
 * @return the interest rate
 */
    public double getInterestRate() {
        return this.interestRate;
    }

    /**
 * Gets the specific type of this credit payment.
 * @return the string "CreditPayment"
 */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
 * Compares this CreditPayment to another object for equality.
 * @param object the object to compare
 * @return true if all fields match, false otherwise
 */
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;
        if (!(object instanceof CreditPayment)) return false;
        CreditPayment other = (CreditPayment) object;
        return this.creditLimit == other.creditLimit &&
           this.cardBalance == other.cardBalance &&
           this.interestRate == other.interestRate;
    }

    /**
 * Calculates credit card risk based on utilization and interest.
 * @return the calculated risk value
 */
    @Override
    public double calculateCardRisk() {
        return (cardBalance / creditLimit) * (1 + interestRate);
    }
    /**
 * Returns a string representation of this CreditPayment, including the
 * transaction name, credit limit, card balance, and interest rate.
 *
 * @return a formatted string describing the credit payment
 */
    @Override
    public String toString() {
        return "CreditPayment (" + getTransactionName() +
        ") creditLimit: " + getCreditLimit() +
        ", cardBalance: " + getCardBalance() +
        ", interestRate: " + getInterestRate();
    }


    
}
