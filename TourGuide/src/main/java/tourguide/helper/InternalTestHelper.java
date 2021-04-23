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
     */
    private static int internalUserNumber;

    /**
     * Instantiates a new Internal test helper.
     */
    protected InternalTestHelper() {
    }

    /**
     * Sets internal user number.
     *
     * @param pInternalUserNumber the p internal user number
     */
    public static void setInternalUserNumber(final int pInternalUserNumber) {
        InternalTestHelper.internalUserNumber = pInternalUserNumber;
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
