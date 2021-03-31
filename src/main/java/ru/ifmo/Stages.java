package ru.ifmo;

import ru.ifmo.models.DataModule;
import ru.ifmo.models.TokenServer;
import ru.ifmo.server.Server;

import java.util.HashSet;
import java.util.concurrent.Executors;

public class Stages {
    static HashSet<Runnable> otherModules = new HashSet<>();
    static HashSet<Server> servers = new HashSet<>();
    static ServerManager serverManager = new ServerManager();


    public static void add_otherModule(Runnable module){
        otherModules.add(module);
    }

    public static void add_server(Server server){
        servers.add(server);
    }
    public static void testing_modules() {
        //Tests
        System.out.println("Testing Modules...");
    }


    public static void loading_modules() {
        //Loading Modules
        System.out.println("Loading Modules...");
        serverManager.init(servers);

    }

    public static void loading_database() {
        //BD
        System.out.println("Loading DB...");
        for(TokenServer server : DataModule.findAllUsers()){
            //Конфигурация серверов
            //servers.add();
            System.out.println(server.toString());
        }
//            DataModule.saveUser(user);
    }

    public static void start() {
        //Run
        System.out.println("Servers starts. /\\");
        Executors.newCachedThreadPool().execute(serverManager);
    }

    public static void loading_otherModules() {
        for(Runnable module : otherModules){
            Executors.newCachedThreadPool().execute(module);
        }
    }
}
