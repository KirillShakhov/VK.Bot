package ru.ifmo.models.interfaces;

import ru.ifmo.models.Message;
import ru.ifmo.modules.CookieModule;

public interface IModule {
    Message getAnswer(Message message, CookieModule cookies);
}
