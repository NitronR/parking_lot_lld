package com.example.parking_lot.commands;

import com.example.parking_lot.system.ParkingLotSystem;

import java.util.Map;
import java.util.Optional;

import static com.example.parking_lot.ParkingLotSystemRunner.PARKING_LOT_ID_ARG;
import static java.lang.String.format;

public class ParkVehicleCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("park_vehicle");
    }

    @Override
    public CommandResult execute(ParkingLotSystem system, String commandLine, Map<String, Optional<String>> extraArgs) {
        // TODO parking lot arg exists check
        // TODO args validation
        String[] commandAndArgs = commandLine.split(" ");

        String parkingLotId = extraArgs.get(PARKING_LOT_ID_ARG).get();
        String vehicleTypeId = commandAndArgs[1].toLowerCase();
        String registrationNumber = commandAndArgs[2];
        String color = commandAndArgs[3];

        Optional<String> ticketIdOptional = system.park(parkingLotId, vehicleTypeId, registrationNumber, color);

        String resultMessage = ticketIdOptional.map(ticketId -> format("Parked vehicle. Ticket ID: %s\n", ticketId))
                .orElse("Parking Lot Full\n");
        return new GenericCommandResult(resultMessage);
    }
}
