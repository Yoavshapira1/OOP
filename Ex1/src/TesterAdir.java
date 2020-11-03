//written by Adir
import java.lang.reflect.Method;

public class TesterAdir {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BG = "\u001B[46m";

    public static void main(String[] args) {
        //general API structure check
        System.out.println("Checking if API methods exists in Book class...");
        checkingMethodExistsBook();
        System.out.println("Checking if API methods exists in Patron class...");
        checkingMethodExistsPatron();
        System.out.println("Checking if API methods exists in Library class...");
        checkingMethodExistsLibrary();

        System.out.println("Running Book basic tests...");
        BookSanityTests();
        System.out.println("Running Patron basic tests...");
        PatronBasicTests();
        System.out.println("Running Library basic tests...");
        LibraryBasicTests();
        LibraryBasicTests2();
        semiMsg("ALL TESTS PASSED!");
    }

    public static void LibraryBasicTests2() {
        int n = 5;
        Patron[] patrons = new Patron[n];
        Book[] books = new Book[n];
        int[] patronsID = new int[n];
        int[] booksID = new int[n];
        Library lib = new Library(n,n,n);
        for (int i = 0; i < n; i++) {
            patrons[i] = new Patron("a", "b", 1, 1, 1, 1);
            books[i] = new Book("a","b",2001,1,1,1);
            patronsID[i] = lib.registerPatronToLibrary(patrons[i]);
            booksID[i] = lib.addBookToLibrary(books[i]);
        }
        for(int i = 0; i < n; i++){
            if(patronsID[i] != lib.getPatronId(patrons[i])){
                writeError("ERROR: getPatronID: Incorrect patron's ID.");
            }
            if(booksID[i] != lib.getBookId(books[i])){
                writeError("ERROR: getPatronID: Incorrect patron's ID.");
            }
            if(!lib.isBookIdValid(booksID[i])){
                writeError("ERROR: isBookIdValid returned false, but ID is valid.");
            }
            if(!lib.isPatronIdValid(patronsID[i])){
                writeError("ERROR: isPatronIdValid returned false, but ID is valid.");
            }
        }
        if(lib.isBookIdValid(23123)) { //random id
            writeError("ERROR: isBookIdValid returned true for random id.");
        }
        if(lib.isPatronIdValid(23123)) { //random id
            writeError("ERROR: isPatronIdValid returned true for random id.");
        }
        writeSuccess("getBookId and getPatronId - VALID");
        writeSuccess("isBookIdValid and isPatronIdValid - VALID");
        for(int i = 0; i < n;i++){
            if(!lib.isBookAvailable(booksID[i])){
                writeError("ERROR: Available book labeled as borrowed.");
            }
            lib.borrowBook(booksID[i],patronsID[i]);
            if(lib.isBookAvailable(booksID[i])){
                writeError("ERROR: Borrowed book labeled as available.");
            }
        }
        boolean c = lib.isBookAvailable(-1);
        if(c){
            writeError("ERROR: isBookAvailable returned True for boolId=-1.");
        }
        //c = false
        c = true;
        c = lib.isBookAvailable(23123);//random id
        if(c){
            writeError("ERROR: isBookAvailable return True for random id.");
        }
        lib.returnBook(booksID[0]);
        lib.returnBook(booksID[1]);
        if(!lib.borrowBook(booksID[0],patronsID[1]) || !lib.borrowBook(booksID[1],patronsID[0])) {
            writeError("ERROR: Trying to borrow returned book failed.");
        }
        lib.returnBook(booksID[0]);
        lib.returnBook(booksID[0]); //double return, check if program collapse.
        lib.returnBook(-1); //check invalid id
        lib.returnBook(202020); //check valid id but not in the library.
        writeSuccess("returnBook - VALID");
        Library lib2 = new Library(4,2,2);
        Patron patron1 = new Patron("a","b",2,2,2,6);
        Patron patron2 = new Patron("a","b",3,4,3,1);
        Book b1 = new Book("t","a",2001,1,1,1);
        Book b2 = new Book("t","a",2003,1,2,1);
        Book b3 = new Book("t","a",2004,2,2,2);
        Book b4 = new Book("t","a",2004,2,2,2);
        lib2.addBookToLibrary(b1);
        lib2.addBookToLibrary(b2);
        lib2.addBookToLibrary(b3);
        lib2.addBookToLibrary(b4);
        lib2.registerPatronToLibrary(patron1);
        lib2.registerPatronToLibrary(patron2);
        lib2.borrowBook(lib2.getBookId(b2),lib2.getPatronId(patron1));
        if(lib2.suggestBookToPatron(lib2.getPatronId(patron1)) != b3){
            writeError("ERROR: Incorrect book returned from suggestBookToPatron.(1)");
        }
        lib2.borrowBook(lib2.getBookId(b3), lib2.getPatronId(patron2));
        if(lib2.suggestBookToPatron(lib2.getPatronId(patron1)) != b4){
            writeError("ERROR: Incorrect book returned from suggestBookToPatron.(2)");
        }
        lib2.borrowBook(lib2.getBookId(b4), lib2.getPatronId(patron1));
        //patron 1 with 2 books (b2,b4)
        //patron 2 with 1 book b3
        //patron 1 won't enjoy any other book, so should get null
        if(lib2.suggestBookToPatron(lib2.getPatronId(patron1)) != null){
            writeError("ERROR: Incorrect book returned from suggestBookToPatron.(3)");
        }
        //patron 2 will enjoy b1.
        if(lib2.suggestBookToPatron(lib2.getPatronId(patron2)) != b1){
            writeError("ERROR: Incorrect book returned from suggestBookToPatron.(4)");
        }
        writeSuccess("suggestBookToPatron - VALID");

    }

