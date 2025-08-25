///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              LibaryManager
// Files:              LibaryManager.java
// Quarter:            CSE11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa 
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * LibraryManager manages a catalog of books within a library.
 * It allows adding books, checking books in and out, and finding 
 * available books by author.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class LibraryManager {

    private String libraryName;
    private Catalog catalog;
    private static final String DEFAULT_NAME = "Geisel";

    /**
 * Constructs a LibraryManager with default name and catalog capacity.
 */
    public LibraryManager() {

        this.libraryName = DEFAULT_NAME;
        this.catalog = new Catalog();

    }

    /**
 * Constructs a LibraryManager with specified name and initial catalog capacity
 *
 * @param libraryName The name of the library
 * @param initialCapacity The initial capacity for the catalog
 */
    public LibraryManager(String libraryName, int initialCapacity) {
        this.libraryName = libraryName;
        this.catalog = new Catalog(initialCapacity);
    }

    /**
 * Adds a new book to the library catalog.
 *
 * @param newLibraryBook The Book object to add to the catalog
 */
    public void addBookToLibrary(Book newLibraryBook) {
        this.catalog.addBook(newLibraryBook);
    }

    /**
 * Checks out a book from the catalog to a borrower if it is not already
 * checked out.
 * @param isbn The ISBN number of the book to check out
 * @param borrowerID The ID of the borrower
 * @return true if the checkout is successful, false otherwise
 */
    public boolean checkOutBook(long isbn, String borrowerID) {
        Book book = this.catalog.returnBookByISBN(isbn);

        if(book != null && !book.isCheckedOut()){
            book.setBorrowerID(borrowerID);
            book.setIsCheckedOut(true);
            return true;
        }

        return false;
    }

    /**
 * Checks in a book to the catalog if it is currently checked out.
 *
 * @param isbn The ISBN number of the book to check in
 * @return true if the check-in is successful, false otherwise
 */
    public boolean checkInBook(long isbn) {
        Book book = this.catalog.returnBookByISBN(isbn);

        if(book != null && book.isCheckedOut()){
            book.setIsCheckedOut(false);
            book.setBorrowerID(null);
            return true;
        }

        return false;
    }

    /**
 * Finds all available books by a specific author that are not checked out.
 *
 * @param author The name of the author to search for
 * @return An array of available Book objects by the specified author
 */
    public Book[] findAvailableBooksByAuthor(String author) {
        Book [] allBooks = this.catalog.getAllBooks();
        int count = 0;

        for (int i = 0; i < allBooks.length; i++){
            if(allBooks[i].getAuthor().equalsIgnoreCase(author) &&
                !allBooks[i].isCheckedOut()){
                count ++;
                }
        }


        Book[] result = new Book[count];
        int index = 0;
        for ( int i = 0; i < allBooks.length; i++){
            if(allBooks[i].getAuthor().equalsIgnoreCase(author) && 
                !allBooks[i].isCheckedOut()){
                    result[index] = allBooks[i];
                    index ++;
                }
        }

        return result;
    }


    /**
 * Gets the total number of books that are currently checked out.
 *
 * @return The number of checked-out books
 */
    public int getCheckedOutCount() {
        Book [] allBooks = this.catalog.getAllBooks();
        int count = 0;

        for ( int i = 0; i < allBooks.length; i++){
            if ( allBooks[i].isCheckedOut()){
                count ++;
            }
        }

        return count;
    }

    /**
 * Unit tests for the LibraryManager class methods.
 *
 * @return true if all tests pass, false otherwise
 */
    @SuppressWarnings("checkstyle:MagicNumber") // DO NOT CHANGE THIS LINE!!!
    public static boolean unitTests() {
        boolean passed = true;

        LibraryManager lm1 = new LibraryManager();
        Book bookToAdd = new Book("Title3", "Author3"
            , 1122334455667L);
        lm1.addBookToLibrary(bookToAdd);
        if (lm1.getCheckedOutCount() != 0) {
         System.out
        .println("LibraryManager Test 1 FAILED: Add book checked out count");
            passed = false;
        }
    // Test 1
        LibraryManager lm2 = new LibraryManager();
        Book b1 = new Book("Title1", "Author1", 1001);
        lm2.addBookToLibrary(b1);
        if (!lm2.checkOutBook(1001, "U1")) { System.out.
                println("LM Test 1 FAIL"); passed = false; }
        if (!lm2.checkInBook(1001)) { System.out.
                println("LM Test 1 FAIL"); passed = false; }
        if (lm2.getCheckedOutCount() != 0) { System.out.
                println("LM Test 1 FAIL"); passed = false; }


        // test2 

       LibraryManager lm3 = new LibraryManager();
Book b4 = new Book("T4", "A4", 104);
Book b5 = new Book("T5", "A4", 105);
lm3.addBookToLibrary(b4);
lm3.addBookToLibrary(b5);
lm3.checkOutBook(105, "U3");

lm3.findAvailableBooksByAuthor("A4");

if (lm3.getCheckedOutCount() != 1) { System.out.
    println("LM Test 3 FAIL"); passed = false; }

        return passed;
    }

    /**
 * Main method to run unit tests for the LibraryManager class.
 *
 * @param args Command-line arguments (not used)
 */
    public static void main(String[] args) {
        if (unitTests()) {
            System.out.println("LibraryManager: All unit tests passed.\n");
        } else {
            System.out.println("LibraryManager: ERROR: Failed test.\n");
            return;
        }
    }
}