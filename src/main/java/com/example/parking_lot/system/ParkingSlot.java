package com.example.parking_lot.system;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ParkingSlot {
    private final int slotNumber;
    private final String vehicleTypeId;

    public ParkingSlot(int slotNumber, String vehicleTypeId) {
        this.slotNumber = slotNumber;
        this.vehicleTypeId = vehicleTypeId;
    }

}
