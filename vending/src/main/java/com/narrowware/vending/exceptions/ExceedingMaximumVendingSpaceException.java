package com.narrowware.vending.exceptions;

public class ExceedingMaximumVendingSpaceException extends RuntimeException {
    public ExceedingMaximumVendingSpaceException() {
        super("You can't put more than 10 items in this slot!");
    }
}
