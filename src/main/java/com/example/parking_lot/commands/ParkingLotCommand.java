package com.example.parking_lot.commands;


import com.example.parking_lot.ParkingLotSystem;

/**
 * The ParkingLotCommand interface represents a command that can be executed on a ParkingLotSystem.
 */
public interface ParkingLotCommand {

    /**
     * Checks if the command is applicable for the given command line.
     *
     * @param commandLine the command line string
     * @return true if the command is applicable, false otherwise
     */
    boolean applicableFor(String commandLine);

    /**
     * Executes the command on the specified ParkingLotSystem.
     *
     * @param system      the ParkingLotSystem on which the command will be executed
     * @param commandLine the command line string
     */
    CommandResult execute(ParkingLotSystem system, String commandLine);
}
