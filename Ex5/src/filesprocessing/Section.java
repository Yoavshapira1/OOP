package filesprocessing;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import filesprocessing.Sorts.*;
import filesprocessing.Errors.*;

/** This is an object represents one section in the command file.
 * Every section object has a filtering method and tn ordering method.
 * If the section in the command file caused warnings, the line of warning appers in the warningLine member.
 */
public class Section {

    /* The comparator function of the section. */
    private Comparator<File> comparator;

    /* The filter function of the section. */
    private FileFilter filter;

    /* Line number which have a warning in. If no warnings occurred - the value is 0. */
    private final ArrayList<Integer> warningLines;

    /**
     * Data constructor: Initialize the comparator and the filter to the given ones, so as the warning line.
     * @param sort A SortStrategy object
     * @param filter A Filter object
     */
    public Section(SortStrategy sort, FileFilter filter) {
        this.comparator = sort;
        this.filter = filter;
        this.warningLines = new ArrayList<Integer>();
    }

    /**
     * Setter for the comparator.
     *
     * @param comparator - The new comparator to set
     */
    public void setComparator(Comparator<File> comparator) {
        this.comparator = comparator;
    }

    /**
     * Setter for the filter.
     *
     * @param filter The new filter to set
     */
    public void setFilter(FileFilter filter) {
        this.filter = filter;
    }

    /**
     * Add warning line to the array
     *
     * @param lineNum - number of line which the warning occurred in.
     */
    public void addWarningLine(int lineNum) {
        this.warningLines.add(lineNum);
    }

    /**
     * @return This section's SortStrategy
     */
    public Comparator<File> getComparator() {
        return this.comparator;
    }

    /**
     * @return This section's Filter
     */
    public FileFilter getFilter() {
        return this.filter;
    }

    /**
     * @return This section's warning array
     */
    public ArrayList<Integer> getWarningLines() {
        return this.warningLines;
    }

    /**
     * Sort the files according to the filter, comparator and warnings. If an error of type2 occurred in this section,
     * it prints the proper error message.
     *
     * @param dir A directory contains the be filtered and sorted
     */
    public void sortFiles(File dir) {
        for (Integer line : this.warningLines) System.err.println(ErrorsFactory.warning(line));
        if (dir != null) {
            ArrayList<File> filteredList = new ArrayList<File>(Arrays.asList(dir.listFiles(this.filter)));
            if (!filteredList.isEmpty()) {
                filteredList.sort(this.comparator);
                for (File file : filteredList) System.out.println(file.getName());
            }
        }
    }
}
