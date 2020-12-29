package filesprocessing.Sorts;

import filesprocessing.Errors.Type1Exception;

/** The Sort Strategy Factory is a singleton which produces sorting methods due a raw input line. */
public class SortsFactory implements ParametersFormat {

    //Todo: To add a sort strategy: Add the name as a static member, than implement a sortStrategy object and
    // add a switch case to the buildSortStrategy method

    /* Sorting by absolute name of the file from a-z. */
    private static final String ABSOLUTE = "abs";

    /* Sorting by absolute size of the file smallest to largest. */
    private static final String TYPE = "type";

    /* Sorting by file type of the file, displayed from a-z. */
    private static final String SIZE = "size";

    /* The instance of the factory. */
    private static final SortsFactory instance = new SortsFactory();

    /* Build the sorting strategy. */
    private SortStrategy buildSortStrategy(String name, boolean reverse) throws Type1Exception {
        switch (name) {
            case ABSOLUTE:
                return new SortAbsolute(reverse);
            case TYPE:
                return new SortType(reverse);
            case SIZE:
                return new SortSize(reverse);
            default:
                throw new Type1Exception();
        }
    }

    /**
     * @return SortStrategy - The only instance of the factory.
     * */
    public static SortsFactory instance() {
        return instance;
    }

    /**
     * @return The SortStrategy which is defined as the default.
     * */
    public SortStrategy getDefaultSort() {
        return new SortAbsolute(false);
    }

    /* Check if the reverse switch should be true or false. Return the desired value of the reverse switch.
     * Throws Type1Exception if the value is not equals to REVERSE value. */
    private boolean getReverseValue(String[] parameters) throws Type1Exception {
        if (parameters.length == 1) {
            return false;
        }
        if (parameters[1].equals(REVERSE)) {
            return true;
        }
        else {
            throw new Type1Exception();
        }
    }

    /** Return a sort strategy according to a given input parameters.
     *
     * @param line the line from input
     * @return A sorting strategy satisfies the input
     * @throws Type1Exception if any error or warning appears during parsing the input
     */
    public SortStrategy getSortStrategy(String line) throws Type1Exception {
        String[] parameters = line.split(SEPARATOR);
        if (parameters.length == 0 || parameters.length > 2) {
            throw new Type1Exception();
        }
        return buildSortStrategy(parameters[0], getReverseValue(parameters));
    }
}
