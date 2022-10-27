package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        final String hql = "CREATE TABLE if not exists `users` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `last_name` varchar(45) NOT NULL,\n" +
                "  `age` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            //  if (transaction != null) {
            transaction.rollback();
            //  }
        }
    }

    @Override
    public void dropUsersTable() {
        final String hql = "DROP TABLE IF EXISTS users;";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            //  if (transaction != null) {
            transaction.rollback();
            // }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            //   if (transaction != null) {
            transaction.rollback();
            //  }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            //   if (transaction == null) {
            transaction.rollback();
            //  }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "SELECT u FROM User u";
        Transaction transaction = null;
        List<User> userList = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery(hql, User.class).list();
            transaction.commit();
            return userList;

        } catch (HibernateException e) {
            e.printStackTrace();
            //  if (transaction != null) {
            transaction.rollback();
            //  }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        final String hql = "TRUNCATE TABLE users;";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            //  if (transaction != null) {
            transaction.rollback();
            // }
        }
    }
}