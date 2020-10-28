//created by Yoav Shapira
//26.10.20

/**
 * This class represents a library, which hold a collection of books.
 * Patrons can register at the library to be able to check out books, if a copy of the requested book is available.
 */

public class Library {

    /** Array of books in the library. It's size is the maximum capacity that given from the constructor. */
    Book[] booksList;

    /** Array of patrons that signed to the library. It's size is the maximum capacity given to the constructor. */
    Patron[] patronsList;

    /** Maximum amount of books that patron can holds at the same time. */
    final int maximumBorroedAllowed;

    /** Current amount of books exist in the library. */
    private int currentBooksAmount;

    /** Current amount of patrons registered in the library. */
    private int currentPatronsAmount;

    /** Array holds the number of borrowed books for each patron. */
    private int[] borrowedBooksForPatron;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given parameters.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a single patron to borrow at a time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        booksList = new Book[maxBookCapacity];
        patronsList = new Patron[maxPatronCapacity];
        maximumBorroedAllowed = maxBorrowedBooks;
        currentBooksAmount = 0;
        currentPatronsAmount = 0;
        borrowedBooksForPatron = new int[maxPatronCapacity];
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library, if there is place available, and if isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        for (int i = 0; i < currentBooksAmount; i++) {
            if (booksList[i].equals(book)) {
                return i;
            }
        }
        if (currentBooksAmount < booksList.length) {        //this condition handles when (booksList.length = 0)
            booksList[currentBooksAmount] = book;
            currentBooksAmount++;
            return (currentBooksAmount - 1);
        }
        return -1;
    }

    /**
     * Checks if a given book is exists in the library, by it's ID number.
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return bookId < currentBooksAmount;
    }

    /**
     * Returns the ID number of a given book, if it's in the library.
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < currentBooksAmount; i++) {
            if (booksList[i].equals(book)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Check if a given book it's available for borrow, by it's ID number.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if ( !isBookIdValid(bookId) ) {
            return false;
        }
        return ( booksList[bookId].getCurrentBorrowerId() == -1 );
    }

    /**
     * Add a given patron to the library, if any spot is available, and determines it's own ID number in the library.
     * If patron is already in the library, return it's ID.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully registered
     * or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        for (int i = 0; i < currentPatronsAmount; i++) {
            if ( patronsList[i].equals(patron) ) {
                return i;
            }
        }
        if (currentPatronsAmount < patronsList.length ) {       //this condition handles when (patronList.length = 0)
            patronsList[currentPatronsAmount] = patron;
            borrowedBooksForPatron[currentBooksAmount] = 0;
            currentPatronsAmount++;
            return (currentPatronsAmount - 1);
        }
        return -1;
    }

    /**
     * Check if given ID number is compatible to any patron in the library.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        return patronId < currentPatronsAmount;
    }

    /**
     * Gets the ID number of a given patron, if it exists in the library.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < currentPatronsAmount; i++) {
            if ( patronsList[i].equals(patron) ) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is
     * available, the given patron isn't already borrowing the maximal number of books allowed, and if the patron will
     * enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if ( isBookAvailable(bookId) && isPatronIdValid(patronId) ) {
            if ( patronsList[patronId].willEnjoyBook(booksList[bookId]) ) {     //FIX THE ISSUE WITH THE MAX BORROW
                 booksList[bookId].setBorrowerId(patronId);
                 borrowedBooksForPatron[patronId]++;
                return true;
            }
        }
        return false;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId) {
        if ( isBookIdValid(bookId) ) {
            borrowedBooksForPatron[booksList[bookId].getCurrentBorrowerId()]--;
            booksList[bookId].returnBook();
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he will enjoy,
     * if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        Book preferedBook = null;
        if ( isPatronIdValid(patronId) ) {
            if ( borrowedBooksForPatron[patronId] > maximumBorroedAllowed ) {
                return null;
            }
            int maxEnjoymentValue = 0;
            for (int i = 0; i < currentBooksAmount; i++) {
                if ( patronsList[patronId].willEnjoyBook(booksList[i]) &&
                     patronsList[patronId].getBookScore(booksList[i]) > maxEnjoymentValue ) {
                    preferedBook = booksList[i];
                    maxEnjoymentValue = patronsList[patronId].getBookScore(booksList[i]);
                }
            }
        }
        return preferedBook;
    }
}