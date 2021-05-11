package tourguide.helper;

/**
 * This class is used mainly to set the number of users that will be used.
 * in internal tests
 *
 * @author Unknown TourGuide Developer
 * @author Yahia CHERIFI
 */
public class InternalTestHelper {

    /**
     * The number of users that will be generated for testing.
     * Set this default up to 100,000 for testing
     */
    private static int internalUserNumber = 100000;

    /**
     * Sets internal user number.
     * @param internalUserNumber the internal user number
     */
    public static void setInternalUserNumber(int internalUserNumber) {
        InternalTestHelper.internalUserNumber = internalUserNumber;
    }

    /**
     * Gets internal user number.
     *
     * @return the internal user number
     */
    public static int getInternalUserNumber() {
        return internalUserNumber;
    }
}
