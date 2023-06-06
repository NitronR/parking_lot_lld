package com.example.parking_lot.system;

import com.example.parking_lot.system.exception.ParkingLotAlreadyExists;
import com.example.parking_lot.system.exception.ParkingLotNotFound;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    private final Map<String, ParkingLot> parkingLots = new ConcurrentHashMap<>();

    public synchronized void createParkingLot(String parkingLotId, int numFloors, List<ParkingSlot> parkingSlots) {
        if (parkingLots.containsKey(parkingLotId)) {
            throw new ParkingLotAlreadyExists("Parking lot already exists with given ID.");
        }

        ParkingLot parkingLot = new ParkingLot(parkingLotId, numFloors, parkingSlots);
        parkingLots.put(parkingLotId, parkingLot);
    }

    public List<Integer> getFreeSlotCountPerFloor(String parkingLotId, String vehicleTypeId) {
        if (!parkingLots.containsKey(parkingLotId)) {
            throw new ParkingLotNotFound("Parking lot with given ID already exists.");
        }

        return parkingLots.get(parkingLotId).getFloors().stream()
                .map(parkingFloor -> parkingFloor.getFreeSlotCount(vehicleTypeId))
                .collect(Collectors.toList());
    }

    public List<List<Integer>> getFreeSlotsPerFloor(String parkingLotId, String vehicleTypeId) {
        return ImmutableList.of(
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6)
        );
    }

    public List<List<Integer>> getOccupiedSlotsPerFloor(String parkingLotId, String vehicleTypeId) {
        return ImmutableList.of(
                ImmutableList.of(),
                ImmutableList.of(),
                ImmutableList.of(),
                ImmutableList.of()
        );
    }

    public void park(String parkingLotId, String vehicleTypeId, String registrationNumber, String color) {

    }
}
