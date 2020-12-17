import java.util.Collection;

public class CollectionFacadeSet implements SimpleSet {

    /** The collection wrapped in the facade. */
    private final Collection<String> set;

    /** Default constructor. Creates a new facade wrapping the specified collection. */
    public CollectionFacadeSet (Collection<String> collection) {
        this.set = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if ( !this.set.contains(newValue) ) {
            return this.set.add(newValue);
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
        return this.set.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        return this.set.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.set.size();
    }
}
