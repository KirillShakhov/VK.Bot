package ru.ifmo.server_modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IServerModule;

import java.util.Locale;


public class AdminServerModule implements IServerModule {

    @Override
    public Message getAnswer(Message message) {
        if(message.getText().equalsIgnoreCase("/admin")){
            message.setCookie("/admin");
            message.setText("Команды: /add_server, /exit");
            return message;
        }
        if(message.getCookie().equalsIgnoreCase("/admin")){
            if ("/exit".equalsIgnoreCase(message.getText())) {
                message.setCookie("");
                message.setText("Выход из админ панели");
                return message;
            } else if ("/add_server".equalsIgnoreCase(message.getText())) {
                message.setText("В разработке... Введите /exit");
                return message;
            }
            else {
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
