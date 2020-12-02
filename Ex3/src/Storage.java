import oop.ex3.spaceship.Item;
import java.util.Map;

/** This Interface used by the storage tools in the program: The locker, and the Long Term Storage, that share
 * methods and static members. */
public interface Storage {

    /** These 3 values are trinary values for telling a successful or not operation. */
    int FAILURE = -1;           //operation failed
    int SUCCESS = 0;            //operation succeed
    int WARNING_SUCCESS = 1;    //operation succeed with a warning
    int CONTRADICTED_ITEM = -2; //adding an contradicted item failed

    /** These 3 strings are the possible messages to be print in case of a non-successful operation. */
    String FAILURE_MESSAGE = "Error: Your request cannot be completed at this time. Problem: ";
    String LONG_TERM_SUCCESSFUL_ADDING_MSG = "Warning: Action successful, but has caused items to be" +
            " moved to storage";
    String ADDING_FAILURE_MSG = "no room for %d items of type %s";

    /** Adds n item of the given type to the locker.
     *
     * @param item the type of the given item
     * @param n the amount of the item to add to locker
     * @return 0 if success, -1 fail, 1 if success but divided with the long term storage.
     */
    int addItem (Item item, int n);
    
    /** Returns the number of items of specific type that the locker contains.
     * @param type the requested type item
     * @return current quantity of item in the locker, failure(-1) if type is null or not exists
     */
    int getItemCount(String type);

    /** Returns a map of all items in the locker and their respective quantities. */
    Map<String, Integer> getInventory();

    /** Return the capacity of the locker. */
    int getCapacity();

    /** Return the current available capacity in the ocker. */
    int getAvailableCapacity();
    
}
