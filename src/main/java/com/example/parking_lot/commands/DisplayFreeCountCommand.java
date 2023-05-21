package com.example.parking_lot.commands;

import com.example.parking_lot.ParkingLotSystem;

public class DisplayFreeCountCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("display free_count");
    }

    @Override
    public void execute(ParkingLotSystem system, String commandLine) {
        System.out.println("No. of free slots for <vehicle_type> on Floor <floor_no>: <no_of_free_slots>");
    }
}
