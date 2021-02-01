package tourguide.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import tourguide.domain.User;

/**
 * The interface RewardsService.
 * It contains abstract methods that provide
 * the logic that will be used to operated on the data sent to and from
 * the controllers and the repository layer
 *
 * @author Yahia CHERIFI
 */

public interface RewardsService {

    /**
     * Calculate rewards for a given user.
     * @param user the user to whom the rewards will be calculated
     */
    void calculateRewards(User user);

    /**
     * Check whether a given location is close to a given attraction.
     * @param attraction the attraction
     * @param location the location
     * @return a boolean to indicate if a location is close to a given location
     */
    boolean isWithinAttractionProximity(Attraction attraction,
                                        Location location);

    /**
     * Get the distance that separates two locations.
     * @param loc1 location one
     * @param loc2 location two
     * @return the distance that separates two locations
     */
    double getDistance(Location loc1, Location loc2);
}