package tourguide;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import rewardCentral.RewardCentral;
import tourguide.domain.User;
import tourguide.helper.InternalTestHelper;
import tourguide.service.RewardsServiceImpl;
import tourguide.service.TourGuideServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class PerformanceTest {

    /*
     * A note on performance improvements:
     *
     *     The number of users generated for the high volume tests can be easily adjusted via this method:
     *
     *     		InternalTestHelper.setInternalUserNumber(100000);
     *
     *
     *     These tests can be modified to suit new solutions, just as long as the performance metrics
     *     at the end of the tests remains consistent.
     *
     *     These are performance metrics that we are trying to hit:
     *
     *     highVolumeTrackLocation: 100,000 users within 15 minutes:
     * assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
     *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     */

    //@Ignore
    @Test
    public void highVolumeTrackLocation() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral());
        // Users should be incremented up to 100,000, and test finishes within 15 minutes
        InternalTestHelper.setInternalUserNumber(100000);
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        List<User> allUsers = tourGuideService.getAllUsers();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for(User user : allUsers) {
            tourGuideService.trackUserLocationWithThread(user);
        }
        tourGuideService.shutDownExecutorService();
        stopWatch.stop();
        tourGuideService.getTracker().stopTracking();

        System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

    //@Ignore
    @Test
    public void highVolumeGetRewards() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsServiceImpl rewardsService = new RewardsServiceImpl(gpsUtil, new RewardCentral());

        // Users should be incremented up to 100,000, and test finishes within 20 minutes
        InternalTestHelper.setInternalUserNumber(100000);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TourGuideServiceImpl tourGuideService = new TourGuideServiceImpl(gpsUtil, rewardsService);

        Attraction attraction = gpsUtil.getAttractions().get(0);
        List<User> allUsers = tourGuideService.getAllUsers();
        allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));

        allUsers.forEach(rewardsService::calculateRewardsWithThread);
        rewardsService.shutDownExecutorService();
        for(User user : allUsers) {
            assertTrue(user.getUserRewards().size() > 0);
        }

        stopWatch.stop();
        tourGuideService.getTracker().stopTracking();
        System.out.println("highVolumeGetRewards: Time Elapsed: "
                + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

}
