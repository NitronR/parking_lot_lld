package com.example.parking_lot;

import com.example.parking_lot.commands.*;
import com.example.parking_lot.system.FirstFreeParkingPicker;
import com.example.parking_lot.system.ParkingLotSystem;
import com.google.common.collect.ImmutableMap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class ParkingLotSystemRunner {

    public static final String PARKING_LOT_ID_ARG = "parkingLotId";

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
        Map<String, Optional<String>> extraArgs = ImmutableMap.of("parkingLotId", Optional.empty());

        while (scanner.hasNextLine()) {
            String commandLine = scanner.nextLine();

            for (ParkingLotCommand command : commands) {
                if (command.applicableFor(commandLine)) {
                    CommandResult commandResult = command.execute(system, commandLine, extraArgs);

                    if (commandResult instanceof CreateParkingLotCommandResult) {
                        // TODO parking lot exists check
                        extraArgs = ImmutableMap.of(PARKING_LOT_ID_ARG,
                                Optional.of(((CreateParkingLotCommandResult) commandResult).getParkingLotId()));
                    }

                    System.out.print(commandResult.getMessage());
                }
            }
        }
    }

}
