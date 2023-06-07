package com.example.parking_lot.system;

import java.util.Optional;

public class FirstFreeParkingPicker implements ParkingPickerStrategy {
    @Override
    public Optional<PickedParkingSlot> pickParkingSlot(ParkingLot parkingLot) {
        return Optional.empty();
    }
}
