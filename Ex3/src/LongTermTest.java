import org.junit.AfterClass;
import org.junit.Test;
import java.util.Map;
import java.util.HashMap;
import oop.ex3.spaceship.*;
import static org.junit.Assert.*;

public class LongTermTest implements SpaceshipTestsInterface {

    private static final String TESTS_PASSED = "--------------------LongTermStorage TESTS PASSED----------------------";

    @Override
    public String getRandomString() {
        String possibleChars = uppers + lowers + digits + chars;
        StringBuilder result = new StringBuilder();
        for (int i = randomGenerator.nextInt(lengthRange); i <= lengthRange; i++) {
            result.append(possibleChars.charAt(randomGenerator.nextInt(possibleChars.length())));
        }
        return result.toString();
    }
    @AfterClass
    public static void tearDown() throws Exception {
        System.out.println(TESTS_PASSED);
    }

    @Test
    public void resetInventory() {
        LongTermStorage lts = new LongTermStorage();
        assertNotNull(lts);
        assertEquals(lts.getAvailableCapacity(), lts.getCapacity());
        assertTrue(lts.getInventory().isEmpty());
    }

    @Test
    public void addItem() {
        LongTermStorage lts = new LongTermStorage();
        for (Item itemToAdd : itemsList) {
            //normal case
            if (lts.getAvailableCapacity() >= itemToAdd.getVolume()) {
                assertEquals(SUCCESS, lts.addItem(itemToAdd, 1));
            }
            else {
                assertEquals(FAILURE, lts.addItem(itemToAdd, 1));
            }

            //null case - n = 0, should success for any item and capacity
            LongTermStorage ltsNullAdd = new LongTermStorage();
            assertNotNull(ltsNullAdd);
            assertEquals(SUCCESS, ltsNullAdd.addItem(itemToAdd, 0));
            assertEquals(ltsNullAdd.getAvailableCapacity(), ltsNullAdd.getCapacity());

            //invalid input - n < 0. should fail
            assertEquals(FAILURE, ltsNullAdd.addItem(itemToAdd, -1));
            assertEquals(ltsNullAdd.getAvailableCapacity(), ltsNullAdd.getCapacity());

            //over the available capacity
            int n = (int) (lts.getAvailableCapacity() / itemToAdd.getVolume()) + 1;
            assertEquals(FAILURE, lts.addItem(itemToAdd, n));
        }
    }

    @Test
    public void getItemCount() {
        //add all possible items and check correctness of the function
        LongTermStorage lts = new LongTermStorage();
        for (Item itemToAdd : itemsList) {
            assertEquals(0, lts.getItemCount(itemToAdd.getType()));
            int amount = 0;
            while (lts.getAvailableCapacity() >= itemToAdd.getVolume()) {
                lts.addItem(itemToAdd, 1);
                assertEquals(amount + 1, lts.getItemCount(itemToAdd.getType()));
                amount++;
            }
            assertEquals(amount, lts.getItemCount(itemToAdd.getType()));
        }

        assertEquals(0, lts.getItemCount(getRandomString()));
        lts.resetInventory();
        //check maintenance of the function
        for (Item itemToAdd : itemsList) {
            assertEquals(0, lts.getItemCount(getRandomString()));
        }
    }

    @Test
    public void getInventory() {
        //initialize elements
        LongTermStorage lts = new LongTermStorage();
        Map<String, Integer> inventory = new HashMap<String, Integer>();
        assertEquals(lts.getInventory(), inventory);

        //add to lts AND to inventory
        for (Item itemToAdd : itemsList){
            if (lts.getAvailableCapacity() >= itemToAdd.getVolume()) {
                assertEquals(SUCCESS, lts.addItem(itemToAdd, 1));
                inventory.put(itemToAdd.getType(), 1);
            }
            assertEquals(lts.getInventory(), inventory);
        }
        assertEquals(lts.getInventory(), inventory);
    }

    @Test
    public void getCapacity() {
        //capacity should be a static final member. these tests check that is never change across instances of LTS
        LongTermStorage lts1 = new LongTermStorage();
        LongTermStorage lts2 = new LongTermStorage();
        assertEquals(lts1.getCapacity(), lts2.getCapacity());
        for (Item itemToAdd : itemsList) {
            lts1.addItem(itemToAdd, 1);
            assertEquals(lts1.getCapacity(), lts2.getCapacity());
            lts1.addItem(itemToAdd, 0);
            assertEquals(lts1.getCapacity(), lts2.getCapacity());
            lts1.resetInventory();
            assertEquals(lts1.getCapacity(), lts2.getCapacity());
        }
    }

    @Test
    public void getAvailableCapacity() {
        //these tests check the maintenance of the available capacity in every variation
        LongTermStorage lts = new LongTermStorage();
        assertEquals(lts.getAvailableCapacity(), lts.getCapacity());
        int prevAvailableCapacity = lts.getAvailableCapacity();
        for (Item itemToAdd : itemsList) {
            //add from 0-5 items each iteration
            int amount = Math.abs(randomGenerator.nextInt()) % 5;
            if (lts.addItem(itemToAdd, amount) == SUCCESS) {
                prevAvailableCapacity -= (amount * itemToAdd.getVolume());
                assertEquals(prevAvailableCapacity, lts.getAvailableCapacity());
            }
        }
        int capacityInUse = 0;
        for (Item item : itemsList) {
            capacityInUse += lts.getItemCount(item.getType()) * item.getVolume();
        }
        assertEquals(capacityInUse, lts.getCapacity() - lts.getAvailableCapacity());
    }
}