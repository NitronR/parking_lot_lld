package com.example.parking_lot.commands;

import com.example.parking_lot.system.ParkingLotSystem;

public class ParkVehicleCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("park_vehicle");
    }

    @Override
    public CommandResult execute(ParkingLotSystem system, String commandLine) {
        System.out.println("Parked vehicle. Ticket ID: <ticket_id>");
        return null;
    }
}
