package ru.ifmo.models.interfaces;

import ru.ifmo.models.Message;

public interface IModule {
    Message getAnswer(Message message);
}
