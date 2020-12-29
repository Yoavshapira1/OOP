package filesprocessing;

import filesprocessing.Errors.*;
import java.io.*;
import java.util.*;

/** The driver of the file processor.*/
public class DirectoryProcessor {

    /* The path to the command file. */
    private static File COMMAND_FILE;

    /* The path to the target directory. */
    private static File DIRECTORY;

    /* Check if the arguments are valid. If yes - initialize the members and return true, else - return false. */
    private static boolean usageCheck(String[] args) throws Exception {
        if (args.length == 2) {
            DIRECTORY = new File(args[0]);
            COMMAND_FILE = new File(args[1]);
            return (DIRECTORY.isDirectory() && COMMAND_FILE.isFile());
        }
        return false;
    }

    /** Run the program with the given arguments as paths.
     *
     * @param args - 2 arguments which are both nut null; The first is a directory, and the second a file.
     */
    public static void main(String[] args) {
        try {
            if (!usageCheck(args)) {
                System.err.println(ErrorsFactory.wrongUsage());
                return;
            }
        }
        catch (Exception exception) {
            System.err.println(ErrorsFactory.invalidFiles());
        }

        try {
            ArrayList<Section> sections = SectionsFactory.parseFileToSections(COMMAND_FILE);
            for (Section section : sections){
                section.sortFiles(DIRECTORY);
            }
        }
        catch (Type2Exception Exception) {
            System.err.println(Exception.getMessage());
        }
    }
}
