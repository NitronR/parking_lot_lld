package com.example.parking_lot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericCommandResult implements CommandResult {
    private final String message;
}
