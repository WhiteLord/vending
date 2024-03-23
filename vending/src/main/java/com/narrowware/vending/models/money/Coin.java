package com.narrowware.vending.models.money;

public enum Coin {
    TENNER(10),
    TWENNY(20),
    FITTY(50),
    LEV(100),
    DVA(200);

    private final int value;

    Coin(int value) { this.value = value; }

    public int getValue() {
        return value;
    }
}
