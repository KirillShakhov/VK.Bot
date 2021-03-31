package ru.ifmo;

import lombok.Getter;
import ru.ifmo.models.TokenServer;
import ru.ifmo.models.interfaces.IModule;
import ru.ifmo.modules.BaseCommandModule;
import ru.ifmo.modules.ConsoleModule;

import java.util.HashSet;

public class Bootstrap {
    @Getter
    public static HashSet<IModule> modules = new HashSet<>();
    public static void main(String[] args) {
        //Config
        Stages.add_otherModule(new ConsoleModule());
        //распределительный модуль

        //Add Servers
        try {
            TokenServer server1 = new TokenServer("NEXT", "940b6044164b91beec0001825b85784168ffba9ba115d40bc3c548f9c76de698c21f33f2d57e783f27463", "144561009");
            TokenServer server2 = new TokenServer("QBot", "f5c685e72504f75ae5d13ffba306a9e7bb9a2382dbe0492a525e7381555a63c03848d2e4239eadb8f51ec", "150386938");

            BaseCommandModule b1 = new BaseCommandModule();
            b1.addCommand(message -> message.equals("Привет") ? "Hi" : null);


            server1.addModule(b1);
            server2.addModule(b1);
            Stages.add_server(server1);
            Stages.add_server(server2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //

        //Start
        System.out.println("Running server...");
        Stages.testing_modules();
        Stages.loading_modules();
        Stages.loading_database();
        Stages.start();
        Stages.loading_otherModules();
    }
}
