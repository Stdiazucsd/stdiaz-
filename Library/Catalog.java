///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              Catalog
// Files:              Catalog.java
// Quarter:            CSE11 Spring 2025
//
// Author:             Steve Diaz
// Email:              stdiaz@ucsd.edu
// Instructor's Name:  Ben Ochoa 
//
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * Catalog class maintains a sorted list of Book objects by ISBN number.
 * It supports adding, removing, and searching books within a dynamically 
 * resizing array.
 *
 * Bugs: None known
 *
 * @author Steve Diaz
 */
public class Catalog {
    private int capacity;
    private int size;
    private Book[] catalog;

    private static final int INITIAL_CAPACITY = 10;
    private static final int RESIZE_FACTOR = 2;
    private static final int NOT_FOUND = -1;

/**
 * Constructs a Catalog with default initial capacity.
 */
    public Catalog() {
        this.capacity = INITIAL_CAPACITY;
        this.catalog = new Book[this.capacity];
        this.size = 0;

    }
/**
 * Constructs a Catalog with specified initial capacity.
 *
 * @param capacity Initial capacity for the catalog array
 */
    public Catalog(int capacity) {
       if ( capacity < 0){
        this.capacity = INITIAL_CAPACITY;
       }
       
       else {
         this.capacity = capacity;
       }

       this.catalog = new Book[this.capacity];
       this.size = 0;
    }   

/**
 * Ensures the internal array has enough capacity to store new books.
 * Doubles the capacity if needed.
 */
    private void ensureCapacity() {

        if(this.size >= this.capacity){

            int newCap = this.capacity * RESIZE_FACTOR;
            Book[] newCatalog = new Book[newCap];

            for (int i = 0; i < this.size; i++){
                newCatalog[i] = this.catalog[i];
            }

            this.catalog = newCatalog;
            this.capacity = newCap;
        }
        
    }

   /**
 * Finds the index of a book by its ISBN number.
 *
 * @param isbn The ISBN number to search for
 * @return The index of the book in the catalog array, or -1 if not found
 */ 
    private int findBookIndexByIsbn(long isbn) {
        
        for (int i = 0; i < this.size; i++) {
            if (this.catalog[i].getIsbn() == isbn) {
                return i;
            }
        }
        return NOT_FOUND;
    }
/**
 * Adds a new book to the catalog in sorted order by ISBN.
 * If a book with the same ISBN already exists, the book is not added.
 *
 * @param newBook The Book object to be added
 */
    public void addBook(Book newBook) {
        long newIsbn = newBook.getIsbn();
        for ( int i = 0; i< size; i++ ){
           if (this.catalog[i].getIsbn() == newIsbn){
           return; // dup not found 
            }
        }

        // make sure there is room
            ensureCapacity();
        

        int insertIndex = 0;
        while (insertIndex < this.size && this.catalog[insertIndex]
            .getIsbn() < newIsbn){
                insertIndex++;
            }

        for (int i = this.size; i > insertIndex; i--){
            this.catalog[i] = this.catalog[i-1];
        }

        this.catalog[insertIndex] = newBook;

        this.size++;

    }
/**
 * Removes a book from the catalog by ISBN.
 *
 * @param bookToRemove The Book object to be removed
 */
    public void removeBook(Book bookToRemove) {
    long isbnToRemove = bookToRemove.getIsbn();
    int removeIndex = findBookIndexByIsbn(isbnToRemove);

    if (removeIndex == NOT_FOUND) {
        return; // Book not found
    }

    // Shift elements left to overwrite the removed book
    for (int i = removeIndex; i < this.size - 1; i++) {
        this.catalog[i] = this.catalog[i + 1];
    }

    // Clear the last duplicate reference
    this.catalog[this.size - 1] = null;

    this.size--;
    }

    /**
 * Returns the book in the catalog that matches the given ISBN.
 *
 * @param isbn The ISBN number of the book to return
 * @return The Book object if found, otherwise null
 */
    public Book returnBookByISBN(long isbn) {
    int index = findBookIndexByIsbn(isbn);

    if (index != NOT_FOUND) {
        return this.catalog[index];
    }

    return null;

    }

    /**
 * Returns the number of books currently in the catalog.
 *
 * @return The current size of the catalog
 */
    public int getSize() {
        return this.size;
    }

    /**
 * Returns the current capacity of the catalog array.
 *
 * @return The capacity of the catalog
 */
    public int getCapacity() {
        return this.capacity;
    }

    /**
 * Returns a copy of all books currently stored in the catalog.
 *
 * @return An array of Book objects currently in the catalog
 */
    public Book[] getAllBooks() {
        Book [] books = new Book[this.size];

        for(int i = 0; i < this.size; i++){
            books[i] = this.catalog[i];
        }

        return books;
    }

    /**
 * Unit tests for Catalog class methods.
 *
 * @return true if all tests pass, false otherwise
 */
    @SuppressWarnings("checkstyle:MagicNumber") // DO NOT CHANGE THIS LINE!!!
    public static boolean unitTests() {
        boolean passed = true;

        Catalog cat1 = new Catalog();
        Book bookToAdd = new Book("Title2", "Author2",
         9876543210987L);
        cat1.addBook(bookToAdd);
        if (cat1.getSize() != 1) {
            System.out.println("Catalog Test 1 FAILED: Add book size check");
            passed = false;
        }

    // Test 1
        Catalog c1 = new Catalog();
        Book b1 = new Book("A", "X", 1);
        Book b2 = new Book("B", "Y", 2);
        Book b3 = new Book("C", "Z", 3);
        c1.addBook(b1); c1.addBook(b2); c1.addBook(b3);
        c1.removeBook(b1); c1.removeBook(b2); c1.removeBook(b3);
        if (c1.getSize() != 0) { System.out.
            println("Catalog Test 1 FAILED"); passed = false; }

        // Test 2
        Catalog c2 = new Catalog(2);
        Book b4 = new Book("D", "M", 4);
        Book b5 = new Book("E", "N", 5);
        Book b6 = new Book("F", "O", 6);
        c2.addBook(b6); c2.addBook(b5); c2.addBook(b4);
        c2.removeBook(b5); c2.removeBook(b6); c2.removeBook(b4);
        if (c2.getSize() != 0) { System.out.
            println("Catalog Test 2 FAILED"); passed = false; }

        // Test 3
        Catalog c3 = new Catalog();
        Book b7 = new Book("G", "A", 7);
        Book b8 = new Book("H", "B", 8);
        Book b9 = new Book("I", "C", 9);
        c3.addBook(b7); c3.addBook(b8); c3.addBook(b9);
        if (c3.returnBookByISBN(8) == null) { System.out.
            println("Catalog Test 3 FAILED"); passed = false; }

        return passed;
    }

/**
 * Main method to run unit tests for the Catalog class.
 *
 * @param args Command-line arguments (not used)
 */
    public static void main(String[] args) {
        if (unitTests()) {
            System.out.println("Catalog: All unit tests passed.\n");
        } else {
            System.out.println("Catalog: ERROR: Failed test.\n");
            return;
        }
    }
}