package services;

import by.it.connection.ConnectionManager;
import by.it.dao.UserDao;
import by.it.dao.impl.UserDaoImpl;
import by.it.entities.User;
import by.it.services.UserService;
import by.it.services.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceTest {
    String[] name = {"виталя", "петя", "вася", "света", "лера", "иван"};
    String[] login = {"lipskj", "vasya", "petya", "sveta", "lera", "vanya", "misha"};
    UserDao userDao = UserDaoImpl.getInstance();
    UserService userService = UserServiceImpl.getInstance();


    @Test
    public void userServiceTest() throws SQLException {
        for (; userDao.selectAll().size() < 7; ) {
            userService.createUser(name[(int) (Math.random() * 6)], login[(int) (Math.random() * 7)],
                    String.valueOf((int) (1000 + Math.random() * 2000)));
        }
//        userService.createUser("vital", "lipskiy", "9876");
    }

    @Test
    public void userTest() throws SQLException {
        Connection connection1 = ConnectionManager.getConnection(2);
        connection1.setAutoCommit(false);
        User user = userDao.selectByLogin("a");
        userService.createUser("вася", "a", "111");
        User newUser = userDao.selectByLogin("a");
        Assert.assertEquals("createUser incorrect",user,null);
        Assert.assertNotEquals("createUser incorrect",newUser,null);

        user = userService.authorization("a","111");
        Assert.assertEquals("authorization incorrect",user,newUser);
        userDao.delete(user.getIdUser());
        connection1.commit();
    }
}


