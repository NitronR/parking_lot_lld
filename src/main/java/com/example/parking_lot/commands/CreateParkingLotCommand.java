package com.example.parking_lot.commands;

import com.example.parking_lot.ParkingLotSystem;

public class CreateParkingLotCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("create_parking_lot");
    }

    @Override
    public void execute(ParkingLotSystem system, String commandLine) {
        System.out.println("Created parking lot with <no_of_floors> floors and <no_of_slots_per_floor> slots per floor");
    }
}
