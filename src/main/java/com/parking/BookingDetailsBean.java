package com.parking;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class BookingDetailsBean implements Serializable {

    private String spaceId;
    private ParkingSpace selectedSpace;

    private Date entryDateTime;
    private Date exitDateTime;

    // Inject the session cart bean
    @ManagedProperty("#{cartBean}")
    private CartBean cartBean;
    public void setCartBean(CartBean cartBean) { this.cartBean = cartBean; }

    // ✅ Inject ParkingSpaceBean so we can fetch the real space by ID
    @ManagedProperty("#{parkingSpaceBean}")
    private ParkingSpaceBean parkingSpaceBean;
    public void setParkingSpaceBean(ParkingSpaceBean parkingSpaceBean) { this.parkingSpaceBean = parkingSpaceBean; }

    // ====== GETTERS & SETTERS ======
    public String getSpaceId() { return spaceId; }
    public void setSpaceId(String spaceId) { this.spaceId = spaceId; }

    public ParkingSpace getSelectedSpace() { return selectedSpace; }
    public void setSelectedSpace(ParkingSpace selectedSpace) { this.selectedSpace = selectedSpace; }

    public Date getEntryDateTime() { return entryDateTime; }
    public void setEntryDateTime(Date entryDateTime) { this.entryDateTime = entryDateTime; }

    public Date getExitDateTime() { return exitDateTime; }
    public void setExitDateTime(Date exitDateTime) { this.exitDateTime = exitDateTime; }

    // ====== Load parking space (called by f:event) ======
    public void loadParkingSpace() {
        if (spaceId != null && (selectedSpace == null || !spaceId.equals(selectedSpace.getId()))) {
            // ❌ REMOVE the mock object creation
            // ✅ Fetch from ParkingSpaceBean, which holds the real list (with spaceNumber, address, etc.)
            selectedSpace = parkingSpaceBean.findParkingSpaceById(spaceId);
        }
    }

    // ====== Proceed to Checkout: add to cart + redirect to view_cart ======
    public String proceedToCheckout() {
        if (entryDateTime == null || exitDateTime == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Both Entry and Exit date/time are required!", null));
            return null;
        }
        if (!exitDateTime.after(entryDateTime)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exit date/time must be after Entry date/time.", null));
            return null;
        }

        if (selectedSpace == null) loadParkingSpace();
        if (selectedSpace == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selected parking space was not found.", null));
            return null;
        }

        cartBean.addToCart(selectedSpace, entryDateTime, exitDateTime);

    

        return "view_cart.xhtml?faces-redirect=true";
        
    }
}
