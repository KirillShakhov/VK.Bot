package ru.ifmo.models;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.Bootstrap;
import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.models.interfaces.IRequestModule;
import ru.ifmo.modules.VKModule;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.Executors;

@Entity
@Table (name = "servers")
public class TokenServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Getter @Setter
    private String name;
    @Setter
    private String token;
    @Setter
    private String group_id;
    @ElementCollection
    private Map<String, String> modules_str = new HashMap<String, String>();
    @ElementCollection
    @Getter
    private List<String> owners = new ArrayList<>();
    @Transient
    HashSet<IModule> modules = new HashSet<>();
    @Transient
    private Map<Integer, String> cookies = new HashMap<>();
    @Transient
    IRequestModule requestModule;

    public TokenServer(String name, String token, String group_id) {
        this.name = name;
        this.token = token;
        this.group_id = group_id;
        init();
    }

    public void init(){
        try {
            requestModule = new VKModule(token, group_id);
            for (Map.Entry<String, String> entry : modules_str.entrySet()) {
                for(IModule module : Bootstrap.getModules()){
                    if(module.toString().equals(entry.getKey())){
                        module.setPerformance(entry.getValue());
                        this.modules.add(module);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Сервер не удалось инициализировать. ID: " + id);
        }
    }

    public void execute() throws DontGetMessage {
        Message message = requestModule.getMessage();
        if(message != null) {
            if (cookies.get(message.getPeerId()) != null) {
                message.setLastAnswer(cookies.get(message.getPeerId()));
            }
            Executors.newCachedThreadPool().execute(() -> requestModule.sendMessage(getAnswer(message)));
            cookies.put(message.getPeerId(), message.getLastAnswer());
        }
    }

    Message getAnswer(Message message){
        Message answer = null;
        for(IModule module : modules){
            answer = module.getAnswer(message);
        }
        if (answer != null) return answer;
        else{
            message.setText("Неизвестная команда, введите /help");
            return message;
        }
    }

    public void addModule(IModule module){
        this.modules.add(module);
    }

    @Override
    public String toString() {
        return "TokenServer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", group_id='" + group_id + '\'' +
                ", modules_str=" + modules_str +
                ", owners=" + owners +
                ", modules=" + modules +
                ", cookies=" + cookies +
                ", requestModule=" + requestModule +
                '}';
    }
}
