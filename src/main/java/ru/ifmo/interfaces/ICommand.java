package ru.ifmo.interfaces;

import com.vk.api.sdk.objects.messages.Message;
import ru.ifmo.models.ResponseMessage;

public interface ICommand {
    ResponseMessage execute(Message message);
}
