// Title:              PA6
// Files:            DebitPayment.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
/**
 * The DebitPayment class represents a payment made using a debit card.
 * It includes financial details such as bank balance and monthly income,
 * and calculates card risk based on these values.
 * This class extends CardPayment.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class DebitPayment extends CardPayment {
    private double bankBalance;
    private double monthlyIncome;
    private static final String TYPE = "DebitPayment";

    /**
     * No-arg constructor for the DebitPayment class
     */
    public DebitPayment() {}

    /**
 * Constructs a DebitPayment with the specified details.
 *
 * @param transactionName the name of the transaction
 * @param amount the amount of the payment
 * @param cardNumber the card number
 * @param expiryMonth the expiration month
 * @param expiryYear the expiration year
 * @param bankBalance the bank balance for the debit account
 * @param monthlyIncome the monthly income of the user
 */
    public DebitPayment(String transactionName, double amount,
                                long cardNumber, int expiryMonth,
                                int expiryYear, double bankBalance,
                                double monthlyIncome) {
        super(transactionName, amount, cardNumber, expiryMonth, expiryYear);
        this.bankBalance = bankBalance;
        this.monthlyIncome = monthlyIncome;
    }

    /**
 * Gets the bank balance associated with this debit payment.
 * @return the bank balance
 */
    @Override
    public double getBankBalance() {
        return this.bankBalance;
    }

    /**
 * Gets the monthly income associated with this debit payment.
 * @return the monthly income
 */
    public double getMonthlyIncome() {
        return this.monthlyIncome;
    }

    /**
 * Gets the specific type of this debit payment.
 * @return the type string "DebitPayment"
 */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
 * Compares this DebitPayment to another object for equality.
 * @param object the object to compare
 * @return true if all fields match, false otherwise
 */
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;
        if (!(object instanceof DebitPayment)) return false;
        DebitPayment other = (DebitPayment) object;
        return this.bankBalance == other.bankBalance &&
           this.monthlyIncome == other.monthlyIncome;
    }

    /**
 * Calculates the card risk based on the impact on balance relative to income.
 * @return the calculated risk value
 */
    @Override
    public double calculateCardRisk() {
        return ( getAmount() / bankBalance) + (getAmount() / monthlyIncome);
    }

  /**
 * Returns a string representation of this DebitPayment, including the
 * transaction name, bank balance, and monthly income.
 *
 * @return a formatted string describing the debit payment
 */
    @Override
    public String toString() {
        return "DebitPayment (" + getTransactionName() +
            ") bankBalance: " + getBankBalance() +
                ", monthlyIncome: " + getMonthlyIncome();
    }
}
