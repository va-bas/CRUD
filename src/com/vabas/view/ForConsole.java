package com.vabas.view;

public enum ForConsole {
    BORDER("====================================================="),
    ERROR_INPUT("You are wrong! Its not number from menu");


    private final String message;

    ForConsole(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
