package ru.ifmo;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.models.TokenServer;

import java.util.HashSet;

public class ServerManager implements Runnable {
    @Setter @Getter
    private HashSet<TokenServer> servers = new HashSet<>();

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

    public void addServer(TokenServer server){
        this.servers.add(server);
    }
}
