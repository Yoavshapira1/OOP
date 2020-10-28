import org.junit.Assert;

public class TestGolan {
    TestGolan() {
        test1();
    }

    void test1() {
        Book book = new Book("test", "golan", 2020, 5, 6, 7);
        Book bookTest = new Book("test2", "golan", 2020, 5, 6, 7);
        int borrowerId = book.getCurrentBorrowerId();
        int value = book.getLiteraryValue();
        String str = book.stringRepresentation();
        Assert.assertEquals(-1, borrowerId);
        Assert.assertEquals(18, value);
        Assert.assertEquals("[test,golan,2020,18]", str);

        Patron golan = new Patron("golan", "shabi", 1, 2, 3, 100);
        Patron yam = new Patron("yam", "fine", 1, 1, 1, 1);
        Patron maor = new Patron("maor", "mizrahi", 1, 2, 3, 1);
        boolean enjoyment = golan.willEnjoyBook(book) || !yam.willEnjoyBook(book);
        boolean score = golan.getBookScore(book) != 38 || yam.getBookScore(book) != 18;
        Assert.assertFalse(enjoyment);
        Assert.assertFalse(score);
        Assert.assertEquals("golan shabi", golan.stringRepresentation());

        Library library = new Library(1, 1, 2);
        int golanId = library.registerPatronToLibrary(golan);
        int yamId = library.registerPatronToLibrary(yam);
        int maorId = library.registerPatronToLibrary(maor);
        Assert.assertTrue(golanId >= 0);
        Assert.assertTrue(yamId >= 0);
        Assert.assertTrue(maorId < 0);

        golanId = library.registerPatronToLibrary(golan);
        yamId = library.registerPatronToLibrary(yam);
        Assert.assertTrue(golanId >= 0);
        Assert.assertTrue(yamId >= 0);

        boolean validPatron = library.isPatronIdValid(golanId) && library.isPatronIdValid(yamId);
        boolean notValidPatron = library.isPatronIdValid(yamId + golanId + 1);
        Assert.assertTrue(validPatron);
        Assert.assertFalse(notValidPatron);

        int bookId = library.addBookToLibrary(book);
        boolean validBook = library.isBookIdValid(bookId);
        boolean notValidBook = library.isBookIdValid(bookId + 1);
        Assert.assertTrue(validBook);
        Assert.assertFalse(notValidBook);

        bookTest.setBorrowerId(book.getCurrentBorrowerId());
        Assert.assertEquals(golanId, library.getPatronId(golan));
        Assert.assertEquals(bookId, library.getBookId(book));
        Assert.assertEquals(-1, library.getBookId(bookTest));
        Assert.assertEquals(-1, library.getPatronId(maor));

        Assert.assertTrue(library.isBookAvailable(bookId));
        Assert.assertTrue(library.borrowBook(bookId, yamId));
        Assert.assertFalse(library.isBookAvailable(bookId));

        library.returnBook(bookId);
        Assert.assertTrue(library.isBookAvailable(bookId));

        Assert.assertNull(library.suggestBookToPatron(golanId));
        Assert.assertEquals(book, library.suggestBookToPatron(yamId));
    }
}
