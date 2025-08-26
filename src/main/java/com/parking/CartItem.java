package com.parking;

import java.io.Serializable;
import java.util.Date;

public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private ParkingSpace parkingSpace;
    private Date bookingStart;
    private Date bookingEnd;

    public CartItem(ParkingSpace parkingSpace, Date bookingStart, Date bookingEnd) {
        this.parkingSpace = parkingSpace;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    // Getters and setters
    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Date getBookingStart() {
        return bookingStart;
    }

    public void setBookingStart(Date bookingStart) {
        this.bookingStart = bookingStart;
    }

    public Date getBookingEnd() {
        return bookingEnd;
    }

    public void setBookingEnd(Date bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    // Optional: override equals() and hashCode() if you plan to remove by object
}
