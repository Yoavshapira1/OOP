//created by Yoav Shapira
//26.10.20

/**
 * This class represents a library patron that has a name and assigns values to different literary aspects of books.
 */

public class Patron {

    /** The first name of this patron. */
    final String firstName;

    /** The last name of this patron. */
    final String lastName;

    /** The tendency of this patron for comic books. */
    int comicTendencyVal;

    /** The tendency of this patron for dramatic books. */
    int dramaticTendencyVal;

    /** The tendency of this patron for educational books. */
    int educationalTendencyVal;

    /** The enjoyment threshold of this patron. */
    int enjoymentThreshold;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new patron with the given characteristics.
     * @param patronFirstName The first name of the patron
     * @param patronLastName The last name of the patron
     * @param comicTendency The weight the patron assigns to the comic aspects of books
     * @param dramaticTendency The weight the patron assigns to the dramatic aspects of books
     * @param educationalTendency The weight the patron assigns to the educational aspects of books
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy it
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold) {
        firstName = patronFirstName;
        lastName = patronLastName;
        comicTendencyVal = comicTendency;
        dramaticTendencyVal = dramaticTendency;
        educationalTendencyVal = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the patron,
     * which is a sequence of its first and last name,
     * separated by a single white space.
     * @return The String representation of this patron
     */
    String stringRepresentation() {
        return (firstName + " " + lastName);
    }

    /**
     * Returns the literary value this patron assigns to the given book
     * @param book The book to asses
     * @return The literary value this patron assigns to the given book
     */
    int getBookScore(Book book) {
        return ((dramaticTendencyVal * book.dramaticValue) +
                (comicTendencyVal * book.comicValue) +
                (educationalTendencyVal * book.educationalValue));
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     * @param book The book to asses
     * @return true if this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return (enjoymentThreshold < getBookScore(book));
    }

}