package tourguide.controller;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tourguide.domain.User;
import tourguide.domain.UserReward;
import tourguide.dto.CurrentLocationDTO;
import tourguide.dto.NearestAttractionsDTO;
import tourguide.service.TourGuideService;
import tripPricer.Provider;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Yahia CHERIFI
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TourGuideControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @MockBean
    private TourGuideService tourGuideService;

    private User user;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        user = new User(UUID.randomUUID(),
                "user",
                "098762423",
                "test@user.com");
    }

    @Test
    public void index_shouldReturnCorrectGreetingMessage_andStatusShouldBe200Ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Greetings from TourGuide!"));
    }

    @Test
    public void getLocation_shouldReturnUserLocation_andStatusShouldBe200Ok() throws Exception {
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(),
                new Location(40.949073, -87.061186),
                new Date());
        user.addToVisitedLocations(visitedLocation);

        when(tourGuideService.getUser(anyString())).thenReturn(user);
        when(tourGuideService.getUserLocation(any(User.class))).thenReturn(visitedLocation);

        mockMvc.perform(MockMvcRequestBuilders.get("/getLocation?userName=internalUser1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude")
                        .value(user.getLastVisitedLocation().location.latitude))
                .andExpect(jsonPath("$.longitude")
                        .value(user.getLastVisitedLocation().location.longitude));
    }

    @Test
    public void getFiveCloseAttractionsToUser_shouldReturnAListOf5ClosestAttractions_andStatusShouldBe200Ok() throws Exception {
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(),
                new Location(1,1),
                new Date());
        List<NearestAttractionsDTO> nearestAttractionsList =
                new ArrayList<>(Arrays.asList(
                        new NearestAttractionsDTO("Attraction1", 40.949073, -87.061186, 2, 12),
                        new NearestAttractionsDTO("Attraction2", 40.949073, -87.061186, 23, 2),
                        new NearestAttractionsDTO("Attraction3", 40.949073, -87.061186, 21, 1),
                        new NearestAttractionsDTO("Attraction4", 40.949073, -87.061186, 231, 122),
                        new NearestAttractionsDTO("Attraction5", 40.949073, -87.061186, 234, 112)));

        when(tourGuideService.getUser(anyString())).thenReturn(user);
        when(tourGuideService.getUserLocation(any(User.class))).thenReturn(visitedLocation);
        when(tourGuideService.getFiveCloseAttractionsToUser(any(VisitedLocation.class), any(User.class))).thenReturn(nearestAttractionsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getFiveNearbyAttractions?userName=test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].attractionName").value("Attraction1"))
                .andExpect(jsonPath("$[2].attractionName").value("Attraction3"))
                .andExpect(jsonPath("$[4].attractionName").value("Attraction5"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getRewards_shouldReturnAListOfUserRewards_andStatusShouldBe200Ok() throws Exception {
        List<UserReward> userRewards = new ArrayList<>(Arrays.asList(
                new UserReward(
                        new VisitedLocation(UUID.randomUUID(),
                                new Location(40.949073,-87.061186),
                                new Date()),
                        new Attraction("Attraction1",
                                "City1", "State1",
                                41.218204,-87.677794)),
                new UserReward(
                        new VisitedLocation(UUID.randomUUID(),
                                new Location(1,-87.677794),
                                new Date()),
                        new Attraction("Attraction1",
                                "City2", "State1",
                                41.218204,-87.677794))));

        when(tourGuideService.getUser(anyString())).thenReturn(user);
        when(tourGuideService.getUserRewards(any(User.class))).thenReturn(userRewards);

        mockMvc.perform(MockMvcRequestBuilders.get("/getRewards?userName=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].visitedLocation.location.latitude")
                        .value(userRewards.get(0).getVisitedLocation().location.latitude));
    }

    @Test
    public void getAllCurrentLocations_shouldReturnAListOfUserCurrentLocations_andStatusShouldBe200Ok() throws Exception {
        List<CurrentLocationDTO> currentLocationDTOList = new ArrayList<>(Arrays.asList(
                new CurrentLocationDTO(UUID.randomUUID(), new Location(39.937778, -82.40667)),
                new CurrentLocationDTO(UUID.randomUUID(), new Location(39.937778, -82.40667)),
                new CurrentLocationDTO(UUID.randomUUID(), new Location(39.937778, -82.40667)),
                new CurrentLocationDTO(UUID.randomUUID(), new Location(39.937778, -82.40667))));

        when(tourGuideService.getAllUsersLocations()).thenReturn(currentLocationDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getAllCurrentLocations")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].location.latitude")
                        .value(currentLocationDTOList.get(0).getLocation().latitude));
    }

    @Test
    public void getTripDeals_shouldReturnAListOfTripDeals_andStatusShouldBe200Ok() throws Exception {
        List<Provider> providers = new ArrayList<>(Arrays.asList(
                new Provider(UUID.randomUUID(), "Provider1", 2),
                new Provider(UUID.randomUUID(), "Provider2", 2)));

        when(tourGuideService.getUser(anyString())).thenReturn(user);
        when(tourGuideService.getTripDeals(any(User.class))).thenReturn(providers);

        mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals?userName=test"
                + "&tripDuration=1"
                + "&numberOfAdults=1"
                + "&numberOfChildren=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Provider1"))
                .andExpect(jsonPath("$[1].name").value("Provider2"));
    }
}