package filesprocessing.Filters;

import filesprocessing.Errors.*;
import java.io.File;

/** Filter all files contains a given String in their absolute name. */
public class FilterContains extends Filter {

    /* The string to check whether contained in file name. */
    private final String string;

    /** The constructor initialize the size member and the NOT switch.
     *
     * @param parameters An array of Strings contains the parameters from the input separated by the SEPARATOR
     * @throws Type1Exception - if the input is not in a valid format.
     */
    public FilterContains(String[] parameters) throws Type1Exception {
        if (parameters.length >= 2) {
            this.string = parameters[1];
            if (parameters.length == 3) {
                initializeNOTswitch(parameters[2]);
            }
        }
        else if (parameters.length == 1) {
            this.string = "";
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
            return (pathname.getName().contains(this.string) != this.isNOTon) && (pathname.isFile());
        }
        return false;
    }
}
