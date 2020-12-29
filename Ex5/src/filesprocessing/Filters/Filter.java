package filesprocessing.Filters;

import filesprocessing.Errors.Type1Exception;
import java.io.FileFilter;

/** This is an abstract class for the different filtering strategies.
 * All filters share some common attributes: The NOT switch, and the format of input they handle with.
 * Changes in the format of the input of the filter parameters, are should be handled here.
 * The class implements the Interfaces:
 *      ParametersFormat - to be able to handle the format if needed.
 *      FileFilter - of the java' I/O package.
 * */
public abstract class Filter implements FileFilter, ParametersFormat {

    /** The NOT switch: If this value is true, than the filter operations flipped. */
    protected boolean isNOTon = false;

    /** Get a parameter which holds the NOT-switch information, and initialize the switch accordingly.
     * @param param The String holds for the NOT switch information.
     * @throws Type1Exception if the parameter is not a String that equals to "NOT". */
    protected void initializeNOTswitch(String param) throws Type1Exception {
        if (!param.equals(NOT)) {
            throw new Type1Exception();
        }
        this.isNOTon = true;
    }

}
