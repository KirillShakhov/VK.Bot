package ru.ifmo.models.interfaces;


import ru.ifmo.models.Message;

public interface ICommand {
    Message execute(Message message);
}