    public static void LibraryBasicTests() {
        Library lib = new Library(1, 1, 1);
        Patron p = new Patron("d", "d", 1, 1, 1, 20);
        Patron p2 = new Patron("dd", "dd", 2, 2, 2, 20);
        Book b = new Book("A", "A", 2000, 1, 1, 1);
        Book b2 = new Book("B", "B", 2000, 2, 2, 2);
        int bookId = lib.addBookToLibrary(b);
        if (bookId < 0) {
            writeError("ERROR: Adding a book to the library returned negative id.");
        }
        int bookId2 = lib.addBookToLibrary(b);
        if (bookId != bookId2) {
            writeError("ERROR: Adding the same book object succeeded.");
        }
        int shouldNotBeIn = lib.addBookToLibrary(b2);
        if (shouldNotBeIn >= 0) {
            writeError("ERROR: Capacity is full, but adding book succeeded.");
        }
        writeSuccess("Book addition, copies handling, capacity - VALID");
        int patronId = lib.registerPatronToLibrary(p);
        if (patronId < 0) {
            writeError("ERROR: Registering patron to the library returned negative id.");
        }
        int patronId2 = lib.registerPatronToLibrary(p);
        if (patronId != patronId2) {
            writeError("ERROR: Adding the same patron object succeeded.");
        }
        int shouldNotBeIn2 = lib.registerPatronToLibrary(p2);
        if (shouldNotBeIn2 >= 0) {
            writeError("ERROR: Capacity is full, but adding patron succeeded.");
        }
        writeSuccess("Patron registration, double registration handling, capacity - VALID");
        Library lib2 = new Library(3, 1, 1);
        Patron patron1 = new Patron("patron1", "patron1", 2, 2, 2, 10);
        Book book11 = new Book("book1", "a1", 2001, 4, 4, 4);
        Book book12 = new Book("book2", "a1", 2001, 3, 1, 1);
        Book book13 = new Book("book2", "a1", 2001, 5, 5, 5);
        int patron1ID = lib2.registerPatronToLibrary(patron1);
        int book11ID = lib2.addBookToLibrary(book11);
        int book12ID = lib2.addBookToLibrary(book12);
        //פטרון משאיל ספר שהוא לא יהנה ממנו
        boolean x = lib2.borrowBook(book12ID, patron1ID);
        if (x) {
            writeError("ERROR: Patron succeeded to borrow he won't enjoy.");
        }
        //פטרון מנסה להשאיל ספר שלא קיים
        boolean xx = lib2.borrowBook(book12ID + 20, patron1ID);
        //פטרון מנסה להשאיל ספר שכבר מושאל
        if (xx) {
            writeError("ERROR: Patron succeeded to borrow that doesn't exist.");
        }
        boolean xxx = lib2.borrowBook(book12ID, patron1ID + 20);
        if (xxx) {
            writeError("ERROR: Incorrect patron's ID succeeded to borrow.");
        }
        boolean xxxx = lib2.borrowBook(book11ID, patron1ID);
        if (!xxxx) {
            writeError("ERROR: Patron failed to borrow valid book.");
        }
        //פטרון מנסה להשאיל ספר למרות שהוא כבר משאיל הכל
        boolean y = lib2.borrowBook(book11ID, patron1ID);
        if (y) {
            writeError("ERROR: Patron succeeded to borrow unavailable book.");
        }
        int book3ID = lib2.addBookToLibrary(book13);
        boolean z = lib2.borrowBook(book3ID, patron1ID);
        if (z) {
            writeError("ERROR: Patron succeeded to borrow 2 books, but max borrow is 1.");
        }
        writeSuccess("Borrowing tests - VALID");

    }

