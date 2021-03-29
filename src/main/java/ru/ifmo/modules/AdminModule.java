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
            switch (message.getText().toLowerCase()){
                case "/exit":
                    cookies.removeLast(message.getPeerId());
                    return new Message("Выход из админ панели", message.getPeerId());
                case "/add_server":
                    return new Message("В разработке... Введите /exit", message.getPeerId());
                default:
                    return new Message("Команды: /add_server, /exit", message.getPeerId());
            }
        }
        return null;
    }
}
