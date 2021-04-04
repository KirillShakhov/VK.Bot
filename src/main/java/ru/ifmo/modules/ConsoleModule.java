package ru.ifmo.modules;

import ru.ifmo.server_modules.AdminServerModule;
import ru.ifmo.server_modules.BaseCommandServerModule;
import ru.ifmo.server_modules.TestAdminServerModule;
import ru.ifmo.models.TokenServer;

import java.util.Scanner;

public class ConsoleModule implements Runnable {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        while (true) {
            if (scanner.hasNext()) {
                String line = scanner.nextLine();
                if ("/exit".equals(line)) {
                    System.out.println("Servers down. \\/");
                    System.exit(0);
                } else if ("/servers".equals(line)) {
                    System.out.println("Серверов: " + ServerManagerModule.getServers().size());
                    for (TokenServer server : ServerManagerModule.getServers()) {
                        System.out.println(server.toString());
                    }
                } else if ("/help".equals(line)) {
                    System.out.println("/servers - количество серверов\n/exit - завершение работы");
                } else if ("/add_test".equals(line)) {
                    TokenServer server = new TokenServer("QBot", "f5c685e72504f75ae5d13ffba306a9e7bb9a2382dbe0492a525e7381555a63c03848d2e4239eadb8f51ec", "150386938");
                    server.addModule(new TestAdminServerModule());
                    BaseCommandServerModule b = new BaseCommandServerModule();
                    b.addCommand(message -> message.equalsIgnoreCase("привет") ? "Привет!" : null);
                    server.addModule(b);
                    DataBaseModule.save(server);
                } else if ("/add_module".equals(line)) {
                    try {
                        System.out.println("Введите id:");
                        String id = scanner.nextLine();
                        TokenServer server = ServerManagerModule.getServerByID(Integer.parseInt(id));
                        System.out.println("Какой модуль добавить?(admin)");
                        String module = scanner.nextLine();
                        if(module.equals("admin")){
                            server.addModule(new AdminServerModule());
                        }
                        DataBaseModule.update(server);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if ("/del".equals(line)) {
                    try {
                        System.out.println("Введите id:");
                        String id = scanner.nextLine();
                        DataBaseModule.deleteById(Integer.parseInt(id));
                        ServerManagerModule.removeServerByID(Integer.parseInt(id));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if ("/add".equals(line)) {
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
                        BaseCommandServerModule baseCommandModule = new BaseCommandServerModule();
                        baseCommandModule.addCommand(mes -> mes.equalsIgnoreCase("привет") ? "Привет!" : null);
                        ser.addModule(baseCommandModule);
                        DataBaseModule.save(ser);
                        if (ServerManagerModule.addServer(ser)) {
                            System.out.println("Сервер добавлен!");
                        } else {
                            System.out.println("Сервер не добавлен. Такой сервер уже существует.");
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка. Возможно введены неправильные данные.");
                    }
                }
                else if (line.startsWith("/add")) {
                    try {
                        String[] arr = line.split("#");
                        TokenServer server = new TokenServer(arr[1], arr[2], arr[3], arr[4]);
                        BaseCommandServerModule b = new BaseCommandServerModule();
                        b.addCommand(mes -> mes.equalsIgnoreCase("привет") ? "Привет!" : null);
                        server.addModule(b);
                        DataBaseModule.save(server);
                        if (ServerManagerModule.addServer(server)) {
                            System.out.println("Сервер добавлен!");
                        } else {
                            System.out.println("Сервер не добавлен. Такой сервер уже существует.");
                        }
                    } catch (Exception e) {
                        System.out.println("Неправильно введена команда.");
                        System.out.println("/add#name#token#group_id#owner_id");
                    }
                }
                else {
                    System.out.println("Введите /help");
                }
            }
        }
    }
}
