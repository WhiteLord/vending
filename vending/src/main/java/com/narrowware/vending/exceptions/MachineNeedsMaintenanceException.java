package com.narrowware.vending.exceptions;

public class MachineNeedsMaintenanceException extends RuntimeException {
    public MachineNeedsMaintenanceException() {super("The machine is out of order! Please, call the maintenance number."); }
}
