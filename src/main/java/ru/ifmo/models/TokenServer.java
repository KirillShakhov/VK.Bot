package ru.ifmo.models;

import ru.ifmo.Bootstrap;
import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.models.interfaces.IRequestModule;
import ru.ifmo.modules.VKModule;
import ru.ifmo.server.Server;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Entity
@Table (name = "servers")
public class TokenServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String token;
    private String group_id;
    private String type;
    @ElementCollection
    private List<String> modules;
    @ElementCollection
    private List<String> owners;

    public TokenServer() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Server initServer(){
        try {
            Server server = null;
            if (type.equals("bsrv")) {
                IRequestModule requestModule = new VKModule(token, group_id);
                server = new Server(requestModule);
                HashSet<IModule> m = new HashSet<>();
                for (String s : modules) {
                    for (IModule module : Bootstrap.modules) {
                        if (module.toString().equals(s)) {
                            m.add(module);
                        }
                    }
                }
                server.init(m);
            }
            return server;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "models.User{" +
                "id=" + id +
                ", name='" + name +
                ", owners='" + owners +
                '}';
    }
}
