package com.example.parking_lot.system;

import com.example.parking_lot.exception.InvalidTicketException;
import com.example.parking_lot.system.exception.ParkingLotAlreadyExists;
import com.example.parking_lot.system.exception.ParkingLotNotFound;
import com.example.parking_lot.system.exception.VehicleNotPresentException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    private final Map<String, ParkingLot> parkingLots = new ConcurrentHashMap<>();
    private final ParkingPickerStrategy parkingPickerStrategy;

    public ParkingLotSystem(ParkingPickerStrategy parkingPickerStrategy) {
        this.parkingPickerStrategy = parkingPickerStrategy;
    }

    public synchronized ParkingLot createParkingLot(String parkingLotId, int numFloors, List<ParkingSlot> parkingSlots) {
        if (parkingLots.containsKey(parkingLotId)) {
            throw new ParkingLotAlreadyExists("Parking lot already exists with given ID.");
        }

        // TODO factory for parking lot creation
        ParkingLot parkingLot = new ParkingLot(parkingLotId, numFloors, parkingSlots);
        parkingLots.put(parkingLotId, parkingLot);

        return parkingLot;
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
        return parkingLots.get(parkingLotId).getFloors().stream()
                .map(parkingFloor -> parkingFloor.getFreeSlots(vehicleTypeId))
                .collect(Collectors.toList());
    }

    public List<List<Integer>> getOccupiedSlotsPerFloor(String parkingLotId, String vehicleTypeId) {
        return parkingLots.get(parkingLotId).getOccupiedSlotsPerFloor(vehicleTypeId);
    }

    public Optional<String> park(String parkingLotId, String vehicleTypeId, String registrationNumber,
                                 String color) {
        // TODO check if parkingLotId valid
        ParkingLot parkingLot = parkingLots.get(parkingLotId);
        Optional<PickedParkingSlot> pickedParkingSlotOptional =
                parkingPickerStrategy.pickParkingSlot(parkingLot, vehicleTypeId);

        Vehicle vehicle = new Vehicle(vehicleTypeId, registrationNumber, color);
        pickedParkingSlotOptional.ifPresent(pickedParkingSlot -> parkingLot.parkVehicle(vehicle, pickedParkingSlot));

        return pickedParkingSlotOptional.map(pickedParkingSlot -> getTicketId(parkingLotId, pickedParkingSlot));
    }

    private String getTicketId(String parkingLotId, PickedParkingSlot pickedParkingSlot) {
        return String.format("%s_%d_%d", parkingLotId, pickedParkingSlot.getFloorNumber(),
                pickedParkingSlot.getParkingSlotNumber());
    }

    public Vehicle unpark(String ticketId) {
        // TODO ticket id validation
        String[] ticketIdComponents = ticketId.split("_");

        if (ticketIdComponents.length != 3) {
            throw new InvalidTicketException("Invalid Ticket");
        }

        try {
            String parkingLotId = ticketIdComponents[0];
            int floorNumber = Integer.parseInt(ticketIdComponents[1]);
            int slotNumber = Integer.parseInt(ticketIdComponents[2]);

            if (!parkingLots.containsKey(parkingLotId)) {
                throw new InvalidTicketException("Invalid Ticket");
            }

            ParkingLot parkingLot = parkingLots.get(parkingLotId);

            return parkingLot.unpark(floorNumber, slotNumber);
        } catch (NumberFormatException | VehicleNotPresentException exception) {
            throw new InvalidTicketException("Invalid Ticket");
        }
    }
}
