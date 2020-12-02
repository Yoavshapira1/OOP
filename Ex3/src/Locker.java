import oop.ex3.spaceship.*;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

/** This class represents the locker object. */
public class Locker implements Storage {

    private static final String ITEMS_DOES_NOT_EXIST = "the locker does not contain %d items of type %s";
    private static final String NEGATIVE_AMOUNT = "cannot remove a negative number of items of type %s";
    /** An error message in case of trying adding a contradicted item. */
    private static final String CONTRADICTION_FAILURE = "the locker cannot contain item of type %s," +
            " as it contains a contradicting item";
    private static final String INVALID_PARAMETERS = "Invalid parameters for locker";
    private final LongTermStorage longTermStorage;
    private Map<String, HashSet<String>> constraints;
    private final int capacity;
    private int availableCapacity;
    private Map<String, Integer> items;

    /** This value represents the threshold (percentages of capacity) before an item move to the long term storage [LTS]. */
    public static final double TRANSFER_TO_LTS_THRESHOLD = 0.5; //Todo: possible values = (0,1]

    /** This value represents the amount (percentages of capacity) that item takes, after transferring to the LTS. */
    public static final double MAX_PERCENTAGE_FOR_TYPE = 0.2;   //Todo: possible values = (0,1]

    /* Check if any transfer of specific item need to be done to the long term storage, in case of adding n copies. */
    private int howManyToLTS(Item item, int n) {
        int curAmount = 0;
        if (this.items.containsKey(item.getType())) {
            curAmount = this.items.get(item.getType());
        }
        if (item.getVolume() * (curAmount + n) > this.capacity * TRANSFER_TO_LTS_THRESHOLD) {
            int result = 0;
            while (result * item.getVolume() <= MAX_PERCENTAGE_FOR_TYPE * this.capacity) {
                result++;
            }
            return curAmount + n - result + 1;
        }
        return 0;
    }

    /* Builds the constraints map. */
    private void buildConstraintsSets(Item[][] constraints) {
        this.constraints = new HashMap<String, HashSet<String>>();
        if (constraints != null) {
            for (Item[] constraint : constraints) {
                if (!this.constraints.containsKey(constraint[0].getType())) {
                    this.constraints.put(constraint[0].getType(), new HashSet<String>());
                }
                this.constraints.get(constraint[0].getType()).add(constraint[1].getType());
                if (!this.constraints.containsKey(constraint[1].getType())) {
                    this.constraints.put(constraint[1].getType(), new HashSet<String>());
                }
                this.constraints.get(constraint[1].getType()).add(constraint[0].getType());
            }
        }
    }

