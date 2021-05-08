package tourguide.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourguide.domain.User;
import tourguide.dto.CurrentLocationDTO;
import tourguide.dto.NearestAttractionsDTO;
import tourguide.user.UserReward;
import tripPricer.Provider;

import java.util.List;

/**
 * The interface TourGuideService.
 * It contains abstract methods that provide
 * the logic that will be used to operated on the data sent to and from
 * the controllers and the repository layer
 *
 * @author Yahia CHERIFI
 */

public interface TourGuideService {

    /**
     * Get all the rewards of a given user.
     * @param user the user.
     * @return a list that contains all the user rewards
     */
    List<UserReward> getUserRewards(User user);

    /**
     * Get the user's location.
     * @param user the user
     * @return the user's location
     */
    VisitedLocation getUserLocation(User user);

    /**
     * Get a user by username.
     * @param userName the user's username
     * @return the found user
     */
    User getUser(String userName);

    /**
     * Get a list of all the users.
     * @return a list of all the users
     */
    List<User> getAllUsers();

    /**
     * Add a new user.
     * @param user the user that will be added
     */
    void addUser(User user);

    /**
     * Get the trip deals of a given user.
     * @param user the user
     * @return a list of all trip deals
     */
    List<Provider> getTripDeals(User user);

    /**
     * Track the location of a given user.
     * @param user the user that will be tracked
     * @return the location visited by the user
     */
    VisitedLocation trackUserLocation(User user);

    /**
     * Track the location of a given user by using n number of threads.
     * It improves the app performances
     * @param user the user that will be tracked
     */
    void trackUserLocationWithThread(User user);

    /**
     * Shutdown the Executor service.
     */
    void shutDownExecutorService();
    /**
     * Get the 5 nearby attractions of given visited location.
     * @param visitedLocation the visited location
     * @return a list that contains 5 nearby attractions
     */
    List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

    /**
     * Get all attractions and sort them by distance(from closest to distant).
     * @param visitedLocation the current user location
     * @param user the user to whom distance will be calculated
     * @return a list of all attractions sorted by distance
     */
    List<NearestAttractionsDTO> attractionsFromClosestToDistant(
            VisitedLocation visitedLocation, User user);

    /**
     * Get the five closest tourist attractions to a given visited location.
     * @param visitedLocation the visited location
     * @param user the user to whom distance will be calculated
     * @return a list that contains 5 tourist attractions
     */
    List<NearestAttractionsDTO> getFiveCloseAttractionsToUser(
            VisitedLocation visitedLocation, User user);

    /**
     * Get the current location of all users.
     * @return a list that contains the users' current location
     */
    List<CurrentLocationDTO> getAllUsersLocations();
}
