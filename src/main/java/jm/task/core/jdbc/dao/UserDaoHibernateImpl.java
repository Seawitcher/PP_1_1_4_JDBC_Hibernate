package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();

        final String hql = "CREATE TABLE if not exists `users` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `last_name` varchar(45) NOT NULL,\n" +
                "  `age` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";
        session.beginTransaction();
        Query query = session.createSQLQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        final String hql = "DROP TABLE IF EXISTS users;";
        session.beginTransaction();
        Query query =session.createSQLQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session =sessionFactory.openSession();

        User user = new User(name,lastName,age);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();


    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.openSession();
        String hql = "SELECT u FROM User u";
        session.getTransaction();
        Query query = session.createQuery(hql, User.class);

        return  (List<User>) query.list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        final String sql = "TRUNCATE TABLE users;";
        session.beginTransaction();
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();

    }
}
