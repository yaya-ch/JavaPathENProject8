package tourguide.dto;

/**
 * A class that holds some attraction related information.
 * <p>
 * Used to return information about the distance
 * that separates a given user from a given attraction
 *
 * @author Yahia CHERIFI
 */
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

    /**
     * Class constructor.
     */
    public NearestAttractionsDTO() {
    }

    /**
     * Class constructor.
     *
     * @param attractionName   the attraction name
     * @param latitude         the attraction latitude
     * @param longitude        the attraction longitude
     * @param distanceFromUser the distance that separates                         the attraction from a given user
     * @param rewardPoint      the reward points that a user will receive                    for visiting the attraction
     */
    public NearestAttractionsDTO(String attractionName,
                                 double latitude,
                                 double longitude,
                                 double distanceFromUser,
                                 double rewardPoint) {
        this.attractionName = attractionName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceFromUser = distanceFromUser;
        this.rewardPoint = rewardPoint;
    }

    /**
     * Gets attraction name.
     *
     * @return the attraction name
     */
    public String getAttractionName() {
        return attractionName;
    }

    /**
     * Sets attraction name.
     *
     * @param attractionName the attraction name
     */
    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets distance from user.
     *
     * @return the distance from user
     */
    public double getDistanceFromUser() {
        return distanceFromUser;
    }

    /**
     * Sets distance from user.
     *
     * @param distanceFromUser the distance from user
     */
    public void setDistanceFromUser(double distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    /**
     * Gets reward point.
     *
     * @return the reward point
     */
    public double getRewardPoint() {
        return rewardPoint;
    }

    /**
     * Sets reward point.
     *
     * @param rewardPoint the reward point
     */
    public void setRewardPoint(double rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    @Override
    public String toString() {
        return "NearestAttractionsDTO{" +
                "attractionName='" + attractionName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distanceFromUser=" + distanceFromUser +
                ", rewardPoint=" + rewardPoint +
                '}';
    }
}
