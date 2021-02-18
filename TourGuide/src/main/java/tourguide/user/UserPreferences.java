package tourguide.user;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;


/**
 * The type User preferences.
 * This class groups all the User's related information
 *
 * @author Some TourGuide Developer
 * @author Yahia CHERIFI
 */

public class UserPreferences {

    /**
     * The attraction max proximity.
     */
    private int attractionProximity = Integer.MAX_VALUE;

    /**
     * The currency.
     */
    private CurrencyUnit currency = Monetary.getCurrency("USD");

    /**
     * The lower preferred price.
     */
    private Money lowerPricePoint = Money.of(0, currency);

    /**
     * The highest preferred price.
     */
    private Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);

    /**
     * The trip duration.
     */
    private int tripDuration;

    /**
     * The ticket quantity.
     */
    private int ticketQuantity;

    /**
     * The number of adults.
     */
    private int numberOfAdults;

    /**
     * The number of children.
     */
    private int numberOfChildren;

    /**
     * Instantiates a new User preferences.
     */
    public UserPreferences() {
    }

    /**
     * Sets attraction proximity.
     *
     * @param pAttractionProximity the attraction proximity
     */
    public void setAttractionProximity(final int pAttractionProximity) {
        this.attractionProximity = pAttractionProximity;
    }

    /**
     * Gets attraction proximity.
     *
     * @return the attraction proximity
     */
    public int getAttractionProximity() {
        return attractionProximity;
    }

    /**
     * Gets lower price point.
     *
     * @return the lower price point
     */
    public Money getLowerPricePoint() {
        return lowerPricePoint;
    }

    /**
     * Sets lower price point.
     *
     * @param pLowerPricePoint the lower price point
     */
    public void setLowerPricePoint(final Money pLowerPricePoint) {
        this.lowerPricePoint = pLowerPricePoint;
    }

    /**
     * Gets high price point.
     *
     * @return the high price point
     */
    public Money getHighPricePoint() {
        return highPricePoint;
    }

    /**
     * Sets high price point.
     *
     * @param pHighPricePoint the high price point
     */
    public void setHighPricePoint(final Money pHighPricePoint) {
        this.highPricePoint = pHighPricePoint;
    }

    /**
     * Gets trip duration.
     *
     * @return the trip duration
     */
    public int getTripDuration() {
        return tripDuration;
    }

    /**
     * Sets trip duration.
     *
     * @param pTripDuration the trip duration
     */
    public void setTripDuration(final int pTripDuration) {
        this.tripDuration = pTripDuration;
    }

    /**
     * Gets ticket quantity.
     *
     * @return the ticket quantity
     */
    public int getTicketQuantity() {
        return ticketQuantity;
    }

    /**
     * Sets ticket quantity.
     *
     * @param pTicketQuantity the ticket quantity
     */
    public void setTicketQuantity(final int pTicketQuantity) {
        this.ticketQuantity = pTicketQuantity;
    }

    /**
     * Gets number of adults.
     *
     * @return the number of adults
     */
    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    /**
     * Sets number of adults.
     *
     * @param pNumberOfAdults the number of adults
     */
    public void setNumberOfAdults(final int pNumberOfAdults) {
        this.numberOfAdults = pNumberOfAdults;
    }

    /**
     * Gets number of children.
     *
     * @return the number of children
     */
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    /**
     * Sets number of children.
     *
     * @param pNumberOfChildren the number of children
     */
    public void setNumberOfChildren(final int pNumberOfChildren) {
        this.numberOfChildren = pNumberOfChildren;
    }

}
