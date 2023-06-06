package com.example.parking_lot.system;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class ParkingLot {
    private final String parkingLotId;
    private final List<ParkingFloor> floors;

    public ParkingLot(String parkingLotId, int numFloors, List<ParkingSlot> parkingSlots) {
        this.parkingLotId = parkingLotId;

        this.floors = IntStream.range(0, numFloors)
                .mapToObj(floorNumber -> new ParkingFloor(parkingSlots))
                .collect(Collectors.toList());
    }

}
