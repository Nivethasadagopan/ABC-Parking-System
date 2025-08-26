package com.parking;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

// import com.booking.BookingBean; // ❌ remove this, not needed anymore

public class CheckoutBean implements Serializable {

    private BookingBean currentBooking;  // Now uses com.parking.BookingBean

    @PostConstruct
    public void init() {
        this.currentBooking = (BookingBean) FacesContext.getCurrentInstance()
                .getExternalContext().getFlash().get("bookingDetails");
    }

    public String confirmAndPay() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Success!", "Your booking is confirmed."));

        return "index2.xhtml?faces-redirect=true";
    }

    public BookingBean getCurrentBooking() {
        return currentBooking;
    }
}
