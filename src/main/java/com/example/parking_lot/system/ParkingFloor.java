package com.example.parking_lot.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingFloor {

    private final ConcurrentHashMap<String, Set<Integer>> freeSlots = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> totalSlots = new ConcurrentHashMap<>();

    public ParkingFloor(List<ParkingSlot> parkingSlots) {
        parkingSlots.forEach(this::insertAvailableSlot);
        initTotalCount();
    }

    private void insertAvailableSlot(ParkingSlot parkingSlot) {
        Set<Integer> availableSlotsForVehicleType =
                freeSlots.getOrDefault(parkingSlot.getVehicleTypeId(), new TreeSet<>());
        availableSlotsForVehicleType.add(parkingSlot.getSlotNumber());
        freeSlots.put(parkingSlot.getVehicleTypeId(), availableSlotsForVehicleType);
    }

    private void initTotalCount() {
        freeSlots.forEach((vehicleTypeId, availableSlots) -> totalSlots.put(vehicleTypeId, availableSlots.size()));
    }

    public int getFreeSlotCount(String vehicleTypeId) {
        return freeSlots.getOrDefault(vehicleTypeId, new HashSet<>()).size();
    }

}
