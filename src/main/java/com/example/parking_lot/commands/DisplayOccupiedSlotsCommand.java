package com.example.parking_lot.commands;

import com.example.parking_lot.system.ParkingLotSystem;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.parking_lot.ParkingLotSystemRunner.PARKING_LOT_ID_ARG;
import static java.lang.String.format;

public class DisplayOccupiedSlotsCommand implements ParkingLotCommand {
    @Override
    public boolean applicableFor(String commandLine) {
        return commandLine.startsWith("display occupied_slots");
    }

    @Override
    public CommandResult execute(ParkingLotSystem system, String commandLine, Map<String, Optional<String>> extraArgs) {
        // TODO parking lot arg exists check
        // TODO args validation
        String[] commandAndArgs = commandLine.split(" ");

        String parkingLotId = extraArgs.get(PARKING_LOT_ID_ARG).get();
        String vehicleTypeId = commandAndArgs[2].toLowerCase();

        List<List<Integer>> occupiedSlotsPerFloor = system.getOccupiedSlotsPerFloor(parkingLotId, vehicleTypeId);

        StringBuilder messageBuilder = new StringBuilder();

        IntStream.rangeClosed(1, occupiedSlotsPerFloor.size())
                .forEach(floorNumber -> {
                    String occupiedSlots = occupiedSlotsPerFloor.get(floorNumber - 1).stream()
                            .map(Objects::toString)
                            .collect(Collectors.joining(","));
                    messageBuilder.append(format("Occupied slots for %s on Floor %d: %s\n",
                            vehicleTypeId.toUpperCase(), floorNumber, occupiedSlots));
                });

        return new GenericCommandResult(messageBuilder.toString());
    }
}
