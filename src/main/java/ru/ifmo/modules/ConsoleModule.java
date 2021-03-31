package ru.ifmo.modules;

import ru.ifmo.command_modules.BaseCommandModule;
import ru.ifmo.command_modules.TestAdminModule;
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
                        System.out.println("Серверов: " + ServerManagerModule.getServers().size());
                        for(TokenServer server : ServerManagerModule.getServers()){
                            System.out.println(server.toString());
                        }
                        break;
                    case "/restart":
                        //TODO работает неправильно
                        ServerManagerModule.restart();
                        break;
                    case "/help":
                        System.out.println("/servers - количество серверов\n/exit - завершение работы");
                        break;
                    case "/add_test":
                        TokenServer server = new TokenServer("QBot", "f5c685e72504f75ae5d13ffba306a9e7bb9a2382dbe0492a525e7381555a63c03848d2e4239eadb8f51ec", "150386938");
                        server.addModule(new TestAdminModule());
                        BaseCommandModule b = new BaseCommandModule();
                        b.addCommand(message -> message.equalsIgnoreCase("привет")?"Привет!":null);
                        server.addModule(b);
                        DataBaseModule.save(server);
                        break;
                    case "/del":
                        try {
                            System.out.println("Введите id:");
                            String id = scanner.nextLine();
                            DataBaseModule.deleteById(Integer.parseInt(id));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case "/add":
                        try {
                            System.out.println("Введите название");
                            String name = scanner.nextLine();
                            System.out.println("Введите токен");
                            String token = scanner.nextLine();
                            System.out.println("Введите id группы");
                            String group_id = scanner.nextLine();
                            System.out.println("Введите id владельца");
                            String owner = scanner.nextLine();
                            //
                            TokenServer ser = new TokenServer(name, token, group_id, owner);
                            BaseCommandModule baseCommandModule = new BaseCommandModule();
                            baseCommandModule.addCommand(mes -> mes.equalsIgnoreCase("привет")?"Привет!":null);
                            ser.addModule(baseCommandModule);
                            DataBaseModule.save(ser);
                            ServerManagerModule.addServer(ser);
                        }catch (Exception e){
                            System.out.println("Ошибка. Возможно введены неправильные данные.");
                        }
                        break;
                    default:
                        System.out.println("Введите /help");
                        break;
                }
            }
        }
    }
}
