package ru.ifmo;

import ru.ifmo.modules.ConsoleModule;
import ru.ifmo.modules.ServerManagerModule;

import java.util.HashSet;
import java.util.concurrent.Executors;
//            TokenServer server1 = new TokenServer("NEXT", "940b6044164b91beec0001825b85784168ffba9ba115d40bc3c548f9c76de698c21f33f2d57e783f27463", "144561009");
//            TokenServer server2 = new TokenServer("QBot", "f5c685e72504f75ae5d13ffba306a9e7bb9a2382dbe0492a525e7381555a63c03848d2e4239eadb8f51ec", "150386938");
public class Bootstrap {
    static HashSet<Runnable> modules = new HashSet<>();
    public static void main(String[] args) {
        add_module(new ConsoleModule());
        add_module(new ServerManagerModule());
        loading_modules();
    }
    public static void add_module(Runnable module){
        modules.add(module);
    }
    public static void loading_modules() {
        for(Runnable module : modules){
            Executors.newCachedThreadPool().execute(module);
        }
    }
}
