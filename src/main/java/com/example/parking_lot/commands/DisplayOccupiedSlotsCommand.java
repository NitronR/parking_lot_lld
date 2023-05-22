package com.example.parking_lot.commands;

import com.example.parking_lot.ParkingLotSystem;

public class DisplayOccupiedSlotsCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("display occupied_slots");
    }

    @Override
    public CommandResult execute(ParkingLotSystem system, String commandLine) {
        System.out.println("Occupied slots for <vehicle_type> on Floor <floor_no>: <comma_separated_values_of_slot_nos>");
        return null;
    }
}
