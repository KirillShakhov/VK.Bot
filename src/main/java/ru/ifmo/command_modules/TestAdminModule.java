package ru.ifmo.command_modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.TokenServer;
import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.modules.DataBaseModule;
import ru.ifmo.modules.ServerManagerModule;

public class TestAdminModule implements IModule {
    @Override
    public Message getAnswer(Message message) {
        if(message.getText().startsWith("/add")){
            try {
                String[] arr = message.getText().split("#");
                TokenServer server = new TokenServer(arr[1], arr[2], arr[3], Integer.toString(message.getPeerId()));
                BaseCommandModule b = new BaseCommandModule();
                b.addCommand(mes -> mes.equalsIgnoreCase("привет")?"Привет!":null);
                server.addModule(b);
                DataBaseModule.save(server);
                ServerManagerModule.addServer(server);
                message.setText("Сервер добавлен!");
                return message;
            }catch (Exception e){
                message.setText("Неправильно введены данные");
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
        return "TestAdminModule";
    }
}
