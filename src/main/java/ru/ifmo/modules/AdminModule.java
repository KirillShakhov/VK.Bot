package ru.ifmo.modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IModule;

import java.util.HashSet;

public class AdminModule implements IModule{
    HashSet<String> admins = new HashSet<>();

    @Override
    public Message getAnswer(Message message, CookieModule cookies) {
        if(message.getText().equalsIgnoreCase("/admin")){
            cookies.push(message.getPeerId(), "/admin");
        }
        if(cookies.getTop(message.getPeerId()).equalsIgnoreCase("/admin")){
            return new Message("Команды: /add_server", message.getPeerId());
        }
        return null;
    }
}
