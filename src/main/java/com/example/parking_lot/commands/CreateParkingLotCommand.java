package com.example.parking_lot.commands;

import com.example.parking_lot.ParkingLotSystem;

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

        parkingLotSystem.createParkingLot(parkingLotId, numFloors, numSlots);

        String resultMessage = String.format(RESULT_MSG_TEMPLATE, numFloors, numSlots);
        return new CommandResultImpl(resultMessage);
    }
}
