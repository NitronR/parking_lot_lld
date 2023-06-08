package com.example.parking_lot.system;

import java.util.Optional;

public interface ParkingPickerStrategy {

    /**
     * Picks an available parking slot and returns it. If none present, returns empty Optional.
     *
     * @param parkingLot    Parking lot from which a parking slot should be picked.
     * @param vehicleTypeId Denotes type of vehicle for which the parking is to be picked.
     * @return Available parking slot if present else empty Optional.
     */
    Optional<PickedParkingSlot> pickParkingSlot(ParkingLot parkingLot, String vehicleTypeId);

}
