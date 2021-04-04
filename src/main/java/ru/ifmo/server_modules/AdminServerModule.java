package ru.ifmo.server_modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IServerModule;


public class AdminServerModule implements IServerModule {

    @Override
    public Message getAnswer(Message message) {
        if(message.getText().equalsIgnoreCase("/admin")){
            message.setCookie("/admin");
        }
        if(message.getCookie().equalsIgnoreCase("/admin")){
            switch (message.getText().toLowerCase()){
                case "/exit":
                    message.setCookie("");
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

    @Override
    public String toString() {
        return "AdminModule";
    }
}
