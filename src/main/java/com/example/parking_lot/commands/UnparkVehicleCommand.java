package com.example.parking_lot.commands;

import com.example.parking_lot.exception.InvalidTicketException;
import com.example.parking_lot.system.ParkingLotSystem;
import com.example.parking_lot.system.Vehicle;

import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

public class UnparkVehicleCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("unpark_vehicle");
    }

    @Override
    public CommandResult execute(ParkingLotSystem system, String commandLine, Map<String, Optional<String>> extraArgs) {
        // TODO parking lot arg exists check
        // TODO args validation
        String[] commandAndArgs = commandLine.split(" ");

        String ticketId = commandAndArgs[1];

        try {
            Vehicle vehicle = system.unpark(ticketId);
            return new GenericCommandResult(format("Unparked vehicle with Registration Number: %s and Color: %s\n",
                    vehicle.getRegistrationNumber(), vehicle.getColor()));
        } catch (InvalidTicketException invalidTicketException) {
            return new GenericCommandResult("Invalid Ticket\n");
        }
    }
}
