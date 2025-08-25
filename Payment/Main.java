// Title:              PA6
// Files:           Assignment6.java
// Quarter:            CSE 11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa
///////////////////////////////////////////////////////////////////////////////
//Online Sources: code Help, helped me understand my errors
import java.util.ArrayList;

/**
 * Assignment6 serves as the test driver for Payment and PaymentProcessor
 *  functionality.
 * It contains helper methods to print payment data and test core
 *  functionality.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class Assignment6 {

    /**
     * Print the payment array in a readable format. Optional to use
     * @param paymentArr An array of Payment instances
     */
    public static void printPaymentArray(Payment[] paymentArr) {
        System.out.println('[');
        for (int i = 0; i < paymentArr.length; i++) {
            System.out.print("  " + paymentArr[i]);
            System.out.println(',');
        }
        System.out.println(']');
    }

    /**
     * Print the payment arraylist in a readable format. Optional to use
     * @param paymentArr An arraylist of Payment instances
     */
    public static void printPaymentArray(ArrayList<Payment> paymentArr) {
        System.out.println('[');
        for (int i = 0; i < paymentArr.size(); i++) {
            System.out.print("  " + paymentArr.get(i));
            System.out.println(',');
        }
        System.out.println(']');
    }

    /**
 * Runs unit tests for the PaymentProcessor and various Payment subclasses.
 * Includes tests for applyPaymentSurge(), compareRisk(), and processPayment().
 *
 * @return true if all tests pass, false otherwise
 */
    @SuppressWarnings("checkstyle:MagicNumber")
    public static boolean unitTests() {
         // SETUP
    PaymentProcessor proc = new PaymentProcessor();

    // Add payments to a PaymentProcessor object
    MobilePayment m1 = new MobilePayment("Dinner", 
    10.0, 0,
                                    "sumu@ucsd.edu", 
                                    "4445556666", 4);
    proc.addToPaymentList(m1);
    Payment[] paymentsToAdd = {
        new DebitPayment("Groceries", 42.5, 
        4444555566667777L,
                            12, 2025, 
                            1200, 100),
        new MobilePayment("Forex", 3.0, 1,
                                    "sumu@ucsd.edu", 
                                    "s@nyse.com", 10),
        new DebitPayment("Pharmacy", 4.77, 
        1111222233334444L,
                            1, 2027, 
                            4000, 10000)
    };
    proc.addToPaymentList(paymentsToAdd);

    // TEST CASE 1: applyPaymentSurge() - provided
    double[] originalPrices = new double[proc.getPaymentList().size()];
    for (int i = 0; i < proc.getPaymentList().size(); i++) {
        originalPrices[i] = proc.getPaymentList().get(i).getAmount();
    }

    double increaseRate = 1.5;
    int increaseIndex = proc.applyPaymentSurge(increaseRate);

    for (int i = 0; i < proc.getPaymentList().size(); i++) {
        Payment payment = proc.getPaymentList().get(i);
        if (i != increaseIndex || increaseRate < 1) {
            if (payment.getAmount() != originalPrices[i]) {
    System.
    out. println("applyPaymentSurge 1 - Payment unexpectedly changed at index:" 
    + i);
                System.out.println(proc.getPaymentList());
                return false;
            }
        } else {
            double actualPrice = originalPrices[i] * increaseRate;
            if (payment.getAmount() != actualPrice) {
                System.out.println
    ("applyPaymentSurge 1 - Payment does not have expected increased amount");
                System.out.println(payment.getAmount());
                System.out.println(actualPrice);
                System.out.println(proc.getPaymentList());
                return false;
            }
        }
    }

    // TEST CASE 2: compareRisk() - Debit vs Mobile
    Payment debit = new DebitPayment("A", 50.0, 
    1234567812345678L, 12, 2026, 
    1000, 1000);
    Payment mobile = new MobilePayment("B", 50.0, 
    2, "b@ucsd.edu", "5551234", 10);
    int result = PaymentProcessor.compareRisk(debit, mobile);
    if (result != -1) {
    System.out.println("compareRisk - Expected -1 but got: " + result);
    return false;
    }

    // TEST CASE 3: applyPaymentSurge() - rate < 1 should not change price
    PaymentProcessor smallSurgeProc = new PaymentProcessor();
    Payment testPayment = new DebitPayment("Bookstore", 
    20.0, 1234L, 10, 2026, 
    500, 1000);
    smallSurgeProc.addToPaymentList(testPayment);
    double original = testPayment.getAmount();
    int idx = smallSurgeProc.applyPaymentSurge(0.5);
    if (smallSurgeProc.getPaymentList().get(idx).getAmount() != original) {
        System.out.
println("applyPaymentSurge 2 - Amount incorrectly changed with low rate");
        return false;
    }

    // TEST CASE 4: processPayment() - invalid email format
    Payment badEmail = new ElectronicPayment("Online", 
    15.0, 999, "bad@ucsd.edu@ucsd.edu");
    if (PaymentProcessor.processPayment(badEmail)) {
        System.out.println
        ("processPayment - Invalid email passed validation");
        return false;
    }

    return true;
}


    /**
 * Main method to execute unit tests for Assignment6.
 *
 * @param args command-line arguments (not used)
 */
    public static void main(String[] args) {
        // Perform unitTests
        if(unitTests()) {
            System.out.println("All unit tests passed.\n");
        } else {
            System.out.println("Failed test.\n");
            return;
        }

        // Don't need to write code in main!
    }
}
