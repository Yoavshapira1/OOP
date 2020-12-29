package filesprocessing.Filters;

import filesprocessing.Errors.Type1Exception;
import java.io.File;

/** Filter all files have name that equals to a given String. */
public class FilterFile extends Filter {

    /* The String to compare to. */
    private String fileName;

    /** The constructor initialize the size member and the NOT switch.
     *
     * @param parameters An array of Strings contains the parameters from the input separated by the SEPARATOR
     * @throws Type1Exception - if the input is not in a valid format for this filter.
     */
    public FilterFile(String[] parameters) throws Type1Exception {
        if (parameters.length >= 2) {
            this.fileName = parameters[1];
            if (parameters.length == 3) {
                initializeNOTswitch(parameters[2]);
            }
        }
        else if (parameters.length == 1) {
            this.fileName = "";
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
            return (pathname.getName().equals(this.fileName) != this.isNOTon) && (pathname.isFile());
        }
        return false;
    }
}
