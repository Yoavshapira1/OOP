package filesprocessing.Filters;

import filesprocessing.Errors.*;
import java.io.FileFilter;
import java.util.regex.PatternSyntaxException;

/** The Filter Strategy Factory is a singleton with which produces a filters methods from a raw input. */
public class FiltersFactory implements ParametersFormat {

    //Todo: When adding a filter, add the name as a static members String,
    // than implement a FilterStrategy and add a switch case in the build method.

    /* The next Strings are the possible names for a filter. */
    private static final String DEFAULT = "all";
    private static final String GREATER_THAN = "greater_than";
    private static final String BETWEEN = "between";
    private static final String SMALLER_THAN = "smaller_than";
    private static final String CONTAINS = "contains";
    private static final String FILE = "file";
    private static final String PREFIX = "prefix";
    private static final String SUFFIX = "suffix";
    private static final String WRITEABLE = "writable";
    private static final String EXECUTABLE = "executable";
    private static final String HIDDEN = "hidden";

    /* The one and only instance of the factory. */
    private static final FiltersFactory instance = new FiltersFactory();

    /* Default constructor initialize the filters names set. */
    private FiltersFactory() {};

    /* Build the wanted filter. Throw a Typ1Exception if any error occurred. */
    private FileFilter build (String[] parameters) throws Type1Exception {
        if (parameters != null)
        {
            try {
                switch (parameters[0]) {
                    case DEFAULT:
                        return new FilterAll(parameters);
                    case GREATER_THAN:
                        return new FilterGreaterThan(parameters);
                    case BETWEEN:
                        return new FilterBetween(parameters);
                    case SMALLER_THAN:
                        return new FilterSmallerThan(parameters);
                    case CONTAINS:
                        return new FilterContains(parameters);
                    case FILE:
                        return new FilterFile(parameters);
                    case HIDDEN:
                        return new FilterHidden(parameters);
                    case WRITEABLE:
                        return new FilterWritable(parameters);
                    case EXECUTABLE:
                        return new FilterExecutable(parameters);
                    case SUFFIX:
                        return new FilterSuffix(parameters);
                    case PREFIX:
                        return new FilterPrefix(parameters);
                    default:
                        throw new Type1Exception();
                }
            }
            catch (Exception e) {
                throw new Type1Exception();
            }
        }
        throw new Type1Exception();
    }

    /** Returns the default filter, which satisfied when the parameters are not valid or any warning occurs.
     *
     * @return The default filter
     */
    public FileFilter getDefaultFilter() {
        return new FilterAll();
    }

    /** Parse the filter-section parameters line, and return a compatible filter for these parameters.
     *
     * @param lineContent - the line contains the parameters
     * @return a FileFilter object
     * @throws Type1Exception if any error occurs while parsing the line
     */
    public FileFilter getFilter(String lineContent) throws Type1Exception {
        try {
            String[] parameters = lineContent.split(SEPARATOR);
            return build(parameters);
        }
        catch (PatternSyntaxException exception) {
            throw new Type1Exception();
        }
    }

    /**
     *  @return FiltersFactory - the only instance of the factory.
     *  */
    public static FiltersFactory instance() {
        return instance;
    }
}
