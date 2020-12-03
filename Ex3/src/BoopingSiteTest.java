import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import oop.ex3.searchengine.*;

import java.util.Random;

public class BoopingSiteTest {

    private static final int TEST_FACTOR = 10; //Todo: This factor determines how many times tests will be running
                                               //Todo: A test randomizes variables, hence more tests - more checks
    private static final String smallDataPath = "hotels_tst1.txt"; //not empty file -> not Null engine
    private static final String nullDataPath = "hotels_tst2.txt"; //empty file -> Null engine
    private static final String bigDataPath = "hotels_dataset.txt";
    private static final BoopingSite smallEngine = new BoopingSite(smallDataPath);
    private static final BoopingSite nullEngine = new BoopingSite(nullDataPath);
    private static final BoopingSite bigEngine = new BoopingSite(bigDataPath);
    private static final double LONGITUDE_MIN = BoopingSite.LONGITUDE_MIN;
    private static final double LONGITUDE_MAX = BoopingSite.LONGITUDE_MAX;
    private static final double LATITUDE_MIN = BoopingSite.LATITUDE_MIN;
    private static final double LATITUDE_MAX = BoopingSite.LATITUDE_MAX;
    private static final String allTestsPassed = "---------------------ALL TESTS PASSED-----------------------";
    private static final Random randomGenerator = new Random();
    private static final String uppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowers = "abcdefghijklmnopqrstuvwxyz";
    private static final String digits = "0123456789";
    private static final String chars = "/*-+.!@#$%^&*()_-+=]}[{<>~`";
    private static final int lengthRange = 7;

    /* Generate a random String in length 0 - this.lengthRange. */
    private static String getRandomString(){
        String possibleChars = uppers + lowers + digits + chars;
        StringBuilder result = new StringBuilder();
        for (int i = randomGenerator.nextInt(lengthRange); i <= lengthRange; i++) {
            result.append(possibleChars.charAt(randomGenerator.nextInt(possibleChars.length())));
        }
        return result.toString();
    }

    /* Compute the distance from a given point to a given hotel, to check the hotels sorting. */
    private double getDistance(double latitude, double longitude, Hotel other){
        return Math.sqrt(Math.pow(latitude - other.getLatitude(), 2) + Math.pow(longitude - other.getLongitude(), 2));
    }

    @AfterClass
    public static void tearDown() {
        System.out.println(allTestsPassed);
    }

    @Test
    public void getHotelsInCityByRating() {

        //null or invalid file path should result null array
        assertArrayEquals(new Hotel[0], new BoopingSite(null).getHotelsInCityByRating(getRandomString()));
        assertArrayEquals(new Hotel[0], new BoopingSite(getRandomString()).getHotelsInCityByRating(getRandomString()));

        //invalid or non exist city should result null array
        assertArrayEquals(new Hotel[0], smallEngine.getHotelsInCityByRating(null));
        assertArrayEquals(smallEngine.getHotelsInCityByRating(getRandomString()), new Hotel[0]);

        for (int k = 0; k < TEST_FACTOR; k++) {
            //------------------------------TESTS FOR THE SMALL DATA SET No.1------------------------------
            //valid file path should cause valid hotels list
            Hotel[] hotelsArr = HotelDataset.getHotels(smallDataPath);
            //randomize an exists city from the data
            String city;
            try {
                city = hotelsArr[Math.abs(randomGenerator.nextInt()) % hotelsArr.length].getCity();
            } catch (Exception e) {
                city = null;
            }

            //if city exists, hence array must be not Null
            Hotel[] arr = smallEngine.getHotelsInCityByRating(city);
            if (city != null) {
                assertNotNull(arr);
                //check correctness of the sorting order for this city
                for (int i = 0; i < arr.length - 1; i++) {
                    assertEquals(city, arr[i].getCity());
                    assertTrue(arr[i].getStarRating() >= arr[i + 1].getStarRating());
                    if (arr[i].getStarRating() == arr[i + 1].getStarRating()) {
                        assertTrue(arr[i].getPropertyName().compareTo(arr[i + 1].getPropertyName()) <= 0);
                    }
                }
            } else {
                assertArrayEquals(new Hotel[0], arr);
            }

            //------------------------------TESTS FOR THE SMALL DATA SET No.2------------------------------
            //valid file path should cause valid hotels list
            hotelsArr = HotelDataset.getHotels(nullDataPath);
            //randomize an exists city from the data
            try {
                city = hotelsArr[Math.abs(randomGenerator.nextInt()) % hotelsArr.length].getCity();
            } catch (Exception e) {
                city = null;
            }

            //if city exists, hence array must be not Null
            arr = nullEngine.getHotelsInCityByRating(city);
            if (city != null) {
                assertNotNull(arr);
                //check correctness of the sorting order for this city
                for (int i = 0; i < arr.length - 1; i++) {
                    assertEquals(city, arr[i].getCity());
                    assertTrue(arr[i].getStarRating() >= arr[i + 1].getStarRating());
                    if (arr[i].getStarRating() == arr[i + 1].getStarRating()) {
                        assertTrue(arr[i].getPropertyName().compareTo(arr[i + 1].getPropertyName()) <= 0);
                    }
                }
            } else {
                assertArrayEquals(new Hotel[0], arr);
            }
        }
    }

