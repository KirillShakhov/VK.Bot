package ru.ifmo.modules;

import org.apache.logging.log4j.core.jmx.Server;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.ifmo.utills.HibernateSessionFactoryUtil;
import ru.ifmo.models.TokenServer;

import java.util.List;

public class DataBaseModule {
    //TODO требует обкатки

    public static TokenServer findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(TokenServer.class, id);
    }

    public static void save(TokenServer server) {
        //Work
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        server.destroy();
        session.save(server);
        tx1.commit();
        session.close();
    }

    public static void update(TokenServer server) {
        //Work
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        server.destroy();
        session.merge(server);
        tx1.commit();
        session.close();
    }

    public static void delete(TokenServer server) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(server);
        tx1.commit();
        session.close();
    }

    public static void deleteById(int id) {
        //Work
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        TokenServer tokenServer = new TokenServer();
        tokenServer.setId(id);
        session.delete(tokenServer);
        tx1.commit();
        session.close();
    }

    public static TokenServer findByName(String name) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(TokenServer.class, name);
    }

    public static List<TokenServer> findAll() {
        //Work
        //noinspection unchecked
        List<TokenServer> tokenServer = (List<TokenServer>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From TokenServer").list();
        return tokenServer;
    }

    public static void saveAll() {
        for(TokenServer server : ServerManagerModule.getServers()){
            update(server);
        }
    }
}
