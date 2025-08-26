package com.parking;

import java.io.Serializable;

public class ParkingSpace implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String spaceNumber;
	private String address1;
	private String address2;
	private String county;
	private double pricePerHour;
	private boolean available;
	private boolean accessibleParking;
	private boolean closeToBus;
	private String city; // ✅ Added city property

	// Default constructor
	public ParkingSpace() {
	}

	// Full constructor
	public ParkingSpace(String id, String spaceNumber, String address1, String address2, String county,
			double pricePerHour, boolean available, boolean accessibleParking, boolean closeToBus) {
		this.id = id;
		this.spaceNumber = spaceNumber;
		this.address1 = address1;
		this.address2 = address2;
		this.county = county;
		this.pricePerHour = pricePerHour;
		this.available = available;
		this.accessibleParking = accessibleParking;
		this.closeToBus = closeToBus;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpaceNumber() {
		return spaceNumber;
	}

	public void setSpaceNumber(String spaceNumber) {
		this.spaceNumber = spaceNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAccessibleParking() {
		return accessibleParking;
	}

	public void setAccessibleParking(boolean accessibleParking) {
		this.accessibleParking = accessibleParking;
	}

	public boolean isCloseToBus() {
		return closeToBus;
	}

	public void setCloseToBus(boolean closeToBus) {
		this.closeToBus = closeToBus;
	}

	// ✅ Added getter and setter for city
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "ParkingSpace [id=" + id + ", spaceNumber=" + spaceNumber + ", address1=" + address1 + ", address2="
				+ address2 + ", county=" + county + ", pricePerHour=" + pricePerHour + ", available=" + available
				+ ", accessibleParking=" + accessibleParking + ", closeToBus=" + closeToBus + ", city=" + city + "]";
	}
}
