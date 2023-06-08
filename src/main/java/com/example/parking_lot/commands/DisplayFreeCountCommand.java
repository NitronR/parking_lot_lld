package com.example.parking_lot.commands;

import com.example.parking_lot.system.ParkingLotSystem;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.example.parking_lot.ParkingLotSystemRunner.PARKING_LOT_ID_ARG;
import static java.lang.String.format;

public class DisplayFreeCountCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("display free_count");
    }

    @Override
    public CommandResult execute(ParkingLotSystem system, String commandLine,
                                 Map<String, Optional<String>> extraArgs) {
        // TODO parking lot arg exists check
        // TODO args validation
        String[] commandAndArgs = commandLine.split(" ");

        String parkingLotId = extraArgs.get(PARKING_LOT_ID_ARG).get();
        String vehicleTypeId = commandAndArgs[2].toLowerCase();

        List<Integer> freeSlotCountPerFloor = system.getFreeSlotCountPerFloor(parkingLotId, vehicleTypeId);

        StringBuilder messageBuilder = new StringBuilder();

        IntStream.rangeClosed(1, freeSlotCountPerFloor.size())
                .forEach(floorNumber -> messageBuilder.append(format("No. of free slots for %s on Floor %d: %d\n",
                        vehicleTypeId.toUpperCase(), floorNumber, freeSlotCountPerFloor.get(floorNumber - 1))));

        return new GenericCommandResult(messageBuilder.toString());
    }
}
