package ru.ifmo.models.interfaces;

import ru.ifmo.models.Message;

public interface IServerModule {
    Message getAnswer(Message message);
    String getPerformance();
    void setPerformance(String s);
    String toString();
}
