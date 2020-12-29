package filesprocessing.Filters;

import filesprocessing.Errors.Type1Exception;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/** Filter all files with size between two numbers of KB. */
public class FilterBetween extends Filter {

    /* The size to be filtered by. */
    private final double lower_size;

    /* The size to be filtered by. */
    private final double upper_size;

    /** The constructor initialize the size member and the NOT switch.
     *
     * @param parameters An array of Strings contains the parameters from the input separated by the SEPARATOR
     * @throws Type1Exception if the input is not in a valid format or the upper double lower that the lower.
     * @throws NumberFormatException if the values from input are not valid.
     */
    public FilterBetween(String[] parameters) throws NumberFormatException, Type1Exception {

        if (parameters.length >= 3) {
            this.lower_size = Double.parseDouble(parameters[1]) * TO_KB;
            this.upper_size = Double.parseDouble(parameters[2]) * TO_KB;
            if (this.upper_size < this.lower_size || this.upper_size < 0 || this.lower_size < 0) {
                throw new Type1Exception();
            }
            if (parameters.length == 4) {
                initializeNOTswitch(parameters[3]);
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
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname != null) {
            long size = 0;
            try {
                size = Files.size(pathname.toPath());
            } catch (IOException e) {
                return false;
            }
            return (size >= this.lower_size && size <= this.upper_size) != this.isNOTon && (pathname.isFile());
        }
        return false;
    }
}
