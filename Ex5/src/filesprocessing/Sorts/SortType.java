package filesprocessing.Sorts;

import java.io.File;

/** Sort files by their type. */
public class SortType extends SortStrategy {

    /* Value represents the period sign: "." .*/
    private static final String PERIOD = ".";

    /* This value holds a a type for files that the type hasn't detected successfully .*/
    private static final String NO_TYPE = ".";

    /** The constructor gets a boolean variable tells if the sorting is in reversed order.
     *
     * @param reversed A boolean value to tell if the SortStrategy is reversed or not.
     * */
    public SortType(boolean reversed) {
        super(reversed);
    }

    /** Implement comparison between two files by their type, alphabetically. Between two files with the same
     * type, the comparison is alphabetically according to their names. The same holds if
     * any exception thrown while reading detecting the type
     *
     * @param file1 Left hand side file
     * @param file2 Right hand side file
     * @return negative value if file1 should appear before file 2 and positive value if otherwise
     */
    @Override
    public int compare(File file1, File file2) {

        int lastPeriodFile1 = file1.getName().lastIndexOf(PERIOD);
        int lastPeriodFile2 = file2.getName().lastIndexOf(PERIOD);
        String type1 = NO_TYPE;
        String type2 = NO_TYPE;

        if (lastPeriodFile1 > 0) {
            type1 = file1.getName().substring(lastPeriodFile1);
        }
        if (lastPeriodFile2 > 0) {
            type2 = file2.getName().substring(lastPeriodFile2);
        }
        int result = type1.compareTo(type2);
        if (result == EQUALS) {
            result = new SortAbsolute(false).compare(file1, file2);
        }
        return result * this.reverseSwitch;
    }
}
