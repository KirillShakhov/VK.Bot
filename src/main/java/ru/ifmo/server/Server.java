package ru.ifmo.server;


import ru.ifmo.models.DontGetMessage;
import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.models.interfaces.IRequestModule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;

public class Server {
    String name;
    HashSet<IModule> modules = new HashSet<>();
    private Map<Integer, String> cookies = new HashMap<>();
    IRequestModule requestModule;

    public Server(IRequestModule requestModule) {
        this.requestModule = requestModule;
    }

    public void init(HashSet<IModule> modules) {
        this.modules.addAll(modules);
    }

    public void execute() throws DontGetMessage {
        Message message = requestModule.getMessage();
        if(message != null) {
            if (cookies.get(message.getPeerId()) != null) {
                message.setLastAnswer(cookies.get(message.getPeerId()));
            }
            Executors.newCachedThreadPool().execute(() -> requestModule.sendMessage(getAnswer(message)));
            cookies.put(message.getPeerId(), message.getLastAnswer());
        }
    }
    Message getAnswer(Message message){
        Message answer = null;
        for(IModule module : modules){
            answer = module.getAnswer(message);
        }
        if (answer != null) return answer;
        else{
            message.setText("Неизвестная команда, введите /help");
            return message;
        }
    }

    public HashSet<IModule> getModules() {
        return modules;
    }

    public void setModules(HashSet<IModule> modules) {
        this.modules = modules;
    }

    public void addModule(IModule module){
        this.modules.add(module);
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", modules=" + modules +
                ", cookies=" + cookies +
                ", requestModule=" + requestModule +
                '}';
    }
}
