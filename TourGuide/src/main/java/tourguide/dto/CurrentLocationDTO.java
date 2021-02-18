package tourguide.dto;

import gpsUtil.location.Location;

import java.util.UUID;

/**
 * A class that holds some current location related information.
 * <p>
 * Used mainly to get the users' current location
 *
 * @author Yahia CHERIFI
 */
public class CurrentLocationDTO {

    /**
     * the user's id.
     */
    private UUID userId;

    /**
     * The user's location.
     */
    private Location location;

    /**
     * Class constructor.
     */
    public CurrentLocationDTO() {
    }

    /**
     * Class constructor.
     *
     * @param pUserId   the use's id
     * @param pLocation the user's location
     */
    public CurrentLocationDTO(final UUID pUserId, final Location pLocation) {
        this.userId = pUserId;
        this.location = pLocation;
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
     * Sets user id.
     *
     * @param pUserId the user id
     */
    public void setUserId(final UUID pUserId) {
        this.userId = pUserId;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param pLocation the location
     */
    public void setLocation(final Location pLocation) {
        this.location = pLocation;
    }

    /**
     * ToString method.
     * @return CurrentLocation in a string format
     */
    @Override
    public String toString() {
        return "CurrentLocationDTO{"
                + "userId=" + userId
                + ", location=" + location
                + '}';
    }
}
