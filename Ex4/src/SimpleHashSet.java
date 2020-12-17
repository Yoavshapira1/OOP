public abstract class SimpleHashSet implements SimpleSet {

    /** default values for initialize. */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;   //Todo: value can be in [0,1]
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;    //Todo: value can be in [0,1]
    protected static final int INITIAL_CAPACITY = 16;

    /** This value represents the increasing & decreasing capacity factor. */
    static final int TABLE_SIZE_FACTOR = 2;                         //Todo: value can be bigger than or equals to 2

    /** The lower load factor of the set. */
    protected float lowerLoadFactor;

    /** The upper load factor of the set. */
    protected float upperLoadFactor;

    /** The current capacity - the size of the main array of the set. */
    protected int curCapacity;

    /** The current amount of items in the set */
    protected int curSize;

    /** Default constructor
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY. */
    protected SimpleHashSet() {
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.curCapacity = INITIAL_CAPACITY;
        this.curSize = 0;
    }

    /** Constructs a new hash set with capacity INITIAL_CAPACITY. */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.lowerLoadFactor = lowerLoadFactor;
        this.upperLoadFactor = upperLoadFactor;
        this.curCapacity = INITIAL_CAPACITY;
        this.curSize = 0;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     * @param index to fit
     * @return a clamped index
     */
    abstract protected int clamp(int index);

    /**
     * @return the current capacity (number of cells) of the table set
     */
    public int capacity() { return this.curCapacity; }

    /**
     * @return the lower load factor
     * */
    public float getLowerLoadFactor() { return this.lowerLoadFactor; }

    /**
     * @return the upper load factor
     */
    public float getUpperLoadFactor() { return this.upperLoadFactor; }

    /**
     * @return The number of elements currently in the set
     */
    public int size() { return this.curSize; }

    /** Setter for the capacity. The setter can only be called when the set is empty.
     *
     * @param newCapacity the new capacity
     * @return True iff the capacity has changed
     */
    abstract protected boolean setCapacity(int newCapacity);

}
