import org.junit.Assert;

/**
 * Created by: Evyatar
 * version 5.0
 * Changes history:
 * 01:37 ‎24/‎03/‎2018 - Kali -    When adding a patron that was already registered to the library, the
 *                              return value should by a !negative number! (as far as I understand the API),
 *                              unlike a book that was added twice - in that case should return existing book
 *                              id. Changed tests: 8.1, 14, 28.
 * 01:47 24/‎03/‎2018 - Kali -    Reverted changes (8.1, 14) due to forum answer.
 *
 * 21:49 25/‎03/‎2018 - Evyatar - Now support any non-negative int's as ID's for books and patrons. Added two
 *                              libraries tests. willEnjoyBook has to be > and not >=.
 * 00:00 25/10/2020 - Golan -   Changed to asserts instead of prints.
 */
class tester_v5 {
    public tester_v5() {

        // set up
        Book book1 = new Book("book1", "author1", 2001,
                2, 3, 1);
        Book book2 = new Book("book2", "author2", 2002,
                8, 3, 6);
        Book book3 = new Book("book2", "author2", 2002,
                8, 3, 6);
        Book book4 = new Book("book4", "author4", 2004,
                10, 10, 10);

        int book1ID, book2ID, book3ID, book4ID;

        Patron patron1 = new Patron("patron1", "last1", 3,
                5, 1, 22);
        Patron patron2 = new Patron("patron1", "last1", 2,
                1, 1, 0);
        Patron patron3 = new Patron("patron3", "last3", 2,
                1, 1, 0);

        int patron1ID1, patron2ID1, patron1ID2, patron2ID2;

        Library lib = new Library(3, 2, 2);


        Assert.assertEquals("[book1,author1,2001,6]", book1.stringRepresentation());

        Assert.assertEquals(2 + 3 + 1, book1.getLiteraryValue());

        Assert.assertTrue(book1.getCurrentBorrowerId() < 0);

        book1.setBorrowerId(5);
        Assert.assertEquals(5, book1.getCurrentBorrowerId());
        book1.returnBook();


        // test patron
        Assert.assertEquals("patron1 last1", patron1.stringRepresentation());

        Assert.assertEquals(3 * 2 + 5 * 3 + 1 * 1, patron1.getBookScore(book1));

        Assert.assertFalse(patron1.willEnjoyBook(book1)); // 22=22
        Assert.assertTrue(patron1.willEnjoyBook(book2));


        // test library
        Assert.assertFalse(lib.isBookIdValid(1));
        Assert.assertFalse(lib.isBookIdValid(88));
        Assert.assertFalse(lib.isBookAvailable(1));

        book1ID = lib.addBookToLibrary(book1);
        Assert.assertTrue(book1ID >= 0);
        Assert.assertTrue(lib.isBookIdValid(book1ID));

        book2ID = lib.addBookToLibrary(book2);
        Assert.assertTrue(book2ID >= 0);
        Assert.assertNotEquals(book2ID, book1ID);
        Assert.assertTrue(lib.isBookIdValid(book2ID));
        Assert.assertEquals(book2ID, lib.addBookToLibrary(book2)); // add the same book, should
        // return the index of original and shouldn't save another copy.

        book3ID = lib.addBookToLibrary(book3); // book with same  arguments
        Assert.assertTrue(book3ID >= 0);
        Assert.assertNotEquals(book3ID, book1ID);
        Assert.assertNotEquals(book3ID, book2ID);

        Assert.assertTrue(lib.addBookToLibrary(book4) < 0); // no room
        Assert.assertEquals(book2ID, lib.addBookToLibrary(book2)); // add the same book when
        // there is no room.

        Assert.assertFalse(lib.isPatronIdValid(1));
        Assert.assertFalse(lib.isPatronIdValid(8));

        patron1ID1 = lib.registerPatronToLibrary(patron1);
        Assert.assertTrue(patron1ID1 >= 0);
        Assert.assertEquals(patron1ID1, lib.registerPatronToLibrary(patron1));
        Assert.assertTrue(lib.isPatronIdValid(patron1ID1));

        patron2ID1 = lib.registerPatronToLibrary(patron2);
        Assert.assertEquals(patron2ID1, lib.registerPatronToLibrary(patron2));
        Assert.assertTrue(lib.isPatronIdValid(patron2ID1));

        Assert.assertTrue(lib.registerPatronToLibrary(patron3) < 0);
        Assert.assertEquals(patron1ID1, lib.registerPatronToLibrary(patron1));

        Assert.assertEquals(patron2ID1, lib.getPatronId(patron2));

        // tests for 'borrowBook'
        Assert.assertTrue(lib.isBookAvailable(book1ID));
        Assert.assertFalse(lib.borrowBook(book1ID, patron1ID1)); // will not enjoy
        Assert.assertTrue(lib.borrowBook(book1ID, patron2ID1));
        Assert.assertFalse(lib.isBookAvailable(book1ID));
        Assert.assertFalse(lib.borrowBook(80, patron2ID1)); // book id is not valid
        Assert.assertFalse(lib.borrowBook(book2ID, 80)); // patron id is not
        // valid
        Assert.assertTrue(lib.borrowBook(book2ID, patron2ID1));
        Assert.assertFalse(lib.borrowBook(book3ID, patron2ID1)); // too many books for one patron
        Assert.assertFalse(lib.borrowBook(book2ID, patron1ID1)); // the book is taken
        lib.returnBook(book1ID);
        Assert.assertTrue(lib.borrowBook(book3ID, patron2ID1));
        Assert.assertTrue(book1.getCurrentBorrowerId() < 0);


        Assert.assertEquals(book1ID, lib.getBookId(lib.suggestBookToPatron(patron2ID1)));
        Assert.assertNull(lib.suggestBookToPatron(patron1ID1)); // null because book2 and
        // book3 are taken and he will not enjoy book1

        // two libraries tests
        Library lib2 = new Library(1, 1, 2);
        Assert.assertFalse(lib2.isBookIdValid(0));

        book4ID = lib2.addBookToLibrary(book4);
        Assert.assertTrue(book4ID >= 0);


        patron1ID2 = lib2.registerPatronToLibrary(patron1);
        patron2ID2 = lib2.registerPatronToLibrary(patron2);

        Assert.assertTrue(patron1ID2 >= 0);
        Assert.assertTrue(patron2ID2 >= 0);
        Assert.assertNotEquals(patron1ID2, patron2ID2);

        Assert.assertTrue(lib2.borrowBook(book4ID, patron1ID2)); // patron1 has the max number
        // of books in library1, should be able to borrow in another library.

    }
}