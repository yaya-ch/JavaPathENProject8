package tourguide;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.junit.Test;
import rewardCentral.RewardCentral;
import tourguide.domain.User;
import tourguide.helper.InternalTestHelper;
import tourguide.service.RewardsServiceImpl;
import tourguide.service.TourGuideServiceImpl;
import tourguide.user.UserReward;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RewardsServiceImplTest {

    @Test
    public void userGetRewards() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral());

        InternalTestHelper.setInternalUserNumber(0);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        Attraction attraction = gpsUtil.getAttractions().get(0);
        user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
        tourGuideService.trackUserLocation(user);
        List<UserReward> userRewards = user.getUserRewards();
        tourGuideService.getTracker().stopTracking();
        assertEquals(1, userRewards.size());
    }

    @Test
    public void isWithinAttractionProximity() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral());
        Attraction attraction = gpsUtil.getAttractions().get(0);
        assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
    }

    @Test
    public void nearAllAttractions() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral());
        rewardsService.setProximityBuffer(Integer.MAX_VALUE);

        InternalTestHelper.setInternalUserNumber(1);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
        List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
        tourGuideService.getTracker().stopTracking();

        assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
    }
}
