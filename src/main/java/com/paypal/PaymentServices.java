package com.paypal;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;


public class PaymentServices {

    private static final String CLIENT_ID     = "ASApEo84olgCTq1R5gr60aZ4MYLeX-zg6pTqN3xf1Z2uTJzEVMrbxQazwmlCtzWYCTvg9DbC2R219piP";
    private static final String CLIENT_SECRET = "EK31DoalUk0JH9CE8Ex2vCLKUUVW_wWj84ovj1JGpLGOZwV6CHP2QKdL5A3AH7deGxetHLym_PsyXhet";
    private static final String MODE          = "sandbox"; // or "live"

    /** Start an authorization, returns the approval URL you should redirect the user to */
    public String authorizePayment(OrderDetail orderDetail) throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setIntent("authorize");
        requestPayment.setPayer(payer);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setTransactions(listTransaction);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo info = new PayerInfo()
                .setFirstName("Team7")
                .setLastName("ABC")
                .setEmail("A00335638@student.tus.ie");
        payer.setPayerInfo(info);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8082/com.Team7Project/cancelledPaypal.xhtml");
        redirectUrls.setReturnUrl("http://localhost:8082/com.Team7Project/confirmedPaypal.xhtml");
        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {

        // Convert values safely to double before formatting
        double shipping = Double.parseDouble(orderDetail.getShipping().toString());
        double subtotal = Double.parseDouble(orderDetail.getSubTotal().toString());
        double tax      = Double.parseDouble(orderDetail.getTax().toString());
        double total    = Double.parseDouble(orderDetail.getTotal().toString());

        Details details = new Details();
        details.setShipping(String.format("%.2f", shipping));
        details.setSubtotal(String.format("%.2f", subtotal));
        details.setTax(String.format("%.2f", tax));

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(String.format("%.2f", total));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setDescription(orderDetail.getProductName());
        transaction.setAmount(amount);

        // Optional line item
        Item item = new Item();
        item.setName(orderDetail.getProductName());
        item.setCurrency("EUR");
        item.setPrice(String.format("%.2f", subtotal));
        item.setTax(String.format("%.2f", tax));
        item.setQuantity("1");

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        items.add(item);
        itemList.setItems(items);

        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);
        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        for (Links link : approvedPayment.getLinks()) {
            if ("approval_url".equalsIgnoreCase(link.getRel())) {
                return link.getHref();
            }
        }
        return null;
    }

    /** After user returns from PayPal (with paymentId & PayerID) */
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);

        Payment payment = new Payment();
        payment.setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return payment.execute(apiContext, execution);
    }

    /** Optional: look up a payment by id */
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }
}