    @Test
    public void getHotelsByProximity() {

        //null BoopingSite and invalid input for the method should result a null array
        Hotel[] emptyArr = new Hotel[0];
        assertArrayEquals(emptyArr, smallEngine.getHotelsByProximity(LATITUDE_MAX + 1, LONGITUDE_MAX + 1));
        assertArrayEquals(emptyArr, smallEngine.getHotelsByProximity(LATITUDE_MIN - 1, LONGITUDE_MIN - 1));
        assertArrayEquals(emptyArr, smallEngine.getHotelsByProximity(LATITUDE_MAX + 1, LONGITUDE_MIN - 1));
        assertArrayEquals(emptyArr, smallEngine.getHotelsByProximity(LATITUDE_MIN - 1, LONGITUDE_MAX + 1));
        assertArrayEquals(emptyArr, smallEngine.getHotelsByProximity(LATITUDE_MIN - 1, 0));
        assertArrayEquals(emptyArr, smallEngine.getHotelsByProximity(0, BoopingSite.LONGITUDE_MAX + 1));
        BoopingSite nullEngine = new BoopingSite(null);
        assertArrayEquals(emptyArr, nullEngine.getHotelsByProximity(0, 0));

        for (int k = 0; k < TEST_FACTOR; k++)  {
            //------------------------------TESTS FOR THE SMALL DATA SET No.1------------------------------
            //valid input with not-Null engine results not Null array
            assertNotNull(smallEngine.getHotelsByProximity(0, 0));

            //check sorting from the null point of the axes (0,0)
            Hotel[] hotelsArr = smallEngine.getHotelsByProximity(0, 0);
            try {
                for (int i = 0; i < hotelsArr.length - 1; i++) {
                    double dist1 = getDistance(0, 0, hotelsArr[i]);
                    double dist2 = getDistance(0, 0, hotelsArr[i + 1]);
                    assertTrue(dist1 <= dist2);
                    if (dist1 == dist2) {
                        assertTrue(hotelsArr[i].getNumPOI() >= hotelsArr[i + 1].getNumPOI());
                    }
                }
            } catch (Exception e) {
                assertArrayEquals(emptyArr, hotelsArr);
            }

            //------------------------------TESTS FOR THE SMALL DATA SET No.1------------------------------
            hotelsArr = nullEngine.getHotelsByProximity(0, 0);
            try {
                for (int i = 0; i < hotelsArr.length - 1; i++) {
                    double dist1 = getDistance(0, 0, hotelsArr[i]);
                    double dist2 = getDistance(0, 0, hotelsArr[i + 1]);
                    assertTrue(dist1 <= dist2);
                    if (dist1 == dist2) {
                        assertTrue(hotelsArr[i].getNumPOI() >= hotelsArr[i + 1].getNumPOI());
                    }
                }
            } catch (Exception e) {
                assertArrayEquals(emptyArr, hotelsArr);
            }
        }
    }

