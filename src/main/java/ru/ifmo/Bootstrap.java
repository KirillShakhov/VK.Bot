package ru.ifmo;

import ru.ifmo.models.interfaces.IServer;
import ru.ifmo.servers.MainServer;

import java.util.HashSet;
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
            System.out.println("Loading Module...");
            serverManager.init(servers);
            System.out.println("Servers starts. \\/");
            //Run
            Executors.newCachedThreadPool().execute(serverManager);
            while(true){
                if(scanner.hasNext()){
                    String line = scanner.nextLine();
                    switch (line){
                        case "exit":
                            System.out.println("Завершение работы");
                            serverManager.stop();
                            System.exit(0);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
