package filesprocessing.Sorts;

import java.io.File;

/** Sort files due to their absolute name, alphabetically. */
public class SortAbsolute extends SortStrategy {

    /** The constructor gets a boolean variable tells if the sorting is in reversed order.
     * @param reversed A boolean value tells if the SortStrategy is reversed or not.
     * */
    public SortAbsolute(boolean reversed) {
        super(reversed);
    }

    /** Implement comparison between two files by their absolute names.
     *
     * @param file1 Left hand side file
     * @param file2 Right hand side file
     * @return negative value if file1 should appear before file 2 and positive value if otherwise
     */
    @Override
    public int compare(File file1, File file2) {
        return file1.getAbsolutePath().compareTo(file2.getAbsolutePath()) * this.reverseSwitch;
    }
}
