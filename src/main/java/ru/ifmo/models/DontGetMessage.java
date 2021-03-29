package ru.ifmo.models;

public class DontGetMessage extends Exception {
    public DontGetMessage(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Не удается получить сообщение";
    }

}
