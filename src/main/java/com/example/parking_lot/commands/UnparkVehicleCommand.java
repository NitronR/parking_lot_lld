package com.example.parking_lot.commands;

import com.example.parking_lot.ParkingLotSystem;

public class UnparkVehicleCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("unpark_vehicle");
    }

    @Override
    public void execute(ParkingLotSystem system, String commandLine) {
        System.out.println("Unparked vehicle with Registration Number: <reg_no> and Color: <color>");
    }
}
