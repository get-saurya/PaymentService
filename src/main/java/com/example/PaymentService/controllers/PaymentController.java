package com.example.PaymentService.controllers;

import com.example.PaymentService.dtos.GeneratePaymentLinkRequestDto;
import com.example.PaymentService.services.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(@Qualifier("stripePaymentGateway") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //Post --> http://Localhost:8080/payments
    @PostMapping()
    public String generatePaymentLink(@RequestBody GeneratePaymentLinkRequestDto requestDto) throws RazorpayException, StripeException {
        //Ideally we should handle the exception in the Controller using Controller Advices.see productService lecture
        return paymentService.generatePaymentLink(requestDto.getOrderId());
    }

    @PostMapping("/webhook")
    public void handleWebhookEvent(@RequestBody Object object){
        System.out.println("Webhook Event triggered");
    }
}
