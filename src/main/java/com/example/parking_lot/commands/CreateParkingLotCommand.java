package com.example.parking_lot.commands;

import com.example.parking_lot.system.ParkingLotSystem;
import com.example.parking_lot.system.ParkingSlot;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static com.example.parking_lot.commands.constants.VehicleTypeIds.*;

public class CreateParkingLotCommand implements ParkingLotCommand {

    public static final String RESULT_MSG_TEMPLATE = "Created parking lot with %d floors "
            + "and %d slots per floor";

    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("create_parking_lot");
    }

    @Override
    public CommandResult execute(ParkingLotSystem parkingLotSystem, String commandLine) {
        String[] commandAndArgs = commandLine.split(" ");

        String parkingLotId = commandAndArgs[1];
        int numFloors = Integer.parseInt(commandAndArgs[2]);
        int numSlots = Integer.parseInt(commandAndArgs[3]);

        parkingLotSystem.createParkingLot(parkingLotId, numFloors, getDefaultParkingSlots(numSlots));

        String resultMessage = String.format(RESULT_MSG_TEMPLATE, numFloors, numSlots);
        return new CommandResultImpl(resultMessage);
    }

    private List<ParkingSlot> getDefaultParkingSlots(int numSlots) {
        List<ParkingSlot> initialSlots = ImmutableList.<ParkingSlot>builder()
                .add(new ParkingSlot(1, TRUCK_TYPE))
                .add(new ParkingSlot(2, BIKE_TYPE))
                .add(new ParkingSlot(3, BIKE_TYPE))
                .build();

        List<ParkingSlot> parkingSlots = new ArrayList<>();

        for (int slotNumber = 1; slotNumber <= numSlots; slotNumber++) {
            if (slotNumber <= initialSlots.size()) {
                parkingSlots.add(initialSlots.get(slotNumber - 1));
                continue;
            }

            parkingSlots.add(new ParkingSlot(slotNumber, CAR_TYPE));
        }

        return parkingSlots;
    }
}
