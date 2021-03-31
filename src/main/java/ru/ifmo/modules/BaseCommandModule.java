package ru.ifmo.modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.ICommand;
import ru.ifmo.models.interfaces.IModule;
import java.util.HashSet;

public class BaseCommandModule implements IModule {
    HashSet<ICommand> commands = new HashSet<>();

    @Override
    public Message getAnswer(Message message) {
        for(ICommand command : commands){
            if(command.execute(message.getText()) != null){
                message.setText(command.execute(message.getText()));
                return message;
            }
        }
        return null;
    }

    public void addCommand(ICommand command){
        this.commands.add(command);
    }

    @Override
    public String toString() {
        return "base_command";
    }
}
