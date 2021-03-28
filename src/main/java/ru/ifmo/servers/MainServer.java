package ru.ifmo.servers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import ru.ifmo.models.ResponseMessage;
import ru.ifmo.modules.VKModule;
import ru.ifmo.interfaces.ICommand;
import ru.ifmo.interfaces.IServer;

import java.util.concurrent.Executors;
import java.util.HashSet;


public class MainServer extends Server implements IServer {
    HashSet<ICommand> commands = new HashSet<>();
    VKModule vk_module;
    @Override
    public void init() {
        commands.add(message -> message.getBody().equals("Привет") ? new ResponseMessage("Hi") : null);
    }

    MainServer() throws ClientException, ApiException {
        String token = "940b6044164b91beec0001825b85784168ffba9ba115d40bc3c548f9c76de698c21f33f2d57e783f27463";
        String group_id = "144561009";
        vk_module = new VKModule(token, group_id);
    }

    @Override
    public void execute() throws ClientException, ApiException {
        Message message = vk_module.getMessage();
        if(message != null) {
            Executors.newCachedThreadPool().execute(() -> {
                ResponseMessage rm = getAnswer(message);
                rm.setPeerId(message.getUserId());
                vk_module.sendMessage(rm);
            });
        }
    }
    ResponseMessage getAnswer(Message message){
        System.out.println(message.toString());
        for(ICommand command : commands){
            if(command.execute(message) != null){
                return command.execute(message);
            }
        }
        return new ResponseMessage("Неизвестная команда, введите /help");
    }
}
