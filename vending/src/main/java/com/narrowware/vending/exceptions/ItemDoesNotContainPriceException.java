package com.narrowware.vending.exceptions;

public class ItemDoesNotContainPriceException extends RuntimeException {
    public ItemDoesNotContainPriceException(){super ("Tis item is not for sale. It sesm the admin forgot to assign a price...");}
}
