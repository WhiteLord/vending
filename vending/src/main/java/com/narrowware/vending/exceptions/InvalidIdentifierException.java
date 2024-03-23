package com.narrowware.vending.exceptions;

public class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException() { super("The requested item does not exist or is invalid");}
}