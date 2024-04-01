package dev.hemil.fakestoreproductservice.controllers;

import com.razorpay.RazorpayException;
import dev.hemil.fakestoreproductservice.dtos.InitiatePaymentRequestDto;
import dev.hemil.fakestoreproductservice.services.PaymentService;
import dev.hemil.fakestoreproductservice.services.strategy.PaymentGatewaySelectionStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private PaymentService razorpayPaymentService;
    private PaymentService stripePaymentService;
    private PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy;
    public PaymentController(
            @Qualifier("razorpay") PaymentService razorpayPaymentService,
            @Qualifier("stripe") PaymentService stripePaymentService,
            PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy){
        this.razorpayPaymentService = razorpayPaymentService;
        this.stripePaymentService = stripePaymentService;
        this.paymentGatewaySelectionStrategy = paymentGatewaySelectionStrategy;
    }

    private int choosePaymentGateway(){
        return paymentGatewaySelectionStrategy.paymentGatewaySelection();
    }

    @PostMapping("/payment")
    public String initiatePayment(@RequestBody InitiatePaymentRequestDto requestDto) throws RazorpayException {
        int paymentGatewayOption = choosePaymentGateway();
        if(paymentGatewayOption <= 7){
            return razorpayPaymentService.doPayment(requestDto.getEmail(),
                    requestDto.getPhoneNumber(),
                    requestDto.getAmount(),
                    requestDto.getOrderId()
            );
        }
        return stripePaymentService.doPayment(requestDto.getEmail(),
                requestDto.getPhoneNumber(),
                requestDto.getAmount(),
                requestDto.getOrderId()
        );
    }


}
