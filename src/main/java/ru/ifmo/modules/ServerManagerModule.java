package ru.ifmo.modules;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.server_modules.AdminServerModule;
import ru.ifmo.server_modules.BaseCommandServerModule;
import ru.ifmo.server_modules.TestAdminServerModule;
import ru.ifmo.models.TokenServer;
import ru.ifmo.models.interfaces.IServerModule;

import java.util.HashSet;

public class ServerManagerModule implements Runnable {
    @Setter @Getter
    private static HashSet<TokenServer> servers = new HashSet<>();
    @Getter
    public static HashSet<IServerModule> modules = new HashSet<>();

    static {
        //Подключение модулей для дальнейшего использования
        BaseCommandServerModule b = new BaseCommandServerModule();
        b.addCommand(message -> message.equalsIgnoreCase("привет")?"Привет!":null);
        modules.add(b);
        modules.add(new TestAdminServerModule());
        modules.add(new AdminServerModule());
    }

    public static void removeServerByID(int id) {
        servers.removeIf(server -> server.getId() == id);
    }

    public static TokenServer getServerByID(int id) {
        return servers.stream().filter(server -> server.getId() == id).findFirst().orElse(null);
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(DataBaseModule::saveAll));
        System.out.println("Running server...");
//        System.out.println("Loading Modules...");
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
        int i = 1;
        for(TokenServer server : DataBaseModule.findAll()){
            server.init();
            addServer(server);
            System.out.println("Initialized Module("+(i++)+"/"+ size +")");
        }
    }

    public static boolean addServer(TokenServer server){
        for(TokenServer s : servers){
            if(s.getName().equals(server.getName()) || s.getToken().equals(server.getToken())){
                return false;
            }
        }
        servers.add(server);
        return true;
    }
    public static void addModuleToServer(int id, IServerModule module){
        TokenServer server = ServerManagerModule.getServerByID(id);
        server.addModule(module);
        DataBaseModule.update(server);
    }
}