    /* Check if a given item can be stored in the locker, type wise. */
    private boolean checkIfItemPermitted(Item item) {
        if (item != null) {
            if (this.constraints.containsKey(item.getType())) {
                for (String type : this.constraints.get(item.getType())) {
                    if (this.items.containsKey(type)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* Implement a single adding operation. */
    private void addToInventory(Item item, int n) {
        if (n != 0) {
            if (!this.items.containsKey(item.getType())) {
                this.items.put(item.getType(), n);
            } else {
                this.items.put(item.getType(), this.items.get(item.getType()) + n);
            }
            this.availableCapacity -= n * item.getVolume();
        }
    }

    /* Implement a single removing operation. */
    private int removeFromInventory(Item item, int n) {
        if (this.items.containsKey(item.getType())) {

            if (this.items.get(item.getType()) > n) {
                this.items.put(item.getType(), this.items.get(item.getType()) - n);
            }
            else if (this.items.get(item.getType()) == n) {
                this.items.remove(item.getType());
            }
            else {
                System.out.printf(FAILURE_MESSAGE + (ITEMS_DOES_NOT_EXIST) + "%n", n, item.getType());
                return FAILURE;
            }
            this.availableCapacity += item.getVolume() * n;
            return SUCCESS;
        }
        System.out.printf(FAILURE_MESSAGE + (ITEMS_DOES_NOT_EXIST) + "%n", n, item.getType());
        return FAILURE;
    }

    /* Handle transferring to LTS cases. */
    private void transferringToLTScases(Item item, int amountToLTS, int desiredToAdd) {
        if (desiredToAdd == amountToLTS) {
            this.longTermStorage.addItem(item, amountToLTS);
        }
        else if (desiredToAdd > amountToLTS) {
            addToInventory(item, desiredToAdd - amountToLTS);
            this.longTermStorage.addItem(item, amountToLTS);
        }
        else {
            removeFromInventory(item, amountToLTS - desiredToAdd);
            this.longTermStorage.addItem(item, amountToLTS);
        }
    }

    /* Handle the different cases possible in the adding operation, printing messages wise. */
    private int handleFlagCases(int flag, Item item, int desiredAmountToAdd) {
        switch (flag) {
            case FAILURE:
                System.out.printf(FAILURE_MESSAGE + (ADDING_FAILURE_MSG) + "%n", desiredAmountToAdd ,item.getType());
                break;
            case CONTRADICTED_ITEM:
                System.out.printf(FAILURE_MESSAGE + (CONTRADICTION_FAILURE) +"%n", item.getType());
                break;
            case WARNING_SUCCESS:
                transferringToLTScases(item, howManyToLTS(item, desiredAmountToAdd), desiredAmountToAdd);
                System.out.println(LONG_TERM_SUCCESSFUL_ADDING_MSG);
                break;
            default:
                addToInventory(item, desiredAmountToAdd);
                break;
        }
        return flag;
    }

    /** Default constructor. This build the sets of constraints items in a Map.
     *
     * @param lts a LongTermStorage object to be associated to this locker
     * @param capacity an integer indicates the capacity of the locker
     * @param constraints a list of the constraints items
     * @throws ExceptionInInitializerError if one of the parameters are invalid
     */
    public Locker (LongTermStorage lts, int capacity, Item[][] constraints)  throws ExceptionInInitializerError {
        if (lts == null || capacity < 0) {
            throw new ExceptionInInitializerError(INVALID_PARAMETERS);
        }
        this.longTermStorage = lts;
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.buildConstraintsSets(constraints);
        this.items = new HashMap<String, Integer>();
        }

    /** Adds n item of the given type to the locker.
     *
     * @param item the type of the given item
     * @param n the amount of the item to add to locker
     * @return 0 if success, -1 fail, 1 if success but divided with the long term storage.
     */
    public int addItem (Item item, int n) {
        if (item != null) {
            int flag;
            if (!checkIfItemPermitted(item) && n > 0) {     //item constraints an exists item
                flag = CONTRADICTED_ITEM;
            }
            else if (this.availableCapacity < n * item.getVolume() || n < 0) {  //no room or invalid amount
                flag = FAILURE;
            }
            else {
                int amountToLTS = this.howManyToLTS(item, n);
                if (amountToLTS == 0) {
                    flag = SUCCESS;
                }
                else {
                    if (this.longTermStorage.getAvailableCapacity() >= amountToLTS * item.getVolume()) {
                        flag = WARNING_SUCCESS;
                    }
                    else {
                        System.out.printf(FAILURE_MESSAGE + (ADDING_FAILURE_MSG) + "%n", amountToLTS, item.getType());
                        return FAILURE;     //LTS has no room - failure
                    }
                }
            }
            return this.handleFlagCases(flag, item, n);
        }
        return FAILURE;
    }

    /** Returns the number of items of specific type that the locker contains.
     * @param type the requested type item
     * @return current quantity of item in the locker, 0 if type is null or not exists
     */
    public int getItemCount(String type) {
        if (type == null) {
            return 0;
        }
        if (!this.items.containsKey(type)) {
            return 0;
        }
        return this.items.get(type);
    }

    /** Removes n items of a given type. if n = 0, operation successes.
     *
     * @param item the specific type of item to be removed
     * @param n amount of item desired to remove
     * @return success (0) if successful removing, failure (-1) otherwise
     */
    public int removeItem(Item item, int n) {
        if (item == null) {
            return FAILURE;
        }
        if (n < 0) {
            System.out.printf(FAILURE_MESSAGE + (NEGATIVE_AMOUNT) + "%n", item.getType());
            return FAILURE;
        }
        if (n == 0) {
            return SUCCESS;
        }
        return removeFromInventory(item, n);
    }

    /** Returns a map of all items in the locker and their respective quantities. */
    public Map<String, Integer> getInventory() { return this.items; }

    /** Return the capacity of the locker. */
    public int getCapacity() { return this.capacity; }

    /** Return the current available capacity in the ocker. */
    public int getAvailableCapacity() { return this.availableCapacity; }
}
