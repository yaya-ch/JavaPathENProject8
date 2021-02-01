package tourguide.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourguide.helper.InternalTestHelper;
import tourguide.tracker.Tracker;
import tourguide.domain.User;
import tourguide.user.UserReward;
import tripPricer.Provider;
import tripPricer.TripPricer;

/**
 * This class implements the TourGuideService.
 *
 * @see TourGuideService
 * @author Some TourGuide Developer
 * @author Yahia CHERIFI
 *
 */

@Service
public class TourGuideServiceImpl implements TourGuideService {

    /**
     * The class logger.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TourGuideServiceImpl.class);

    /**
     * The GpsUtil that will be injected.
     */
    private final GpsUtil gpsUtil;

    /**
     * The RewardService that will be injected.
     */
    private final RewardsService rewardsService;

    /**
     * The TripPricer.
     */
    private final TripPricer tripPricer = new TripPricer();

    /**
     * The Tracker.
     */
    private final Tracker tracker;

    /**
     * Test mode.
     */
    boolean testMode = true;

    /**
     * Constructor injection.
     * @param gpsUtil the GpsUtil
     * @param pRewardsService the RewardService
     */
    @Autowired
    public TourGuideServiceImpl(final GpsUtil gpsUtil,
                                final RewardsService pRewardsService) {
        this.gpsUtil = gpsUtil;
        this.rewardsService = pRewardsService;

        if(testMode) {
            LOGGER.info("TestMode enabled");
            LOGGER.debug("Initializing users");
            initializeInternalUsers();
            LOGGER.debug("Finished initializing users");
        }
        tracker = new Tracker(this);
        addShutDownHook();
    }

    /**
     * Get all the rewards of a given user.
     * @param user the user
     * @return a list that contains all the user rewards
     */
    public List<UserReward> getUserRewards(User user) {
        return user.getUserRewards();
    }

    /**
     * Get the user's location.
     * @param user the user
     * @return the user's location
     */
    @Override
    public VisitedLocation getUserLocation(User user) {
        return (user.getVisitedLocations().size() > 0) ?
                user.getLastVisitedLocation() :
                trackUserLocation(user);
    }

    /**
     * Get a user by username.
     * @param userName the user's username
     * @return the found user
     */
    @Override
    public User getUser(String userName) {
        return internalUserMap.get(userName);
    }

    /**
     * Get a list of all the users.
     * @return a list of all the users
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(internalUserMap.values());
    }

    /**
     * Add a new user.
     * @param user the user that will be added
     */
    @Override
    public void addUser(User user) {
        if(!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
        }
    }

    /**
     * Get the trip deals of a given user.
     * @param user the user
     * @return a list of all trip deals
     */
    @Override
    public List<Provider> getTripDeals(User user) {
        int cumulativeRewardPoints =
                user.getUserRewards()
                        .stream()
                        .mapToInt(i -> i.getRewardPoints())
                        .sum();
        List<Provider> providers =
                tripPricer.getPrice(TRIP_PRICER_API_KEY,
                        user.getUserId(),
                        user.getUserPreferences().getNumberOfAdults(),
                        user.getUserPreferences().getNumberOfChildren(),
                        user.getUserPreferences().getTripDuration(),
                        cumulativeRewardPoints);
        user.setTripDeals(providers);
        return providers;
    }

    /**
     * Track the location of a given user.
     * @param user the user that will be tracked
     * @return the location visited by the user
     */
    @Override
    public VisitedLocation trackUserLocation(User user) {
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
        user.addToVisitedLocations(visitedLocation);
        rewardsService.calculateRewards(user);
        return visitedLocation;
    }

    /**
     * Get the 5 nearby attractions of given visited location.
     * @param visitedLocation the visited location
     * @return a list that contains 5 nearby attractions
     */
    @Override
    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
        List<Attraction> nearbyAttractions = new ArrayList<>();
        for(Attraction attraction : gpsUtil.getAttractions()) {
            if(rewardsService.isWithinAttractionProximity(attraction, visitedLocation.location)) {
                nearbyAttractions.add(attraction);
            }
        }

        return nearbyAttractions;
    }

    /**
     * Gets the tracker.
     * @return the tracker
     */
    public Tracker getTracker() {
        return tracker;
    }

    /**
     * The shutDown hook.
     */
    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(tracker::stopTracking));
    }

    /*
     *
     * Methods Below: For Internal Testing
     *
     */

    /**
     * Trip pricer api key.
     */
    private static final String TRIP_PRICER_API_KEY =
            "test-server-api-key";

    /**
     * A map that will be used to store users.
     * It is used for testing purposes
     */
    private final Map<String, User> internalUserMap = new HashMap<>();

    /**
     * Initialize users and add them into the internalUserMap.
     */
    private void initializeInternalUsers() {
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName, phone, email);
            generateUserLocationHistory(user);

            internalUserMap.put(userName, user);
        });
        LOGGER.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    /**
     * Generate three locations for each user.
     * It is used for testing purposes
     * @param user the user to whom the location history will be generated
     */
    private void generateUserLocationHistory(User user) {
        IntStream.range(0, 3).forEach(i-> {
            user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
        });
    }

    /**
     * Generate a random longitude.
     * @return a random longitude
     */
    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Generate random latitude.
     * @return a random latitude
     */
    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Generate random time.
     * @return a random time
     */
    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

}
