package tourguide.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A class that holds some attraction related information.
 * Used to return information about the distance
 * that separates a given user from a given attraction
 *
 * @author Yahia CHERIFI
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NearestAttractionsDTO {

    /**
     * The attraction name.
     */
    private String attractionName;

    /**
     * The attraction latitude.
     */
    private double latitude;

    /**
     * The attraction longitude.
     */
    private double longitude;

    /**
     * The distance that separates a given user from the tourist attraction.
     */
    private double distanceFromUser;

    /**
     * The number of reward points for visiting the attraction.
     */
    private double rewardPoint;
}
