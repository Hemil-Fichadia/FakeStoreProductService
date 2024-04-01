package dev.hemil.fakestoreproductservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitiatePaymentRequestDto {
    private String email;
    private String phoneNumber;
    private Long amount;
    private String orderId;
}
