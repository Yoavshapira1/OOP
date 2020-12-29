package filesprocessing.Filters;

/** Interface to gather the format variables of the filter parameters line in the command file. */
public interface ParametersFormat {

    /** The String represents a NOT switch for the filter. */
    String NOT = "NOT";

    /** The String represents a YES switch for the filter: Writable, Hidden, Executable. */
    String YES = "YES";

    /** The String represents a NO switch for the filter: Writable, Hidden, Executable. */
    String NO = "NO";

    /** The String represents the separator in the input lines. */
    String SEPARATOR = "#";

    /** Represents the conversion between Bytes and KB. */
    int TO_KB = 1024;

}
