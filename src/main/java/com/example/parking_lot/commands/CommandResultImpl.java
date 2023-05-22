package com.example.parking_lot.commands;

public class CommandResultImpl implements CommandResult {

    private final String message;

    public CommandResultImpl(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
