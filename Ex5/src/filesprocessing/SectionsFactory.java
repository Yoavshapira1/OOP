package filesprocessing;

import java.io.*;
import java.util.ArrayList;
import filesprocessing.Errors.*;
import filesprocessing.Filters.*;
import filesprocessing.Sorts.*;

/** This sections factory parse the command file into different sections: Every section has a filtering method and
 * an ordering method.
 */
public class SectionsFactory {

    /* This is the factory that in charge of creating the different filtering functions. */
    private static final FiltersFactory FILTER_FACTORY = FiltersFactory.instance();

    /* This is the factory that in charge of creating the different sorting functions. */
    private static final SortsFactory SORTING_FACTORY = SortsFactory.instance();

    /* This is the String determines a FILTER sub-section. */
    private static final String FILTER = "FILTER";

    /* This is the String determines a ORDER sub-section. */
    private static final String ORDER = "ORDER";

    /* Represents the amount of line in a section. */
    private static final int LINES_IN_SECTION = 4;

    /* Represents the line that dedicated to the FILTER headline. */
    private static final int FILTER_HEADLINE_LINE = 1 % LINES_IN_SECTION;

    /* Represents the line that dedicated to the FILTER parameters. */
    private static final int FILTER_PARAM_LINE = 2 % LINES_IN_SECTION;

    /* Represents the line that dedicated to the ORDER headline. */
    private static final int ORDER_HEADLINE_LINE = 3 % LINES_IN_SECTION;

    /* Represents the line that dedicated to the ORDER parameters. */
    private static final int ORDER_PARAM_LINE = 4 % LINES_IN_SECTION;

    /* Separate a single line from input, by division to cases. Return false only in one case: When the
    * Order's parameters line is missing, and another section should be added to the array. */
    private static boolean parseSingleLine(int lineNumber, String line, Section section, int missingParametersLines)
            throws Type2Exception {
        try {
            switch ((lineNumber + missingParametersLines) % LINES_IN_SECTION) {
                case FILTER_HEADLINE_LINE:
                    if (!line.equals(FILTER)) {
                        throw new Type2Exception(ErrorsFactory.filterLineErr());
                    }
                    break;
                case FILTER_PARAM_LINE:
                    section.setFilter(FILTER_FACTORY.getFilter(line));
                    break;
                case ORDER_HEADLINE_LINE:
                    if (!line.equals(ORDER)) {
                        throw new Type2Exception(ErrorsFactory.orderLineErr());
                    }
                    break;
                case ORDER_PARAM_LINE:
                    if (line.equals(FILTER)) {
                        return false;
                    }
                    section.setComparator(SORTING_FACTORY.getSortStrategy(line));
                    break;
            }
        }
        catch (Type1Exception exception) {
            section.addWarningLine(lineNumber);
        }
        return true;
    }

    /* Iterate and parse the file into sections. */
    private static void parse(ArrayList<Section> sections, BufferedReader buffer) throws IOException, Type2Exception {
        String lineContent;
        int lineNumber = 1;
        int missingParametersLines = 0;
        while ((lineContent = buffer.readLine()) != null) {
            if ((lineNumber + missingParametersLines) % LINES_IN_SECTION == FILTER_HEADLINE_LINE) {
                sections.add(new Section(SORTING_FACTORY.getDefaultSort(), FILTER_FACTORY.getDefaultFilter()));
            }
            if (!parseSingleLine(lineNumber, lineContent, sections.get(sections.size() - 1), missingParametersLines)) {
                sections.add(new Section(SORTING_FACTORY.getDefaultSort(), FILTER_FACTORY.getDefaultFilter()));
                missingParametersLines++;
            }
            lineNumber++;
        }
        if ((lineNumber + missingParametersLines) % LINES_IN_SECTION == FILTER_PARAM_LINE ||
                (lineNumber + missingParametersLines) % LINES_IN_SECTION == ORDER_HEADLINE_LINE) {
            throw new Type2Exception(ErrorsFactory.badFormat());
        }
    }

    /** Parse the command file to different sections, by parsing to section one by one till it gets to the EOF or to
     * a section which cause an error of type2. If such a Sections.Section was found, the last Section in the
     * returned array will contain null values as filter and comparator.
     *
     * @param commandFile the file that need to be parsed
     * @return an array of Sections
     * @throws Type2Exception related to the files reading.
     */
    public static ArrayList<Section> parseFileToSections(File commandFile) throws Type2Exception {
        ArrayList<Section> sections = new ArrayList<Section>();
        try {
            FileReader reader = new FileReader(commandFile);
            BufferedReader buffer = new BufferedReader(reader);
            parse(sections, buffer);
        } catch (IOException exception) {
            throw new Type2Exception(ErrorsFactory.IOError());
        }
        return sections;
    }
}
