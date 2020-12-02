import oop.ex3.searchengine.*;
import java.util.*;

public class BoopingSite{

    //--------------------------------Nested Classes to implements comparators-----------------------------------//

    /* Implements the comparator by priority of: 1) Distance, 2) Number of POI, 3) Alphabetically */
    private static class distanceComparator implements Comparator<Hotel> {

        private final double longitude;
        private final double latitude;
        private static final int LHS_IS_CLOSER = -1;
        private static final int RHS_IS_CLOSER = 1;
        private static final int EQUALS = 0;

        /* Compute a distance from the given point to a given hotel */
        private double getDistance(Hotel other){
            return Math.sqrt(Math.pow(this.latitude - other.getLatitude(), 2) +
                             Math.pow(this.longitude - other.getLongitude(), 2));
        }

        /* Construct the specific coordinate-related for this comparator. */
        public distanceComparator(double latitude, double longitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        @Override
        public int compare(Hotel hotel1, Hotel hotel2) {
            double result = (getDistance(hotel1) - getDistance(hotel2));
            if (result == EQUALS) {
                return (new pointOfInterestsComparator().compare(hotel1, hotel2));
            }
            if (result > EQUALS) {
                return RHS_IS_CLOSER;
            }
            return LHS_IS_CLOSER;
        }
    }

    /* Implements the comparator by number of POI order. */
    private static class pointOfInterestsComparator implements Comparator<Hotel> {
        private static final int EQUALS = 0;

        @Override
        public int compare(Hotel hotel1, Hotel hotel2) {
            int result = hotel1.getNumPOI() - hotel2.getNumPOI();
            if (result == EQUALS) {
                return (new alphabeticComparator()).compare(hotel1, hotel2);
            }
            return -result;
        }
    }

    /* Implements the comparator by alphabetic order. */
    private static class alphabeticComparator implements Comparator<Hotel> {

        @Override
        public int compare(Hotel hotel1, Hotel hotel2) {
            return hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
        }
    }

    /* Implements the comparator by priority: 1) Star rating, 2) Alphabetically. */
    private static class ratingAlphabeticComparator implements Comparator<Hotel> {

        @Override
        public int compare(Hotel hotel1, Hotel hotel2) {
            int result = hotel1.getStarRating() - (hotel2.getStarRating());
            if (result == 0) {
                return (new alphabeticComparator().compare(hotel1, hotel2));
            }
            return -result;
        }
    }

    //----------------------------------------Members & Private methods ---------------------------------------------//

    /** These values limit the possible values for longitude and latitude coordinates. */
    public static final double LONGITUDE_MIN = -180;
    public static final double LONGITUDE_MAX = 180;
    public static final double LATITUDE_MIN = -90;
    public static final double LATITUDE_MAX = 90;

    private ArrayList<Hotel> allHotels;

    /* Convert a given list to an array of Hotel. */
    private Hotel[] collectionToArray(Collection<Hotel> list) {
        Hotel[] resultArr = new Hotel[list.size()];
        int i = 0;
        for (Hotel hotel : list) {
            resultArr[i++] = hotel;
        }
        return resultArr;
    }

    private boolean checkCoordinates(double latitude, double longitude) {
        return (!(latitude > LATITUDE_MAX || latitude < LATITUDE_MIN ||
                longitude > LONGITUDE_MAX || longitude < LONGITUDE_MIN));
    }

    //-------------------------------------------Public methods -----------------------------------------------//

    /** This constructor receives as parameter a string, which is the name of the dataset.
     * If an exception is thrown - caused by null String or invalid file path - the hotels array will be null
     *
     * @param name name of the dataset
     */
    public BoopingSite(String name){
        try {
            this.allHotels = new ArrayList<Hotel>(Arrays.asList(HotelDataset.getHotels(name)));
        }
        catch (Exception e) {
            this.allHotels = null;
        }
    }

    /** This method returns an array of hotels located in the given city, sorted from the highest star-rating to the
     * lowest. Hotels that have the same rating will be organized according to the alphabet order of the hotel’s
     *  (property) name. In case there are no hotels in the given city, this method returns an empty array.
     *
     *  @param city name of the city to search in
     *  @return an array of hotel as described, null if no hotels were found */
    public Hotel[] getHotelsInCityByRating(String city) {
        if (this.allHotels != null && city != null) {
            TreeSet<Hotel> hotelsInTheCity = new TreeSet<Hotel>(new ratingAlphabeticComparator());
            for (Hotel hotel : this.allHotels) {
                if (hotel.getCity().equals(city)) {
                    hotelsInTheCity.add(hotel);
                }
            }
            return collectionToArray(hotelsInTheCity);
            }
        return new Hotel[0];
    }

     /** Returns an array of hotels, sorted according to their Euclidean distance from the given geographic
      * location, in ascending order. Hotels that are at the same distance from the given location are organized
      * according to the number of points-of-interest for which they are close to (in a decreasing order). In
      * case of illegal input, this method returns an empty array.
      *
      * @param latitude  latitude coordinate
      * @param longitude longitude coordinate
      * @return an array of hotel as described, null if no hotels were found */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        if (checkCoordinates(latitude, longitude) && this.allHotels != null) {
            Collections.sort(this.allHotels, new distanceComparator(latitude, longitude));
            return collectionToArray(this.allHotels);
        }
        return new Hotel[0];
    }

    /** This method returns an array of hotels in the given city, sorted according to their Euclidean distance
     * from the given geographic location, in ascending order. Hotels that are at the same distance from the
     * given location are organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order). In case of illegal input, this method returns an empty array.
     *
     * @param city name of the city to search in
     * @param latitude  latitude coordinate
     * @param longitude longitude coordinate
     * @return an array of hotel as described, null if no hotels were found */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        if (this.allHotels != null && city != null && (checkCoordinates(latitude, longitude))) {
            ArrayList<Hotel> resultArr = new ArrayList<Hotel>();
            for (Hotel hotel : getHotelsByProximity(latitude, longitude)) {
                if (hotel.getCity().equals(city)) {
                    resultArr.add(hotel);
                }
            }
            return collectionToArray(resultArr);
        }
        return new Hotel[0];
    }
}


