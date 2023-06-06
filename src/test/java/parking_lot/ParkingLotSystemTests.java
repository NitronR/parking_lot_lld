package parking_lot;

import com.example.parking_lot.system.ParkingLotSystem;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.example.parking_lot.commands.constants.VehicleTypeIds.CAR_TYPE;
import static org.junit.Assert.assertEquals;
import static parking_lot.constants.TestConstants.*;

public class ParkingLotSystemTests {

    private ParkingLotSystem system;

    @Before
    public void setup() {
        system = new ParkingLotSystem();
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
    public void should_increaseCarFreeSlotCount_when_carParked() {
        int numFloors = 4;

        system.createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, DUMMY_SLOTS_LAYOUT);
        system.park(DUMMY_PARKING_LOT_ID, CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);

        List<Integer> expectedFreeSlotCount = ImmutableList.of(2, 3, 3, 3);
        assertEquals(expectedFreeSlotCount, system.getFreeSlotCountPerFloor(DUMMY_PARKING_LOT_ID, CAR_TYPE));
    }

    @Test
    public void should_increaseBikeFreeSlotCount_when_bikeParked() {

    }

    @Test
    public void should_throwParkingLotFullException_when_carParkRequestedForFullParkingLot() {

    }

    @Test
    public void should_decreaseBikeFreeSlotCount_when_bikeUnparked() {

    }

    @Test
    public void should_removeFreeCarSlot_when_carParked() {

    }

    @Test
    public void should_addFreeCarSlot_when_carUnparked() {

    }

    @Test
    public void should_addFreeBikeSlot_when_bikeUnparked() {

    }

    @Test
    public void should_addOccupiedCarSlot_when_carParked() {

    }

    @Test
    public void should_removeOccupiedCarSlot_when_carUnparked() {

    }

    @Test
    public void should_removeOccupiedBikeSlot_when_bikeUnparked() {

    }

    @Test
    public void should_throwParkingLotNotFound_when_invalidParkingLotIdPassed() {

    }

}
