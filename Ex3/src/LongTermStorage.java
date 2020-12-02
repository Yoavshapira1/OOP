import oop.ex3.spaceship.*;
import java.util.HashMap;
import java.util.Map;

/** This class represents the long term storage object. */
public class LongTermStorage implements Storage {

    private static final int CAPACITY = 1000;
    private int availableCapacity;
    private Map<String, Integer> items;

    /** Default constructor. */
    public LongTermStorage() {
        this.availableCapacity = CAPACITY;
        this.items = new HashMap<String, Integer>();
    }

    /** Resets the long term storage inventory. */
    public void resetInventory() {
        this.items.clear();
        this.availableCapacity = CAPACITY;
    }

    /** Add n items of a specific type to the storage.
     *
     * @param item the type of the given item
     * @param n the amount of the item to add to locker
     * @return 0 for successful adding, otherwise -1
     */
    public int addItem(Item item, int n) {
        if (item != null) {
            if (n > 0){
                if (this.availableCapacity < item.getVolume() * n) {
                    return FAILURE;
                }
                if (!this.items.containsKey(item.getType())) {
                    this.items.put(item.getType(), n);
                } else {
                    this.items.put(item.getType(), this.items.get(item.getType()) + n);
                }
                this.availableCapacity -= item.getVolume() * n;
                return SUCCESS;
            }
            if (n == 0) {
                return SUCCESS;
            }
        }
        return FAILURE;
    }

    /** Get the number of items of a specific type that currently in the long term storage.
     *
     * @param type the type requested
     * @return the quantity of items in the storage, 0 if type is null or not exists
     */
    public int getItemCount (String type) {
        if (type == null) {
            return 0;
        }
        if (!this.items.containsKey(type)) {
            return 0;
        }
        return this.items.get(type);
    }

    /** Returns a map of all items in the storage and their respective quantities. */
    public Map<String, Integer> getInventory() { return this.items; }

    /** Return the capacity of the storage. */
    public int getCapacity() { return CAPACITY; }

    /** Return the current available capacity in the storage. */
    public int getAvailableCapacity() { return this.availableCapacity; }
}
