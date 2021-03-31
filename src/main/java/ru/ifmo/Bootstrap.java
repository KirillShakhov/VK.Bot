package ru.ifmo;

import lombok.Getter;
import ru.ifmo.models.TokenServer;
import ru.ifmo.models.TokenServerDao;
import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.modules.BaseCommandModule;
import ru.ifmo.modules.ConsoleModule;
import ru.ifmo.modules.TestModule;

import java.util.HashSet;
import java.util.concurrent.Executors;

public class Bootstrap {
    @Getter
    public static HashSet<IModule> modules = new HashSet<>();
    static HashSet<Runnable> otherModules = new HashSet<>();
    @Getter
    static ServerManager serverManager = new ServerManager();

    public static void main(String[] args) {
        //Config
        add_otherModule(new ConsoleModule());
        add_otherModule(new TestModule());
        //распределительный модуль

        //Service
        try {
//            TokenServer server1 = new TokenServer("NEXT", "940b6044164b91beec0001825b85784168ffba9ba115d40bc3c548f9c76de698c21f33f2d57e783f27463", "144561009");
//            TokenServer server2 = new TokenServer("QBot", "f5c685e72504f75ae5d13ffba306a9e7bb9a2382dbe0492a525e7381555a63c03848d2e4239eadb8f51ec", "150386938");
//
//            BaseCommandModule b1 = new BaseCommandModule();
//            b1.addCommand(message -> message.equals("Привет") ? "Hi" : null);
//
//
//            server1.addModule(b1);
//            server2.addModule(b1);
//            serverManager.addServer(server1);
//            serverManager.addServer(server2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //

        //Start
        System.out.println("Running server...");
        System.out.println("Loading Modules...");
        loading_database();
        start();
        loading_otherModules();
    }

    public static void add_otherModule(Runnable module){
        otherModules.add(module);
    }

    public static void loading_database() {
        //BD
        System.out.println("Loading DB...");
        for(TokenServer server : TokenServerDao.findAll()){
            server.init();
            System.out.println("Initialized Module("+server.getId()+"/"+TokenServerDao.findAll().size()+")");
            serverManager.addServer(server);
        }
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
