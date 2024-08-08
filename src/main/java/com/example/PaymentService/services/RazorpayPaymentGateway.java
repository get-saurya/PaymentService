package com.example.PaymentService.services;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("razorpayPaymentGateway")
public class RazorpayPaymentGateway implements PaymentService {
    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String generatePaymentLink (Long orderId) throws RazorpayException, StripeException {
        //Make a call to Razorpay to generate the payment Link
        JSONObject paymentLinkRequest = new JSONObject();

        paymentLinkRequest.put("amount",1000); //10.00
        paymentLinkRequest.put("currency","INR");
//        paymentLinkRequest.put("accept_partial",true);
//        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + 10 * 60 *1000); //10 minutes
        paymentLinkRequest.put("reference_id", orderId.toString());
        paymentLinkRequest.put("description","Payment for testing payment gateway integration");
        JSONObject customer = new JSONObject();

        //call the orderService to get the order details
        //Order order = restTemplate.getOrderObject("orderService URL", Order.class);

        customer.put("name","Surajit Gouri"); //order.customer_name
        customer.put("contact","+917908316164"); //order.customer_contact_number
        customer.put("email","surajitroy35213@gmail.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Jeevan Bima");
//        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://scaler.com/"); //callback url
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        //return payment.toString(); complte object returns
        return payment.get("short_url");
    }
}
