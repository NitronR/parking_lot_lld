package parking_lot.commands;

import com.example.parking_lot.ParkingLotSystem;
import com.example.parking_lot.commands.CommandResult;
import com.example.parking_lot.commands.CreateParkingLotCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static parking_lot.constants.TestConstants.CREATE_PARKING_LOT_CMD_TEMPLATE;
import static parking_lot.constants.TestConstants.DUMMY_PARKING_LOT_ID;

@RunWith(MockitoJUnitRunner.class)
public class CreateParkingLotCommandTests {

    private CreateParkingLotCommand createParkingLotCommand;

    @Mock
    private ParkingLotSystem parkingLotSystem;

    @Before
    public void setup() {
        createParkingLotCommand = new CreateParkingLotCommand();
    }

    @Test
    public void should_returnTrue_when_applicableForCalledWithCreateParkingLotCommandLine() {

    }

    @Test
    public void should_createParkingLot_when_validCommandPassed() {
        int numFloors = 2;
        int numSlots = 6;

        String createParkingLotCommandLine = String.format(CREATE_PARKING_LOT_CMD_TEMPLATE, DUMMY_PARKING_LOT_ID,
                numFloors, numSlots);
        CommandResult commandResult = createParkingLotCommand.execute(parkingLotSystem, createParkingLotCommandLine);

        String expectedResult = "Created parking lot with 2 floors and 6 slots per floor";

        assertEquals(expectedResult, commandResult.getMessage());
        verify(parkingLotSystem, times(1))
                .createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, numSlots);
    }

    @Test
    public void should_throwInvalidCommandException_whenInvalidCreateParkingLotCommandLinePassed() {

    }

    @Test
    public void should_createParkingLot_when_validCommandPassedToCreateSingleFloorParking() {
        int numFloors = 1;
        int numSlots = 6;

        String createParkingLotCommandLine = String.format("create_parking_lot %s %d %d", DUMMY_PARKING_LOT_ID,
                numFloors, numSlots);
        CommandResult commandResult = createParkingLotCommand.execute(parkingLotSystem, createParkingLotCommandLine);

        String expectedResult = "Created parking lot with 1 floors and 6 slots per floor";

        assertEquals(expectedResult, commandResult.getMessage());
        verify(parkingLotSystem, times(1))
                .createParkingLot(DUMMY_PARKING_LOT_ID, numFloors, numSlots);
    }

}
