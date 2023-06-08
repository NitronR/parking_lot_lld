package com.example.parking_lot.exception;

import com.example.parking_lot.system.exception.ParkingLotSystemException;

public class InvalidTicketException extends ParkingLotSystemException {
    public InvalidTicketException(String message) {
        super(message);
    }
}
