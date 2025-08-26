package com.parking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class BookingBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // List of parking spaces
    private List<ParkingSpace> parkingSpaces = new ArrayList<>();
    
    // Selected parking space
    private ParkingSpace selectedSpace = new ParkingSpace();

    // Properties for adding a new parking space
    private String spaceNumber;
    private String address1;
    private String address2;
    private String county;
    private double pricePerHour;
    private boolean available;
    private boolean accessibleParking;
    private boolean closeToBus;

    // Getters and setters
    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public ParkingSpace getSelectedSpace() {
        return selectedSpace;
    }

    public void setSelectedSpace(ParkingSpace selectedSpace) {
        this.selectedSpace = selectedSpace;
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

    // Add a new parking space
    public void addParkingSpace() {
        ParkingSpace newSpace = new ParkingSpace(
            UUID.randomUUID().toString(),
            spaceNumber,
            address1,
            address2,
            county,
            pricePerHour,
            available,
            accessibleParking,
            closeToBus
        );

        parkingSpaces.add(newSpace);

        // Reset fields after adding
        spaceNumber = "";
        address1 = "";
        address2 = "";
        county = "";
        pricePerHour = 0.0;
        available = false;
        accessibleParking = false;
        closeToBus = false;

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Parking space added successfully!"));
    }

    // Select a parking space
    public void selectParkingSpace(ParkingSpace space) {
        this.selectedSpace = space;
    }
}
