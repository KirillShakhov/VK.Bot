package ru.ifmo.modules;

import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.ICommand;
import ru.ifmo.models.interfaces.IModule;

import java.util.HashSet;

public class BaseCommandModule implements IModule {
    HashSet<ICommand> commands = new HashSet<>();

    @Override
    public Message getAnswer(Message message, CookieModule cookies) {
        for(ICommand command : commands){
            if(command.execute(message.getText()) != null){
                String answer = command.execute(message.getText());
                return new Message(answer, message.getPeerId());
            }
        }
        return null;
    }

    public void addCommand(ICommand command){
        this.commands.add(command);
    }
}
