package com.example.parking_lot.commands;

import com.example.parking_lot.ParkingLotSystem;

public class ParkVehicleCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("park_vehicle");
    }

    @Override
    public void execute(ParkingLotSystem system, String commandLine) {
        System.out.println("Parked vehicle. Ticket ID: <ticket_id>");
    }
}