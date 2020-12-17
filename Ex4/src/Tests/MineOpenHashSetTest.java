import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public class MineOpenHashSetTest {

    /**
     * parameters that determine the random variables in the tests.
     */
    static String uppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    static String lowers = "abcdefghijklmnopqrstuvwxyz";
    static String digits = "0123456789";
    static String chars = "/*-+.!@#$%^&*()_-+=]}[{<>~`";
    static int lengthRange = 7;
    static Random random = new Random();

    public static String getRandomString() {
        String possibleChars = uppers + lowers;
        StringBuilder result = new StringBuilder();
        for (int i = random.nextInt(lengthRange); i <= lengthRange; i++) {
            result.append(possibleChars.charAt(random.nextInt(possibleChars.length())));
        }

        return result.toString();
    }

    public static void main(String[] args) {

//        HashSet<String> hashSet = new HashSet<String>();
//        OpenHashSet openSet = new OpenHashSet();
        ClosedHashSet closedSet = new ClosedHashSet(1,0);
        Integer num = 26;
        for (Integer i = 0; i < num; i++) {
            assertTrue(closedSet.add(i.toString()));
            assertFalse(closedSet.add(i.toString()));
            assertTrue(closedSet.contains(i.toString()));
        }
        System.out.println("check point");
        assertTrue(closedSet.add(num.toString()));
        assertFalse(closedSet.add(num.toString()));
        assertTrue(closedSet.contains(num.toString()));
        System.out.println("check point");

        for (Integer i = 0; i < num; i++) {
            System.out.println("" + i);
            assertTrue(closedSet.delete(i.toString()));
            assertFalse(closedSet.delete(i.toString()));
            assertFalse(closedSet.contains(i.toString()));
        }
        System.out.println("check point");

        assertTrue(closedSet.delete(num.toString()));
        assertFalse(closedSet.delete(num.toString()));
        assertFalse(closedSet.contains(num.toString()));
        System.out.println("check point");




//        String[] array = new String[11];
//        for (int i = 0; i < 11; i++) {
//            array[i] = "" + i;
//        }
//        ClosedHashSet dataOpenSet = new ClosedHashSet(array);
//        assertEquals(16, dataOpenSet.capacity());
//
//        array = new String[12];
//        for (int i = 0; i < 12; i++) {
//            array[i] = "" + i;
//        }
//        dataOpenSet = new ClosedHashSet(array);
//        assertEquals(16, dataOpenSet.capacity());
//
//        array = new String[13];
//        for (int i = 0; i < 13; i++) {
//            array[i] = "" + i;
//        }
//        dataOpenSet = new ClosedHashSet(array);
//        assertEquals(32, dataOpenSet.capacity());

//
//        /** Tests the default constructed sets. */
//        int capacity = (int) (SimpleHashSet.INITIAL_CAPACITY * SimpleHashSet.DEFAULT_HIGHER_CAPACITY) - 1;
//        String[] stringArr = new String[capacity];
//        for (int i = 0; i < capacity; i++) {
//            stringArr[i] = getRandomString();
//        }
//
//
//        for (String string : stringArr) {
//            hashSet.add(string);
//            openSet.add(string);
//            closedSet.add(string);
//
//            assertEquals(hashSet.contains(string), openSet.contains(string));
//            assertEquals(hashSet.contains(string), closedSet.contains(string));
//
//            assertEquals(SimpleHashSet.INITIAL_CAPACITY, openSet.capacity());
//            assertEquals(SimpleHashSet.INITIAL_CAPACITY, closedSet.capacity());
//
//            assertEquals(hashSet.size(), openSet.size());
//            assertEquals(hashSet.size(), closedSet.size());
//        }
//
//        // cross the threshold of load factor
//
//        String str = getRandomString();
//        hashSet.add(str);
//        openSet.add(str);
//        closedSet.add(str);
//        assertEquals(hashSet.contains(str), openSet.contains(str));
//        assertEquals(hashSet.contains(str), closedSet.contains(str));
////        assertEquals(SimpleHashSet.INITIAL_CAPACITY * SimpleHashSet.TABLE_SIZE_FACTOR, openSet.capacity());
////        assertEquals(SimpleHashSet.INITIAL_CAPACITY * SimpleHashSet.TABLE_SIZE_FACTOR, closedSet.capacity());
//        assertEquals(hashSet.size(), openSet.size());
//        assertEquals(hashSet.size(), closedSet.size());
//
//        //calculate how many should be removed to cross threshold back
//        int amount = (int) (SimpleHashSet.INITIAL_CAPACITY * (SimpleHashSet.DEFAULT_HIGHER_CAPACITY -
//                (SimpleHashSet.TABLE_SIZE_FACTOR * SimpleHashSet.DEFAULT_LOWER_CAPACITY)));
//        for (int i = 0; i < amount; i++) {
//            String string = stringArr[i];
//
//            openSet.delete(string);
//            closedSet.delete(string);
//            hashSet.remove(string);
//
//            assertEquals(hashSet.contains(string), openSet.contains(string));
//            assertEquals(hashSet.contains(string), closedSet.contains(string));
//
//            String s = getRandomString();
//            if (!hashSet.contains(s)) {
//                assertEquals(hashSet.contains(s), openSet.contains(s));
//                assertEquals(hashSet.contains(s), closedSet.contains(s));
//            }
//
//            assertEquals(hashSet.size(), openSet.size());
//            assertEquals(hashSet.size(), closedSet.size());
//        }
//
//        assertEquals(hashSet.contains(str), openSet.contains(str));
//        assertEquals(hashSet.contains(str), closedSet.contains(str));
//        assertEquals(hashSet.size(), openSet.size());
//        assertEquals(hashSet.size(), closedSet.size());
//
////        assertEquals(SimpleHashSet.INITIAL_CAPACITY, openSet.capacity());
////        assertEquals(SimpleHashSet.INITIAL_CAPACITY, closedSet.capacity());
//
//        //****************************************************************************
//        //****************************************************************************
//        //****************************** N A D A V ' S *******************************
//        //****************************************************************************
//        //****************************************************************************
//
//        String[] words = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
//        OpenHashSet set = new OpenHashSet();
//        System.out.println("checking default arguments");
//        assert (set.getLowerLoadFactor() == 0.25);
//        assert (set.getUpperLoadFactor() == 0.75);
//        assert (set.size() == 0);
//        assert (set.capacity() == 16);
//
//        System.out.println("adding " + words[0] + " to set");
//        assert (set.add(words[0]));
//        assert (set.contains(words[0]));
//        assert (set.size() == 1);
//        assert (set.capacity() == 16);
//
//        System.out.println("try adding " + words[0] + " to set again");
//        assert (!set.add(words[0]));
//        assert (set.contains(words[0]));
//        assert (set.size() == 1);
//        assert (set.capacity() == 16);
//
//        System.out.println("deleting " + words[0] + " from set capacity should be 8 ");
//        assert (set.delete(words[0]));
//        assert (set.size() == 0);
//        assert (set.capacity() == 8);
//
//        System.out.println("try deleting " + words[0] + " from set again");
//        assert (!set.delete(words[0]));
//        assert (set.size() == 0);
//        assert (set.capacity() == 8);
//
//        System.out.println("adding 13 letters to set");
//        for (String letter : words) {
//            assert (set.add(letter));
//        }
//        System.out.println("checking if set contains the letters and capacity should be 32");
//        for (String letter : words) {
//            assert (set.contains(letter));
//        }
//        assert (set.size() == 13);
//        assert (set.capacity() == 32);
//
//        System.out.println("deleting 6 letters from set");
//        for (int i = 0; i < 6; i++) {
//            assert (set.delete(words[i]));
//        }
//        System.out.println("checking if set contains the letters and capacity should be 16");
//        for (int i = 0; i < 6; i++) {
//            assert (!set.contains(words[i]));
//        }
//        for (int i = 6; i < words.length; i++) {
//            assert (set.contains(words[i]));
//        }
//        assert (set.size() == 7);
//        assert (set.capacity() == 16);
//
//        System.out.println("deleting 4 letters from set");
//        for (int i = 6; i < 10; i++) {
//            assert (set.delete(words[i]));
//        }
//
//        System.out.println("checking if set contains the letters and capacity should be 8");
//        for (int i = 6; i < 10; i++) {
//            assert (!set.contains(words[i]));
//        }
//        assert (set.capacity() == 8);
//        assert (set.size() == 3);
//
//        System.out.println("deleting 2 letters from set");
//        for (int i = 10; i < 12; i++) {
//            assert (set.delete(words[i]));
//        }
//        System.out.println("capacity should be 4");
//        assert (set.capacity() == 4);
//        assert (set.size() == 1);
//
//        System.out.println("deleting 1 letters from set");
//        assert (set.delete(words[12]));
//
//        System.out.println("capacity should be 2");
//        assert (set.capacity() == 2);
//        assert (set.size() == 0);
//
//        System.out.println("adding letter and then deleting it capacity should be 1");
//        assert (set.add(words[0]));
//
//        assert (set.capacity() == 2);
//        assert (set.size() == 1);
//
//        assert (set.delete(words[0]));
//
//        assert (set.capacity() == 1);
//        assert (set.size() == 0);

//        String[] allWords1 = Ex4Utils.file2array("src/data1.txt");
//        String[] wordsData1 = new String[10000];
//        for(int i = 0; i < 10000; i++) wordsData1[i] = allWords1[i];
//
//        ClosedHashSet myClosedHash = new ClosedHashSet(wordsData1);
//        System.out.println("Data cunstructor: " + myClosedHash.size());
//
//        TreeSet<String> treeSet = new TreeSet<String>();
//        ClosedHashSet oneByOne = new ClosedHashSet();
//        for (String s : wordsData1) {
//            oneByOne.add(s);
//            treeSet.add(s);
//        }
//        System.out.println("One by one: " + oneByOne.size());
//        System.out.println("Java's Tree Set: " + treeSet.size());


    }
}
