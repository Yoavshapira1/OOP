import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import java.util.TreeSet;
import static org.junit.Assert.*;

public class SpaceshipTest implements SpaceshipTestsInterface {

    private int[] crewId;
    private Spaceship normalSpaceship;
    private Spaceship noCrewSpaceship;
    private Spaceship noConstraintsSpaceship;
    private Spaceship noLockersSpaceship;
    private int validID;
    private int invalidID;
    private int validCapacity;
    private int invalidCapacity;
    private int numOfLockers;
    private static final String TESTS_PASSED = "--------------------Spaceship TESTS PASSED----------------------";

    /* Generate an invalid ID number for false negative tests. */
    private int getInvalidID() {
        int invalidID = randomGenerator.nextInt();
        while (true) {
            int i;
            for (i = 0; i < this.crewId.length; i++) {
                if (this.crewId[i] == invalidID) {
                    break;
                }
            }
            if (i == this.crewId.length) {
                break;
            }
            invalidID = randomGenerator.nextInt();
        }
        return invalidID;
    }

    @Override
    public String getRandomString() {
        String possibleChars = uppers + lowers + digits + chars;
        StringBuilder result = new StringBuilder();
        for (int i = randomGenerator.nextInt(lengthRange); i <= lengthRange; i++) {
            result.append(possibleChars.charAt(randomGenerator.nextInt(possibleChars.length())));
        }
        return result.toString();
    }

    /* Reset the members before each test to guarantee hermetic tests. */
    @Before
    public void setUp() throws Exception {

        //initialize the private members to random values
        crewId = new int[TESTS_FACTOR];
        for (int i = 0; i < crewId.length; i++) {
            crewId[i] = randomGenerator.nextInt();
        }

        this.validID = this.crewId[Math.abs(randomGenerator.nextInt() % this.crewId.length)];
        this.invalidID = getInvalidID();
        this.validCapacity = Math.abs(randomGenerator.nextInt()) + 1;
        this.invalidCapacity = -1;
        this.numOfLockers = Math.abs(randomGenerator.nextInt() % TESTS_FACTOR) + 1;

        //initialize the spaceships: one with valid crew members and one with empty array
        //and tests the constructors
        this.normalSpaceship = new Spaceship(getRandomString(), this.crewId, this.numOfLockers, constraints);
        this.noCrewSpaceship = new Spaceship(getRandomString(), null, this.numOfLockers, constraints);
        this.noConstraintsSpaceship = new Spaceship(getRandomString(), this.crewId, this.numOfLockers, null);
        this.noLockersSpaceship = new Spaceship(getRandomString(), this.crewId, 0, constraints);
        assertNotNull(this.normalSpaceship);
        assertNotNull(this.noCrewSpaceship);
        assertNotNull(this.noConstraintsSpaceship);
        assertNotNull(this.noLockersSpaceship);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        System.out.println(TESTS_PASSED);
    }

    @Test
    public void getLongTermStorage() {
        //check if every spaceship has a LTS
        assertNotNull(this.normalSpaceship.getLongTermStorage());
        assertNotNull(this.noCrewSpaceship.getLongTermStorage());
        assertNotNull(this.noConstraintsSpaceship.getLongTermStorage());

        //check that all LTS are different
        assertNotSame(this.noCrewSpaceship.getLongTermStorage(), this.noConstraintsSpaceship.getLongTermStorage());
        assertNotSame(this.normalSpaceship.getLongTermStorage(), this.noConstraintsSpaceship.getLongTermStorage());
        assertNotSame(this.noCrewSpaceship.getLongTermStorage(), this.normalSpaceship.getLongTermStorage());
    }

    @Test
    public void createLocker() {

        //check invalid id and success operation with priorities of the errors
        assertEquals(SUCCESS, this.normalSpaceship.createLocker(this.validID, this.validCapacity));
        assertEquals(ID_FAILURE, this.normalSpaceship.createLocker(this.invalidID, this.validCapacity));
        assertEquals(ID_FAILURE, this.normalSpaceship.createLocker(this.invalidID, this.invalidCapacity)); //priority
        assertEquals(CAPACITY_FAILURE, this.normalSpaceship.createLocker(this.validID, this.invalidCapacity));
        assertEquals(ID_FAILURE, this.noCrewSpaceship.createLocker(this.validID, this.validCapacity));
        assertEquals(ID_FAILURE, this.noCrewSpaceship.createLocker(this.validID, this.invalidCapacity));  //priority
        assertEquals(NO_ROOM_FAILURE, this.noLockersSpaceship.createLocker(this.validID, this.validCapacity));

        //check when spaceship already has the maximum amount of lockers and this value is not 0
        int maximumLockersAmount = Math.abs(randomGenerator.nextInt() % TESTS_FACTOR);
        Spaceship maxLockerAmountSpaceship = new Spaceship(getRandomString(), this.crewId,
                maximumLockersAmount, constraints);
        for(int i = 0; i < maximumLockersAmount; i++) {
            assertEquals(SUCCESS, maxLockerAmountSpaceship.createLocker(this.validID, this.validCapacity));
        }
        assertEquals(NO_ROOM_FAILURE, maxLockerAmountSpaceship.createLocker(this.validID, this.validCapacity));
        assertEquals(ID_FAILURE, maxLockerAmountSpaceship.createLocker(this.invalidID, this.validCapacity)); //priority
        assertEquals(CAPACITY_FAILURE, maxLockerAmountSpaceship.createLocker(this.validID, this.invalidCapacity)); //priority
    }

    @Test
    public void getCrewIDs() {
        TreeSet<Integer> IDset = new TreeSet<Integer>();
        for (int id : this.crewId) {
            IDset.add(id);
        }
        for (int id : this.normalSpaceship.getCrewIDs()) {
            assertTrue(IDset.contains(id));
        }
        int[] emptyArr = {};
        assertArrayEquals(this.noCrewSpaceship.getCrewIDs(), emptyArr);
    }

    @Test
    public void getLockers() {
        //empty case
        assertArrayEquals(this.noLockersSpaceship.getLockers(), new Locker[0]);
        this.noLockersSpaceship.createLocker(this.validID, this.validCapacity);
        assertArrayEquals(this.noLockersSpaceship.getLockers(), new Locker[0]);

        //success case with adding a locker
        assertEquals(this.normalSpaceship.getLockers().length, this.numOfLockers);
        assertEquals(SUCCESS, this.normalSpaceship.createLocker(this.validID, this.validCapacity));
        assertEquals(this.normalSpaceship.getLockers().length, this.numOfLockers);
        assertNotNull(this.normalSpaceship.getLockers()[0]);

        //failed adding locker
        assertEquals(ID_FAILURE,this.noCrewSpaceship.createLocker(validID, validCapacity));
        assertArrayEquals(this.noCrewSpaceship.getLockers(), (new Locker[numOfLockers]));
    }
}