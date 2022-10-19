package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userDao = new UserServiceImpl();
        userDao.createUsersTable();

        User user1 = new User("Vanya", "baban", (byte) 35);
        User user2 = new User("Viktor", "sherema", (byte) 38);
        User user3 = new User("Vasiliy", "usach", (byte) 18);
        User user4 = new User("Nik", "shishkin", (byte) 44);

        for (User user : Arrays.asList(user1, user2, user3, user4)) {
            userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("New user – %s добавлен БД %n", user.getName());
        }
        System.out.println(userDao.getAllUsers());
        //userDao.cleanUsersTable();
        //userDao.dropUsersTable();
    }
}
