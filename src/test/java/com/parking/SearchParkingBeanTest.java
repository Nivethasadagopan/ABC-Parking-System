package com.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SearchParkingBeanTest {

    private SearchParkingBean searchParking;
    private ParkingSpaceBean parkingSpaceBean;

    @BeforeEach
    public void setUp() {
        searchParking = new SearchParkingBean();
        parkingSpaceBean = new ParkingSpaceBean();

        ArrayList<ParkingSpace> spaces = new ArrayList<>();
        spaces.add(new ParkingSpace("A1", "City Center", 10.0, true));
        spaces.add(new ParkingSpace("B2", "Dublin", 20.0, false));
        spaces.add(new ParkingSpace("C3", "City Center", 30.0, true));
        parkingSpaceBean.setParkingSpaces(spaces);

        searchParking.setParkingSpaceBean(parkingSpaceBean);
    }

    @Test
    public void testSearchByLocationOnly() {
        searchParking.setLocationInfo("City");
        searchParking.searchParkingSpaces();

        assertEquals(2, searchParking.getSearchResults().size());
    }

    @Test
    public void testSearchByParkingNameOnly() {
        searchParking.setParkingName("B2");
        searchParking.searchParkingSpaces();

        assertEquals(1, searchParking.getSearchResults().size());
        assertEquals("B2", searchParking.getSearchResults().get(0).getSpaceNumber());
    }

    @Test
    public void testSearchByPriceOnly() {
        searchParking.setPriceRange("15");
        searchParking.searchParkingSpaces();

        assertEquals(1, searchParking.getSearchResults().size());
        assertTrue(searchParking.getSearchResults().get(0).getPrice() <= 15);
    }

    @Test
    public void testInvalidPriceFormat() {
        searchParking.setPriceRange("abc"); // invalid input
        searchParking.searchParkingSpaces();

        assertEquals(0, searchParking.getSearchResults().size()); // should be empty
    }


    @Test
    public void testGetAvailableSearchResults() {
        searchParking.setSearchResults(parkingSpaceBean.getParkingSpaces());
        List<ParkingSpace> available = searchParking.getAvailableSearchResults();

        assertEquals(2, available.size());
        assertTrue(available.stream().allMatch(ParkingSpace::isAvailable));
    }

    

    

    @Test
    public void testHasSearchCriteria() {
        assertFalse(searchParking.hasSearchCriteria());

        searchParking.setLocationInfo("City");
        assertTrue(searchParking.hasSearchCriteria());

        searchParking.clearSearch();
        searchParking.setParkingName("A1");
        assertTrue(searchParking.hasSearchCriteria());

        searchParking.clearSearch();
        searchParking.setPriceRange("15");
        assertTrue(searchParking.hasSearchCriteria());

        searchParking.clearSearch();
        searchParking.setEntryDate(new Date());
        assertTrue(searchParking.hasSearchCriteria());
    }
}
