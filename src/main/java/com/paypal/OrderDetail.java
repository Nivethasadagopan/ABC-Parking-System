package com.paypal;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class OrderDetail {
    private String productName;
    private float subTotal;
    private float shipping;
    private float tax;
    private float total;
 
    public OrderDetail(String productName, float subtotal,
            float shipping, float tax, float total) {
        this.productName = productName;
        this.subTotal = subtotal;
        this.shipping = shipping;
        this.tax = tax;
        this.total = total;
    }
 
    public String getProductName() {
        return productName;
    }
 
    public String getSubTotal() {
        return String.format("%.2f", subTotal);
    }
 
    public String getShipping() {
        return String.format("%.2f", shipping);
    }
 
    public String getTax() {
        return String.format("%.2f", tax);
    }
     
    public String getTotal() {
        return String.format("%.2f", total);
    }
}
