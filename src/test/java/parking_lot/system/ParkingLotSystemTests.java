package parking_lot.system;

import com.example.parking_lot.system.*;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.example.parking_lot.commands.constants.VehicleTypeIds.BIKE_TYPE;
import static com.example.parking_lot.commands.constants.VehicleTypeIds.CAR_TYPE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static parking_lot.constants.TestConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotSystemTests {

    private ParkingLotSystem system;

    @Mock
    private ParkingPickerStrategy parkingStrategy;

    @Before
    public void setup() {
        system = new ParkingLotSystem(parkingStrategy);
    }

    @Test
    public void should_haveFreeSlotCountEqualToTotalSlotCount_when_parkingLotCreatedWithThreeFloors() {
        int numFloors = 3;

        system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        List<Integer> expectedFreeSlotCount = ImmutableList.of(3, 3, 3);
        assertEquals(expectedFreeSlotCount, system.getFreeSlotCountPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_haveFreeSlotCountEqualToTotalSlotCount_when_parkingLotCreatedWithFourFloors() {
        int numFloors = 4;

        system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        List<Integer> expectedFreeSlotCount = ImmutableList.of(3, 3, 3, 3);
        assertEquals(expectedFreeSlotCount, system.getFreeSlotCountPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_haveAllFreeSlots_when_parkingLotCreated() {
        int numFloors = 4;

        system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        List<List<Integer>> expectedFreeSlots = ImmutableList.of(
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6)
        );

        assertEquals(expectedFreeSlots, system.getFreeSlotsPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_haveNoOccupiedSlots_when_parkingLotCreated() {
        int numFloors = 4;

        system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        List<List<Integer>> expectedOccupiedSlots = ImmutableList.of(
                ImmutableList.of(),
                ImmutableList.of(),
                ImmutableList.of(),
                ImmutableList.of()
        );

        assertEquals(expectedOccupiedSlots, system.getOccupiedSlotsPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_decreaseCarFreeSlotCount_when_carParked() {
        int numFloors = 4;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(1, 4)));

        system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        List<Integer> expectedFreeSlotCount = ImmutableList.of(2, 3, 3, 3);
        assertEquals(expectedFreeSlotCount, system.getFreeSlotCountPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_returnTicketId_when_carParked() {
        int numFloors = 4;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(1, 1)));

        Optional<String> ticketIdOptional = system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER,
                DUMMY_COLOR_RED);

        assertTrue(ticketIdOptional.isPresent());
        assertEquals(ticketIdOptional.get(), "PR1234_1_1");
    }

    @Test
    public void should_decreaseBikeFreeSlotCount_when_bikeParked() {
        int numFloors = 4;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(2, 3)));

        system.park(DUMMY_PARKING_LOT_ID, BIKE_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        List<Integer> expectedFreeSlotCount = ImmutableList.of(2, 1, 2, 2);
        assertEquals(expectedFreeSlotCount, system.getFreeSlotCountPerFloor(DUMMY_PARKING_LOT_ID, BIKE_TYPE));
    }

    @Test
    public void should_returnEmptyOptional_when_carParkRequestedForFullParkingLot() {
        int numFloors = 1;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.empty());

        Optional<String> ticketIdOptional =
                system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        assertFalse(ticketIdOptional.isPresent());
    }

    @Test
    public void should_throwVehicleAlreadyParkedException_whenCarParkingRequestedWithExistingRegistrationNumber() {

    }

    @Test
    public void should_increaseBikeFreeSlotCount_when_bikeUnparked() {
        int numFloors = 4;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(2, 3)));

        system.park(DUMMY_PARKING_LOT_ID, BIKE_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        String ticketId = String.format("%s_2_3", DUMMY_PARKING_LOT_ID);
        Vehicle vehicle = system.unpark(ticketId);

        List<Integer> expectedFreeSlotCount = ImmutableList.of(2, 2, 2, 2);
        assertEquals(expectedFreeSlotCount, system.getFreeSlotCountPerFloor(DUMMY_PARKING_LOT_ID, BIKE_TYPE));
        assertEquals(DUMMY_REGISTRATION_NUMBER, vehicle.getRegistrationNumber());
        assertEquals(DUMMY_COLOR_RED, vehicle.getColor());
    }

    @Test
    public void should_removeFreeCarSlot_when_carParked() {
        int numFloors = 4;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(1, 4)));

        system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        List<List<Integer>> expectedFreeSlots = ImmutableList.of(
                ImmutableList.of(5, 6),
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6)
        );

        assertEquals(expectedFreeSlots, system.getFreeSlotsPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_addFreeCarSlot_when_carUnparked() {
        int numFloors = 2;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(2, 5)));

        system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        String ticketId = String.format("%s_2_5", DUMMY_PARKING_LOT_ID);
        system.unpark(ticketId);

        List<List<Integer>> expectedFreeSlots = ImmutableList.of(
                ImmutableList.of(4, 5, 6),
                ImmutableList.of(4, 5, 6)
        );

        assertEquals(expectedFreeSlots, system.getFreeSlotsPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_addFreeBikeSlot_when_bikeUnparked() {
        int numFloors = 2;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(2, 3)));

        system.park(DUMMY_PARKING_LOT_ID, BIKE_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        String ticketId = String.format("%s_2_3", DUMMY_PARKING_LOT_ID);
        system.unpark(ticketId);

        List<List<Integer>> expectedFreeSlots = ImmutableList.of(
                ImmutableList.of(2, 3),
                ImmutableList.of(2, 3)
        );

        assertEquals(expectedFreeSlots, system.getFreeSlotsPerFloor(DUMMY_PARKING_LOT_ID, BIKE_TYPE));
    }

    @Test
    public void should_addOccupiedCarSlot_when_carParked() {
        int numFloors = 2;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(2, 5)));

        system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        List<List<Integer>> expectedOccupiedSlots = ImmutableList.of(
                ImmutableList.of(),
                ImmutableList.of(5)
        );

        assertEquals(expectedOccupiedSlots, system.getOccupiedSlotsPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_removeOccupiedCarSlot_when_carUnparked() {
        int numFloors = 2;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(2, 5)));

        system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        String ticketId = String.format("%s_2_5", DUMMY_PARKING_LOT_ID);
        system.unpark(ticketId);

        List<List<Integer>> expectedOccupiedSlots = ImmutableList.of(
                ImmutableList.of(),
                ImmutableList.of()
        );

        assertEquals(expectedOccupiedSlots, system.getOccupiedSlotsPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_removeOccupiedBikeSlot_when_bikeUnparked() {
        int numFloors = 2;

        ParkingLot parkingLot = system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);

        when(parkingStrategy.pickParkingSlot(parkingLot))
                .thenReturn(Optional.of(new PickedParkingSlot(2, 3)));

        system.park(DUMMY_PARKING_LOT_ID, BIKE_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        String ticketId = String.format("%s_2_3", DUMMY_PARKING_LOT_ID);
        system.unpark(ticketId);

        List<List<Integer>> expectedOccupiedSlots = ImmutableList.of(
                ImmutableList.of(),
                ImmutableList.of()
        );

        assertEquals(expectedOccupiedSlots, system.getOccupiedSlotsPerFloor(DUMMY_PARKING_LOT_ID, BIKE_TYPE));
    }

    @Test
    public void should_throwParkingLotNotFound_when_invalidParkingLotIdPassed() {

    }

}
