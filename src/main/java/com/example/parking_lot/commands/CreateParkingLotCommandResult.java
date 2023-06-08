package com.example.parking_lot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateParkingLotCommandResult implements CommandResult {
    private final String message;
    private final String parkingLotId;
}
