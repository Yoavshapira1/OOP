package filesprocessing.Filters;

import filesprocessing.Errors.Type1Exception;
import java.io.File;
import java.nio.file.Files;

/** Filter all writable files. */
public class FilterWritable extends Filter {

    /* Indicates if the filter is satisfies a condition of NOT satisfies a condition. */
    private boolean yesOrNo;

    /* Initialize the yesORno member. */
    private void initHelper(String param) throws Type1Exception{
        if (param.equals(YES)) {
            this.yesOrNo = true;
        }
        else if (param.equals(NO)) {
            this.yesOrNo = false;
        }
        else {
            throw new Type1Exception();
        }
    }

    /** The constructor initialize the size member and the NOT switch.
     *
     * @param parameters An array of Strings contains the parameters from the input separated by the SEPARATOR
     * @throws Type1Exception - if the input is not in a valid format.
     */
    public FilterWritable(String[] parameters) throws Exception {
        if (parameters.length >= 2) {
            initHelper(parameters[1]);
            if (parameters.length == 3) {
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
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname != null) {
            return (Files.isWritable(pathname.toPath()) == this.yesOrNo) != isNOTon && (pathname.isFile());
        }
        return false;
    }
}
