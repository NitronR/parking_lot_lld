package com.example.parking_lot.system;

import com.example.parking_lot.system.exception.VehicleNotPresentException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingFloor {

    // TODO data structures should be part of parking strategy?
    private final ConcurrentHashMap<String, Set<Integer>> freeSlots = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<Integer>> occupiedSlots = new ConcurrentHashMap<>();
    private final List<Optional<Vehicle>> parkedVehicles = new ArrayList<>();

    public ParkingFloor(List<ParkingSlot> parkingSlots) {
        parkingSlots.forEach((parkingSlot) -> {
            parkedVehicles.add(Optional.empty());
            insertAvailableSlot(parkingSlot);
        });
        initOccupiedSlots();
    }

    private void insertAvailableSlot(ParkingSlot parkingSlot) {
        Set<Integer> availableSlotsForVehicleType =
                freeSlots.getOrDefault(parkingSlot.getVehicleTypeId(), new TreeSet<>());
        availableSlotsForVehicleType.add(parkingSlot.getSlotNumber());
        freeSlots.put(parkingSlot.getVehicleTypeId(), availableSlotsForVehicleType);
    }

    private void initOccupiedSlots() {
        freeSlots.forEach((vehicleTypeId, availableSlots) -> occupiedSlots.put(vehicleTypeId, new HashSet<>()));
    }

    public int getFreeSlotCount(String vehicleTypeId) {
        return freeSlots.getOrDefault(vehicleTypeId, new HashSet<>()).size();
    }

    public void parkVehicle(Vehicle vehicle, int parkingSlotNumber) {
        parkedVehicles.set(parkingSlotNumber - 1, Optional.of(vehicle));
        freeSlots.get(vehicle.getVehicleTypeId()).remove(parkingSlotNumber);
        occupiedSlots.get(vehicle.getVehicleTypeId()).add(parkingSlotNumber);
    }

    public Vehicle unpark(int slotNumber) {
        Vehicle parkedVehicle = parkedVehicles.get(slotNumber - 1)
                .orElseThrow(() -> new VehicleNotPresentException("No parked vehicle available at this slot."));

        parkedVehicles.set(slotNumber - 1, Optional.empty());
        freeSlots.get(parkedVehicle.getVehicleTypeId()).add(slotNumber);
        occupiedSlots.get(parkedVehicle.getVehicleTypeId()).remove(slotNumber);
        return parkedVehicle;
    }

    public List<Integer> getFreeSlots(String vehicleTypeId) {
        return new ArrayList<>(freeSlots.get(vehicleTypeId));
    }

    public List<Integer> getOccupiedSlots(String vehicleTypeId) {
        return new ArrayList<>(occupiedSlots.get(vehicleTypeId));
    }
}
