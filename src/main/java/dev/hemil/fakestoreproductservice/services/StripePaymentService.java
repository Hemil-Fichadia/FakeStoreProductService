package dev.hemil.fakestoreproductservice.services;

import dev.hemil.fakestoreproductservice.dtos.PaymentResponseDto;
import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentService implements PaymentService{

    @Override
    public String doPayment(String email, String phone, Long amount, String orderId) {

        return null;
    }
}
