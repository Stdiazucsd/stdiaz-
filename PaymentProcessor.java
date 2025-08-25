// Title:              PA6
// Files:           PaymentProcessor.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
import java.util.ArrayList;

/**
 * The PaymentProcessor class manages a list of payments and provides
 * functionality to add, retrieve, compare, update, and validate payments.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class PaymentProcessor {
    private ArrayList<Payment> paymentList;

    private static final int MAX_EXPIRY_MONTH = 12;
    private static final int MIN_EXPIRY_MONTH_2025 = 7;
    private static final int MIN_EXPIRY_YEAR = 2025;
    private static final int MAX_STARS = 10;
    private static final String UCSD_EMAIL = "@ucsd.edu";
    private static final String CARD_PAYMENT = "CardPayment";
    private static final String ELECTRONIC_PAYMENT = "ElectronicPayment";
    private static final String DEBIT_PAYMENT = "DebitPayment";
    private static final String CREDIT_PAYMENT = "CreditPayment";
    private static final String MOBILE_PAYMENT = "MobilePayment";
    private static final String DIGITAL_WALLET_PAYMENT = "DigitalWalletPayment";

    /**
 * Constructs a PaymentProcessor with an empty list of payments.
 */
    public PaymentProcessor() {
        this.paymentList = new ArrayList<>();
    }

    /**
 * Adds a single payment to the list.
 *
 * @param payment the payment to add
 */
    public void addToPaymentList(Payment payment) {
        paymentList.add(payment);
    }

    /**
 * Adds multiple payments to the list.
 *
 * @param payments an array of payments to add
 */
    public void addToPaymentList(Payment[] payments) {
        for (Payment p : payments) {
        paymentList.add(p);
        }
    }

    /**
 * Checks if a payment with the given transaction name exists in the list.
 *
 * @param paymentTransactionName the name of the payment to search for
 * @return true if found, false otherwise
 */
    public boolean hasPayment(String paymentTransactionName) {
        for (Payment p : paymentList) {
        if (p.getTransactionName().equals(paymentTransactionName)) {
            return true;
        }
    }
    return false;
    }

    /**
 * Retrieves and removes the first payment with the specified transaction name.
 *
 * @param paymentTransactionName the name of the payment to retrieve
 * @return the Payment if found, null otherwise
 */
    public Payment getAndRemovePayment(String paymentTransactionName) {
        for (int i = 0; i < paymentList.size(); i++) {
        if (paymentList.get(i).getTransactionName().
            equals(paymentTransactionName)) {
            return paymentList.remove(i);
        }
    }
    return null;
    }

    /**
 * Compares the risk of two payments.
 *
 * @param paymentOne the first payment
 * @param paymentTwo the second payment
 * @return -1 if paymentOne has less risk, 1 if more, 0 if equal
 */
    public static int compareRisk(Payment paymentOne, Payment paymentTwo) {
        double riskOne = 0.0;
        double riskTwo = 0.0;

        if (paymentOne.getHighLevelType().equals(CARD_PAYMENT)) {
            riskOne = ((CardPayment) paymentOne).calculateCardRisk();
        }   else if (paymentOne.getHighLevelType().equals(ELECTRONIC_PAYMENT)){
             riskOne = ((ElectronicPayment) paymentOne).calculateDigitalRisk();
        }

        if (paymentTwo.getHighLevelType().equals(CARD_PAYMENT)) {
            riskTwo = ((CardPayment) paymentTwo).calculateCardRisk();
        } else if (paymentTwo.getHighLevelType().equals(ELECTRONIC_PAYMENT)) {
            riskTwo = ((ElectronicPayment) paymentTwo).calculateDigitalRisk();
        }

    return Double.compare(riskOne, riskTwo);
    }

    /**
 * Applies a price surge to a random payment in the list.
 *
 * @param increaseRate the multiplier to apply
 * @return the index of the payment updated
 */
    public int applyPaymentSurge(double increaseRate) {
        if (paymentList.isEmpty()) return -1;
    int index = (int) (Math.random() * paymentList.size());
    Payment p = paymentList.get(index);
    if (increaseRate >= 1.0) {
        p.setAmount(p.getAmount() * increaseRate);
    }
    return index;
    }

    /**
 * Validates the given payment for processing.
 *
 * @param payment the payment to process
 * @return true if valid, false otherwise
 */
    public static boolean processPayment(Payment payment) {
        if (payment.getAmount() < 0) return false;

    if (payment.getHighLevelType().equals(CARD_PAYMENT)) {
        CardPayment cp = (CardPayment) payment;
        int year = cp.getExpiryYear();
        int month = cp.getExpiryMonth();

        if (year < MIN_EXPIRY_YEAR) return false;
        if (year == MIN_EXPIRY_YEAR && (month < MIN_EXPIRY_MONTH_2025 ||
         month > MAX_EXPIRY_MONTH)) return false;
        if (year > MIN_EXPIRY_YEAR && (month < 1 || month > MAX_EXPIRY_MONTH))
             return false;

        if (payment.getType().equals(DEBIT_PAYMENT)) {
            DebitPayment dp = (DebitPayment) cp;
            return dp.getBankBalance() >= 0 && dp.getMonthlyIncome() >= 0;
        } else if (payment.getType().equals(CREDIT_PAYMENT)) {
            CreditPayment cp2 = (CreditPayment) cp;
            return cp2.getCardBalance() >= 0 &&
                   cp2.getCreditLimit() >= 0 &&
                   cp2.getCreditLimit() >= cp2.getCardBalance();
        }
    }

    if (payment.getHighLevelType().equals(ELECTRONIC_PAYMENT)) {
        ElectronicPayment ep = (ElectronicPayment) payment;
        String email = ep.getEmailAddress();
        int index = email.indexOf(UCSD_EMAIL);
        if (index == -1 || email.indexOf(UCSD_EMAIL, index + 1) != -1) 
            return false;

        if (payment.getType().equals(MOBILE_PAYMENT)) {
            MobilePayment mp = (MobilePayment) ep;
            int stars = mp.getStarRating();
            return stars >= 0 && stars <= MAX_STARS;
        } else if (payment.getType().equals(DIGITAL_WALLET_PAYMENT)) {
            DigitalWalletPayment dp = (DigitalWalletPayment) ep;
            return dp.getNumTransactions() >= 0;
        }
    }

    return true;
    }

    /**
     * The getter method of the paymentList member variable
     * @return the paymentList of the PaymentProcessor instance
     */
    public ArrayList<Payment> getPaymentList() {
        return this.paymentList;
    }
}
