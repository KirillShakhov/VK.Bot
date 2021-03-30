package ru.ifmo.servers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.ifmo.modules.AdminModule;
import ru.ifmo.modules.BaseCommandModule;
import ru.ifmo.modules.VKModule;
import ru.ifmo.models.interfaces.IServer;


public class MainServer extends Server implements IServer {
    public MainServer() throws ClientException, ApiException {
        String token = "940b6044164b91beec0001825b85784168ffba9ba115d40bc3c548f9c76de698c21f33f2d57e783f27463";
        String group_id = "144561009";
        requestModule = new VKModule(token, group_id);
        modules.add(new AdminModule());
        BaseCommandModule baseCommandModule = new BaseCommandModule();
        baseCommandModule.addCommand(message -> message.equalsIgnoreCase("/help") ? "/admin - панель админа" : null);
        modules.add(baseCommandModule);
    }
}
