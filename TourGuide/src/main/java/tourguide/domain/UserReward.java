package tourguide.domain;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

/**
 * The type User reward.
 *
 * @author Unknown TourGuide Developer
 */
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class UserReward {

    /**
     * The Visited location.
     */
    @NonNull
    private VisitedLocation visitedLocation;
    /**
     * The Attraction.
     */
    @NonNull
    private Attraction attraction;

    /**
     * The reward points.
     */
    private int rewardPoints;
}
