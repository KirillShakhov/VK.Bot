package ru.ifmo.modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IModule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AdminModule implements IModule{
    HashSet<String> admins = new HashSet<>();
    private Map<Integer, String> cookies = new HashMap<>();

    @Override
    public Message getAnswer(Message message) {
        if(message.getText().equalsIgnoreCase("/admin")){
            message.setLastAnswer("/admin");
        }
        if(message.getLastAnswer().equalsIgnoreCase("/admin")){
            switch (message.getText().toLowerCase()){
                case "/exit":
                    message.setLastAnswer("");
                    message.setText("Выход из админ панели");
                    return message;
                case "/add_server":
                    message.setText("В разработке... Введите /exit");
                    return message;
                default:
                    message.setText("Команды: /add_server, /exit");
                    return message;
            }
        }
        return null;
    }

    @Override
    public String getPerformance() {
        return "";
    }

    @Override
    public void setPerformance(String s) {
    }
}
