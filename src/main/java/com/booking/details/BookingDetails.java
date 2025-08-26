package com.booking.details;

import java.io.Serializable;

import com.parking.ParkingSpace;

public class BookingDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookingId;
    private ParkingSpace parkingSpace;
    private String bookedBy;
    private String bookingDate;
    private int hoursBooked;

    // Default constructor
    public BookingDetails() {
    }

    // Full constructor
    public BookingDetails(String bookingId, ParkingSpace parkingSpace, String bookedBy, String bookingDate, int hoursBooked) {
        this.bookingId = bookingId;
        this.parkingSpace = parkingSpace;
        this.bookedBy = bookedBy;
        this.bookingDate = bookingDate;
        this.hoursBooked = hoursBooked;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getHoursBooked() {
        return hoursBooked;
    }

    public void setHoursBooked(int hoursBooked) {
        this.hoursBooked = hoursBooked;
    }

    @Override
    public String toString() {
        return "BookingDetails [bookingId=" + bookingId + ", parkingSpace=" + parkingSpace + ", bookedBy=" + bookedBy
                + ", bookingDate=" + bookingDate + ", hoursBooked=" + hoursBooked + "]";
    }
}
