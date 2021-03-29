package ru.ifmo.models.interfaces;

import ru.ifmo.models.Message;
import ru.ifmo.models.DontGetMessage;

public interface IRequestModule {
    Message getMessage() throws DontGetMessage;
    void sendMessage(Message message);
}
