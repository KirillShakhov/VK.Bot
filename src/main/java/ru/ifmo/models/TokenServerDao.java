package ru.ifmo.models;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.ifmo.HibernateSessionFactoryUtil;

import java.util.List;

public class TokenServerDao {

    public TokenServer findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(TokenServer.class, id);
    }

    public void save(TokenServer server) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(server);
        tx1.commit();
        session.close();
    }

    public void update(TokenServer user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(TokenServer user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public TokenServer findTokenById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(TokenServer.class, id);
    }

    public List<TokenServer> findAll() {
        List<TokenServer> users = (List<TokenServer>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From TokenServer").list();
        return users;
    }
}
