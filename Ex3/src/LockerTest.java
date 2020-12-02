import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LockerTest implements SpaceshipTestsInterface {

    private final LongTermStorage lts = new LongTermStorage();
    private static int DEFAULT_CAPACITY;
    private static final String TESTS_PASSED = "--------------------Locker TESTS PASSED----------------------";

    @Override
    public String getRandomString() {
        String possibleChars = uppers + lowers + digits + chars;
        StringBuilder result = new StringBuilder();
        for (int i = randomGenerator.nextInt(lengthRange); i <= lengthRange; i++) {
            result.append(possibleChars.charAt(randomGenerator.nextInt(possibleChars.length())));
        }
        return result.toString();
    }

    /* Initialize the default capacity to be able to hold at least 1 item of each type from factory. */
    @BeforeClass
    public static void initDefaultCapacity() {
        DEFAULT_CAPACITY = 0;
        for(Item item : itemsList) {
            DEFAULT_CAPACITY += (item.getVolume() / TRANSFER_TO_LTS_THRESHOLD);
        }
        DEFAULT_CAPACITY++;
    }

    @Before
    public void resetLTS() {
        this.lts.resetInventory();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        System.out.println(TESTS_PASSED);
    }

    @Test
    public void addItem() {
        //check successful adding possibilities for every single item
        for (Item itemToAdd : itemsList) {
            if (itemToAdd.getVolume() > 0) {
                int capacity = (int)(itemToAdd.getVolume() / TRANSFER_TO_LTS_THRESHOLD) + 1; //can contain 1 item at most
                lts.resetInventory();
                Locker locker = new Locker(lts, capacity, null);

                assertEquals(SUCCESS, locker.addItem(itemToAdd, 0));
                assertEquals(FAILURE, locker.addItem(itemToAdd, -1));

                assertEquals(SUCCESS, locker.addItem(itemToAdd, 1));
                if (lts.getAvailableCapacity() >= itemToAdd.getVolume() * 2) {
                    assertEquals(WARNING_SUCCESS, locker.addItem(itemToAdd, 1));
                } else {
                    assertEquals(FAILURE, locker.addItem(itemToAdd, 1));
                }
            }
        }

        //failure transferring to LTS
        lts.resetInventory();
        Item itemToAdd = itemsList[Math.abs(randomGenerator.nextInt()) % itemsList.length];
        lts.addItem(itemToAdd, (int)(lts.getCapacity() / itemToAdd.getVolume()));
        Locker locker = new Locker(lts, DEFAULT_CAPACITY, null);
        assertEquals(FAILURE, locker.addItem(itemToAdd, (int)(DEFAULT_CAPACITY / itemToAdd.getVolume()) + 1));

        //check successful maintenance of inventory for adding possibilities
        itemToAdd = itemsList[Math.abs(randomGenerator.nextInt()) % itemsList.length];
        lts.resetInventory();
        int capacity = (int)(itemToAdd.getVolume() * 3 / TRANSFER_TO_LTS_THRESHOLD) + 1;  //can contain 3 items at most
        locker = new Locker(lts, capacity, null);
        assertEquals(SUCCESS, locker.addItem(itemToAdd,3));
        if (locker.addItem(itemToAdd, 1) == WARNING_SUCCESS) {
            assertTrue(locker.getItemCount(itemToAdd.getType()) <=
                    MAX_PERCENTAGE_FOR_TYPE * capacity / itemToAdd.getVolume());
        }
    }

    @Test
    public void getItemCount() {
        Locker locker = new Locker(lts, DEFAULT_CAPACITY, null);
        for (Item itemToAdd : itemsList) {
            assertEquals( 0, locker.getItemCount(itemToAdd.getType()));
            int amount = Math.abs(randomGenerator.nextInt()) % 5;       //between 0-5 items to add
            switch (locker.addItem(itemToAdd, amount)) {
                case SUCCESS:
                    assertEquals( amount, locker.getItemCount(itemToAdd.getType()));
                    break;
                case FAILURE:
                    assertEquals( 0, locker.getItemCount(itemToAdd.getType()));
                    break;
                case WARNING_SUCCESS:
                    assertTrue(locker.getItemCount(itemToAdd.getType()) <=
                            MAX_PERCENTAGE_FOR_TYPE * DEFAULT_CAPACITY / itemToAdd.getVolume());

            }
        }
    }

    @Test
    public void removeItem() {
        for (Item itemToAdd : itemsList ){
            int capacity = (int)(itemToAdd.getVolume() / TRANSFER_TO_LTS_THRESHOLD * 2); //able to contain max 2 same items
            lts.resetInventory();
            Locker locker = new Locker(lts, capacity, null);

            //remove 1 item
            assertEquals(FAILURE, locker.removeItem(itemToAdd, 1));
            assertEquals(SUCCESS, locker.removeItem(itemToAdd, 0));
            locker.addItem(itemToAdd, 1);
            assertEquals(FAILURE, locker.removeItem(itemToAdd, -1));
            assertEquals(SUCCESS, locker.removeItem(itemToAdd, 1));

            //remove 2 items
            locker.addItem(itemToAdd, 2);
            assertEquals(SUCCESS, locker.removeItem(itemToAdd, 2));

            //remove 3 items - should fail
            locker.addItem(itemToAdd, 3);
            assertEquals(FAILURE, locker.removeItem(itemToAdd, 3));
            assertTrue(locker.getItemCount(itemToAdd.getType()) < 3);

            //null remove
            assertEquals(FAILURE, locker.removeItem(null, 1));
        }
    }

    @Test
    public void getInventory() {
        Map<String, Integer> inventory = new HashMap<String, Integer>();
        Locker locker = new Locker(lts, DEFAULT_CAPACITY, constraints);
        assertEquals(locker.getInventory(), inventory);

        //check maintenance of inventory when adding items
        for (Item itemToAdd : itemsList) {
            int amount = Math.abs(randomGenerator.nextInt()) % 5;       //random amount between 0-5 items to add
            switch (locker.addItem(itemToAdd, amount)) {
                case SUCCESS:
                    if (amount != 0) {
                        inventory.put(itemToAdd.getType(), amount);
                    }
                    break;
                case FAILURE:
                    break;
                case WARNING_SUCCESS:
                    if (locker.getItemCount(itemToAdd.getType()) != 0) {
                        inventory.put(itemToAdd.getType(), locker.getItemCount(itemToAdd.getType()));
                        break;
                    }
                    assertEquals(inventory, locker.getInventory());
            }
        }

        //check maintenance of inventory when removing items
        for (Item itemToRemove : itemsList) {
            int amount = Math.abs(randomGenerator.nextInt()) % 5;       //random amount between 0-5 items to remove
            if (locker.removeItem(itemToRemove, amount) == SUCCESS) {
                if (amount != 0) {
                    inventory.put(itemToRemove.getType(), inventory.get(itemToRemove.getType()) - amount);
                    if (inventory.get(itemToRemove.getType()) <= 0) {
                        inventory.remove(itemToRemove.getType());
                    }
                }
                assertEquals(inventory, locker.getInventory());
            }
        }
    }

    @Test
    public void getCapacity() {
        //initialize capacity be able to hold 1 item
        Item item = itemsList[Math.abs(randomGenerator.nextInt()) % itemsList.length];
        int capacity = (int)(item.getVolume() / TRANSFER_TO_LTS_THRESHOLD) + 1;
        Locker locker = new Locker(lts, capacity, null);

        //check capacity doesn't change
        assertEquals(locker.getCapacity(), capacity);
        locker.addItem(item,1);
        assertEquals(locker.getCapacity(), capacity);
        locker.removeItem(item, 1);
        assertEquals(locker.getCapacity(), capacity);
    }

    @Test
    public void getAvailableCapacity() {
        Locker locker = new Locker(lts, DEFAULT_CAPACITY, null);
        int availableCapacity = DEFAULT_CAPACITY;
        int capacityInUse = 0;

        //check maintenance of inventory when adding items
        for (Item itemToAdd : itemsList ){
            int amount = Math.abs(randomGenerator.nextInt()) % 5;       //random amount between 0-5 items to add
            locker.addItem(itemToAdd, amount);
            availableCapacity -= locker.getItemCount(itemToAdd.getType()) * itemToAdd.getVolume();
            capacityInUse += locker.getItemCount(itemToAdd.getType()) * itemToAdd.getVolume();
            assertEquals(availableCapacity, locker.getAvailableCapacity());
            assertEquals(capacityInUse, locker.getCapacity() - locker.getAvailableCapacity());
        }

        //check maintenance of inventory when removing items
        for (Item itemToRemove : itemsList ){
            int amount = Math.abs(randomGenerator.nextInt()) % 5;       //random amount between 0-5 items to add
            if (locker.removeItem(itemToRemove, amount) == SUCCESS) {
                availableCapacity += amount * itemToRemove.getVolume();
                capacityInUse -= amount * itemToRemove.getVolume();
            }
            assertEquals(availableCapacity, locker.getAvailableCapacity());
            assertEquals(capacityInUse, locker.getCapacity() - locker.getAvailableCapacity());
        }

        int totalCapacityBothStorages = 0;
        for (Item item : itemsList) {
            totalCapacityBothStorages += (lts.getItemCount(item.getType()) +
                    locker.getItemCount(item.getType())) * item.getVolume();
        }

        assertEquals(totalCapacityBothStorages, lts.getCapacity() - lts.getAvailableCapacity() +
                locker.getCapacity() - locker.getAvailableCapacity());
    }

    @Test
    public void contradictedTests() {
        if (constraints != null) {
            Item itemToAdd = ItemFactory.createSingleItem(constraints[0][0].getType());
            Item contradicted = ItemFactory.createSingleItem(constraints[0][1].getType());
            int capacity = (int)(itemToAdd.getVolume() / TRANSFER_TO_LTS_THRESHOLD) +
                        (int)(contradicted.getVolume() / TRANSFER_TO_LTS_THRESHOLD) + 1;
            Locker locker = new Locker(lts, capacity, constraints);
            locker.addItem(itemToAdd, 1);
            assertEquals(locker.addItem(contradicted, 1), CONTRADICTED_ITEM);
            assertEquals(0, locker.getItemCount(contradicted.getType()));
            assertEquals(locker.addItem(ItemFactory.createSingleItem(constraints[0][1].getType()), capacity), CONTRADICTED_ITEM);
            assertEquals(0, locker.getItemCount(contradicted.getType()));
            locker.removeItem(itemToAdd, 1);
            assertEquals(SUCCESS, locker.addItem(contradicted,1));
            assertEquals(1, locker.getItemCount(contradicted.getType()));
        }
    }
}