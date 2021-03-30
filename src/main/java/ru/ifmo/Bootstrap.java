package ru.ifmo;

import ru.ifmo.models.TokenServer;
import ru.ifmo.models.DataModule;
import ru.ifmo.models.interfaces.IServer;
import ru.ifmo.servers.MainServer;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Bootstrap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Running server...");
        try {
            HashSet<IServer> servers = new HashSet<>();
            servers.add(new MainServer());
            ServerManager serverManager = new ServerManager();
            System.out.println("Loading Modules...");
            serverManager.init(servers);
            //Tests
//            System.out.println("Testing Modules...");
            //BD
            System.out.println("Loading DB...");
            for(TokenServer server : DataModule.findAllUsers()){
                //Конфигурация серверов
                //servers.add();
                System.out.println(server.toString());
            }
//            DataModule.saveUser(user);

            //Run
            System.out.println("Servers starts. /\\");
            Executors.newCachedThreadPool().execute(serverManager);
            //Console
            Executors.newCachedThreadPool().execute(() -> {
                while(true){
                    if(scanner.hasNext()){
                        String line = scanner.nextLine();
                        switch (line){
                            case "/exit":
                                System.out.println("Servers down. \\/");
                                serverManager.stop();
                                System.exit(0);
                                break;
                            case "/servers":
                                System.out.println("Серверов: " + servers.size());
                                break;
                            case "/help":
                                System.out.println("/servers - количество серверов\n/exit - завершение работы");
                                break;
                            default:
                                System.out.println("Введите /help");
                                break;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
