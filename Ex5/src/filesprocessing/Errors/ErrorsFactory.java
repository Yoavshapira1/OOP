package filesprocessing.Errors;

/** The ErrorFactory has members as Strings, which hold for every possible error in the program.
 * Every String has a getter method.
 */
public class ErrorsFactory {

    /* All these Strings are possible errors messages. */
    private static final String WRONG_USAGE = "ERROR: Wrong usage. Should receive 2 arguments";
    private static final String FILES_ERR = "ERROR: Invalid files paths";
    private static final String WARNING = "Warning in line ";
    private static final String FILTER_SUB_SECTION_ERR = "ERROR: Filter sub suction is missing";
    private static final String ORDER_SUB_SECTION_ERR = "ERROR: Order sub suction is missing";
    private static final String BAD_FORMAT = "ERROR: Bad format of the command file";
    private static final String IO_ERROR = "ERROR: An error occurred while reading the files";

    /** Returns a wrong usage error message.
     * @return String contains informative messages. */
    public static String wrongUsage() { return(WRONG_USAGE); }

    /** Returns a invalid Files error message.
     * @return String contains informative messages. */
    public static String invalidFiles() { return(FILES_ERR); }

    /** Returns a warning error message.
     * @param line the line which the problem occurred in
     * @return String contains informative messages.*/
    public static String warning(int line) { return(WARNING + line); }

    /** Returns a filter sub section error message.
     * @return String contains informative messages. */
    public static String filterLineErr() { return(FILTER_SUB_SECTION_ERR); }

    /** Returns an order sub section error message.
     * @return String contains informative messages.*/
    public static String orderLineErr() { return(ORDER_SUB_SECTION_ERR); }

    /** Returns a bad format error message.
     * @return String contains informative messages. */
    public static String badFormat() { return(BAD_FORMAT); }

    /** Returns a bad format error message.
     * @return String contains informative messages. */
    public static String IOError() { return(IO_ERROR); }

}
