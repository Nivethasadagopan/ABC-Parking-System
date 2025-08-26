package com.parking;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "cartBean")
@SessionScoped
public class CartBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CartItem> cartItems = new ArrayList<>();

    // Add item to cart
    public void addToCart(ParkingSpace parkingSpace, Date bookingStart, Date bookingEnd) {
        if (parkingSpace == null || bookingStart == null || bookingEnd == null) return;
        CartItem newItem = new CartItem(parkingSpace, bookingStart, bookingEnd);
        cartItems.add(newItem);
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Booking added to cart.")
        );
    }

    // Remove item from cart
    public void removeFromCart(CartItem cartItem) {
        if (cartItem != null) {
            cartItems.remove(cartItem);
        }
    }

    // Go to checkout page
    public String checkout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().put("checkoutItems", new ArrayList<>(cartItems));
        return "checkout.xhtml?faces-redirect=true";
    }

    // Getters
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getCartItemCount() {
        return (cartItems != null) ? cartItems.size() : 0;
    }

    // Clear cart
    public void clearCart() {
        cartItems.clear();
    }

    // Calculate total price of all cart items
    public float getTotalPrice() {
        float total = 0f;
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                total += getItemCost(item);
            }
        }
        return total;
    }

    // Mark all spaces as booked
    public void markSpacesAsBooked() {
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                if (item.getParkingSpace() != null) {
                    item.getParkingSpace().setAvailable(false);
                }
            }
        }
    }

    // Calculate cost of a single item
    public float getItemCost(CartItem item) {
        if (item == null || item.getBookingStart() == null || item.getBookingEnd() == null || item.getParkingSpace() == null)
            return 0f;
        long millis = item.getBookingEnd().getTime() - item.getBookingStart().getTime();
        long hours = millis / (1000 * 60 * 60);
        if (hours <= 0) hours = 1; // Minimum 1 hour
        return (float) (item.getParkingSpace().getPricePerHour() * hours);
    }
}
