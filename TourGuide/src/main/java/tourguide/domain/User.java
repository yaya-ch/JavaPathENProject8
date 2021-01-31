package tourguide.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import gpsUtil.location.VisitedLocation;
import tourguide.user.UserPreferences;
import tourguide.user.UserReward;
import tripPricer.Provider;

/**
 * The user class.
 * This class groups all the User's related information
 */
public class User {

    /**
     * The user's id.
     */
    private final UUID userId;

    /**
     * The user's username.
      */
    private final String userName;

    /**
     * The user's phone number.
     */
    private String phoneNumber;

    /**
     * The user's email address.
     */
    private String emailAddress;

    /**
     * The time when the user visited the last location.
     */
    private Date latestLocationTimestamp;

    /**
     * A list of the locations visited by the user.
     */
    private final List<VisitedLocation> visitedLocations = new ArrayList<>();

    /**
     * A list of the user's rewards.
     */
    private final List<UserReward> userRewards = new ArrayList<>();

    /**
     * The user's preferences.
     * Defined by certain params
     */
    private UserPreferences userPreferences = new UserPreferences();

    /**
     * A list of all the trip providers.
     */
    private List<Provider> tripDeals = new ArrayList<>();

    /**
     * Class constructor.
     *
     * @param pUserId       the user's id
     * @param pUserName     the user's username
     * @param pPhoneNumber  the user's phone number
     * @param pEmailAddress the user's email address
     */
    public User(final UUID pUserId, final String pUserName,
                final String pPhoneNumber, final String pEmailAddress) {
        this.userId = pUserId;
        this.userName = pUserName;
        this.phoneNumber = pPhoneNumber;
        this.emailAddress = pEmailAddress;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets phone number.
     *
     * @param pPhoneNumber the phone number
     */
    public void setPhoneNumber(final String pPhoneNumber) {
        this.phoneNumber = pPhoneNumber;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets email address.
     *
     * @param pEmailAddress the email address
     */
    public void setEmailAddress(final String pEmailAddress) {
        this.emailAddress = pEmailAddress;
    }

    /**
     * Gets email address.
     *
     * @return the email address
     */
    public String getEmailAddress() {
    return emailAddress;
    }

    /**
     * Sets latest location timestamp.
     *
     * @param pLatestLocationTimestamp the latest location timestamp
     */
    public void setLatestLocationTimestamp(
            final Date pLatestLocationTimestamp) {
        this.latestLocationTimestamp = pLatestLocationTimestamp;
    }

    /**
     * Gets latest location timestamp.
     *
     * @return the latest location timestamp
     */
    public Date getLatestLocationTimestamp() {
        return latestLocationTimestamp;
    }

    /**
     * Add to visited locations.
     *
     * @param visitedLocation the visited location
     */
    public void addToVisitedLocations(final VisitedLocation visitedLocation) {
        visitedLocations.add(visitedLocation);
    }

    /**
     * Gets visited locations.
     *
     * @return the visited locations
     */
    public List<VisitedLocation> getVisitedLocations() {
        return (List<VisitedLocation>)
                ((ArrayList<VisitedLocation>) visitedLocations).clone();
    }

    /**
     * Clear visited locations.
     */
    public void clearVisitedLocations() {
        visitedLocations.clear();
    }

    /**
     * Add user reward.
     *
     * @param userReward the user reward
     */
    public void addUserReward(final UserReward userReward) {
        if (this.userRewards.stream()
                .noneMatch(r -> r.attraction.attractionName.equals(
                        userReward.attraction.attractionName))) {
            this.userRewards.add(userReward);
        }
    }

    /**
     * Gets user rewards.
     *
     * @return the user rewards
     */
    public List<UserReward> getUserRewards() {
        return (List<UserReward>) ((ArrayList<UserReward>) userRewards).clone();
    }

    /**
     * Gets user preferences.
     *
     * @return the user preferences
     */
    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    /**
     * Sets user preferences.
     *
     * @param pUserPreferences the user preferences
     */
    public void setUserPreferences(final UserPreferences pUserPreferences) {
        this.userPreferences = pUserPreferences;
    }

    /**
     * Gets last visited location.
     *
     * @return the last visited location
     */
    public VisitedLocation getLastVisitedLocation() {
        return visitedLocations.get(visitedLocations.size() - 1);
    }

    /**
     * Sets trip deals.
     *
     * @param pTripDeals the trip deals
     */
    public void setTripDeals(final List<Provider> pTripDeals) {
        this.tripDeals = pTripDeals;
    }

    /**
     * Gets trip deals.
     *
     * @return the trip deals
     */
    public List<Provider> getTripDeals() {
        return tripDeals;
    }
}
