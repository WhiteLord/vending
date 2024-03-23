package com.narrowware.vending.aspects;

import com.narrowware.vending.services.PaymentService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ClearSessionCoinsAspect {

    @Autowired
    private PaymentService paymentService;

    @AfterReturning("@annotation(com.narrowware.vending.annotations.ClearSessionCoins)")
    public void clearSessionCoins() {
        this.paymentService.emptySessionCoins();
    }
}
