package ru.ifmo;

import ru.ifmo.models.TokenServer;

import java.util.HashSet;

public class ServerManager implements Runnable {
    private HashSet<TokenServer> servers;
    public void init(HashSet<TokenServer> servers) {
        int i = 1;
        for(TokenServer server : servers){
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
            for(TokenServer server : servers){
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
