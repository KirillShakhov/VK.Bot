package ru.ifmo;

import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.models.interfaces.IServer;
import ru.ifmo.modules.BaseCommandModule;
import java.util.HashSet;

public class ServerManager implements Runnable {
    private HashSet<IServer> servers;
    public void init(HashSet<IServer> servers) {
        HashSet<IModule> m = new HashSet<>();
        m.add(new BaseCommandModule());
        int i = 1;
        for(IServer server : servers){
            server.init(m);
            System.out.println("Loaded module("+(i++)+"/"+servers.size()+")");
        }
        this.servers = servers;
    }

    public void run() {
        while (true){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(IServer server : servers){
                try {
                    server.execute();
                }catch (Exception e){
                    e.printStackTrace();
                    servers.remove(server);
                    System.out.println("Сервер вышел из строя");
                }
            }
        }
    }

    public void stop() {
    }
}
