package com.narrowware.vending.services;

import com.narrowware.vending.aspects.ClearSessionCoinsAspect;
import org.springframework.stereotype.Service;

/**
 * Why would I do such a thing here?
 * As we don't have RBAC, or capabilities or whatever other source of truth,
 * I want to ensure that the following method is only called from one place
 */
@Service
public class AllowanceService {

    public boolean issClearSessionCoinsEnabled() {
        final String aspectClassName = ClearSessionCoinsAspect.class.getName();
        final String paymentServiceClassName = PaymentService.class.getName();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().equals(aspectClassName) || element.getClassName().equals(paymentServiceClassName)) {
                return true;
            }
        }
        return false;
    }
}
