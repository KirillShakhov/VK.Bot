package ru.ifmo.server_modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IServerModule;


public class AdminServerModule implements IServerModule {
    int i = 0;
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
                i++;
                return message;
            } else if ("/i".equalsIgnoreCase(message.getText())) {
                message.setText("i: " + i++);
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
        return String.valueOf(i);
    }

    @Override
    public void setPerformance(String s) {
        try {
            i = Integer.parseInt(s);
        }catch (Exception ignored){ }
    }

    @Override
    public String toString() {
        return "AdminModule";
    }
}
