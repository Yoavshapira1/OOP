import java.util.LinkedList;
import java.math.*;

public class OpenHashSet extends SimpleHashSet {

    /* Implement a single linked list in the hash table. */
    private class Bucket {

        /* The linked list which gonna be used as a bucket in the table. */
        private LinkedList<String> list;

        /* Initialize the linked list to a new empty one. */
        public Bucket() {
            this.list = new LinkedList<String>();
        }
    }

    /* A string represents the name of this data structure. */
    private static final String THIS_CLASS_NAME = "OpenHashSet";

    /* An array of linked list of strings. */
    private Bucket[] tableArr = setBucketsArr(INITIAL_CAPACITY);

    /* Initialize empty buckets in an array of a given size */
    private Bucket[] setBucketsArr(int size) {
        Bucket[] arr = new Bucket[size];
        for (int i = 0; i < size; i++) {
            arr[i] = new Bucket();
        }
        return arr;
    }

    /* Implement a single operation of adding a new value that is NOT in the set yet. Expected time: O(1) */
    private void addSingleValue(String value) {
        int index = this.clamp(value.hashCode());
        this.tableArr[index].list.addFirst(value);
        this.curSize++;
    }

    /* Modify this table Set, by transferring the elements from this to a given anotherSet,
    and then change this values to the the values of anotherSet. */
    private void modifyThisSet(OpenHashSet anotherSet) {
        for (Bucket bucket : this.tableArr) {
            for (String element : bucket.list) {
                anotherSet.add(element);
            }
        }
        this.curCapacity = anotherSet.capacity();
        this.tableArr = anotherSet.tableArr;
        this.curSize = anotherSet.size();
    }

    /* Maintains the table *before* a successful adding operation, if any maintenance is needed. */
    private void maintenanceWhileAdding() {
        if (this.size() + 1 > this.upperLoadFactor * this.capacity()) {
            OpenHashSet newSet = new OpenHashSet(this.upperLoadFactor, this.lowerLoadFactor);
            if (newSet.setCapacity(this.curCapacity * TABLE_SIZE_FACTOR)) {
                modifyThisSet(newSet);
            }
        }
    }

    /* Implement a single operation of removing an exists value. Expected time: O(bucket length) */
    private void removeSingleValue(String value) {
        int index = this.clamp(value.hashCode());
        this.tableArr[index].list.remove(value);
        this.curSize--;
    }

    /* Maintains the table *after* a successful removing operation, if any maintenance is needed. */
    private void maintenanceWhileRemoving() {
        if (this.size() < this.lowerLoadFactor * this.capacity() && this.capacity() > 1) {
            OpenHashSet newSet = new OpenHashSet(this.upperLoadFactor, this.lowerLoadFactor);
            if (newSet.setCapacity(this.curCapacity / TABLE_SIZE_FACTOR)) {
                modifyThisSet(newSet);
            }
        }
    }

    /** A default constructor.
     * Constructs a new, empty table with default initial capacity upper load factor and lower load factor. */
    public OpenHashSet() {
        super();
    }

    /**Constructs a new, empty table with the specified load factors, and the default initial capacity. */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /** Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity, upper load factor and lower load factor */
    public OpenHashSet(String[] data) {
        super();
        for (String string : data) {
            this.add(string);
        }
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     *
     * @param index to fit
     * @return a clamped index
     */
    @Override
    protected int clamp(int index) { return index & (this.curCapacity - 1); }

    /**
     * Setter for the capacity. The setter can only be called when the set is empty.
     * This is only for inner use, and safely written, hence it's not throwing an exception.
     *
     * @param newCapacity the new capacity
     * @return True iff the capacity has changed
     */
    @Override
    protected boolean setCapacity(int newCapacity) {
        if (this.curSize == 0) {
            this.curCapacity = newCapacity;
            this.tableArr = this.setBucketsArr(this.capacity());
            return true;
        }
        return false;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (newValue != null) {
            if (!this.contains(newValue)) {
                this.maintenanceWhileAdding();
                this.addSingleValue(newValue);
                return true;
            }
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        if (searchVal != null) {
            int index = this.clamp(searchVal.hashCode());
            return this.tableArr[index].list.contains(searchVal);
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (this.contains(toDelete)) {
            this.removeSingleValue(toDelete);
            this.maintenanceWhileRemoving();
            return true;
        }
        return false;
    }
}