    public static void PatronBasicTests() {
        Patron p = new Patron("Some", "Name", 4, 3, 11, 33);
        Book b1 = new Book("A", "B", 2001, 1, 1, 1);
        Book b2 = new Book("A", "B", 2001, 2, 1, 2);
        Book b3 = new Book("A", "B", 2001, 3, 1, 2);
        if (p.getBookScore(b1) != (4 + 3 + 11)) {
            writeError("Patron's getBookScore FAILED, should be " + (4 + 3 + 11) + ".");
        }
        if (p.getBookScore(b2) != (4 * 2 + 3 + 11 * 2)) {
            writeError("Patron's getBookScore FAILED, should be " + (4 * 2 + 3 + 11 * 2) + ".");
        }
        writeSuccess("Patron's getBookScore PASSED");
        if (!p.stringRepresentation().equals("Some Name")) {
            writeError("Patron's stringRepresentation FAILED, expected:Some Name got: " + p.stringRepresentation());
        }
        writeSuccess("Patron's stringRepresentation PASSED");
        if (p.willEnjoyBook(b1)) {
            //enjoyment of b1 is 18 < 33 - should be false.
            writeError("Patron's willEnjoyBook FAILED, should be false.");
        }
        if (p.willEnjoyBook(b2)) {
            //enjoyment of b2 is 33 - should be false.
            writeError("Patron's willEnjoyBook FAILED, should be false.");
        }
        if (!p.willEnjoyBook(b3)) {
            //enjoyment of b3 is 37 > 33 - should be true.
            writeError("Patron's willEnjoyBook FAILED, should be true.");
        }
        writeSuccess("Patron's willEnjoyBook PASSED");
        semiMsg("Basic tests of Patron PASSED");

    }

    public static void BookSanityTests() {
        Book test = new Book("A", "B", 2009, 4, 12, 5);
        if (test.getLiteraryValue() != (4 + 12 + 5)) {
            writeError("Book's getLiteraryValue FAILED");
        }
        writeSuccess("Book's getLiteraryValue PASSED");
        if (!test.stringRepresentation().equals("[A,B,2009," + (4 + 12 + 5) + "]")) {
            writeError("Book's stringRepresentation FAILED");
        }
        writeSuccess("Book's stringRepresentation PASSED");
        if (test.getCurrentBorrowerId() != -1) {
            writeError("Book's getCurrentBorrowerId FAILED, should be -1.");
        }
        int borrowerID = 15;
        test.setBorrowerId(borrowerID);
        if (test.getCurrentBorrowerId() != borrowerID) {
            writeError("Book's getCurrentBorrowerId FAILED, should be " + borrowerID + ".");
        }
        writeSuccess("Book's getCurrentBorrowerId and setBorrowerId PASSED");
        test.returnBook();
        test.returnBook();
        if (test.getCurrentBorrowerId() != -1) {
            writeError("Book's returnBook FAILED, getCurrentBorrowerId() should return -1.");
        }
        writeSuccess("Book's returnBook PASSED");
        semiMsg("Basic tests of Book PASSED");
    }

