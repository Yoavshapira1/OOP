package filesprocessing.Filters;

import filesprocessing.Errors.Type1Exception;
import java.io.File;

/** Filter all files starts with a given prefix. */
public class FilterPrefix extends Filter {

    /* The string to check whether prefix is equal to. */
    private final String prefix;

    /** The constructor initialize the size member and the NOT switch.
     *
     * @param parameters An array of Strings contains the parameters from the input separated by the SEPARATOR
     * @throws NullPointerException - if the input is not in a valid format for this filter.
     * @throws Type1Exception - if the input is not in a valid format for this filter.
     */
    public FilterPrefix(String[] parameters) throws Exception {
        if (parameters.length >= 2) {
            this.prefix = parameters[1];
            if (parameters.length == 3) {
                initializeNOTswitch(parameters[2]);
            }
        }
        else if (parameters.length == 1) {
            this.prefix = "";
        }
        else {
            throw new Type1Exception();
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
            return (pathname.getName().startsWith(this.prefix) != this.isNOTon) && pathname.isFile();
        }
        return false;
    }
}
