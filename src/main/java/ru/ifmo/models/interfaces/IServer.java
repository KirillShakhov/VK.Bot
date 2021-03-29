package ru.ifmo.models.interfaces;


import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.ifmo.models.DontGetMessage;

import java.util.HashSet;


public interface IServer {
    HashSet<ICommand> commands = new HashSet<>();
    void init(HashSet<IModule> modules);
    void execute() throws ClientException, ApiException, DontGetMessage;
}
