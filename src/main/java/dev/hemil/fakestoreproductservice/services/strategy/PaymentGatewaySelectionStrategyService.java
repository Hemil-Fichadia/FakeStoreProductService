package dev.hemil.fakestoreproductservice.services.strategy;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentGatewaySelectionStrategyService implements PaymentGatewaySelectionStrategy{

    @Override
    public int paymentGatewaySelection() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }
}
