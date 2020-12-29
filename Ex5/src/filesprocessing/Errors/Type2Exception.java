package filesprocessing.Errors;

/** This is a class for Exceptions caused by an essential error:
 * 1) Invalid usage
 * 2) I/O problem
 * 3) Wrong Section's sub-sections names
 * 4) Bad format of the command file
 */
public class Type2Exception extends Exception {

    /** The constructor must get a message indicates the problem.
     * @param msg The message, as a String, that the exception will contain. */
    public Type2Exception(String msg) {
        super(msg);
    }

}
