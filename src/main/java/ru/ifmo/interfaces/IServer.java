package ru.ifmo.interfaces;


import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.HashSet;


public interface IServer {
    HashSet<ICommand> commands = new HashSet<>();
    void init();
    void execute() throws ClientException, ApiException;
}
