package com.narrowware.vending.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Please add more coins!");
    }
}
