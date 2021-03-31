package ru.ifmo.modules;

import ru.ifmo.Stages;
import ru.ifmo.models.TokenServer;

import java.util.Scanner;

public class ConsoleModule implements Runnable{
    Scanner scanner = new Scanner(System.in);
    @Override
    public void run() {
        while(true){
            if(scanner.hasNext()){
                String line = scanner.nextLine();
                switch (line){
                    case "/exit":
                        System.out.println("Servers down. \\/");
                        System.exit(0);
                        break;
                    case "/servers":
                        System.out.println("Серверов: " + Stages.getServers().size());
                        for(TokenServer server : Stages.getServers()){
                            System.out.println(server.toString());
                        }
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
    }
}
