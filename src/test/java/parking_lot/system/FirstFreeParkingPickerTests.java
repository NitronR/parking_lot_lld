package parking_lot.system;

import com.example.parking_lot.system.*;
import org.junit.Test;

import java.util.Optional;

import static com.example.parking_lot.commands.constants.VehicleTypeIds.BIKE_TYPE;
import static com.example.parking_lot.commands.constants.VehicleTypeIds.CAR_TYPE;
import static org.junit.Assert.*;
import static parking_lot.constants.TestConstants.*;

public class FirstFreeParkingPickerTests {

    private final ParkingPickerStrategy parkingPickerStrategy = new FirstFreeParkingPicker();

    @Test
    public void should_returnFirstSlotOfFirstFloor_when_getAvailableParkingSlotCalledForEmptyParkingLit() {
        int numFloors = 4;
        ParkingLot parkingLot = new ParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        Optional<PickedParkingSlot> pickedParkingSlot = parkingPickerStrategy.pickParkingSlot(parkingLot, CAR_TYPE);

        int expectedFloor = 1, expectedSlot = 4;
        assertTrue(pickedParkingSlot.isPresent());
        assertEquals(expectedFloor, pickedParkingSlot.get().getFloorNumber());
        assertEquals(expectedSlot, pickedParkingSlot.get().getParkingSlotNumber());
    }

    @Test
    public void should_returnSecondParkingSlotForCar_when_getAvailableParkingSlotCalledWithFirstSlotOccupied() {
        int numFloors = 4;
        ParkingLot parkingLot = new ParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        parkingLot.parkVehicle(DUMMY_CAR, new PickedParkingSlot(1, 4));

        Optional<PickedParkingSlot> pickedParkingSlot = parkingPickerStrategy.pickParkingSlot(parkingLot, CAR_TYPE);

        int expectedFloor = 1, expectedSlot = 5;
        assertTrue(pickedParkingSlot.isPresent());
        assertEquals(expectedFloor, pickedParkingSlot.get().getFloorNumber());
        assertEquals(expectedSlot, pickedParkingSlot.get().getParkingSlotNumber());
    }

    @Test
    public void should_returnSlotFromSecondFloorForCar_when_getAvailableParkingSlotCalledWithFirstFloorOccupied() {
        int numFloors = 4;
        ParkingLot parkingLot = new ParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        parkingLot.parkVehicle(DUMMY_CAR, new PickedParkingSlot(1, 4));
        parkingLot.parkVehicle(DUMMY_CAR, new PickedParkingSlot(1, 5));
        parkingLot.parkVehicle(DUMMY_CAR, new PickedParkingSlot(1, 6));

        Optional<PickedParkingSlot> pickedParkingSlot = parkingPickerStrategy.pickParkingSlot(parkingLot, CAR_TYPE);

        int expectedFloor = 2, expectedSlot = 4;
        assertTrue(pickedParkingSlot.isPresent());
        assertEquals(expectedFloor, pickedParkingSlot.get().getFloorNumber());
        assertEquals(expectedSlot, pickedParkingSlot.get().getParkingSlotNumber());
    }

    @Test
    public void should_returnLowestAvailableParkingSlotForBike_when_getAvailableParkingSlotCalled() {
        int numFloors = 4;
        ParkingLot parkingLot = new ParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        parkingLot.parkVehicle(DUMMY_BIKE, new PickedParkingSlot(1, 2));

        Optional<PickedParkingSlot> pickedParkingSlot = parkingPickerStrategy.pickParkingSlot(parkingLot, BIKE_TYPE);

        int expectedFloor = 1, expectedSlot = 3;
        assertTrue(pickedParkingSlot.isPresent());
        assertEquals(expectedFloor, pickedParkingSlot.get().getFloorNumber());
        assertEquals(expectedSlot, pickedParkingSlot.get().getParkingSlotNumber());
    }

    @Test
    public void should_returnNoParkingSlot_when_noParkingAvailable() {
        int numFloors = 2;
        ParkingLot parkingLot = new ParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        parkingLot.parkVehicle(DUMMY_BIKE, new PickedParkingSlot(1, 2));
        parkingLot.parkVehicle(DUMMY_BIKE, new PickedParkingSlot(1, 3));
        parkingLot.parkVehicle(DUMMY_BIKE, new PickedParkingSlot(2, 2));
        parkingLot.parkVehicle(DUMMY_BIKE, new PickedParkingSlot(2, 3));

        Optional<PickedParkingSlot> pickedParkingSlot = parkingPickerStrategy.pickParkingSlot(parkingLot, BIKE_TYPE);

        assertFalse(pickedParkingSlot.isPresent());
    }

}
