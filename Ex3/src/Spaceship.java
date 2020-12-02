import oop.ex3.spaceship.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/** This class represents the spaceship object. It composited with onr member of "LongTermStorage" and a Map object
 * contains the "Locker" objects that belongs to the crew ("Has a-" relation). */
public class Spaceship {

    private final String name;
    private final Item[][] constraints;
    private final LongTermStorage lts;
    private final int maxLockersAmount;
    private int currentLockersAmount;
    private Map<Integer, HashSet<Locker>> lockers;

    /** This values represent possible failure exit numbers for internal functions. */
    public static final int SUCCESS = 0;
    public static final int ID_FAILURE = -1;
    public static final int CAPACITY_FAILURE = -2;
    public static final int NO_ROOM_FAILURE = -3;

    /* Build the crew members - for every member in the spaceship, initialize set of lockers to empty set. */
    private void buildLockersList(int[] idList){
        if (idList != null) {
            for (int i = 0; i < idList.length; i++) {
                this.lockers.put(idList[i], new HashSet<Locker>());
            }
        }
    }

    /** Default constructor. the constructor build a Map of the lockers mapped by ths crew id numbers.
     * Every member in the spaceship initialized to hold a 'null locker'
     *
     * @param name the name of the spaceship
     * @param crewIDs a list contains the crew members id numbers
     * @param numOfLockers maximum number of lockers allowed in the spaceship
     * @param constraints a list of contradicting items
     */
    public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constraints){
        this.name = name;
        this.constraints = constraints;
        this.maxLockersAmount = numOfLockers;
        this.lts = new LongTermStorage();
        this.lockers = new HashMap<Integer, HashSet<Locker>>();
        buildLockersList(crewIDs);
    }

    /** Get the Long Term Storage that associated with this spaceship. */
    public LongTermStorage getLongTermStorage() { return this.lts; }

    /** Create a new locker with a given capacity and associate it with a given crew member.
     *
     * @param crewID the ID number of the crew member
     * @param capacity the desired capacity of the new locker
     * @return
     * SUCCESS (0) if succeed
     * ID_FAILURE (-1) if id is not a valid id number, or it doesn't exist in the spaceship
     * CAPACITY FAILURE (-2) if the desired capacity is higher than the allowed capacity by the locker class
     * NO_ROOM_FAILURE (-3) if the spaceship already contains the maximum amount allowed of lockers
     */
    public int createLocker(int crewID, int capacity) {
        if (!this.lockers.containsKey(crewID)){
            return ID_FAILURE;
        }
        else if (capacity < 0) {
            return CAPACITY_FAILURE;
        }
        else if (this.maxLockersAmount == this.currentLockersAmount) {
            return NO_ROOM_FAILURE;
        }
        else {
            this.lockers.get(crewID).add(new Locker(this.lts, capacity, constraints));
            this.currentLockersAmount++;
            return SUCCESS;
        }
    }

    /** Returns an array contains the id numbers of the crew. */
    public int[] getCrewIDs() {
        int[] crewIDsArr = new int[this.lockers.size()];
        int i = 0;
        for (Integer id : this.lockers.keySet()) {
            crewIDsArr[i++] = id;
        }
        return crewIDsArr;
    }

    /** Return an array contains the lockers in th spaceship. */
    public Locker[] getLockers(){
        Locker[] lockersArr = new Locker[this.maxLockersAmount];
        int i = 0;
        for (HashSet<Locker> set : this.lockers.values()) {
            for (Locker locker : set) {
                lockersArr[i++] = locker;
            }
        }
        return lockersArr;
    }
}
