package tourguide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;
import tourguide.domain.User;
import tourguide.domain.UserReward;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the RewardsService.
 *
 * @see TourGuideService
 * @author Some TourGuide Developer
 * @author Yahia CHERIFI
 *
 */

@Service
public class RewardsServiceImpl implements RewardsService {

    /**
     * Statute miles per nautical mile.
     */
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    /**
     * proximity in miles.
     */
    private final int defaultProximityBuffer = 10;

    /**
     * proximity Buffer.
     */
    private int proximityBuffer = defaultProximityBuffer;

    /**
     * GpsUtil that will be injected.
     */
    private final GpsUtil gpsUtil;

    /**
     * The RewardCentral that will be injected.
     */
    private final RewardCentral rewardsCentral;

    /**
     * The executor service that allows using n number of threads.
     */
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(200);


    /**
     * Constructor injection.
     * @param pGpsUtil the gpsUtil.
     * @param pRewardCentral the RewardCentral
     */
    @Autowired
    public RewardsServiceImpl(final GpsUtil pGpsUtil,
                              final RewardCentral pRewardCentral) {
        this.gpsUtil = pGpsUtil;
        this.rewardsCentral = pRewardCentral;
    }

    /**
     * Sets proximity buffer.
     * @param pProximityBuffer the proximity buffer
     */
    public void setProximityBuffer(final int pProximityBuffer) {
        this.proximityBuffer = pProximityBuffer;
    }

    /**
     * Sets the default proximity buffer.
     */
    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }

    /**
     * Calculate rewards for a given user.
     * @param user the user to whom the rewards will be calculated
     */
    @Override
    public void calculateRewards(final User user) {
        List<VisitedLocation> userLocations = user.getVisitedLocations();
        List<Attraction> attractions = gpsUtil.getAttractions();

        for (VisitedLocation visitedLocation : userLocations) {
            for (Attraction attraction : attractions) {
                if (user.getUserRewards().stream()
                        .noneMatch(r -> r.getAttraction().attractionName
                                .equals(attraction.attractionName))
                        && nearAttraction(visitedLocation, attraction)) {
                    user.addUserReward(new UserReward(visitedLocation,
                            attraction, getRewardPoints(attraction, user)));
                }
            }
        }
    }

    /**
     * Calculate the users' rewards using n number of threads.
     * It improves the app performances
     * @param user the user to who rewards will be calculated
     */
    @Override
    public void calculateRewardsWithThread(final User user) {
        executorService.execute(() -> calculateRewards(user));
    }

    /**
     * Shutdown the executor service.
     */
    @Override
    public void shutDownExecutorService() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(Integer.MAX_VALUE,
                    TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException interruptedException) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();

        }
    }

    /**
     * Check whether a given location is close to a given attraction.
     * @param attraction the attraction
     * @param location the location
     * @return a boolean to indicate if a location is close to a given location
     */
    @Override
    public boolean isWithinAttractionProximity(final Attraction attraction,
                                               final Location location) {
        int attractionProximityRange = 200;
        return getDistance(
                attraction,
                location) > attractionProximityRange ? false : true;
    }

    /**
     * Get the distance that separates two locations.
     * @param loc1 location one
     * @param loc2 location two
     * @return the distance that separates two locations
     */
    @Override
    public double getDistance(final Location loc1, final Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2)
                * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }

    /**
     * Check if a given attraction is near to a given visited location.
     * @param visitedLocation the visited location
     * @param attraction the attraction
     * @return a boolean that indicates if a visited location
     * is near to a given attraction
     */
    private boolean nearAttraction(final VisitedLocation visitedLocation,
                                   final Attraction attraction) {
        return getDistance(
                attraction,
                visitedLocation.location) > proximityBuffer ? false : true;
    }

    /**
     * Get the reward points for a given user and a given attraction.
     * @param attraction the attraction
     * @param user the user
     * @return the number of reward points
     */
    @Override
    public int getRewardPoints(final Attraction attraction,
                                final User user) {
        return rewardsCentral
                .getAttractionRewardPoints(attraction.attractionId,
                        user.getUserId());
    }
}
