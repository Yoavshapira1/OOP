import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {

    /* This class is a collection of 5 kind of collection. */
    private static class SetCollection {

        /* An array of all collections to compare.
        * NOTE: array's last set is the a linked list */
        private SimpleSet[] setArray;

        /* Construct new set of collections. */
        public SetCollection() {
            setArray = new SimpleSet[]
                    { new OpenHashSet(),
                    new ClosedHashSet(),
                    new CollectionFacadeSet(new TreeSet<String>()),
                    new CollectionFacadeSet(new HashSet<String>()),
                    new CollectionFacadeSet(new LinkedList<String>()) };
        }
    }

    /* The paths to files contain the data. */
    private static final String DATA_1_TXT = "src/data1.txt";
    private static final String DATA_2_TXT = "src/data2.txt";

    /* These words are to use in the contains methods test. */
    private static final String DIF_HASH_FROM_DATA1_WORD = "hi";
    private static final String SAME_HASH_FROM_DATA1_WORD = "-1317089015";
    private static final String EXISTS_WORD_IN_DATA2 = "23";
    private static final String NOT_EXISTS_WORD_IN_DATA2 = "hi";


    /* The uniform warm-up iterations for each contain method test. */
    private static final int WARM_UP_FACTOR = 70000;

    /* The number of iterations for the linked list contains tests. */
    private static final int WARM_UP_LINKED_LIST = 7000;

    /* Use to convert time to millisecond. */
    private static final int TO_MS = 1000000;

    /* Gets an array of strings from a given data path. */
    private static String[] getArray(String path) {
        return Ex4Utils.file2array(path);
    }

    /* Measure the time of inserting data to each of the sets, from a given path file. */
    private static void addingDataMeasure(SetCollection sets, String path) {
        String[] stringArr = getArray(path);
        for (SimpleSet set : sets.setArray) {
            long start = System.nanoTime();
            for (String str : stringArr) {
                set.add(str);
            }
            long measured = System.nanoTime() - start;
            System.out.println("Time for the " + set.getClass() + " to add " + path +": " + (measured / TO_MS) + " ms.");
        }
        System.out.println("\n");
    }

    /* Measure contains operation for each data structure in specific set of structures - on a given String. */
    private static void containStringMeasure(SetCollection sets, String string) {
        //all except the Linked List
        for (int i = 0; i < sets.setArray.length - 1; i++) {
            // warming up
            for (int j = 0; j < WARM_UP_FACTOR; j++) {
                sets.setArray[i].contains(string);
            }
            //measuring
            long start = System.nanoTime();
            for (int j = 0; j < WARM_UP_FACTOR; j++) {
                sets.setArray[i].contains(string);
            }
            long measured = System.nanoTime() - start;
            System.out.println("Time for " + sets.setArray[i].getClass() + " to check if " + string +
                    " exists in the set: " + (measured / WARM_UP_FACTOR) + " nanosecond.");
        }

        long start = System.nanoTime();
        for (int i = 0; i < WARM_UP_LINKED_LIST; i++) {
            sets.setArray[sets.setArray.length - 1].contains(string);
        }
        long measured = System.nanoTime() - start;
        System.out.println("Time for linked list to check if " +
                string + " exists in the set: " + (measured / WARM_UP_LINKED_LIST) + " nanosecond.\n");
    }

    public static void main (String[] args) {

        SetCollection data1Structures = new SetCollection();
        SetCollection data2Structures = new SetCollection();

        addingDataMeasure(data1Structures, DATA_1_TXT);
        addingDataMeasure(data2Structures, DATA_2_TXT);

        containStringMeasure(data1Structures, DIF_HASH_FROM_DATA1_WORD);
        containStringMeasure(data1Structures, SAME_HASH_FROM_DATA1_WORD);

        containStringMeasure(data2Structures, EXISTS_WORD_IN_DATA2);
        containStringMeasure(data2Structures, NOT_EXISTS_WORD_IN_DATA2);

    }
}
