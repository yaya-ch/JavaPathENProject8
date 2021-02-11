package tourguide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourguide.dto.NearestAttractionsDTO;
import tourguide.helper.InternalTestHelper;
import tourguide.service.RewardsServiceImpl;
import tourguide.service.TourGuideServiceImpl;
import tourguide.domain.User;
import tripPricer.Provider;

public class TourGuideServiceImplTest {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1000);

    @Test
    public void getUserLocation() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral(), executorService);
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
        tourGuideService.getTracker().stopTracking();
        assertEquals(visitedLocation.userId, user.getUserId());
    }

    @Test
    public void addUser() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral(), executorService);
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        User retrievedUser = tourGuideService.getUser(user.getUserName());
        User retrievedUser2 = tourGuideService.getUser(user2.getUserName());

        tourGuideService.getTracker().stopTracking();

        assertEquals(user, retrievedUser);
        assertEquals(user2, retrievedUser2);
    }

    @Test
    public void getAllUsers() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral(), executorService);
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        List<User> allUsers = tourGuideService.getAllUsers();

        tourGuideService.getTracker().stopTracking();

        assertTrue(allUsers.contains(user));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    public void trackUser() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral(), executorService);
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

        tourGuideService.getTracker().stopTracking();

        assertEquals(user.getUserId(), visitedLocation.userId);
    }

    @Test
    public void getFiveCloseAttractionsToUser() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral(), executorService);
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

        List<NearestAttractionsDTO> attractions = tourGuideService.getFiveCloseAttractionsToUser(visitedLocation, user);

        tourGuideService.getTracker().stopTracking();

        assertEquals(5, attractions.size());
    }

    @Test
    public void getTripDeals() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral(), executorService);
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        List<Provider> providers = tourGuideService.getTripDeals(user);

        tourGuideService.getTracker().stopTracking();

        assertEquals(5, providers.size());
    }
}
