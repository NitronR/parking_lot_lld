package parking_lot.constants;

import com.example.parking_lot.system.ParkingSlot;
import com.example.parking_lot.system.Vehicle;
import com.google.common.collect.ImmutableList;

import static com.example.parking_lot.commands.constants.VehicleTypeIds.*;

public class TestConstants {
    public static final String DUMMY_PARKING_LOT_ID = "PR1234";
    public static final String CREATE_PARKING_LOT_CMD_TEMPLATE = "create_parking_lot %s %d %d";
    public static final ImmutableList<ParkingSlot> DUMMY_SLOTS_LAYOUT = ImmutableList.<ParkingSlot>builder()
            .add(new ParkingSlot(1, TRUCK_TYPE))
            .add(new ParkingSlot(2, BIKE_TYPE))
            .add(new ParkingSlot(3, BIKE_TYPE))
            .add(new ParkingSlot(4, CAR_TYPE))
            .add(new ParkingSlot(5, CAR_TYPE))
            .add(new ParkingSlot(6, CAR_TYPE))
            .build();
    public static final String DUMMY_REGISTRATION_NUMBER = "122";
    public static final String DUMMY_COLOR_RED = "Red";
    public static final Vehicle DUMMY_CAR = new Vehicle(CAR_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);
    public static final Vehicle DUMMY_BIKE = new Vehicle(BIKE_TYPE, DUMMY_REGISTRATION_NUMBER, DUMMY_COLOR_RED);
}
