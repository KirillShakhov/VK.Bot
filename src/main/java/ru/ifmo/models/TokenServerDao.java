package ru.ifmo.models;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.ifmo.HibernateSessionFactoryUtil;

import java.util.List;

public class TokenServerDao {

    public static TokenServer findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(TokenServer.class, id);
    }

    public static void save(TokenServer server) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(server);
        tx1.commit();
        session.close();
    }

    public static void update(TokenServer user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public static void delete(TokenServer user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public static TokenServer findTokenById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(TokenServer.class, id);
    }

    public static List<TokenServer> findAll() {
        List<TokenServer> tokenServer = (List<TokenServer>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From TokenServer").list();
        return tokenServer;
    }
}
