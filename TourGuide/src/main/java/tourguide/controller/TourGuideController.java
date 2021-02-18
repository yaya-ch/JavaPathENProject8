package tourguide.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.VisitedLocation;
import tourguide.dto.CurrentLocationDTO;
import tourguide.dto.NearestAttractionsDTO;
import tourguide.service.TourGuideService;
import tourguide.domain.User;
import tripPricer.Provider;

/**
 * The TourGuideController.
 * This controller provides access to some of the app's endpoints.
 *
 * @author Some TourGuide Developer
 * @author Yahia CHERIFI
 */

@RestController
public class TourGuideController {

    /**
     * The TourGuideService that will be injected.
     */
    private final TourGuideService tourGuideService;

    /**
     * Class constructor.
     * @param pTourGuideService an instance of the TourGuideService
     */
    @Autowired
    public TourGuideController(final TourGuideService pTourGuideService) {
        this.tourGuideService = pTourGuideService;
    }

    /**
     * The TourGuide's home page.
     * @return a greeting message
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    /**
     * Get user's location.
     * @param userName the user's username
     * @return a user's location
     */
    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam final String userName) {
        VisitedLocation visitedLocation =
                tourGuideService.getUserLocation(getUser(userName));
        return JsonStream.serialize(visitedLocation.location);
    }

    /**
     * Get the 5 closest tourist attractions to the user.
     * @param userName the user's username
     * @return a list of the closest tourist attractions in json format
     */
    @RequestMapping("/getFiveNearbyAttractions")
    public List<NearestAttractionsDTO> getFiveCloseAttractionsToUser(
            @RequestParam final String userName) {
        VisitedLocation visitedLocation =
                tourGuideService.getUserLocation(getUser(userName));
        return (tourGuideService
                .getFiveCloseAttractionsToUser(
                        visitedLocation, getUser(userName)));
    }

    /**
     * Get all the user's rewards.
     * @param userName the user's username
     * @return all the user's rewards in json format
     */
    @RequestMapping("/getRewards")
    public String getRewards(@RequestParam final String userName) {
        return JsonStream.serialize(tourGuideService
                .getUserRewards(getUser(userName)));
    }

    /**
     * Get the current positions of all users at the same time.
     * @return a list of the users' positions in a json format
     */
    @RequestMapping("/getAllCurrentLocations")
    public List<CurrentLocationDTO> getAllCurrentLocations() {
        return tourGuideService.getAllUsersLocations();
    }

    /**
     * Get all the trip deals for a given user.
     * @param userName the user's username
     * @return a list of all the trip deals for a given user in a json format
     */
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam final String userName) {
        List<Provider> providers =
                tourGuideService.getTripDeals(getUser(userName));
        return JsonStream.serialize(providers);
    }

    /**
     * Get a given user by username.
     * @param userName the user's username
     * @return the found user
     */
    @GetMapping("/getUser")
    private User getUser(@RequestParam final String userName) {
        return tourGuideService.getUser(userName);
    }
}
