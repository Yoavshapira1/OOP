package filesprocessing.Filters;

import filesprocessing.Errors.Type1Exception;
import java.io.File;

/** This is the filter by default, and it doesn't filter any file. */
public class FilterAll extends Filter {

    /** Default constructor - initialize the switch member to false. */
    public FilterAll() {};

    /** The constructor initialize the size member and the NOT switch.
     *
     * @param parameters An array of Strings contains the parameters from the input separated by the SEPARATOR
     * @throws Type1Exception if the input is not in a valid format for this filter.
     */
    public FilterAll(String[] parameters) throws Type1Exception {
        if (parameters.length > 2) {
            throw new Type1Exception();
        }
        else if (parameters.length == 2) {
            initializeNOTswitch(parameters[1]);
        }
    }

    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return <code>true</code> if and only if <code>pathname</code>
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname != null) {
            return pathname.isFile() && !this.isNOTon;
        }
        return false;
    }
}
