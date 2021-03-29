package ru.ifmo.servers;

import ru.ifmo.models.DontGetMessage;
import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.models.interfaces.IRequestModule;
import ru.ifmo.models.interfaces.IServer;
import ru.ifmo.modules.CookieModule;

import java.util.HashSet;
import java.util.concurrent.Executors;

public abstract class Server implements IServer {
    HashSet<IModule> modules = new HashSet<>();
    CookieModule cookies = new CookieModule();
    IRequestModule requestModule;

    @Override
    public void init(HashSet<IModule> modules) {
        this.modules.addAll(modules);
    }

    @Override
    public void execute() throws DontGetMessage {
        Message message = requestModule.getMessage();
        if(message != null) {
            Executors.newCachedThreadPool().execute(() -> {
                requestModule.sendMessage(getAnswer(message));
            });
        }
    }
    Message getAnswer(Message message){
        Message answer = null;
        for(IModule module : modules){
            answer = module.getAnswer(message, cookies);
        }
        cookies.push(message.getPeerId(), message.getText());
        return answer != null ? answer : new Message("Неизвестная команда, введите /help", message.getPeerId());
    }
}