    @Test
    public void getHotelsInCityByProximity() {
        for (int k = 0; k < TEST_FACTOR; k++) {
            //randomize an exists city
            Hotel[] hotelsArr = HotelDataset.getHotels(smallDataPath);
            String city = hotelsArr[Math.abs(randomGenerator.nextInt()) % hotelsArr.length].getCity();

            //null engine and invalid inputs for the method should result a null array
            Hotel[] emptyArr = new Hotel[0];
            assertArrayEquals(emptyArr, smallEngine.getHotelsInCityByProximity(city, LATITUDE_MAX + 1,
                    LONGITUDE_MAX + 1));
            assertArrayEquals(emptyArr, smallEngine.getHotelsInCityByProximity(city, LATITUDE_MIN - 1,
                    LONGITUDE_MIN - 1));
            assertArrayEquals(emptyArr, smallEngine.getHotelsInCityByProximity(city, LATITUDE_MAX + 1,
                    LONGITUDE_MIN - 1));
            assertArrayEquals(emptyArr, smallEngine.getHotelsInCityByProximity(city, LATITUDE_MIN - 1,
                    LONGITUDE_MAX + 1));
            assertArrayEquals(emptyArr, smallEngine.getHotelsInCityByProximity(city, LATITUDE_MIN - 1, 0));
            assertArrayEquals(smallEngine.getHotelsInCityByProximity(getRandomString(), 0, 0), new Hotel[0]);
            BoopingSite nullEngine = new BoopingSite(null);
            assertArrayEquals(emptyArr, nullEngine.getHotelsInCityByProximity(city, LATITUDE_MAX + 1,
                    LONGITUDE_MAX + 1));

            //------------------------------------TESTS FOR THE BIG DATA SET -----------------------------------
            //if city exists, hence array must be not Null
            hotelsArr = HotelDataset.getHotels(bigDataPath);
            city = hotelsArr[Math.abs(randomGenerator.nextInt()) % hotelsArr.length].getCity();

            //check from null point - start axes point (0,0)
            Hotel[] sortedHotels = bigEngine.getHotelsInCityByProximity(city, 0, 0);
            if (city != null) {
                assertNotNull(sortedHotels);
                //check correctness of the sorting order for this city
                for (int i = 0; i < sortedHotels.length - 1; i++) {
                    assertEquals(city, sortedHotels[i].getCity());
                    double dist1 = getDistance(0, 0, sortedHotels[i]);
                    double dist2 = getDistance(0, 0, sortedHotels[i + 1]);
                    assertTrue(dist1 <= dist2);
                    if (dist1 == dist2) {
                        assertTrue(sortedHotels[i].getNumPOI() >= sortedHotels[i + 1].getNumPOI());
                    }
                }
            } else {
                assertArrayEquals(emptyArr, sortedHotels);
            }

            //check from maximum coordinate point with random exists city
            city = hotelsArr[Math.abs(randomGenerator.nextInt()) % hotelsArr.length].getCity();
            sortedHotels = bigEngine.getHotelsInCityByProximity(city, LATITUDE_MAX, LONGITUDE_MAX);
            if (city != null) {
                assertNotNull(sortedHotels);
                //check correctness of the sorting order for this city
                for (int i = 0; i < sortedHotels.length - 1; i++) {
                    assertEquals(city, sortedHotels[i].getCity());
                    double dist1 = getDistance(LATITUDE_MAX, LONGITUDE_MAX, sortedHotels[i]);
                    double dist2 = getDistance(LATITUDE_MAX, LONGITUDE_MAX, sortedHotels[i + 1]);
                    assertTrue(dist1 <= dist2);
                    if (dist1 == dist2) {
                        assertTrue(sortedHotels[i].getNumPOI() >= sortedHotels[i + 1].getNumPOI());
                    }
                }
            } else {
                assertArrayEquals(emptyArr, sortedHotels);
            }

            //check from minimum coordinate point with random exists city
            city = hotelsArr[Math.abs(randomGenerator.nextInt()) % hotelsArr.length].getCity();
            sortedHotels = bigEngine.getHotelsInCityByProximity(city, LATITUDE_MIN, LONGITUDE_MIN);
            if (city != null) {
                assertNotNull(sortedHotels);
                //check correctness of the sorting order for this city
                for (int i = 0; i < sortedHotels.length - 1; i++) {
                    assertEquals(city, sortedHotels[i].getCity());
                    double dist1 = getDistance(LATITUDE_MIN, LONGITUDE_MIN, sortedHotels[i]);
                    double dist2 = getDistance(LATITUDE_MIN, LONGITUDE_MIN, sortedHotels[i + 1]);
                    assertTrue(dist1 <= dist2);
                    if (dist1 == dist2) {
                        assertTrue(sortedHotels[i].getNumPOI() >= sortedHotels[i + 1].getNumPOI());
                    }
                }
            } else {
                assertArrayEquals(emptyArr, sortedHotels);
            }
        }
    }
}