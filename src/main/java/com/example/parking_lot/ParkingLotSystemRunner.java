package com.example.parking_lot;

import com.example.parking_lot.commands.*;
import com.example.parking_lot.system.FirstFreeParkingPicker;
import com.example.parking_lot.system.ParkingLotSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ParkingLotSystemRunner {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Needs a single argument denoting the input file path.");
            return;
        }

        ParkingLotSystem system = new ParkingLotSystem(new FirstFreeParkingPicker());
        List<ParkingLotCommand> commands = Arrays.asList(
                new CreateParkingLotCommand(),
                new ParkVehicleCommand(),
                new UnparkVehicleCommand(),
                new DisplayFreeCountCommand(),
                new DisplayFreeSlotsCommand(),
                new DisplayOccupiedSlotsCommand()
        );

        String inputFilePath = args[0];
        Scanner scanner = new Scanner(new FileInputStream(inputFilePath));

        while (scanner.hasNextLine()) {
            String commandLine = scanner.nextLine();

            for (ParkingLotCommand command: commands) {
                if (command.applicableFor(commandLine)) {
                    command.execute(system, commandLine);
                }
            }
        }
    }

}
