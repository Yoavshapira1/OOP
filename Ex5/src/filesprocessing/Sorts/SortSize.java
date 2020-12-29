package filesprocessing.Sorts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/** Sort the files due their size from smallest to largest. */
public class SortSize extends SortStrategy {

    /**
     * The constructor gets a boolean variable tells if the sorting is in reversed order.
     *
     * @param reversed A boolean value to tell if the SortStrategy is reversed or not.
     */
    public SortSize(boolean reversed) {
        super(reversed);
    }

    /**
     * Implement comparison between two files by their sizes. Between two files with the same size, the order
     * is alphabetically.
     *
     * @param file1 Left hand side file
     * @param file2 Right hand side file
     * @return negative value if file1 should appear before file 2 and positive value if otherwise
     */
    @Override
    public int compare(File file1, File file2) {
        try {
            long result = (Files.size(file1.toPath()) - Files.size(file2.toPath()));
            if (result == EQUALS) {
                result = new SortAbsolute(false).compare(file1, file2);
            }
            return (int)result * this.reverseSwitch;
        }
        catch (IOException e) {
            return (new SortAbsolute(false).compare(file1, file2) * this.reverseSwitch);
        }
    }
}
