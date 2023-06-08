package com.example.parking_lot.system;

import java.util.List;
import java.util.Optional;

public class FirstFreeParkingPicker implements ParkingPickerStrategy {
    @Override
    public Optional<PickedParkingSlot> pickParkingSlot(ParkingLot parkingLot, String vehicleTypeId) {
        for (int floorNumber = 1; floorNumber <= parkingLot.getFloors().size(); floorNumber++) {
            List<Integer> freeSlots = parkingLot.getFloors().get(floorNumber - 1).getFreeSlots(vehicleTypeId);
            if (freeSlots.size() > 0) {
                return Optional.of(new PickedParkingSlot(floorNumber, freeSlots.get(0)));
            }
        }

        return Optional.empty();
    }
}
