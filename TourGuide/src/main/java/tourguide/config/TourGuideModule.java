package tourguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TourGuide Config class.
 * This class contains the different beans that can be used by the application
 *
 * @author Some TourGuide Developer
 * @author Yahia CHERIFI
 */

@Configuration
public class TourGuideModule {

    /**
     * The maximum number of threads that will be active to process tasks.
     */
    private static final int NUMBER_OF_THREADS = 100;

    /**
     * The gpsUtil bean.
     * @return a new instance of the gpsUtil
     */
    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }

    /**
     * The rewardCentral bean.
     * @return a new instance of the RewardCentral
     */
    @Bean
    public RewardCentral getRewardCentral() {
        return new RewardCentral();
    }

    /**
     * The executorService bean.
     * @return a new instance of the ExecutorService.
     */
    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }
}
