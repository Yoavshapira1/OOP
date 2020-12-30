package filesprocessing.Filters;

import filesprocessing.Errors.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/** Filter all files with size bigger than a given number of KB. */
public class FilterGreaterThan extends Filter {

    /* The size to be filtered by. */
    private final double size;

    /** The constructor initialize the size member and the NOT switch.
     *
     * @param parameters An array of Strings contains the parameters from the input separated by the SEPARATOR
     * @throws Type1Exception if the input is not in a valid format for this filter.
     * @throws NumberFormatException if the number in the parameter line is not valid.
     * @throws NullPointerException if the number in the parameter line is not valid.
     */
    public FilterGreaterThan(String[] parameters) throws NullPointerException, NumberFormatException, Type1Exception {
        if (parameters.length >= 2) {
            this.size = Double.parseDouble(parameters[1]) * TO_KB;
            if (this.size < 0) {
                throw new Type1Exception();
            }
            if (parameters.length >= 3) {
                initializeNOTswitch(parameters[2]);
            }
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
     * should be included.
     * Notice: If any IO exception occurs while reading the size, return false.
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname != null) {
            try {
                return (Files.size(pathname.toPath()) > this.size) != this.isNOTon && (pathname.isFile());
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
}