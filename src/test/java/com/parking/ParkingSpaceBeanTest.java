package com.parking;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class ParkingSpaceBeanTest {

	private ParkingSpaceBean parkingSpaceBean;

	@Before
	public void setUp() {
		parkingSpaceBean = new ParkingSpaceBean();
	}

	@Test
	public void testConstructor() {
		// Test that constructor initialises properly
		assertNotNull("Parking spaces list should be initialized", parkingSpaceBean.getParkingSpaces());
		assertTrue("Parking spaces list should be empty initially", parkingSpaceBean.getParkingSpaces().isEmpty());
		assertEquals("Default price should be 0.0", 0.0, parkingSpaceBean.getPrice(), 0.001);
	}

	@Test
	public void testGettersAndSetters() {
		// Test space number
		parkingSpaceBean.setSpaceNumber("A101");
		assertEquals("Space number should be set correctly", "A101", parkingSpaceBean.getSpaceNumber());

		// Test location
		parkingSpaceBean.setLocation("Level 1, North Wing");
		assertEquals("Location should be set correctly", "Level 1, North Wing", parkingSpaceBean.getLocation());

		// Test price
		parkingSpaceBean.setPrice(15.50);
		assertEquals("Price should be set correctly", 15.50, parkingSpaceBean.getPrice(), 0.001);

		// Test parking spaces list
		ArrayList<ParkingSpace> spaces = new ArrayList<>();
		parkingSpaceBean.setParkingSpaces(spaces);
		assertEquals("Parking spaces list should be set correctly", spaces, parkingSpaceBean.getParkingSpaces());
	}

	@Test
	public void testAddParkingSpaceSuccess() {
		// Set up valid data
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(20.0);

		String result = parkingSpaceBean.addParkingSpace();

		// Should return "parking-list" on success
		assertEquals("Should return parking-list on success", "parking-list", result);

		// Verify the parking space was added to the list
		assertEquals("One parking space should be added", 1, parkingSpaceBean.getParkingSpaces().size());

		ParkingSpace addedSpace = parkingSpaceBean.getParkingSpaces().get(0);
		assertEquals("Space number should match", "A101", addedSpace.getSpaceNumber());
		assertEquals("Location should match", "Level 1", addedSpace.getLocation());
		assertEquals("Price should match", 20.0, addedSpace.getPrice(), 0.001);
		assertTrue("Space should be available by default", addedSpace.isAvailable());

		// Verify form fields are cleared
		assertNull("Space number should be cleared", parkingSpaceBean.getSpaceNumber());
		assertNull("Location should be cleared", parkingSpaceBean.getLocation());
		assertEquals("Price should be reset to 0.0", 0.0, parkingSpaceBean.getPrice(), 0.001);
	}

	@Test
	public void testAddParkingSpaceWithEmptySpaceNumber() {
		// Test with empty space number
		parkingSpaceBean.setSpaceNumber("");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(20.0);

		String result = parkingSpaceBean.addParkingSpace();

		// Should return null (validation failed)
		assertNull("Should return null for invalid input", result);
		// No parking space should be added
		assertEquals("No parking space should be added", 0, parkingSpaceBean.getParkingSpaces().size());
	}

	@Test
	public void testAddParkingSpaceWithNullSpaceNumber() {
		// Test with null space number
		parkingSpaceBean.setSpaceNumber(null);
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(20.0);

		String result = parkingSpaceBean.addParkingSpace();

		assertNull("Should return null for null space number", result);
		assertEquals("No parking space should be added", 0, parkingSpaceBean.getParkingSpaces().size());
	}

	@Test
	public void testAddParkingSpaceWithEmptyLocation() {
		// Test with empty location
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("");
		parkingSpaceBean.setPrice(20.0);

		String result = parkingSpaceBean.addParkingSpace();

		assertNull("Should return null for empty location", result);
		assertEquals("No parking space should be added", 0, parkingSpaceBean.getParkingSpaces().size());
	}

	@Test
	public void testAddParkingSpaceWithNullLocation() {
		// Test with null location
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation(null);
		parkingSpaceBean.setPrice(20.0);

		String result = parkingSpaceBean.addParkingSpace();

		assertNull("Should return null for null location", result);
		assertEquals("No parking space should be added", 0, parkingSpaceBean.getParkingSpaces().size());
	}

	@Test
	public void testAddParkingSpaceWithNegativePrice() {
		// Test with negative price
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(-5.0);

		String result = parkingSpaceBean.addParkingSpace();

		assertNull("Should return null for negative price", result);
		assertEquals("No parking space should be added", 0, parkingSpaceBean.getParkingSpaces().size());
	}

	@Test
	public void testAddParkingSpaceWithZeroPrice() {
		// Test with zero price (should be valid)
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(0.0);

		String result = parkingSpaceBean.addParkingSpace();

		// Should succeed (zero price is valid)
		assertEquals("One parking space should be added", 1, parkingSpaceBean.getParkingSpaces().size());
		assertEquals("Price should be 0.0", 0.0, parkingSpaceBean.getParkingSpaces().get(0).getPrice(), 0.001);
	}

	@Test
	public void testAddDuplicateSpaceNumber() {
		// Add first parking space
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(20.0);
		parkingSpaceBean.addParkingSpace();

		// Try to add another space with same number
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("Level 2");
		parkingSpaceBean.setPrice(25.0);

		String result = parkingSpaceBean.addParkingSpace();

		assertNull("Should return null for duplicate space number", result);
		assertEquals("Should still have only one parking space", 1, parkingSpaceBean.getParkingSpaces().size());
	}

	@Test
	public void testAddDuplicateSpaceNumberWithWhitespace() {
		// Add first parking space
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(20.0);
		parkingSpaceBean.addParkingSpace();

		// Try to add another space with same number but with whitespace
		parkingSpaceBean.setSpaceNumber(" A101 ");
		parkingSpaceBean.setLocation("Level 2");
		parkingSpaceBean.setPrice(25.0);

		String result = parkingSpaceBean.addParkingSpace();

		assertNull("Should return null for duplicate space number (whitespace trimmed)", result);
		assertEquals("Should still have only one parking space", 1, parkingSpaceBean.getParkingSpaces().size());
	}

	@Test
	public void testAddMultipleParkingSpaces() {
		// Add first parking space
		parkingSpaceBean.setSpaceNumber("A101");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(20.0);
		parkingSpaceBean.addParkingSpace();

		// Add second parking space
		parkingSpaceBean.setSpaceNumber("A102");
		parkingSpaceBean.setLocation("Level 1");
		parkingSpaceBean.setPrice(22.0);
		parkingSpaceBean.addParkingSpace();

		// Add third parking space
		parkingSpaceBean.setSpaceNumber("B101");
		parkingSpaceBean.setLocation("Level 2");
		parkingSpaceBean.setPrice(25.0);
		parkingSpaceBean.addParkingSpace();

		assertEquals("Should have three parking spaces", 3, parkingSpaceBean.getParkingSpaces().size());

		// Verify all spaces are present
		boolean foundA101 = false, foundA102 = false, foundB101 = false;
		for (ParkingSpace space : parkingSpaceBean.getParkingSpaces()) {
			if ("A101".equals(space.getSpaceNumber()))
				foundA101 = true;
			if ("A102".equals(space.getSpaceNumber()))
				foundA102 = true;
			if ("B101".equals(space.getSpaceNumber()))
				foundB101 = true;
		}

		assertTrue("A101 should be found", foundA101);
		assertTrue("A102 should be found", foundA102);
		assertTrue("B101 should be found", foundB101);
	}

	@Test
	public void testWhitespaceHandling() {
		// Test that whitespace is properly trimmed
		parkingSpaceBean.setSpaceNumber("  A101  ");
		parkingSpaceBean.setLocation("  Level 1  ");
		parkingSpaceBean.setPrice(20.0);

		parkingSpaceBean.addParkingSpace();

		assertEquals("One parking space should be added", 1, parkingSpaceBean.getParkingSpaces().size());

		ParkingSpace addedSpace = parkingSpaceBean.getParkingSpaces().get(0);
		assertEquals("Space number should be trimmed", "A101", addedSpace.getSpaceNumber());
		assertEquals("Location should be trimmed", "Level 1", addedSpace.getLocation());
	}
}