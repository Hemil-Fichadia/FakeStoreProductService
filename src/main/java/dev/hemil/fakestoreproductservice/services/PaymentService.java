package dev.hemil.fakestoreproductservice.services;

import com.razorpay.RazorpayException;
import dev.hemil.fakestoreproductservice.dtos.PaymentResponseDto;

public interface PaymentService {
    String doPayment(String email, String phone, Long amount, String orderId) throws RazorpayException;
}
