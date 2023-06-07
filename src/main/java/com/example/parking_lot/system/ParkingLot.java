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

    public void parkVehicle(Vehicle vehicle, PickedParkingSlot pickedParkingSlot) {
        // TODO validate floor and slot number validity and availability
        ParkingFloor parkingFloor = floors.get(pickedParkingSlot.getFloorNumber() - 1);
        parkingFloor.parkVehicle(vehicle, pickedParkingSlot.getParkingSlotNumber());
    }

    public Vehicle unpark(int floorNumber, int slotNumber) {
        return floors.get(floorNumber - 1).unpark(slotNumber);
    }

    public List<List<Integer>> getOccupiedSlotsPerFloor(String vehicleTypeId) {
        return floors.stream()
                .map(floor -> floor.getOccupiedSlots(vehicleTypeId))
                .collect(Collectors.toList());
    }
}
