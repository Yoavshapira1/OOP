import oop.ex3.spaceship.*;
import java.util.Random;

public interface SpaceshipTestsInterface {

    /** These 3 values are trinary values for telling a successful or not operation. */
    int FAILURE = Storage.FAILURE;   //operation failed
    int SUCCESS = Storage.SUCCESS;  //operation succeed
    int WARNING_SUCCESS = Storage.WARNING_SUCCESS;    //operation succeed with a warning
    int CONTRADICTED_ITEM = Storage.CONTRADICTED_ITEM;

    /** More values for error indication in Spaceship class. */
    int ID_FAILURE = Spaceship.ID_FAILURE;
    int CAPACITY_FAILURE = Spaceship.CAPACITY_FAILURE;
    int NO_ROOM_FAILURE = Spaceship.NO_ROOM_FAILURE;

    /** This value represents the threshold (percentages of capacity) before an item move to the long term storage [LTS]. */
    double TRANSFER_TO_LTS_THRESHOLD = Locker.TRANSFER_TO_LTS_THRESHOLD;

    /** This value represents the amount (percentages of capacity) that item takes, after transferring to the LTS. */
    double MAX_PERCENTAGE_FOR_TYPE = Locker.MAX_PERCENTAGE_FOR_TYPE;

    /** These values determines the vast of the tests: bigger number - more tests. */
    int TOTAL_TESTS_AMOUNT = 100;   //number ot tests
    int TESTS_FACTOR = 10;          //factor for nested parameters like: Items[] length, Capacity size, etc.

    /** Set the neccessary static variables for tests from the factory + random generator. */
    Random randomGenerator = new Random();
    LongTermStorage lts = new LongTermStorage();
    Item[] itemsList = ItemFactory.createAllLegalItems();
    Item[][] constraints = ItemFactory.getConstraintPairs();

    /** parameters that determine the random variables in the tests. */
    String uppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowers = "abcdefghijklmnopqrstuvwxyz";
    String digits = "0123456789";
    String chars = "/*-+.!@#$%^&*()_-+=]}[{<>~`";
    int lengthRange = 7;

    /** Generate a random String in length 0 - this.lengthRange. */
    String getRandomString();
}
