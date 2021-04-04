package ru.ifmo.server_modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.ICommand;
import ru.ifmo.models.interfaces.IServerModule;
import java.util.HashSet;

public class BaseCommandServerModule implements IServerModule {
    HashSet<ICommand> commands = new HashSet<>();

    @Override
    public Message getAnswer(Message message) {
        //Администрирование
        for(ICommand command : commands){
            if(command.execute(message.getText()) != null){
                message.setText(command.execute(message.getText()));
                return message;
            }
        }
        return null;
    }

    @Override
    public String getPerformance() {
        return null;
    }

    @Override
    public void setPerformance(String s) {
    }

    public void addCommand(ICommand command){
        this.commands.add(command);
    }

    @Override
    public String toString() {
        return "BaseCommandModule";
    }
}
