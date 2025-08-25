///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              Book
// Files:             Book.java
// Quarter:            CSE11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa 
//
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class represents a Book with a title, author, ISBN, and checkout status
 * It provides methods for accessing and modifying book details.
 *
 * Bugs: None known
 *
 * @author Your Name
 */
public class Book {

    private String title;
    private String author;
    private long isbn;
    private String borrowerID;
    private boolean isCheckedOut;
    private static final long DEFAULT_TEST_ISBN = 1234567890L;
    private static final long NEW_TEST_ISBN = 9876543210L;

    

    /**
     * Constructs a default Book with null fields and unchecked status.
     */
    public Book(){
        this.title = null;
        this.author = null;
        this.isbn = 0;
        this.borrowerID = null;
        this.isCheckedOut = false;
        

    }
    /**
     * Constructs a Book with a title, author, and ISBN.
     *
     * @param title the book's title
     * @param author the book's author
     * @param isbn the book's ISBN
     */
    public Book(String title, String author, long isbn){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.borrowerID = null;
        this.isCheckedOut = false;

    }
    /**
     * Returns the book's title.
     *
     * @return the title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Returns the book's author.
     *
     * @return the author
     */
    public String getAuthor(){
        return this.author;
    }
    /**
     * Returns the book's ISBN.
     *
     * @return the ISBN
     */
    public long getIsbn(){
        return this.isbn;
    }
    /**
     * Returns whether the book is checked out.
     *
     * @return true if checked out, false otherwise
     */
    public boolean isCheckedOut(){
        return this.isCheckedOut;
    }
    /**
     * Returns the borrower's ID.
     *
     * @return the borrower ID or null if not checked out
     */
    public String getBorrowerID(){
        return this.borrowerID;
    }

     /**
     * Sets the book's title.
     *
     * @param title the new title
     */
    public void setTitle(String title){
        this.title = title;
    }
    /**
     * Sets the book's author.
     *
     * @param author the new author
     */
    public void setAuthor(String author){
        this.author = author;
    }
    /**
     * Sets the book's ISBN.
     *
     * @param isbn the new ISBN
     */
    public void setIsbn(long isbn){
        this.isbn = isbn;
    }
    /**
     * Sets the borrower's ID.
     *
     * @param borrowerID the ID of the borrower
     */
    public void setBorrowerID(String borrowerID){
        this.borrowerID = borrowerID;
    }
   
    /**
     * Sets the checked-out status of the book.
     *
     * @param isCheckedOut true to check out, false to check in
     */
    public void setIsCheckedOut(boolean isCheckedOut){
        this.isCheckedOut = isCheckedOut;
    }

   /**
     * Runs unit tests on the Book class.
     *
     * @return true if all tests pass
     */ 
  public static boolean unitTests() {
        boolean passed = true;

        // Test 1: Default constructor
        Book b1 = new Book();
        if (b1.getTitle() != null || b1.getAuthor() != null ||
             b1.getIsbn() != 0) {
            System.out.println("Book Test 1 FAILED: Default constructor");
            passed = false;
        }

        // Test 2: Parameterized constructor and getters
        Book b2 = new Book("TestTitle", "TestAuthor",
             DEFAULT_TEST_ISBN);
        if (!b2.getTitle().equals("TestTitle") || !b2.getAuthor().
        equals("TestAuthor") || b2.getIsbn() != DEFAULT_TEST_ISBN){
            System.out.
            println("Book Test 2 FAILED: Param constructor or getters");
            passed = false;
        }

        // Test 3: Setters
        b2.setTitle("NewTitle");
        b2.setAuthor("NewAuthor");
        b2.setIsbn(NEW_TEST_ISBN);
        if (!b2.getTitle().equals("NewTitle") || !b2.getAuthor().
        equals("NewAuthor") || b2.getIsbn() != NEW_TEST_ISBN) {
            System.out.println("Book Test 3 FAILED: Setters");
            passed = false;
        }

        // Test 4: Borrower ID and isCheckedOut
        b2.setBorrowerID("user123");
        b2.setIsCheckedOut(true);
        if (!b2.getBorrowerID().equals("user123") || 
        !b2.isCheckedOut()) {
            System.out.println("Book Test 4 FAILED: Checkout data");
            passed = false;
        }

        return passed;
    }


     /**
     * Entry point for running Book unit tests.
     *
     * @param args unused
     */
    public static void main(String[] args) {
    if (unitTests()) {
        System.out.println("Book: All unit tests passed.");
    } else {
        System.out.println("Book: ERROR: Failed test.");
    }
}

    
    
}
