package ru.ifmo.modules;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.command_modules.BaseCommandModule;
import ru.ifmo.command_modules.TestAdminModule;
import ru.ifmo.models.TokenServer;
import ru.ifmo.models.interfaces.IModule;

import java.util.HashSet;

public class ServerManagerModule implements Runnable {
    @Setter @Getter
    private static HashSet<TokenServer> servers = new HashSet<>();
    @Getter
    public static HashSet<IModule> modules = new HashSet<>();

    public static void restart() {
        servers = new HashSet<>();
        System.out.println("Restart server...");
        System.out.println("Loading Modules...");
        BaseCommandModule b = new BaseCommandModule();
        b.addCommand(message -> message.equalsIgnoreCase("привет")?"Привет!":null);
        modules.add(b);
        modules.add(new TestAdminModule());
        //
        loading_database();
        System.out.println("Servers starts. /\\");
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (TokenServer server : servers) {
                try {
                    server.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    servers.remove(server);
                    System.out.println("Сервер вышел из строя");
                }
            }
        }
    }


    public void run() {
        System.out.println("Running server...");
        System.out.println("Loading Modules...");
        BaseCommandModule b = new BaseCommandModule();
        b.addCommand(message -> message.equalsIgnoreCase("привет")?"Привет!":null);
        modules.add(b);
        modules.add(new TestAdminModule());
        //

        loading_database();
        System.out.println("Servers starts. /\\");

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (TokenServer server : servers) {
                try {
                    server.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    servers.remove(server);
                    System.out.println("Сервер вышел из строя");
                }
            }
        }
    }

    public static void loading_database() {
        //BD
        System.out.println("Loading DB...");
        int size = DataBaseModule.findAll().size();
        for(TokenServer server : DataBaseModule.findAll()){
            server.init();
            System.out.println("Initialized Module("+server.getId()+"/"+ size +")");
            addServer(server);
        }
    }

    public static void addServer(TokenServer server){
        servers.add(server);
    }
}
