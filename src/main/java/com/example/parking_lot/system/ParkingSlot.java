package com.example.parking_lot.system;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ParkingSlot {
    private final int slotNumber;
    private final String vehicleTypeId;

    public ParkingSlot(int slotNumber, String vehicleTypeId) {
        this.slotNumber = slotNumber;
        this.vehicleTypeId = vehicleTypeId;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }
}
