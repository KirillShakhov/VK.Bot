package ru.ifmo;

import ru.ifmo.server.Server;

import java.util.HashSet;

public class ServerManager implements Runnable {
    private HashSet<Server> servers;
    public void init(HashSet<Server> servers) {
        int i = 1;
        for(Server server : servers){
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
            for(Server server : servers){
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
