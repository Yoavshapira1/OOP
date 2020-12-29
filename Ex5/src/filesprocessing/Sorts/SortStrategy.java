package filesprocessing.Sorts;

import java.io.File;
import java.util.Comparator;

/** Abstract class for the different Sorting strategies.
 * Every sort strategy has a switch of REVERSE.
 * The class implements the Interfaces:
 *      ParametersFormat - to be able to handle the format if needed.
 *      Comparator - implements a compare method.
 */
public abstract class SortStrategy implements Comparator<File>, ParametersFormat {

    /** This value represents an equal comparison result. */
    protected static final int EQUALS = 0;

    /** This value holds if file1 should be returned from comparator. */
    protected static final int FILE1 = -1;

    /** This value holds if file2 should be returned from comparator. */
    protected static final int FILE2 = 1;

    /** This value holds for reverse ON in this sort. */
    protected static final int REVERSE_ON = -1;

    /** This value holds for reverse OFF in this sort. */
    protected static final int REVERSE_OFF = 1;

    /** This switch tells if the sorting is reversed or not.
     * 1 : means the sort is no reversed.
     * -1 : means the sort is in reverse order. */
    protected int reverseSwitch;

    /** The constructor get a boolean value to tell if the reversed order is ON.
     * If it is, initialize the reverseSwitch member to -1. otherwise, to 1.
     * @param reverse a boolean value tells if this sort strategy sorts in reversed order.
     */
    public SortStrategy (boolean reverse) {
        if (reverse) {
            this.reverseSwitch = REVERSE_ON;
        }
        else {
            this.reverseSwitch = REVERSE_OFF;
        }
    }
}
