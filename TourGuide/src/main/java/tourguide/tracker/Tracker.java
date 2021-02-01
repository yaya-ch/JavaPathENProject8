package tourguide.tracker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tourguide.service.TourGuideService;
import tourguide.domain.User;

/**
 * The Tracker class.
 *
 * @author Some TourGuide Developer
 */

public class Tracker extends Thread {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Tracker.class);

    /**
     * The tracking interval.
     */
    private static final long TRACKING_POLLING_INTERVAL =
            TimeUnit.MINUTES.toSeconds(5);

    /**
     * The executor service.
     */
    private final ExecutorService executorService =
            Executors.newSingleThreadExecutor();

    /**
     * The TourGuideService.
     */
    private final TourGuideService tourGuideService;

    /**
     * The boolean stop.
     */
    private boolean stop = false;

    /**
     * Class constructor.
     * @param pTourGuideService the TourGuideService
     */
    public Tracker(final TourGuideService pTourGuideService) {
        this.tourGuideService = pTourGuideService;
        executorService.submit(this);
    }

    /**
    * Assures to shut down the Tracker thread.
    */
    public void stopTracking() {
        stop = true;
        executorService.shutdownNow();
    }

    /**
     * Overriding the run method of the Thread class.
     */
    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();
        while (true) {
            if (Thread.currentThread().isInterrupted() || stop) {
                LOGGER.debug("Tracker stopping");
                break;
            }

            List<User> users = tourGuideService.getAllUsers();
            LOGGER.debug("Begin Tracker. Tracking " + users.size() + " users.");
            stopWatch.start();
            users.forEach(tourGuideService::trackUserLocation);
            stopWatch.stop();
            LOGGER.debug("Tracker Time Elapsed: "
                    + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
                    + " seconds.");
            stopWatch.reset();
            try {
                LOGGER.debug("Tracker sleeping");
                TimeUnit.SECONDS.sleep(TRACKING_POLLING_INTERVAL);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
