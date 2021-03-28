package ru.ifmo;

import ru.ifmo.interfaces.IServer;

import java.util.HashSet;

public class ServerManager implements Runnable {
    private HashSet<IServer> servers;
    public void init(HashSet<IServer> servers) {
        for(IServer server : servers){
            server.init();
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
