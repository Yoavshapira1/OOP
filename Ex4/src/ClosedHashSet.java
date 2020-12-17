public class ClosedHashSet extends SimpleHashSet {

    //Todo: values of DELETED_FLAG and ANTI_DELETED_FLAG may change,
    //Todo: but their hashcode values have to differ by 1, and only by 1, from each other


    /* This flag tells that the slot contains it was occupied previously by a String which is NOT "". */
    private static final String DELETED_FLAG = "";

    /* This flag tells that the slot contains it was occupied previously by the String "1". */
    private static final String DELETED_ANTI_FLAG = "1";

    //Todo: Probing factor may change, but not recommended

    /* The values in the quadratic equation of the table. The equation for the i-th attempt to index element E is:
    hash(E) + (LINEAR_FACTOR * i) + (SQUARE_FACTOR * i^2). */
    private static final double LINEAR_FACTOR = (double)1 / TABLE_SIZE_FACTOR;
    private static final double SQUARE_FACTOR = (double)1 / TABLE_SIZE_FACTOR;

    /* A string represents the name of this data structure. */
    private static final String THIS_CLASS_NAME = "ClosedHashSet";

    /* This value tells if the reserved slot contains a real value (which is always the FLAG or ANTI-FLAG). */
    private boolean reservedSlotContainRealVal = false;

    /* This value counts the unsuccessful attempt to search an element in the table.
     * This value is a member, and not local, since I wasn't allowed to change the signature of the clamping method,
     * which uses this value in every call - but the signature only obtain integer. */
    private int attemptCounter = 0;

    /* This value represents the slot to be reserved to a String which equals to DELETE_FLAG. */
    private int reservedSlotForFlag;

    /* The array which contains the set values. */
    private String[] tableArr;

    /* Modify this table Set, by transferring the elements from this to a given anotherSet,
    and then change this values to the the values of anotherSet. */
    private void modifyThisSet(ClosedHashSet anotherSet) {
        for (int index = 0; index < this.tableArr.length; index++) {
            if (this.tableArr[index] != null) {
                if ((this.tableArr[index].equals(DELETED_FLAG) && index != this.reservedSlotForFlag) ||
                (index == this.reservedSlotForFlag && !this.reservedSlotContainRealVal)) {
                    continue;
                }
                anotherSet.add(this.tableArr[index]);
            }
        }
        this.curCapacity = anotherSet.curCapacity;
        this.tableArr = anotherSet.tableArr;
        this.reservedSlotContainRealVal = anotherSet.reservedSlotContainRealVal;
    }

    /* Implement a single operation of adding a new value that is NOT in the set yet. Expected time: O(1) */
    private void addSingleValue(String value) {
        int index = this.clamp(value.hashCode());
        this.tableArr[index] = value;
        this.curSize++;
    }

    /* Maintains the table *before* a successful adding operation, if any maintenance is needed. */
    private boolean maintenanceWhileAdding() {
        if (this.size() + 1 > this.upperLoadFactor * this.capacity()) {
            ClosedHashSet newSet = new ClosedHashSet(this.upperLoadFactor, this.lowerLoadFactor);
            newSet.setCapacity(this.curCapacity * TABLE_SIZE_FACTOR);
            modifyThisSet(newSet);
            return true;
        }
        return false;
    }

    /* Implement a single operation of removing an exists value. Removing an element cause a changes in the table:
     * All not-null elements in the compatible quadratic probe for this value, are to be moved so that the null
     * element will be the last one in the probe. */
    private void removeSingleValue(String value) {
        int index = this.clamp(value.hashCode());
        if (index == this.reservedSlotForFlag && this.reservedSlotContainRealVal) {
            this.tableArr[this.reservedSlotForFlag] = DELETED_ANTI_FLAG;
            this.reservedSlotContainRealVal = false;
        }
        else {
            this.tableArr[index] = DELETED_FLAG;
        }
        this.curSize--;
    }

    /* Maintains the table *after* a successful removing operation, if any maintenance is needed. */
    private void maintenanceWhileRemoving() {
        if (this.size() < this.lowerLoadFactor * this.capacity() && this.capacity() > 1) {
            ClosedHashSet newSet = new ClosedHashSet(this.upperLoadFactor, this.lowerLoadFactor);
            newSet.setCapacity(this.curCapacity / TABLE_SIZE_FACTOR);
            modifyThisSet(newSet);
        }
    }

    /* Initializes an empty array (contains null values). */
    private void arrayInit(int capacity) {
        this.tableArr = new String[capacity];
        this.attemptCounter = 0;
        this.reservedSlotForFlag = this.clamp(DELETED_FLAG.hashCode());
        this.tableArr[this.reservedSlotForFlag] = DELETED_ANTI_FLAG;
    }

    /* Check if the table contains the String which is the DELETED_FLAG. */
    private boolean searchForFlag() {
        return this.tableArr[this.reservedSlotForFlag].equals(DELETED_FLAG);
    }

    /* Check if the table contains the String which is the ANTI_DELETED_FLAG. */
    private boolean searchAntiFlag() {
        int flagIndex = this.clamp(DELETED_FLAG.hashCode());
        int antiFlagIndex = this.clamp(DELETED_ANTI_FLAG.hashCode());
        return (flagIndex != antiFlagIndex);
    }

    /* Add the DELETED_FLAG String to the table. */
    private boolean addFlag() {
        if (!this.reservedSlotContainRealVal) {
            this.tableArr[this.reservedSlotForFlag] = DELETED_FLAG;
            this.reservedSlotContainRealVal = true;
            this.curSize++;
            return true;
        }

        if (!this.tableArr[this.reservedSlotForFlag].equals(DELETED_FLAG)) {
            String prevValue = this.tableArr[this.reservedSlotForFlag];
            this.tableArr[this.reservedSlotForFlag] = DELETED_FLAG;
            return add(prevValue);
        }
        return false;
    }

    /* Special case of adding operation: When there is only 1 available slot. Can happen iff upperLoadFactor == 1.
    * In this case, and only in this case, add the value to the reserved slot - even if it's not the DELETE_FLAG. */
    private void addFullTableCase(String newValue) {
        this.tableArr[this.reservedSlotForFlag] = newValue;
        this.reservedSlotContainRealVal = true;
        this.curSize++;
    }

    /* Remove the DELETE_FLAG String from the table. */
    private boolean deleteFlag() {
        if (this.tableArr[this.reservedSlotForFlag].equals(DELETED_FLAG)) {
            this.tableArr[this.reservedSlotForFlag] = DELETED_ANTI_FLAG;
            this.reservedSlotContainRealVal = false;
            this.curSize--;
            return true;
        }
        return false;
    }

    /* Find whether a given value exists in the set or not. */
    private boolean find(String searchVal) {
        this.attemptCounter = 0;
        int index = this.clamp(searchVal.hashCode());

        //Run the loop till it gets to a null slot, or to the maximum possible attempts.
        //The maximal possible attempts == The Maximum amount of items the set can fit at the moment.
        while (this.tableArr[index] != null  && this.attemptCounter <= upperLoadFactor * curCapacity) {
            if (index == this.reservedSlotForFlag) {
                if (searchVal.equals(this.tableArr[index]) && this.reservedSlotContainRealVal){
                    return true;
                }
            }
            else if (this.tableArr[index].equals(searchVal)) {
                return true;
            }
            this.attemptCounter++;
            index = this.clamp(searchVal.hashCode());
        }
        return false;
    }

    /** A default constructor.
     * Constructs a new, empty table with default initial capacity, upper load factor  and lower load factor.
     */
    public ClosedHashSet() {
        super();
        arrayInit(INITIAL_CAPACITY);
    }

    /** Constructs a new, empty table with the specified load factors, and the default initial capacity. */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        arrayInit(INITIAL_CAPACITY);
    }

    /** Data constructor - builds the hash set by adding the elements one by one. Duplicate values are ignored.
     * The new table has the default values of initial capacity, upper load factor and lower load factor */
    public ClosedHashSet(String[] data) {
        super();
        arrayInit(INITIAL_CAPACITY);
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
    protected int clamp(int index) {
        return (int)(index + (LINEAR_FACTOR * this.attemptCounter) +
                (SQUARE_FACTOR * this.attemptCounter * this.attemptCounter)) & (this.curCapacity - 1);
    }

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
            arrayInit(newCapacity);
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
        if (!this.contains(newValue)) {
            if (maintenanceWhileAdding()) {
                this.contains(newValue);    // Changes the attempts Counter
            }                               // (If the table changed then the hash probe changed too)
            if (this.upperLoadFactor == 1 && this.curSize == this.curCapacity - 1 && !this.reservedSlotContainRealVal) {
                addFullTableCase(newValue);
                return true;
            }
            if (newValue.equals(DELETED_FLAG)) {
                return addFlag();
            }
            addSingleValue(newValue);
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set. Assumption for the correctness of the operation:
     * 1) The TABLE_SIZE_FACTOR is 2, and the quadratic equation variable are 1/2 - this property guarantee an empty
     * cell to be found.
     * 2) Every operation relates to the member "attemptCounter", needs first to call the contains methods.
     * since the methods reset the attemptCounter, it's guaranteed that the operations use the correct value.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        if (searchVal != null) {
            if (searchVal.equals(DELETED_FLAG)) {
                return searchForFlag();
            }
            return find(searchVal);
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
        if (toDelete != null) {
            if (toDelete.equals(DELETED_FLAG)) {
                return deleteFlag();
            }
            if (this.contains(toDelete)) {
                this.removeSingleValue(toDelete);
                this.maintenanceWhileRemoving();
                return true;
            }
        }
        return false;
    }
}

