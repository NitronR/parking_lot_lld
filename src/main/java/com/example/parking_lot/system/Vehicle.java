package com.example.parking_lot.system;

import lombok.Getter;

@Getter
public class Vehicle {
    private final String vehicleTypeId;
    private final String registrationNumber;
    private final String color;

    public Vehicle(String vehicleTypeId, String registrationNumber, String color) {
        this.vehicleTypeId = vehicleTypeId;
        this.registrationNumber = registrationNumber;
        this.color = color;
    }
}
