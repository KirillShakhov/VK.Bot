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
            if(command.execute(message) != null){
                Message answer = command.execute(message);
                answer.setPeerId(message.getPeerId());
                return answer;
            }
        }
        return null;
    }

    void addCommand(ICommand command){
        this.commands.add(command);
    }
}
