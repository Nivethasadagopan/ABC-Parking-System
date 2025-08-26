package com.paypal;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.paypal.base.rest.PayPalRESTException;

@ManagedBean
@SessionScoped
public class AuthorizePayment {

    private String product;
    private float subTotal, shipping, tax;

    @PostConstruct
    public void init() {
        // Initialize default values when page loads
        this.product = "Parking Space Bookings";
        this.subTotal = 0;
        this.shipping = 0;
        this.tax = 0;
    }

    // Product
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    // Subtotal
    public float getSubTotal() { return subTotal; }
    public void setSubTotal(float subTotal) { this.subTotal = subTotal; }

    // Shipping
    public float getShipping() { return shipping; }
    public void setShipping(float shipping) { this.shipping = shipping; }

    // Tax
    public float getTax() { return tax; }
    public void setTax(float tax) { this.tax = tax; }

    // Total is calculated dynamically
    public float getTotal() {
        return subTotal + shipping + tax;
    }

    // Checkout method
    public String checkOut() throws IOException {
        try {
            OrderDetail orderDetail = new OrderDetail(product, subTotal, shipping, tax, getTotal());
            PaymentServices paymentServices = new PaymentServices();
            String approvalLink = paymentServices.authorizePayment(orderDetail);
            FacesContext.getCurrentInstance().getExternalContext().redirect(approvalLink);
        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // -------------------- ADDITION START --------------------
    // Inject CartBean to dynamically get subtotal
    @javax.faces.bean.ManagedProperty(value = "#{cartBean}")
    private com.parking.CartBean cartBean;

    public com.parking.CartBean getCartBean() {
        return cartBean;
    }

    public void setCartBean(com.parking.CartBean cartBean) {
        this.cartBean = cartBean;
    }

    // Update subtotal automatically from CartBean
    public void updateSubTotalFromCart() {
        if (cartBean != null) {
            this.subTotal = cartBean.getTotalPrice();
        }
    }

    // Return updated total dynamically
    public float getTotalUpdated() {
        updateSubTotalFromCart(); // ensure subtotal is current
        return getTotal();         // subTotal + shipping + tax
    }
    // -------------------- ADDITION END --------------------
}
