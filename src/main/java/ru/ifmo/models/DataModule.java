package ru.ifmo.models;

import java.util.List;

public class DataModule{
    private static TokenServerDao tokenServerDao = new TokenServerDao();

    public TokenServer findServer(int id) {
        return tokenServerDao.findById(id);
    }

    public static void saveUser(TokenServer server) {
        tokenServerDao.save(server);
    }

    public static void deleteUser(TokenServer server) {
        tokenServerDao.delete(server);
    }

    public static void updateUser(TokenServer user) {
        tokenServerDao.update(user);
    }

    public static List<TokenServer> findAllUsers() {
        return tokenServerDao.findAll();
    }

    public static TokenServer findTokenById(int id) {
        return tokenServerDao.findTokenById(id);
    }
}