    public static void checkingMethodExistsBook() {
        //checking Constructor
        Book b = new Book("Title", "Author", 2000, 1, 1, 1);
        writeSuccess("Book Constructor EXISTS");
        int checkIntReturn = b.getCurrentBorrowerId(); //won't compile unless getCurrentBorrowerId exists and returns int.
        writeSuccess("Book getCurrentBorrowerId EXISTS and return type's VALID");
        checkIntReturn = b.getLiteraryValue();  //won't compile unless getCurrentBorrowerId exists and returns int.
        writeSuccess("Book getLiteraryValue EXISTS and return type's VALID");
        b.returnBook();
        try {
            Method m = Book.class.getDeclaredMethod("returnBook");
            if (m.getReturnType().equals(Void.TYPE)) {
                writeSuccess("Book returnBook EXISTS and return type's VALID");
            } else {
                writeError("ERROR: Book returnBook's return type is not void.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        b.setBorrowerId(10);
        try {
            Method m = Book.class.getDeclaredMethod("setBorrowerId", int.class);
            if (m.getReturnType().equals(Void.TYPE)) {
                writeSuccess("Book setBorrowerId EXISTS and return type's VALID");
            } else {
                writeError("ERROR: Book setBorrowerId's return type is not void.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String t = b.stringRepresentation();
        writeSuccess("Book stringRepresentation EXISTS and return type's VALID");
        semiMsg("Book class includes all API methods - VALID");
    }

    public static void checkingMethodExistsPatron() {
        Patron p = new Patron("First", "Name", 1, 1, 1, 10);
        writeSuccess("Patron Constructor EXISTS");
        Book b = new Book("Title", "Author", 2001, 1, 1, 1);
        int d = p.getBookScore(b);
        writeSuccess("Patron getBookScore EXISTS and return type's VALID");
        boolean s = p.willEnjoyBook(b);
        writeSuccess("Patron willEnjoyBook EXISTS and return type's VALID");
        String s1 = p.stringRepresentation();
        writeSuccess("Patron stringRepresentation EXISTS and return type's VALID");
        semiMsg("Patron class includes all API methods - VALID");
    }

    public static void checkingMethodExistsLibrary() {
        Library lib = new Library(1, 1, 1);
        Patron p = new Patron("A", "B", 1, 1, 1, 1);
        Book b = new Book("a", "b", 2009, 1, 1, 2);
        writeSuccess("Library Constructor EXISTS");
        int d = lib.addBookToLibrary(b);
        boolean s = lib.borrowBook(0, 2); //invalid id's!!
        writeSuccess("Library addBookToLibrary EXISTS and return type's VALID");
        d = lib.getBookId(b);
        writeSuccess("Library getBookId EXISTS and return type's VALID");
        d = lib.getPatronId(p);
        writeSuccess("Library getPatronId EXISTS and return type's VALID");
        s = lib.isBookAvailable(0);
        writeSuccess("Library isBookAvailable EXISTS and return type's VALID");
        s = lib.isBookIdValid(0);
        writeSuccess("Library isBookIdValid EXISTS and return type's VALID");
        s = lib.isPatronIdValid(0);
        writeSuccess("Library isPatronIdValid EXISTS and return type's VALID");
        d = lib.registerPatronToLibrary(p);
        writeSuccess("Library registerPatronToLibrary EXISTS and return type's VALID");
        try {
            Method m = Library.class.getDeclaredMethod("returnBook", int.class);
            if (m.getReturnType().equals(Void.TYPE)) {
                writeSuccess("Library returnBook EXISTS and return type's VALID");
            } else {
                writeError("ERROR: Library returnBook's return type is not void.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Book t = lib.suggestBookToPatron(0);
        writeSuccess("Library suggestBookToPatron EXISTS and return type's VALID");
        semiMsg("Library class includes all API methods - VALID");

    }

    public static void writeError(String msg) {
        System.out.println(ANSI_RED + msg + ANSI_RESET);
        System.exit(0);
    }

    public static void writeSuccess(String msg) {
        System.out.println(ANSI_CYAN + msg + ANSI_RESET);
    }

    public static void semiMsg(String msg) {
        System.out.println("=========================================");
        System.out.println(ANSI_BG + msg + ANSI_RESET);
        System.out.println("=========================================");
    }
}

