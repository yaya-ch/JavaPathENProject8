package tourguide.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

/**
 * TourGuide Config class.
 * This class contains the different beans that can be used by the application
 *
 * @author Unknown TourGuide Developer
 * @author Yahia CHERIFI
 */

@Configuration
public class TourGuideModule {

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
}
